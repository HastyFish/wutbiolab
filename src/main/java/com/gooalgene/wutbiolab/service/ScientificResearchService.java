package com.gooalgene.wutbiolab.service;

import com.gooalgene.wutbiolab.entity.scientificResearch.AcademicCategory;
import com.gooalgene.wutbiolab.entity.scientificResearch.ScientificResearchCategory;
import com.gooalgene.wutbiolab.entity.scientificResearch.ScientificResearchDetail;
import com.gooalgene.wutbiolab.response.AcademicResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ScientificResearchService {
    /**
     * 通过子模块类型获取对应分页数据
     * @param scientificResearchCategoryId
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResponse<ScientificResearchDetail> getSRDetialByCategoryId(Long scientificResearchCategoryId, Integer pageNum, Integer pageSize);

    /**
     * 获取学术会议子模块分页列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResponse<AcademicResponse> getAcademicList(Integer pageNum, Integer pageSize);
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
     * 新增、编辑或发布一条数据
     * @param scientificResearchDetail
     */
    void saveOrPublish(ScientificResearchDetail scientificResearchDetail,Integer publishStatus);

    void deleteById(Long id);

    ScientificResearchCategory getScientificResearchCategoryById(Long id);

    PageResponse<ScientificResearchDetail> getScientificResearchDetailByLabCategoryId(Long labCategoryId, Integer pageNum, Integer pageSize);

    /*********************************************** 前端使用 ***************************************************/


    PageResponse<ScientificResearchDetail> getScientificResearchDetailByLabCategoryIdAndPublishStatus(Long labCategoryId, Integer pageNum, Integer pageSize, Integer publishStatus);
    /**
     * 通过子模块类型获取对应已发布的分页数据
     * @param scientificResearchCategoryId
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResponse<ScientificResearchDetail> getPublishedByCategoryId(Long scientificResearchCategoryId, Integer pageNum, Integer pageSize);
    /**
     * 通过id查询一条已发布的数据
     * @param id
     * @return
     */
    Map<String,Object> getPublishedById(Long id);

    ScientificResearchCategory getCategoryById(Long categoryId);

    /**
     * 通过时间段查询论文
     * @param pageNum
     * @param pageSize
     * @param beginDate
     * @param endDate
     * @return
     */
    PageResponse<ScientificResearchDetail> getSRDetialByDate(Integer pageNum, Integer pageSize, String beginDate, String endDate);

    /**
     * 通过发布状态查询论文
     * @param pageNum
     * @param pageSize
     * @param status
     * @return
     */
    PageResponse<ScientificResearchDetail> getSRDetialByStatus(Integer pageNum, Integer pageSize, Integer status);

    /**
     * 通过标题模糊查询论文
     * @param pageNum
     * @param pageSize
     * @param title
     * @return
     */
    PageResponse<ScientificResearchDetail> getSRDetialByTitle(Integer pageNum, Integer pageSize, String title);

    /**
     * 通过刊物名称模糊查询论文
     * @param pageNum
     * @param pageSize
     * @param periodicalName
     * @return
     */
    PageResponse<ScientificResearchDetail> getSRDetialByPeriodicalName(Integer pageNum, Integer pageSize, String periodicalName);

    /**
     * 通过作者模糊查询论文
     * @param pageNum
     * @param pageSize
     * @param author
     * @return
     */
    PageResponse<ScientificResearchDetail> getSRDetialByAuthor(Integer pageNum, Integer pageSize, String author);
}
