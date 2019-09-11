package com.gooalgene.wutbiolab.entity.news;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.lang.reflect.GenericArrayType;

@Getter
@Setter
@Entity
@Table(name = "news_category")
public class NewsCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String typeName;
}
