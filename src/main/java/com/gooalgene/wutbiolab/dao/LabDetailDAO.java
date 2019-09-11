package com.gooalgene.wutbiolab.dao;

import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabDetailDAO extends JpaRepository<LabDetail,Long> {

    List<LabDetail> getByLabCategoryId(Long labCategoryId);
    Page<LabDetail> getByLabCategoryId(Long labCategoryId, Pageable pageable);
}
