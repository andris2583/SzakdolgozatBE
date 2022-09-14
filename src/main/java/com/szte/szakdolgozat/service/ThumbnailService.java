package com.szte.szakdolgozat.service;

import com.szte.szakdolgozat.models.Thumbnail;
import com.szte.szakdolgozat.repository.ThumbnailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ThumbnailService {

    private final ThumbnailRepository thumbnailRepository;


    public List<Thumbnail> getAllThumbnails(){
        return thumbnailRepository.findAll();
    }

    public Thumbnail insertThumbnail(Thumbnail thumbnail){
        return thumbnailRepository.insert(thumbnail);
    }

    public void deleteThumbnail(Thumbnail thumbnail){
        thumbnailRepository.delete(thumbnail);
    }

    public Thumbnail getThumbnailByImageId(String id) {
        return thumbnailRepository.findAll().stream().filter(thumbnail -> Objects.equals(thumbnail.getImageID(), id)
        ).findFirst().orElse(null);
    }
}
