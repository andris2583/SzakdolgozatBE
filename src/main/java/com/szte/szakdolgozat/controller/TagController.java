package com.szte.szakdolgozat.controller;

import com.szte.szakdolgozat.model.Tag;
import com.szte.szakdolgozat.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

    @GetMapping("/getAll")
    public List<Tag> getAllCategories() {
        return tagService.getAllTags();
    }

    @PutMapping("/insert")
    public Tag insertTag(@RequestParam Tag tag) {
        return tagService.insertTag(tag);
    }

    @DeleteMapping("/delete")
    public void deleteTag(@RequestBody Tag tag) {
        tagService.deleteTag(tag);
    }
}
