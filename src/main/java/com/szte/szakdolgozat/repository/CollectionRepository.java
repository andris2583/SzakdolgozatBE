package com.szte.szakdolgozat.repository;

import com.szte.szakdolgozat.model.Collection;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CollectionRepository extends MongoRepository<Collection, String> {

    List<Collection> findAll();
}
