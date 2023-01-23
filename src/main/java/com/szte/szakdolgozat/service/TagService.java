package com.szte.szakdolgozat.service;

import com.szte.szakdolgozat.model.Tag;
import com.szte.szakdolgozat.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TagService {

    private final TagRepository tagRepository;


    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Tag insertTag(Tag tag) {
        return tagRepository.insert(tag);
    }

    public void deleteTag(Tag tag) {
        tagRepository.delete(tag);
    }

}
