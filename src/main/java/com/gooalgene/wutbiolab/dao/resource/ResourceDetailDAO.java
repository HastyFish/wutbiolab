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

    @Query("select a.id as id,  a.publishDate as publishDate, " +
            " a.title as title,a.category as category from ResourceDetail a where a.publishStatus=:publishStatus " +
            "and a.categoryId=:categoryId")
    Page<ResourceOverview> findNewsDetailByPublishStatus(@Param("categoryId")Long categoryId,
                                                         @Param("publishStatus") Integer publishStatus, Pageable pageable);

    @Query("select a.image as image, a.title as title, a.id as id, " +
            "a.category as category, a.categoryId as categoryId from ResourceDetail a where a.publishStatus = ?1")
    List<ResourceOverview> findByPublishStatusEquals(Integer publishStatus, Pageable pageable);

    ResourceDetail getByIdAndPublishStatus(Long id,Integer publishStatus);
}

