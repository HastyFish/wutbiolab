package com.gooalgene.wutbiolab.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.dao.news.NewsCategoryDAO;
import com.gooalgene.wutbiolab.dao.news.NewsDetailDAO;
import com.gooalgene.wutbiolab.entity.Picture;
import com.gooalgene.wutbiolab.entity.news.NewsCategory;
import com.gooalgene.wutbiolab.entity.news.NewsDetail;
import com.gooalgene.wutbiolab.entity.news.NewsOverview;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.response.front.DetailPageResponse;
import com.gooalgene.wutbiolab.response.front.NewsDetailResponse;
import com.gooalgene.wutbiolab.service.NewsService;
import com.gooalgene.wutbiolab.service.PictureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    private NewsCategoryDAO newsCategoryDAO;

    private NewsDetailDAO newsDetailDAO;

    private PictureService pictureService;

    private ObjectMapper objectMapper;

    private Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);

    public NewsServiceImpl(NewsCategoryDAO newsCategoryDAO, NewsDetailDAO newsDetailDAO,
                           PictureService pictureService, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.pictureService = pictureService;
        this.newsDetailDAO = newsDetailDAO;
        this.newsCategoryDAO = newsCategoryDAO;
    }

    @Override
    public CommonResponse<PageResponse<NewsOverview>> newsDetailPage(Integer pageNum, Integer pageSize) {
//        Page<NewsDetail> page = newsDetailDAO.findAll(PageRequest.of(pageNum - 1, pageSize));
        Page<NewsOverview> page = newsDetailDAO.findNewsDetailBy(PageRequest.of(pageNum - 1, pageSize));
        return ResponseUtil.success(new PageResponse<>(page.getContent(), pageNum, pageSize, page.getTotalElements()));
    }

    @Override
    public CommonResponse<List<NewsCategory>> allNewsCategory() {
        return ResponseUtil.success(newsCategoryDAO.findAll());
    }

    @Override
    public CommonResponse<NewsDetail> newsDetailById(Integer id) {
        if (newsDetailDAO.findById(id.longValue()).isPresent()) {
            NewsDetail newsDetail = newsDetailDAO.findById(id.longValue()).get();
            newsDetail.setImage(pictureService.formImageUrl(newsDetail.getImage()));
            return ResponseUtil.success(newsDetail);
        } else {
            return ResponseUtil.error("no such news!!!");
        }
    }

    @Override
    @Transactional
    public CommonResponse<Boolean> renewNews(NewsDetail newsDetail) {
        if (null != newsDetail.getId()) {
            if (newsDetailDAO.findById(newsDetail.getId()).isPresent()) {
                NewsDetail oldNewsDetail = newsDetailDAO.findById(newsDetail.getId()).get();
                try {
                    String newImage = pictureService.saveBase64(oldNewsDetail.getImage(), newsDetail.getImage());
                    if (null != newImage) {
                        newsDetail.setImage(newImage);
                    } else {
                        logger.error("fail to storge image");
                        newsDetail.setImage(objectMapper.writeValueAsString(new Picture()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("fail to storge image");
                }
                newsDetailDAO.save(newsDetail);
                return ResponseUtil.success(true);
            } else {
                logger.error("wrong id");
                return ResponseUtil.error("faild to save news");
            }
        } else {
            try {
                if (newsDetailDAO.findByCategoryEquals(newsDetail.getCategory()).size() >= 5) {
                    return ResponseUtil.error("more than 5 item");
                }
                String newImage = pictureService.saveBase64(null, newsDetail.getImage());
                if (null != newImage) {
                    newsDetail.setImage(newImage);
                } else {
                    logger.error("fail to storge image");
                    newsDetail.setImage(objectMapper.writeValueAsString(new Picture()));
                }
                newsDetailDAO.save(newsDetail);
                return ResponseUtil.success(true);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseUtil.error("fail to save news");
            }
        }
    }

    @Override
    @Transactional
    public CommonResponse<Boolean> deleteById(Integer id) {
        try {
            newsDetailDAO.deleteById(id.longValue());
            return ResponseUtil.success(true);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error("id is null");
        }
    }

    @Override
    public CommonResponse<DetailPageResponse<NewsOverview>> newsDetailPageByCategory(Integer categoryId,
                                                                                     int pageNum,
                                                                                     int pageSize) {
        if (newsCategoryDAO.findById(categoryId.longValue()).isPresent()) {
            NewsCategory newsCategory = newsCategoryDAO.findById(categoryId.longValue()).get();
            Page<NewsOverview> newsOverviewPage = newsDetailDAO.findByCategoryAndPublishStatusPage(newsCategory.getCategory(),
                    CommonConstants.PUBLISHED, PageRequest.of(pageNum - 1, pageSize));
            return ResponseUtil.success(new DetailPageResponse<>(newsOverviewPage.getContent(), pageNum,
                    pageSize, newsOverviewPage.getTotalElements(), newsCategory.getCategory()));
        } else {
            return ResponseUtil.error("Wrong id");
        }
    }

    @Override
    public CommonResponse<NewsDetailResponse> newsDetailPublishedById(long id) {
        NewsDetail newsDetail = newsDetailDAO.findByIdAndPublishStatus(id, CommonConstants.PUBLISHED);
        NewsOverview next = nextPublishedNewsDetail(newsDetail.getPublishDate(), newsDetail.getCategory());
        NewsOverview previous = previousPublishedNewsDetail(newsDetail.getPublishDate(), newsDetail.getCategory());
        return ResponseUtil.success(new NewsDetailResponse(newsDetail, previous, next));
    }

    private NewsOverview nextPublishedNewsDetail(long publishDate, String category) {
        Page<NewsOverview> newsDetailPage = newsDetailDAO.findNextNewsDetail(publishDate, category, CommonConstants.PUBLISHED,
                PageRequest.of(0, 1, new Sort(Sort.Direction.ASC, CommonConstants.PUBLISHDATEFIELD)));
        if (newsDetailPage.getTotalElements() > 0) {
            return newsDetailPage.getContent().get(0);
        } else {
            return null;
        }
    }

    private NewsOverview previousPublishedNewsDetail(long publishDate, String category) {
        Page<NewsOverview> newsDetailPage = newsDetailDAO.findPreviousNewsDetail(publishDate, category, CommonConstants.PUBLISHED,
                PageRequest.of(0, 1, new Sort(Sort.Direction.DESC, CommonConstants.PUBLISHDATEFIELD)));
        if (newsDetailPage.getTotalElements() > 0) {
            return newsDetailPage.getContent().get(0);
        } else {
            return null;
        }
    }
}
