package com.gooalgene.wutbiolab;

import com.gooalgene.wutbiolab.dao.news.NewsCategoryDAO;
import com.gooalgene.wutbiolab.dao.news.NewsDetailDAO;
import com.gooalgene.wutbiolab.entity.news.NewsCategory;
import com.gooalgene.wutbiolab.entity.news.NewsOverview;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NewsTests {

    @Autowired
    private NewsCategoryDAO newsCategoryDAO;

    @Autowired
    private NewsDetailDAO newsDetailDAO;

    @Test
    public void testCategory() {
        List<NewsCategory> a = newsCategoryDAO.findAll();
        System.out.println(a.size());
        System.out.println(a.get(0));
    }

    @Test
    public void testPage() {
//        Page<NewsOverview> newsOverviewPage = newsDetailDAO.findNewsDetailBy(PageRequest.of(0, 10));
//        System.out.println(newsOverviewPage.getTotalElements());
    }

}
