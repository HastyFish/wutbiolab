package com.gooalgene.wutbiolab.dao.home;

import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.entity.home.Footer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FooterDAO extends JpaRepository<Footer, Long> {

    List<Footer> findByPublishStatusEquals(Integer publishStatus);

}
