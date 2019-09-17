package com.gooalgene.wutbiolab.service.impl;

import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.dao.scientific.AcademicCategoryDAO;
import com.gooalgene.wutbiolab.dao.scientific.ScientificResearchCategoryDAO;
import com.gooalgene.wutbiolab.dao.scientific.ScientificResearchDetailDAO;
import com.gooalgene.wutbiolab.entity.scientificResearch.AcademicCategory;
import com.gooalgene.wutbiolab.entity.scientificResearch.ScientificResearchCategory;
import com.gooalgene.wutbiolab.entity.scientificResearch.ScientificResearchDetail;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.service.ScientificResearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ScientificResearchServiceImpl implements ScientificResearchService {
    @Autowired
    private ScientificResearchDetailDAO scientificResearchDetailDAO;
    @Autowired
    private ScientificResearchCategoryDAO scientificResearchCategoryDAO;
    @Autowired
    private AcademicCategoryDAO academicCategoryDAO;

    @Override
    public PageResponse<ScientificResearchDetail> getSRDetialByCategoryId(Long scientificResearchCategoryId, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        Page<ScientificResearchDetail> scientificResearchDetailPage = scientificResearchDetailDAO.getByScientificResearchCategoryId(scientificResearchCategoryId, pageable);
        long total = scientificResearchDetailPage.getTotalElements();
        List<ScientificResearchDetail> content = scientificResearchDetailPage.getContent();
        PageResponse<ScientificResearchDetail> pageResponse=new PageResponse();
        pageResponse.setList(content);
        pageResponse.setTotal(total);
        pageResponse.setPageSize(pageSize);
        pageResponse.setPageNum(pageNum);
        return pageResponse;
    }

    @Override
    public ScientificResearchDetail getById(Long id){
        Optional<ScientificResearchDetail> optional = scientificResearchDetailDAO.findById(id);
        return optional.orElse(null);
    }

    @Override
    public List<ScientificResearchCategory> getAllCategory() {
        return scientificResearchCategoryDAO.findAll();
    }


    @Override
    public List<AcademicCategory> getAllAcademicCategory(){
        return academicCategoryDAO.findAll();
    }

    @Override
    @Transactional
    public void saveOrPublish(ScientificResearchDetail scientificResearchDetail,Integer publishStatus){
        scientificResearchDetail.setPublishStatus(publishStatus);
        scientificResearchDetailDAO.save(scientificResearchDetail);
    }

    @Transactional
    public void deleteById(Long id){
        scientificResearchDetailDAO.deleteById(id);
    }


    /*********************************************** 前端使用 ***************************************************/

    @Override
    public PageResponse<ScientificResearchDetail> getPublishedByCategoryId(Long scientificResearchCategoryId, Integer pageNum, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        Page<ScientificResearchDetail> scientificResearchDetailPage = scientificResearchDetailDAO.getByScientificResearchCategoryIdAndPublishStatus(scientificResearchCategoryId,
                CommonConstants.PUBLISHED, pageable);
        long totalElements = scientificResearchDetailPage.getTotalElements();
        List<ScientificResearchDetail> content = scientificResearchDetailPage.getContent();
        PageResponse<ScientificResearchDetail> pageResponse=new PageResponse<>(content,pageNum,pageSize,totalElements);
        return pageResponse;
    }

    @Override
    public ScientificResearchDetail getPublishedById(Long id){
        ScientificResearchDetail one = scientificResearchDetailDAO.getByIdAndPublishStatus(id,CommonConstants.PUBLISHED);
        return one;
    }

}
