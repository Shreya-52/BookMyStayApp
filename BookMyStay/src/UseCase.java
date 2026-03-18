import java.util.*;

// =========================
// MAIN CLASS
// =========================
public class UseCase {

    // =========================
    // CUSTOM EXCEPTION
    // =========================
    static class InvalidBookingException extends Exception {
        public InvalidBookingException(String message) {
            super(message);
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
            availability.put("Suite", 0); // intentionally 0 for testing
        }

        public int getAvailability(String type) {
            return availability.getOrDefault(type, -1);
        }
    }

    // =========================
    // VALIDATOR CLASS
    // =========================
    static class ReservationValidator {

        public static void validate(String guestName,
                                    String roomType,
                                    RoomInventory inventory)
                throws InvalidBookingException {

            // Validate guest name
            if (guestName == null || guestName.trim().isEmpty()) {
                throw new InvalidBookingException("Guest name cannot be empty");
            }

            // Validate room type
            if (roomType == null || roomType.trim().isEmpty()) {
                throw new InvalidBookingException("Room type cannot be empty");
            }

            // Check if room type exists
            int available = inventory.getAvailability(roomType);

            if (available == -1) {
                throw new InvalidBookingException("Invalid room type selected");
            }

            // Check availability
            if (available <= 0) {
                throw new InvalidBookingException("No rooms available for " + roomType);
            }
        }
    }

    // =========================
    // MAIN METHOD
    // =========================
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        // Test cases
        String guestName = "Abhi";
        String roomType = "Suite"; // intentionally unavailable

        try {
            ReservationValidator.validate(guestName, roomType, inventory);

            System.out.println("Booking is valid. Proceeding with reservation...");

        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        // Another test (invalid input)
        try {
            ReservationValidator.validate("", "Single", inventory);

        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }
    }
}