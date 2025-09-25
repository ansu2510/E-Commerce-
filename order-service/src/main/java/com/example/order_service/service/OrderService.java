package com.example.order_service.service;

import com.example.order_service.repository.OrderRepository;
import com.example.order_service.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

//    private OrderRepository orderRepository;
    public Order createOrder(Order order){
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUser(Long userId){
        return orderRepository.findByUserId(userId);
    }
}
