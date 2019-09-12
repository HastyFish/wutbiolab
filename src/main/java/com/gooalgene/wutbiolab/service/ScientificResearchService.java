package com.gooalgene.wutbiolab.service;

import com.gooalgene.wutbiolab.entity.scientificResearch.AcademicCategory;
import com.gooalgene.wutbiolab.entity.scientificResearch.ScientificResearchCategory;
import com.gooalgene.wutbiolab.entity.scientificResearch.ScientificResearchDetail;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ScientificResearchService {
    /**
     * 通过子模块类型获取对应分页数据
     * @param scientificResearchCategoryId
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<ScientificResearchDetail> getSRDetialByCategoryId(Long scientificResearchCategoryId, Integer pageNum, Integer pageSize);

    /**
     * 通过id获取一条数据
     * @param id
     * @return
     */
    ScientificResearchDetail getById(Long id);

    /**
     * 获取所有模块下分类
     * @return
     */
    List<ScientificResearchCategory> getAllCategory();


    /**
     * 获取所有学术分类
     * @return
     */
    List<AcademicCategory> getAllAcademicCategory();


    /**
     * 新增或编辑一条数据
     * @param scientificResearchDetail
     */
    void save(ScientificResearchDetail scientificResearchDetail);


    /*********************************************** 前端使用 ***************************************************/

}
