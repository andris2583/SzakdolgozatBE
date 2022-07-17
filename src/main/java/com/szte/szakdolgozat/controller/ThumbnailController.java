package com.szte.szakdolgozat.controller;

import com.szte.szakdolgozat.models.Thumbnail;
import com.szte.szakdolgozat.service.ThumbnailService;
import com.szte.szakdolgozat.util.Constants;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.szte.szakdolgozat.util.Constants.THUMBNAIL_PATH;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
@RequestMapping("/thumbnail")
public class ThumbnailController {

    private final ThumbnailService thumbnailService;

    @GetMapping("/getAll")
    public List<Thumbnail> getAllThumbnails(){
        List<Thumbnail> thumbnails = thumbnailService.getAllThumbnails();
        thumbnails.forEach(thumbnail -> {
            byte[] fileContent;
            try {
                fileContent = FileUtils.readFileToByteArray(new File(THUMBNAIL_PATH+thumbnail.getName()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            thumbnail.setImgB64(Base64.getEncoder().encodeToString(fileContent));
        });
        return thumbnails;
    }

//
//    @GetMapping("/getAll")
//    public ResponseEntity<List<String>> getAll(){
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.set("Access-Control-Allow-Origin","http://localhost:4200");
//        List<String> imgStrings = urls.stream().map(url -> {
//            byte[] fileContent;
//            try {
//                fileContent = FileUtils.readFileToByteArray(new File(url));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            return Base64.getEncoder().encodeToString(fileContent);
//        }).toList();
//        return ResponseEntity.ok().headers(responseHeaders).body(imgStrings);
//    }
}
