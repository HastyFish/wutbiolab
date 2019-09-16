package com.gooalgene.wutbiolab.dao.resource;

import com.gooalgene.wutbiolab.entity.resource.ResourceDetail;
import com.gooalgene.wutbiolab.entity.resource.ResourceOverview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ResourceDetailDAO extends JpaRepository<ResourceDetail, Long> {

    @Query("select a.id as id, a.publishStatus as publishStatus, a.publishDate as publishDate, " +
            "a.category as category, a.title as title from ResourceDetail a")
    Page<ResourceOverview> findNewsDetailBy(Pageable pageable);
}
