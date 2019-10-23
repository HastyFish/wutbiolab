package com.gooalgene.wutbiolab.dao.contactus;

import com.gooalgene.wutbiolab.entity.contactus.ContactusCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactusCategoryDAO extends JpaRepository<ContactusCategory, Long> {
}
