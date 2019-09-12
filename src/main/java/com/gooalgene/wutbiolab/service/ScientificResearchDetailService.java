package com.gooalgene.wutbiolab.service;

import com.gooalgene.wutbiolab.entity.scientificResearch.ScientificResearchDetail;
import org.springframework.data.domain.Page;

public interface ScientificResearchDetailService {
    /**
     * 通过子模块类型获取对应分页数据
     * @param scientificResearchCategoryId
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<ScientificResearchDetail> getLabDetailByLabCategoryId(Long scientificResearchCategoryId, Integer pageNum, Integer pageSize);

    /**
     * 通过id获取一条数据
     * @param id
     * @return
     */
    ScientificResearchDetail getById(Long id);
}
