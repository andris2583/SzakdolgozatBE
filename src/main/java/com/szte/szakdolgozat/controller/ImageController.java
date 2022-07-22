package com.szte.szakdolgozat.controller;

import com.szte.szakdolgozat.models.Image;
import com.szte.szakdolgozat.models.Thumbnail;
import com.szte.szakdolgozat.service.ImageService;

import com.szte.szakdolgozat.service.ThumbnailService;
import com.szte.szakdolgozat.util.ThumbnailGenerator;
import lombok.AllArgsConstructor;

import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;


import java.io.*;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.szte.szakdolgozat.util.Constants.IMAGE_PATH;
import static com.szte.szakdolgozat.util.Constants.THUMBNAIL_PATH;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;
    private final ThumbnailService thumbnailService;

    @GetMapping("/getAll")
    public List<Image> getAllImages(){
        List<Image> images = imageService.getAllImages();
        images.forEach(image -> {
            byte[] fileContent;
            try {
                fileContent = FileUtils.readFileToByteArray(new File(IMAGE_PATH +image.getName()));
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
            fileContent = FileUtils.readFileToByteArray(new File(IMAGE_PATH +image.getName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        image.setImgB64(Base64.getEncoder().encodeToString(fileContent));
        return image;
    }

    @PutMapping("/insert")
    public Image insertImage(@RequestBody Image image){
        //TODO extensions
        byte[] data = Base64.getDecoder().decode(image.getImgB64());
        try (OutputStream stream = new FileOutputStream(IMAGE_PATH +image.getName())) {
            stream.write(data);
        } catch (IOException e) {
            System.err.println(e);
        }
        Image inserted = imageService.insertImage(image);
        Thumbnail thumbnail = new Thumbnail();
        thumbnail.setName(inserted.getName());
        thumbnail.setImageID(inserted.getId());
        try {
            ThumbnailGenerator.generateThumbnail(thumbnail.getName(),0.1f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        thumbnailService.insertThumbnail(thumbnail);
        return image;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteImage(@PathVariable String id) {
        Image image = imageService.getImageById(id).orElse(null);
        assert image != null;
        try {
            File file = new File(IMAGE_PATH +image.getName());
            Files.deleteIfExists(file.toPath());
            file = new File(THUMBNAIL_PATH+image.getName());
            Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Thumbnail thumbnail = thumbnailService.getAllThumbnails().stream().filter(thumbnail1 ->
                Objects.equals(thumbnail1.getImageID(), image.getId())
        ).toList().get(0);
        thumbnailService.deleteThumbnail(thumbnail);
        imageService.deleteImage(image);
    }
}
