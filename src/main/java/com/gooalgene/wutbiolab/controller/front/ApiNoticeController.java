package com.gooalgene.wutbiolab.controller.front;

import com.gooalgene.wutbiolab.entity.notice.NoticeCategory;
import com.gooalgene.wutbiolab.entity.notice.NoticeOverview;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.front.DetailPageResponse;
import com.gooalgene.wutbiolab.response.front.NoticeResponse;
import com.gooalgene.wutbiolab.service.NoticeService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/list/{categoryId}")
    public CommonResponse<DetailPageResponse<NoticeOverview>> newsDetailByCategory(@PathVariable("categoryId") Integer categoryId,
                                                                                   @RequestParam("pageNum") Integer pageNum,
                                                                                   @RequestParam("pageSize") Integer pageSize) {
        return noticeService.noticeDetailPageByCategory(categoryId, pageNum, pageSize);
    }

    @GetMapping("/{id}")
    public CommonResponse<NoticeResponse> newsDetailById(@PathVariable long id) {
        return noticeService.noticeDetailPublishedById(id);
    }

}
