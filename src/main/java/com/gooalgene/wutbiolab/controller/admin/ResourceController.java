package com.gooalgene.wutbiolab.controller.admin;

import com.gooalgene.wutbiolab.entity.resource.ResourceCategory;
import com.gooalgene.wutbiolab.entity.resource.ResourceDetail;
import com.gooalgene.wutbiolab.entity.resource.ResourceOverview;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "后台资源", tags = {"后台资源接口"})
@RestController
@RequestMapping("/resource")
public class ResourceController {

    private ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @ApiOperation(value="查询资源分类", notes="查询资源分类")
    @GetMapping("/category")
    public CommonResponse<List<ResourceCategory>> getResouceCategory() {
        return resourceService.allResourceCategory();
    }

    @ApiOperation(value="查询资源分页", notes="查询带分页的资源")
    @GetMapping
    public CommonResponse<PageResponse<ResourceOverview>> getResourceDetailPage(Integer pageNum, Integer pageSize,
                                                                                Long categoryId) {
        return resourceService.resourceDetailPage(pageNum, pageSize, categoryId);
    }

    @ApiOperation(value="根据id查询资源详情", notes="根据id查询资源详情")
    @GetMapping("/{id}")
    public CommonResponse<ResourceDetail> getResourceDetail(@PathVariable Integer id) {
        return resourceService.resourceDetailById(id);
    }

    @ApiOperation(value="保存资源", notes="保存资源")
    @PostMapping
    public CommonResponse<Boolean> renewResourceDetail(@RequestBody ResourceDetail resourceDetail) {
        return resourceService.renewResourceDetail(resourceDetail);
    }

    @ApiOperation(value="根据id删除资源", notes="根据id删除资源")
    @DeleteMapping("/{id}")
    public CommonResponse<Boolean> deleteResourceDetail(@PathVariable Integer id) {
        return resourceService.deleteResourceDetail(id);
    }

}
