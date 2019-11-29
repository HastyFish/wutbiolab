package com.gooalgene.wutbiolab.service.impl;

import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.dao.scientific.AcademicCategoryDAO;
import com.gooalgene.wutbiolab.dao.scientific.ScientificResearchCategoryDAO;
import com.gooalgene.wutbiolab.dao.scientific.ScientificResearchDetailDAO;
import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import com.gooalgene.wutbiolab.entity.scientificResearch.AcademicCategory;
import com.gooalgene.wutbiolab.entity.scientificResearch.ScientificResearchCategory;
import com.gooalgene.wutbiolab.entity.scientificResearch.ScientificResearchDetail;
import com.gooalgene.wutbiolab.response.AcademicResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.front.DetailPageResponse;
import com.gooalgene.wutbiolab.service.CommonService;
import com.gooalgene.wutbiolab.service.ScientificResearchService;
import com.gooalgene.wutbiolab.util.DateConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
public class ScientificResearchServiceImpl implements ScientificResearchService {
    @Autowired
    private ScientificResearchDetailDAO scientificResearchDetailDAO;
    @Autowired
    private ScientificResearchCategoryDAO scientificResearchCategoryDAO;
    @Autowired
    private AcademicCategoryDAO academicCategoryDAO;
    @Autowired
    private DateConverter converter;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private CommonService commonService;

    @Override
    public PageResponse<ScientificResearchDetail> getSRDetialByCategoryId(Long scientificResearchCategoryId, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        Page<ScientificResearchDetail> scientificResearchDetailPage = scientificResearchDetailDAO.getByCategoryId(scientificResearchCategoryId, pageable);
        long total = scientificResearchDetailPage.getTotalElements();
        List<ScientificResearchDetail> content = scientificResearchDetailPage.getContent();
        PageResponse<ScientificResearchDetail> pageResponse=new PageResponse();
        pageResponse.setList(content);
        pageResponse.setTotal(total);
        pageResponse.setPageSize(pageSize);
        pageResponse.setPageNum(pageNum);
        return pageResponse;
    }

    @Override
    public PageResponse<AcademicResponse> getAcademicList(Integer pageNum,Integer pageSize){
        List<Object[]> srAcademicList = scientificResearchDetailDAO.getSRAcademicList(pageNum-1,pageSize);
        Long count = scientificResearchDetailDAO.getSRAcademicListCount();
        List<AcademicResponse> academicResponses=new ArrayList<>();
        srAcademicList.forEach(objects -> {
            BigInteger idObj = (BigInteger) objects[0];
            BigInteger publishDateObj = (BigInteger) objects[2];
            Long id=null;
            Long publishDate=null;
            if(idObj!=null){
                id=idObj.longValue();
            }
            if(publishDateObj!=null){
                publishDate=publishDateObj.longValue();
            }
            String title= (String) objects[1];
            Integer publishStatus= (Integer) objects[3];
            String academicCategoryName = (String) objects[4];
            AcademicResponse academicResponse = AcademicResponse.builder().id(id).publishDate(publishDate).publishStatus(publishStatus)
                    .title(title).academicCategoryName(academicCategoryName).build();
            academicResponses.add(academicResponse);
        });

        PageResponse<AcademicResponse> pageResponse=new PageResponse(academicResponses,pageNum,pageSize,count);
        return pageResponse;
    }

    @Override
    public ScientificResearchDetail getById(Long id){
        Optional<ScientificResearchDetail> optional = scientificResearchDetailDAO.findById(id);
        return optional.orElse(null);
    }

    @Override
    public List<ScientificResearchCategory> getAllCategory() {
        return scientificResearchCategoryDAO.findAll(Sort.by(CommonConstants.ORDER_CATEGORY));
    }


    @Override
    public List<AcademicCategory> getAllAcademicCategory(){
        return academicCategoryDAO.findAll(Sort.by(CommonConstants.ORDER_CATEGORY));
    }

