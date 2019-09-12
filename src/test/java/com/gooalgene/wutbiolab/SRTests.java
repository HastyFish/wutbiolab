package com.gooalgene.wutbiolab;

import com.gooalgene.wutbiolab.dao.ScientificResearchDetailDAO;
import com.gooalgene.wutbiolab.entity.scientificResearch.ScientificResearchDetail;
import com.gooalgene.wutbiolab.service.ScientificResearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
        scientificResearchDetail.setAuthor("huyao");
        scientificResearchDetail.setContext("胡尧牛逼");
        scientificResearchDetail.setPeriodicalName("干大事的");
        scientificResearchService.save(scientificResearchDetail);
    }
}
