package com.gooalgene.wutbiolab.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooalgene.wutbiolab.dao.news.NewsCategoryDAO;
import com.gooalgene.wutbiolab.dao.news.NewsDetailDAO;
import com.gooalgene.wutbiolab.entity.Picture;
import com.gooalgene.wutbiolab.entity.news.NewsCategory;
import com.gooalgene.wutbiolab.entity.news.NewsDetail;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.service.NewsService;
import com.gooalgene.wutbiolab.service.PictureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    private NewsCategoryDAO newsCategoryDAO;

    private NewsDetailDAO newsDetailDAO;

    private PictureService pictureService;

    private Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);

    public NewsServiceImpl(NewsCategoryDAO newsCategoryDAO, NewsDetailDAO newsDetailDAO, PictureService pictureService) {
        this.pictureService = pictureService;
        this.newsDetailDAO = newsDetailDAO;
        this.newsCategoryDAO = newsCategoryDAO;
    }

    @Override
    public CommonResponse<Page<NewsDetail>> newsDetailPage(Integer pageNum, Integer pageSize) {
        return ResponseUtil.success(newsDetailDAO.findAll(PageRequest.of(pageNum, pageSize)));
    }

    @Override
    public CommonResponse<List<NewsCategory>> allNewsCategory() {
        return ResponseUtil.success(newsCategoryDAO.findAll());
    }

    @Override
    public CommonResponse<NewsDetail> newsDetailById(Integer id) {
        if (newsDetailDAO.findById(id.longValue()).isPresent()) {
            return ResponseUtil.success(newsDetailDAO.findById(id.longValue()).get());
        } else {
            return ResponseUtil.error("no such news!!!");
        }
    }

    @Override
    @Transactional
    public CommonResponse<Boolean> renewNews(NewsDetail newsDetail) {
        ObjectMapper objectMapper = new ObjectMapper();
        if (null != newsDetail.getId()) {
            if (newsDetailDAO.findById(newsDetail.getId()).isPresent()) {
                NewsDetail oldNewsDetail = newsDetailDAO.findById(newsDetail.getId()).get();
                try {
                    String newImageUrl = pictureService.saveBase64(oldNewsDetail.getImage(), newsDetail.getImage());
                    if (null != newImageUrl) {
                        newsDetail.setImage(newImageUrl);
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
                Picture newImage = objectMapper.readValue(newsDetail.getImage(), Picture.class);
                newImage.setUrl(pictureService.saveBase64(null, newImage.getUrl()));
                newsDetail.setImage(objectMapper.writeValueAsString(newImage));
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
}