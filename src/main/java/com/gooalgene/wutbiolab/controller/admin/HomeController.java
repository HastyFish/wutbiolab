package com.gooalgene.wutbiolab.controller.admin;

import com.gooalgene.wutbiolab.entity.Home;
import com.gooalgene.wutbiolab.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private HomeService homeService;

    @PostMapping("/images")
    public void saveImages(@RequestBody Home home){
        String newsImage = home.getNewsImage();
        String academicImage = home.getAcademicImage();

    }
}
