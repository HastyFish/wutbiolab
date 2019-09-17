package com.gooalgene.wutbiolab.controller.admin;

import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.entity.scientificResearch.AcademicCategory;
import com.gooalgene.wutbiolab.entity.scientificResearch.ScientificResearchCategory;
import com.gooalgene.wutbiolab.entity.scientificResearch.ScientificResearchDetail;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.service.ScientificResearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "后端科研工作模块", tags = {"后端科研工作模块接口"})
@RestController
@RequestMapping("/scientificResearch")
public class ScientificResearchController {
    @Autowired
    private ScientificResearchService scientificResearchService;

    @ApiOperation(value="通过分类id查询对应子模块列表", notes="通过分类id查询对应子模块列表")
    @GetMapping("/list/{categoryId}")
    public CommonResponse<PageResponse<ScientificResearchDetail>> getSRDetialByCategoryId(@PathVariable("categoryId") Long categoryId,
                                                  @RequestParam("pageNum") Integer pageNum,
                                                  @RequestParam("pageSize") Integer pageSize){
        PageResponse<ScientificResearchDetail> scientificResearchDetails =
                scientificResearchService.getSRDetialByCategoryId(categoryId, pageNum, pageSize);
        return ResponseUtil.success(scientificResearchDetails);
    }

    @ApiOperation(value="通过id获取一条详情", notes="通过id获取一条详情")
    @GetMapping("/{id}")
    public CommonResponse<ScientificResearchDetail> getById(@PathVariable("id")Long id){
        ScientificResearchDetail scientificResearchDetail = scientificResearchService.getById(id);
        return ResponseUtil.success(scientificResearchDetail);
    }

    @ApiOperation(value="获取所有一级分类", notes="获取所有一级分类")
    @GetMapping("/all/category")
    public CommonResponse<List<ScientificResearchCategory>> getAllCategory(){
        List<ScientificResearchCategory> allCategory = scientificResearchService.getAllCategory();
        return ResponseUtil.success(allCategory);
    }

    @ApiOperation(value="获取所有学术分类", notes="获取所有学术分类")
    @GetMapping("/all/academicCategory")
    public CommonResponse<List<AcademicCategory>> getAllAcademicCategory(){
        List<AcademicCategory> allAcademicCategory = scientificResearchService.getAllAcademicCategory();
        return ResponseUtil.success(allAcademicCategory);
    }


    @ApiOperation(value="保存一条数据", notes="保存一条数据")
    @PostMapping
    public CommonResponse save(@RequestBody ScientificResearchDetail scientificResearchDetail){
        scientificResearchService.saveOrPublish(scientificResearchDetail, CommonConstants.UNPUBLISHED);
        return ResponseUtil.success();
    }

    @ApiOperation(value="发布一条数据", notes="发布一条数据")
    @PostMapping("/publish")
    public CommonResponse publish(@RequestBody ScientificResearchDetail scientificResearchDetail){
        scientificResearchService.saveOrPublish(scientificResearchDetail,CommonConstants.PUBLISHED);
        return ResponseUtil.success();
    }

    @DeleteMapping("/{id}")
    public CommonResponse deleteById(@PathVariable("id") Long id){
        scientificResearchService.deleteById(id);
        return ResponseUtil.success();
    }

}
