package com.gooalgene.wutbiolab.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "researchTeam")
@Entity
@Data
public class ResearchTeam extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -6519103474643544011L;

    /**
     * 导师类型id
     */
    private String mentorTypeId;
    /**
     * 导师名称
     */
    private String mentorName;

    private Integer order;
}
