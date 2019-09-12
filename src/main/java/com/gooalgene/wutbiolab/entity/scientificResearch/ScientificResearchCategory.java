package com.gooalgene.wutbiolab.entity.scientificResearch;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 科研工作大分类
 */
@Table(name = "scientific_research_category")
@Entity
@Data
public class ScientificResearchCategory implements Serializable {

    private static final long serialVersionUID = 3631378440172366790L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 大分类名称
     */
    private String categoryName;



}
