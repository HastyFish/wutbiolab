package com.gooalgene.wutbiolab;

import com.gooalgene.wutbiolab.dao.lab.LabDetailDAO;
import com.gooalgene.wutbiolab.dao.resource.ResourceDetailDAO;
import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import com.gooalgene.wutbiolab.entity.resource.ResourceDetail;
import com.gooalgene.wutbiolab.entity.resource.ResourceOverview;
import com.gooalgene.wutbiolab.service.LabService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResourceTests {
	@Autowired
	private ResourceDetailDAO resourceDetailDAO;

	@Test
	public void testDAO() {
		Page<ResourceOverview> newsDetailByPublishStatus = resourceDetailDAO.findNewsDetailByPublishStatus(37l,1, PageRequest.of(0, 11));

		newsDetailByPublishStatus.forEach(resourceOverview -> {
			Long id = resourceOverview.getId();
			Long publishDate = resourceOverview.getPublishDate();
			String title = resourceOverview.getTitle();
			System.out.println(id+":"+title+":"+publishDate);
		});
		System.out.println(1);

	}



}
