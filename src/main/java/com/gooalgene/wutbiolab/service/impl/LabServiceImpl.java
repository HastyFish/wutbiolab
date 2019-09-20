package com.gooalgene.wutbiolab.service.impl;

import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.dao.lab.GraduateCategoryDAO;
import com.gooalgene.wutbiolab.dao.lab.LabCategoryDAO;
import com.gooalgene.wutbiolab.dao.lab.LabDetailDAO;
import com.gooalgene.wutbiolab.dao.lab.MentorCategoryDAO;
import com.gooalgene.wutbiolab.entity.lab.GraduateCategory;
import com.gooalgene.wutbiolab.entity.lab.LabCategory;
import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import com.gooalgene.wutbiolab.entity.lab.MentorCategory;
import com.gooalgene.wutbiolab.response.GraduateResponse;
import com.gooalgene.wutbiolab.response.MentorResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.front.DetailPageResponse;
import com.gooalgene.wutbiolab.service.LabService;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.*;

@Slf4j
@Service
public class LabServiceImpl implements LabService {

    @Autowired
    private LabDetailDAO labDetailDAO;
    @Autowired
    private MentorCategoryDAO mentorCategoryDAO;
    @Autowired
    private GraduateCategoryDAO graduateCategoryDAO;
    @Autowired
    private LabCategoryDAO labCategoryDAO;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PageResponse<LabDetail> getLabDetailByLabCategoryId(Long labCategoryId, Integer pageNum, Integer pageSize, Boolean isList) {
        if (pageNum == null && pageSize == null) {
            List<LabDetail> labDetails = labDetailDAO.getByLabCategoryId(labCategoryId);
            return new PageResponse<LabDetail>(labDetails);
        }
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        Page<LabDetail> labDetailPage = labDetailDAO.getByLabCategoryId(labCategoryId, pageable);
        long totalElements = labDetailPage.getTotalElements();
        List<LabDetail> content = labDetailPage.getContent();
        PageResponse<LabDetail> pageResponse=new PageResponse<>(content,pageNum,pageSize,totalElements);
        return pageResponse;
    }

    @Override
    public PageResponse<GraduateResponse> getGraduates(Integer pageNum, Integer pageSize){
        List<GraduateResponse> graduateResponses=new ArrayList<>();
        List<Object[]> objectsList = labDetailDAO.getGraduates(pageNum - 1, pageSize);
        objectsList.forEach(objects -> {
            Long id = ((BigInteger) objects[0]).longValue();
            String graduateCategoryName=(String)objects[1];
            String title=(String)objects[2];
            Long publishDate = objects[3]==null?null:((BigInteger) objects[3]).longValue();
            Integer publishStatus=(Integer) objects[4];
            GraduateResponse graduateResponse=GraduateResponse.builder().labDetailId(id).graduateCategoryName(graduateCategoryName)
                    .publishDate(publishDate).title(title).publishStatus(publishStatus).build();
            graduateResponses.add(graduateResponse);
        });
        Long graduatesCount = labDetailDAO.getGraduatesCount();
        PageResponse pageResponse=new PageResponse();
        pageResponse.setList(graduateResponses);
        pageResponse.setPageNum(pageNum);
        pageResponse.setPageSize(pageSize);
        pageResponse.setTotal(graduatesCount);
        return pageResponse;
    }

    @Override
    public LabDetail getById(Long id) {
        Optional<LabDetail> optional = labDetailDAO.findById(id);
        return optional.orElse(null);
    }


    @Override
    public List<MentorResponse> getResearchTeam() {
        List<Object[]> researchTeam = labDetailDAO.getResearchTeam();
        return formatObj2MentorResponse(researchTeam);
    }

