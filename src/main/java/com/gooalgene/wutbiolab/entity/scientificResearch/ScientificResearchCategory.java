package com.gooalgene.wutbiolab.entity.scientificResearch;

import com.gooalgene.wutbiolab.entity.common.AllCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 科研工作大分类
 */
@Table(name = "scientific_research_category")
@Entity
@Getter
@Setter
@DiscriminatorValue("scientific")
public class ScientificResearchCategory extends AllCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;





}
