import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PhoneBook {

    // HashMap to store contacts: Key is Phone Number (String), Value is Name (String)
    private static Map<String, String> contacts = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Run the main menu loop
        runPhoneBook();
    }

    private static void runPhoneBook() {
        int choice;
        boolean running = true;

        // Populate with a couple of default contacts for demonstration
        contacts.put("123-456-7890", "Alice Smith");
        contacts.put("987-654-3210", "Bob Johnson");

        while (running) {
            displayMenu();
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline left over
                System.out.println("---------------------------------");

                switch (choice) {
                    case 1:
                        addContact();
                        break;
                    case 2:
                        viewAllContacts();
                        break;
                    case 3:
                        searchContact();
                        break;
                    case 4:
                        deleteContact();
                        break;
                    case 5:
                        System.out.println("👋 Exiting PhoneBook application. Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("⚠️ Invalid choice. Please enter a number between 1 and 5.");
                }
                System.out.println("---------------------------------");
            } else {
                System.out.println("⚠️ Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
                System.out.println("---------------------------------");
            }
        }
        scanner.close(); // Close the scanner when the application exits
    }

    private static void displayMenu() {
        System.out.println("\n*** ☎️ PhoneBook Menu ***");
        System.out.println("1. Add New Contact");
        System.out.println("2. View All Contacts");
        System.out.println("3. Search Contact by Name/Number");
        System.out.println("4. Delete Contact by Number");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    // --- 1. Add Contact ---
    private static void addContact() {
        System.out.println("\n-- Add New Contact --");
        System.out.print("Enter Contact Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter Phone Number (e.g., 123-456-7890): ");
        String number = scanner.nextLine().trim();

        if (contacts.containsKey(number)) {
            System.out.println("❌ Error: A contact with the number " + number + " already exists.");
        } else if (name.isEmpty() || number.isEmpty()) {
             System.out.println("❌ Error: Name and Phone Number cannot be empty.");
        } else {
            contacts.put(number, name);
            System.out.println("✅ Success: Contact '" + name + "' added!");
        }
    }

    // --- 2. View All Contacts ---
    private static void viewAllContacts() {
        System.out.println("\n-- All Contacts --");
        if (contacts.isEmpty()) {
            System.out.println("📭 Your phonebook is empty.");
            return;
        }

        // Use Stream API to sort contacts by name before displaying
        contacts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(entry ->
                        System.out.printf("Name: %-20s | Number: %s\n", entry.getValue(), entry.getKey()));
    }

    // --- 3. Search Contact ---
    private static void searchContact() {
        System.out.println("\n-- Search Contact --");
        System.out.print("Enter Name or Phone Number to search: ");
        String query = scanner.nextLine().trim().toLowerCase();

        if (query.isEmpty()) {
            System.out.println("❌ Error: Search query cannot be empty.");
            return;
        }

        // Search in both keys (numbers) and values (names)
        Map<String, String> results = contacts.entrySet().stream()
                .filter(entry -> entry.getKey().contains(query) || entry.getValue().toLowerCase().contains(query))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (results.isEmpty()) {
            System.out.println("🔎 No contacts found matching '" + query + "'.");
        } else {
            System.out.println("\n✅ Found " + results.size() + " result(s):");
            results.forEach((number, name) ->
                    System.out.printf("Name: %-20s | Number: %s\n", name, number));
        }
    }

    // --- 4. Delete Contact ---
    private static void deleteContact() {
        System.out.println("\n-- Delete Contact --");
        System.out.print("Enter the Phone Number of the contact to delete: ");
        String number = scanner.nextLine().trim();

        String name = contacts.remove(number);

        if (name != null) {
            System.out.println("🗑️ Success: Contact '" + name + "' (" + number + ") has been deleted.");
        } else {
            System.out.println("❌ Error: No contact found with the number " + number + ".");
        }
    }
}