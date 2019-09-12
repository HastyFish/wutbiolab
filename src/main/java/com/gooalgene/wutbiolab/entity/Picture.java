package com.gooalgene.wutbiolab.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Picture {

    private String uid;

    private String name;

    private String status;

    /**
     * 前端上传图片的时候存为base64，后端处理后存为url
     */
    private String url;
}
