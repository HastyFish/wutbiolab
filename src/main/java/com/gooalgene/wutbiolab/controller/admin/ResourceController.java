package com.gooalgene.wutbiolab.controller.admin;

import com.gooalgene.wutbiolab.entity.resource.ResourceCategory;
import com.gooalgene.wutbiolab.entity.resource.ResourceDetail;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.service.ResourceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    private ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping("/category")
    public CommonResponse<List<ResourceCategory>> getResouceCategory() {
        return resourceService.allResourceCategory();
    }

    @GetMapping
    public CommonResponse<PageResponse<ResourceDetail>> getResourceDetailPage(Integer pageNum, Integer pageSize) {
        return resourceService.resourceDetailPage(pageNum, pageSize);
    }

    @GetMapping("/{id}")
    public CommonResponse<ResourceDetail> getResourceDetail(@PathVariable Integer id) {
        return resourceService.resourceDetailById(id);
    }

    @PostMapping
    public CommonResponse<Boolean> renewResourceDetail(@RequestBody ResourceDetail resourceDetail) {
        return resourceService.renewResourceDetail(resourceDetail);
    }

    @DeleteMapping("/{id}")
    public CommonResponse<Boolean> deleteResourceDetail(@PathVariable Integer id) {
        return resourceService.deleteResourceDetail(id);
    }
}
