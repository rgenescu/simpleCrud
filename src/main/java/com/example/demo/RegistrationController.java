package com.example.demo;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation error(s): ");

            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorMessage.append(fieldError.getField())
                        .append(": ")
                        .append(fieldError.getDefaultMessage())
                        .append("; ");
            }

            JSONObject errorResponse = new JSONObject();
            errorResponse.put("error", true);
            errorResponse.put("message", errorMessage.toString());

            return ResponseEntity.badRequest().body(errorResponse.toString());
        }

        userService.createUser(user);
        String message = "User with email address " + user.getEmail() + " has been successfully registered.";

        JSONObject successResponse = new JSONObject();
        successResponse.put("error", false);
        successResponse.put("message", message);
        successResponse.put("userId", user.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse.toString());
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/user/{id}")
    public ResponseEntity<String> getUserDetails(@PathVariable Long id) {
        User user = userService.getUserByID(id);
        JSONObject response = new JSONObject();
        if (user != null) {
            response.put("userId", user.getId());
            response.put("firstName", user.getFirstName());
            response.put("lastName", user.getLastName());
            response.put("email", user.getEmail());
            response.put("mobile", user.getPhoneNumber());

            return ResponseEntity.status(HttpStatus.OK).body(response.toString());
        } else {
            response.put("error", "User with requested Id ws not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.toString());
        }
    }

    @PutMapping("/users/user/{id}/update")
    public ResponseEntity<String> updateUserDetails(
            @PathVariable Long id,
            @RequestBody User updatedUser) {

        User existingUser = userService.getUserByID(id);
        JSONObject response = new JSONObject();
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
            response.put("error", false);
            response.put("userId", id);

        } else {
            message = "User with ID " + id + " was not found.";
            response.put("error", true);
        }
        response.put("message", message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.toString());
    }

    @DeleteMapping("/users/user/{id}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        JSONObject response = new JSONObject();
        String message;
        if (id != null) {
            message = "User with ID " + id + " has been successfully deleted.";
            response.put("error", false);
            response.put("message", message);

            return ResponseEntity.status(HttpStatus.OK).body(response.toString());
        } else {
            message = "User with ID " + id + " was not found.";
            response.put("error", true);
            response.put("message", message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.toString());
        }
    }
}

