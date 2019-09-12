package com.gooalgene.wutbiolab.entity.news;

import com.gooalgene.wutbiolab.entity.common.AllCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "news_category")
@DiscriminatorValue("news")
public class NewsCategory extends AllCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
