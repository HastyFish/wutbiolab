package com.gooalgene.wutbiolab.response.front;

import com.gooalgene.wutbiolab.response.common.PageResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DetailPageResponse<T> extends PageResponse<T> {

    public DetailPageResponse(List<T> list, Integer pageNum, Integer pageSize, Long total, String category) {
        super(list, pageNum, pageSize, total);
        this.category = category;
    }

    public DetailPageResponse(List<T> list, Integer pageNum, Integer pageSize, Long total) {
        super(list, pageNum, pageSize, total);
    }


    private String category;

}
