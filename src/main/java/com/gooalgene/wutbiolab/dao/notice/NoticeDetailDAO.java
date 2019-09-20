package com.gooalgene.wutbiolab.dao.notice;

import com.gooalgene.wutbiolab.entity.notice.NoticeDetail;
import com.gooalgene.wutbiolab.entity.notice.NoticeOverview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeDetailDAO extends JpaRepository<NoticeDetail, Long> {

    @Query("select a.id as id, a.publishStatus as publishStatus, a.publishDate as publishDate, " +
            "a.category as category, a.title as title from NoticeDetail a")
    Page<NoticeOverview> findNewsDetailBy(Pageable pageable);

    @Query("select a.id as id, a.title as title, a.publishDate as publishDate, a.category as category from NoticeDetail a")
    Page<NoticeOverview> findByPublishStatusEquals(Integer published, Pageable pageable);

    @Query("select a.id as id, a.publishStatus as publishStatus, a.category as category from NoticeDetail a " +
            "where a.category = ?1 and a.publishStatus = ?2")
    List<NoticeOverview> findByCategoryAndPublishStatus(String category, Integer publishStatus);

    NoticeDetail findByIdAndPublishStatus(long id, Integer publishStatus);

    @Query("select a.id as id, a.title as title, a.publishDate as publishDate, a.category as category from NoticeDetail a " +
            "where a.category = ?1 and a.publishStatus = ?2")
    Page<NoticeOverview> findByCategoryAndPublishStatusPage(String category, Integer publishStatus, Pageable pageable);

    @Query("select a.id as id, a.title as title from NoticeDetail a where publishDate < ?1 " +
            "and category = ?2 and publishStatus = ?3")
    Page<NoticeOverview> findPreviousNoticeDetail(long publishDate, String category, Integer publishStatus, Pageable pageable);

    @Query("select a.id as id, a.title as title from NoticeDetail a where publishDate > ?1 " +
            "and category = ?2 and publishStatus = ?2")
    Page<NoticeOverview> findNextNoticeDetail(long publishDate, String category, Integer publishStatus, Pageable pageable);


}
