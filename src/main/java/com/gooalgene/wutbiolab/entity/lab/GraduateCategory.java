package com.gooalgene.wutbiolab.entity.lab;

import com.gooalgene.wutbiolab.entity.common.AllCategory;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 毕业生类型
 */
@Table(name = "graduate_category")
@Entity
@Data
@DiscriminatorValue("graduate")
public class GraduateCategory extends AllCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
