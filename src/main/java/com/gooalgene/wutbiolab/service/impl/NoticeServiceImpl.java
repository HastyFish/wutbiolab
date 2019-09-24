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
    public CommonResponse<PageResponse<NoticeOverview>> noticeDetailPage(Integer pageNum, Integer pageSize,
                                                                         Long categoryId) {
//        Page<NoticeDetail> page = noticeDetailDAO.findAll(PageRequest.of(pageNum - 1, pageSize));
        Sort.Order categoryOrder = new Sort.Order(Sort.Direction.ASC, CommonConstants.CATEGORYIDFIELD);
        Sort.Order idOrder = new Sort.Order(Sort.Direction.ASC, CommonConstants.IDFIELD);
        Sort.Order daterder = new Sort.Order(Sort.Direction.DESC, CommonConstants.PUBLISHDATEFIELD);
        Page<NoticeOverview> page;
        if (null == categoryId) {
            page = noticeDetailDAO.findNewsDetailBy(PageRequest.of(pageNum - 1, pageSize,
                    Sort.by(daterder, categoryOrder, idOrder)));
        } else {
            page = noticeDetailDAO.findNewsDetailByCategoryId(categoryId, PageRequest.of(pageNum - 1, pageSize,
                    Sort.by(daterder, categoryOrder, idOrder)));
        }
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
        /*新增或保存时，查询前端返回的categoryId对应的category*/
        if (noticeCategoryDAO.findById(noticeDetail.getCategoryId()).isPresent()) {
            NoticeCategory resourceCategory = noticeCategoryDAO.findById(noticeDetail.getCategoryId()).get();
            noticeDetail.setCategory(resourceCategory.getCategory());
        } else {
            return ResponseUtil.error("Wrong category");
        }
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
                    PageRequest.of(pageNum - 1, pageSize,
                            Sort.by(
                                    new Sort.Order(Sort.Direction.DESC, CommonConstants.PUBLISHDATEFIELD),
                                    new Sort.Order(Sort.Direction.ASC, CommonConstants.IDFIELD)
                                    )
                            ));
            return ResponseUtil.success(new DetailPageResponse<>(noticeOverviewPage.getContent(), pageNum,
                    pageSize, noticeOverviewPage.getTotalElements(), noticeCategory.getCategory()));
        } else {
            return ResponseUtil.error("wrong id");
        }
    }

    @Override
    public CommonResponse<DetailResponse<NoticeDetail, NoticeOverview>> noticeDetailPublishedById(long id) {
        NoticeDetail noticeDetail = noticeDetailDAO.findByIdAndPublishStatus(id, CommonConstants.PUBLISHED);
        NoticeOverview next;
        NoticeOverview previous;
        if (noticeDetailDAO.countByPublishDateAndPublishStatus(
                noticeDetail.getPublishDate(), CommonConstants.PUBLISHED) > 1) {
            next = nextPublishedNoticeDetail(noticeDetail.getPublishDate(),
                    noticeDetail.getCategory(),
                    noticeDetail.getId());
            previous = previousPublishedNewsDetail(noticeDetail.getPublishDate(),
                    noticeDetail.getCategory(),
                    noticeDetail.getId());
        } else {
            next = nextPublishedNoticeDetail(noticeDetail.getPublishDate(),
                    noticeDetail.getCategory(),
                    null);
            previous = previousPublishedNewsDetail(noticeDetail.getPublishDate(),
                    noticeDetail.getCategory(),
                    null);
        }
        return ResponseUtil.success(new DetailResponse<>(noticeDetail, next, previous));
    }

    private NoticeOverview nextPublishedNoticeDetail(long publishDate, String category, Long id) {
        Page<NoticeOverview> newsDetailPage;
        if (null != id) {
            newsDetailPage = noticeDetailDAO.findNextNoticeDetail(publishDate, category,
                    CommonConstants.PUBLISHED,
                    id,
                    PageRequest.of(0, 1,
                            Sort.by(
                                    new Sort.Order(Sort.Direction.DESC, CommonConstants.PUBLISHDATEFIELD),
                                    new Sort.Order(Sort.Direction.ASC, CommonConstants.IDFIELD)
                            )
                    )
            );
        } else {
            newsDetailPage = noticeDetailDAO.findNextNoticeDetail(publishDate, category,
                    CommonConstants.PUBLISHED,
                    PageRequest.of(0, 1,
                            Sort.by(
                                    new Sort.Order(Sort.Direction.DESC, CommonConstants.PUBLISHDATEFIELD),
                                    new Sort.Order(Sort.Direction.ASC, CommonConstants.IDFIELD)
                            )
                    )
            );
        }
        if (newsDetailPage.getTotalElements() > 0) {
            return newsDetailPage.getContent().get(0);
        } else {
            return null;
        }
    }

    private NoticeOverview previousPublishedNewsDetail(long publishDate, String category, Long id) {
        Page<NoticeOverview> noticeDetailPage;
        if (null != id) {
            noticeDetailPage = noticeDetailDAO.findPreviousNoticeDetail(publishDate, category,
                    CommonConstants.PUBLISHED,
                    id,
                    PageRequest.of(0, 1,
                            Sort.by(
                                    new Sort.Order(Sort.Direction.ASC, CommonConstants.PUBLISHDATEFIELD),
                                    new Sort.Order(Sort.Direction.DESC, CommonConstants.IDFIELD)
                            )
                    )
            );
        } else {
            noticeDetailPage = noticeDetailDAO.findPreviousNoticeDetail(publishDate, category,
                    CommonConstants.PUBLISHED,
                    PageRequest.of(0, 1,
                            Sort.by(
                                    new Sort.Order(Sort.Direction.ASC, CommonConstants.PUBLISHDATEFIELD),
                                    new Sort.Order(Sort.Direction.DESC, CommonConstants.IDFIELD)
                            )
                    )
            );
        }
        if (noticeDetailPage.getTotalElements() > 0) {
            return noticeDetailPage.getContent().get(0);
        } else {
            return null;
        }
    }
}
