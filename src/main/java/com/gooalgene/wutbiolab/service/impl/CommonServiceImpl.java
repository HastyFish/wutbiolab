package com.gooalgene.wutbiolab.service.impl;

import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.service.CommonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public  <T> T getOneByPublishDateAndId(Class<T> tClass,Long count,Long id,Long categoryId,Long publishDate,String operation,String sort){
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
}
