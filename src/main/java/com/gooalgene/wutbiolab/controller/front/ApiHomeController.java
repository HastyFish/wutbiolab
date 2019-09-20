package com.gooalgene.wutbiolab.controller.front;

import com.gooalgene.wutbiolab.entity.common.AllCategory;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.response.front.ImageResponse;
import com.gooalgene.wutbiolab.service.HomeService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "前台首页", tags = {"前台首页接口"})
@RestController
@RequestMapping("/api/home")
public class ApiHomeController {

    private HomeService homeService;

    public ApiHomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/slideshow")
    public CommonResponse<List<ImageResponse>> getSlideshow() {
        return homeService.displayNewsSlideShow();
    }

    @GetMapping
    public CommonResponse<List<Object>> displayHomeInfo() {
        return homeService.displayHomeInfo();
    }

    @GetMapping("/all/category")
    public CommonResponse<List<AllCategory>> getAllCategorys(){
        return ResponseUtil.success(homeService.getAllCategorys());
    }
}
