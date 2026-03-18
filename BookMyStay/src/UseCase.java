import java.util.HashMap;
import java.util.Map;

public class UseCase {

    // =========================
    // Abstract Room Class
    // =========================
    static abstract class Room {
        protected int numberOfBeds;
        protected int squareFeet;
        protected double pricePerNight;

        public Room(int beds, int size, double price) {
            this.numberOfBeds = beds;
            this.squareFeet = size;
            this.pricePerNight = price;
        }

        public void displayDetails() {
            System.out.println("Beds: " + numberOfBeds);
            System.out.println("Size: " + squareFeet + " sqft");
            System.out.println("Price per night: " + pricePerNight);
        }
    }

    // =========================
    // Room Types
    // =========================
    static class SingleRoom extends Room {
        public SingleRoom() {
            super(1, 250, 1500.0);
        }
    }

    static class DoubleRoom extends Room {
        public DoubleRoom() {
            super(2, 400, 2500.0);
        }
    }

    static class SuiteRoom extends Room {
        public SuiteRoom() {
            super(3, 750, 5000.0);
        }
    }

    // =========================
    // RoomInventory Class
    // =========================
    static class RoomInventory {

        // HashMap to store availability
        private Map<String, Integer> roomAvailability;

        // Constructor
        public RoomInventory() {
            roomAvailability = new HashMap<>();
            initializeInventory();
        }

        // Initialize default values
        private void initializeInventory() {
            roomAvailability.put("Single", 5);
            roomAvailability.put("Double", 3);
            roomAvailability.put("Suite", 2);
        }

        // Get all availability
        public Map<String, Integer> getRoomAvailability() {
            return roomAvailability;
        }

        // Update availability safely
        public void updateAvailability(String roomType, int count) {
            if (roomAvailability.containsKey(roomType)) {
                roomAvailability.put(roomType, count);
            } else {
                System.out.println("Invalid room type: " + roomType);
            }
        }
    }

    // =========================
    // MAIN METHOD
    // =========================
    public static void main(String[] args) {

        System.out.println("Hotel Room Inventory Status\n");

        // Create room objects
        Room single = new SingleRoom();
        Room dbl = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Create inventory
        RoomInventory inventory = new RoomInventory();

        // Fetch availability
        Map<String, Integer> availability = inventory.getRoomAvailability();

        // Display Single Room
        System.out.println("Single Room:");
        single.displayDetails();
        System.out.println("Available Rooms: " + availability.get("Single") + "\n");

        // Display Double Room
        System.out.println("Double Room:");
        dbl.displayDetails();
        System.out.println("Available Rooms: " + availability.get("Double") + "\n");

        // Display Suite Room
        System.out.println("Suite Room:");
        suite.displayDetails();
        System.out.println("Available Rooms: " + availability.get("Suite"));
    }
}