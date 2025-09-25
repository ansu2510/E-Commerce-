package com.example.order_service.controller;


import com.example.order_service.service.OrderService;
import com.example.order_service.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {



    @Autowired
    private OrderService orderService;

    @GetMapping("/test")
    public String test() {
        return "OrderController is working!";
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody Order order){
        Order savedOrder = orderService.createOrder(order);
        return ResponseEntity.ok(savedOrder);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable Long userId){
        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }



}
