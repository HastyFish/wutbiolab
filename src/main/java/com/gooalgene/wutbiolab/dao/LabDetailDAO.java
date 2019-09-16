package com.gooalgene.wutbiolab.dao;

import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LabDetailDAO extends JpaRepository<LabDetail,Long> {

//    List<LabDetail> getByLabCategoryId(Long labCategoryId);
//    Page<LabDetail> getByLabCategoryId(Long labCategoryId, Pageable pageable);

    @Query(value = "SELECT ld.id,gc.categoryName,ld.title,ld.publishDate,ld.publishStatus " +
            " FROM lab_detail ld JOIN graduate_category gc ON ld.graduateCategoryId=gc.id LIMIT :pageNum,:pageSize",
            nativeQuery = true)
    List<Object[]> getGraduates(@Param("pageNum")Integer pageNum,@Param("pageSize")Integer pageSize);

    @Query(value = "SELECT count(1) FROM lab_detail ld JOIN graduate_category gc ON ld.graduateCategoryId=gc.id",nativeQuery = true)
    Long getGraduatesCount();

    List<LabDetail> getByLabCategoryIdAndPublishStatus(Long labCategoryId,Integer publishStatus);
    Page<LabDetail> getByLabCategoryIdAndPublishStatus(Long labCategoryId,Integer publishStatus, Pageable pageable);

    @Query("select new LabDetail(labDetail.id,labDetail.title,labDetail.publishDate) from LabDetail labDetail " +
            "where labDetail.labCategoryId=:labCategoryId and labDetail.publishStatus=:publishStatus")
    Page<LabDetail> getListByLabCategoryIdAndPublishStatus(@Param("labCategoryId") Long labCategoryId,
                                                           @Param("publishStatus") Integer publishStatus, Pageable pageable);

    @Query(value = "SELECT ld.id,mc.id mentorCategoryId,ld.mentorName,ld.mentorOrder,mc.categoryName FROM lab_detail ld " +
            " RIGHT JOIN mentor_category mc ON ld.mentorCategoryId=mc.id " +
            " order by mentorCategoryId, ld.mentorOrder",nativeQuery = true)
    List<Object[]> getResearchTeam();

    @Query(value = "SELECT ld.id,mc.id mentorCategoryId,ld.mentorName,ld.mentorOrder,mc.categoryName FROM lab_detail ld " +
            " RIGHT JOIN mentor_category mc ON ld.mentorCategoryId=mc.id where ld.publishStatus=:publishStatus" +
            " order by mentorCategoryId, ld.mentorOrder",nativeQuery = true)
    List<Object[]> getResearchTeamByPublishStatus(@Param("publishStatus") Integer publishStatus);



    LabDetail getByIdAndPublishStatus(Long id,Integer publishStatus);


    List<LabDetail> getByIdIn(List<Long> ids);
}
