package com.gooalgene.wutbiolab.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

//@Table(name = "research_direction")
//@Entity
//@Data
public class ResearchDirection implements Serializable {
    private static final long serialVersionUID = -8459142846731415486L;

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private Long publishDate;

    /**
     * ����״̬��һ��������Ϊ�ѷ�����һ�����漴Ϊ�ݸ壨δ������
     */
    @Column(columnDefinition = "INT default 0")
    private Integer publishStatus = 0;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String context;
}
