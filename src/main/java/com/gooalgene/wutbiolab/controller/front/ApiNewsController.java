package com.gooalgene.wutbiolab.controller.front;

import com.gooalgene.wutbiolab.entity.news.NewsCategory;
import com.gooalgene.wutbiolab.entity.news.NewsDetail;
import com.gooalgene.wutbiolab.entity.news.NewsOverview;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.service.NewsService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{category}")
    public CommonResponse<PageResponse<NewsOverview>> newsDetailByCategory(@PathVariable String category,
                                                                           Integer pageNum,
                                                                           Integer pageSize) {
        return newsService.newsDetailPageByCategory(category, pageNum, pageSize);
    }

    @GetMapping("/{id}")
    public CommonResponse<NewsDetail> newsDetailById(@PathVariable long id) {
        return newsService.newsDetailPublishedById(id);
    }

    @GetMapping("/next")
    public CommonResponse<NewsOverview> nextNewsDetail(long publishDate) {
        return newsService.nextPublishedNewsDetail(publishDate);
    }

    @GetMapping("/previous")
    public CommonResponse<NewsOverview> peviousNewsDetail(long publishDate) {
        return newsService.previousPublishedNewsDetail(publishDate);
    }

}