    @Override
    @Transactional
    public void saveOrPublish(ScientificResearchDetail scientificResearchDetail,Integer publishStatus){
        scientificResearchDetail.setPublishStatus(publishStatus);
        scientificResearchDetailDAO.save(scientificResearchDetail);
    }

    @Transactional
    public void deleteById(Long id){
        scientificResearchDetailDAO.deleteById(id);
    }

    @Override
    public ScientificResearchCategory getScientificResearchCategoryById(Long id){
        Optional<ScientificResearchCategory> optional = scientificResearchCategoryDAO.findById(id);
        return optional.orElse(null);
    }

    @Override
    public PageResponse<ScientificResearchDetail> getScientificResearchDetailByLabCategoryId(Long labCategoryId, Integer pageNum, Integer pageSize) {
        if (pageNum == null && pageSize == null) {
            List<ScientificResearchDetail> scientificResearchDetails = scientificResearchDetailDAO.findByCategoryId(labCategoryId);
            return new PageResponse<ScientificResearchDetail>(scientificResearchDetails);
        }
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        Page<ScientificResearchDetail> labDetailPage = scientificResearchDetailDAO.findByCategoryId(labCategoryId, pageable);
        long totalElements = labDetailPage.getTotalElements();
        List<ScientificResearchDetail> content = labDetailPage.getContent();
        PageResponse<ScientificResearchDetail> pageResponse=new PageResponse<>(content,pageNum,pageSize,totalElements);
        return pageResponse;
    }


    /*********************************************** 前端使用 ***************************************************/

    @Override
    public PageResponse<ScientificResearchDetail> getScientificResearchDetailByLabCategoryIdAndPublishStatus(Long labCategoryId, Integer pageNum, Integer pageSize, Integer publishStatus) {
        if (pageNum == null && pageSize == null) {
            List<ScientificResearchDetail> scientificResearchDetails = scientificResearchDetailDAO.findByCategoryIdAndPublishStatus(labCategoryId,publishStatus);
            return new PageResponse<ScientificResearchDetail>(scientificResearchDetails);
        }
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        Page<ScientificResearchDetail> labDetailPage = scientificResearchDetailDAO.findByCategoryIdAndPublishStatus(labCategoryId,publishStatus, pageable);
        long totalElements = labDetailPage.getTotalElements();
        List<ScientificResearchDetail> content = labDetailPage.getContent();
        PageResponse<ScientificResearchDetail> pageResponse=new PageResponse<>(content,pageNum,pageSize,totalElements);
        return pageResponse;
    }

    @Override
    public PageResponse<ScientificResearchDetail> getPublishedByCategoryId(Long categoryId, Integer pageNum, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        Page<ScientificResearchDetail> scientificResearchDetailPage = scientificResearchDetailDAO.getByCategoryIdAndPublishStatus(categoryId,
                CommonConstants.PUBLISHED, pageable);
        long totalElements = scientificResearchDetailPage.getTotalElements();
        List<ScientificResearchDetail> content = scientificResearchDetailPage.getContent();
        ScientificResearchCategory category = getCategoryById(categoryId);
        PageResponse<ScientificResearchDetail> pageResponse=new DetailPageResponse<>(content,pageNum,pageSize,totalElements,category.getCategory());
        return pageResponse;
    }

    @Override
    public ScientificResearchCategory getCategoryById(Long categoryId) {
        return scientificResearchCategoryDAO.findById(categoryId).orElse(null);
    }

    @Override
    public PageResponse<ScientificResearchDetail> getSRDetialByDate(Integer pageNum, Integer pageSize, String beginDate, String endDate) {
        long begin = converter.convert(beginDate).getTime();
        long end = converter.convert(endDate).getTime();
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        Page<ScientificResearchDetail> scientificResearchDetailPage = scientificResearchDetailDAO.getByDate(begin, end, pageable);
        long total = scientificResearchDetailPage.getTotalElements();
        List<ScientificResearchDetail> content = scientificResearchDetailPage.getContent();
        PageResponse<ScientificResearchDetail> pageResponse= new PageResponse();
        pageResponse.setList(content);
        pageResponse.setTotal(total);
        pageResponse.setPageSize(pageSize);
        pageResponse.setPageNum(pageNum);
        return pageResponse;
    }

