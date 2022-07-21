package com.szte.szakdolgozat.controller;

import com.szte.szakdolgozat.models.Image;
import com.szte.szakdolgozat.service.ImageService;

import lombok.AllArgsConstructor;

import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;


import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import static com.szte.szakdolgozat.util.Constants.IMG_PATH;
import static com.szte.szakdolgozat.util.Constants.THUMBNAIL_PATH;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;

    @GetMapping("/getAll")
    public List<Image> getAllImages(){
        List<Image> images = imageService.getAllImages();
        images.forEach(image -> {
            byte[] fileContent;
            try {
                fileContent = FileUtils.readFileToByteArray(new File(IMG_PATH+image.getName()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            image.setImgB64(Base64.getEncoder().encodeToString(fileContent));
        });
        return images;
    }

    @GetMapping("/get")
    public Image getImageById(@RequestParam String id){
        Image image = imageService.getImageById(id).orElse(null);
        byte[] fileContent;
        try {
            assert image != null;
            fileContent = FileUtils.readFileToByteArray(new File(IMG_PATH+image.getName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        image.setImgB64(Base64.getEncoder().encodeToString(fileContent));
        return image;
    }
}
