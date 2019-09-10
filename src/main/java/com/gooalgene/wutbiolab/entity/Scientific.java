package com.gooalgene.wutbiolab.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 科研工作
 */
@Table(name = "scientific")
@Entity
@Data
public class Scientific implements Serializable {

    private static final long serialVersionUID = 3631378440172366790L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 大分类名称
     */
    private String categoryName;




}
