package com.szte.szakdolgozat.repository;

import com.szte.szakdolgozat.models.Image;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ImageRepository extends MongoRepository<Image,String> {
    @Aggregation(pipeline = {
            "{ '$skip' : ?0 }",
            "{ '$limit' : ?1 }"
    })
    List<Image> findAll(int skip,int limit);
}
