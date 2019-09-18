package com.gooalgene.wutbiolab.controller.front;

import com.gooalgene.wutbiolab.entity.home.Footer;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.service.HomeService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "页脚", tags = {"页脚接口"})
@RestController
@RequestMapping("/api/footer")
public class ApiFooterController {

    private HomeService homeService;

    public ApiFooterController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping
    public CommonResponse<List<Footer>> getFooter() {
        return homeService.getFooter();
    }

}
