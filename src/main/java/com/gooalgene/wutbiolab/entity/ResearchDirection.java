package com.gooalgene.wutbiolab.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "research_direction")
@Entity
@Data
public class ResearchDirection extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -8459142846731415486L;
}
