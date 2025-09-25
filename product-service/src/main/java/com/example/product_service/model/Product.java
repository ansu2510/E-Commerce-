package com.example.product_service.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String description;
    private double price;
    private int stock;

}
