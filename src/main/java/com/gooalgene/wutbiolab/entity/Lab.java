package com.gooalgene.wutbiolab.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "lab")
@Entity
@Data
public class Lab {
    @Id
    @Column(columnDefinition = "INT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;

    private String title;
    private Long publishDate;

    /**
     * 导师类型
     */
    private String mentorType;
    /**
     * 导师名称
     */
    private String mentorName;

    @Column(columnDefinition = "TEXT")
    private String context;


    @Column(columnDefinition = "TEXT")
    private String unpublishedJson;

    /**
     * 发布状态，一旦发布即为已发布，一旦保存即为草稿（未发布）
     */
    @Column(columnDefinition = "INT default 0")
    private Integer publishStatus;
}
