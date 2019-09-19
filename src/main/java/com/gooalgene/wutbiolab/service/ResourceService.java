package com.gooalgene.wutbiolab.service;

import com.gooalgene.wutbiolab.entity.resource.ResourceCategory;
import com.gooalgene.wutbiolab.entity.resource.ResourceDetail;
import com.gooalgene.wutbiolab.entity.resource.ResourceOverview;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;

import java.util.List;

public interface ResourceService {
    CommonResponse<List<ResourceCategory>> allResourceCategory();

    CommonResponse<PageResponse<ResourceOverview>> resourceDetailPage(Integer pageNum, Integer pageSize);

    CommonResponse<ResourceDetail> resourceDetailById(Integer id);

    CommonResponse<Boolean> renewResourceDetail(ResourceDetail resourceDetail);

    CommonResponse<Boolean> deleteResourceDetail(Integer id);

    /**
     * 通过状态查询分页列表
     * @param publishStatus
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResponse<ResourceOverview> getByPublishStatus(Integer publishStatus,Integer pageNum, Integer pageSize);
}
