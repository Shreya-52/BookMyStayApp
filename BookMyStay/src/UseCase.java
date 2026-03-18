import java.util.*;

// =========================
// MAIN CLASS
// =========================
public class UseCase {

    // =========================
    // INVENTORY CLASS
    // =========================
    static class RoomInventory {
        private Map<String, Integer> availability;

        public RoomInventory() {
            availability = new HashMap<>();
            availability.put("Single", 2);
            availability.put("Double", 1);
        }

        public synchronized boolean reduceAvailability(String type) {
            int count = availability.getOrDefault(type, 0);

            if (count > 0) {
                availability.put(type, count - 1);
                return true;
            }
            return false;
        }
    }

    // =========================
    // ROOM ALLOCATION SERVICE
    // =========================
    static class RoomAllocationService {

        // Tracks assigned rooms (to avoid duplicates)
        private Set<String> allocatedRooms = new HashSet<>();

        // Track room count per type
        private Map<String, Integer> roomCounters = new HashMap<>();

        public RoomAllocationService() {
            roomCounters.put("Single", 1);
            roomCounters.put("Double", 1);
        }

        // Allocate unique room number
        public synchronized String allocateRoom(String type, RoomInventory inventory) {

            // Step 1: check availability
            if (!inventory.reduceAvailability(type)) {
                return null;
            }

            // Step 2: generate unique room number
            String roomNumber = generateRoomNumber(type);

            // Step 3: ensure uniqueness
            while (allocatedRooms.contains(roomNumber)) {
                roomNumber = generateRoomNumber(type);
            }

            allocatedRooms.add(roomNumber);

            return roomNumber;
        }

        private String generateRoomNumber(String type) {
            int count = roomCounters.get(type);
            roomCounters.put(type, count + 1);

            return type.substring(0, 1) + "-Room-" + count;
        }
    }

    // =========================
    // MAIN METHOD
    // =========================
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        System.out.println("Allocation Processing...\n");

        // Simulate multiple booking requests
        processBooking("Single", allocationService, inventory);
        processBooking("Single", allocationService, inventory);
        processBooking("Single", allocationService, inventory); // should fail

        processBooking("Double", allocationService, inventory);
        processBooking("Double", allocationService, inventory); // should fail
    }

    // =========================
    // BOOKING FLOW
    // =========================
    public static void processBooking(String type,
                                      RoomAllocationService service,
                                      RoomInventory inventory) {

        String room = service.allocateRoom(type, inventory);

        if (room != null) {
            System.out.println("Booking confirmed for " + type + ". Room ID: " + room);
        } else {
            System.out.println("Booking failed for " + type + " (No rooms available)");
        }
    }
}