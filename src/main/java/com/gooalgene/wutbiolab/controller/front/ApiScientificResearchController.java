package com.gooalgene.wutbiolab.controller.front;

import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import com.gooalgene.wutbiolab.entity.scientificResearch.ScientificResearchDetail;
import com.gooalgene.wutbiolab.response.MentorResponse;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.service.LabService;
import com.gooalgene.wutbiolab.service.ScientificResearchService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "前端科研工作模块", tags = {"前端科研工作模块接口"})
@RestController
@RequestMapping("/api/scientificResearch")
public class ApiScientificResearchController {
    @Autowired
    private ScientificResearchService scientificResearchService;


    @GetMapping("/list/{labCategoryId}")
    public CommonResponse<Page<ScientificResearchDetail>> getLabDetailByLabCategoryIdAndPublishStatus(@PathVariable("labCategoryId")Long labCategoryId,
                                @RequestParam("pageNum")Integer pageNum,@RequestParam("pageSize")Integer pageSize){
        Page<ScientificResearchDetail> scientificResearchDetails = scientificResearchService.getPublishedByCategoryId(labCategoryId, pageNum, pageSize);
        return ResponseUtil.success(scientificResearchDetails);
    }

    @GetMapping("/{id}")
    public CommonResponse<ScientificResearchDetail> getPublishedById(@PathVariable("id") Long id){
        ScientificResearchDetail scientificResearchDetail = scientificResearchService.getPublishedById(id);
        return ResponseUtil.success(scientificResearchDetail);
    }





}
