package com.gooalgene.wutbiolab.controller.admin;

import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.entity.scientificResearch.AcademicCategory;
import com.gooalgene.wutbiolab.entity.scientificResearch.ScientificResearchCategory;
import com.gooalgene.wutbiolab.entity.scientificResearch.ScientificResearchDetail;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.service.ScientificResearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scientificResearch")
public class ScientificResearchController {
    @Autowired
    private ScientificResearchService scientificResearchService;

    @GetMapping("/list/{categoryId}")
    public CommonResponse<Page<ScientificResearchDetail>> getSRDetialByCategoryId(@PathVariable("categoryId") Long categoryId,
                                                  @RequestParam("pageNum") Integer pageNum,
                                                  @RequestParam("pageSize") Integer pageSize){
        Page<ScientificResearchDetail> scientificResearchDetails =
                scientificResearchService.getSRDetialByCategoryId(categoryId, pageNum, pageSize);
        return ResponseUtil.success(scientificResearchDetails);
    }

    @GetMapping("/{id}")
    public CommonResponse<ScientificResearchDetail> getById(@PathVariable("id")Long id){
        ScientificResearchDetail scientificResearchDetail = scientificResearchService.getById(id);
        return ResponseUtil.success(scientificResearchDetail);
    }

    @GetMapping("/all/category")
    public CommonResponse<List<ScientificResearchCategory>> getAllCategory(){
        List<ScientificResearchCategory> allCategory = scientificResearchService.getAllCategory();
        return ResponseUtil.success(allCategory);
    }

    @GetMapping("/all/academicCategory")
    public CommonResponse<List<AcademicCategory>> getAllAcademicCategory(){
        List<AcademicCategory> allAcademicCategory = scientificResearchService.getAllAcademicCategory();
        return ResponseUtil.success(allAcademicCategory);
    }


    @PostMapping("/")
    public CommonResponse save(@RequestBody ScientificResearchDetail scientificResearchDetail){
        scientificResearchService.saveOrPublish(scientificResearchDetail, CommonConstants.UNPUBLISHED);
        return ResponseUtil.success();
    }

    @PostMapping("/publish")
    public CommonResponse publishById(@RequestBody ScientificResearchDetail scientificResearchDetail){
        scientificResearchService.saveOrPublish(scientificResearchDetail,CommonConstants.PUBLISHED);
        return ResponseUtil.success();
    }

}