    @Override
    public PageResponse<ScientificResearchDetail> getSRDetialByStatus(Integer pageNum, Integer pageSize, Integer status) {
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        Page<ScientificResearchDetail> scientificResearchDetailPage = scientificResearchDetailDAO.getByStatus(status, pageable);
        long total = scientificResearchDetailPage.getTotalElements();
        List<ScientificResearchDetail> content = scientificResearchDetailPage.getContent();
        PageResponse<ScientificResearchDetail> pageResponse= new PageResponse();
        pageResponse.setList(content);
        pageResponse.setTotal(total);
        pageResponse.setPageSize(pageSize);
        pageResponse.setPageNum(pageNum);
        return pageResponse;
    }

    @Override
    public PageResponse<ScientificResearchDetail> getSRDetialByTitle(Integer pageNum, Integer pageSize, String title) {
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        Page<ScientificResearchDetail> scientificResearchDetailPage = scientificResearchDetailDAO.getByTitle(title, pageable);
        long total = scientificResearchDetailPage.getTotalElements();
        List<ScientificResearchDetail> content = scientificResearchDetailPage.getContent();
        PageResponse<ScientificResearchDetail> pageResponse= new PageResponse();
        pageResponse.setList(content);
        pageResponse.setTotal(total);
        pageResponse.setPageSize(pageSize);
        pageResponse.setPageNum(pageNum);
        return pageResponse;
    }

    @Override
    public PageResponse<ScientificResearchDetail> getSRDetialByPeriodicalName(Integer pageNum, Integer pageSize, String periodicalName) {
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        Page<ScientificResearchDetail> scientificResearchDetailPage = scientificResearchDetailDAO.getByPeriodicalName(periodicalName, pageable);
        long total = scientificResearchDetailPage.getTotalElements();
        List<ScientificResearchDetail> content = scientificResearchDetailPage.getContent();
        PageResponse<ScientificResearchDetail> pageResponse= new PageResponse();
        pageResponse.setList(content);
        pageResponse.setTotal(total);
        pageResponse.setPageSize(pageSize);
        pageResponse.setPageNum(pageNum);
        return pageResponse;
    }

    @Override
    public PageResponse<ScientificResearchDetail> getSRDetialByAuthor(Integer pageNum, Integer pageSize, String author) {
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        Page<ScientificResearchDetail> scientificResearchDetailPage = scientificResearchDetailDAO.getByAhuthor(author, pageable);
        long total = scientificResearchDetailPage.getTotalElements();
        List<ScientificResearchDetail> content = scientificResearchDetailPage.getContent();
        PageResponse<ScientificResearchDetail> pageResponse= new PageResponse();
        pageResponse.setList(content);
        pageResponse.setTotal(total);
        pageResponse.setPageSize(pageSize);
        pageResponse.setPageNum(pageNum);
        return pageResponse;
    }

