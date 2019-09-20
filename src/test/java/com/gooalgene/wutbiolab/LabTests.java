package com.gooalgene.wutbiolab;

import com.gooalgene.wutbiolab.dao.lab.LabDetailDAO;
import com.gooalgene.wutbiolab.entity.lab.GraduateCategory;
import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import com.gooalgene.wutbiolab.service.LabService;
import org.hibernate.transform.Transformers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LabTests {
	@Autowired
	private LabDetailDAO labDetailDAO;
	@Autowired
	private LabService labService;
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	@Test
	public void testDAO() {
		labDetailDAO.updatePublishStatusById(2l);
		System.out.println(1);

	}

	@Test
	public void testService(){
//		List<MentorResponse> researchTeam = labService.getPublishedResearchTeam();
//		Page<LabDetail> published = labService.getPublished(0, 2);

//		Page<LabDetail> labDetails = labService.getLabDetailByLabCategoryIdAndPublishStatus(
//				3l, 0, 2, CommonConstants.PUBLISHED,true);
//		PageResponse<LabDetail> graduates = labService.getGraduates(1, 3);
//		List<GraduateCategory> allCategory = labService.getGraduateCategorys();
		Map<String, LabDetail> publishedById = labService.getPublishedById(27l);
		System.out.println(1);

	}

}
