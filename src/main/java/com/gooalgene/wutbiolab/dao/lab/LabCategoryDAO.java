package com.gooalgene.wutbiolab.dao.lab;

import com.gooalgene.wutbiolab.entity.lab.LabCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabCategoryDAO extends JpaRepository<LabCategory,Long> {
}
