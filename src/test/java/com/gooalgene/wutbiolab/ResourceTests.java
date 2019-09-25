package com.gooalgene.wutbiolab;

import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.dao.lab.LabDetailDAO;
import com.gooalgene.wutbiolab.dao.resource.ResourceDetailDAO;
import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import com.gooalgene.wutbiolab.entity.resource.ResourceDetail;
import com.gooalgene.wutbiolab.entity.resource.ResourceOverview;
import com.gooalgene.wutbiolab.service.LabService;
import com.gooalgene.wutbiolab.service.ResourceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResourceTests {
	@Autowired
	private ResourceDetailDAO resourceDetailDAO;

	@Autowired
	private ResourceService resourceService;
	@Test
	public void testDAO() {
//		Page<ResourceOverview> newsDetailByPublishStatus = resourceDetailDAO.findNewsDetailByPublishStatus(37l,1, PageRequest.of(0, 11));

		Sort.Order orderPublishDate=new Sort.Order(Sort.Direction.DESC, CommonConstants.PUBLISHDATEFIELD);
		Sort.Order orderId=new Sort.Order(Sort.Direction.DESC, CommonConstants.ID);
		List<ResourceOverview> resourceOverviewList = resourceDetailDAO.findByPublishStatusEquals(
				CommonConstants.PUBLISHED, PageRequest.of(0, 4,
						Sort.by(orderPublishDate,orderId)));
		resourceOverviewList.forEach(resourceOverview -> {
			Long id = resourceOverview.getId();
			String image = resourceOverview.getImage();
			String title = resourceOverview.getTitle();
			String category = resourceOverview.getCategory();
			System.out.println(id+":"+title+":"+image+":"+category);
		});
		System.out.println(1);

	}

	@Test
	public void testService(){
//		Map<String, ResourceDetail> publishedById5 = resourceService.getPublishedById(5l);
//		Map<String, ResourceDetail> publishedById4 = resourceService.getPublishedById(4l);
//		Map<String, ResourceDetail> publishedById3 = resourceService.getPublishedById(3l);
//		Map<String, ResourceDetail> publishedById2 = resourceService.getPublishedById(2l);
//		Map<String, ResourceDetail> publishedById1 = resourceService.getPublishedById(1l);
		System.out.println(1);
	}



}
