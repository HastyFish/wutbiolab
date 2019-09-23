package com.gooalgene.wutbiolab.service;

import com.gooalgene.wutbiolab.entity.notice.NoticeCategory;
import com.gooalgene.wutbiolab.entity.notice.NoticeDetail;
import com.gooalgene.wutbiolab.entity.notice.NoticeOverview;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.front.DetailPageResponse;
import com.gooalgene.wutbiolab.response.front.DetailResponse;

import java.util.List;

public interface NoticeService {
    CommonResponse<PageResponse<NoticeOverview>> noticeDetailPage(Integer pageNum, Integer pageSize, Long categoryId);

    CommonResponse<List<NoticeCategory>> allNoticeCategory();

    CommonResponse<NoticeDetail> noticeDetailById(Integer id);

    CommonResponse<Boolean> renewNoticeDetail(NoticeDetail noticeDetail);

    CommonResponse<Boolean> deleteById(Integer id);

    CommonResponse<DetailPageResponse<NoticeOverview>> noticeDetailPageByCategory(Integer categoryId, Integer pageNum, Integer pageSize);

    CommonResponse<DetailResponse<NoticeDetail, NoticeOverview>> noticeDetailPublishedById(long id);
}
