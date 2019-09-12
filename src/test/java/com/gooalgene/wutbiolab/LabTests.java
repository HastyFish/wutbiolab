package com.gooalgene.wutbiolab;

import com.gooalgene.wutbiolab.dao.LabDetailDAO;
import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.service.LabService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LabTests {
	@Autowired
	private LabDetailDAO labDetailDAO;
	@Autowired
	private LabService labService;
	@Test
	public void testDAO() {
//		List<Object[]> researchTeam = labDetailDAO.getResearchTeam();
//		Page<LabDetail> byLabCategoryId = labDetailDAO.getByLabCategoryId(3l, PageRequest.of(0, 3));
//		List<Object[]> researchTeam = labDetailDAO.getResearchTeamByPublishStatus(1);
//		List<LabDetail> byLabCategoryId = labDetailDAO.getByLabCategoryId(3l);
//		List<LabDetail> byIdAndPublishStatus2 = labDetailDAO.getByIdAndPublishStatus(2l, CommonConstants.PUBLISHED);
//		LabDetail byIdAndPublishStatus = labDetailDAO.getByIdAndPublishStatus(1l, CommonConstants.PUBLISHED);

//		List<Object[]> graduates = labDetailDAO.getGraduates(0, 3);
		Long graduatesCount = labDetailDAO.getGraduatesCount();
		System.out.println(1);

	}

	@Test
	public void testService(){
//		List<MentorResponse> researchTeam = labService.getPublishedResearchTeam();
//		Page<LabDetail> published = labService.getPublished(0, 2);

//		Page<LabDetail> labDetails = labService.getLabDetailByLabCategoryIdAndPublishStatus(
//				3l, 0, 2, CommonConstants.PUBLISHED,true);
		PageResponse<LabDetail> graduates = labService.getGraduates(1, 3);
		System.out.println(1);

	}

}
