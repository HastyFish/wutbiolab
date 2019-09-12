package com.gooalgene.wutbiolab.entity.lab;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 毕业生类型
 */
@Table(name = "graduate_category")
@Entity
@Data
public class GraduateCategory implements Serializable {
    private static final long serialVersionUID = 7694145115741909007L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryName;
}
