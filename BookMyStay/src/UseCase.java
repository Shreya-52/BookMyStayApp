import java.io.*;
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
        }

        public void setAvailability(String type, int count) {
            availability.put(type, count);
        }

        public Map<String, Integer> getAvailability() {
            return availability;
        }

        public void display() {
            System.out.println("\nCurrent Inventory:");
            for (String type : availability.keySet()) {
                System.out.println(type + ": " + availability.get(type));
            }
        }
    }

    // =========================
    // FILE PERSISTENCE SERVICE
    // =========================
    static class FilePersistenceService {

        // Save inventory to file
        public void saveInventory(RoomInventory inventory, String filePath) {

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

                for (Map.Entry<String, Integer> entry : inventory.getAvailability().entrySet()) {
                    writer.write(entry.getKey() + ":" + entry.getValue());
                    writer.newLine();
                }

                System.out.println("Inventory saved successfully.");

            } catch (IOException e) {
                System.out.println("Error saving inventory: " + e.getMessage());
            }
        }

        // Load inventory from file
        public void loadInventory(RoomInventory inventory, String filePath) {

            File file = new File(filePath);

            if (!file.exists()) {
                System.out.println("No valid inventory data found. Starting fresh.");
                return;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

                String line;

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");

                    if (parts.length == 2) {
                        String type = parts[0];
                        int count = Integer.parseInt(parts[1]);
                        inventory.setAvailability(type, count);
                    }
                }

                System.out.println("Inventory loaded successfully.");

            } catch (IOException | NumberFormatException e) {
                System.out.println("Error loading inventory. Starting fresh.");
            }
        }
    }

    // =========================
    // MAIN METHOD
    // =========================
    public static void main(String[] args) {

        String filePath = "inventory.txt";

        RoomInventory inventory = new RoomInventory();
        FilePersistenceService persistence = new FilePersistenceService();

        System.out.println("System Recovery\n");

        // Load previous state
        persistence.loadInventory(inventory, filePath);

        // If empty, initialize default
        if (inventory.getAvailability().isEmpty()) {
            inventory.setAvailability("Single", 5);
            inventory.setAvailability("Double", 3);
            inventory.setAvailability("Suite", 2);
        }

        // Display current inventory
        inventory.display();

        // Save current state
        persistence.saveInventory(inventory, filePath);
    }
}