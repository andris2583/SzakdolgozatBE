package com.szte.szakdolgozat.controller;

import com.szte.szakdolgozat.models.Category;
import com.szte.szakdolgozat.models.Image;
import com.szte.szakdolgozat.service.CategoryService;
import com.szte.szakdolgozat.service.ImageService;
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
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/getAll")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @PutMapping("/insert")
    public Category insertCategory(@RequestParam Category category){
        return categoryService.insertCategory(category);
    }

    @DeleteMapping("/delete")
    public void deleteCategory(@RequestBody Category category){
        categoryService.deleteCategory(category);
    }
}
