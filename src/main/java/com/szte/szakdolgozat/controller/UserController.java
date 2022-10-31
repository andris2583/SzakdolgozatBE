package com.szte.szakdolgozat.controller;

import com.szte.szakdolgozat.models.User;
import com.szte.szakdolgozat.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
@RequestMapping("/auth")
public class UserController {

    UserService userService;

    @PutMapping("/login")
    public boolean login(@RequestBody User user) {
        List<User> users = userService.getAllUsers().stream().filter(tempUser -> tempUser.getUsername().equals(user.getUsername()) && tempUser.getPassword().equals(user.getPassword())).toList();
        return users.size() > 0;
    }

    @PutMapping("/register")
    public User register(@RequestBody User user) {
        List<User> users = userService.getAllUsers().stream().filter(tempUser -> tempUser.getUsername().equals(user.getUsername()) && tempUser.getPassword().equals(user.getPassword())).toList();
        return users.size() == 0 ? userService.insertUser(user) : null;
    }

    @PutMapping("/get")
    public User getUser(@RequestBody String id) {
        return userService.getUserById(id).orElse(null);
    }
}

