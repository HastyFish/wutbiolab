package com.gooalgene.wutbiolab.dao.news;

import com.gooalgene.wutbiolab.entity.news.NewsDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsDetailDAO extends JpaRepository<NewsDetail, Long> {
}
