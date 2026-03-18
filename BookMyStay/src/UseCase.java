import java.util.*;

// =========================
// MAIN CLASS
// =========================
public class UseCase {

    // =========================
    // ADD-ON SERVICE CLASS
    // =========================
    static class AddOnService {
        private String serviceName;
        private double cost;

        public AddOnService(String serviceName, double cost) {
            this.serviceName = serviceName;
            this.cost = cost;
        }

        public String getServiceName() {
            return serviceName;
        }

        public double getCost() {
            return cost;
        }
    }

    // =========================
    // ADD-ON SERVICE MANAGER
    // =========================
    static class AddOnServiceManager {

        // Map: reservationID → list of services
        private Map<String, List<AddOnService>> serviceMapping;

        public AddOnServiceManager() {
            serviceMapping = new HashMap<>();
        }

        // Add service to reservation
        public void addService(String reservationId, AddOnService service) {

            serviceMapping.putIfAbsent(reservationId, new ArrayList<>());

            serviceMapping.get(reservationId).add(service);

            System.out.println(service.getServiceName() + " added to " + reservationId);
        }

        // Calculate total add-on cost
        public double calculateTotalServiceCost(String reservationId) {

            double total = 0;

            List<AddOnService> services = serviceMapping.get(reservationId);

            if (services != null) {
                for (AddOnService s : services) {
                    total += s.getCost();
                }
            }

            return total;
        }
    }

    // =========================
    // MAIN METHOD
    // =========================
    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId = "RES-101";

        // Create services
        AddOnService breakfast = new AddOnService("Breakfast", 500);
        AddOnService spa = new AddOnService("Spa", 1000);

        System.out.println("Add-On Service Selection\n");

        // Add services
        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, spa);

        // Calculate total
        double totalCost = manager.calculateTotalServiceCost(reservationId);

        System.out.println("\nReservation ID: " + reservationId);
        System.out.println("Total Add-On Cost: " + totalCost);
    }
}