package com.szte.szakdolgozat.controller;

import com.szte.szakdolgozat.model.Collection;
import com.szte.szakdolgozat.model.CollectionType;
import com.szte.szakdolgozat.model.Privacy;
import com.szte.szakdolgozat.model.User;
import com.szte.szakdolgozat.service.CollectionService;
import com.szte.szakdolgozat.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
@RequestMapping("/auth")
public class UserController {

    UserService userService;
    CollectionService collectionService;

    @PutMapping("/login")
    public User login(@RequestBody User user) {
        List<User> users = userService.getAllUsers().stream().filter(tempUser -> tempUser.getUsername().equals(user.getUsername()) && tempUser.getPassword().equals(user.getPassword())).toList();
        return users.size() == 0 ? null : users.get(0);
    }

    @PutMapping("/register")
    public User register(@RequestBody User user) {
        List<User> users = userService.getAllUsers().stream().filter(tempUser -> tempUser.getUsername().equals(user.getUsername()) && tempUser.getPassword().equals(user.getPassword())).toList();
        if (users.size() == 0) {
            User insertedUser = this.userService.insertUser(user);
            this.collectionService.insertCollection(new Collection("Favourites", Collections.emptySet(), insertedUser.getId(), Privacy.PRIVATE, CollectionType.FAVOURITE));
            return insertedUser;
        } else {
            return null;
        }
    }

    @PutMapping("/get")
    public User getUser(@RequestBody String id) {
        return userService.getUserById(id).orElse(null);
    }
}

