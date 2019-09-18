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

    @Query("select a.id, a.image from NewsDetail a")
    List<NewsOverview> findByCategoryEquals(String category);

}