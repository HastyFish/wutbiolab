package com.gooalgene.wutbiolab.response.front;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OverviewWithImageResponse {

    public OverviewWithImageResponse() {
    }

    public OverviewWithImageResponse(Long id, Long categoryId, String category, String title, String imageurl) {
        this.id = id;
        this.categoryId = categoryId;
        this.category = category;
        this.title = title;
        this.imageurl = imageurl;
    }

    private Long id;

    private Long categoryId;

    private String category;

    private String title;

    private String imageurl;

}
