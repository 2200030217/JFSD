package com.example.SdpProject.controller;
import com.example.SdpProject.model.User;
import com.example.SdpProject.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

	
	@RestController
	@RequestMapping("/api/auth")
	@CrossOrigin(origins = "http://localhost:3000") // Allow requests from React app
	public class UserController {

	    @Autowired
	    private UserRepository userRepository;

	    @PostMapping("/signup")
	    public ResponseEntity<?> signup(@RequestBody User user) {
	        if (userRepository.existsByUsername(user.getUsername())) {
	            return ResponseEntity.badRequest().body("Username is already taken");
	        }
	        if (userRepository.existsByEmail(user.getEmail())) {
	            return ResponseEntity.badRequest().body("Email is already registered");
	        }
	        userRepository.save(user);
	        return ResponseEntity.ok("User registered successfully");
	    }
	
	
	

	    @PostMapping("/login")
	    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
	        String email = loginRequest.getEmail();
	        String password = loginRequest.getPassword();

	        // Find the user by email
	        Optional<User> optionalUser = userRepository.findByEmail(email);

	        if (optionalUser.isPresent()) {
	            User user = optionalUser.get();

	            // Directly compare the provided password with the stored password
	            if (password.equals(user.getPassword())) {
	                // Create a response map (you can include a token here if needed)
	                Map<String, String> response = new HashMap<>();
	                response.put("message", "Login successful");
	                response.put("email", user.getEmail());
	                return ResponseEntity.ok(response);
	            } else {
	                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                        .body(Map.of("message", "Invalid credentials"));
	            }
	        } else {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                    .body(Map.of("message", "Invalid credentials"));
	        }
	    }
	}





