package com.gooalgene.wutbiolab.controller.admin;

import com.gooalgene.wutbiolab.entity.news.NewsCategory;
import com.gooalgene.wutbiolab.entity.news.NewsDetail;
import com.gooalgene.wutbiolab.entity.news.NewsOverview;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.service.NewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Api(value = "后台新闻", tags = {"后台新闻接口"})
@RestController
@RequestMapping("/news")
public class NewsController {

    private NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @ApiOperation(value="查询新闻分类", notes="查询新闻分类")
    @GetMapping("/category")
    public CommonResponse<List<NewsCategory>> getNewsCategory() {
        return newsService.allNewsCategory();
    }

    @ApiOperation(value="查询新闻分页列表", notes="查询新闻分页列表，参数为pageNum, pageSize和categoryId（可为空）")
    @GetMapping
    public CommonResponse<PageResponse<NewsOverview>> getNewsDetailPage(Integer pageNum,
                                                                        Integer pageSize,
                                                                        Long categoryId) {
        System.out.println("分类为: " + categoryId);
        return newsService.newsDetailPage(pageNum, pageSize, categoryId);
    }

    @ApiOperation(value="根据id查询新闻", notes="根据id查询新闻")
    @GetMapping("/{id}")
    public CommonResponse<NewsDetail> getNewsDetail(@PathVariable Integer id) {
        return newsService.newsDetailById(id);
    }

    @ApiOperation(value="保存新闻", notes="保存新闻")
    @PostMapping
    public CommonResponse<Boolean> renewNewsDetail(@RequestBody NewsDetail newsDetail) {
        return newsService.renewNews(newsDetail);
    }

    @ApiOperation(value="删除新闻", notes="删除新闻")
    @DeleteMapping("/{id}")
    public CommonResponse<Boolean> deleteNewsDetail(@PathVariable int id) {
        return newsService.deleteById(id);
    }


    @ApiOperation(value = "pageNum, pageSize publishStatus", notes="查询指定状态的新闻分页列表")
    @GetMapping("/status")
    public CommonResponse<PageResponse<NewsOverview>> getNewsDetailPageByStatus(Integer pageNum,
                                                                                Integer pageSize,
                                                                                Integer publishStatus) {
        return newsService.newsDetailPageByStatus(pageNum, pageSize, publishStatus);
    }

    @ApiOperation(value = "pageNum, pageSize, title", notes = "根据新闻标题进行模糊查询")
    @GetMapping("/title")
    public CommonResponse<PageResponse<NewsOverview>> getNewsDetailPageByTitle(Integer pageNum,
                                                                               Integer pageSize,
                                                                               String title){
        return newsService.newsDetailPageByTitle(pageNum, pageSize, title);
    }

    @ApiOperation(value = "pageNum, pageSize, beginDate, endDate", notes = "根据时间段查询")
    @GetMapping("/date")
    public CommonResponse<PageResponse<NewsOverview>> getNewsDetailPageByDate(Integer pageNum,
                                                                              Integer pageSize,
                                                                              String beginDate,
                                                                              String endDate){
        return newsService.newsDetailPageByDate(pageNum, pageSize, beginDate, endDate);
    }
}
