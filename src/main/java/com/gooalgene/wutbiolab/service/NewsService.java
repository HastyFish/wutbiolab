package com.gooalgene.wutbiolab.service;

import com.gooalgene.wutbiolab.entity.news.NewsCategory;
import com.gooalgene.wutbiolab.entity.news.NewsDetail;
import com.gooalgene.wutbiolab.entity.news.NewsOverview;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.front.NewsResponse;

import java.util.List;

public interface NewsService {
    CommonResponse<PageResponse<NewsOverview>> newsDetailPage(Integer pageNum, Integer pageSize);

    CommonResponse<List<NewsCategory>> allNewsCategory();

    CommonResponse<NewsDetail> newsDetailById(Integer id);

    CommonResponse<Boolean> renewNews(NewsDetail newsDetail);

    CommonResponse<Boolean> deleteById(Integer id);

    CommonResponse<PageResponse<NewsOverview>> newsDetailPageByCategory(String category, int pageNum, int pageSize);

    CommonResponse<NewsResponse> newsDetailPublishedById(long id);
}
