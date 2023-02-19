package com.szte.szakdolgozat.service;

import com.szte.szakdolgozat.model.ImageViewMap;
import com.szte.szakdolgozat.repository.ImageViewMapRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ImageViewMapService {

    private final ImageViewMapRepository imageViewMapRepository;

    public List<ImageViewMap> getAllImageViewMaps() {
        return imageViewMapRepository.findAll();
    }

    public Optional<ImageViewMap> getImageViewMapById(String id) {
        return imageViewMapRepository.findById(id);
    }

    public ImageViewMap insertImageViewMap(ImageViewMap image) {
        return imageViewMapRepository.insert(image);
    }

    public void deleteImageViewMap(ImageViewMap image) {
        imageViewMapRepository.delete(image);
    }

    public ImageViewMap saveImageViewMap(ImageViewMap image) {
        return imageViewMapRepository.save(image);
    }

}
