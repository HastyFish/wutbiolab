package com.gooalgene.wutbiolab.service.impl;

import com.gooalgene.wutbiolab.dao.ScientificResearchDetailDAO;
import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import com.gooalgene.wutbiolab.entity.scientificResearch.ScientificResearchDetail;
import com.gooalgene.wutbiolab.service.ScientificResearchDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScientificResearchDetailServiceImpl implements ScientificResearchDetailService {
    @Autowired
    private ScientificResearchDetailDAO scientificResearchDetailDAO;

    @Override
    public Page<ScientificResearchDetail> getLabDetailByLabCategoryId(Long scientificResearchCategoryId, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return scientificResearchDetailDAO.getByScientificResearchCategoryId(scientificResearchCategoryId, pageable);
    }

    @Override
    public ScientificResearchDetail getById(Long id){
        ScientificResearchDetail one = scientificResearchDetailDAO.getOne(id);
        return one;
    }

}
