package com.gooalgene.wutbiolab.dao.news;

import com.gooalgene.wutbiolab.entity.news.NewsDetail;
import com.gooalgene.wutbiolab.entity.news.NewsOverview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewsDetailDAO extends JpaRepository<NewsDetail, Long> {

    @Query("select a.id as id, a.publishStatus as publishStatus, a.publishDate as publishDate, " +
            "a.category as category, a.title as title from NewsDetail a")
    Page<NewsOverview> findNewsDetailBy(Pageable pageable);

    @Query("select a.id as id, a.image as image, a.publishStatus, a.title as title, a.category as category" +
            " from NewsDetail a where a.category = ?1 and a.publishStatus = ?2")
    List<NewsOverview> findByCategoryAndPublishStatus(String category, Integer publishStatus);

    @Query("select a.title as title, a.publishDate as publishDate from NewsDetail a")
    List<NewsOverview> findByCategoryEquals(String category);

    @Query("select a.title as title, a.publishDate as publishDate, a.category as category from NewsDetail a")
    Page<NewsOverview> findByPublishStatusEquals(Integer published, Pageable pageable);

    @Query("select a.id as id, a.title as title, a.publishDate as publishDate from NewsDetail a " +
            "where a.category = ?1 and a.publishStatus = ?2")
    Page<NewsOverview> findByCategoryAndPublishStatusPage(String category, Integer publishStatus, Pageable pageable);

    NewsDetail findByIdAndPublishStatus(Long id, Integer publishStatus);

    @Query("select a.id as id, a.title as title from NewsDetail a where publishDate < ?1 " +
            "and category = ?2 and publishStatus = ?3")
    Page<NewsOverview> findPreviousNewsDetail(long publishDate, String category, int publishStatus, Pageable pageable);

    @Query("select a.id as id, a.title as title from NewsDetail a where publishDate > ?1 " +
            "and category = ?2 and publishStatus = ?2")
    Page<NewsOverview> findNextNewsDetail(long publishDate, String category, Integer publishStatus, Pageable pageable);
}