package com.gooalgene.wutbiolab.service;

import com.gooalgene.wutbiolab.entity.notice.NoticeCategory;
import com.gooalgene.wutbiolab.entity.notice.NoticeDetail;
import com.gooalgene.wutbiolab.entity.notice.NoticeOverview;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.front.NoticeResponse;

import java.util.List;

public interface NoticeService {
    CommonResponse<PageResponse<NoticeOverview>> noticeDetailPage(Integer pageNum, Integer pageSize);

    CommonResponse<List<NoticeCategory>> allNoticeCategory();

    CommonResponse<NoticeDetail> noticeDetailById(Integer id);

    CommonResponse<Boolean> renewNoticeDetail(NoticeDetail noticeDetail);

    CommonResponse<Boolean> deleteById(Integer id);

    CommonResponse<PageResponse<NoticeOverview>> noticeDetailPageByCategory(String category, Integer pageNum, Integer pageSize);

    CommonResponse<NoticeResponse> noticeDetailPublishedById(long id);
}
