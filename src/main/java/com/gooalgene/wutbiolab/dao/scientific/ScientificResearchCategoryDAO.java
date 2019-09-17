package com.gooalgene.wutbiolab.dao.scientific;

import com.gooalgene.wutbiolab.entity.scientificResearch.ScientificResearchCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScientificResearchCategoryDAO  extends JpaRepository<ScientificResearchCategory,Long> {
}
