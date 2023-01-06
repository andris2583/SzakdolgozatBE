package com.szte.szakdolgozat.models.request;

import lombok.Data;

@Data
public class RequestFilter {
    private String nameFilterString;
    private Integer maxCount;
}
