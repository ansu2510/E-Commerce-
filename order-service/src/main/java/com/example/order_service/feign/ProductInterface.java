package com.example.order_service.feign;


import com.example.order_service.model.Order;
import com.example.order_service.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("PRODUCT-SERVICE")
public interface ProductInterface {
    public ResponseEntity<Product> getProductById(@PathVariable long id);

}
