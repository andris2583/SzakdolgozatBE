package com.szte.szakdolgozat.models;

import lombok.Data;
import org.aspectj.apache.bcel.classfile.Unknown;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Data
@Document
public class Image {
    @Id
    private String id;
    private String name;
    private String extension;
    private String location = "Unknown";
    private List<String> tags = new ArrayList<>();
    private Date uploaded = new Date(System.currentTimeMillis());
    private String imgB64;
    private Object properties;

    public String getIdWithExtension(){
        return this.id + "." + this.extension;
    }

    public String getThumbnailName() {
        return this.id + ".png";
    }
}
