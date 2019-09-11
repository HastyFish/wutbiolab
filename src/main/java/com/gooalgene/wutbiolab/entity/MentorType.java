package com.gooalgene.wutbiolab.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 导师类型
 */
@Table(name = "mentor_type")
@Entity
@Data
public class MentorType implements Serializable {
    private static final long serialVersionUID = 2483636965097465390L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String typeName;

}
