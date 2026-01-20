package com.internship.inventory.core;

import com.internship.inventory.model.Product;
import com.internship.inventory.transaction.Transaction;
import com.internship.inventory.utils.InventoryUtils;
import com.internship.inventory.comparators.*;

import java.util.*;

public class InventoryManagementSystem {

    private HashSet<Product> productSet = new HashSet<>();
    private TreeSet<Product> sortedProducts = new TreeSet<>();
    private LinkedList<Transaction> transactionLog = new LinkedList<>();
    private Stack<Product> undoStack = new Stack<>();
    private Queue<Product> lowStockQueue = new LinkedList<>();

    private final int LOW_STOCK_THRESHOLD = 10;

    public void addProduct(Product p) {
        if (productSet.add(p)) {
            sortedProducts.add(p);
            transactionLog.addFirst(new Transaction("ADD", p.getSku(), "Added " + p.getName() + " (Qty: " + p.getQuantity() + ")"));

            if (p.getQuantity() < LOW_STOCK_THRESHOLD) {
                lowStockQueue.add(p);
                System.out.println("Low stock alert for " + p.getSku() + "!");
            }
            System.out.println("Product added successfully!");
        } else {
            System.out.println("Duplicate SKU not allowed.");
        }
    }

    public void updateQuantity(String sku, int qty) {
        for (Product p : productSet) {
            if (p.getSku().equals(sku)) {
                undoStack.push(new Product(p.getSku(), p.getName(), p.getCategory(), p.getPrice(), p.getQuantity()));

                int oldQty = p.getQuantity();
                p.setQuantity(qty);

                transactionLog.addFirst(new Transaction("UPDATE", sku, "Quantity changed from " + oldQty + " to " + qty));
                System.out.println("Quantity updated successfully!");

                if (qty < LOW_STOCK_THRESHOLD) {
                    lowStockQueue.add(p);
                }
                return;
            }
        }
        System.out.println("❌ Product not found.");
    }

    public void undoLastUpdate() {
        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo.");
            return;
        }

        Product prev = undoStack.pop();
        for (Product p : productSet) {
            if (p.getSku().equals(prev.getSku())) {
                p.setQuantity(prev.getQuantity());
                transactionLog.addFirst(new Transaction("UNDO", prev.getSku(), "Reverted quantity to " + prev.getQuantity()));
                System.out.println("Undo successful!");
                return;
            }
        }
    }

    public void showSortedProducts(String criteria) {
        List<Product> list = new ArrayList<>(productSet);

        switch (criteria.toLowerCase()) {
            case "sku":
                list.sort(null);
                System.out.println("\n=== PRODUCTS SORTED BY SKU ===");
                break;
            case "price":
                list.sort(new PriceComparator());
                System.out.println("\n=== PRODUCTS SORTED BY PRICE ===");
                break;
            case "value":
                list.sort(new ValueComparator());
                System.out.println("\n=== PRODUCTS SORTED BY VALUE ===");
                break;
            case "name":
                list.sort(new NameComparator());
                System.out.println("\n=== PRODUCTS SORTED BY NAME ===");
                break;
            default:
                System.out.println("Invalid sort criteria.");
                return;
        }

        System.out.printf("%-10s %-20s %-15s %-10s %-8s %-10s%n",
                "SKU", "Name", "Category", "Price", "Qty", "Value");
        System.out.println("-------------------------------------------------------------------------------------");

        for (Product p : list) {
            System.out.printf("%-10s %-20s %-15s ₹%-9.2f %-8d ₹%-10.2f%n",
                    p.getSku(), p.getName(), p.getCategory(), p.getPrice(), p.getQuantity(), p.getValue());
        }
    }

    public void searchProduct(String query) {
        boolean found = false;
        for (Product p : productSet) {
            if (p.getSku().equalsIgnoreCase(query) || p.getName().equalsIgnoreCase(query)) {
                System.out.println("Found: " + p);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No product found.");
        }
    }

    public void showLowStock() {
        System.out.println("\n=== LOW STOCK ALERTS ===");
        System.out.println("Items with stock less than " + LOW_STOCK_THRESHOLD + ":");

        int index = 1;
        for (Product p : lowStockQueue) {
            System.out.println(index++ + ". " + p.getSku() + " - " + p.getName() + " (Current Stock: " + p.getQuantity() + ")");
        }
        if (lowStockQueue.isEmpty()) {
            System.out.println("No low stock items.");
        }
    }

    public void showTransactions(int n) {
        System.out.println("\n=== LAST " + n + " TRANSACTIONS ===");
        int count = 0;
        for (Transaction t : transactionLog) {
            if (count++ == n) break;
            System.out.println(t);
        }
    }

    public void showStatistics() {
        System.out.println("\n=== INVENTORY STATISTICS ===");

        System.out.println("Total Products: " + productSet.size());

        double totalValue = 0;
        Map<String, Double> categoryValue = new HashMap<>();
        Map<String, Integer> categoryCount = new HashMap<>();

        for (Product p : productSet) {
            totalValue += p.getValue();
            categoryValue.put(p.getCategory(),
                    categoryValue.getOrDefault(p.getCategory(), 0.0) + p.getValue());
            categoryCount.put(p.getCategory(),
                    categoryCount.getOrDefault(p.getCategory(), 0) + 1);
        }

        System.out.printf("Total Inventory Value: ₹%.2f%n%n", totalValue);
        System.out.println("Category-wise Breakdown:");

        for (String cat : categoryValue.keySet()) {
            double val = categoryValue.get(cat);
            int cnt = categoryCount.get(cat);
            double percent = (val / totalValue) * 100;
            System.out.printf("• %s: %d products, Value: ₹%.2f (%.1f%%)%n", cat, cnt, val, percent);
        }
    }
}
