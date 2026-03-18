import java.util.*;

// =========================
// MAIN CLASS
// =========================
public class UseCase {

    // =========================
    // RESERVATION CLASS
    // =========================
    static class Reservation {
        private String guestName;
        private String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public String getGuestName() {
            return guestName;
        }

        public String getRoomType() {
            return roomType;
        }
    }

    // =========================
    // BOOKING HISTORY CLASS
    // =========================
    static class BookingHistory {

        // List to store confirmed reservations
        private List<Reservation> confirmedReservations;

        public BookingHistory() {
            confirmedReservations = new ArrayList<>();
        }

        // Add reservation to history
        public void addReservation(Reservation reservation) {
            confirmedReservations.add(reservation);
        }

        // Retrieve all reservations
        public List<Reservation> getConfirmedReservations() {
            return confirmedReservations;
        }
    }

    // =========================
    // REPORT SERVICE CLASS
    // =========================
    static class BookingReportService {

        public void generateReport(BookingHistory history) {

            System.out.println("Booking History Report\n");

            List<Reservation> reservations = history.getConfirmedReservations();

            for (Reservation r : reservations) {
                System.out.println("Guest: " + r.getGuestName() +
                        ", Room Type: " + r.getRoomType());
            }
        }
    }

    // =========================
    // MAIN METHOD
    // =========================
    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulate confirmed bookings
        history.addReservation(new Reservation("Abhi", "Single"));
        history.addReservation(new Reservation("Suba", "Double"));
        history.addReservation(new Reservation("Varnith", "Suite"));

        // Generate report
        reportService.generateReport(history);
    }
}