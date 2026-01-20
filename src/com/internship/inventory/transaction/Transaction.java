package com.internship.inventory.transaction;

import java.util.Date;

public class Transaction {

    private String type;
    private String sku;
    private String details;
    private Date timestamp;

    public Transaction(String type, String sku, String details) {
        this.type = type;
        this.sku = sku;
        this.details = details;
        this.timestamp = new Date();
    }

    @Override
    public String toString() {
        return type + ": " + sku + " - " + details + " at " + timestamp;
    }
}
