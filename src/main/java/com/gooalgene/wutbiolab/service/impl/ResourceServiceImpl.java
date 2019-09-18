package com.gooalgene.wutbiolab.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooalgene.wutbiolab.dao.resource.ResourceCategoryDAO;
import com.gooalgene.wutbiolab.dao.resource.ResourceDetailDAO;
import com.gooalgene.wutbiolab.entity.Picture;
import com.gooalgene.wutbiolab.entity.resource.ResourceCategory;
import com.gooalgene.wutbiolab.entity.resource.ResourceDetail;
import com.gooalgene.wutbiolab.entity.resource.ResourceOverview;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.service.PictureService;
import com.gooalgene.wutbiolab.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {

    private ResourceDetailDAO resourceDetailDAO;

    private ResourceCategoryDAO resourceCategoryDAO;

    private PictureService pictureService;

    private Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);

    public ResourceServiceImpl(ResourceDetailDAO resourceDetailDAO, ResourceCategoryDAO resourceCategoryDAO,
                               PictureService pictureService) {
        this.pictureService = pictureService;
        this.resourceCategoryDAO = resourceCategoryDAO;
        this.resourceDetailDAO = resourceDetailDAO;

    }

    @Override
    public CommonResponse<List<ResourceCategory>> allResourceCategory() {
        return ResponseUtil.success(resourceCategoryDAO.findAll());
    }

    @Override
    public CommonResponse<PageResponse<ResourceOverview>> resourceDetailPage(Integer pageNum, Integer pageSize) {
//        Page<ResourceDetail> page = resourceDetailDAO.findAll(PageRequest.of(pageNum - 1, pageSize));
        Page<ResourceOverview> page = resourceDetailDAO.findNewsDetailBy(PageRequest.of(pageNum - 1, pageSize));
        return ResponseUtil.success(new PageResponse<>(page.getContent(), pageNum, pageSize, page.getTotalElements()));
    }

    @Override
    public CommonResponse<ResourceDetail> resourceDetailById(Integer id) {
        if (null != id && resourceDetailDAO.findById(id.longValue()).isPresent()) {
            ResourceDetail resourceDetail = resourceDetailDAO.findById(id.longValue()).get();
            resourceDetail.setImage(pictureService.formImageUrl(resourceDetail.getImage()));
            return ResponseUtil.success(resourceDetail);
        } else {
            return ResponseUtil.error("No such id:" + id);
        }
    }

    @Override
    public CommonResponse<Boolean> renewResourceDetail(ResourceDetail resourceDetail) {
        ObjectMapper objectMapper = new ObjectMapper();
        if (null != resourceDetail.getId()) {
            if (resourceDetailDAO.findById(resourceDetail.getId()).isPresent()) {
                ResourceDetail oldResourceDetail = resourceDetailDAO.findById(resourceDetail.getId()).get();
                try {
                    String newImage = pictureService.saveBase64(oldResourceDetail.getImage(), resourceDetail.getImage());
                    if (null != newImage) {
                        resourceDetail.setImage(newImage);
                    } else {
                        resourceDetail.setImage(objectMapper.writeValueAsString(new Picture()));
                        logger.error("fail to storge image");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("fail to storge image");
                }
                resourceDetailDAO.save(resourceDetail);
                return ResponseUtil.success(true);
            } else {
                logger.error("wrong id");
                return ResponseUtil.error("faild to save news");
            }
        } else {
            try {
                String newImage = pictureService.saveBase64(null, resourceDetail.getImage());
                if (null != newImage) {
                    resourceDetail.setImage(newImage);
                } else {
                    resourceDetail.setImage(objectMapper.writeValueAsString(new Picture()));
                    logger.error("fail to storge image");
                }
                resourceDetailDAO.save(resourceDetail);
                return ResponseUtil.success(true);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseUtil.error("fail to save news");
            }
        }
    }

    @Override
    public CommonResponse<Boolean> deleteResourceDetail(Integer id) {
        try {
            resourceDetailDAO.deleteById(id.longValue());
            return ResponseUtil.success(true);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error("id is null");
        }
    }
}
