package com.gooalgene.wutbiolab.service;

import com.gooalgene.wutbiolab.entity.contactus.ContactusCategory;
import com.gooalgene.wutbiolab.entity.contactus.ContactusDetail;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.front.DetailPageResponse;

import java.util.List;

public interface ContactusService {

    void saveOrPublishLabDetail(ContactusDetail contactusDetail, Integer publishStatus);

    PageResponse<ContactusDetail> getContactusDetailByLabCategoryId(Long labCategoryId, Integer pageNum, Integer pageSize);





    /*********************************************** 前端使用 ***************************************************/


    List<ContactusCategory> getAllCategory();

    ContactusCategory getContactusCategoryById(Long id);

    DetailPageResponse<ContactusDetail> getContactusDetailByLabCategoryIdAndPublishStatus(Long labCategoryId, Integer pageNum, Integer pageSize, Integer publishStatus);
}
