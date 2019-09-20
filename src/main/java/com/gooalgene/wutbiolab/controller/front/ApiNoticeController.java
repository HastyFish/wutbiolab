package com.gooalgene.wutbiolab.controller.front;

import com.gooalgene.wutbiolab.entity.notice.NoticeCategory;
import com.gooalgene.wutbiolab.entity.notice.NoticeOverview;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.front.NoticeResponse;
import com.gooalgene.wutbiolab.service.NoticeService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "前台通知", tags = {"前台通知接口"})
@RestController
@RequestMapping("/api/notice")
public class ApiNoticeController {

    private NoticeService noticeService;

    public ApiNoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/category")
    public CommonResponse<List<NoticeCategory>> categoryList() {
        return noticeService.allNoticeCategory();
    }

    @GetMapping
    public CommonResponse<PageResponse<NoticeOverview>> newsDetailByCategory(Integer categoryId,
                                                                             Integer pageNum,
                                                                             Integer pageSize) {
        return noticeService.noticeDetailPageByCategory(categoryId, pageNum, pageSize);
    }

    @GetMapping("/{id}")
    public CommonResponse<NoticeResponse> newsDetailById(@PathVariable long id) {
        return noticeService.noticeDetailPublishedById(id);
    }

}
