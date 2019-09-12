package com.gooalgene.wutbiolab.service.impl;

import com.gooalgene.wutbiolab.dao.resource.ResourceCategoryDAO;
import com.gooalgene.wutbiolab.dao.resource.ResourceDetailDAO;
import com.gooalgene.wutbiolab.entity.resource.ResourceCategory;
import com.gooalgene.wutbiolab.entity.resource.ResourceDetail;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.service.ResourceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {

    private ResourceDetailDAO resourceDetailDAO;

    private ResourceCategoryDAO resourceCategoryDAO;

    public ResourceServiceImpl(ResourceDetailDAO resourceDetailDAO, ResourceCategoryDAO resourceCategoryDAO) {
        this.resourceCategoryDAO = resourceCategoryDAO;
        this.resourceDetailDAO = resourceDetailDAO;

    }

    @Override
    public CommonResponse<List<ResourceCategory>> allNewsCategory() {
        return ResponseUtil.success(resourceCategoryDAO.findAll());
    }

    @Override
    public CommonResponse<PageResponse<ResourceDetail>> resourceDetailPage(Integer pageNum, Integer pageSize) {
        Page<ResourceDetail> page = resourceDetailDAO.findAll(PageRequest.of(pageNum - 1, pageSize));
        return ResponseUtil.success(new PageResponse<>(page.getContent(), pageNum, pageSize, page.getTotalElements()));
    }

    @Override
    public CommonResponse<ResourceDetail> resourceDetailById(Integer id) {
        if (null != id && resourceDetailDAO.findById(id.longValue()).isPresent()) {
            return ResponseUtil.success(resourceDetailDAO.findById(id.longValue()).get());
        } else {
            return ResponseUtil.error("No such id:" + id);
        }
    }
}
