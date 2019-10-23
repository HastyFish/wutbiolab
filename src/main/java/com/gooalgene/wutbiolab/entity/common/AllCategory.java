package com.gooalgene.wutbiolab.entity.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
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

    private static final long serialVersionUID = -7330942254729890746L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "分类名称", example = "分类1")
    private String category;

    @Column(columnDefinition = "double default 0")
    private Double categoryOrder;

}
