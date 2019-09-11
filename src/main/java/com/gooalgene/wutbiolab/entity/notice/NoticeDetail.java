package com.gooalgene.wutbiolab.entity.notice;

import com.gooalgene.wutbiolab.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "notice_detail")
public class NoticeDetail extends BaseEntity {

    @Column(columnDefinition = "MEDIUMTEXT")
    private String context;

}
