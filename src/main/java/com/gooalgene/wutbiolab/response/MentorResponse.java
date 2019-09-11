package com.gooalgene.wutbiolab.response;

import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import lombok.Data;

import java.util.List;

@Data
public class MentorResponse extends LabDetail {
    private Long mentorCategoryId;
    private String mentorCategoryName;
    private List<LabDetail> labDetails;

}
