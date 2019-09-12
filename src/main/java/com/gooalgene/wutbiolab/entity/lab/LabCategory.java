package com.gooalgene.wutbiolab.entity.lab;

import lombok.Data;

import javax.persistence.*;

@Table(name = "lab_category")
@Entity
@Data
public class LabCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryName;


}
