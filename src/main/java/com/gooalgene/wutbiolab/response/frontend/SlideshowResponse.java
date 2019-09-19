package com.gooalgene.wutbiolab.response.frontend;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlideshowResponse {

    public SlideshowResponse() {
    }

    public SlideshowResponse(String title, String imageurl) {
        this.title = title;
        this.imageurl = imageurl;
    }

    private String title;

    private String imageurl;

}
