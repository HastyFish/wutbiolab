package com.gooalgene.wutbiolab.entity.home;

import com.gooalgene.wutbiolab.entity.home.common.HomeInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
@DiscriminatorValue("academic_picture")
public class AcademicImage extends HomeInfo {
}
