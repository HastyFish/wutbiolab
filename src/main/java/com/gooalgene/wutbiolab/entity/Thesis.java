package com.gooalgene.wutbiolab.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "thesis")
@Entity
@Data
public class Thesis implements Serializable {

    private static final long serialVersionUID = -1230603782936178326L;
    @Id
    @Column(columnDefinition = "INT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String thesisTitle;
    private String periodicalName;
    private String author;
    private Long publishYear;

    private Long publishDate;
    @Column(columnDefinition = "TEXT")
    private String context;


    /**
     * 发布状态，一旦发布即为已发布，一旦保存即为已保存
     */
    private Integer publishStatus;

    @Column(columnDefinition = "TEXT")
    private String unpublishedJson;


}
