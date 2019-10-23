package com.gooalgene.wutbiolab.controller.admin;


import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.entity.contactus.ContactusDetail;
import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.service.ContactusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "加入我们的后台", tags = {"加入我们的后台接口"})
@RestController
@RequestMapping("/contactus")
public class ContactusController {

    @Autowired
    private ContactusService contactusService;

    @ApiOperation(value="保存一条数据", notes="保存一条数据")
    @PostMapping
    public CommonResponse saveLabDetail(@RequestBody ContactusDetail contactusDetail){
        contactusService.saveOrPublishLabDetail(contactusDetail, CommonConstants.UNPUBLISHED);
        return ResponseUtil.success();
    }


    @ApiOperation(value="发布消息", notes="发布消息")
    @PostMapping("/publish")
    public CommonResponse publishLabDetail(@RequestBody ContactusDetail contactusDetail){
        contactusService.saveOrPublishLabDetail(contactusDetail, CommonConstants.PUBLISHED);
        return ResponseUtil.success();
    }


    @ApiOperation(value="通过一级分类的id查询一条数据（只包含一条数据的子模块）", notes="通过一级分类的id查询一条数据（目前针对实验室、科研工作和加入我们）")
    @GetMapping("/one/{categoryId}")
    public CommonResponse<ContactusDetail> getOneLabDetail(@PathVariable("categoryId")Long labCategoryId){
        PageResponse<ContactusDetail> labDetails = contactusService.getContactusDetailByLabCategoryId(labCategoryId, null, null);
        ContactusDetail labDetail=null;
        if(labDetails!=null){
            List<ContactusDetail> content = labDetails.getList();
            if(content!=null&&!content.isEmpty()){
                labDetail = content.get(0);
            }
        }
        return ResponseUtil.success(labDetail);
    }

    
}
