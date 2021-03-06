package com.gooalgene.wutbiolab.entity.news;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "news_detail")
@Entity
@Data
public class NewsDetail implements Serializable {
    private static final long serialVersionUID = -3215337937375965993L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;

    private String title;

    private Long publishDate;

    @Column(columnDefinition = "text")
    private String image;

    @Column(columnDefinition = "LONGTEXT")
    private String context;

    @Column
    private Long categoryId;

    /**
     * 发布状态，一旦发布即为已发布，一旦保存即为草稿（未发布）
     */
    @Column(columnDefinition = "INT default 0")
    private Integer publishStatus;

}
