package com.gooalgene.wutbiolab.dao;

import com.gooalgene.wutbiolab.entity.scientificResearch.ScientificResearchDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ScientificResearchDetailDAO extends JpaRepository<ScientificResearchDetail,Long> {

    //针对列表查询，不查context这样的大字段
    @Query("select new ScientificResearchDetail(s.id,s.title,s.publishDate,s.publishStatus,s.periodicalName," +
            "s.author,s.publishYear ) from ScientificResearchDetail s " +
            " where s.scientificResearchCategoryId=:scientificResearchCategoryId")
    Page<ScientificResearchDetail> getByScientificResearchCategoryId(@Param("scientificResearchCategoryId") Long scientificResearchCategoryId,
                                                                     Pageable pageable);

    @Query("select new ScientificResearchDetail(s.id,s.title,s.publishDate,s.publishStatus,s.periodicalName," +
            "s.author,s.publishYear ) from ScientificResearchDetail s " +
            " where s.scientificResearchCategoryId=:scientificResearchCategoryId and " +
            "s.publishStatus=:publishStatus")
    Page<ScientificResearchDetail> getByScientificResearchCategoryIdAndPublishStatus(@Param("scientificResearchCategoryId") Long scientificResearchCategoryId,
                                                                                     @Param("publishStatus") Integer publishStatus, Pageable pageable);

    ScientificResearchDetail getByIdAndPublishStatus(Long id,Integer publishStatus);
}
