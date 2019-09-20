package com.gooalgene.wutbiolab.controller.front;

import com.gooalgene.wutbiolab.entity.news.NewsCategory;
import com.gooalgene.wutbiolab.entity.news.NewsDetail;
import com.gooalgene.wutbiolab.entity.news.NewsOverview;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.front.DetailPageResponse;
import com.gooalgene.wutbiolab.response.front.DetailResponse;
import com.gooalgene.wutbiolab.service.NewsService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "前台新闻", tags = {"前台新闻接口"})
@RestController
@RequestMapping("/api/news")
public class ApiNewsController {

    private NewsService newsService;

    public ApiNewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/category")
    public CommonResponse<List<NewsCategory>> categoryList() {
        return newsService.allNewsCategory();
    }

    @GetMapping("/list/{categoryId}")
    public CommonResponse<DetailPageResponse<NewsOverview>> newsDetailByCategory(@PathVariable("categoryId") Integer categoryId,
                                                                                 @RequestParam("pageNum") Integer pageNum,
                                                                                 @RequestParam("pageSize") Integer pageSize) {
        return newsService.newsDetailPageByCategory(categoryId, pageNum, pageSize);
    }

    @GetMapping("/{id}")
    public CommonResponse<DetailResponse<NewsDetail, NewsOverview>> newsDetailById(@PathVariable long id) {
        return newsService.newsDetailPublishedById(id);
    }
}
