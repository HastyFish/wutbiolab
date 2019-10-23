package com.gooalgene.wutbiolab.service.impl;

import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.dao.contactus.ContactusCategoryDAO;
import com.gooalgene.wutbiolab.dao.contactus.ContactusDetailDAO;
import com.gooalgene.wutbiolab.entity.contactus.ContactusCategory;
import com.gooalgene.wutbiolab.entity.contactus.ContactusDetail;
import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import com.gooalgene.wutbiolab.entity.scientificResearch.ScientificResearchCategory;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.front.DetailPageResponse;
import com.gooalgene.wutbiolab.service.ContactusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class ContactusServiceImpl implements ContactusService {


    @Autowired
    private ContactusDetailDAO contactusDetailDAO;

    @Override
    public void saveOrPublishLabDetail(ContactusDetail contactusDetail, Integer publishStatus) {
        contactusDetail.setPublishStatus(publishStatus);
        ContactusDetail save = contactusDetailDAO.save(contactusDetail);
    }


    @Override
    public PageResponse<ContactusDetail> getContactusDetailByLabCategoryId(Long labCategoryId, Integer pageNum, Integer pageSize) {
        if (pageNum == null && pageSize == null) {
            List<ContactusDetail> labDetails = contactusDetailDAO.findByCategoryId(labCategoryId);
            return new PageResponse<ContactusDetail>(labDetails);
        }
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<ContactusDetail> labDetailPage = contactusDetailDAO.findByCategoryId(labCategoryId, pageable);
        long totalElements = labDetailPage.getTotalElements();
        List<ContactusDetail> content = labDetailPage.getContent();
        PageResponse<ContactusDetail> pageResponse = new PageResponse<>(content, pageNum, pageSize, totalElements);
        return pageResponse;
    }






    /*********************************************** 前端使用 ***************************************************/



    @Autowired
    private ContactusCategoryDAO contactusCategoryDAO;

    @Override
    public List<ContactusCategory> getAllCategory(){
        return contactusCategoryDAO.findAll(Sort.by(CommonConstants.ORDER_CATEGORY));
    }

    @Override
    public ContactusCategory getContactusCategoryById(Long id){
        Optional<ContactusCategory> optional = contactusCategoryDAO.findById(id);
        return optional.orElse(null);
    }


    @Override
    public DetailPageResponse<ContactusDetail> getContactusDetailByLabCategoryIdAndPublishStatus(Long labCategoryId,Integer pageNum, Integer pageSize,Integer publishStatus) {
        Page<ContactusDetail> labDetailPage = null;
        //非list情况，针对机构概况和研究方向
        if (pageNum == null && pageSize == null) {
            List<ContactusDetail> contactusDetails = contactusDetailDAO.findByCategoryIdAndPublishStatus(labCategoryId, publishStatus);
            labDetailPage = new PageImpl<>(contactusDetails);
        } else {
            Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
            labDetailPage = contactusDetailDAO.findByCategoryIdAndPublishStatus(labCategoryId, publishStatus, pageable);
        }
        if (labDetailPage != null) {
            List<ContactusDetail> content = labDetailPage.getContent();
            long totalElements = labDetailPage.getTotalElements();
            DetailPageResponse<ContactusDetail> pageResponse = new DetailPageResponse<>(content, pageNum, pageSize, totalElements);
            return pageResponse;
        }
        return null;
    }

}
