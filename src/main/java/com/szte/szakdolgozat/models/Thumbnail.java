package com.szte.szakdolgozat.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Thumbnail {
    @Id
    private String id;
    private String name;
    private String extension;
    private String imageID;
    private String imgB64;

    public String getNameWithExtension(){
        return this.name + "." + this.extension;
    }
}
