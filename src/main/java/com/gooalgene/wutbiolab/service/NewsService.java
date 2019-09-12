package com.gooalgene.wutbiolab.service;

import com.gooalgene.wutbiolab.entity.news.NewsCategory;
import com.gooalgene.wutbiolab.entity.news.NewsDetail;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NewsService {
    CommonResponse<Page<NewsDetail>> newsDetailPage(Integer pageNum, Integer pageSize);

    CommonResponse<List<NewsCategory>> allNewsCategory();

    CommonResponse<NewsDetail> newsDetailById(Integer id);

    CommonResponse<Boolean> renewNews(NewsDetail newsDetail);

    CommonResponse<Boolean> deleteById(Integer id);
}
