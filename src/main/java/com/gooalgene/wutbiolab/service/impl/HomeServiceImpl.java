package com.gooalgene.wutbiolab.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.dao.home.AcademicImageDAO;
import com.gooalgene.wutbiolab.dao.home.CooperationLinkDAO;
import com.gooalgene.wutbiolab.dao.home.FooterDAO;
import com.gooalgene.wutbiolab.dao.home.NewsImageDAO;
import com.gooalgene.wutbiolab.entity.home.AcademicImage;
import com.gooalgene.wutbiolab.entity.home.CooperationLink;
import com.gooalgene.wutbiolab.entity.home.Footer;
import com.gooalgene.wutbiolab.entity.home.NewsImage;
import com.gooalgene.wutbiolab.request.HomeRequest;
import com.gooalgene.wutbiolab.response.HomeImageResponse;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.service.HomeService;
import com.gooalgene.wutbiolab.service.PictureService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {

    private AcademicImageDAO academicImageDAO;

    private NewsImageDAO newsImageDAO;

    private CooperationLinkDAO cooperationLinkDAO;

    private FooterDAO footerDAO;

    private PictureService pictureService;

    private ObjectMapper objectMapper;

    public HomeServiceImpl(AcademicImageDAO academicImageDAO, NewsImageDAO newsImageDAO,
                           CooperationLinkDAO cooperationLinkDAO, FooterDAO footerDAO,
                           PictureService pictureService, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
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
    public CommonResponse<Boolean> saveImages(HomeRequest homeRequest) {
        if (null != homeRequest.getNewsImage()) {
            NewsImage newsImage = new NewsImage();
            newsImage.setContext(pictureService.saveBase64(null, homeRequest.getNewsImage()));
            newsImage.setPublishStatus(CommonConstants.PUBLISHED);
            newsImageDAO.save(newsImage);
        }
        if (null != homeRequest.getAcademicImage()) {
            AcademicImage academicImage = new AcademicImage();
            academicImage.setContext(pictureService.saveBase64(null, homeRequest.getAcademicImage()));
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
}
