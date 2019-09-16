package com.gooalgene.wutbiolab.service;

import com.gooalgene.wutbiolab.entity.resource.ResourceCategory;
import com.gooalgene.wutbiolab.entity.resource.ResourceDetail;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;

import java.util.List;

public interface ResourceService {
    CommonResponse<List<ResourceCategory>> allNewsCategory();

    CommonResponse<PageResponse<ResourceDetail>> resourceDetailPage(Integer pageNum, Integer pageSize);

    CommonResponse<ResourceDetail> resourceDetailById(Integer id);
}
