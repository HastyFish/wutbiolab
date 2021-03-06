package com.gooalgene.wutbiolab.controller.front;

import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import com.gooalgene.wutbiolab.entity.scientificResearch.AcademicCategory;
import com.gooalgene.wutbiolab.entity.scientificResearch.ScientificResearchCategory;
import com.gooalgene.wutbiolab.entity.scientificResearch.ScientificResearchDetail;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.service.LabService;
import com.gooalgene.wutbiolab.service.ScientificResearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(value = "前端科研工作模块", tags = {"前端科研工作模块接口"})
@RestController
@RequestMapping("/api/scientificResearch")
public class ApiScientificResearchController {
    @Autowired
    private ScientificResearchService scientificResearchService;


    @GetMapping("/list/{categoryId}")
    public CommonResponse<PageResponse<ScientificResearchDetail>> getLabDetailByLabCategoryIdAndPublishStatus(@PathVariable("categoryId")Long categoryId,
                                @RequestParam("pageNum")Integer pageNum,@RequestParam("pageSize")Integer pageSize){
        PageResponse<ScientificResearchDetail> scientificResearchDetails =
                scientificResearchService.getPublishedByCategoryId(categoryId, pageNum, pageSize);
        return ResponseUtil.success(scientificResearchDetails);
    }

    @GetMapping("/{id}")
    public CommonResponse<Map<String,Object>> getPublishedById(@PathVariable("id") Long id){
        Map<String,Object> map = scientificResearchService.getPublishedById(id);
        return ResponseUtil.success(map);
    }

    @ApiOperation(value="获取所有一级分类", notes="获取所有一级分类")
    @GetMapping("/all/category")
    public CommonResponse<List<ScientificResearchCategory>> getAllCategory(){
        List<ScientificResearchCategory> allCategory = scientificResearchService.getAllCategory();
        return ResponseUtil.success(allCategory);
    }

    @GetMapping("/one/{categoryId}")
    public CommonResponse<ScientificResearchDetail> getOneLabDetail(@PathVariable("categoryId")Long labCategoryId){
        PageResponse<ScientificResearchDetail> labDetails = scientificResearchService.getScientificResearchDetailByLabCategoryIdAndPublishStatus(labCategoryId, null, null,CommonConstants.PUBLISHED);
        ScientificResearchDetail scientificResearchDetail=new ScientificResearchDetail();
        if(labDetails!=null){
            List<ScientificResearchDetail> content = labDetails.getList();
            if(content!=null&&!content.isEmpty()){
                scientificResearchDetail = content.get(0);
                scientificResearchDetail.setFirstCategory(CommonConstants.CATEGORY_SCIENTIFICRESEARCH);
            }
        }
        ScientificResearchCategory scientificResearchCategory = scientificResearchService.getScientificResearchCategoryById(labCategoryId);
        if(scientificResearchCategory!=null){
            scientificResearchDetail.setCategory(scientificResearchCategory.getCategory());
        }
        return ResponseUtil.success(scientificResearchDetail);
    }





}
