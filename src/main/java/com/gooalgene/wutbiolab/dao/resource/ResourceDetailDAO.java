package com.gooalgene.wutbiolab.dao.resource;

import com.gooalgene.wutbiolab.entity.resource.ResourceDetail;
import com.gooalgene.wutbiolab.entity.resource.ResourceOverview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResourceDetailDAO extends JpaRepository<ResourceDetail, Long> {

    @Query("select a.id as id, a.publishStatus as publishStatus, a.publishDate as publishDate, " +
            "a.category as category, a.title as title from ResourceDetail a")
    Page<ResourceOverview> findNewsDetailBy(Pageable pageable);

    @Query("select a.id as id, a.publishStatus as publishStatus, a.publishDate as publishDate, " +
            "a.category as category, a.title as title from ResourceDetail a where a.categoryId = ?1")
    Page<ResourceOverview> findNewsDetailByCategoryId(long categoryId, Pageable pageable);

    @Query("select a.id as id,  a.publishDate as publishDate, " +
            " a.title as title,a.category as category from ResourceDetail a where a.publishStatus=:publishStatus " +
            "and a.categoryId=:categoryId order by a.publishDate desc,a.id desc ")
    Page<ResourceOverview> findNewsDetailByPublishStatus(@Param("categoryId")Long categoryId,
                                                         @Param("publishStatus") Integer publishStatus, Pageable pageable);

    @Query("select a.image as image, a.title as title, a.id as id, " +
            "a.category as category, a.categoryId as categoryId from ResourceDetail a where a.publishStatus = ?1")
    List<ResourceOverview> findByPublishStatusEquals(Integer publishStatus, Pageable pageable);

    ResourceDetail getByIdAndPublishStatus(Long id,Integer publishStatus);


    @Query("select a.id as id,  a.publishDate as publishDate, a.image as image , " +
            " a.title as title,a.category as category,a.categoryId as categoryId  from ResourceDetail a where a.publishStatus=:publishStatus " +
            "and a.categoryId in (:categoryId2 ,:categoryId3 ,:categoryId4) order by a.publishDate desc,a.id desc ")
    Page<ResourceOverview> findResourceOverviewByNotCategoryIdAndPublishStatus(@Param("categoryId2")Long categoryId2,@Param("categoryId3")Long categoryId3,@Param("categoryId4")Long categoryId4,
                                                         @Param("publishStatus") Integer publishStatus, Pageable pageable);

}

