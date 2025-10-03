package com.example.product_service.controller;


import com.example.product_service.model.Product;
import com.example.product_service.repository.ProductRepo;
import com.example.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/product")
public class ProductController {


    @Autowired
    private ProductService productService;


    @GetMapping("getProduct/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable long id){
        Product product = productService.getById(id);
        return ResponseEntity.ok(product);
    }


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
    public ResponseEntity<?> updateProduct(@PathVariable("id") long id , @RequestBody Map<String, Integer> request) {

        int quantity = request.get("quantity");

        if (!productService.existsById(id)) {
            return ResponseEntity.status(404)
                    .body(Map.of("error", "Product not found with id " + id));
        }

        Product product = productService.getById(id);

        if (product.getStock() <= 0) {
            return ResponseEntity.status(400).body("Sorry Product out of stock");
        }

        product.setStock(product.getStock() - quantity);

        Product updatedProduct = productService.saveProduct(product);

        return ResponseEntity.ok(updatedProduct);
    }

















}
