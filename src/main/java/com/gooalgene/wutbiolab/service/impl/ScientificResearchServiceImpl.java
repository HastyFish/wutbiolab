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
import com.gooalgene.wutbiolab.service.ScientificResearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
public class ScientificResearchServiceImpl implements ScientificResearchService {
    @Autowired
    private ScientificResearchDetailDAO scientificResearchDetailDAO;
    @Autowired
    private ScientificResearchCategoryDAO scientificResearchCategoryDAO;
    @Autowired
    private AcademicCategoryDAO academicCategoryDAO;
    @PersistenceContext
    private EntityManager entityManager;

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
        return scientificResearchCategoryDAO.findAll();
    }


    @Override
    public List<AcademicCategory> getAllAcademicCategory(){
        return academicCategoryDAO.findAll();
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


    /*********************************************** 前端使用 ***************************************************/

    @Override
    public PageResponse<ScientificResearchDetail> getPublishedByCategoryId(Long scientificResearchCategoryId, Integer pageNum, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        Page<ScientificResearchDetail> scientificResearchDetailPage = scientificResearchDetailDAO.getByCategoryIdAndPublishStatus(scientificResearchCategoryId,
                CommonConstants.PUBLISHED, pageable);
        long totalElements = scientificResearchDetailPage.getTotalElements();
        List<ScientificResearchDetail> content = scientificResearchDetailPage.getContent();
        ScientificResearchCategory category = getCategoryById(scientificResearchCategoryId);
        PageResponse<ScientificResearchDetail> pageResponse=new DetailPageResponse<>(content,pageNum,pageSize,totalElements,category.getCategory());
        return pageResponse;
    }
    @Override
    public ScientificResearchCategory getCategoryById(Long categoryId) {
        return scientificResearchCategoryDAO.findById(categoryId).orElse(null);
    }

    @Override
    public Map<String,ScientificResearchDetail> getPublishedById(Long id){
        Map<String,ScientificResearchDetail> map=new HashMap<>();
//        ScientificResearchDetail one = scientificResearchDetailDAO.getByIdAndPublishStatus(id,CommonConstants.PUBLISHED);
        List<Object[]> objects = scientificResearchDetailDAO.getByIdAndPublishStatus(id, CommonConstants.PUBLISHED);
        ScientificResearchDetail scientificResearchDetail=null;
        if(objects!=null&&!objects.isEmpty()){
            scientificResearchDetail= formatObj2Detail(objects.get(0));
        }
        if(scientificResearchDetail!=null){
            Long publishDate = scientificResearchDetail.getPublishDate();
            Long categoryId = scientificResearchDetail.getCategoryId();
            ScientificResearchDetail pre = getOneByPublishDate(id,categoryId,publishDate, ">=","asc");
            ScientificResearchDetail next = getOneByPublishDate(id,categoryId,publishDate, "<=","desc");
            map.put("detail",scientificResearchDetail);
            map.put("previous",pre);
            map.put("next",next);
        }
        return map;
    }



    private ScientificResearchDetail getOneByPublishDate(Long id,Long categoryId,Long publishDate, String operation,String sort){
        String sql="select srd.id,srd.title from scientific_research_detail srd where  srd.publishDate "+operation+
                " :publishDate  and srd.publishStatus=1  and srd.categoryId=:categoryId and srd.id!=:id " +
                " ORDER BY srd.publishDate "+sort+",srd.id "+sort+" limit 1";
        Query nativeQuery = entityManager.createNativeQuery(sql);
        nativeQuery.setParameter("id",id);
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
