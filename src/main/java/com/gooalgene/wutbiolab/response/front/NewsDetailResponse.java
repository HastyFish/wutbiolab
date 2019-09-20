package com.gooalgene.wutbiolab.response.front;

import com.gooalgene.wutbiolab.entity.news.NewsDetail;
import com.gooalgene.wutbiolab.entity.news.NewsOverview;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsDetailResponse {

    public NewsDetailResponse() {
    }

    public NewsDetailResponse(NewsDetail detail, NewsOverview previous, NewsOverview next) {
        this.detail = detail;
        this.previous = previous;
        this.next = next;
    }

    NewsDetail detail;

    NewsOverview previous;

    NewsOverview next;
}
