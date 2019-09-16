package com.gooalgene.wutbiolab.controller.front;

import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import com.gooalgene.wutbiolab.response.MentorResponse;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.service.LabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lab")
public class ApiLabController {
    @Autowired
    private LabService labService;


    //查询列表（分页）
    @GetMapping("/list/{labCategoryId}")
    public CommonResponse<Page<LabDetail>> getLabDetailByLabCategoryIdAndPublishStatus(@PathVariable("labCategoryId")Long labCategoryId,
                                @RequestParam("pageNum")Integer pageNum,@RequestParam("pageSize")Integer pageSize){
        Page<LabDetail> labDetails =
                labService.getLabDetailByLabCategoryIdAndPublishStatus(labCategoryId, pageNum, pageSize,
                        CommonConstants.PUBLISHED,true);
        return ResponseUtil.success(labDetails);
    }

    //通过分类id查询子模块（只包含一条数据的子模块）
    @GetMapping("/one/{labCategoryId}")
    public CommonResponse<LabDetail> getOneLabDetail(@PathVariable("labCategoryId")Long labCategoryId){
        Page<LabDetail> labDetails =
                labService.getLabDetailByLabCategoryIdAndPublishStatus(labCategoryId,
                        null, null, CommonConstants.PUBLISHED,false);
        LabDetail labDetail=null;
        if(labDetails!=null){
            List<LabDetail> content = labDetails.getContent();
            if(content!=null&&!content.isEmpty()){
                labDetail = content.get(0);
            }
        }
        return ResponseUtil.success(labDetail);
    }

    //通过id查询一条数据(已发布)
    @GetMapping("/{id}")
    public CommonResponse<LabDetail> getPublishedById(@PathVariable("id")Long id){
        LabDetail publishedById = labService.getPublishedById(id);
        return ResponseUtil.success(publishedById);
    }

    @GetMapping("/researchTeam")
    public CommonResponse<List<MentorResponse>> getPublishedResearchTeam(){
        List<MentorResponse> researchTeam = labService.getPublishedResearchTeam();
        return ResponseUtil.success(researchTeam);
    }




}
