package com.szte.szakdolgozat.service;

import com.szte.szakdolgozat.models.BatchImageRequest;
import com.szte.szakdolgozat.models.Image;
import com.szte.szakdolgozat.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;
    public List<Image> getAllImages(int skip,int limit){
        return imageRepository.findAll(skip,limit);
    }
    public Optional<Image> getImageById(String id){
        return imageRepository.findById(id);
    }

    public Image insertImage(Image image){
        return imageRepository.insert(image);
    }

    public void deleteImage(Image image){
        imageRepository.delete(image);
    }

    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }
}
