package com.gooalgene.wutbiolab.service.impl;

import com.gooalgene.wutbiolab.dao.notice.NoticeCategoryDAO;
import com.gooalgene.wutbiolab.dao.notice.NoticeDetailDAO;
import com.gooalgene.wutbiolab.entity.notice.NoticeCategory;
import com.gooalgene.wutbiolab.entity.notice.NoticeDetail;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.service.NoticeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public CommonResponse<PageResponse<NoticeDetail>> noticeDetailPage(Integer pageNum, Integer pageSize) {
        Page<NoticeDetail> page = noticeDetailDAO.findAll(PageRequest.of(pageNum - 1, pageSize));
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
}
