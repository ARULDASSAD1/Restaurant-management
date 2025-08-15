package com.example.demo.controller;

import com.example.demo.model.MenuItem;
import com.example.demo.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.InterruptedException;

@RestController
@RequestMapping("/api/menuitems")
public class MenuItemController {

    private final MenuItemService menuItemService;

    @Autowired
    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @PostMapping
    public ResponseEntity<MenuItem> createMenuItem(@RequestBody MenuItem menuItem) {
        MenuItem createdMenuItem = menuItemService.createMenuItem(menuItem);
        return new ResponseEntity<>(createdMenuItem, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable String id) {
        MenuItem menuItem = menuItemService.getMenuItemById(id);
        if (menuItem != null) {
            return new ResponseEntity<>(menuItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(params = "restaurantId")
    public ResponseEntity<List<MenuItem>> getMenuItemsByRestaurantId(@RequestParam String restaurantId) {
        List<MenuItem> menuItems = menuItemService.getMenuItemsByRestaurantId(restaurantId);
        return new ResponseEntity<>(menuItems, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable String id, @RequestBody MenuItem menuItem) {
        menuItem.setId(id); // Ensure the ID from the path is used
        MenuItem updatedMenuItem = menuItemService.updateMenuItem(menuItem);
        if (updatedMenuItem != null) {
            return new ResponseEntity<>(updatedMenuItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable String id) {
        boolean deleted = menuItemService.deleteMenuItem(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}