package com.example.product_service.controller;


import com.example.product_service.model.Product;
import com.example.product_service.repository.ProductRepo;
import com.example.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {


    @Autowired
    private ProductService productService;




    @GetMapping("/getAll")
    public ResponseEntity<List<Product>> getAll() {
        List<Product> products = productService.getAllProduct();
        return ResponseEntity.ok(products); // wraps the list inside ResponseEntity with HTTP 200
    }


    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@RequestBody List<Product> product){
        productService.saveProduct(product);
        return ResponseEntity.ok("product added successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") long id) {
        if (!productService.existsById(id)) {
            return ResponseEntity.status(404).body("Product not found with id " + id);
        }
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @PutMapping("/productID/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") long id) {
        if (!productService.existsById(id)) {
            return ResponseEntity.status(404).body("Product not found with id " + id);
        }

        Product product = productService.getById(id);

        if(product.getStock()<=0){
            return ResponseEntity.status(400).body("Sorry Product out of stock");
        }

        product.setStock(product.getStock()-1);

        productService.saveProduct(product);

        return ResponseEntity.ok("Product updated successsfully");


    }














}
