package com.gooalgene.wutbiolab;

import com.gooalgene.wutbiolab.dao.AllCategroryDAO;
import com.gooalgene.wutbiolab.entity.common.AllCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WutbiolabApplicationTests {
	@Autowired
	private AllCategroryDAO allCategroryDAO;

	@Test
	public void contextLoads() {
		List<AllCategory> all = allCategroryDAO.findAll();
		System.out.println(1);
	}

}
