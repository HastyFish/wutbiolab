package com.gooalgene.wutbiolab.service;

import com.gooalgene.wutbiolab.entity.news.NewsCategory;
import com.gooalgene.wutbiolab.entity.news.NewsDetail;
import com.gooalgene.wutbiolab.entity.news.NewsOverview;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.front.DetailPageResponse;
import com.gooalgene.wutbiolab.response.front.DetailResponse;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface NewsService {
    CommonResponse<PageResponse<NewsOverview>> newsDetailPage(Integer pageNum, Integer pageSize, Long categoryId);

    CommonResponse<List<NewsCategory>> allNewsCategory();

    CommonResponse<NewsDetail> newsDetailById(Integer id);

    CommonResponse<Boolean> renewNews(NewsDetail newsDetail);

    CommonResponse<Boolean> deleteById(Integer id);

    CommonResponse<DetailPageResponse<NewsOverview>> newsDetailPageByCategory(long categoryId, int pageNum, int pageSize);

    CommonResponse<DetailResponse<NewsDetail, NewsOverview>> newsDetailPublishedById(long id);

    /**
     * 根据发布状态查询新闻
     */
    CommonResponse<PageResponse<NewsOverview>> newsDetailPageByStatus(Integer pageNum, Integer pageSize, Integer publishStatus);

    /**
     * 根据新闻标题模糊查询
     */
    CommonResponse<PageResponse<NewsOverview>> newsDetailPageByTitle(Integer pageNum, Integer pageSize, String title);

    /**
     * 根据时间段查询
     */
    CommonResponse<PageResponse<NewsOverview>> newsDetailPageByDate(Integer pageNum, Integer pageSize, String beginDate, String endDate);
}
