package com.gooalgene.wutbiolab.controller.admin;

import com.gooalgene.wutbiolab.entity.notice.NoticeCategory;
import com.gooalgene.wutbiolab.entity.notice.NoticeDetail;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.service.NoticeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    private NoticeService noticeService;

    private NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/category")
    public CommonResponse<List<NoticeCategory>> getNoticeCategory() {
        return noticeService.allNoticeCategory();
    }

    @GetMapping
    public CommonResponse<PageResponse<NoticeDetail>> getNoticeDetailPage(Integer pageNum, Integer pageSize) {
        return noticeService.noticeDetailPage(pageNum, pageSize);
    }

    @GetMapping("/{id}")
    public CommonResponse<NoticeDetail> getNoticeDetail(@PathVariable Integer id) {
        return noticeService.noticeDetailById(id);
    }

    @PostMapping
    public CommonResponse<Boolean> renewNoticeDetail(@RequestBody NoticeDetail noticeDetail) {
        return noticeService.renewNoticeDetail(noticeDetail);
    }

    @DeleteMapping("/{id}")
    public CommonResponse<Boolean> deleteNoticeDetail(@PathVariable Integer id) {
        return noticeService.deleteById(id);
    }
}
