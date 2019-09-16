package com.gooalgene.wutbiolab.controller.admin;

import com.gooalgene.wutbiolab.entity.news.NewsCategory;
import com.gooalgene.wutbiolab.entity.news.NewsDetail;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.service.NewsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    private NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/category")
    public CommonResponse<List<NewsCategory>> getNewsCategory() {
        return newsService.allNewsCategory();
    }

    @GetMapping
    public CommonResponse<PageResponse<NewsDetail>> getNewsDetailPage(Integer pageNum, Integer pageSize) {
        return newsService.newsDetailPage(pageNum, pageSize);
    }

    @GetMapping("/{id}")
    public CommonResponse<NewsDetail> getNewsDetail(@PathVariable Integer id) {
        return newsService.newsDetailById(id);
    }

    @PostMapping
    public CommonResponse<Boolean> renewNewsDetail(@RequestBody NewsDetail newsDetail) {
        return newsService.renewNews(newsDetail);
    }

    @DeleteMapping("/{id}")
    public CommonResponse<Boolean> deleteNewsDetail(@PathVariable int id) {
        return newsService.deleteById(id);
    }
}
