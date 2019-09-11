package com.gooalgene.wutbiolab;

import com.gooalgene.wutbiolab.dao.InstitutionalProfileDAO;
import com.gooalgene.wutbiolab.entity.Academic;
import com.gooalgene.wutbiolab.entity.InstitutionalProfile;
import com.gooalgene.wutbiolab.service.InstitutionalProfileService;
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
	@Autowired
	private InstitutionalProfileService institutionalProfileService;

	@Test
	public void testDAO() {
		InstitutionalProfile institutionalProfile=new InstitutionalProfile();
		institutionalProfile.setContext("xxx");
		institutionalProfile.setTitle("title");

		institutionalProfileDAO.save(institutionalProfile);
	}

	@Test
	public void testService(){
		InstitutionalProfile institutionalProfile=new InstitutionalProfile();
		institutionalProfile.setContext("xxx");
		institutionalProfile.setTitle("title");

		institutionalProfileService.saveUnpublished(institutionalProfile);
		System.out.println(1);

	}

}
