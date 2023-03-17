package com.szte.szakdolgozat.controller;

import com.szte.szakdolgozat.model.auth.User;
import com.szte.szakdolgozat.model.auth.UserRole;
import com.szte.szakdolgozat.repository.RoleRepository;
import com.szte.szakdolgozat.service.CollectionService;
import com.szte.szakdolgozat.service.UserService;
import com.szte.szakdolgozat.util.ImageUtils;
import lombok.AllArgsConstructor;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

import static com.szte.szakdolgozat.util.Constants.PROFILE_PATH;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/user")
public class UserController {

    UserService userService;
    CollectionService collectionService;
    RoleRepository roleRepository;

    @PutMapping("/get")
    public User getUser(@RequestBody String id) {
        return userService.getUserById(id).orElse(null);
    }

    @GetMapping("/getAll")
    public List<User> getAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/getAllRoles")
    public List<UserRole> getAllRoles() {
        return this.roleRepository.findAll();
    }

    @PutMapping("/uploadProfilePicture")
    public boolean uploadProfilePicture(@RequestBody String[] array) throws IOException {
        String base64 = array[0].replaceFirst("data:image/.*;base64,", "");
        byte[] data = Base64.getDecoder().decode(base64);
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
        BufferedImage profileImage = Thumbnailator.createThumbnail(img, 640, 360);
        data = ImageUtils.toByteArray(profileImage, "png");
        try (OutputStream stream = new FileOutputStream(PROFILE_PATH + array[1] + ".png")) {
            stream.write(data);
        } catch (IOException e) {
            System.err.println(e);
        }
        return true;
    }

    @PutMapping("/update")
    public boolean updateUser(@RequestBody User user) {
        User updatedUser = this.userService.saveUser(user);
        return updatedUser != null;
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
            Path imagePath = Paths.get(PROFILE_PATH + "profile-alt.png");
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(imagePath));
            return ResponseEntity
                    .ok()
                    .contentLength(imagePath.toFile().length())
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        }
    }


}

