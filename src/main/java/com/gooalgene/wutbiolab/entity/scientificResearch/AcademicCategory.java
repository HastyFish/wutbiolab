package com.gooalgene.wutbiolab.entity.scientificResearch;

import com.gooalgene.wutbiolab.entity.common.AllCategory;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Table(name = "academic_category")
@Entity
@Data
@DiscriminatorValue("academic")
public class AcademicCategory extends AllCategory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




}
