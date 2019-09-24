package com.gooalgene.wutbiolab.dao.news;

import com.gooalgene.wutbiolab.entity.news.NewsDetail;
import com.gooalgene.wutbiolab.entity.news.NewsOverview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewsDetailDAO extends JpaRepository<NewsDetail, Long> {

    @Query("select a.id as id, a.publishStatus as publishStatus, a.publishDate as publishDate, " +
            "a.category as category, a.title as title from NewsDetail a")
    Page<NewsOverview> findNewsDetailBy(Pageable pageable);

    @Query("select a.id as id, a.publishStatus as publishStatus, a.publishDate as publishDate, " +
            "a.category as category, a.title as title from NewsDetail a where a.categoryId = ?1")
    Page<NewsOverview> findNewsDetailByCategoryId(long categoryId, Pageable pageable);

    @Query("select a.id as id, a.image as image, a.publishStatus, a.title as title, a.category as category," +
            "a.categoryId as categoryId from NewsDetail a where a.category = ?1 and a.publishStatus = ?2")
    List<NewsOverview> findByCategoryAndPublishStatus(String category, Integer publishStatus, Pageable pageable);

    @Query("select a.id as id, a.title as title, a.category as category, a.categoryId as categoryId," +
            "a.publishDate as publishDate from NewsDetail a where a.categoryId = ?1 and a.publishStatus = ?2")
    Page<NewsOverview> findByCategoryIdAndPublishStatusPage(Long categoryId, Integer publishStatus, Pageable pageable);

//    @Query("select a.title as title, a.publishDate as publishDate from NewsDetail a")
    long countByCategoryIdEquals(long categoryId);

    @Query("select a.id as id, a.title as title, a.publishDate as publishDate, " +
            "a.category as category, a.categoryId as categoryId from NewsDetail a where a.publishStatus = ?1")
    Page<NewsOverview> findByPublishStatusEquals(Integer publishStatus, Pageable pageable);

    @Query("select a.id as id, a.title as title, a.publishDate as publishDate, a.category as category from NewsDetail a " +
            "where a.category = ?1 and a.publishStatus = ?2")
    Page<NewsOverview> findByCategoryAndPublishStatusPage(String category, Integer publishStatus, Pageable pageable);

    NewsDetail findByIdAndPublishStatus(Long id, Integer publishStatus);

    /**
     * 发布内容中不存在相同时间的前一条查询
     */
    @Query("select a.id as id, a.title as title from NewsDetail a where publishDate > ?1 " +
            "and category = ?2 and publishStatus = ?3")
    Page<NewsOverview> findPreviousNewsDetail(long publishDate, String category, int publishStatus, Pageable pageable);

    /**
     * 发布内容中不存在相同时间的后一条查询
     */
    @Query("select a.id as id, a.title as title from NewsDetail a where publishDate < ?1 " +
            "and category = ?2 and publishStatus = ?3")
    Page<NewsOverview> findNextNewsDetail(long publishDate, String category, int publishStatus, Pageable pageable);

    /**
     * 发布内容中与指定时间相等的发布条数
     */
    long countByPublishDateAndPublishStatus(long publishDate, int publishStatus);

    /**
     * 发布内容中存在相同时间的前一条查询
     */
    @Query("select a.id as id, a.title as title from NewsDetail a where publishDate >= ?1 and a.id < ?4 " +
            "and category = ?2 and publishStatus = ?3")
    Page<NewsOverview> findPreviousNewsDetail(long publishDate, String category, int publishStatus, long id, Pageable pageable);

    /**
     * 发布内容中存在相同时间的后一条查询
     */
    @Query("select a.id as id, a.title as title from NewsDetail a where publishDate <= ?1 and a.id > ?4 " +
            "and a.category = ?2 and a.publishStatus = ?3")
    Page<NewsOverview> findNextNewsDetail(long publishDate, String category, int publishStatus,
                                          long id, Pageable pageable);
}