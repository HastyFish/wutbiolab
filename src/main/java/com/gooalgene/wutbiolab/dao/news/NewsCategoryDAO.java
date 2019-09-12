package com.gooalgene.wutbiolab.dao.news;

import com.gooalgene.wutbiolab.entity.news.NewsCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsCategoryDAO extends JpaRepository<NewsCategory, Long> {
}
