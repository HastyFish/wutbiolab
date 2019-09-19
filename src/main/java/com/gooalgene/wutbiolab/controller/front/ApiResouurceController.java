package com.gooalgene.wutbiolab.controller.front;

import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.entity.resource.ResourceDetail;
import com.gooalgene.wutbiolab.entity.resource.ResourceOverview;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/resource")
public class ApiResouurceController {
    @Autowired
    private ResourceService resourceService;

    @GetMapping("/published/list")
    public CommonResponse<PageResponse<ResourceOverview>> getByPublishStatus(@RequestParam("pageNum") Integer pageNum,
                                                                             @RequestParam("pageSize") Integer pageSize){
        PageResponse<ResourceOverview> byPublishStatus = resourceService.getByPublishStatus(CommonConstants.PUBLISHED, pageNum, pageSize);
        return ResponseUtil.success(byPublishStatus);
    }

    @GetMapping("/published/{id}")
    public CommonResponse<Map<String,ResourceDetail>> getPublishedById(@PathVariable("id")Long id){
        Map<String, ResourceDetail> publishedById = resourceService.getPublishedById(id);
        return ResponseUtil.success(publishedById);
    }
}
