package com.szte.szakdolgozat.controller;

import com.szte.szakdolgozat.model.Tag;
import com.szte.szakdolgozat.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;
    private final ImageController imageController;

    private static List<String> tagIds = new ArrayList<>();
    private static Map<String, String> tagIdNameMap = new HashMap<>();

    private static void initTagIdNameMap() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Andras\\Desktop\\Egyetem\\Szakdolgozat\\BackEnd\\szakdolgozat\\src\\main\\resources\\tagger\\class-descriptions.csv"));
        String line = null;
        while ((line = br.readLine()) != null) {
            String str[] = line.split(",");
            tagIdNameMap.put(str[0], str[1]);
        }
    }

    private static void initTagIds() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Andras\\Desktop\\Egyetem\\Szakdolgozat\\BackEnd\\szakdolgozat\\src\\main\\resources\\tagger\\classes-trainable.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            tagIds.add(line);
        }
        br.close();
    }

    @PutMapping("/getAll")
    public List<Tag> getAllCategories(@RequestBody boolean emptyTags) throws IOException {
        initTagIdNameMap();
        initTagIds();
        List<Tag> tags = tagService.getAllTags();
        if (emptyTags) {
            tags.addAll(tagIdNameMap.values().stream().filter(tag -> !tags.stream().map(Tag::getName).toList().contains(tag)).map(Tag::new).toList());
        }
        return tags;
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
