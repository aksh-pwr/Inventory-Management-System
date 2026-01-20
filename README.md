# Inventory Management System

## 🚀 Project Overview
This project is a **console-based Inventory Management System** built using advanced Java Collections and Data Structures.  
It demonstrates practical use of:

- **HashSet** for unique product management
- **TreeSet** for sorted product display
- **LinkedList** for transaction history
- **Stack** for undo operations
- **Queue** for low stock alerts
- **Comparable & Comparator** for sorting
- **Generic programming** for reusable utility methods

---

## 🎯 Features

### ✅ Product Management
- Add new products with unique SKU
- Update product quantity
- Prevent duplicate products using HashSet

### ✅ Sorting & Display
- View products sorted by:
  - SKU (natural order)
  - Price
  - Inventory value (price × quantity)
  - Name

### ✅ Transaction & Undo
- Record every operation in transaction log
- Undo last quantity update using Stack

### ✅ Low Stock Alerts
- Automatically generate alerts for products with stock < 10
- Uses Queue to manage alert list

### ✅ Inventory Statistics
- Total number of products
- Total inventory value
- Category-wise breakdown with percentage