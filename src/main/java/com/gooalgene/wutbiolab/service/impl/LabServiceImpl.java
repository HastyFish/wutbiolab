package com.gooalgene.wutbiolab.service.impl;

import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.dao.lab.GraduateCategoryDAO;
import com.gooalgene.wutbiolab.dao.lab.LabCategoryDAO;
import com.gooalgene.wutbiolab.dao.lab.LabDetailDAO;
import com.gooalgene.wutbiolab.dao.lab.MentorCategoryDAO;
import com.gooalgene.wutbiolab.entity.common.AllCategory;
import com.gooalgene.wutbiolab.entity.lab.GraduateCategory;
import com.gooalgene.wutbiolab.entity.lab.LabCategory;
import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import com.gooalgene.wutbiolab.entity.lab.MentorCategory;
import com.gooalgene.wutbiolab.response.GraduateResponse;
import com.gooalgene.wutbiolab.response.MentorResponse;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.response.front.DetailPageResponse;
import com.gooalgene.wutbiolab.service.CommonService;
import com.gooalgene.wutbiolab.service.LabService;
import com.gooalgene.wutbiolab.util.DateConverter;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
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
    @Autowired
    private CommonService commonService;
    @Autowired
    private DateConverter converter;

    @Override
    public PageResponse<LabDetail> getLabDetailByLabCategoryId(Long labCategoryId, Integer pageNum, Integer pageSize, Boolean isList) {
        if (pageNum == null && pageSize == null) {
            List<LabDetail> labDetails = labDetailDAO.getByCategoryId(labCategoryId);
            return new PageResponse<LabDetail>(labDetails);
        }
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        Page<LabDetail> labDetailPage = labDetailDAO.getByCategoryId(labCategoryId, pageable);
        long totalElements = labDetailPage.getTotalElements();
        List<LabDetail> content = labDetailPage.getContent();
        PageResponse<LabDetail> pageResponse=new PageResponse<>(content,pageNum,pageSize,totalElements);
        return pageResponse;
    }

    @Override
    public PageResponse<GraduateResponse> getGraduates(Integer pageNum, Integer pageSize){
        Integer offset=(pageNum-1)*pageSize;
        List<GraduateResponse> graduateResponses=new ArrayList<>();
        List<Object[]> objectsList = labDetailDAO.getGraduates(offset, pageSize);
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
    public List<LabDetail> getByIdIn(List<Long> ids){
        return labDetailDAO.getByIdIn(ids);
    }
    @Override
    public void saveList(List<LabDetail> labDetails){
        labDetailDAO.saveAll(labDetails);
    }

    @Override
    @Transactional
    public void updateMentorOrderById(Map<Long,Integer> map){
        StringBuilder sqlBuilder=new StringBuilder("update lab_detail set mentorOrder= case id ");
        StringBuilder idsStrBuilder=new StringBuilder();
        for(Map.Entry<Long,Integer> entry:map.entrySet()){
            Long id = entry.getKey();
            Integer mentorOrder = entry.getValue();
            idsStrBuilder.append(id).append(",");
            sqlBuilder.append("WHEN "+id+" THEN "+mentorOrder+" ");
        }
        String ids = idsStrBuilder.toString();
        sqlBuilder.append("end where id in (".concat(ids.substring(0,ids.length()-1)).concat(")") );
        String sql = sqlBuilder.toString();
        Query nativeQuery = entityManager.createNativeQuery(sql);
        int i = nativeQuery.executeUpdate();
    }
    @Override
    @Transactional
    public void saveOrPublishLabDetail(LabDetail labDetail, Integer publishStatus) {
        labDetail.setPublishStatus(publishStatus);
        LabDetail save = labDetailDAO.save(labDetail);
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
        List<LabDetail> labDetails = labDetailDAO.getByCategoryId(labCategoryId);
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
        return labCategoryDAO.findAll(Sort.by(CommonConstants.ORDER_CATEGORY));
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
    public Map<String,Object> getPublishedById(Long id) {
        Map<String,Object> map=new HashMap<>();
        List<Object[]> objects = labDetailDAO.getByIdAndPublishStatus(id, CommonConstants.PUBLISHED);
        LabDetail labDetail =null;
        if(objects!=null&&!objects.isEmpty()){
            labDetail= formatObj2Detail(objects.get(0));
        }
        if (labDetail != null) {
            Long publishDate = labDetail.getPublishDate();
            Long categoryId = labDetail.getCategoryId();
            String countSql="select count(1) from  lab_detail labDetail  where labDetail.publishDate =:publishDate " +
                    "and labDetail.publishStatus="+CommonConstants.PUBLISHED;
            Object singleResult = entityManager.createNativeQuery(countSql)
                    .setParameter("publishDate",publishDate).getSingleResult();
            BigInteger bigInteger = (BigInteger) singleResult;
            long count = bigInteger == null ? 0 : bigInteger.longValue();

//            LabDetail pre = getOneByPublishDate(count,id,categoryId,publishDate, ">","asc");
//            LabDetail next = getOneByPublishDate(count,id,categoryId,publishDate, "<","desc");
            LabDetail pre = commonService.getOneByPublishDateAndId(LabDetail.class,count,id,categoryId,publishDate, ">","asc");
            LabDetail next = commonService.getOneByPublishDateAndId(LabDetail.class,count,id,categoryId,publishDate, "<","desc");
            map.put("detail",labDetail);
            map.put("previous",pre);
            map.put("next",next);
            map.put("firstCategory",CommonConstants.CATEGORY_LAB);
        }
        return map;
    }

    private LabDetail getOneByPublishDate(Long count,Long id,Long categoryId,Long publishDate,String operation,String sort){
        Query nativeQuery=null;
        if(count>1){
            String operationAndEq = operation.concat("=");
            String sql="select labDetail.id,labDetail.title from lab_detail labDetail where  labDetail.publishDate "+operationAndEq+
                    " :publishDate  and labDetail.publishStatus=1 and labDetail.categoryId=:categoryId and labDetail.id"+operation+":id " +
                    " ORDER BY labDetail.publishDate "+sort+",labDetail.id "+sort+" limit 1";
            nativeQuery = entityManager.createNativeQuery(sql);
            nativeQuery.setParameter("id",id);
        }else {
            String sql="select labDetail.id,labDetail.title from lab_detail labDetail where  labDetail.publishDate "+operation+
                    " :publishDate  and labDetail.publishStatus=1 and labDetail.categoryId=:categoryId " +
                    " ORDER BY labDetail.publishDate "+sort+",labDetail.id "+sort+" limit 1";
            nativeQuery = entityManager.createNativeQuery(sql);
        }
        nativeQuery.setParameter("publishDate",publishDate);
        nativeQuery.setParameter("categoryId",categoryId);
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
    private <T> T getOneByPublishDateAndId(Class<T> tClass,Long count,Long id,Long categoryId,Long publishDate,String operation,String sort){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(tClass);
        Root<T> root = query.from(tClass);
        Predicate predicate1=null;
        Predicate predicate4=null;
        Predicate predicate2 = criteriaBuilder.equal(root.get("publishStatus"), CommonConstants.PUBLISHED);
        Predicate predicate3 = criteriaBuilder.equal(root.get("categoryId"), categoryId);
        if(count>1){
            if(StringUtils.equals(operation,">")){
                predicate1 = criteriaBuilder.ge(root.get("publishDate"), publishDate);
                predicate4=criteriaBuilder.gt(root.get("id"),id);
            }else if (StringUtils.equals(operation,"<")){
                predicate1 = criteriaBuilder.le(root.get("publishDate"), publishDate);
                predicate4=criteriaBuilder.lt(root.get("id"),id);
            }
            Predicate and = criteriaBuilder.and(predicate1, predicate2,predicate3,predicate4);
            query.where(and);
        }else {
            if(StringUtils.equals(operation,">")){
                predicate1 = criteriaBuilder.gt(root.get("publishDate"), publishDate);
            }else if (StringUtils.equals(operation,"<")){
                predicate1 = criteriaBuilder.lt(root.get("publishDate"), publishDate);
            }
            Predicate and = criteriaBuilder.and(predicate1, predicate2,predicate3);
            query.where(and);
        }
        query.multiselect(root.get("id"),root.get("title"));
        if(StringUtils.equals(sort,"asc")){
            query.orderBy(criteriaBuilder.asc(root.get("publishDate")),criteriaBuilder.asc(root.get("id")));
        }else if(StringUtils.equals(sort,"desc")){
            query.orderBy(criteriaBuilder.desc(root.get("publishDate")),criteriaBuilder.desc(root.get("id")));
        }
        List<T> details = entityManager.createQuery(query).setFirstResult(0).setMaxResults(1).getResultList();
        if(!CollectionUtils.isEmpty(details)){
            return details.get(0);
        }
        return null;
    }

    @Override
    public List<MentorResponse> getPublishedResearchTeam() {
        List<Object[]> researchTeam = labDetailDAO.getResearchTeamByPublishStatus(CommonConstants.PUBLISHED);
        return formatObj2MentorResponse(researchTeam);
    }

    @Override
    public DetailPageResponse<LabDetail> getLabDetailByLabCategoryIdAndPublishStatus(Long labCategoryId,
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
                labDetailPage = labDetailDAO.getListByCategoryIdAndPublishStatus(labCategoryId, publishStatus, pageable);
            }
        } else {
            //非list情况，针对机构概况和研究方向
            if (pageNum == null && pageSize == null) {
                List<LabDetail> labDetails = labDetailDAO.getByCategoryIdAndPublishStatus(labCategoryId, publishStatus);
                labDetailPage = new PageImpl<>(labDetails);
            }else {
                Pageable pageable = PageRequest.of(pageNum-1, pageSize);
                labDetailPage = labDetailDAO.getByCategoryIdAndPublishStatus(labCategoryId, publishStatus, pageable);
            }
        }
        if(labDetailPage!=null){
            List<LabDetail> content = labDetailPage.getContent();
            long totalElements = labDetailPage.getTotalElements();
            DetailPageResponse<LabDetail> pageResponse=new DetailPageResponse<>(content,pageNum,pageSize,totalElements);
            return pageResponse;
        }
        return null;
    }

    @Override
    public CommonResponse<PageResponse<LabDetail>> labDetailPage(Integer pageNum,
                                                                 Integer pageSize,
                                                                 Long categoryId,
                                                                 Integer publishStatus,
                                                                 String startDate, String endDate) {
        Sort.Order daterder = new Sort.Order(Sort.Direction.DESC, CommonConstants.PUBLISHDATEFIELD);
        Sort.Order categoryOrder = new Sort.Order(Sort.Direction.ASC, CommonConstants.CATEGORYIDFIELD);
        Sort.Order idOrder = new Sort.Order(Sort.Direction.DESC, CommonConstants.IDFIELD);
        Specification<LabDetail> specification = (Specification<LabDetail>) (root, criteriaQuery, cb) -> {
            List<Predicate> predicatesList = new ArrayList<>();
            if (publishStatus != null){
                Predicate publishStatusPre = cb.equal(root.get("publishStatus"), publishStatus);
                predicatesList.add(publishStatusPre);
            }
            if (categoryId != null){
                Predicate categoryPre= cb.equal(root.get("categoryId"), categoryId);
                predicatesList.add(categoryPre);
            }
            if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)){
                Long start = converter.convert(startDate).getTime();
                Long end = converter.convert(endDate).getTime();
                Predicate datePre = cb.between(root.get("publishDate"), start, end);
                predicatesList.add(datePre);
            }
            Predicate[] predicates = new Predicate[predicatesList.size()];
            return cb.and(predicatesList.toArray(predicates));
        };
        Page<LabDetail> page = labDetailDAO.findAll(specification, PageRequest.of(pageNum - 1, pageSize,
                Sort.by(daterder, categoryOrder, idOrder)));

        return ResponseUtil.success(new PageResponse<>(page.getContent(), pageNum, pageSize, page.getTotalElements()));
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
            Boolean isEmpty=false;
            try {
                BigInteger bigIntegerContextLength = (BigInteger) objects[6];
                if(bigIntegerContextLength==null){
                    isEmpty=true;
                }else if(bigIntegerContextLength.longValue()==0) {
                    isEmpty=true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
            }

            Object idObject = objects[0];
            if (idObject != null) {
                Long labDetailId = ((BigInteger) idObject).longValue();
                String mentorName = (String) objects[2];
                Integer mentorOder = (Integer) objects[3];
                LabDetail labDetail = new LabDetail();

                labDetail.setIsEmpty(isEmpty);
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

    private LabDetail formatObj2Detail(Object[] objects){
        BigInteger idBigInt = (BigInteger) objects[0];
        Long id=idBigInt!=null?idBigInt.longValue():null;
        BigInteger categoryIdBigInt = (BigInteger) objects[1];
        Long categoryId=categoryIdBigInt!=null?categoryIdBigInt.longValue():null;
        String context = (String) objects[2];
        BigInteger graduateCategoryIdBigInt = (BigInteger) objects[3];
        Long graduateCategoryId=graduateCategoryIdBigInt!=null?graduateCategoryIdBigInt.longValue():null;
        BigInteger mentorCategoryIdBigInt = (BigInteger) objects[4];
        Long mentorCategoryId=mentorCategoryIdBigInt!=null?mentorCategoryIdBigInt.longValue():null;
        String mentorName = (String) objects[5];
        Integer mentorOder = (Integer) objects[6];
        BigInteger publishDateBigInt = (BigInteger) objects[7];
        Long publishDate=publishDateBigInt!=null?publishDateBigInt.longValue():null;
        Integer publishStatus = (Integer) objects[8];
        String title = (String) objects[9];
        String category = (String) objects[10];

        LabDetail labDetail = LabDetail.builder().id(id).categoryId(categoryId).context(context).graduateCategoryId(graduateCategoryId)
                .mentorCategoryId(mentorCategoryId).mentorName(mentorName).mentorOrder(mentorOder).publishDate(publishDate)
                .publishStatus(publishStatus).title(title).category(category).build();

        return labDetail;
    }
}
