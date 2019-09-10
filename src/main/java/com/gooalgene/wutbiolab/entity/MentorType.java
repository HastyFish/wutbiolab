package com.gooalgene.wutbiolab.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "mentorType")
@Entity
@Data
public class MentorType implements Serializable {
    private static final long serialVersionUID = 2483636965097465390L;

    private String typeName;

}
