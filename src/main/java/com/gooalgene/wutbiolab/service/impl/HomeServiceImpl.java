package com.gooalgene.wutbiolab.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.dao.AllCategroryDAO;
import com.gooalgene.wutbiolab.dao.home.AcademicImageDAO;
import com.gooalgene.wutbiolab.dao.home.CooperationLinkDAO;
import com.gooalgene.wutbiolab.dao.home.FooterDAO;
import com.gooalgene.wutbiolab.dao.home.NewsImageDAO;
import com.gooalgene.wutbiolab.dao.news.NewsCategoryDAO;
import com.gooalgene.wutbiolab.dao.news.NewsDetailDAO;
import com.gooalgene.wutbiolab.dao.notice.NoticeDetailDAO;
import com.gooalgene.wutbiolab.dao.resource.ResourceDetailDAO;
import com.gooalgene.wutbiolab.entity.Picture;
import com.gooalgene.wutbiolab.entity.common.AllCategory;
import com.gooalgene.wutbiolab.entity.home.AcademicImage;
import com.gooalgene.wutbiolab.entity.home.CooperationLink;
import com.gooalgene.wutbiolab.entity.home.Footer;
import com.gooalgene.wutbiolab.entity.home.NewsImage;
import com.gooalgene.wutbiolab.entity.news.NewsCategory;
import com.gooalgene.wutbiolab.entity.news.NewsOverview;
import com.gooalgene.wutbiolab.entity.notice.NoticeOverview;
import com.gooalgene.wutbiolab.entity.resource.ResourceOverview;
import com.gooalgene.wutbiolab.request.HomeImageRequest;
import com.gooalgene.wutbiolab.response.admin.HomeImageResponse;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.response.front.OverviewWithImageResponse;
import com.gooalgene.wutbiolab.service.HomeService;
import com.gooalgene.wutbiolab.service.PictureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    private NoticeDetailDAO noticeDetailDAO;

    private ResourceDetailDAO resourceDetailDAO;

    private AllCategroryDAO allCategroryDAO;

    private Logger logger = LoggerFactory.getLogger(HomeServiceImpl.class);

    public HomeServiceImpl(AcademicImageDAO academicImageDAO, NewsImageDAO newsImageDAO,
                           CooperationLinkDAO cooperationLinkDAO, FooterDAO footerDAO,
                           PictureService pictureService, NewsCategoryDAO newsCategoryDAO,
                           NewsDetailDAO newsDetailDAO, ObjectMapper objectMapper,
                           NoticeDetailDAO noticeDetailDAO,
                           ResourceDetailDAO resourceDetailDAO, AllCategroryDAO allCategroryDAO) {
        this.resourceDetailDAO = resourceDetailDAO;
        this.noticeDetailDAO = noticeDetailDAO;
        this.objectMapper = objectMapper;
        this.newsDetailDAO = newsDetailDAO;
        this.newsCategoryDAO = newsCategoryDAO;
        this.pictureService = pictureService;
        this.academicImageDAO = academicImageDAO;
        this.newsImageDAO = newsImageDAO;
        this.cooperationLinkDAO = cooperationLinkDAO;
        this.footerDAO = footerDAO;
        this.allCategroryDAO = allCategroryDAO;
    }

    @Override
    public CommonResponse<HomeImageResponse> getImages() {
        NewsImage newsImage = newsImageDAO.findAll().size() == 0 ? new NewsImage() : newsImageDAO.findAll().get(0);
        AcademicImage academicImage = academicImageDAO.findAll().size() == 0 ? new AcademicImage() : academicImageDAO.findAll().get(0);
        //            List<Picture> newsImageList = objectMapper.readValue(newsImage.getContext(),
//                    objectMapper.getTypeFactory().constructParametricType(List.class, Picture.class));
//            newsImageList.forEach(one -> one.setUrl(gooalApplicationProperty.getImageNginxUrl() + one.getUrl()));
//            List<Picture> academicImageList = objectMapper.readValue(academicImage.getContext(),
//                    objectMapper.getTypeFactory().constructParametricType(List.class, Picture.class));
//            academicImageList.forEach(one -> one.setUrl(gooalApplicationProperty.getImageNginxUrl() + one.getUrl()));
        String academicImageListString = pictureService.formImageUrl(
                null == academicImage.getContext() ? "[]" : academicImage.getContext());
        String newsImageListString = pictureService.formImageUrl(
                null == newsImage.getContext() ? "[]" : newsImage.getContext());
        return ResponseUtil.success(new HomeImageResponse(
                academicImageListString, newsImageListString));
    }

    @Override
    @Transactional
    public CommonResponse<Boolean> saveImages(HomeImageRequest homeImageRequest) {
        if (null != homeImageRequest.getNewsImage()) {
            NewsImage newsImage = newsImageDAO.findAll().get(0);
            newsImage.setContext(pictureService.saveBase64(newsImage.getContext(), homeImageRequest.getNewsImage()));
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
    public CommonResponse<List<OverviewWithImageResponse>> displayNewsSlideShow() {
        List<NewsOverview> newsDetailList = newsDetailDAO.findByCategoryIdAndPublishStatus(
                CommonConstants.TOUTIAO, CommonConstants.PUBLISHED, PageRequest.of(0, 5,
                        Sort.by(new Sort.Order(Sort.Direction.DESC, CommonConstants.PUBLISHDATEFIELD),
                                new Sort.Order(Sort.Direction.DESC, CommonConstants.IDFIELD))));
        List<OverviewWithImageResponse> overviewList = new ArrayList<>();
        newsDetailList.forEach(one -> {
            try {
                String pictureListImage = pictureService.formImageUrl(one.getImage());
                List<Picture> pictureList = objectMapper.readValue(
                        pictureListImage,
                        objectMapper.getTypeFactory().constructParametricType(List.class, Picture.class));
                if (pictureList.size() > 0) {
                    pictureList.forEach(onePicture -> overviewList.add(new OverviewWithImageResponse(
                            one.getId(), one.getCategoryId(), one.getCategory(), one.getTitle(),
                            onePicture.getUrl())));
                } else {
                    overviewList.add(new OverviewWithImageResponse(one.getId(), one.getCategoryId(),
                            one.getCategory(), one.getTitle(), ""));
                }

            } catch (IOException e) {
                e.printStackTrace();
                logger.error("Error type in convert " + one.getId() + "'s image to Picture.class");
            }
        });
        return ResponseUtil.success(overviewList);
    }

    @Override
    public CommonResponse<List<Footer>> displayFooter() {
        return ResponseUtil.success(footerDAO.findByPublishStatusEquals(CommonConstants.PUBLISHED));
    }

    @Override
    public CommonResponse<List<Object>> displayHomeInfo() {
//        Map<String, Object> result = new HashMap<>();
        List<Object> result = new ArrayList<>();

        /*按发布时间降序排序*/
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.DESC, CommonConstants.PUBLISHDATEFIELD),
                new Sort.Order(Sort.Direction.DESC, CommonConstants.IDFIELD));

        /*科研动态*/
        List<NewsOverview> scientificNewsList = newsDetailDAO.findByCategoryIdAndPublishStatusPage(
                CommonConstants.KEYAN, CommonConstants.PUBLISHED, PageRequest.of(0, 5,
                        sort)).getContent();
        result.add(scientificNewsList);

        /*新闻动态*/
        List<NewsOverview> latestNewsOverviewList = newsDetailDAO.findByCategoryIdAndPublishStatusPage(
                CommonConstants.ZONGHE, CommonConstants.PUBLISHED,
                PageRequest.of(0, 5, sort)).getContent();
//        result.put(CommonConstants.TOUTIAOFIELD, latestNewsOverviewList);
        result.add(latestNewsOverviewList);

        /*新闻动态图片*/
        try {
            NewsImage newsImage = newsImageDAO.findAll().get(0);
            List<Picture> pictureList = objectMapper.readValue((pictureService.formImageUrl(newsImage.getContext())),
                    objectMapper.getTypeFactory().constructParametricType(List.class, Picture.class));
            if (pictureList.size() > 1) {
                result.add(pictureList.get(0));
            } else if (pictureList.size() == 0) {
                result.add("");
            } else {
                pictureList.forEach(one -> result.add(one.getUrl()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            result.add("");
        }

        /*通知公告*/
        List<NoticeOverview> noticeDetailList = noticeDetailDAO.findByPublishStatusEquals(
                CommonConstants.PUBLISHED, PageRequest.of(0, 5, sort)).getContent();
        result.add(noticeDetailList);

        /*学术活动*/
        List<NewsOverview> acadeimcNewsList = newsDetailDAO.findByCategoryIdAndPublishStatusPage(
                CommonConstants.XUESHU, CommonConstants.PUBLISHED, PageRequest.of(0, 5,
                        sort)).getContent();
        result.add(acadeimcNewsList);

        /*学术活动图片*/
        try {
            AcademicImage academicImage = academicImageDAO.findAll().get(0);
            List<Picture> pictureList = objectMapper.readValue((pictureService.formImageUrl(academicImage.getContext())),
                    objectMapper.getTypeFactory().constructParametricType(List.class, Picture.class));
            if (pictureList.size() > 1) {
                result.add(pictureList.get(0));
            } else if (pictureList.size() == 1) {
                pictureList.forEach(one -> result.add(one.getUrl()));
            } else {
                result.add("");
            }
            //            result.add(pictureList.get(0).getUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*招聘招生*/
        List<NoticeOverview> noticeList = noticeDetailDAO.findByCategoryIdAndPublishStatusPage(
                CommonConstants.ZHAOPIN, CommonConstants.PUBLISHED, PageRequest.of(0, 5, sort))
                .getContent();
        result.add(noticeList);

        /*资源发布*/
        Sort.Order orderPublishDate = new Sort.Order(Sort.Direction.DESC, CommonConstants.PUBLISHDATEFIELD);
        Sort.Order orderId = new Sort.Order(Sort.Direction.DESC, CommonConstants.ID);
        List<ResourceOverview> resourceOverviewList = resourceDetailDAO.findByPublishStatusEquals(
                CommonConstants.PUBLISHED, PageRequest.of(0, 4,
                        Sort.by(orderPublishDate, orderId)));
        List<OverviewWithImageResponse> resourceImageUrlList = new ArrayList<>();
        resourceOverviewList.forEach(one -> {
            try {
                String pictureListImage = pictureService.formImageUrl(one.getImage());
                List<Picture> pictureList = objectMapper.readValue(pictureListImage,
                        objectMapper.getTypeFactory().constructParametricType(List.class, Picture.class));
                if (pictureList.size() > 0) {
                    pictureList.forEach(onePicture -> resourceImageUrlList.add(new OverviewWithImageResponse(
                            one.getId(), one.getCategoryId(), one.getCategory(), one.getTitle(), onePicture.getUrl())));
                } else {
                    resourceImageUrlList.add(new OverviewWithImageResponse(
                            one.getId(), one.getCategoryId(), one.getCategory(), one.getTitle(), new Picture().getUrl()));
                }
            } catch (IOException e) {
                logger.error("Error type in convert " + one.getId() + "'s image to Picture.class");
                e.printStackTrace();
            }
        });
        result.add(resourceImageUrlList);

        /*友情链接*/
        List<CooperationLink> cooperationLinkList = cooperationLinkDAO.findByPublishStatusEquals(
                CommonConstants.PUBLISHED);
        result.add(cooperationLinkList);


        return ResponseUtil.success(result);
    }

    @Override
    public List<AllCategory> getAllCategorys() {
        return allCategroryDAO.findAll();
    }
}
