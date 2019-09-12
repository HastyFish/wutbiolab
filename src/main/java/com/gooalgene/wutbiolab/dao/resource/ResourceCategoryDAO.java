package com.gooalgene.wutbiolab.dao.resource;

import com.gooalgene.wutbiolab.entity.resource.ResourceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceCategoryDAO extends JpaRepository<ResourceCategory, Long> {
}
