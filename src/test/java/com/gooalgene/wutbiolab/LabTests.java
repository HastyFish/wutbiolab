package com.gooalgene.wutbiolab;

import com.gooalgene.wutbiolab.dao.lab.LabDetailDAO;
import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import com.gooalgene.wutbiolab.service.LabService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
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

//	@Rollback(false)
	@Transactional
	@Test
	public void testDAO() {
//		Integer integer = labDetailDAO.updatePublishStatusById(2l);
//		List<Object[]> byIdAndPublishStatus =labDetailDAO.getByIdAndPublishStatus(4l, 1);
		String countSql="select count(1) from  resource_detail labDetail  where labDetail.publishDate =:publishDate";
		Query nativeQuery = entityManager.createNativeQuery(countSql);
		nativeQuery.setParameter("publishDate",1569859200000l);
		Object singleResult = nativeQuery.getSingleResult();
		System.out.println(1);

	}

	@Test
	public void testService(){
		Map<String, LabDetail> publishedById = labService.getPublishedById(1l);
		System.out.println(1);

	}

}
