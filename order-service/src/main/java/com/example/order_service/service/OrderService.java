package com.example.order_service.service;

import com.example.order_service.feign.ProductInterface;
import com.example.order_service.model.OrderItem;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductInterface productInterface;

    //    private OrderRepository orderRepository;
    public Order createOrder(Order order){
        for (OrderItem item : order.getItems()) {
            long productId = item.getProductId();
            int quantity = item.getQuantity();

            Map<String , Integer>request = Map.of("quantity" , quantity);

            productInterface.updateProduct(productId , request);
        }
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUser(Long userId){
        return orderRepository.findByUserId(userId);
    }
}
