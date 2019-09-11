package com.gooalgene.wutbiolab.controller.admin;

import com.gooalgene.wutbiolab.entity.news.NewsDetail;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    @GetMapping
    public CommonResponse<PageResponse> getNewsDetailList(Integer pageNum, Integer pageSize) {
        return ResponseUtil.success(new PageResponse());
    }

    @GetMapping("/{id}")
    public CommonResponse<NewsDetail> getNewsDetail(@PathVariable Integer id) {
        return ResponseUtil.success(new NewsDetail());
    }

    @PostMapping
    public CommonResponse<Boolean> renewNewsDetail(NewsDetail newsDetail) {
        return ResponseUtil.success(true);
    }

    @DeleteMapping("/{id}")
    public CommonResponse<Boolean> deleteNewsDetail(@PathVariable Integer id) {
        return ResponseUtil.success(true);
    }
}
