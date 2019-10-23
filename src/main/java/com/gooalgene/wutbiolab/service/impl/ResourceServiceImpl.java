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
import com.gooalgene.wutbiolab.exception.WutbiolabException;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.response.common.ResultCode;
import com.gooalgene.wutbiolab.response.front.DetailPageResponse;
import com.gooalgene.wutbiolab.service.CommonService;
import com.gooalgene.wutbiolab.service.PictureService;
import com.gooalgene.wutbiolab.service.ResourceService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import java.util.Optional;

@Service
public class ResourceServiceImpl implements ResourceService {

    private ResourceDetailDAO resourceDetailDAO;

    private ResourceCategoryDAO resourceCategoryDAO;

    private PictureService pictureService;

    private ObjectMapper objectMapper;

    @PersistenceContext
    private EntityManager entityManager;

    private CommonService commonService;

    private Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);

    public ResourceServiceImpl(ResourceDetailDAO resourceDetailDAO, ResourceCategoryDAO resourceCategoryDAO,
                               PictureService pictureService, ObjectMapper objectMapper, CommonService commonService) {
        this.objectMapper = objectMapper;
        this.pictureService = pictureService;
        this.resourceCategoryDAO = resourceCategoryDAO;
        this.resourceDetailDAO = resourceDetailDAO;
        this.commonService = commonService;

    }


    @Override
    public CommonResponse<List<ResourceCategory>> allResourceCategory() {
        return ResponseUtil.success(resourceCategoryDAO.findAll(Sort.by(CommonConstants.ORDER_CATEGORY)));
    }

    @Override
    public CommonResponse<PageResponse<ResourceOverview>> resourceDetailPage(Integer pageNum, Integer pageSize,
                                                                             Long categoryId) {
//        Page<ResourceDetail> page = resourceDetailDAO.findAll(PageRequest.of(pageNum - 1, pageSize));
        Page<ResourceOverview> page;
        Sort.Order daterder = new Sort.Order(Sort.Direction.DESC, CommonConstants.PUBLISHDATEFIELD);
        Sort.Order idOrder = new Sort.Order(Sort.Direction.DESC, CommonConstants.IDFIELD);
        Sort.Order categoryOrder = new Sort.Order(Sort.Direction.ASC, CommonConstants.CATEGORYIDFIELD);
        if (null == categoryId) {
            page = resourceDetailDAO.findNewsDetailBy(PageRequest.of(pageNum - 1, pageSize,
                    Sort.by(daterder, categoryOrder, idOrder)));
        } else {
            page = resourceDetailDAO.findNewsDetailByCategoryId(categoryId, PageRequest.of(pageNum - 1, pageSize,
                    Sort.by(daterder, categoryOrder, idOrder)));
        }
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
    public Map<String, Object> getPublishedById(Long id) {
        Map<String, Object> map = new HashMap<>();
        ResourceDetail resourceDetail = resourceDetailDAO.getByIdAndPublishStatus(id, CommonConstants.PUBLISHED);
        if (resourceDetail != null) {
            Long categoryId = resourceDetail.getCategoryId();
            Long publishDate = resourceDetail.getPublishDate();
            String countSql = "select count(1) from  resource_detail rd  where rd.publishDate =:publishDate " +
                    " and rd.categoryId=:categoryId  and rd.publishStatus=" + CommonConstants.PUBLISHED;
            Object singleResult = entityManager.createNativeQuery(countSql)
                    .setParameter("publishDate", publishDate)
                    .setParameter("categoryId", categoryId)
                    .getSingleResult();
            BigInteger bigInteger = (BigInteger) singleResult;
            long count = bigInteger == null ? 0 : bigInteger.longValue();


//            ResourceDetail pre = getOneByPublishDate(count,id,categoryId,publishDate, ">","asc");
//            ResourceDetail next = getOneByPublishDate(count,id,categoryId,publishDate, "<","desc");
            ResourceDetail pre = commonService.getOneByPublishDateAndId(ResourceDetail.class, count, id, categoryId, publishDate, ">", "asc");
            ResourceDetail next = commonService.getOneByPublishDateAndId(ResourceDetail.class, count, id, categoryId, publishDate, "<", "desc");
            map.put("detail", resourceDetail);
            map.put("previous", pre);
            map.put("next", next);
            map.put("firstCategory", CommonConstants.CATEGORY_RESOURCE);
        }
        return map;
    }

    private ResourceDetail getOneByPublishDate(Long count, Long id, Long categoryId, Long publishDate, String operation, String sort) {
        Query nativeQuery = null;
        if (count > 1) {
            String operationAndEq = operation.concat("=");
            String sql = "select rd.id,rd.title from resource_detail rd where  rd.publishDate " + operationAndEq +
                    " :publishDate and rd.publishStatus=1 and rd.categoryId=:categoryId and rd.id" + operation + ":id " +
                    " ORDER BY rd.publishDate " + sort + ",rd.id " + sort + " limit 1";
            nativeQuery = entityManager.createNativeQuery(sql);
            nativeQuery.setParameter("id", id);
        } else {
            String sql = "select rd.id,rd.title from resource_detail rd where  rd.publishDate " + operation +
                    " :publishDate and rd.publishStatus=1 and rd.categoryId=:categoryId " +
                    " ORDER BY rd.publishDate " + sort + ",rd.id " + sort + " limit 1";
            nativeQuery = entityManager.createNativeQuery(sql);
        }

        nativeQuery.setParameter("publishDate", publishDate);
        nativeQuery.setParameter("categoryId", categoryId);
        Object object = null;
        try {
            object = nativeQuery.getSingleResult();
        } catch (NoResultException e) {
            logger.info("publishDate为：{}是最后一条数据了", publishDate);
            return null;
        }
        Object[] objects = (Object[]) object;
        ResourceDetail resourceDetail = new ResourceDetail();
        BigInteger idBigInt = (BigInteger) objects[0];
        if (idBigInt != null) {
            resourceDetail.setId(idBigInt.longValue());
        }
        String title = (String) objects[1];
        resourceDetail.setTitle(title);
        return resourceDetail;
    }

    @Override
    public PageResponse<ResourceOverview> getByPublishStatus(Long categoryId, Integer publishStatus,
                                                             Integer pageNum, Integer pageSize) {
        Page<ResourceOverview> resourceOverviewPage =
                resourceDetailDAO.findNewsDetailByPublishStatus(categoryId, publishStatus, PageRequest.of(pageNum - 1, pageSize));
        List<ResourceOverview> content = resourceOverviewPage.getContent();
        String category = null;
        if (content != null && !content.isEmpty()) {
            category = content.get(0).getCategory();
        } else {
            Optional<ResourceCategory> optionalResourceCategory = resourceCategoryDAO.findById(categoryId);
            if (optionalResourceCategory.isPresent()) {
                category = optionalResourceCategory.get().getCategory();
            }
        }
        return new DetailPageResponse<>(content, pageNum, pageSize, resourceOverviewPage.getTotalElements(), category);
    }
}
