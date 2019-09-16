package com.gooalgene.wutbiolab.entity.common;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "all_category")
@Inheritance
@DiscriminatorColumn(name = "discriminator")
public class AllCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;

}