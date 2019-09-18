package com.gooalgene.wutbiolab.service;

import com.gooalgene.wutbiolab.entity.home.CooperationLink;
import com.gooalgene.wutbiolab.entity.home.Footer;
import com.gooalgene.wutbiolab.request.HomeImageRequest;
import com.gooalgene.wutbiolab.response.HomeImageResponse;
import com.gooalgene.wutbiolab.response.common.CommonResponse;

import java.util.List;
import java.util.Map;

public interface HomeService {

    /**
     * 后台获取新闻动态图片和学术图片
     */
    CommonResponse<HomeImageResponse> getImages();

    /**
     * 后台保存首页新闻动态图片和学术图片
     */
    CommonResponse<Boolean> saveImages(HomeImageRequest homeImageRequest);

    /**
     * 后台获取友情链接
     */
    CommonResponse<List<CooperationLink>> getCooperationLink();

    /**
     * 后台保存友情链接
     */
    CommonResponse<Boolean> saveCooperationLink(List<CooperationLink> cooperationLinkList);

    /**
     * 后台获取页脚信息
     */
    CommonResponse<List<Footer>> getFooter();

    /**
     * 后台保存页脚信息
     */
    CommonResponse<Boolean> saveFooter(List<Footer> footerList);

    /**
     * 后台删除友情链接
     */
    CommonResponse<Boolean> deleteCooperationLinkById(long id);

    /**
     * 后台后台删除页脚
     */
    CommonResponse<Boolean> deleteFooterById(long id);

    /**
     * 前台获取轮播图
     */
    CommonResponse<List<String>> displayNewsSlideShow();

    /**
     * 前台展示页脚
     * @return 已发布的页脚
     */
    CommonResponse<List<Footer>> displayFooter();

    /**
     * 前台展示首页信息
     */
    CommonResponse<Map<String, Object>> displayHomeInfo();
}
