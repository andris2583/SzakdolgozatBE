package com.szte.szakdolgozat.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Set;

@Data
public class Collection {
    @Id
    private String id;
    private String name;
    private Set<String> imageIds;
    private String userId;
    private Privacy privacy;
    private CollectionType type;

    public Collection(String name, Set<String> imageIds, String userId, Privacy privacy, CollectionType type) {
        this.name = name;
        this.imageIds = imageIds;
        this.userId = userId;
        this.privacy = privacy;
        this.type = type;
    }
}
