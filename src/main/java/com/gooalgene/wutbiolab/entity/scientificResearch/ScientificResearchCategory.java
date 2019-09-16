package com.gooalgene.wutbiolab.entity.scientificResearch;

import com.gooalgene.wutbiolab.entity.common.AllCategory;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 科研工作大分类
 */
@Table(name = "scientific_research_category")
@Entity
@Data
@DiscriminatorValue("scientific")
public class ScientificResearchCategory extends AllCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;





}
