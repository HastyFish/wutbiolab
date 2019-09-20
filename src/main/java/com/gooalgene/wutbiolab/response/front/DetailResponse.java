package com.gooalgene.wutbiolab.response.front;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailResponse<T1,T2> {

    public DetailResponse() {
    }

    public DetailResponse(T1 detail, T2 next, T2 previous) {
        this.detail = detail;
        this.next = next;
        this.previous = previous;
    }

    T1 detail;

    T2 next;

    T2 previous;

}
