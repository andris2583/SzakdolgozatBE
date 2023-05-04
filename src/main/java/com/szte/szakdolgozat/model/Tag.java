package com.szte.szakdolgozat.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Tag {
    @Id
    private String id;
    private String name;

    public Tag(String name) {
        this.name = name;
    }

    public Tag() {
    }
}
