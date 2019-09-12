package com.gooalgene.wutbiolab;

import com.gooalgene.wutbiolab.dao.LabDetailDAO;
import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import com.gooalgene.wutbiolab.response.MentorResponse;
import com.gooalgene.wutbiolab.service.LabService;
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
public class TestLab {
	@Autowired
	private LabDetailDAO labDetailDAO;
	@Autowired
	private LabService labService;
	@Test
	public void testDAO() {
//		List<Object[]> researchTeam = labDetailDAO.getResearchTeam();
//		Page<LabDetail> byLabCategoryId = labDetailDAO.getByLabCategoryId(3l, PageRequest.of(0, 3));
		List<Object[]> researchTeam = labDetailDAO.getResearchTeam(1);
		System.out.println(1);

	}

	@Test
	public void testService(){
		List<MentorResponse> researchTeam = labService.getPublishedResearchTeam();
//		Page<LabDetail> published = labService.getPublished(0, 2);

		System.out.println(1);

	}

}
