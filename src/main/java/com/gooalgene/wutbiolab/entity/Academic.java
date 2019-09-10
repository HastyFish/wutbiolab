package com.gooalgene.wutbiolab.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Table(name = "academic")
@Entity
@Data
public class Academic extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 7283628150365860390L;

    private Long startersCategoryId;


    /**
     * 学术类型
     */
    private String academicCategory;



}
