package com.gooalgene.wutbiolab;

import com.gooalgene.wutbiolab.dao.scientific.ScientificResearchDetailDAO;
import com.gooalgene.wutbiolab.entity.scientificResearch.ScientificResearchCategory;
import com.gooalgene.wutbiolab.entity.scientificResearch.ScientificResearchDetail;
import com.gooalgene.wutbiolab.service.ScientificResearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SRTests {
    @Autowired
    private ScientificResearchService scientificResearchService;

    @Autowired
    private ScientificResearchDetailDAO scientificResearchDetailDAO;

    @Test
    public void testService(){
        ScientificResearchDetail scientificResearchDetail=new ScientificResearchDetail();
        scientificResearchDetail.setAuthor("huyao4");
        scientificResearchDetail.setContext("胡尧牛逼4");
        scientificResearchDetail.setPeriodicalName("干大事的4");
//        scientificResearchDetail.setId(3l);
//        scientificResearchService.saveOrPublish(scientificResearchDetail, CommonConstants.PUBLISHED);
    }

    @Test
    public void testService2(){
        Map<String, ScientificResearchDetail> publishedById62 = scientificResearchService.getPublishedById(62l);
        Map<String, ScientificResearchDetail> publishedById61 = scientificResearchService.getPublishedById(61l);
        Map<String, ScientificResearchDetail> publishedById60 = scientificResearchService.getPublishedById(60l);
        Map<String, ScientificResearchDetail> publishedById59 = scientificResearchService.getPublishedById(59l);
        Map<String, ScientificResearchDetail> publishedById58 = scientificResearchService.getPublishedById(58l);
        System.out.println(1);
    }

    @Test
    public void testDao(){
        List<Object[]> srAcademicList = scientificResearchDetailDAO.getSRAcademicList(0,19);
        Long srAcademicListCount = scientificResearchDetailDAO.getSRAcademicListCount();
        System.out.println(1);
    }
}
