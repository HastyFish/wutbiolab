package com.gooalgene.wutbiolab.dao;

import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LabDetailDAO extends JpaRepository<LabDetail,Long> {

    List<LabDetail> getByLabCategoryId(Long labCategoryId);
    Page<LabDetail> getByLabCategoryId(Long labCategoryId, Pageable pageable);

    @Query(value = "SELECT ld.id,mc.id mentorCategoryId,ld.mentorName,ld.mentorOrder,mc.categoryName FROM lab_detail ld " +
            " RIGHT JOIN mentor_category mc ON ld.mentorCategoryId=mc.id " +
            " order by mentorCategoryId, ld.mentorOrder",nativeQuery = true)
    List<Object[]> getResearchTeam();

    @Query(value = "SELECT ld.id,mc.id mentorCategoryId,ld.mentorName,ld.mentorOrder,mc.categoryName FROM lab_detail ld " +
            " RIGHT JOIN mentor_category mc ON ld.mentorCategoryId=mc.id where ld.publishStatus=#{publishStatus}" +
            " order by mentorCategoryId, ld.mentorOrder",nativeQuery = true)
    List<Object[]> getResearchTeam(@Param("publishStatus") Integer publishStatus);

    List<LabDetail> getByPublishStatus(Integer publishStatus);

    Page<LabDetail> getByPublishStatus(Integer publishStatus,Pageable pageable);
}
