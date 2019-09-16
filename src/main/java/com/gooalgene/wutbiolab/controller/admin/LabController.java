package com.gooalgene.wutbiolab.controller.admin;

import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.entity.lab.GraduateCategory;
import com.gooalgene.wutbiolab.entity.lab.LabCategory;
import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import com.gooalgene.wutbiolab.entity.lab.MentorCategory;
import com.gooalgene.wutbiolab.request.MentorRequest;
import com.gooalgene.wutbiolab.response.MentorResponse;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.service.LabService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ApiOperation(value="查询毕业生分页列表", notes="查询毕业生分页列表，参数为pageNum和pageSize")
    @GetMapping("/graduate")
    public CommonResponse<PageResponse<LabDetail>> getListByCategoryId(@PathVariable("categoryId")Long categoryId,
                                                               @RequestParam("pageNum") Integer pageNum,
                                                               @RequestParam("pageSize") Integer pageSize) {
        PageResponse<LabDetail> graduates = labService.getGraduates(pageNum, pageSize);
        return ResponseUtil.success(graduates);
    }

    @GetMapping("/{id}")
    public CommonResponse<LabDetail> getById(@PathVariable("id")Long id){
        LabDetail byId = labService.getById(id);
        return ResponseUtil.success(byId);
    }

    @GetMapping("/researchTeam")
    public CommonResponse<List<MentorResponse>> getResearchTeam(){
        List<MentorResponse> researchTeam = labService.getResearchTeam();
        return ResponseUtil.success(researchTeam);
    }

    @PostMapping("/")
    public CommonResponse saveLabDetail(@RequestBody LabDetail labDetail){
        labService.saveOrPublishLabDetail(labDetail, CommonConstants.UNPUBLISHED);
        return ResponseUtil.success();
    }

    @PostMapping("/publish")
    public CommonResponse publishLabDetail(@RequestBody LabDetail labDetail){
        labService.saveOrPublishLabDetail(labDetail, CommonConstants.PUBLISHED);
        return ResponseUtil.success();
    }

    @PostMapping("/researchTeam/publish")
    public CommonResponse publishResearchTeam(@RequestBody List<MentorRequest> mentorRequests){
        labService.publishResearchTeam(mentorRequests);
        return ResponseUtil.success();
    }

    @PostMapping("/mentorCategory")
    public CommonResponse saveMentorCategory(@RequestBody MentorCategory mentorCategory){
        labService.saveMentorCategory(mentorCategory);
        return ResponseUtil.success();
    }

    @GetMapping("/mentorCategorys")
    public CommonResponse<List<MentorCategory>> getMentorCategorys(){
        List<MentorCategory> mentorCategorys = labService.getMentorCategorys();
        return ResponseUtil.success(mentorCategorys);
    }

    @GetMapping("/graduateCategorys")
    public CommonResponse<List<GraduateCategory>> getGraduateCategorys(){
        List<GraduateCategory> graduateCategorys = labService.getGraduateCategorys();
        return ResponseUtil.success(graduateCategorys);
    }

    @GetMapping("/all/category")
    public CommonResponse<List<LabCategory>> getAllCategory(){
        List<LabCategory> allCategory = labService.getAllCategory();
        return ResponseUtil.success(allCategory);
    }






}
