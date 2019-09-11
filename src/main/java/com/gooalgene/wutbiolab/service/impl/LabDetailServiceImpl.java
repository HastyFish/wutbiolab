package com.gooalgene.wutbiolab.service.impl;

import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.dao.LabDetailDAO;
import com.gooalgene.wutbiolab.entity.LabDetail;
import com.gooalgene.wutbiolab.service.LabDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabDetailServiceImpl implements LabDetailService {

    @Autowired
    private LabDetailDAO labDetailDAO;
    @Override
    public Page<LabDetail> getByLabCategoryId(Long labCategoryId, Integer pageNum, Integer pageSize) {
        if(pageNum==null&&pageSize==null){
            List<LabDetail> labDetails = labDetailDAO.getByLabCategoryId(labCategoryId);
            return new PageImpl<>(labDetails);
        }
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return labDetailDAO.getByLabCategoryId(labCategoryId, pageable);
    }

    public void save(LabDetail labDetail){
        labDetail.setPublishStatus(CommonConstants.UNPUBLISHED);
        labDetailDAO.save(labDetail);
    }
}
