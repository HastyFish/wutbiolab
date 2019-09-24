package com.gooalgene.wutbiolab.entity.home.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "home_info")
@Inheritance
@DiscriminatorColumn(name = "discriminator")
public class HomeInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "LONGTEXT")
    private String context;

    @ApiModelProperty(value = "发布状态(0:未发布，1:已发布)", example = "0")
    @Column(columnDefinition = "INT default 0")
    private Integer publishStatus;
}
