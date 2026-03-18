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
            availability.put("Single", 5);
            availability.put("Double", 3);
        }

        public void increaseAvailability(String type) {
            availability.put(type, availability.getOrDefault(type, 0) + 1);
        }

        public int getAvailability(String type) {
            return availability.getOrDefault(type, 0);
        }
    }

    // =========================
    // CANCELLATION SERVICE
    // =========================
    static class CancellationService {

        // Stack for rollback tracking
        private Stack<String> releasedRoomIds;

        // Map: reservationID → roomType
        private Map<String, String> reservationMap;

        public CancellationService() {
            releasedRoomIds = new Stack<>();
            reservationMap = new HashMap<>();
        }

        // Register confirmed booking
        public void registerBooking(String reservationId, String roomType) {
            reservationMap.put(reservationId, roomType);
        }

        // Cancel booking
        public void cancelBooking(String reservationId, RoomInventory inventory) {

            if (!reservationMap.containsKey(reservationId)) {
                System.out.println("Invalid cancellation: Reservation not found.");
                return;
            }

            String roomType = reservationMap.get(reservationId);

            // Restore inventory
            inventory.increaseAvailability(roomType);

            // Track rollback using stack
            releasedRoomIds.push(reservationId);

            // Remove from active bookings
            reservationMap.remove(reservationId);

            System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
        }

        // Display rollback history (LIFO)
        public void showRollbackHistory() {

            System.out.println("\nRollback History (Most Recent First):");

            Stack<String> tempStack = (Stack<String>) releasedRoomIds.clone();

            while (!tempStack.isEmpty()) {
                System.out.println("Released Reservation ID: " + tempStack.pop());
            }
        }
    }

    // =========================
    // MAIN METHOD
    // =========================
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        CancellationService service = new CancellationService();

        // Simulate confirmed bookings
        service.registerBooking("Single-1", "Single");
        service.registerBooking("Double-1", "Double");

        // Cancel one booking
        service.cancelBooking("Single-1", inventory);

        // Show rollback history
        service.showRollbackHistory();

        // Show updated inventory
        System.out.println("\nUpdated Single Room Availability: " +
                inventory.getAvailability("Single"));
    }
}