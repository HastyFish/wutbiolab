package com.gooalgene.wutbiolab.request;

import com.gooalgene.wutbiolab.entity.home.CooperationLink;
import com.gooalgene.wutbiolab.entity.home.Footer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
public class HomeImageRequest implements Serializable {

    private static final long serialVersionUID = -5248568620857022978L;

    private String newsImage;

    private String academicImage;

    private Integer publishStatus = 0;

}
