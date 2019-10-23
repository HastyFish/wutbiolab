package com.gooalgene.wutbiolab.controller.front;


import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.entity.contactus.ContactusCategory;
import com.gooalgene.wutbiolab.entity.contactus.ContactusDetail;
import com.gooalgene.wutbiolab.entity.lab.LabCategory;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.service.ContactusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "加入我们", tags = {"加入我们的接口"})
@RestController
@RequestMapping("/api/contactus")
public class ApiContactusController {


    @Autowired
    private ContactusService contactusService;

    @ApiOperation(value="获取所有一级分类", notes="获取所有一级分类")
    @GetMapping("/all/category")
    public CommonResponse<List<ContactusCategory>> getAllCategory(){
        List<ContactusCategory> allCategory = contactusService.getAllCategory();
        return ResponseUtil.success(allCategory);
    }


    //通过分类id查询子模块（只包含一条数据的子模块）
    @ApiOperation(value="通过一级分类的id查询一条数据（只包含一条数据的子模块）", notes="通过一级分类的id查询一条数据（目前针对实验室、科研工作和加入我们）")
    @GetMapping("/one/{categoryId}")
    public CommonResponse<ContactusDetail> getOneLabDetail(@PathVariable("categoryId")Long labCategoryId){
        PageResponse<ContactusDetail> labDetails =
                contactusService.getContactusDetailByLabCategoryIdAndPublishStatus(labCategoryId, null, null, CommonConstants.PUBLISHED);
        ContactusDetail contactusDetail=new ContactusDetail();
        ContactusCategory category = contactusService.getContactusCategoryById(labCategoryId);
        String categoryName = category != null ? category.getCategory() : null;
        if(labDetails!=null){
            List<ContactusDetail> content = labDetails.getList();
            if(content!=null&&!content.isEmpty()){
                contactusDetail = content.get(0);
                contactusDetail.setFirstCategory(CommonConstants.CATEGORY_CONTACTUS);
            }
        }
        contactusDetail.setCategory(categoryName);
        return ResponseUtil.success(contactusDetail);
    }
}
