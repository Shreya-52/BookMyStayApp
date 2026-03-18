import java.util.*;

// =========================
// MAIN CLASS
// =========================
public class UseCase {

    // =========================
    // ABSTRACT ROOM CLASS
    // =========================
    static abstract class Room {
        protected int beds;
        protected int size;
        protected double price;

        public Room(int beds, int size, double price) {
            this.beds = beds;
            this.size = size;
            this.price = price;
        }

        public void displayDetails() {
            System.out.println("Beds: " + beds);
            System.out.println("Size: " + size + " sqft");
            System.out.println("Price per night: " + price);
        }
    }

    // =========================
    // ROOM TYPES
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
    // INVENTORY (READ SOURCE)
    // =========================
    static class RoomInventory {
        private Map<String, Integer> availability;

        public RoomInventory() {
            availability = new HashMap<>();
            availability.put("Single", 5);
            availability.put("Double", 3);
            availability.put("Suite", 2);
        }

        public Map<String, Integer> getAvailability() {
            return availability;
        }
    }

    // =========================
    // SEARCH SERVICE (READ ONLY)
    // =========================
    static class RoomSearchService {

        private RoomInventory inventory;

        public RoomSearchService(RoomInventory inventory) {
            this.inventory = inventory;
        }

        public void searchAvailableRooms() {

            Map<String, Integer> availability = inventory.getAvailability();

            System.out.println("Room Search Results:\n");

            for (String type : availability.keySet()) {

                int count = availability.get(type);

                // Filter: only available rooms
                if (count > 0) {

                    Room room = null;

                    // Map type → object
                    switch (type) {
                        case "Single":
                            room = new SingleRoom();
                            break;
                        case "Double":
                            room = new DoubleRoom();
                            break;
                        case "Suite":
                            room = new SuiteRoom();
                            break;
                    }

                    // Display
                    System.out.println(type + " Room:");
                    room.displayDetails();
                    System.out.println("Available: " + count + "\n");
                }
            }
        }
    }

    // =========================
    // MAIN METHOD
    // =========================
    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Create search service
        RoomSearchService searchService = new RoomSearchService(inventory);

        // Perform search
        searchService.searchAvailableRooms();
    }
}