package com.gooalgene.wutbiolab.entity.scientificResearch;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

//todo 字段顺序严禁修改!!!!!!!!!!!!!!!
/**
 * 科研工作子模块详情
 */

@AllArgsConstructor
@Builder
@ApiModel(value = "科研工作子模块详情", description = "科研工作子模块详情")
@NoArgsConstructor
@Table(name = "scientific_research_detail")
@Entity
@Data
public class ScientificResearchDetail implements Serializable {
    private static final long serialVersionUID = -8184839871153993897L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "一级分类id", example = "10")
    private Long categoryId;

    @ApiModelProperty(value = "标题", example = "标题1")
    @Column
    private String title;

    @ApiModelProperty(value = "发布时间（时间戳）", example = "1568615594830")
    @Column
    private Long publishDate;

    /**
     * 发布状态，一旦发布即为已发布，一旦保存即为草稿（未发布）
     */
    @Builder.Default
    @ApiModelProperty(value = "发布状态(0:未发布，1:已发布)", example = "0")
    @Column(columnDefinition = "INT default 0")
    private Integer publishStatus = 0;


    @ApiModelProperty(value = "刊物名称", example = "刊物1")
    private String periodicalName;
    @ApiModelProperty(value = "作者", example = "作者1")
    private String author;
    @ApiModelProperty(value = "发布年度", example = "2019")
    private String publishYear;

    @ApiModelProperty(value = "富文本内容", example = "内容1")
    @Column(columnDefinition = "LONGTEXT")
    private String context;

    @ApiModelProperty(value = "学术类型id", example = "1")
    private Long academicCategoryId;

    @Transient
    private String category;

    @Transient
    private String firstCategory;


    public ScientificResearchDetail(Long id,String title, Long publishDate, Integer publishStatus, String periodicalName, String author,
                                    String publishYear) {
        this.id=id;
        this.title = title;
        this.publishDate = publishDate;
        this.publishStatus = publishStatus;
        this.periodicalName = periodicalName;
        this.author = author;
        this.publishYear = publishYear;
    }
    public ScientificResearchDetail(Long id,String title){
        this.id=id;
        this.title = title;
    }
}
