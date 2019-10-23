package com.gooalgene.wutbiolab.entity.contactus;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "contact_us_detail")
public class ContactusDetail implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private Long publishDate;

    /**
     * 发布状态，一旦发布即为已发布，一旦保存即为草稿（未发布）
     */
    @Column(columnDefinition = "INT default 0")
    private Integer publishStatus = 0;

    @Column
    private Long categoryId;

    @Column
    private String category;

    @Column(columnDefinition = "LONGTEXT")
    private String context;


    @Transient
    private String firstCategory;

}
