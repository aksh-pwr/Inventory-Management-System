package com.internship.inventory.comparators;

import com.internship.inventory.model.Product;
import java.util.Comparator;

public class ValueComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return Double.compare(p2.getValue(), p1.getValue());
    }
}
