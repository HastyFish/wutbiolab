package com.gooalgene.wutbiolab.controller.admin;

import com.gooalgene.wutbiolab.entity.resource.ResourceDetail;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resoucre")
public class ResourceController {
    @GetMapping
    public CommonResponse<PageResponse<ResourceDetail>> getResourceDetailList(Integer pageNum, Integer pageSize) {
        return ResponseUtil.success(new PageResponse<>());
    }

    @GetMapping("/{id}")
    public CommonResponse<ResourceDetail> getResourceDetail(@PathVariable Integer id) {
        return ResponseUtil.success(new ResourceDetail());
    }

    @PostMapping
    public CommonResponse<Boolean> renewResourceDetail(ResourceDetail resourceDetail) {
        return ResponseUtil.success(true);
    }

    @DeleteMapping("/{id}")
    public CommonResponse<Boolean> deleteResourceDetail(@PathVariable Integer id) {
        return ResponseUtil.success(true);
    }
}
