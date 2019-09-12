package com.gooalgene.wutbiolab.entity.notice;

import com.gooalgene.wutbiolab.entity.common.AllCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "notice_category")
@DiscriminatorValue("notice")
public class NoticeCategory extends AllCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
