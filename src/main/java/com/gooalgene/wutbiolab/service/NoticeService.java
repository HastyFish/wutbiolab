package com.gooalgene.wutbiolab.service;

import com.gooalgene.wutbiolab.entity.notice.NoticeCategory;
import com.gooalgene.wutbiolab.entity.notice.NoticeDetail;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;

import java.util.List;

public interface NoticeService {
    CommonResponse<PageResponse<NoticeDetail>> noticeDetailPage(Integer pageNum, Integer pageSize);

    CommonResponse<List<NoticeCategory>> allNewsCategory();

    CommonResponse<NoticeDetail> noticeDetailById(Integer id);

    CommonResponse<Boolean> renewNoticeDetail(NoticeDetail noticeDetail);
}
