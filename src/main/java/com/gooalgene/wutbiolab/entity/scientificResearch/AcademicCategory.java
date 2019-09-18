package com.gooalgene.wutbiolab.entity.scientificResearch;

import com.gooalgene.wutbiolab.entity.common.AllCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Table(name = "academic_category")
@Entity
@Getter
@Setter
@DiscriminatorValue("academic")
public class AcademicCategory extends AllCategory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




}
