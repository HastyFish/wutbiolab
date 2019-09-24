package com.gooalgene.wutbiolab.controller.admin;

import com.gooalgene.wutbiolab.entity.notice.NoticeCategory;
import com.gooalgene.wutbiolab.entity.notice.NoticeDetail;
import com.gooalgene.wutbiolab.entity.notice.NoticeOverview;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "后台通知", tags = {"后台通知接口"})
@RestController
@RequestMapping("/notice")
public class NoticeController {

    private NoticeService noticeService;

    private NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @ApiOperation(value="查询通知分类", notes="查询通知分类")
    @GetMapping("/category")
    public CommonResponse<List<NoticeCategory>> getNoticeCategory() {
        return noticeService.allNoticeCategory();
    }

    @GetMapping
    public CommonResponse<PageResponse<NoticeOverview>> getNoticeDetailPage(Integer pageNum, Integer pageSize,
                                                                            Long categoryId) {
        return noticeService.noticeDetailPage(pageNum, pageSize, categoryId);
    }

    @ApiOperation(value="查询通知", notes="根据id查询通知")
    @GetMapping("/{id}")
    public CommonResponse<NoticeDetail> getNoticeDetail(@PathVariable Integer id) {
        return noticeService.noticeDetailById(id);
    }

    @ApiOperation(value="保存通知", notes="保存通知")
    @PostMapping
    public CommonResponse<Boolean> renewNoticeDetail(@RequestBody NoticeDetail noticeDetail) {
        return noticeService.renewNoticeDetail(noticeDetail);
    }

    @ApiOperation(value="根据id删除通知", notes="根据id删除通知")
    @DeleteMapping("/{id}")
    public CommonResponse<Boolean> deleteNoticeDetail(@PathVariable Integer id) {
        return noticeService.deleteById(id);
    }
}
