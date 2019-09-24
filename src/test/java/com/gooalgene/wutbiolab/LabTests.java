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
import java.util.Arrays;
import java.util.HashMap;
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
//		List<LabDetail> byIdIn = labDetailDAO.getByIdIn(Arrays.asList(1l, 6l, 3l));
		boolean b = 0 == 0;
		System.out.println(1);

	}

	@Test
	public void testService(){
		Map<Long,Integer> map=new HashMap<>();
		map.put(3l,3);
		map.put(6l,6);
		map.put(7l,7);
		labService.updateMentorOrderById(map);
		System.out.println(1);

	}

}
