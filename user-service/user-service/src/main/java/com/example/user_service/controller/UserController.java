package com.example.user_service.controller;

import com.example.user_service.model.User;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private JwtUtil jwtUtil;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @GetMapping("/test")
    public String test() {
        return "UserController is working!";
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        try {
            user.setPassword(encoder.encode(user.getPassword()));

            if (user.getRole() == null) {
                user.setRole("CUSTOMER"); // default role
            }

            User savedUser = userRepository.save(user);

            String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Registration successful");
            response.put("token", token);
            response.put("role", user.getRole());
            response.put("userId", savedUser.getId());

            System.out.println("this is the response" + response);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("success", String.valueOf(false));
            errorResponse.put("message", "There is an error during signup: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
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

        return ResponseEntity.ok(Map.of("token" , token ,"userId" , user.getId()));

    }

    @GetMapping("/getallusers")
    public ResponseEntity<?> getAll(){
        System.out.println("hello world");
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
}
