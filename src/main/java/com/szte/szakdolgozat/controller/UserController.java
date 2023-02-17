package com.szte.szakdolgozat.controller;

import com.szte.szakdolgozat.model.Collection;
import com.szte.szakdolgozat.model.CollectionType;
import com.szte.szakdolgozat.model.Privacy;
import com.szte.szakdolgozat.model.User;
import com.szte.szakdolgozat.service.CollectionService;
import com.szte.szakdolgozat.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import static com.szte.szakdolgozat.util.Constants.PROFILE_PATH;

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

    @PutMapping("/uploadProfilePicture")
    public boolean insertImage(@RequestBody String[] array) {
        String base64 = array[0].replaceFirst("data:image/.*;base64,", "");
        byte[] data = Base64.getDecoder().decode(base64);
        try (OutputStream stream = new FileOutputStream(PROFILE_PATH + array[1] + ".png")) {
            stream.write(data);
        } catch (IOException e) {
            System.err.println(e);
        }
        return true;
    }

    @GetMapping(value = "/getProfileData/{id}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getProfileData(@PathVariable String id) throws IOException {
        try {
            Path imagePath = Paths.get(PROFILE_PATH + id + ".png");
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(imagePath));
            return ResponseEntity
                    .ok()
                    .contentLength(imagePath.toFile().length())
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}

