package com.gooalgene.wutbiolab.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "home")
public class HomeRequest implements Serializable {

    private static final long serialVersionUID = -5248568620857022978L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String newsImage;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String academicImage;

    @Column(columnDefinition = "text")
    private String cooperationLink;

    @Column(columnDefinition = "text")
    private String footer;

    @ApiModelProperty(value = "发布状态(0:未发布，1:已发布)", example = "0")
    @Column(columnDefinition = "INT default 0")
    private Integer publishStatus = 0;


}
