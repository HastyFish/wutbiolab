package com.gooalgene.wutbiolab.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 机构概况
 */
@Table(name = "institutional_profile")
@Entity
@Data
public class InstitutionalProfile extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -6873685462844692336L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(columnDefinition = "MEDIUMTEXT")
    protected String context;
}
