package com.gooalgene.wutbiolab.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "organization")
@Entity
@Data
public class Organization extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -6873685462844692336L;
}
