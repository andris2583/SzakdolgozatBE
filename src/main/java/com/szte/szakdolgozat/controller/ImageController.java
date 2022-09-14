package com.szte.szakdolgozat.controller;

import com.szte.szakdolgozat.models.Image;
import com.szte.szakdolgozat.models.Thumbnail;
import com.szte.szakdolgozat.service.ImageService;

import com.szte.szakdolgozat.service.ThumbnailService;
import com.szte.szakdolgozat.util.ImageUtils;
import com.szte.szakdolgozat.util.ThumbnailGenerator;
import lombok.AllArgsConstructor;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.imageio.ImageIO;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
                fileContent = FileUtils.readFileToByteArray(new File(IMAGE_PATH +image.getNameWithExtension()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            image.setImgB64(Base64.getEncoder().encodeToString(fileContent));
        });
        return images;
    }

    @GetMapping("/get/{id}")
    public Image getImageById(@PathVariable String id){
        Image image = imageService.getImageById(id).orElse(null);
        byte[] fileContent;
        try {
            assert image != null;
            fileContent = FileUtils.readFileToByteArray(new File(IMAGE_PATH +image.getNameWithExtension()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        image.setImgB64(Base64.getEncoder().encodeToString(fileContent));
        return image;
    }

    @PutMapping("/insert")
    public Image insertImage(@RequestBody Image image){
        image.setExtension(FilenameUtils.getExtension(image.getName()));
        image.setName(FilenameUtils.getBaseName(image.getName()));
        byte[] data = Base64.getDecoder().decode(image.getImgB64().replaceFirst("data:image/png;base64,",""));
        try (OutputStream stream = new FileOutputStream(IMAGE_PATH + image.getNameWithExtension())) {
            stream.write(data);
        } catch (IOException e) {
            System.err.println(e);
        }
        Image inserted = imageService.insertImage(image);
        Thumbnail thumbnail = new Thumbnail();
        thumbnail.setName(inserted.getName());
        thumbnail.setExtension(inserted.getExtension());
        thumbnail.setImageID(inserted.getId());
        try (OutputStream stream = new FileOutputStream(THUMBNAIL_PATH + thumbnail.getNameWithExtension())) {
            stream.write(ImageUtils.toByteArray(
                    ThumbnailGenerator.generateThumbnail(thumbnail.getNameWithExtension(), 0.1f),thumbnail.getExtension()
            ));
        } catch (IOException e) {
            System.err.println(e);
        }
        thumbnailService.insertThumbnail(thumbnail);
        return image;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteImage(@PathVariable String id) {
        Image image = imageService.getImageById(id).orElse(null);
        assert image != null;
        try {
            File file = new File(IMAGE_PATH + image.getNameWithExtension());
            Files.deleteIfExists(file.toPath());
            file = new File(THUMBNAIL_PATH + image.getNameWithExtension());
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

    @GetMapping(value = "/getImageData/{id}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getImageData(@PathVariable String id) throws IOException {
        try {
            Image image = getImageById(id);
            Path imagePath = Paths.get(IMAGE_PATH+image.getNameWithExtension());

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

    @GetMapping(value = "/getImageDataNoZoom/{id}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getImageDataNoZoom(@PathVariable String id) throws IOException {
        try {
            Image image = getImageById(id);
            Path imagePath = Paths.get(IMAGE_PATH+image.getNameWithExtension());

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
