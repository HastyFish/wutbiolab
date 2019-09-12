package com.gooalgene.wutbiolab.request;

import com.gooalgene.wutbiolab.entity.lab.LabDetail;
import lombok.Data;

import java.util.List;

@Data
public class MentorRequest {
    private Long mentorCategoryId;
    private List<LabDetail> mentors;
}
