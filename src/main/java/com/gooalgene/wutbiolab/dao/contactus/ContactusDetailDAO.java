package com.gooalgene.wutbiolab.dao.contactus;

import com.gooalgene.wutbiolab.entity.contactus.ContactusDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactusDetailDAO extends JpaRepository<ContactusDetail, Long> {


    List<ContactusDetail> findByCategoryId(Long categoryId);

    Page<ContactusDetail> findByCategoryId(Long categoryId, Pageable pageable);


    List<ContactusDetail> findByCategoryIdAndPublishStatus(Long categoryId, Integer publishStatus);

    Page<ContactusDetail> findByCategoryIdAndPublishStatus(Long categoryId, Integer publishStatus, Pageable pageable);

}
