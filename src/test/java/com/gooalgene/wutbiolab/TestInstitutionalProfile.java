package com.gooalgene.wutbiolab;

import com.gooalgene.wutbiolab.dao.InstitutionalProfileDAO;
import com.gooalgene.wutbiolab.entity.InstitutionalProfile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestInstitutionalProfile {
	@Autowired
	private InstitutionalProfileDAO institutionalProfileDAO;

	@Test
	public void testDAO() {
		InstitutionalProfile institutionalProfile=new InstitutionalProfile();
		institutionalProfile.setContext("xxx");
		institutionalProfile.setTitle("title");

		institutionalProfileDAO.save(institutionalProfile);
	}

}
