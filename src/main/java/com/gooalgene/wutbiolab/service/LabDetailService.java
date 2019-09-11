package com.gooalgene.wutbiolab.service;

import com.gooalgene.wutbiolab.entity.LabDetail;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LabDetailService {

    Page<LabDetail> getByLabCategoryId(Long category, Integer pageNum, Integer pageSize);
}
