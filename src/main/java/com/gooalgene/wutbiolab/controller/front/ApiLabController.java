package com.gooalgene.wutbiolab.controller.front;

import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.entity.lab.LabCategory;
import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import com.gooalgene.wutbiolab.response.MentorResponse;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.response.front.DetailPageResponse;
import com.gooalgene.wutbiolab.service.LabService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Api(value = "前端实验室模块", tags = {"前端实验室模块接口"})
@RestController
@RequestMapping("/api/lab")
public class ApiLabController {
    @Autowired
    private LabService labService;

    @ApiOperation(value="获取所有一级分类", notes="获取所有一级分类")
    @GetMapping("/all/category")
    public CommonResponse<List<LabCategory>> getAllCategory(){
        List<LabCategory> allCategory = labService.getAllCategory();
        return ResponseUtil.success(allCategory);
    }

    @ApiOperation(value="通过一级分类的id查询列表", notes="通过一级分类的id查询列表")
    //查询列表（分页）
    @GetMapping("/list/{categoryId}")
    public CommonResponse<PageResponse<LabDetail>> getLabDetailByLabCategoryIdAndPublishStatus(@PathVariable("categoryId")Long labCategoryId,
                                                                                               @RequestParam("pageNum")Integer pageNum,
                                                                                               @RequestParam("pageSize")Integer pageSize){
        DetailPageResponse<LabDetail> labDetailPage =
                labService.getLabDetailByLabCategoryIdAndPublishStatus(labCategoryId, pageNum, pageSize,
                        CommonConstants.PUBLISHED,true);
        LabCategory labCategory = labService.getCategoryById(labCategoryId);
        String category = labCategory!=null ? labCategory.getCategory() : null;
        if (labDetailPage != null) {
            labDetailPage.setCategory(category);
        }
        return ResponseUtil.success(labDetailPage);
    }

    @ApiOperation(value="通过一级分类的id查询一条数据", notes="通过一级分类的id查询一条数据（目前针对机构概况和研究方向）")
    //通过分类id查询子模块（只包含一条数据的子模块）
    @GetMapping("/one/{categoryId}")
    public CommonResponse<LabDetail> getOneLabDetail(@PathVariable("categoryId")Long labCategoryId){
        PageResponse<LabDetail> labDetails =
                labService.getLabDetailByLabCategoryIdAndPublishStatus(labCategoryId,
                        null, null, CommonConstants.PUBLISHED,false);
        LabDetail labDetail=null;
        if(labDetails!=null){
            List<LabDetail> content = labDetails.getList();
            if(content!=null&&!content.isEmpty()){
                labDetail = content.get(0);
                LabCategory category = labService.getCategoryById(labCategoryId);
                labDetail.setCategory(category!=null?category.getCategory():null);
                labDetail.setFirstCategory(CommonConstants.CATEGORY_LAB);
            }
        }
        return ResponseUtil.success(labDetail);
    }

    @ApiOperation(value="通过id查询一条已发布数据", notes="通过id查询一条已发布数据")
    //通过id查询一条数据(已发布)
    @GetMapping("/{id}")
    public CommonResponse<Map<String, Object>> getPublishedById(@PathVariable("id")Long id){
        Map<String, Object> publishedById = labService.getPublishedById(id);
        return ResponseUtil.success(publishedById);
    }

    @ApiOperation(value="查询研究团队所有已发布数据", notes="查询研究团队所有已发布数据")
    @GetMapping("/researchTeam/{categoryId}")
    public CommonResponse<Map<String,Object>> getPublishedResearchTeam(@PathVariable("categoryId")Long categoryId){
        Map<String,Object> map=new HashMap<>();
        LabCategory labCategory= labService.getCategoryById(categoryId);
        String category=labCategory!=null?labCategory.getCategory():null;
        List<MentorResponse> researchTeam = labService.getPublishedResearchTeam();
        map.put("category",category);
        map.put("list",researchTeam);
        return ResponseUtil.success(map);
    }




}
