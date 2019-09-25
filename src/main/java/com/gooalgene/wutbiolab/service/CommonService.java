package com.gooalgene.wutbiolab.service;

public interface CommonService {
    <T> T getOneByPublishDateAndId(Class<T> tClass,Long count,Long id,Long categoryId,Long publishDate,String operation,String sort);
}
