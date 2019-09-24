package com.gooalgene.wutbiolab.dao.lab;

import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LabDetailDAO extends JpaRepository<LabDetail,Long> {

    List<LabDetail> getByCategoryId(Long labCategoryId);
    Page<LabDetail> getByCategoryId(Long labCategoryId, Pageable pageable);

    @Query(value = "SELECT ld.id,gc.category,ld.title,ld.publishDate,ld.publishStatus " +
            " FROM lab_detail ld JOIN all_category gc ON ld.graduateCategoryId=gc.id " +
            " where gc.discriminator='graduate' LIMIT :pageNum,:pageSize",
            nativeQuery = true)
    List<Object[]> getGraduates(@Param("pageNum")Integer pageNum,@Param("pageSize")Integer pageSize);

    @Query(value = "SELECT count(1) FROM lab_detail ld JOIN all_category gc ON ld.graduateCategoryId=gc.id " +
            " where gc.discriminator='graduate'",nativeQuery = true)
    Long getGraduatesCount();

    List<LabDetail> getByCategoryIdAndPublishStatus(Long labCategoryId,Integer publishStatus);
    Page<LabDetail> getByCategoryIdAndPublishStatus(Long labCategoryId,Integer publishStatus, Pageable pageable);

    @Query("select new LabDetail(labDetail.id,labDetail.title,labDetail.publishDate) from LabDetail labDetail " +
            "where labDetail.categoryId=:categoryId and labDetail.publishStatus=:publishStatus" +
            " order by labDetail.publishDate desc,labDetail.id desc ")
    Page<LabDetail> getListByCategoryIdAndPublishStatus(@Param("categoryId") Long categoryId,
                                                           @Param("publishStatus") Integer publishStatus, Pageable pageable);

    @Query(value = "SELECT ld.id,mc.id mentorCategoryId,ld.mentorName,ld.mentorOrder,mc.category,ld.publishStatus FROM lab_detail ld " +
            " RIGHT JOIN all_category mc ON ld.mentorCategoryId=mc.id where mc.discriminator='mentor' " +
            " order by mentorCategoryId, ld.mentorOrder",nativeQuery = true)
    List<Object[]> getResearchTeam();

    @Query(value = "SELECT ld.id,mc.id mentorCategoryId,ld.mentorName,ld.mentorOrder,mc.category,ld.publishStatus," +
            "LENGTH(context) contextLength FROM lab_detail ld " +
            " RIGHT JOIN all_category mc ON ld.mentorCategoryId=mc.id where mc.discriminator='mentor'and " +
            " ld.publishStatus=:publishStatus " +
            " order by mentorCategoryId, ld.mentorOrder",nativeQuery = true)
    List<Object[]> getResearchTeamByPublishStatus(@Param("publishStatus") Integer publishStatus);

    List<LabDetail> getByIdIn(List<Long> ids);


    @Modifying
    @Query("update LabDetail ld set ld.publishStatus=1 where ld.id=:id")
    Integer updatePublishStatusById(@Param("id")Long id);


//    LabDetail getByIdAndPublishStatus(Long id,Integer publishStatus);


    @Query(value = "select ld.*,ac.category from lab_detail ld join all_category ac on (ld.categoryId=ac.id and ac.discriminator='lab')" +
            " where ld.id=:id and ld.publishStatus=:publishStatus",nativeQuery = true)
    List<Object[]> getByIdAndPublishStatus(@Param("id") Long id,@Param("publishStatus") Integer publishStatus);




//    List<LabDetail> getByIdIn(List<Long> ids);
}
