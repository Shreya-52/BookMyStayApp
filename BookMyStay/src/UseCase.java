import java.util.*;

// =========================
// MAIN CLASS
// =========================
public class UseCase {

    // =========================
    // RESERVATION CLASS
    // =========================
    static class Reservation {
        String guestName;
        String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }
    }

    // =========================
    // BOOKING QUEUE
    // =========================
    static class BookingQueue {
        private Queue<Reservation> queue = new LinkedList<>();

        public synchronized void addReservation(Reservation r) {
            queue.add(r);
        }

        public synchronized Reservation getReservation() {
            return queue.poll();
        }
    }

    // =========================
    // INVENTORY
    // =========================
    static class RoomInventory {
        private Map<String, Integer> availability;

        public RoomInventory() {
            availability = new HashMap<>();
            availability.put("Single", 5);
            availability.put("Double", 3);
            availability.put("Suite", 2);
        }

        public synchronized boolean allocate(String type) {
            int count = availability.getOrDefault(type, 0);

            if (count > 0) {
                availability.put(type, count - 1);
                return true;
            }
            return false;
        }

        public synchronized void display() {
            System.out.println("\nRemaining Inventory:");
            for (String type : availability.keySet()) {
                System.out.println(type + ": " + availability.get(type));
            }
        }
    }

    // =========================
    // ALLOCATION SERVICE
    // =========================
    static class RoomAllocationService {
        private Map<String, Integer> counters = new HashMap<>();

        public RoomAllocationService() {
            counters.put("Single", 1);
            counters.put("Double", 1);
            counters.put("Suite", 1);
        }

        public synchronized String allocateRoom(String type) {
            int num = counters.get(type);
            counters.put(type, num + 1);
            return type.charAt(0) + "-Room-" + num;
        }
    }

    // =========================
    // CONCURRENT PROCESSOR
    // =========================
    static class ConcurrentBookingProcessor implements Runnable {

        private BookingQueue queue;
        private RoomInventory inventory;
        private RoomAllocationService service;

        public ConcurrentBookingProcessor(BookingQueue queue,
                                          RoomInventory inventory,
                                          RoomAllocationService service) {
            this.queue = queue;
            this.inventory = inventory;
            this.service = service;
        }

        @Override
        public void run() {

            while (true) {

                Reservation r;

                // CRITICAL SECTION 1 (Queue access)
                synchronized (queue) {
                    r = queue.getReservation();
                }

                if (r == null) break;

                // CRITICAL SECTION 2 (Inventory + allocation)
                synchronized (inventory) {
                    if (inventory.allocate(r.roomType)) {

                        String roomId = service.allocateRoom(r.roomType);

                        System.out.println("Booking confirmed for Guest: " +
                                r.guestName + ", Room ID: " + roomId);
                    } else {
                        System.out.println("Booking failed for " +
                                r.guestName + " (No rooms available)");
                    }
                }
            }
        }
    }

    // =========================
    // MAIN METHOD
    // =========================
    public static void main(String[] args) {

        BookingQueue queue = new BookingQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService service = new RoomAllocationService();

        // Add booking requests
        queue.addReservation(new Reservation("Abhi", "Single"));
        queue.addReservation(new Reservation("Varnith", "Double"));
        queue.addReservation(new Reservation("Kunal", "Suite"));
        queue.addReservation(new Reservation("Subha", "Single"));

        // Create threads
        Thread t1 = new Thread(new ConcurrentBookingProcessor(queue, inventory, service));
        Thread t2 = new Thread(new ConcurrentBookingProcessor(queue, inventory, service));

        System.out.println("Concurrent Booking Simulation\n");

        // Start threads
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted.");
        }

        // Final inventory
        inventory.display();
    }
}