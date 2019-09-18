package com.gooalgene.wutbiolab.controller.front;

import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.service.HomeService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(value = "前台首页", tags = {"前台首页接口"})
@RestController
@RequestMapping("/api/home")
public class ApiHomeController {

    private HomeService homeService;

    public ApiHomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/slideshow")
    public CommonResponse<List<String>> getSlideshow() {
        return homeService.getNewsSlideShow();
    }

    @GetMapping
    public CommonResponse<Map<String, ?>> displayHomeInfo() {
        return homeService.displayHomeInfo();
    }

}
