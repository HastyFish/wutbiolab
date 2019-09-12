package com.gooalgene.wutbiolab.service;

public interface PictureService {

    /**
     *
     * @param oldPictureListString Picture的json字符串
     * @param newPictureListString Picture的json字符串
     * @return 更改url后的Picture字符串
     */
    String saveBase64(String oldPictureListString, String newPictureListString);
}
