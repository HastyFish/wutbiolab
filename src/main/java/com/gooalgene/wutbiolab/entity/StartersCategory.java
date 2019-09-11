package com.gooalgene.wutbiolab.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 一级分类，针对实验室模块和科研工作模块
 */
@Table(name = "starters_category")
@Entity
@Data
public class StartersCategory implements Serializable {

    private static final long serialVersionUID = 3631378440172366790L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 大分类名称
     */
    private String categoryName;

    /**
     * 模块分类，实验室模块，科研工作模块
     */
    private Integer type;


}
