package com.gooalgene.wutbiolab.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "research_direction")
@Entity
@Data
public class ResearchDirection extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -8459142846731415486L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(columnDefinition = "MEDIUMTEXT")
    protected String context;
}
