package com.gooalgene.wutbiolab.service.impl;

import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.dao.LabDetailDAO;
import com.gooalgene.wutbiolab.dao.MentorCategoryDAO;
import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import com.gooalgene.wutbiolab.entity.lab.MentorCategory;
import com.gooalgene.wutbiolab.request.MentorRequest;
import com.gooalgene.wutbiolab.service.LabService;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LabServiceImpl implements LabService {

    @Autowired
    private LabDetailDAO labDetailDAO;
    @Autowired
    private MentorCategoryDAO mentorCategoryDAO;

    @Override
    public Page<LabDetail> getLabDetailByLabCategoryId(Long labCategoryId, Integer pageNum, Integer pageSize) {
        if(pageNum==null&&pageSize==null){
            List<LabDetail> labDetails = labDetailDAO.getByLabCategoryId(labCategoryId);
            return new PageImpl<>(labDetails);
        }
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return labDetailDAO.getByLabCategoryId(labCategoryId, pageable);
    }

    public void getResearchTeam(){
        List<Object[]> researchTeam = labDetailDAO.getResearchTeam();
        Table<String,Integer,List<LabDetail>> table= HashBasedTable.create();
        researchTeam.forEach(objects -> {
            Object idObject = objects[0];
            Long labDetailId=null;
            String mentorName=null;
            Integer mentorOder=null;
            if(idObject!=null){
                labDetailId=(Long)idObject;
                mentorName=(String)objects[2];
                mentorOder=(Integer)objects[3];
            }
            Long mentorCategoryId=(Long)objects[1];
            String mentorCategoryName=(String)objects[4];




        });
    }

    public void saveLabDetail(LabDetail labDetail){
        labDetail.setPublishStatus(CommonConstants.UNPUBLISHED);
        labDetailDAO.save(labDetail);
    }

    public void saveMentorCategory(MentorCategory mentorCategory){
        mentorCategoryDAO.save(mentorCategory);
    }

    public void publishLabDetail(LabDetail labDetail){
        labDetail.setPublishStatus(CommonConstants.PUBLISHED);
        labDetailDAO.save(labDetail);
    }

    public void publishResearchTeam(List<MentorRequest> mentorRequests){
        List<LabDetail> labDetails=new ArrayList<>();
        mentorRequests.forEach(mentorRequest -> {
            List<LabDetail> mentors = mentorRequest.getMentors();
            Long mentorCategoryId = mentorRequest.getMentorCategoryId();
            mentors.forEach(mentor->{
                mentor.setPublishStatus(CommonConstants.PUBLISHED);
                mentor.setMentorCategoryId(mentorCategoryId);
                labDetails.add(mentor);
            });
        });
        labDetailDAO.saveAll(labDetails);
    }


}
