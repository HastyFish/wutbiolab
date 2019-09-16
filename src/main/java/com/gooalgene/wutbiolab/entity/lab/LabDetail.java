package com.gooalgene.wutbiolab.entity.lab;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 实验室四个模块通用详情表
 */
@NoArgsConstructor
@Table(name = "lab_detail")
@Entity
@Data
public class LabDetail implements Serializable {

    private static final long serialVersionUID = -6873685462844692336L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "标题", example = "标题1")
    @Column
    private String title;

    @ApiModelProperty(value = "发布时间（时间戳）", example = "1568615594830", required = true)
    @Column
    private Long publishDate;

    /**
     * 发布状态，一旦发布即为已发布，一旦保存即为草稿（未发布）
     */
    @ApiModelProperty(value = "发布状态(0:未发布，1:已发布)", example = "0")
    @Column(columnDefinition = "INT default 0")
    private Integer publishStatus = 0;

    @ApiModelProperty(value = "一级分类id", example = "1", required = true)
    private Long labCategoryId;

    @ApiModelProperty(value = "富文本内容", example = "内容")
    @Column(columnDefinition = "MEDIUMTEXT")
    private String context;

    @ApiModelProperty(value = "导师类型id", example = "1")
    /**
     * 导师类型id
     */
    private Long mentorCategoryId;

    @ApiModelProperty(value = "毕业生类型id", example = "2")
    /**
     * 毕业生类型id
     */
    private Long graduateCategoryId;

    @ApiModelProperty(value = "导师名称", example = "大学导师")
    private String mentorName;

    @ApiModelProperty(value = "导师排序", example = "1")
    /**
     * 导师排序
     */
    private Integer mentorOrder;

    public LabDetail(Long id,String title, Long publishDate) {
        this.id=id;
        this.title = title;
        this.publishDate = publishDate;
    }
}
