package com.gooalgene.wutbiolab.entity.lab;

import com.gooalgene.wutbiolab.entity.common.AllCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = false)
@Table(name = "lab_category")
@Entity
@Data
@DiscriminatorValue("lab")
public class LabCategory extends AllCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
