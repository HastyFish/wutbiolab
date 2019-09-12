package com.gooalgene.wutbiolab.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GraduateResponse {
    private Long labDetailId;
    private String graduateCategoryName;
    private String title;
    private Long publishDate;
    private Integer publishStatus;
}
