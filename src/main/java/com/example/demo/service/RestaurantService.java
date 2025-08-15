package com.example.demo.service;

import com.example.demo.model.Restaurant;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class RestaurantService {

    private final Firestore firestore;

    @Autowired
    public RestaurantService(Firestore firestore) {
        this.firestore = firestore;
    }

    private CollectionReference getRestaurantsCollection() {
        return firestore.collection("restaurants");
    }

    public String createRestaurant(Restaurant restaurant) {
        ApiFuture<DocumentReference> addedDocRef = getRestaurantsCollection().add(restaurant);
        try {
            return addedDocRef.get().getId();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null; // Or throw a custom exception
        }
    }

    public Restaurant getRestaurantById(String id) {
        DocumentReference docRef = getRestaurantsCollection().document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        try {
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                Restaurant restaurant = document.toObject(Restaurant.class);
                restaurant.setId(document.getId());
                return restaurant;
            } else {
                return null; // Restaurant not found
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null; // Or throw a custom exception
        }
    }

    public List<Restaurant> getAllRestaurants() {
        ApiFuture<QuerySnapshot> future = getRestaurantsCollection().get();
        List<Restaurant> restaurants = new ArrayList<>();
        try {
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                Restaurant restaurant = document.toObject(Restaurant.class);
                restaurant.setId(document.getId());
                restaurants.add(restaurant);
            }
            return restaurants;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Or throw a custom exception
        }
    }

    public boolean updateRestaurant(Restaurant restaurant) {
        if (restaurant.getId() == null || restaurant.getId().isEmpty()) {
            // Cannot update if no ID is provided
            return false;
        }
        DocumentReference docRef = getRestaurantsCollection().document(restaurant.getId());
        ApiFuture<WriteResult> future = docRef.set(restaurant); // Use set to overwrite or update
        try {
            future.get(); // Wait for the write to complete
            return true;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false; // Or throw a custom exception
        }
    }

    public boolean deleteRestaurant(String id) {
        DocumentReference docRef = getRestaurantsCollection().document(id);
        ApiFuture<WriteResult> future = docRef.delete();
        try {
            future.get(); // Wait for the delete to complete
            return true;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false; // Or throw a custom exception
        }
    }
}