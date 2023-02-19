package com.szte.szakdolgozat.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Document
public class ImageViewMap {
    @Id
    private String id;
    private Map<String, Integer> imageViewMap;
}
