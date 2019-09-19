package com.gooalgene.wutbiolab.response.front;

import com.gooalgene.wutbiolab.entity.news.NewsDetail;
import com.gooalgene.wutbiolab.entity.news.NewsOverview;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsResponse {

    public NewsResponse() {
    }

    public NewsResponse(NewsDetail newsDetail, NewsOverview previous, NewsOverview next) {
        this.newsDetail = newsDetail;
        this.previous = previous;
        this.next = next;
    }

    NewsDetail newsDetail;

    NewsOverview previous;

    NewsOverview next;
}
