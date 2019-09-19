package com.gooalgene.wutbiolab.response.front;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageResponse {

    public ImageResponse() {
    }

    public ImageResponse(String title, String imageurl) {
        this.title = title;
        this.imageurl = imageurl;
    }

    private String title;

    private String imageurl;

}