    @Override
    public Map<String,Object> getPublishedById(Long id){
        Map<String,Object> map=new HashMap<>();
//        ScientificResearchDetail one = scientificResearchDetailDAO.getByIdAndPublishStatus(id,CommonConstants.PUBLISHED);
        List<Object[]> objects = scientificResearchDetailDAO.getByIdAndPublishStatus(id, CommonConstants.PUBLISHED);
        ScientificResearchDetail scientificResearchDetail=null;
        if(objects!=null&&!objects.isEmpty()){
            scientificResearchDetail= formatObj2Detail(objects.get(0));
        }
        if(scientificResearchDetail!=null){
            Long publishDate = scientificResearchDetail.getPublishDate();
            Long categoryId = scientificResearchDetail.getCategoryId();
            String countSql="select count(1) from  scientific_research_detail srd  where srd.publishDate =:publishDate " +
                    "and srd.publishStatus="+CommonConstants.PUBLISHED;
            Object singleResult = entityManager.createNativeQuery(countSql)
                    .setParameter("publishDate",publishDate).getSingleResult();
            BigInteger bigInteger = (BigInteger) singleResult;
            long count = bigInteger == null ? 0 : bigInteger.longValue();



//            ScientificResearchDetail pre = getOneByPublishDate(count,id,categoryId,publishDate, ">","asc");
//            ScientificResearchDetail next = getOneByPublishDate(count,id,categoryId,publishDate, "<","desc");
            ScientificResearchDetail pre = commonService.getOneByPublishDateAndId(ScientificResearchDetail.class,count,id,categoryId,publishDate, ">","asc");
            ScientificResearchDetail next = commonService.getOneByPublishDateAndId(ScientificResearchDetail.class,count,id,categoryId,publishDate, "<","desc");
            map.put("detail",scientificResearchDetail);
            map.put("previous",pre);
            map.put("next",next);
            map.put("firstCategory",CommonConstants.CATEGORY_SCIENTIFICRESEARCH);
        }
        return map;
    }


    private ScientificResearchDetail getOneByPublishDate(Long count,Long id,Long categoryId,Long publishDate, String operation,String sort){
        Query nativeQuery=null;
        if(count>1){
            String operationAndEq = operation.concat("=");
            String sql="select srd.id,srd.title from scientific_research_detail srd where  srd.publishDate "+operationAndEq+
                    " :publishDate  and srd.publishStatus=1  and srd.categoryId=:categoryId and srd.id"+operation+":id " +
                    " ORDER BY srd.publishDate "+sort+",srd.id "+sort+" limit 1";
            nativeQuery = entityManager.createNativeQuery(sql);
            nativeQuery.setParameter("id",id);
        }else {
            String sql="select srd.id,srd.title from scientific_research_detail srd where  srd.publishDate "+operation+
                    " :publishDate  and srd.publishStatus=1  and srd.categoryId=:categoryId  " +
                    " ORDER BY srd.publishDate "+sort+",srd.id "+sort+"  limit 1";
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
        ScientificResearchDetail scientificResearchDetail=new ScientificResearchDetail();
        BigInteger idBigInt = (BigInteger) objects[0];
        if(idBigInt!=null){
            scientificResearchDetail.setId(idBigInt.longValue());
        }
        String title = (String) objects[1];
        scientificResearchDetail.setTitle(title);
        return scientificResearchDetail;
    }


    private ScientificResearchDetail formatObj2Detail(Object[] objects){
        BigInteger idBigInt = (BigInteger) objects[0];
        Long id=idBigInt!=null?idBigInt.longValue():null;
        BigInteger academicCategoryIdBigInt = (BigInteger) objects[1];
        Long academicCategoryId=academicCategoryIdBigInt!=null?academicCategoryIdBigInt.longValue():null;
        String author = (String) objects[2];
        BigInteger categoryIdBigInt = (BigInteger) objects[3];
        Long categoryId=categoryIdBigInt!=null?categoryIdBigInt.longValue():null;
        String context = (String) objects[4];
        String periodicalName = (String) objects[5];
        BigInteger publishDateBigInt = (BigInteger) objects[6];
        Long publishDate=publishDateBigInt!=null?publishDateBigInt.longValue():null;
        Integer publishStatus = (Integer) objects[7];
        String publishYear = (String) objects[8];
        String title = (String) objects[9];
        String category = (String) objects[10];

        ScientificResearchDetail scientificResearchDetail = ScientificResearchDetail.builder().id(id).academicCategoryId(academicCategoryId).author(author).categoryId(categoryId)
                .context(context).periodicalName(periodicalName).publishDate(publishDate).publishStatus(publishStatus).publishYear(publishYear)
                .title(title).category(category).build();
        return scientificResearchDetail;
    }
}
