package com.example.demo.controller;

import com.example.demo.model.Restaurant;
import com.example.demo.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        String restaurantId = restaurantService.createRestaurant(restaurant);
        // Assuming the service sets the ID on the passed restaurant object
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable String id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        if (restaurant != null) {
            return new ResponseEntity<>(restaurant, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRestaurant(@PathVariable String id, @RequestBody Restaurant restaurant) {
        // Ensure the ID in the path matches the ID in the request body (optional but good practice)
        if (restaurant.getId() != null && !restaurant.getId().equals(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        restaurant.setId(id); // Set the ID from the path

        boolean updated = restaurantService.updateRestaurant(restaurant);
        if (updated) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable String id) {
        boolean deleted = restaurantService.deleteRestaurant(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}