package com.gooalgene.wutbiolab.service.impl;

import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.dao.notice.NoticeCategoryDAO;
import com.gooalgene.wutbiolab.dao.notice.NoticeDetailDAO;
import com.gooalgene.wutbiolab.entity.notice.NoticeCategory;
import com.gooalgene.wutbiolab.entity.notice.NoticeDetail;
import com.gooalgene.wutbiolab.entity.notice.NoticeOverview;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.response.front.DetailPageResponse;
import com.gooalgene.wutbiolab.response.front.DetailResponse;
import com.gooalgene.wutbiolab.service.NoticeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    private NoticeDetailDAO noticeDetailDAO;

    private NoticeCategoryDAO noticeCategoryDAO;

    public NoticeServiceImpl(NoticeDetailDAO noticeDetailDAO, NoticeCategoryDAO noticeCategoryDAO) {
        this.noticeCategoryDAO = noticeCategoryDAO;
        this.noticeDetailDAO = noticeDetailDAO;
    }

    @Override
    public CommonResponse<PageResponse<NoticeOverview>> noticeDetailPage(Integer pageNum, Integer pageSize) {
//        Page<NoticeDetail> page = noticeDetailDAO.findAll(PageRequest.of(pageNum - 1, pageSize));
        Page<NoticeOverview> page = noticeDetailDAO.findNewsDetailBy(PageRequest.of(pageNum - 1, pageSize));
        return ResponseUtil.success(new PageResponse<>(page.getContent(), pageNum, pageSize, page.getTotalElements()));
    }

    @Override
    public CommonResponse<List<NoticeCategory>> allNoticeCategory() {
        return ResponseUtil.success(noticeCategoryDAO.findAll());
    }

    @Override
    public CommonResponse<NoticeDetail> noticeDetailById(Integer id) {
        if (null != id && noticeDetailDAO.findById(id.longValue()).isPresent()) {
            return ResponseUtil.success(noticeDetailDAO.findById(id.longValue()).get());
        } else {
            return ResponseUtil.error("No such notice");
        }
    }

    @Override
    @Transactional
    public CommonResponse<Boolean> renewNoticeDetail(NoticeDetail noticeDetail) {
        noticeDetailDAO.save(noticeDetail);
        return ResponseUtil.success(true);
    }

    @Override
    @Transactional
    public CommonResponse<Boolean> deleteById(Integer id) {
        try {
            noticeDetailDAO.deleteById(id.longValue());
            return ResponseUtil.success(true);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error("id is null");
        }
    }

    @Override
    public CommonResponse<DetailPageResponse<NoticeOverview>> noticeDetailPageByCategory(Integer categoryId, Integer pageNum, Integer pageSize) {
        if (noticeCategoryDAO.findById(categoryId.longValue()).isPresent()) {
            NoticeCategory noticeCategory = noticeCategoryDAO.findById(categoryId.longValue()).get();
            Page<NoticeOverview> noticeOverviewPage = noticeDetailDAO.findByCategoryAndPublishStatusPage(
                    noticeCategory.getCategory(),
                    CommonConstants.PUBLISHED,
                    PageRequest.of(pageNum - 1, pageSize));
            return ResponseUtil.success(new DetailPageResponse<>(noticeOverviewPage.getContent(), pageNum,
                    pageSize, noticeOverviewPage.getTotalElements(), noticeCategory.getCategory()));
        } else {
            return ResponseUtil.error("wrong id");
        }
    }

    @Override
    public CommonResponse<DetailResponse<NoticeDetail, NoticeOverview>> noticeDetailPublishedById(long id) {
        NoticeDetail newsDetail = noticeDetailDAO.findByIdAndPublishStatus(id, CommonConstants.PUBLISHED);
        NoticeOverview next = nextPublishedNewsDetail(newsDetail.getPublishDate(), newsDetail.getCategory());
        NoticeOverview previous = previousPublishedNewsDetail(newsDetail.getPublishDate(), newsDetail.getCategory());
        return ResponseUtil.success(new DetailResponse<>(newsDetail, previous, next));
    }

    private NoticeOverview nextPublishedNewsDetail(long publishDate, String category) {
        Page<NoticeOverview> newsDetailPage = noticeDetailDAO.findNextNoticeDetail(publishDate, category, CommonConstants.PUBLISHED,
                PageRequest.of(0, 1, new Sort(Sort.Direction.ASC, CommonConstants.PUBLISHDATEFIELD)));
        if (newsDetailPage.getTotalElements() > 0) {
            return newsDetailPage.getContent().get(0);
        } else {
            return null;
        }
    }

    private NoticeOverview previousPublishedNewsDetail(long publishDate, String category) {
        Page<NoticeOverview> newsDetailPage = noticeDetailDAO.findPreviousNoticeDetail(publishDate, category, CommonConstants.PUBLISHED,
                PageRequest.of(0, 1, new Sort(Sort.Direction.DESC, CommonConstants.PUBLISHDATEFIELD)));
        if (newsDetailPage.getTotalElements() > 0) {
            return newsDetailPage.getContent().get(0);
        } else {
            return null;
        }
    }
}
