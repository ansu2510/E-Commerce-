package com.example.user_service.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {



    @Id
    @GeneratedValue
    private Long id;

    private String name;


    @Column(unique = true)
    private String email;
    private String password;
    private String role;

}
