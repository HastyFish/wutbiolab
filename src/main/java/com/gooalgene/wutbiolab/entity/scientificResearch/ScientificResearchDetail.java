package com.gooalgene.wutbiolab.entity.scientificResearch;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 科研工作子模块详情
 */
@Table(name = "scientific_research_detail")
@Entity
@Data
public class ScientificResearchDetail implements Serializable {
    private static final long serialVersionUID = -8184839871153993897L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long scientificResearchCategoryId;

    @Column
    private String title;

    @Column
    private Long publishDate;

    /**
     * 发布状态，一旦发布即为已发布，一旦保存即为草稿（未发布）
     */
    @Column(columnDefinition = "INT default 0")
    private Integer publishStatus = 0;


    private String periodicalName;
    private String author;
    private Long publishYear;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String context;

    private Long academicCategoryId;
}
