package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{firstName}")
    @ResponseBody
    public ResponseEntity<String> getUserName(@PathVariable String firstName) {
        User user = userService.getUserByFirstName(firstName);

        if (user != null) {
            String userFirstName = user.getFirstName();
            return ResponseEntity.ok(userFirstName);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}