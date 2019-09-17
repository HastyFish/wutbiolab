package com.gooalgene.wutbiolab;

import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.dao.scientific.ScientificResearchDetailDAO;
import com.gooalgene.wutbiolab.entity.scientificResearch.ScientificResearchCategory;
import com.gooalgene.wutbiolab.entity.scientificResearch.ScientificResearchDetail;
import com.gooalgene.wutbiolab.service.ScientificResearchService;
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
//        Page<ScientificResearchDetail> srDetialByCategoryId = scientificResearchService.getSRDetialByCategoryId(1l, 0, 2);
        List<ScientificResearchCategory> allCategory = scientificResearchService.getAllCategory();
        System.out.println(1);
    }

    @Test
    public void testDao(){
        List<Object[]> srAcademicList = scientificResearchDetailDAO.getSRAcademicList(0,19);
        Long srAcademicListCount = scientificResearchDetailDAO.getSRAcademicListCount();
        System.out.println(1);
    }
}
