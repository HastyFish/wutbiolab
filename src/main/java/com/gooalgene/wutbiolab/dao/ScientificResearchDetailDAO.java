package com.gooalgene.wutbiolab.dao;

import com.gooalgene.wutbiolab.entity.scientificResearch.ScientificResearchDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScientificResearchDetailDAO extends JpaRepository<ScientificResearchDetail,Long> {
    Page<ScientificResearchDetail> getByScientificResearchCategoryId(Long scientificResearchCategoryId, Pageable pageable);
}
