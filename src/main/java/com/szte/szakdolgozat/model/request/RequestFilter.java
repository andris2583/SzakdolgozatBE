package com.szte.szakdolgozat.model.request;

import lombok.Data;

@Data
public class RequestFilter {
    private String nameFilterString;
    private Integer maxCount;
    private String ownerId;
}
