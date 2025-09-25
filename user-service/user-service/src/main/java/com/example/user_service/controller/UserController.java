package com.example.user_service.controller;

import com.example.user_service.model.User;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private JwtUtil jwtUtil;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user){
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String , String> userMap){
        User user = userRepository.findByEmail(userMap.get("email"))
        .orElseThrow(() -> new RuntimeException("User not found!"));

        if(!encoder.matches(userMap.get("password") , user.getPassword())){
            return ResponseEntity.badRequest().body("Invalid Credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail() , user.getRole());
        System.out.println("here is the token" + token);
        return ResponseEntity.ok(Map.of("token" , token , "role" , user.getRole()));

    }
}
