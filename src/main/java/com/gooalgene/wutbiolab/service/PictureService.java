package com.gooalgene.wutbiolab.service;

public interface PictureService {

    /**
     *
     * @param oldPictureListString Picture的json字符串
     * @param newPictureListString Picture的json字符串
     * @return 更改url后的Picture字符串
     */
    String saveBase64(String oldPictureListString, String newPictureListString);

    /**
     * 将图片的相对路径带上访问链接
     * @param oldImageListString 待处理的图片json数组
     * @return 处理后的结果
     */
    String formImageUrl(String oldImageListString);
}
