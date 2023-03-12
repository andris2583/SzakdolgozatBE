package com.szte.szakdolgozat.service;

import com.szte.szakdolgozat.model.Collection;
import com.szte.szakdolgozat.repository.CollectionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CollectionService {

    private final CollectionRepository collectionRepository;

    public List<Collection> getAllCollections() {
        return collectionRepository.findAll();
    }

    public Optional<Collection> getCollectionById(String id) {
        return collectionRepository.findById(id);
    }

    public Collection insertCollection(Collection collection) {
        return collectionRepository.insert(collection);
    }

    public void deleteCollection(String id) {
        collectionRepository.deleteById(id);
    }

    public Collection saveCollection(Collection collection) {
        return collectionRepository.save(collection);
    }
}