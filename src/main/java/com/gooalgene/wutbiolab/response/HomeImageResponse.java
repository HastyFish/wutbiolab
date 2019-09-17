package com.gooalgene.wutbiolab.response;

import com.gooalgene.wutbiolab.entity.home.AcademicImage;
import com.gooalgene.wutbiolab.entity.home.NewsImage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomeImageResponse {

    public HomeImageResponse() {
    }

    public HomeImageResponse(AcademicImage academicImage, NewsImage newsImage) {
        this.academicImage = academicImage;
        this.newsImage = newsImage;
    }

    private AcademicImage academicImage;

    private NewsImage newsImage;
}
