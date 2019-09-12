package com.gooalgene.wutbiolab.entity.scientificResearch;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Table(name = "academic_category")
@Entity
@Data
public class AcademicCategory implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 学术类型
     */
    private String categoryName;



}
