package com.gooalgene.wutbiolab.entity.lab;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 导师类型
 */
@Table(name = "mentor_type")
@Entity
@Data
public class MentorCategory implements Serializable {
    private static final long serialVersionUID = 2483636965097465390L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryName;

}
