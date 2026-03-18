import java.util.*;

// =========================
// MAIN CLASS
// =========================
public class UseCase {

    // =========================
    // ROOM CLASS
    // =========================
    static abstract class Room {
        protected String type;
        protected int beds;
        protected double price;

        public Room(String type, int beds, double price) {
            this.type = type;
            this.beds = beds;
            this.price = price;
        }

        public void display() {
            System.out.println(type + " Room | Beds: " + beds + " | Price: " + price);
        }

        public String getType() {
            return type;
        }
    }

    static class SingleRoom extends Room {
        public SingleRoom() {
            super("Single", 1, 1500.0);
        }
    }

    static class DoubleRoom extends Room {
        public DoubleRoom() {
            super("Double", 2, 2500.0);
        }
    }

    static class SuiteRoom extends Room {
        public SuiteRoom() {
            super("Suite", 3, 5000.0);
        }
    }

    // =========================
    // INVENTORY CLASS
    // =========================
    static class RoomInventory {
        private Map<String, Integer> availability;

        public RoomInventory() {
            availability = new HashMap<>();
            availability.put("Single", 2);
            availability.put("Double", 1);
            availability.put("Suite", 1);
        }

        public int getAvailability(String type) {
            return availability.getOrDefault(type, 0);
        }

        public boolean reduceAvailability(String type) {
            int count = getAvailability(type);

            if (count > 0) {
                availability.put(type, count - 1);
                return true;
            }
            return false;
        }

        public void displayInventory() {
            System.out.println("\nCurrent Inventory:");
            for (String type : availability.keySet()) {
                System.out.println(type + ": " + availability.get(type));
            }
        }
    }

    // =========================
    // BOOKING SERVICE
    // =========================
    static class BookingService {

        private RoomInventory inventory;

        public BookingService(RoomInventory inventory) {
            this.inventory = inventory;
        }

        public void bookRoom(String type) {

            System.out.println("\nAttempting to book " + type + " room...");

            if (inventory.reduceAvailability(type)) {
                System.out.println("Booking SUCCESSFUL for " + type + " room.");
            } else {
                System.out.println("Booking FAILED. No " + type + " rooms available.");
            }
        }
    }

    // =========================
    // MAIN METHOD
    // =========================
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService(inventory);

        // Initial Inventory
        inventory.displayInventory();

        // Booking attempts
        bookingService.bookRoom("Single");
        bookingService.bookRoom("Double");
        bookingService.bookRoom("Suite");

        // Try booking again (to show failure)
        bookingService.bookRoom("Double");

        // Final Inventory
        inventory.displayInventory();
    }
}