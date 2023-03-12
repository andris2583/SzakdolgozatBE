package com.szte.szakdolgozat.controller;

import com.szte.szakdolgozat.model.Collection;
import com.szte.szakdolgozat.service.CollectionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/collection")
public class CollectionController {
    private final CollectionService collectionService;

    @GetMapping("/getAll")
    public List<Collection> getAllCollections() {
        return this.collectionService.getAllCollections();
    }

    @GetMapping("/getCollectionsByUserId/{id}")
    public List<Collection> getCollectionsByUserId(@PathVariable String id) {
        List<Collection> collections = this.collectionService.getAllCollections();
        return collections.stream().filter(collection -> Objects.equals(collection.getUserId(), id)).collect(Collectors.toList());
    }

    @GetMapping("/getCollectionsById/{id}")
    public Collection getCollectionsById(@PathVariable String id) {
        List<Collection> collections = this.collectionService.getAllCollections();
        return collections.stream().filter(collection -> Objects.equals(collection.getId(), id)).toList().get(0);
    }

    @PutMapping("/saveCollection")
    public Collection saveCollection(@RequestBody Collection collectionToSave) {
        return collectionService.saveCollection(collectionToSave);
    }

    @PutMapping("/insert")
    public Collection insertCollection(@RequestBody Collection collection) {
        return collectionService.insertCollection(collection);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCollection(@PathVariable String id) {
        collectionService.deleteCollection(id);
    }

}