    @Override
    public void saveList(List<LabDetail> labDetails){
        labDetailDAO.saveAll(labDetails);
    }
    @Override
    @Transactional
    public void saveOrPublishLabDetail(LabDetail labDetail, Integer publishStatus) {
        labDetail.setPublishStatus(publishStatus);
        labDetailDAO.save(labDetail);
    }


//    @Override
//    @Transactional
//    public void publishResearchTeam(List<MentorRequest> mentorRequests) {
//        List<LabDetail> labDetails = new ArrayList<>();
//        mentorRequests.forEach(mentorRequest -> {
//            List<LabDetail> mentors = mentorRequest.getMentors();
//            Long mentorCategoryId = mentorRequest.getMentorCategoryId();
//            mentors.forEach(mentor -> {
//                mentor.setPublishStatus(CommonConstants.PUBLISHED);
//                mentor.setMentorCategoryId(mentorCategoryId);
//                labDetails.add(mentor);
//            });
//        });
//        labDetailDAO.saveAll(labDetails);
//    }

    @Override
    @Transactional
    public void publishByLabCategoryId(Long labCategoryId) {
        List<LabDetail> labDetails = labDetailDAO.getByLabCategoryId(labCategoryId);
        labDetails.forEach(labDetail -> {
            labDetail.setPublishStatus(CommonConstants.PUBLISHED);
        });
        labDetailDAO.saveAll(labDetails);
    }


    @Override
    @Transactional
    public void saveMentorCategory(List<MentorCategory> mentorCategorys) {
        mentorCategoryDAO.saveAll(mentorCategorys);
    }

    @Override
    public List<MentorCategory> getMentorCategorys() {
        return mentorCategoryDAO.findAll();
    }

    @Override
    public List<GraduateCategory> getGraduateCategorys() {
        List<GraduateCategory> all = graduateCategoryDAO.findAll();
        return all;
    }

