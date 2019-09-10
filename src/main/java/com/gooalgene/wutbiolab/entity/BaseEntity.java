package com.gooalgene.wutbiolab.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = -5961450421975493551L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String context;

    private Long publishDate;

    /**
     * 发布状态，一旦发布即为已发布，一旦保存即为草稿（未发布）
     */
    @Column(columnDefinition = "INT default 0")
    private Integer publishStatus;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String unpublishedJson;
}
