package com.example.demo.model;

public class OrderItem {

    private String id; // Firestore document ID
    private String menuItemId; // Links to the MenuItem document
    private int quantity;
    private double price;

    public OrderItem() {
        // No-argument constructor required by Firebase
    }

    public OrderItem(String id, String menuItemId, int quantity, double price) {
        this.id = id;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(String menuItemId) {
        this.menuItemId = menuItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}