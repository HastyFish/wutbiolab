package com.gooalgene.wutbiolab.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 机构概况和研究方向的共用表
 */
@Table(name = "lab_summarize")
@Entity
@Data
public class LabDetail extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -6873685462844692336L;


    private Integer LabCategoryId;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String context;

    /**
     * 导师类型id
     */
    private Integer mentorCategoryId;

    /**
     * 毕业生类型id
     */
    private Integer graduateCategoryId;

    /**
     * 导师排序
     */
    private Integer mentorOrder;

}
