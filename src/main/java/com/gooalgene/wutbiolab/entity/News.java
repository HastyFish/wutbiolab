package com.gooalgene.wutbiolab.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

//@Table(name = "news")
//@Entity
//@Data
public class News implements Serializable {
    private static final long serialVersionUID = -3215337937375965993L;

    @Id
    @Column(columnDefinition = "INT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer category;
    private String title;
    private Long publishDate;

    private String cover;
    private Boolean needCover;

    @Column(columnDefinition = "TEXT")
    private String context;


    @Column(columnDefinition = "TEXT")
    private String unpublishedJson;

    /**
     * 发布状态，一旦发布即为已发布，一旦保存即为草稿（未发布）
     */
    @Column(columnDefinition = "INT default 0")
    private Integer publishStatus;



}
