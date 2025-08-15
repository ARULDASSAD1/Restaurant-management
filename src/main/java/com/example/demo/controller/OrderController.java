package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable String id) {
        Order order = orderService.getOrderById(id);
        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(params = "restaurantId")
    public ResponseEntity<List<Order>> getOrdersByRestaurantId(@RequestParam String restaurantId) {
        List<Order> orders = orderService.getOrdersByRestaurantId(restaurantId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateOrder(@PathVariable String id, @RequestBody Order order) {
        order.setId(id); // Ensure the ID from the path is used
        orderService.updateOrder(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{orderId}/items")
    public ResponseEntity<Void> addOrderItemToOrder(@PathVariable String orderId, @RequestBody OrderItem orderItem) {
        orderService.addOrderItemToOrder(orderId, orderItem);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}/items")
    public ResponseEntity<List<OrderItem>> getOrderItemsByOrderId(@PathVariable String orderId) {
        List<OrderItem> orderItems = orderService.getOrderItemsByOrderId(orderId);
        return new ResponseEntity<>(orderItems, HttpStatus.OK);
    }
}