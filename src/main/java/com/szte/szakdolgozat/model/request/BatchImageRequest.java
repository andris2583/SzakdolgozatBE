package com.szte.szakdolgozat.model.request;

import lombok.Data;

import java.util.List;

@Data
public class BatchImageRequest {
    private List<String> tags;
    private RequestTagType requestTagType;
    private RequestOrderByType requestOrderByType;
    private RequestOrderType requestOrderType = RequestOrderType.ASC;
    private Integer batchSize = -1;
    private Integer pageCount;
    private RequestFilter requestFilter;
    private String collectionId;
    private String requestUserId;
    private Boolean loadThumbnails = false;
}
