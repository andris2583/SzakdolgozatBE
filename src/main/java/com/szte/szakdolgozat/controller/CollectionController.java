package com.szte.szakdolgozat.controller;

import com.szte.szakdolgozat.models.Collection;
import com.szte.szakdolgozat.models.CollectionType;
import com.szte.szakdolgozat.service.CollectionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
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
        return collections.stream().filter(collection -> Objects.equals(collection.getUserId(), id)).toList();
    }

    @GetMapping("/saveToFavourites/{userId}/{imageId}")
    public Collection saveToFavourites(@PathVariable String userId, @PathVariable String imageId) {
        try {
            List<Collection> collections = getCollectionsByUserId(userId);
            Collection favouriteCollection = collections.stream().filter(collection -> collection.getType().equals(CollectionType.FAVOURITE)).toList().get(0);
            if (favouriteCollection.getImageIds().contains(imageId)) {
                favouriteCollection.getImageIds().remove(imageId);
            } else {
                favouriteCollection.getImageIds().add(imageId);
            }
            this.collectionService.saveCollection(favouriteCollection);
            return favouriteCollection;
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/saveToCollection/{userId}/{collectionId}/{imageId}")
    public Collection saveToCollection(@PathVariable String userId, @PathVariable String collectionId, @PathVariable String imageId) {
        try {
            List<Collection> collections = getCollectionsByUserId(userId);
            Collection selectedCollection = collections.stream().filter(collection -> collection.getId().equals(collectionId)).toList().get(0);
            if (selectedCollection.getImageIds().contains(imageId)) {
                selectedCollection.getImageIds().remove(imageId);
            } else {
                selectedCollection.getImageIds().add(imageId);
            }
            this.collectionService.saveCollection(selectedCollection);
            return selectedCollection;
        } catch (Exception e) {
            return null;
        }
    }
}
