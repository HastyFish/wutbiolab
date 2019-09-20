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
import com.gooalgene.wutbiolab.dao.notice.NoticeCategoryDAO;
import com.gooalgene.wutbiolab.dao.notice.NoticeDetailDAO;
import com.gooalgene.wutbiolab.dao.resource.ResourceDetailDAO;
import com.gooalgene.wutbiolab.dao.scientific.ScientificResearchDetailDAO;
import com.gooalgene.wutbiolab.entity.Picture;
import com.gooalgene.wutbiolab.entity.common.AllCategory;
import com.gooalgene.wutbiolab.entity.home.AcademicImage;
import com.gooalgene.wutbiolab.entity.home.CooperationLink;
import com.gooalgene.wutbiolab.entity.home.Footer;
import com.gooalgene.wutbiolab.entity.home.NewsImage;
import com.gooalgene.wutbiolab.entity.news.NewsCategory;
import com.gooalgene.wutbiolab.entity.news.NewsOverview;
import com.gooalgene.wutbiolab.entity.notice.NoticeCategory;
import com.gooalgene.wutbiolab.entity.notice.NoticeOverview;
import com.gooalgene.wutbiolab.entity.resource.ResourceOverview;
import com.gooalgene.wutbiolab.entity.scientificResearch.ScientificResearchOverview;
import com.gooalgene.wutbiolab.request.HomeImageRequest;
import com.gooalgene.wutbiolab.response.admin.HomeImageResponse;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.response.front.ImageResponse;
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

    private NoticeCategoryDAO noticeCategoryDAO;

    private ScientificResearchDetailDAO scientificResearchDetailDAO;

    private ResourceDetailDAO resourceDetailDAO;

    private AllCategroryDAO allCategroryDAO;

    private Logger logger = LoggerFactory.getLogger(HomeServiceImpl.class);

    public HomeServiceImpl(AcademicImageDAO academicImageDAO, NewsImageDAO newsImageDAO,
                           CooperationLinkDAO cooperationLinkDAO, FooterDAO footerDAO,
                           PictureService pictureService, NewsCategoryDAO newsCategoryDAO,
                           NewsDetailDAO newsDetailDAO, ObjectMapper objectMapper,
                           ScientificResearchDetailDAO scientificResearchDetailDAO,
                           NoticeDetailDAO noticeDetailDAO, NoticeCategoryDAO noticeCategoryDAO,
                           ResourceDetailDAO resourceDetailDAO,AllCategroryDAO allCategroryDAO) {
        this.resourceDetailDAO = resourceDetailDAO;
        this.noticeCategoryDAO = noticeCategoryDAO;
        this.noticeDetailDAO = noticeDetailDAO;
        this.scientificResearchDetailDAO = scientificResearchDetailDAO;
        this.objectMapper = objectMapper;
        this.newsDetailDAO = newsDetailDAO;
        this.newsCategoryDAO = newsCategoryDAO;
        this.pictureService = pictureService;
        this.academicImageDAO = academicImageDAO;
        this.newsImageDAO = newsImageDAO;
        this.cooperationLinkDAO = cooperationLinkDAO;
        this.footerDAO = footerDAO;
        this.allCategroryDAO=allCategroryDAO;
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
    public CommonResponse<List<ImageResponse>> displayNewsSlideShow() {
        if (newsCategoryDAO.findById(CommonConstants.TOUTIAO.longValue()).isPresent()) {
            NewsCategory headline = newsCategoryDAO.findById(CommonConstants.TOUTIAO.longValue()).get();
            List<NewsOverview> newsDetailList = newsDetailDAO.findByCategoryAndPublishStatus(
                    headline.getCategory(), CommonConstants.PUBLISHED);
            List<ImageResponse> imageUrlList = new ArrayList<>();
            newsDetailList.forEach(one -> {
                    try {
                        String pictureListImage = pictureService.formImageUrl(one.getImage());
                        List<Picture> pictureList = objectMapper.readValue(
                                pictureListImage,
                                objectMapper.getTypeFactory().constructParametricType(List.class, Picture.class));
                        imageUrlList.add(new ImageResponse(one.getTitle(), pictureList.get(0).getUrl()));
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
    public CommonResponse<List<Footer>> displayFooter() {
        return ResponseUtil.success(footerDAO.findByPublishStatusEquals(CommonConstants.PUBLISHED));
    }

    @Override
    public CommonResponse<List<Object>> displayHomeInfo() {
//        Map<String, Object> result = new HashMap<>();
        List<Object> result = new ArrayList<>();

        /*按发布时间降序排序*/
        Sort sort = new Sort(Sort.Direction.DESC, CommonConstants.PUBLISHDATEFIELD);

        /*科研动态*/
//        List<NewsOverview> scientificNewsList = newsDetailDAO.findByCategoryAndPublishStatus();
//        List<ScientificResearchOverview> scientificResearchOverviewList =
//                scientificResearchDetailDAO
//                        .findByPublishStatusEquals(
//                                CommonConstants.PUBLISHED, PageRequest.of(0, 5, sort)).getContent();
////        result.put(ScientificResearchDetail.class.getSimpleName(), scientificResearchOverviewList);
//        result.add(scientificResearchOverviewList);

        /*新闻动态*/
        List<NewsOverview> latestNewsOverviewList = newsDetailDAO.findByPublishStatusEquals(
                CommonConstants.PUBLISHED, PageRequest.of(0, 5, sort)).getContent();
//        result.put(CommonConstants.TOUTIAOFIELD, latestNewsOverviewList);
        result.add(latestNewsOverviewList);

        /*新闻动态图片*/
        try {
            NewsImage newsImage = newsImageDAO.findAll().get(0);
            List<Picture> pictureList = objectMapper.readValue((pictureService.formImageUrl(newsImage.getContext())),
                    objectMapper.getTypeFactory().constructParametricType(List.class, Picture.class));
            result.add(pictureList.get(0).getUrl());
        } catch (IOException e) {
            e.printStackTrace();
            result.add("");
        }

        /*通知公告*/
        List<NoticeOverview> noticeDetailList = noticeDetailDAO.findByPublishStatusEquals(
                CommonConstants.PUBLISHED, PageRequest.of(0, 5, sort)).getContent();
        result.add(noticeDetailList);

        /*学术活动*/
        if (newsCategoryDAO.findById(CommonConstants.XUESHU.longValue()).isPresent()) {
            NewsCategory academicNewsCategory = newsCategoryDAO.findById(CommonConstants.XUESHU.longValue()).get();
            List<NewsOverview> acadeimcNewsList = newsDetailDAO.findByCategoryAndPublishStatus(
                    academicNewsCategory.getCategory(), CommonConstants.PUBLISHED);
            result.add(acadeimcNewsList);
        }

        /*学术活动图片*/
        try {
            AcademicImage academicImage = academicImageDAO.findAll().get(0);
            List<Picture> pictureList = objectMapper.readValue((pictureService.formImageUrl(academicImage.getContext())),
                    objectMapper.getTypeFactory().constructParametricType(List.class, Picture.class));
            result.add(pictureList.get(0).getUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*招聘招生*/
        if (noticeCategoryDAO.findById(CommonConstants.ZHAOPIN.longValue()).isPresent()) {
            NoticeCategory noticeCategory = noticeCategoryDAO.findById(CommonConstants.ZHAOPIN.longValue()).get();
            List<NoticeOverview> acadeimcNewsList = noticeDetailDAO.findByCategoryAndPublishStatus(
                    noticeCategory.getCategory(), CommonConstants.PUBLISHED);
            result.add(acadeimcNewsList);
        }

        /*资源发布*/
        List<ResourceOverview> resourceOverviewList = resourceDetailDAO.findByPublishStatusEquals(
                CommonConstants.PUBLISHED, PageRequest.of(0, 4,
                        new Sort(Sort.Direction.DESC, CommonConstants.PUBLISHDATEFIELD)));
        List<ImageResponse> resourceImageUrlList = new ArrayList<>();
        resourceOverviewList.forEach(one -> {
            try {
                String pictureListImage = pictureService.formImageUrl(one.getImage());
                List<Picture> pictureList = objectMapper.readValue(pictureListImage,
                        objectMapper.getTypeFactory().constructParametricType(List.class, Picture.class));
                resourceImageUrlList.add(new ImageResponse(one.getTitle(), pictureList.get(0).getUrl()));
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
