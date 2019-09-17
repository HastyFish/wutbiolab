package com.gooalgene.wutbiolab.controller.admin;

import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.entity.lab.GraduateCategory;
import com.gooalgene.wutbiolab.entity.lab.LabCategory;
import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import com.gooalgene.wutbiolab.entity.lab.MentorCategory;
import com.gooalgene.wutbiolab.response.MentorResponse;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.service.LabService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "后端实验室模块", tags = {"后端实验室模块接口"})
@RestController
@RequestMapping("/lab")
public class LabController {
    @Autowired
    private LabService labService;


//    @ApiOperation(value="通过模块分类id查询分页列表", notes="通过模块分类id查询分页列表，参数为模块分类id，pageNum和pageSize")
//    @GetMapping("/list/{categoryId}")
//    public CommonResponse<Page<LabDetail>> getListByCategoryId(@PathVariable("categoryId")Long categoryId,
//                                              @RequestParam("pageNum") Integer pageNum,
//                                              @RequestParam("pageSize") Integer pageSize){
//        Page<LabDetail> labDetails = labService.getLabDetailByLabCategoryId(categoryId, pageNum, pageSize);
//        return ResponseUtil.success(labDetails);
//    }

    @ApiOperation(value="通过一级分类的id查询一条数据（只包含一条数据的子模块）", notes="通过一级分类的id查询一条数据（目前针对机构概况和研究方向）")
    @GetMapping("/one/{labCategoryId}")
    public CommonResponse<LabDetail> getOneLabDetail(@PathVariable("labCategoryId")Long labCategoryId){
        Page<LabDetail> labDetails =
                labService.getLabDetailByLabCategoryId(labCategoryId,
                        null, null,false);
        LabDetail labDetail=null;
        if(labDetails!=null){
            List<LabDetail> content = labDetails.getContent();
            if(content!=null&&!content.isEmpty()){
                labDetail = content.get(0);
            }
        }
        return ResponseUtil.success(labDetail);
    }


    @ApiOperation(value="查询毕业生分页列表", notes="查询毕业生分页列表，参数为pageNum和pageSize")
    @GetMapping("/graduate")
    public CommonResponse<PageResponse<LabDetail>> getListByCategoryId(@RequestParam("pageNum") Integer pageNum,
                                                               @RequestParam("pageSize") Integer pageSize) {
        PageResponse<LabDetail> graduates = labService.getGraduates(pageNum, pageSize);
        return ResponseUtil.success(graduates);
    }

    @ApiOperation(value="通过id查询单条数据", notes="通过id查询单条数据")
    @GetMapping("/{id}")
    public CommonResponse<LabDetail> getById(@PathVariable("id")Long id){
        LabDetail byId = labService.getById(id);
        return ResponseUtil.success(byId);
    }

    @ApiOperation(value="获取研究团队所有数据", notes="获取研究团队所有数据")
    @GetMapping("/researchTeam")
    public CommonResponse<List<MentorResponse>> getResearchTeam(){
        List<MentorResponse> researchTeam = labService.getResearchTeam();
        return ResponseUtil.success(researchTeam);
    }

    @ApiOperation(value="保存一条数据", notes="保存一条数据")
    @PostMapping
    public CommonResponse saveLabDetail(@RequestBody LabDetail labDetail){
        labService.saveOrPublishLabDetail(labDetail, CommonConstants.UNPUBLISHED);
        return ResponseUtil.success();
    }

    @ApiOperation(value="研究团队排序", notes="研究团队排序")
    @PostMapping("/researchTeam/sort")
    public CommonResponse saveLabDetails(@RequestBody List<LabDetail> labDetails){
        labService.saveList(labDetails);
        return ResponseUtil.success();
    }

    @PostMapping("/publish")
    public CommonResponse publishLabDetail(@RequestBody LabDetail labDetail){
        labService.saveOrPublishLabDetail(labDetail, CommonConstants.PUBLISHED);
        return ResponseUtil.success();
    }

//    @ApiOperation(value="发布研究团队所有数据", notes="发布研究团队所有数据(并且排好序)")
//    @PostMapping("/researchTeam/publish")
//    public CommonResponse publishResearchTeam(@RequestBody List<MentorRequest> mentorRequests){
//        labService.publishResearchTeam(mentorRequests);
//        return ResponseUtil.success();
//    }
    @ApiOperation(value="通过一级分类id发布多条数据", notes="通过一级分类id发布多条数据")
    @PostMapping("/publish/{labCategoryId}")
    public CommonResponse publishList(@PathVariable("labCategoryId") Long labCategoryId){
        labService.publishByLabCategoryId(labCategoryId);
        return ResponseUtil.success();
    }

    @ApiOperation(value="保存多条导师类型的数据", notes="保存多条导师类型的数据")
    @PostMapping("/mentorCategorys")
    public CommonResponse saveMentorCategory(@RequestBody List<MentorCategory> mentorCategorys){
        labService.saveMentorCategory(mentorCategorys);
        return ResponseUtil.success();
    }

    @ApiOperation(value="获取所有导师类型", notes="获取所有导师类型")
    @GetMapping("/mentorCategorys")
    public CommonResponse<List<MentorCategory>> getMentorCategorys(){
        List<MentorCategory> mentorCategorys = labService.getMentorCategorys();
        return ResponseUtil.success(mentorCategorys);
    }

    @ApiOperation(value="获取所有毕业生类型", notes="获取所有毕业生类型")
    @GetMapping("/graduateCategorys")
    public CommonResponse<List<GraduateCategory>> getGraduateCategorys(){
        List<GraduateCategory> graduateCategorys = labService.getGraduateCategorys();
        return ResponseUtil.success(graduateCategorys);
    }

    @ApiOperation(value="获取所有一级分类", notes="获取所有一级分类")
    @GetMapping("/all/category")
    public CommonResponse<List<LabCategory>> getAllCategory(){
        List<LabCategory> allCategory = labService.getAllCategory();
        return ResponseUtil.success(allCategory);
    }

    @ApiOperation(value="通过id删除一条记录", notes="通过id删除一条记录")
    @DeleteMapping("/{id}")
    public CommonResponse deleteById(@PathVariable("id")Long id){
        labService.deleteById(id);
        return ResponseUtil.success();
    }

    @ApiOperation(value="通过id删除一条导师类型数据", notes="通过id删除一条导师类型数据")
    @DeleteMapping("/mentorCategory/{id}")
    public CommonResponse deleteMentorCategoryById(@PathVariable("id")Long id){
        labService.deleteMentorCategoryById(id);
        return ResponseUtil.success();
    }




}
