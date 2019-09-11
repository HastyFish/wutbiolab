package com.gooalgene.wutbiolab.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "research_team")
@Entity
@Data
public class ResearchTeam extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -6519103474643544011L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
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
