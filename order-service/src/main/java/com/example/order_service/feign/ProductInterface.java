package com.example.order_service.feign;


import com.example.order_service.model.Order;
import com.example.order_service.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient("PRODUCT-SERVICE")
public interface ProductInterface {

    @GetMapping("product/getProduct/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable long id);

    @PutMapping("product/stockupdate")
    public ResponseEntity<?> stockupdate(@RequestBody List<Map<String,Integer>> updates);


}
