package com.szte.szakdolgozat.repository;

import com.szte.szakdolgozat.models.Collection;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CollectionRepository extends MongoRepository<Collection, String> {

    List<Collection> findAll();
}
