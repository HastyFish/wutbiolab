package com.gooalgene.wutbiolab.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooalgene.wutbiolab.dao.home.AcademicImageDAO;
import com.gooalgene.wutbiolab.dao.home.CooperationLinkDAO;
import com.gooalgene.wutbiolab.dao.home.FooterDAO;
import com.gooalgene.wutbiolab.dao.home.NewsImageDAO;
import com.gooalgene.wutbiolab.entity.Picture;
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



    public HomeServiceImpl(AcademicImageDAO academicImageDAO, NewsImageDAO newsImageDAO,
                           CooperationLinkDAO cooperationLinkDAO, FooterDAO footerDAO,
                           PictureService pictureService) {
        this.pictureService = pictureService;
        this.academicImageDAO = academicImageDAO;
        this.newsImageDAO = newsImageDAO;
        this.cooperationLinkDAO = cooperationLinkDAO;
        this.footerDAO = footerDAO;
    }

    @Override
    public CommonResponse<HomeImageResponse> getImages() {
        NewsImage newsImage = newsImageDAO.findAll().get(0);
        AcademicImage academicImage = academicImageDAO.findAll().get(0);
        return ResponseUtil.success(new HomeImageResponse(academicImage, newsImage));
    }

    @Override
    @Transactional
    public CommonResponse<Boolean> saveImages(HomeRequest homeRequest) {
        NewsImage newsImage = new NewsImage();
        newsImage.setContext(pictureService.saveBase64(null, homeRequest.getNewsImage()));
        newsImageDAO.save(newsImage);
        AcademicImage academicImage = new AcademicImage();
        academicImage.setContext(pictureService.saveBase64(null, homeRequest.getNewsImage()));
        academicImageDAO.save(academicImage);
        return ResponseUtil.success(true);
    }

    @Override
    public CommonResponse<List<CooperationLink>> getCooperationLink() {
        return null;
    }

    @Override
    public CommonResponse<Boolean> saveCooperationLink(HomeRequest homeRequest) {
        return null;
    }

    @Override
    public CommonResponse<List<Footer>> getFooter() {
        return null;
    }

    @Override
    public CommonResponse<Boolean> saveFooter(HomeRequest homeRequest) {
        return null;
    }
}
