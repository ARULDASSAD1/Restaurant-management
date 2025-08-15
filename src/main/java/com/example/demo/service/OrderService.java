package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.google.cloud.firestore.*;
import com.google.api.core.ApiFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class OrderService {

    private static final String COLLECTION_NAME = "orders";
    private static final String ORDER_ITEMS_SUBCOLLECTION = "orderItems";

    @Autowired
    private Firestore firestore;

    public Order createOrder(Order order) {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document();
        order.setId(docRef.getId());
        ApiFuture<WriteResult> result = docRef.set(order);
        try {
            result.get(); // Wait for the write to complete
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(); // Handle exception appropriately
            return null;
        }
        return order;
    }

    public Order getOrderById(String id) {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        try {
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                return document.toObject(Order.class);
            } else {
                return null; // Order not found
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(); // Handle exception appropriately
            return null;
        }
    }

    public List<Order> getOrdersByRestaurantId(String restaurantId) {
        CollectionReference orders = firestore.collection(COLLECTION_NAME);
        Query query = orders.whereEqualTo("restaurantId", restaurantId);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        List<Order> orderList = new ArrayList<>();
        try {
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                orderList.add(document.toObject(Order.class));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(); // Handle exception appropriately
            return new ArrayList<>();
        }
        return orderList;
    }

    public Order updateOrder(Order order) {
        if (order.getId() == null || order.getId().isEmpty()) {
            return null; // Cannot update without an ID
        }
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(order.getId());
        ApiFuture<WriteResult> result = docRef.set(order, SetOptions.merge()); // Use merge to update specific fields
        try {
            result.get(); // Wait for the write to complete
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(); // Handle exception appropriately
            return null;
        }
        return order;
    }

    public boolean deleteOrder(String id) {
        if (id == null || id.isEmpty()) {
            return false; // Cannot delete without an ID
        }
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(id);
        ApiFuture<WriteResult> result = docRef.delete();
        try {
            result.get(); // Wait for the delete to complete
            return true;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(); // Handle exception appropriately
            return false;
        }
    }

    public OrderItem addOrderItemToOrder(String orderId, OrderItem orderItem) {
        if (orderId == null || orderId.isEmpty()) {
            return null; // Cannot add to an order without an ID
        }
        CollectionReference orderItemsRef = firestore.collection(COLLECTION_NAME).document(orderId).collection(ORDER_ITEMS_SUBCOLLECTION);
        DocumentReference docRef = orderItemsRef.document();
        orderItem.setId(docRef.getId());
        ApiFuture<WriteResult> result = docRef.set(orderItem);
        try {
            result.get(); // Wait for the write to complete
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(); // Handle exception appropriately
            return null;
        }
        return orderItem;
    }

    public List<OrderItem> getOrderItemsByOrderId(String orderId) {
        if (orderId == null || orderId.isEmpty()) {
            return new ArrayList<>(); // Cannot get items for an order without an ID
        }
        CollectionReference orderItemsRef = firestore.collection(COLLECTION_NAME).document(orderId).collection(ORDER_ITEMS_SUBCOLLECTION);
        ApiFuture<QuerySnapshot> querySnapshot = orderItemsRef.get();
        List<OrderItem> orderItemList = new ArrayList<>();
        try {
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                orderItemList.add(document.toObject(OrderItem.class));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(); // Handle exception appropriately
            return new ArrayList<>();
        }
        return orderItemList;
    }
}