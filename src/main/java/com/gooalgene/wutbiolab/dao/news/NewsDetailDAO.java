package com.gooalgene.wutbiolab.dao.news;

import com.gooalgene.wutbiolab.entity.news.NewsDetail;
import com.gooalgene.wutbiolab.entity.news.NewsOverview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewsDetailDAO extends JpaRepository<NewsDetail, Long> {

    @Query("select a.id as id, a.publishStatus as publishStatus, a.publishDate as publishDate, " +
            "a.category as category, a.title as title from NewsDetail a")
    Page<NewsOverview> findNewsDetailBy(Pageable pageable);

    @Query("select a.id as id, a.image as image, a.publishStatus as publishStatus from NewsDetail a")
    List<NewsOverview> findByCategoryEquals(String category);

    @Query("select a.title as title, a.publishDate as publishDate from NewsDetail a")
    Page<NewsOverview> findByPublishStatusEquals(Integer published, Pageable pageable);
}