package com.gooalgene.wutbiolab.service;

import com.gooalgene.wutbiolab.entity.news.NewsCategory;
import com.gooalgene.wutbiolab.entity.news.NewsDetail;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;

import java.util.List;

public interface NewsService {
    CommonResponse<PageResponse<NewsDetail>> newsDetailPage(Integer pageNum, Integer pageSize);

    CommonResponse<List<NewsCategory>> allNewsCategory();

    CommonResponse<NewsDetail> newsDetailById(Integer id);

    CommonResponse<Boolean> renewNews(NewsDetail newsDetail);

    CommonResponse<Boolean> deleteById(Integer id);
}
