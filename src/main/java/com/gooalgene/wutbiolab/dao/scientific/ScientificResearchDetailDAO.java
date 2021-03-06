package com.gooalgene.wutbiolab.dao.scientific;

import com.gooalgene.wutbiolab.entity.scientificResearch.ScientificResearchDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScientificResearchDetailDAO extends JpaRepository<ScientificResearchDetail,Long> {
    List<ScientificResearchDetail> findByCategoryId(Long categoryId);
    Page<ScientificResearchDetail> findByCategoryId(Long categoryId,Pageable pageable);


    List<ScientificResearchDetail> findByCategoryIdAndPublishStatus(Long categoryId,Integer publishStatus);
    Page<ScientificResearchDetail> findByCategoryIdAndPublishStatus(Long categoryId,Integer publishStatus,Pageable pageable);

    //针对列表查询，不查context这样的大字段
    @Query("select new ScientificResearchDetail(s.id,s.title,s.publishDate,s.publishStatus,s.periodicalName," +
            "s.author,s.publishYear ) from ScientificResearchDetail s " +
            " where s.categoryId=:categoryId")
    Page<ScientificResearchDetail> getByCategoryId(@Param("categoryId") Long categoryId,
                                                                     Pageable pageable);

    @Query(value = "select s.id as id,s.title as title,s.publishDate as publishDate ," +
            "s.publishStatus as publishStatus,a.category as academicCategoryName " +
            "from scientific_research_detail s " +
            "join all_category a on s.academicCategoryId " +
            "=a.id where a.discriminator='academic' limit :pageNum,:pageSize"
            ,nativeQuery = true)
    List<Object[]> getSRAcademicList(@Param("pageNum")Integer pageNum,@Param("pageSize") Integer pageSize);

    @Query(value = "select count(1) " +
            "from scientific_research_detail s " +
            "join all_category a on s.academicCategoryId " +
            "=a.id where a.discriminator='academic'",nativeQuery = true)
    Long getSRAcademicListCount();


    @Query("select new ScientificResearchDetail(s.id,s.title,s.publishDate,s.publishStatus,s.periodicalName," +
            "s.author,s.publishYear ) from ScientificResearchDetail s " +
            " where s.categoryId=:categoryId and " +
            "s.publishStatus=:publishStatus order by s.publishDate desc,s.id desc ")
    Page<ScientificResearchDetail> getByCategoryIdAndPublishStatus(@Param("categoryId") Long categoryId,
                                                                                     @Param("publishStatus") Integer publishStatus, Pageable pageable);

//    ScientificResearchDetail getByIdAndPublishStatus(Long id,Integer publishStatus);
    @Query(value = "select srd.*,ac.category from scientific_research_detail srd join all_category ac on (srd.categoryId=ac.id and ac.discriminator='scientific')" +
        " where srd.id=:id and srd.publishStatus=:publishStatus",nativeQuery = true)
    List<Object[]> getByIdAndPublishStatus(@Param("id") Long id,@Param("publishStatus")Integer publishStatus);

    //根据时间段查询论文
    @Query("select new ScientificResearchDetail(s.id,s.title,s.publishDate,s.publishStatus,s.periodicalName," +
            "s.author,s.publishYear ) from ScientificResearchDetail s " +
            " where s.publishDate between ?1 and ?2")
    Page<ScientificResearchDetail> getByDate(long beginDate, long endDate, Pageable pageable);

    //根据发布状态查询论文
    @Query("select new ScientificResearchDetail(s.id,s.title,s.publishDate,s.publishStatus,s.periodicalName," +
            "s.author,s.publishYear ) from ScientificResearchDetail s " +
            " where s.publishStatus = ?1")
    Page<ScientificResearchDetail> getByStatus(int status, Pageable pageable);

    //根据论文标题模糊查询论文
    @Query("select new ScientificResearchDetail(s.id,s.title,s.publishDate,s.publishStatus,s.periodicalName," +
            "s.author,s.publishYear ) from ScientificResearchDetail s " +
            "where s.title like %?1%")
    Page<ScientificResearchDetail> getByTitle(String title, Pageable pageable);

    //根据刊物名称模糊查询论文
    @Query("select new ScientificResearchDetail(s.id,s.title,s.publishDate,s.publishStatus,s.periodicalName," +
            "s.author,s.publishYear ) from ScientificResearchDetail s " +
            "where s.periodicalName like %?1%")
    Page<ScientificResearchDetail> getByPeriodicalName(String periodicalName, Pageable pageable);

    //根据作者模糊查询论文
    @Query("select new ScientificResearchDetail(s.id,s.title,s.publishDate,s.publishStatus,s.periodicalName," +
            "s.author,s.publishYear ) from ScientificResearchDetail s " +
            "where s.author like %?1%")
    Page<ScientificResearchDetail> getByAhuthor(String author, Pageable pageable);
}
