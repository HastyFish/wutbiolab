package com.gooalgene.wutbiolab.dao;

import com.gooalgene.wutbiolab.request.HomeRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeDAO extends JpaRepository<HomeRequest,Long> {

}
