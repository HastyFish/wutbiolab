package com.gooalgene.wutbiolab;

import com.gooalgene.wutbiolab.dao.LabDetailDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestLab {
	@Autowired
	private LabDetailDAO labDetailDAO;
	@Test
	public void testDAO() {
		List<Object[]> researchTeam = labDetailDAO.getResearchTeam();
		System.out.println(1);

	}

	@Test
	public void testService(){

		System.out.println(1);

	}

}
