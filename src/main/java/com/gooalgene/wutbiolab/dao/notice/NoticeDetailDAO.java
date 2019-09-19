package com.gooalgene.wutbiolab.dao.notice;

import com.gooalgene.wutbiolab.entity.notice.NoticeDetail;
import com.gooalgene.wutbiolab.entity.notice.NoticeOverview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeDetailDAO extends JpaRepository<NoticeDetail, Long> {

    @Query("select a.id as id, a.publishStatus as publishStatus, a.publishDate as publishDate, " +
            "a.category as category, a.title as title from NoticeDetail a")
    Page<NoticeOverview> findNewsDetailBy(Pageable pageable);

    @Query("select a.title as title, a.publishDate as publishDate from NoticeDetail a")
    Page<NoticeOverview> findByPublishStatusEquals(Integer published, Pageable pageable);

    @Query("select a.id as id, a.publishStatus as publishStatus from NoticeDetail a " +
            "where a.category = ?1 and a.publishStatus = ?2")
    List<NoticeOverview> findByCategoryAndPublishStatus(String category, Integer publishStatus);
}
