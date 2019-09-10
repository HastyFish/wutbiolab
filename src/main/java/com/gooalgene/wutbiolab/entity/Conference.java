package com.gooalgene.wutbiolab.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 科研工作
 */
@Table(name = "conference")
@Entity
@Data
public class Conference implements Serializable {

    private static final long serialVersionUID = 7283628150365860390L;
    @Id
    @Column(columnDefinition = "INT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long scientificResearchId;


    private String category;
    private String title;

    private Long publishDate;
    @Column(columnDefinition = "TEXT")
    private String context;


    /**
     * 发布状态，一旦发布即为已发布，一旦保存即为草稿
     */
    @Column(columnDefinition = "INT default 0")
    private Integer publishStatus;

    @Column(columnDefinition = "TEXT")
    private String unpublishedJson;


}
