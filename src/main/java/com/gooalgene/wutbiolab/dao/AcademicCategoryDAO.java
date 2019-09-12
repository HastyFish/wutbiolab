package com.gooalgene.wutbiolab.dao;

import com.gooalgene.wutbiolab.entity.scientificResearch.AcademicCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcademicCategoryDAO extends JpaRepository<AcademicCategory,Long> {
}
