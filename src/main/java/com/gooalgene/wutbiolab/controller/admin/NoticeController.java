package com.gooalgene.wutbiolab.controller.admin;

import com.gooalgene.wutbiolab.entity.notice.NoticeDetail;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    @GetMapping
    public CommonResponse<PageResponse<NoticeDetail>> getNoticeDetailList(Integer pageNum, Integer pageSize) {
        return ResponseUtil.success(new PageResponse<>());
    }

    @GetMapping("/{id}")
    public CommonResponse<NoticeDetail> getNoticeDetail(@PathVariable Integer id) {
        return ResponseUtil.success(new NoticeDetail());
    }

    @PostMapping
    public CommonResponse<Boolean> renewNoticeDetail(NoticeDetail noticeDetail) {
        return ResponseUtil.success(true);
    }

    @DeleteMapping("/{id}")
    public CommonResponse<Boolean> deleteNoticeDetail(@PathVariable Integer id) {
        return ResponseUtil.success(true);
    }
}
