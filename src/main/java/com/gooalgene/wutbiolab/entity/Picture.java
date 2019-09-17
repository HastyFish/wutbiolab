package com.gooalgene.wutbiolab.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
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

    @JsonIgnore
    //物理文件路径
    private String targetFile;

    public Picture(String name, String url, String targetFile) {
        this.name = name;
        this.url = url;
        this.targetFile = targetFile;
    }
}
