package com.gooalgene.wutbiolab.response.backend;

import com.gooalgene.wutbiolab.entity.home.AcademicImage;
import com.gooalgene.wutbiolab.entity.home.NewsImage;
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
