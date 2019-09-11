package com.gooalgene.wutbiolab.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


//@Table(name = "academic")
//@Entity
//@Data
public class Academic extends BaseEntity implements Serializable {


    private static final long serialVersionUID = 5430378745336437789L;
    private Long startersCategoryId;


    /**
     * 学术类型
     */
    private String academicCategory;



}
