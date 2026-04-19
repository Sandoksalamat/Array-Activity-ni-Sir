import java.util.Scanner;

public class InventorySystem {

    static final int MAX = 100;

    static String[] code = new String[MAX];
    static String[] name = new String[MAX];
    static String[] category = new String[MAX];
    static int[] qty = new int[MAX];
    static double[] price = new double[MAX];
    static int[] sold = new int[MAX];
    static int[] reorder = new int[MAX];

    static int count = 0;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;

        do {
            menu();
            choice = getInt();

            switch (choice) {
                case 1: addProduct(); break;
                case 2: restockProduct(); break;
                case 3: recordSale(); break;
                case 4: updateProduct(); break;
                case 5: searchProduct(); break;
                case 6: inventoryReport(); break;
                case 7: lowStockReport(); break;
                case 8: salesSummary(); break;
                case 9: System.out.println("Exiting system..."); break;
                default: System.out.println("Invalid choice.");
            }

        } while (choice != 9);
    }

    // MENU
    static void menu() {
        System.out.println("\n===== INVENTORY SYSTEM =====");
        System.out.println("1. Add Product");
        System.out.println("2. Restock Product");
        System.out.println("3. Record Sale");
        System.out.println("4. Update Product");
        System.out.println("5. Search Product");
        System.out.println("6. Inventory Report");
        System.out.println("7. Low Stock Report");
        System.out.println("8. Sales Summary");
        System.out.println("9. Exit");
        System.out.print("Enter choice: ");
    }

    // SAFE INPUTS
    static int getInt() {
        while (true) {
            try {
                return sc.nextInt();
            } catch (Exception e) {
                System.out.print("Invalid input. Enter number: ");
                sc.next();
            }
        }
    }

    static double getDouble() {
        while (true) {
            try {
                return sc.nextDouble();
            } catch (Exception e) {
                System.out.print("Invalid input. Enter number: ");
                sc.next();
            }
        }
    }

    // ADD PRODUCT
    static void addProduct() {

        if (count >= MAX) {
            System.out.println("Inventory full.");
            return;
        }

        sc.nextLine();

        System.out.print("Product Code: ");
        code[count] = sc.nextLine();

        System.out.print("Product Name: ");
        name[count] = sc.nextLine();

        System.out.print("Category: ");
        category[count] = sc.nextLine();

        System.out.print("Quantity: ");
        qty[count] = getInt();

        if (qty[count] < 0) {
            System.out.println("Invalid quantity.");
            return;
        }

        System.out.print("Price: ");
        price[count] = getDouble();

        if (price[count] < 0) {
            System.out.println("Invalid price.");
            return;
        }

        System.out.print("Reorder Point: ");
        reorder[count] = getInt();

        sold[count] = 0;

        count++;
        System.out.println("Product added successfully!");
    }

    // FIND PRODUCT
    static int find(String c) {
        for (int i = 0; i < count; i++) {
            if (code[i].equalsIgnoreCase(c)) {
                return i;
            }
        }
        return -1;
    }

    // RESTOCK
    static void restockProduct() {

        sc.nextLine();
        System.out.print("Enter product code: ");
        String c = sc.nextLine();

        int i = find(c);

        if (i == -1) {
            System.out.println("Product not found.");
            return;
        }

        System.out.print("Add quantity: ");
        int add = getInt();

        if (add <= 0) {
            System.out.println("Invalid quantity.");
            return;
        }

        qty[i] += add;
        System.out.println("Stock updated successfully!");
    }

    // RECORD SALE
    static void recordSale() {

        sc.nextLine();
        System.out.print("Enter product code: ");
        String c = sc.nextLine();

        int i = find(c);

        if (i == -1) {
            System.out.println("Product not found.");
            return;
        }

        System.out.print("Quantity sold: ");
        int s = getInt();

        if (s <= 0) {
            System.out.println("Invalid quantity.");
            return;
        }

        if (s > qty[i]) {
            System.out.println("Not enough stock.");
            return;
        }

        qty[i] -= s;
        sold[i] += s;

        System.out.println("Sale recorded successfully!");
    }

    // UPDATE PRODUCT
    static void updateProduct() {

        sc.nextLine();
        System.out.print("Enter product code: ");
        String c = sc.nextLine();

        int i = find(c);

        if (i == -1) {
            System.out.println("Product not found.");
            return;
        }

        System.out.println("1. Name");
        System.out.println("2. Category");
        System.out.println("3. Price");
        System.out.println("4. Reorder Point");
        System.out.print("Choose: ");

        int ch = getInt();
        sc.nextLine();

        switch (ch) {
            case 1:
                System.out.print("New name: ");
                name[i] = sc.nextLine();
                break;
            case 2:
                System.out.print("New category: ");
                category[i] = sc.nextLine();
                break;
            case 3:
                System.out.print("New price: ");
                price[i] = getDouble();
                break;
            case 4:
                System.out.print("New reorder point: ");
                reorder[i] = getInt();
                break;
            default:
                System.out.println("Invalid choice.");
        }

        System.out.println("Product updated.");
    }

    // SEARCH
    static void searchProduct() {

        sc.nextLine();
        System.out.print("Enter product code: ");
        String c = sc.nextLine();

        int i = find(c);

        if (i == -1) {
            System.out.println("Product not found.");
            return;
        }

        System.out.println("\n=== PRODUCT INFO ===");
        System.out.println("Code: " + code[i]);
        System.out.println("Name: " + name[i]);
        System.out.println("Category: " + category[i]);
        System.out.println("Stock: " + qty[i]);
        System.out.println("Price: " + price[i]);
        System.out.println("Sold: " + sold[i]);
    }

    // INVENTORY REPORT
    static void inventoryReport() {

        System.out.println("\n===== INVENTORY REPORT =====");

        for (int i = 0; i < count; i++) {

            double value = qty[i] * price[i];
            String status = (qty[i] <= reorder[i]) ? "LOW" : "OK";

            System.out.println(code[i] + " | " + name[i] +
                    " | Stock: " + qty[i] +
                    " | Value: " + value +
                    " | " + status);
        }
    }

    // LOW STOCK
    static void lowStockReport() {

        System.out.println("\n===== LOW STOCK ITEMS =====");

        for (int i = 0; i < count; i++) {
            if (qty[i] <= reorder[i]) {
                System.out.println(code[i] + " - " + name[i] +
                        " (Stock: " + qty[i] + ")");
            }
        }
    }

    // SALES SUMMARY
    static void salesSummary() {

        System.out.println("\n===== SALES SUMMARY =====");

        int max = -1, min = Integer.MAX_VALUE;
        int maxI = -1, minI = -1;

        for (int i = 0; i < count; i++) {

            System.out.println(name[i] + " sold: " + sold[i]);

            if (sold[i] > max) {
                max = sold[i];
                maxI = i;
            }

            if (sold[i] < min) {
                min = sold[i];
                minI = i;
            }
        }

        if (maxI != -1)
            System.out.println("Best Selling: " + name[maxI]);

        if (minI != -1)
            System.out.println("Least Selling: " + name[minI]);
    }
}