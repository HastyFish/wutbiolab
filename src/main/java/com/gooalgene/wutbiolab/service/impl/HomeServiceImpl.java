package com.gooalgene.wutbiolab.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.dao.home.AcademicImageDAO;
import com.gooalgene.wutbiolab.dao.home.CooperationLinkDAO;
import com.gooalgene.wutbiolab.dao.home.FooterDAO;
import com.gooalgene.wutbiolab.dao.home.NewsImageDAO;
import com.gooalgene.wutbiolab.dao.news.NewsCategoryDAO;
import com.gooalgene.wutbiolab.dao.news.NewsDetailDAO;
import com.gooalgene.wutbiolab.entity.Picture;
import com.gooalgene.wutbiolab.entity.home.AcademicImage;
import com.gooalgene.wutbiolab.entity.home.CooperationLink;
import com.gooalgene.wutbiolab.entity.home.Footer;
import com.gooalgene.wutbiolab.entity.home.NewsImage;
import com.gooalgene.wutbiolab.entity.news.NewsCategory;
import com.gooalgene.wutbiolab.entity.news.NewsOverview;
import com.gooalgene.wutbiolab.property.GooalApplicationProperty;
import com.gooalgene.wutbiolab.request.HomeImageRequest;
import com.gooalgene.wutbiolab.response.HomeImageResponse;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.service.HomeService;
import com.gooalgene.wutbiolab.service.PictureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HomeServiceImpl implements HomeService {

    private AcademicImageDAO academicImageDAO;

    private NewsImageDAO newsImageDAO;

    private CooperationLinkDAO cooperationLinkDAO;

    private FooterDAO footerDAO;

    private PictureService pictureService;

    private NewsCategoryDAO newsCategoryDAO;

    private NewsDetailDAO newsDetailDAO;

    private ObjectMapper objectMapper;

    private GooalApplicationProperty gooalApplicationProperty;

    private Logger logger = LoggerFactory.getLogger(HomeServiceImpl.class);

    public HomeServiceImpl(AcademicImageDAO academicImageDAO, NewsImageDAO newsImageDAO,
                           CooperationLinkDAO cooperationLinkDAO, FooterDAO footerDAO,
                           PictureService pictureService, NewsCategoryDAO newsCategoryDAO,
                           NewsDetailDAO newsDetailDAO, ObjectMapper objectMapper,
                           GooalApplicationProperty gooalApplicationProperty) {
        this.gooalApplicationProperty = gooalApplicationProperty;
        this.objectMapper = objectMapper;
        this.newsDetailDAO = newsDetailDAO;
        this.newsCategoryDAO = newsCategoryDAO;
        this.pictureService = pictureService;
        this.academicImageDAO = academicImageDAO;
        this.newsImageDAO = newsImageDAO;
        this.cooperationLinkDAO = cooperationLinkDAO;
        this.footerDAO = footerDAO;
    }

    @Override
    public CommonResponse<HomeImageResponse> getImages() {
        NewsImage newsImage = newsImageDAO.findAll().size() == 0 ? new NewsImage() : newsImageDAO.findAll().get(0);
        AcademicImage academicImage = academicImageDAO.findAll().size() == 0 ? new AcademicImage() : academicImageDAO.findAll().get(0);
        return ResponseUtil.success(new HomeImageResponse(academicImage.getContext(), newsImage.getContext()));
    }

    @Override
    @Transactional
    public CommonResponse<Boolean> saveImages(HomeImageRequest homeImageRequest) {
        if (null != homeImageRequest.getNewsImage()) {
            NewsImage newsImage = newsImageDAO.findAll().get(0);
            newsImage.setContext(pictureService.saveBase64(null, homeImageRequest.getNewsImage()));
            newsImage.setPublishStatus(CommonConstants.PUBLISHED);
            newsImageDAO.save(newsImage);
        }
        if (null != homeImageRequest.getAcademicImage()) {
            AcademicImage academicImage = academicImageDAO.findAll().get(0);
            academicImage.setContext(pictureService.saveBase64(null, homeImageRequest.getAcademicImage()));
            academicImage.setPublishStatus(CommonConstants.PUBLISHED);
            academicImageDAO.save(academicImage);
        }
        return ResponseUtil.success(true);
    }

    @Override
    public CommonResponse<List<CooperationLink>> getCooperationLink() {
        List<CooperationLink> cooperationLinkList = cooperationLinkDAO.findAll();
        return ResponseUtil.success(cooperationLinkList);
    }

    @Override
    @Transactional
    public CommonResponse<Boolean> saveCooperationLink(List<CooperationLink> cooperationLinkList) {
//        List<CooperationLink> cooperationLinkList = coo.getCooperationLink();
        cooperationLinkDAO.saveAll(cooperationLinkList);
        return ResponseUtil.success(true);
    }

    @Override
    public CommonResponse<List<Footer>> getFooter() {
        return ResponseUtil.success(footerDAO.findAll());
    }

    @Override
    @Transactional
    public CommonResponse<Boolean> saveFooter(List<Footer> footerList) {
        if (footerList.size() > 5) {
            return ResponseUtil.error("more than 5 item");
        } else {
            footerDAO.saveAll(footerList);
            return ResponseUtil.success(true);
        }
    }

    @Override
    @Transactional
    public CommonResponse<Boolean> deleteCooperationLinkById(long id) {
        cooperationLinkDAO.deleteById(id);
        return ResponseUtil.success(true);
    }

    @Override
    public CommonResponse<Boolean> deleteFooterById(long id) {
        footerDAO.deleteById(id);
        return ResponseUtil.success(true);
    }

    @Override
    public CommonResponse<List<String>> getNewsSlideShow() {
        if (newsCategoryDAO.findById(CommonConstants.TOUTIAO.longValue()).isPresent()) {
            NewsCategory headline = newsCategoryDAO.findById(CommonConstants.TOUTIAO.longValue()).get();
            List<NewsOverview> newsDetailList = newsDetailDAO.findByCategoryEquals(headline.getCategory());
            List<String> imageUrlList = new ArrayList<>();
            newsDetailList.forEach(one -> {
                try {
                    Picture picture = objectMapper.readValue(one.getImage(), Picture.class);
                    imageUrlList.add(gooalApplicationProperty.getImageNginxUrl() + picture.getUrl());
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("Error type in convert " + one.getId() + "'s image to Picture.class");
                }
            });
            return ResponseUtil.success(imageUrlList);
        } else {
            return ResponseUtil.error("No category like headline");
        }
    }

    @Override
    public CommonResponse<Map<String, ?>> displayHomeInfo() {
        return null;
    }
}
