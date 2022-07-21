package com.szte.szakdolgozat.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document
public class Image {
    @Id
    private String id;
    private String name;
    private String location = "Unknown";
    private List<String> categories = new ArrayList<>();
    private Date uploaded = new Date(System.currentTimeMillis());
    private String imgB64;
    //TODO lots of metadata

}
