package com.gooalgene.wutbiolab.dao.notice;

import com.gooalgene.wutbiolab.entity.notice.NoticeDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeDetailDAO extends JpaRepository<NoticeDetail, Long> {
}
