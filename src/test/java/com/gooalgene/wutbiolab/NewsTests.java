package com.gooalgene.wutbiolab;

import com.gooalgene.wutbiolab.dao.news.NewsCategoryDAO;
import com.gooalgene.wutbiolab.entity.news.NewsCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NewsTests {

    @Autowired
    private NewsCategoryDAO newsCategoryDAO;

    @Test
    public void testCategory() {
        List<NewsCategory> a = newsCategoryDAO.findAll();
        System.out.println(a.size());
        System.out.println(a.get(0));
    }

}
