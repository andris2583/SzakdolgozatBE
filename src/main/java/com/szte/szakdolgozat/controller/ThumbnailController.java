package com.szte.szakdolgozat.controller;

import com.szte.szakdolgozat.models.Image;
import com.szte.szakdolgozat.models.Thumbnail;
import com.szte.szakdolgozat.service.ImageService;
import com.szte.szakdolgozat.service.ThumbnailService;
import com.szte.szakdolgozat.util.Constants;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.szte.szakdolgozat.util.Constants.IMAGE_PATH;
import static com.szte.szakdolgozat.util.Constants.THUMBNAIL_PATH;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
@RequestMapping("/thumbnail")
public class ThumbnailController {

    private final ThumbnailService thumbnailService;

    private final ImageService imageService;

    @GetMapping("/getAll")
    public List<Thumbnail> getAllThumbnails(){
        List<Thumbnail> thumbnails = thumbnailService.getAllThumbnails();
        thumbnails.forEach(thumbnail -> {
            byte[] fileContent;
            try {
                fileContent = FileUtils.readFileToByteArray(new File(THUMBNAIL_PATH+thumbnail.getNameWithExtension()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            thumbnail.setImgB64(Base64.getEncoder().encodeToString(fileContent));
        });
        return thumbnails;
    }

    @PutMapping("/insert")
    public Thumbnail insertThumbnail(@RequestParam Thumbnail thumbnail){
        return thumbnailService.insertThumbnail(thumbnail);
    }

    @DeleteMapping("/delete")
    public void deleteThumbnail(@RequestBody Thumbnail thumbnail){
        thumbnailService.deleteThumbnail(thumbnail);
    }

    @GetMapping("/getThumbnailImageByImageId/{id}")
    public ResponseEntity<?> getThumbnailByImageId(@PathVariable String id){
        try {
            Thumbnail thumbnail = thumbnailService.getThumbnailByImageId(id);
            Path imagePath = Paths.get(THUMBNAIL_PATH+thumbnail.getNameWithExtension());

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
