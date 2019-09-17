package com.gooalgene.wutbiolab.service.impl;

import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.dao.HomeDAO;
import com.gooalgene.wutbiolab.entity.Home;
import com.gooalgene.wutbiolab.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private HomeDAO homeDAO;
    @Override
    public void saveHome(Home home) {
        home.setPublishStatus(CommonConstants.UNPUBLISHED);
        homeDAO.save(home);
    }
}
