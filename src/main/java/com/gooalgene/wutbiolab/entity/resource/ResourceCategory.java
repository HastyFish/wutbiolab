package com.gooalgene.wutbiolab.entity.resource;

import com.gooalgene.wutbiolab.entity.common.AllCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "resource_categroy")
@DiscriminatorValue("resource")
public class ResourceCategory extends AllCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
