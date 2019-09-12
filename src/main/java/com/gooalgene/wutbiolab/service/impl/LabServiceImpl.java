package com.gooalgene.wutbiolab.service.impl;

import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.dao.GraduateCategoryDAO;
import com.gooalgene.wutbiolab.dao.LabDetailDAO;
import com.gooalgene.wutbiolab.dao.MentorCategoryDAO;
import com.gooalgene.wutbiolab.entity.lab.GraduateCategory;
import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import com.gooalgene.wutbiolab.entity.lab.MentorCategory;
import com.gooalgene.wutbiolab.request.MentorRequest;
import com.gooalgene.wutbiolab.response.MentorResponse;
import com.gooalgene.wutbiolab.service.LabService;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LabServiceImpl implements LabService {

    @Autowired
    private LabDetailDAO labDetailDAO;
    @Autowired
    private MentorCategoryDAO mentorCategoryDAO;
    @Autowired
    private GraduateCategoryDAO graduateCategoryDAO;

    @Override
    public Page<LabDetail> getLabDetailByLabCategoryId(Long labCategoryId, Integer pageNum, Integer pageSize) {
        if(pageNum==null&&pageSize==null){
            List<LabDetail> labDetails = labDetailDAO.getByLabCategoryId(labCategoryId);
            return new PageImpl<>(labDetails);
        }
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return labDetailDAO.getByLabCategoryId(labCategoryId, pageable);
    }

    @Override
    public LabDetail getById(Long id){
        LabDetail one = labDetailDAO.getOne(id);
        return one;
    }


    @Override
    public List<MentorResponse> getResearchTeam(){
        List<Object[]> researchTeam = labDetailDAO.getResearchTeam();
        return formatMentorResponse(researchTeam);
    }
    @Override
    public void saveOrPublishLabDetail(LabDetail labDetail,Integer publishStatus){
        labDetail.setPublishStatus(publishStatus);
        labDetailDAO.save(labDetail);
    }


    @Override
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


    @Override
    public void saveMentorCategory(MentorCategory mentorCategory){
        mentorCategoryDAO.save(mentorCategory);
    }
    @Override
    public List<MentorCategory> getMentorCategorys(){
        return mentorCategoryDAO.findAll();
    }

    @Override
    public List<GraduateCategory> getGraduateCategorys(){
        List<GraduateCategory> all = graduateCategoryDAO.findAll();
        return all;
    }



    /*********************************************** 前端使用 ***************************************************/

    @Override
    public Page<LabDetail> getPublished(Integer pageNum,Integer pageSize){
        if(pageNum==null&&pageSize==null){
            List<LabDetail> labDetails = labDetailDAO.getByPublishStatus(CommonConstants.PUBLISHED);
            return new PageImpl<>(labDetails);
        }
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<LabDetail> page = labDetailDAO.getByPublishStatus(CommonConstants.PUBLISHED, pageable);
        return page;
    }

    @Override
    public List<MentorResponse> getPublishedResearchTeam(){
        List<Object[]> researchTeam = labDetailDAO.getResearchTeam(CommonConstants.PUBLISHED);
        return formatMentorResponse(researchTeam);
    }

    private List<MentorResponse>  formatMentorResponse(List<Object[]> researchTeam){
        Table<Long,String,List<LabDetail>> table= HashBasedTable.create();
        researchTeam.forEach(objects -> {
            Long mentorCategoryId=((BigInteger)objects[1]).longValue();
            String mentorCategoryName=(String)objects[4];
            Object idObject = objects[0];
            if(idObject!=null){
                Long labDetailId=((BigInteger)idObject).longValue();
                String mentorName=(String)objects[2];
                Integer mentorOder=(Integer)objects[3];
                LabDetail labDetail=new LabDetail();
                labDetail.setId(labDetailId);
                labDetail.setMentorName(mentorName);
                labDetail.setMentorOrder(mentorOder);
                List<LabDetail> labDetails = table.get(mentorCategoryId, mentorCategoryName);
                if(labDetails==null){
                    labDetails=new ArrayList<>();
                    table.put(mentorCategoryId,mentorCategoryName,labDetails);
                }
                labDetails.add(labDetail);
            }else {
                table.put(mentorCategoryId,mentorCategoryName,new ArrayList<>());
            }
        });
        Map<Long, Map<String, List<LabDetail>>> longMapMap = table.rowMap();

        List<MentorResponse> mentorResponses=new ArrayList<>();
        for(Map.Entry entry:longMapMap.entrySet()){
            Long mentorCategoryId = (Long)entry.getKey();
            Map<String, List<LabDetail>> value = (Map<String, List<LabDetail>>)entry.getValue();
            String mentorCategoryName=null;
            for(Map.Entry entry2:value.entrySet()){
                MentorResponse mentorResponse=new MentorResponse();
                if(mentorCategoryName==null){
                    mentorCategoryName=(String) entry2.getKey();
                }
                List<LabDetail> labDetails = (List<LabDetail>) entry2.getValue();
                mentorResponse.setMentorCategoryId(mentorCategoryId);
                mentorResponse.setMentorCategoryName(mentorCategoryName);
                mentorResponse.setLabDetails(labDetails);
                mentorResponses.add(mentorResponse);
            }
        }
        return mentorResponses;
    }

}
