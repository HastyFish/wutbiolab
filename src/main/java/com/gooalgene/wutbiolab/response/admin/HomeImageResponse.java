package com.gooalgene.wutbiolab.response.admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomeImageResponse {

    public HomeImageResponse() {
    }

    public HomeImageResponse(String academicImage, String newsImage) {
        this.academicImage = academicImage;
        this.newsImage = newsImage;
    }

    private String academicImage;

    private String newsImage;
}
