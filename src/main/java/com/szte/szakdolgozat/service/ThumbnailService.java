package com.szte.szakdolgozat.service;

import com.szte.szakdolgozat.models.Thumbnail;
import com.szte.szakdolgozat.repository.ThumbnailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
