package com.internship.inventory.app;

import com.internship.inventory.core.InventoryManagementSystem;
import com.internship.inventory.model.Product;

import java.util.Scanner;

public class InventoryApp {

    public static void main(String[] args) {

        InventoryManagementSystem ims = new InventoryManagementSystem();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== INVENTORY MANAGEMENT SYSTEM ===");
            System.out.println("1. Add Product");
            System.out.println("2. Update Quantity");
            System.out.println("3. View Products (Sorted)");
            System.out.println("4. Search Products");
            System.out.println("5. Low Stock Alerts");
            System.out.println("6. Transaction History");
            System.out.println("7. Inventory Statistics");
            System.out.println("8. Undo Last Update");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.println("\n=== ADD NEW PRODUCT ===");
                    System.out.print("Enter SKU: ");
                    String sku = sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Category: ");
                    String category = sc.nextLine();
                    System.out.print("Enter Price: ");
                    double price = sc.nextDouble();
                    System.out.print("Enter Quantity: ");
                    int qty = sc.nextInt();
                    sc.nextLine();

                    ims.addProduct(new Product(sku, name, category, price, qty));
                }

                case 2 -> {
                    System.out.print("\nEnter SKU to update: ");
                    String sku = sc.nextLine();
                    System.out.print("Enter new quantity: ");
                    int qty = sc.nextInt();
                    sc.nextLine();
                    ims.updateQuantity(sku, qty);
                }

                case 3 -> {
                    System.out.print("\nSort by (sku/price/value/name): ");
                    String criteria = sc.nextLine();
                    ims.showSortedProducts(criteria);
                }

                case 4 -> {
                    System.out.print("\nEnter SKU or Name to search: ");
                    String query = sc.nextLine();
                    ims.searchProduct(query);
                }

                case 5 -> ims.showLowStock();

                case 6 -> {
                    System.out.print("\nEnter number of transactions to view: ");
                    int n = sc.nextInt();
                    sc.nextLine();
                    ims.showTransactions(n);
                }

                case 7 -> ims.showStatistics();

                case 8 -> ims.undoLastUpdate();

                case 9 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }

                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
