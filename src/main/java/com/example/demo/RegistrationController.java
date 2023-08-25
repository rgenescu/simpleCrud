package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        return "registration";
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        userService.createUser(user);
        String message = "User with email address " + user.getEmail() + " has been successfully registered.";
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/user/{id}")
    public ResponseEntity<User> getUserDetails(@PathVariable Long id) {
        User user = userService.getUserByID(id);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/users/user/{id}/update")
    public ResponseEntity<String> updateUserDetails(
            @PathVariable Long id,
            @RequestBody User updatedUser) {

        User existingUser = userService.getUserByID(id);
        String message;
        if (existingUser != null) {
            if (updatedUser.getFirstName() != null) {
                existingUser.setFirstName(updatedUser.getFirstName());
            }
            if (updatedUser.getLastName() != null) {
                existingUser.setLastName(updatedUser.getLastName());
            }
            if (updatedUser.getEmail() != null) {
                existingUser.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getPhoneNumber() != null) {
                existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
            }

            userService.updateUser(existingUser);

            message = "User with ID " + id + " has been successfully updated.";
            return ResponseEntity.ok(message);
        } else {
            message = "User with ID " + id + " was not found.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @DeleteMapping("/users/user/{id}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        String message;
        if (id != null) {
            message = "User with ID " + id + " has been successfully deleted.";
            return ResponseEntity.ok(message);
        } else {
            message = "User with ID " + id + " was not found.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }
}

