package com.gooalgene.wutbiolab.entity.lab;

import com.gooalgene.wutbiolab.entity.common.AllCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 导师类型
 */
@EqualsAndHashCode(callSuper = false)
@Table(name = "mentor_category")
@Entity
@Data
@DiscriminatorValue("mentor")
public class MentorCategory extends AllCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
