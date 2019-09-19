package com.gooalgene.wutbiolab.controller.admin;

import com.gooalgene.wutbiolab.entity.home.CooperationLink;
import com.gooalgene.wutbiolab.entity.home.Footer;
import com.gooalgene.wutbiolab.request.HomeImageRequest;
import com.gooalgene.wutbiolab.response.HomeImageResponse;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    private HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/image")
    public CommonResponse<HomeImageResponse> getImges() {
        return homeService.getImages();
    }

    @PostMapping("/image")
    public CommonResponse<Boolean> saveImages(@RequestBody HomeImageRequest homeImageRequest){
        return homeService.saveImages(homeImageRequest);
    }

    @GetMapping("/cooperation-link")
    public CommonResponse<List<CooperationLink>> getCooperationLink() {
        return homeService.getCooperationLink();
    }

    @PostMapping("/cooperation-link")
    public CommonResponse<Boolean> saveCooperationLink(@RequestBody List<CooperationLink> cooperationLinkList) {
        return homeService.saveCooperationLink(cooperationLinkList);
    }

    @DeleteMapping("/cooperation-link/{id}")
    public CommonResponse<Boolean> deleteCooperationLink(@PathVariable long id) {
        return homeService.deleteCooperationLinkById(id);
    }

    @GetMapping("/footer")
    public CommonResponse<List<Footer>> getFooter() {
        return homeService.getFooter();
    }

    @PostMapping("/footer")
    public CommonResponse<Boolean> saveFooter(@RequestBody List<Footer> footerList) {
        return homeService.saveFooter(footerList);
    }

    @DeleteMapping("/footer/{id}")
    public CommonResponse<Boolean> deleteFooter(@PathVariable long id) {
        return homeService.deleteFooterById(id);
    }

}
