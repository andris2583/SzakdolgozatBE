package com.szte.szakdolgozat.controller;

import com.szte.szakdolgozat.models.BatchImageRequest;
import com.szte.szakdolgozat.models.Image;
import com.szte.szakdolgozat.models.Tag;
import com.szte.szakdolgozat.service.ImageService;

import com.szte.szakdolgozat.service.TagService;
import com.szte.szakdolgozat.util.ImageTagger;
import lombok.AllArgsConstructor;

import net.coobird.thumbnailator.Thumbnailator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tensorflow.op.math.Imag;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static com.szte.szakdolgozat.util.Constants.IMAGE_PATH;
import static com.szte.szakdolgozat.util.Constants.THUMBNAIL_PATH;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;

    private final TagService tagService;
    private final ImageTagger imageTagger;

    @PutMapping("/getAll")
    public List<Image> getAllImages(@RequestBody BatchImageRequest request){
        List<Image> images = imageService.getAllImages();
        if (!Objects.equals(request.getTag(), "all")){
            images = images.stream().filter(image -> image.getTags().contains(request.getTag())).collect(Collectors.toList());
        }
        int from = Math.min(request.getPageCount()*request.getBatchSize(),images.size());
        int to = Math.min(request.getPageCount()*request.getBatchSize()+ request.getBatchSize(),images.size());
        images = images.subList(from,to);
        images.forEach(image -> {
            byte[] fileContent;
            try {
                fileContent = FileUtils.readFileToByteArray(new File(THUMBNAIL_PATH + image.getThumbnailName()));
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
        return image;
    }

    @PutMapping("/insert")
    public Image insertImage(@RequestBody Image image){
        image.setExtension(FilenameUtils.getExtension(image.getName()));
        image.setName(FilenameUtils.getBaseName(image.getName()));
        byte[] data = Base64.getDecoder().decode(image.getImgB64().replaceFirst("data:image/png;base64,",""));
        image.setImgB64(null);
        Image insertedImage = imageService.insertImage(image);
        try (OutputStream stream = new FileOutputStream(IMAGE_PATH + insertedImage.getIdWithExtension())) {
            stream.write(data);
        } catch (IOException e) {
            System.err.println(e);
        }
        try {
            BufferedImage thumbnailImage = Thumbnailator.createThumbnail(new File(IMAGE_PATH + insertedImage.getIdWithExtension()),640,480);
            ImageIO.write(thumbnailImage, "png", new File(THUMBNAIL_PATH + insertedImage.getId()+".png"));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(thumbnailImage, "png", byteArrayOutputStream);
            byte[] fileContent = byteArrayOutputStream.toByteArray();
            insertedImage.setImgB64(Base64.getEncoder().encodeToString(fileContent));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<String> newTags = new ArrayList<>(insertedImage.getTags());
        newTags.removeAll(tagService.getAllTags().stream().map(Tag::getName).toList());
        if (newTags.size() !=0){
            for(String tagName : newTags){
                Tag tag = new Tag();
                tag.setName(tagName);
                tagService.insertTag(tag);
            }
        }
        return insertedImage;
    }

    @PutMapping("/update")
    public Image updateImage(@RequestBody Image image){
        return imageService.saveImage(image);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteImage(@PathVariable String id) {
        Image image = imageService.getImageById(id).orElse(null);
        assert image != null;
        try {
            File file = new File(IMAGE_PATH + image.getIdWithExtension());
            Files.deleteIfExists(file.toPath());
            file = new File(THUMBNAIL_PATH + image.getId()+".png");
            Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        imageService.deleteImage(image);
    }

    @GetMapping(value = "/getImageData/{id}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getImageData(@PathVariable String id) throws IOException {
        try {
            Image image = getImageById(id);
            Path imagePath = Paths.get(IMAGE_PATH+image.getIdWithExtension());

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

    @PutMapping("/getTags")
    public List<String> getTags(@RequestBody String imageB64){
        byte[] data = Base64.getDecoder().decode(imageB64.replaceFirst("data:image/png;base64,",""));
        return imageTagger.generateTags(data);
    }

    @PutMapping("/getSimilarImages")
    public List<Image> getSimilarImages(@RequestBody List<String> tags){
        List<Image> images = imageService.getAllImages();
        images = images.stream().filter(image -> image.getTags().stream().distinct().filter(tags::contains).collect(Collectors.toSet()).size() > 1 ).toList();
        images.forEach(image -> {
            byte[] fileContent;
            try {
                fileContent = FileUtils.readFileToByteArray(new File(THUMBNAIL_PATH + image.getThumbnailName()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            image.setImgB64(Base64.getEncoder().encodeToString(fileContent));
        });
        return images;
    }

}
