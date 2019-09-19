package com.gooalgene.wutbiolab.controller.front;

import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.entity.resource.ResourceOverview;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.ParameterizedType;

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
}
