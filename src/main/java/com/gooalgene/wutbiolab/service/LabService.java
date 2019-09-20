package com.gooalgene.wutbiolab.service;

import com.gooalgene.wutbiolab.entity.lab.GraduateCategory;
import com.gooalgene.wutbiolab.entity.lab.LabCategory;
import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import com.gooalgene.wutbiolab.entity.lab.MentorCategory;
import com.gooalgene.wutbiolab.request.MentorRequest;
import com.gooalgene.wutbiolab.response.GraduateResponse;
import com.gooalgene.wutbiolab.response.MentorResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface LabService {


    /**
     * 目前针对机构概况和研究方向，所以查了所有字段（日后加了说不定要改）
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @param isList
     * @return
     */
    PageResponse<LabDetail> getLabDetailByLabCategoryId(Long categoryId, Integer pageNum, Integer pageSize, Boolean isList);

    /**
     * 获取毕业生分页列表
     *
     */
    PageResponse<GraduateResponse> getGraduates(Integer pageNum, Integer pageSize);
    /**
     * 通过id获取一条数据
     * @param id
     * @return
     */
    LabDetail getById(Long id);


    /**
     * 针对研究团队模块获取其数据
     * @return
     */
    List<MentorResponse> getResearchTeam();

    /**
     * 保存多条数据
     * @param labDetails
     */
    void saveList(List<LabDetail> labDetails);

    /**
     * 保存或发布模块的一条数据
     * @param labDetail
     */
    void saveOrPublishLabDetail(LabDetail labDetail,Integer publishStatus);



//    void publishResearchTeam(List<MentorRequest> mentorRequests);

    /**
     * 通过一级分类id进行发布
     * @param labCategoryId
     */
    void publishByLabCategoryId(Long labCategoryId);



    /**
     * 保存导师类型
     * @param mentorCategorys
     */
    void saveMentorCategory(List<MentorCategory> mentorCategorys);

    /**
     * 获取所有导师分类
     * @return
     */
    List<MentorCategory> getMentorCategorys();
    /**
     *  获取毕业生模块的所有类型
     * @return
     */
    List<GraduateCategory> getGraduateCategorys();

    /**
     * 获取模块下所有分类
     * @return
     */
    List<LabCategory> getAllCategory();

    /**
     * 通过id删除一条记录
     * @param id
     */
    void deleteById(Long id);

    /**
     * 通过id删除一条导师类型数据
     * @param id
     */
    void deleteMentorCategoryById(Long id);

    /*********************************************** 前端使用 ***************************************************/

    /**
     * 通过id和发布状态查询一行数据
     * @return
     */
    Map<String,LabDetail> getPublishedById(Long id);

    /**
     * 获取已发布的研究团队的数据
     * @return
     */
    List<MentorResponse> getPublishedResearchTeam();


    /**
     * 通过分类id查找分类
     * @param labCategoryId
     * @return
     */
    LabCategory getCategoryById(Long labCategoryId);
    /**
     * 通过模块分类id及发布状态查询数据
     * @param labCategoryId
     * @param pageNum
     * @param pageSize
     * @param publishStatus
     * @return
     */
    PageResponse<LabDetail> getLabDetailByLabCategoryIdAndPublishStatus(Long labCategoryId,Integer pageNum,
                                                                Integer pageSize,Integer publishStatus,Boolean isList);
}
