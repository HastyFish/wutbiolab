package com.gooalgene.wutbiolab.service;

import com.gooalgene.wutbiolab.entity.home.CooperationLink;
import com.gooalgene.wutbiolab.entity.home.Footer;
import com.gooalgene.wutbiolab.request.HomeRequest;
import com.gooalgene.wutbiolab.response.HomeImageResponse;
import com.gooalgene.wutbiolab.response.common.CommonResponse;

import java.util.List;

public interface HomeService {

    CommonResponse<HomeImageResponse> getImages();

    /**
     * 保存首页信息
     */
    CommonResponse<Boolean> saveImages(HomeRequest homeRequest);

    CommonResponse<List<CooperationLink>> getCooperationLink();

    CommonResponse<Boolean> saveCooperationLink(HomeRequest homeRequest);

    CommonResponse<List<Footer>> getFooter();

    CommonResponse<Boolean> saveFooter(HomeRequest homeRequest);
}
