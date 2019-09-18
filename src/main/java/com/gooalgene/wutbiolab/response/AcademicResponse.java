package com.gooalgene.wutbiolab.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AcademicResponse {
    private Long id;

    @ApiModelProperty(value = "标题", example = "标题1")
    private String title;

    @ApiModelProperty(value = "发布时间（时间戳）", example = "1568615594830")
    private Long publishDate;

    /**
     * 发布状态，一旦发布即为已发布，一旦保存即为草稿（未发布）
     */
    @ApiModelProperty(value = "发布状态(0:未发布，1:已发布)", example = "0")
    private Integer publishStatus;

    @ApiModelProperty(value = "学术类型名称", example = "学术会议")
    private String academicCategoryName;
}
