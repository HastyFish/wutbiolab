package com.gooalgene.wutbiolab.dao.home;

import com.gooalgene.wutbiolab.entity.home.CooperationLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CooperationLinkDAO extends JpaRepository<CooperationLink, Long> {


    List<CooperationLink> findByPublishStatusEquals(Integer publishStatus);

}
