package com.szte.szakdolgozat.repository;

import com.szte.szakdolgozat.models.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageRepository extends MongoRepository<Image,String> {
}
