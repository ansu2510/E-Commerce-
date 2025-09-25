package com.example.product_service.service;

import com.example.product_service.model.Product;
import com.example.product_service.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;



    public List<Product> saveProduct(List<Product> product){
        return productRepo.saveAll(product);
    }

    public Product saveProduct(Product product){
        return productRepo.save(product);
    }


    public List<Product> getAllProduct(){
        return productRepo.findAll();
    }

    public Product getById(long id){
        return productRepo.getById(id);
    }

    public void deleteProduct(Long id) {
        if (!productRepo.existsById(id)) {
            throw new RuntimeException("Product not found with id " + id);
        }
        productRepo.deleteById(id);
    }

    public boolean existsById(Long id){
        return productRepo.existsById(id);
    }



}