    @Override
    public List<LabCategory> getAllCategory(){
        return labCategoryDAO.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id){
        labDetailDAO.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteMentorCategoryById(Long id){
        mentorCategoryDAO.deleteById(id);
    }

    /*********************************************** 前端使用 ***************************************************/

    @Override
    public Map<String,LabDetail> getPublishedById(Long id) {
        Map<String,LabDetail> map=new HashMap<>();
        LabDetail labDetail = labDetailDAO.getByIdAndPublishStatus(id, CommonConstants.PUBLISHED);
        if (labDetail != null) {
            Long publishDate = labDetail.getPublishDate();
            LabDetail pre = getOneByPublishDate(publishDate, ">");
            LabDetail next = getOneByPublishDate(publishDate, "<");
            map.put("detail",labDetail);
            map.put("previous",pre);
            map.put("next",next);
        }
        return map;
    }

    private LabDetail getOneByPublishDate(Long publishDate,String operation){
        String sql="select labDetail.id,labDetail.title from lab_detail labDetail where  labDetail.publishDate "+operation+
                " :publishDate  and labDetail.publishStatus=1 ORDER BY publishDate limit 1";
        Query nativeQuery = entityManager.createNativeQuery(sql);
        nativeQuery.setParameter("publishDate",publishDate);
        Object object = null;
        try {
            object = nativeQuery.getSingleResult();
        } catch (NoResultException e) {
            log.info("publishDate为：{}是最后一条数据了",publishDate);
            return null;
        }
        Object[] objects = (Object[])object;
        LabDetail labDetail=new LabDetail();
        BigInteger idBigInt = (BigInteger) objects[0];
        if(idBigInt!=null){
            labDetail.setId(idBigInt.longValue());
        }
        String title = (String) objects[1];
        labDetail.setTitle(title);
        return labDetail;
    }

    @Override
    public List<MentorResponse> getPublishedResearchTeam() {
        List<Object[]> researchTeam = labDetailDAO.getResearchTeamByPublishStatus(CommonConstants.PUBLISHED);
        return formatObj2MentorResponse(researchTeam);
    }

    @Override
    public PageResponse<LabDetail> getLabDetailByLabCategoryIdAndPublishStatus(Long labCategoryId,
                                                                       Integer pageNum, Integer pageSize,
                                                                       Integer publishStatus, Boolean isList) {
        //如果isList为true，只查几个字段（主要是不查context这样的大字段），
        // 如果不是，例如机构概况和研究方向这样的子模块，只能查出一条数据，所以就相当于进详情页了（但又不能等同于通过id进入详情页）
        Page<LabDetail> labDetailPage = null;
        if (isList) {
            if (pageNum == null && pageSize == null) {
                //暂时没有是列表还不分页的情况！
            } else {
                Pageable pageable = PageRequest.of(pageNum-1, pageSize);
                labDetailPage = labDetailDAO.getListByLabCategoryIdAndPublishStatus(labCategoryId, publishStatus, pageable);
            }
        } else {
            //非list情况，针对机构概况和研究方向
            if (pageNum == null && pageSize == null) {
                List<LabDetail> labDetails = labDetailDAO.getByLabCategoryIdAndPublishStatus(labCategoryId, publishStatus);
                labDetailPage = new PageImpl<>(labDetails);
            }else {
                Pageable pageable = PageRequest.of(pageNum-1, pageSize);
                labDetailPage = labDetailDAO.getByLabCategoryIdAndPublishStatus(labCategoryId, publishStatus, pageable);
            }
        }
        if(labDetailPage!=null){
//            Optional<LabCategory> optional = labCategoryDAO.findById(labCategoryId);
//            String category = optional.isPresent() ? optional.get().getCategory() : null;
            List<LabDetail> content = labDetailPage.getContent();
            long totalElements = labDetailPage.getTotalElements();
            PageResponse<LabDetail> pageResponse=new PageResponse<>(content,pageNum,pageSize,totalElements);
            return pageResponse;
        }
        return null;
    }

    public LabCategory getCategoryById(Long labCategoryId){
        return labCategoryDAO.findById(labCategoryId).orElse(null);
    }

    private List<MentorResponse> formatObj2MentorResponse(List<Object[]> researchTeam) {
        Table<Long, String, List<LabDetail>> table = HashBasedTable.create();
        researchTeam.forEach(objects -> {
            Long mentorCategoryId = ((BigInteger) objects[1]).longValue();
            String mentorCategoryName = (String) objects[4];
            Integer publishStatus = (Integer) objects[5];
            Object idObject = objects[0];
            if (idObject != null) {
                Long labDetailId = ((BigInteger) idObject).longValue();
                String mentorName = (String) objects[2];
                Integer mentorOder = (Integer) objects[3];
                LabDetail labDetail = new LabDetail();
                labDetail.setId(labDetailId);
                labDetail.setMentorName(mentorName);
                labDetail.setMentorOrder(mentorOder);
                labDetail.setMentorCategoryId(mentorCategoryId);
                labDetail.setPublishStatus(publishStatus);
                List<LabDetail> labDetails = table.get(mentorCategoryId, mentorCategoryName);
                if (labDetails == null) {
                    labDetails = new ArrayList<>();
                    table.put(mentorCategoryId, mentorCategoryName, labDetails);
                }
                labDetails.add(labDetail);
            } else {
                table.put(mentorCategoryId, mentorCategoryName, new ArrayList<>());
            }
        });
        Map<Long, Map<String, List<LabDetail>>> longMapMap = table.rowMap();

        List<MentorResponse> mentorResponses = new ArrayList<>();
        for (Map.Entry entry : longMapMap.entrySet()) {
            Long mentorCategoryId = (Long) entry.getKey();
            Map<String, List<LabDetail>> value = (Map<String, List<LabDetail>>) entry.getValue();
            String mentorCategoryName = null;
            for (Map.Entry entry2 : value.entrySet()) {
                MentorResponse mentorResponse = new MentorResponse();
                if (mentorCategoryName == null) {
                    mentorCategoryName = (String) entry2.getKey();
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
