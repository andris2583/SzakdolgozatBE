package com.szte.szakdolgozat.repository;

import com.szte.szakdolgozat.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ImageRepository extends MongoRepository<Image, String> {

    List<Image> findAll();

}
