package com.gooalgene.wutbiolab.entity;

import com.gooalgene.wutbiolab.constant.CommonConstants;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 论文
 */
//@Table(name = "thesis")
//@Entity
//@Data
public class Thesis implements Serializable {

    private static final long serialVersionUID = -5717645323223914836L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private Long publishDate;

    /**
     * 发布状态，一旦发布即为已发布，一旦保存即为草稿（未发布）
     */
    @Column(columnDefinition = "INT default 0")
    private Integer publishStatus = 0;

    private Long startersCategoryId;


    private String periodicalName;
    private String author;
    private Long publishYear;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String context;





}
