package com.gooalgene.wutbiolab.service;

import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import org.springframework.data.domain.Page;

public interface LabService {

    Page<LabDetail> getLabDetailByLabCategoryId(Long category, Integer pageNum, Integer pageSize);
}
