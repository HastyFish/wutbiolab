package com.gooalgene.wutbiolab.service.impl;

import com.alibaba.fastjson.JSON;
import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.dao.InstitutionalProfileDAO;
import com.gooalgene.wutbiolab.entity.InstitutionalProfile;
import com.gooalgene.wutbiolab.service.InstitutionalProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class InstitutionalProfileServiceImpl implements InstitutionalProfileService {
    @Autowired
    private InstitutionalProfileDAO institutionalProfileDAO;


    @Transactional
    @Override
    public void saveUnpublished(InstitutionalProfile institutionalProfile) {
        Long id = institutionalProfile.getId();
        institutionalProfile.setPublishStatus(CommonConstants.UNPUBLISHED);
        String s = JSON.toJSONString(institutionalProfile);
        if (id == null) {
            //新增
            institutionalProfile.setUnpublishedJson(s);
            institutionalProfileDAO.save(institutionalProfile);
        }else {
            //修改
            institutionalProfile.setUnpublishedJson(s);
            institutionalProfileDAO.save(institutionalProfile);
        }
    }
}
