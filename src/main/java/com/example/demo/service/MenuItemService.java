package com.example.demo.service;

import com.example.demo.model.MenuItem;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class MenuItemService {

    private final Firestore firestore;

    @Autowired
    public MenuItemService(Firestore firestore) {
        this.firestore = firestore;
    }

    public MenuItem createMenuItem(MenuItem menuItem) {
        try {
            CollectionReference menuItemsCollection = firestore.collection("menuItems");
            ApiFuture<DocumentReference> addedDocRef = menuItemsCollection.add(menuItem);
            menuItem.setId(addedDocRef.get().getId());
            return menuItem;
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public MenuItem getMenuItemById(String id) {
        try {
            DocumentReference docRef = firestore.collection("menuItems").document(id);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                MenuItem menuItem = document.toObject(MenuItem.class);
                menuItem.setId(document.getId());
                return menuItem;
            } else {
                return null;
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<MenuItem> getMenuItemsByRestaurantId(String restaurantId) {
        try {
            CollectionReference menuItemsCollection = firestore.collection("menuItems");
            Query query = menuItemsCollection.whereEqualTo("restaurantId", restaurantId);
            ApiFuture<QuerySnapshot> querySnapshot = query.get();
            List<MenuItem> menuItems = new ArrayList<>();
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                MenuItem menuItem = document.toObject(MenuItem.class);
                menuItem.setId(document.getId());
                menuItems.add(menuItem);
            }
            return menuItems;
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public MenuItem updateMenuItem(MenuItem menuItem) {
        try {
            if (menuItem.getId() == null || menuItem.getId().isEmpty()) {
                throw new IllegalArgumentException("Menu Item ID must not be null or empty for update.");
            }
            DocumentReference docRef = firestore.collection("menuItems").document(menuItem.getId());
            ApiFuture<WriteResult> writeResult = docRef.set(menuItem);
            writeResult.get(); // Wait for the write to complete
            return menuItem;
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteMenuItem(String id) {
        try {
            DocumentReference docRef = firestore.collection("menuItems").document(id);
            ApiFuture<WriteResult> writeResult = docRef.delete();
            writeResult.get(); // Wait for the delete to complete
            return true; // Assuming successful deletion if no exception is thrown
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}