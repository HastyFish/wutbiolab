package com.gooalgene.wutbiolab.service.impl;

import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.dao.AcademicCategoryDAO;
import com.gooalgene.wutbiolab.dao.ScientificResearchCategoryDAO;
import com.gooalgene.wutbiolab.dao.ScientificResearchDetailDAO;
import com.gooalgene.wutbiolab.entity.scientificResearch.AcademicCategory;
import com.gooalgene.wutbiolab.entity.scientificResearch.ScientificResearchCategory;
import com.gooalgene.wutbiolab.entity.scientificResearch.ScientificResearchDetail;
import com.gooalgene.wutbiolab.service.ScientificResearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScientificResearchServiceImpl implements ScientificResearchService {
    @Autowired
    private ScientificResearchDetailDAO scientificResearchDetailDAO;
    @Autowired
    private ScientificResearchCategoryDAO scientificResearchCategoryDAO;
    @Autowired
    private AcademicCategoryDAO academicCategoryDAO;

    @Override
    public Page<ScientificResearchDetail> getSRDetialByCategoryId(Long scientificResearchCategoryId, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        return scientificResearchDetailDAO.getByScientificResearchCategoryId(scientificResearchCategoryId, pageable);
    }

    @Override
    public ScientificResearchDetail getById(Long id){
        ScientificResearchDetail one = scientificResearchDetailDAO.getOne(id);
        return one;
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


    /*********************************************** 前端使用 ***************************************************/

    @Override
    public Page<ScientificResearchDetail> getPublishedByCategoryId(Long scientificResearchCategoryId, Integer pageNum, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        return scientificResearchDetailDAO.getByScientificResearchCategoryIdAndPublishStatus(scientificResearchCategoryId,
                CommonConstants.PUBLISHED, pageable);
    }

    @Override
    public ScientificResearchDetail getPublishedById(Long id){
        ScientificResearchDetail one = scientificResearchDetailDAO.getByIdAndPublishStatus(id,CommonConstants.PUBLISHED);
        return one;
    }

}
