package com.gooalgene.wutbiolab.response.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Deprecated
public class PageResponse<T> {

    public PageResponse() {
    }

    public PageResponse(List<T> list) {
        this.list = list;
    }

    public PageResponse(List<T> list, Integer pageNum, Integer pageSize, Long total) {
        this.list = list;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
    }

    private List<T> list;

    private Integer pageNum;

    private Integer pageSize;

    private Long total;
}
