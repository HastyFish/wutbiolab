package com.gooalgene.wutbiolab.response.front;

import com.gooalgene.wutbiolab.entity.notice.NoticeDetail;
import com.gooalgene.wutbiolab.entity.notice.NoticeOverview;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeResponse {

    public NoticeResponse() {
    }

    public NoticeResponse(NoticeDetail noticeDetail, NoticeOverview next, NoticeOverview previous) {
        this.noticeDetail = noticeDetail;
        this.next = next;
        this.previous = previous;
    }

    NoticeDetail noticeDetail;

    NoticeOverview next;

    NoticeOverview previous;

}
