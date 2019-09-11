package com.gooalgene.wutbiolab.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "research_team")
@Entity
@Data
public class ResearchTeam implements Serializable {
    private static final long serialVersionUID = -6519103474643544011L;


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

    /**
     * 导师类型id
     */
    private String mentorTypeId;
    /**
     * 导师名称
     */
    private String mentorName;

    private Integer researchTeamOrder;

    @Column(columnDefinition = "MEDIUMTEXT")
    protected String context;
}
