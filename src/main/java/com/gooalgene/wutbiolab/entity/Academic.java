package com.gooalgene.wutbiolab.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Table(name = "academic")
@Entity
@Data
public class Academic implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    protected String title;

    @Column
    protected Long publishDate;

    /**
     * 发布状态，一旦发布即为已发布，一旦保存即为草稿（未发布）
     */
    @Column(columnDefinition = "INT default 0")
    protected Integer publishStatus = 0;
    private static final long serialVersionUID = 5430378745336437789L;
    private Long startersCategoryId;


    /**
     * 学术类型
     */
    private String academicCategory;



}
