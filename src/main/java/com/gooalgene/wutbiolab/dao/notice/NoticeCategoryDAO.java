package com.gooalgene.wutbiolab.dao.notice;

import com.gooalgene.wutbiolab.entity.notice.NoticeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeCategoryDAO extends JpaRepository<NoticeCategory, Long> {
}
