package com.szte.szakdolgozat.service;

import com.szte.szakdolgozat.models.Image;
import com.szte.szakdolgozat.models.Thumbnail;
import com.szte.szakdolgozat.repository.ImageRepository;
import com.szte.szakdolgozat.repository.ThumbnailRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public List<Image> getAllImages(){
        return imageRepository.findAll();
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


}
