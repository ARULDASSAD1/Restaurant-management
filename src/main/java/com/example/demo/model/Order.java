package com.example.demo.model;

import java.util.Date;


public class Order {

    private String id; // Firestore document ID for the order
    private String restaurantId; // ID of the restaurant the order belongs to
    private Date orderTime; // Timestamp when the order was placed
    private String status; // Status of the order (e.g., 'PENDING', 'PREPARING', 'DELIVERED')
    private double totalPrice; // Total price of the order

    public Order() {
    }

    public Order(String id, String restaurantId, Date orderTime, String status, double totalPrice) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.orderTime = orderTime;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}