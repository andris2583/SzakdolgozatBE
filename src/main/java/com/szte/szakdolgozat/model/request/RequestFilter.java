package com.szte.szakdolgozat.model.request;

import lombok.Data;

import java.util.Date;

@Data
public class RequestFilter {
    private String nameFilterString;
    private Integer maxCount;
    private String ownerId;
    private Date fromDate;
    private Date toDate;
    private Double latitude;
    private Double longitude;
    private Integer distance;
}
