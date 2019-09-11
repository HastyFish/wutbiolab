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
public class Thesis extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -5717645323223914836L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    private Long startersCategoryId;


    private String periodicalName;
    private String author;
    private Long publishYear;

    @Column(columnDefinition = "MEDIUMTEXT")
    protected String context;





}
