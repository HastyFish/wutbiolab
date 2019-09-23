package com.gooalgene.wutbiolab.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.dao.resource.ResourceCategoryDAO;
import com.gooalgene.wutbiolab.dao.resource.ResourceDetailDAO;
import com.gooalgene.wutbiolab.entity.Picture;
import com.gooalgene.wutbiolab.entity.common.CommonOverview;
import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import com.gooalgene.wutbiolab.entity.resource.ResourceCategory;
import com.gooalgene.wutbiolab.entity.resource.ResourceDetail;
import com.gooalgene.wutbiolab.entity.resource.ResourceOverview;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.response.front.DetailPageResponse;
import com.gooalgene.wutbiolab.service.PictureService;
import com.gooalgene.wutbiolab.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResourceServiceImpl implements ResourceService {

    private ResourceDetailDAO resourceDetailDAO;

    private ResourceCategoryDAO resourceCategoryDAO;

    private PictureService pictureService;

    private ObjectMapper objectMapper;

    @PersistenceContext
    private EntityManager entityManager;

    private Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);

    public ResourceServiceImpl(ResourceDetailDAO resourceDetailDAO, ResourceCategoryDAO resourceCategoryDAO,
                               PictureService pictureService, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
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
        /*新增或保存时，查询前端返回的categoryId对应的category*/
        if (resourceCategoryDAO.findById(resourceDetail.getCategoryId()).isPresent()) {
            ResourceCategory resourceCategory = resourceCategoryDAO.findById(
                    resourceDetail.getCategoryId()).get();
            resourceDetail.setCategory(resourceCategory.getCategory());
        } else {
            return ResponseUtil.error("Wrong category");
        }
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




    @Override
    public Map<String,ResourceDetail> getPublishedById(Long id){
        Map<String,ResourceDetail> map=new HashMap<>();
        ResourceDetail resourceDetail = resourceDetailDAO.getByIdAndPublishStatus(id, CommonConstants.PUBLISHED);
        if(resourceDetail!=null){
            Long categoryId = resourceDetail.getCategoryId();
            Long publishDate = resourceDetail.getPublishDate();
            ResourceDetail pre = getOneByPublishDate(publishDate,categoryId, ">");
            ResourceDetail next = getOneByPublishDate(publishDate,categoryId, "<");
            map.put("detail",resourceDetail);
            map.put("previous",pre);
            map.put("next",next);
        }
        return map;
    }

    private ResourceDetail getOneByPublishDate(Long publishDate,Long categoryId, String operation) {
        String sql="select rd.id,rd.title from resource_detail rd where  rd.publishDate "+operation+
                " :publishDate and rd.publishStatus=1 and rd.categoryId=:categoryId ORDER BY publishDate limit 1";
        Query nativeQuery = entityManager.createNativeQuery(sql);
        nativeQuery.setParameter("publishDate",publishDate);
        nativeQuery.setParameter("categoryId",categoryId);
        Object object = null;
        try {
            object = nativeQuery.getSingleResult();
        } catch (NoResultException e) {
            logger.info("publishDate为：{}是最后一条数据了",publishDate);
            return null;
        }
        Object[] objects = (Object[])object;
        ResourceDetail resourceDetail=new ResourceDetail();
        BigInteger idBigInt = (BigInteger) objects[0];
        if(idBigInt!=null){
            resourceDetail.setId(idBigInt.longValue());
        }
        String title = (String) objects[1];
        resourceDetail.setTitle(title);
        return resourceDetail;
    }
    @Override
    public PageResponse<ResourceOverview> getByPublishStatus(Long categoryId,Integer publishStatus,
                                                             Integer pageNum, Integer pageSize){
        Page<ResourceOverview> resourceOverviewPage =
                resourceDetailDAO.findNewsDetailByPublishStatus(categoryId,publishStatus, PageRequest.of(pageNum - 1, pageSize));
        List<ResourceOverview> content = resourceOverviewPage.getContent();
        String category=null;
        if(content!=null&&!content.isEmpty()){
            category=content.get(0).getCategory();
        }
        return new DetailPageResponse<>(content,pageNum,pageSize,resourceOverviewPage.getTotalElements(),category);
    }
}
