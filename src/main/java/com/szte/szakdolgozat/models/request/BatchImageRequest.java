package com.szte.szakdolgozat.models.request;

import lombok.Data;

import java.util.List;

@Data
public class BatchImageRequest {
    private List<String> tags;
    private RequestTagType requestTagType;
    private RequestOrderByType requestOrderByType;
    private RequestOrderType requestOrderType;
    private Integer batchSize;
    private Integer pageCount;
    private RequestFilter requestFilter;
}
