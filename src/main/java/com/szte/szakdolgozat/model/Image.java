package com.szte.szakdolgozat.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Document
public class Image {
    @Id
    String id;
    String name;
    String extension;
    List<String> tags = new ArrayList<>();
    Date uploaded = new Date(System.currentTimeMillis());
    String imgB64;
    Map<String, Object> properties;
    String ownerId;
    Privacy privacy;

    public String getIdWithExtension() {
        return this.id + "." + this.extension;
    }

    public String getThumbnailName() {
        return this.id + ".png";
    }
}
