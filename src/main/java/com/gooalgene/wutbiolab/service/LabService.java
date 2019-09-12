package com.gooalgene.wutbiolab.service;

import com.gooalgene.wutbiolab.entity.lab.GraduateCategory;
import com.gooalgene.wutbiolab.entity.lab.LabCategory;
import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import com.gooalgene.wutbiolab.entity.lab.MentorCategory;
import com.gooalgene.wutbiolab.request.MentorRequest;
import com.gooalgene.wutbiolab.response.MentorResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LabService {

    /**
     * 通过模块类型的id获取对应模块的相关数据
     * @param category
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<LabDetail> getLabDetailByLabCategoryId(Long category, Integer pageNum, Integer pageSize);

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
     * 保存或发布模块的一条数据
     * @param labDetail
     */
    void saveOrPublishLabDetail(LabDetail labDetail,Integer publishStatus);


    /**
     * 针对研究团队模块发布所有数据
     * @param mentorRequests
     */
    void publishResearchTeam(List<MentorRequest> mentorRequests);


    /**
     * 保存导师类型
     * @param mentorCategory
     */
    void saveMentorCategory(MentorCategory mentorCategory);

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

    /*********************************************** 前端使用 ***************************************************/

    /**
     * 通过id和发布状态查询一行数据
     * @return
     */
    LabDetail getPublishedById(Long id);

    /**
     * 获取已发布的研究团队的数据
     * @return
     */
    List<MentorResponse> getPublishedResearchTeam();

    /**
     * 通过模块分类id及发布状态查询数据
     * @param labCategoryId
     * @param pageNum
     * @param pageSize
     * @param publishStatus
     * @return
     */
    Page<LabDetail> getLabDetailByLabCategoryIdAndPublishStatus(Long labCategoryId,Integer pageNum,
                                                                Integer pageSize,Integer publishStatus,Boolean isList);
}
