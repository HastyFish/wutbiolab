package com.gooalgene.wutbiolab.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.dao.news.NewsCategoryDAO;
import com.gooalgene.wutbiolab.dao.news.NewsDetailDAO;
import com.gooalgene.wutbiolab.entity.Picture;
import com.gooalgene.wutbiolab.entity.news.NewsCategory;
import com.gooalgene.wutbiolab.entity.news.NewsDetail;
import com.gooalgene.wutbiolab.entity.news.NewsOverview;
import com.gooalgene.wutbiolab.response.common.CommonResponse;
import com.gooalgene.wutbiolab.response.common.PageResponse;
import com.gooalgene.wutbiolab.response.common.ResponseUtil;
import com.gooalgene.wutbiolab.response.front.DetailPageResponse;
import com.gooalgene.wutbiolab.response.front.DetailResponse;
import com.gooalgene.wutbiolab.service.NewsService;
import com.gooalgene.wutbiolab.service.PictureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    private NewsCategoryDAO newsCategoryDAO;

    private NewsDetailDAO newsDetailDAO;

    private PictureService pictureService;

    private ObjectMapper objectMapper;

    private Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);

    public NewsServiceImpl(NewsCategoryDAO newsCategoryDAO, NewsDetailDAO newsDetailDAO,
                           PictureService pictureService, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.pictureService = pictureService;
        this.newsDetailDAO = newsDetailDAO;
        this.newsCategoryDAO = newsCategoryDAO;
    }

    @Override
    public CommonResponse<PageResponse<NewsOverview>> newsDetailPage(Integer pageNum,
                                                                     Integer pageSize,
                                                                     Long categoryId) {
//        Page<NewsDetail> page = newsDetailDAO.findAll(PageRequest.of(pageNum - 1, pageSize));
        Sort.Order daterder = new Sort.Order(Sort.Direction.DESC, CommonConstants.PUBLISHDATEFIELD);
        Sort.Order categoryOrder = new Sort.Order(Sort.Direction.ASC, CommonConstants.CATEGORYIDFIELD);
        Sort.Order idOrder = new Sort.Order(Sort.Direction.DESC, CommonConstants.IDFIELD);
        Page<NewsOverview> page;
        if (null != categoryId) {
            page = newsDetailDAO.findNewsDetailByCategoryId(categoryId, PageRequest.of(pageNum - 1, pageSize,
                    Sort.by(daterder, categoryOrder, idOrder)));
        } else {
            page = newsDetailDAO.findNewsDetailBy(PageRequest.of(pageNum - 1, pageSize,
                    Sort.by(daterder, categoryOrder, idOrder)));
        }
        return ResponseUtil.success(new PageResponse<>(page.getContent(), pageNum, pageSize, page.getTotalElements()));
    }

    @Override
    public CommonResponse<List<NewsCategory>> allNewsCategory() {
        return ResponseUtil.success(newsCategoryDAO.findAll(Sort.by(CommonConstants.ORDER_CATEGORY)));
    }

    @Override
    public CommonResponse<NewsDetail> newsDetailById(Integer id) {
        if (newsDetailDAO.findById(id.longValue()).isPresent()) {
            NewsDetail newsDetail = newsDetailDAO.findById(id.longValue()).get();
            newsDetail.setImage(pictureService.formImageUrl(newsDetail.getImage()));
            return ResponseUtil.success(newsDetail);
        } else {
            return ResponseUtil.error("no such news!!!");
        }
    }

    @Override
    @Transactional
    public CommonResponse<Boolean> renewNews(NewsDetail newsDetail) {
        /*新增或保存时，查询前端返回的categoryId对应的category*/
        if (newsCategoryDAO.findById(newsDetail.getCategoryId()).isPresent()) {
            NewsCategory newsCategory = newsCategoryDAO.findById(newsDetail.getCategoryId()).get();
            newsDetail.setCategory(newsCategory.getCategory());
        } else {
            return ResponseUtil.error("Wrong category");
        }
        if (null != newsDetail.getId()) {
            if (newsDetailDAO.findById(newsDetail.getId()).isPresent()) {
                NewsDetail oldNewsDetail = newsDetailDAO.findById(newsDetail.getId()).get();
                try {
                    String newImage = pictureService.saveBase64(oldNewsDetail.getImage(), newsDetail.getImage());
                    if (null != newImage) {
                        newsDetail.setImage(newImage);
                    } else {
                        logger.error("fail to storge image");
                        newsDetail.setImage(objectMapper.writeValueAsString(new Picture()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("fail to storge image");
                }
                newsDetailDAO.save(newsDetail);
                return ResponseUtil.success(true);
            } else {
                logger.error("wrong id");
                return ResponseUtil.error("faild to save news");
            }
        } else {
            try {
//                if (newsDetail.getCategoryId().equals(CommonConstants.TOUTIAO)
//                        && newsDetailDAO.countByCategoryIdEquals(CommonConstants.TOUTIAO) >= 5) {
//                    return ResponseUtil.error("头条新闻已经有5条");
//                }
                String newImage = pictureService.saveBase64(null, newsDetail.getImage());
                if (null != newImage) {
                    newsDetail.setImage(newImage);
                } else {
                    logger.error("fail to storge image");
                    newsDetail.setImage(objectMapper.writeValueAsString(new Picture()));
                }
                newsDetailDAO.save(newsDetail);
                return ResponseUtil.success(true);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseUtil.error("fail to save news");
            }
        }
    }

    @Override
    @Transactional
    public CommonResponse<Boolean> deleteById(Integer id) {
        try {
            newsDetailDAO.deleteById(id.longValue());
            return ResponseUtil.success(true);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error("id is null");
        }
    }

    @Override
    public CommonResponse<DetailPageResponse<NewsOverview>> newsDetailPageByCategory(long categoryId,
                                                                                     int pageNum,
                                                                                     int pageSize) {
        if (newsCategoryDAO.findById(categoryId).isPresent()) {
            NewsCategory newsCategory = newsCategoryDAO.findById(categoryId).get();
            Page<NewsOverview> newsOverviewPage = newsDetailDAO.findByCategoryAndPublishStatusPage(newsCategory.getCategory(),
                    CommonConstants.PUBLISHED, PageRequest.of(pageNum - 1, pageSize,
                            Sort.by(
                                    new Sort.Order(Sort.Direction.DESC, CommonConstants.PUBLISHDATEFIELD),
                                    new Sort.Order(Sort.Direction.DESC, CommonConstants.IDFIELD)
                            )
                    ));
            return ResponseUtil.success(new DetailPageResponse<>(newsOverviewPage.getContent(), pageNum,
                    pageSize, newsOverviewPage.getTotalElements(), newsCategory.getCategory()));
        } else {
            return ResponseUtil.error("Wrong id");
        }
    }

    @Override
    public CommonResponse<DetailResponse<NewsDetail, NewsOverview>> newsDetailPublishedById(long id) {
        NewsDetail newsDetail = newsDetailDAO.findByIdAndPublishStatus(id, CommonConstants.PUBLISHED);
        NewsOverview next;
        NewsOverview previous;
        if (newsDetailDAO.countByPublishDateAndPublishStatus(
                newsDetail.getPublishDate(), CommonConstants.PUBLISHED) > 1) {
            next = nextPublishedNewsDetail(newsDetail.getPublishDate(),
                    newsDetail.getCategory(),
                    newsDetail.getId());
            previous = previousPublishedNewsDetail(newsDetail.getPublishDate(),
                    newsDetail.getCategory(),
                    newsDetail.getId());
        } else {
            next = nextPublishedNewsDetail(newsDetail.getPublishDate(),
                    newsDetail.getCategory(),
                    null);
            previous = previousPublishedNewsDetail(newsDetail.getPublishDate(),
                    newsDetail.getCategory(),
                    null);
        }
        return ResponseUtil.success(new DetailResponse<>(newsDetail, next, previous));
    }

    private NewsOverview nextPublishedNewsDetail(long publishDate, String category, Long id) {
        Page<NewsOverview> newsDetailPage;
        if (null != id) {
            newsDetailPage = newsDetailDAO.findNextNewsDetail(publishDate, category,
                    CommonConstants.PUBLISHED,
                    id,
                    PageRequest.of(0, 1,
                            Sort.by(
                                    new Sort.Order(Sort.Direction.DESC, CommonConstants.PUBLISHDATEFIELD),
                                    new Sort.Order(Sort.Direction.DESC, CommonConstants.IDFIELD)
                            )
                    )
            );
            if ((newsDetailPage.getTotalElements() > 0
                    && !newsDetailPage.getContent().get(0).getPublishDate().equals(publishDate))
                    || newsDetailPage.getTotalElements() == 0) {
                newsDetailPage = newsDetailDAO.findNextNewsDetail(publishDate, category,
                        CommonConstants.PUBLISHED,
                        PageRequest.of(0, 1,
                                Sort.by(
                                        new Sort.Order(Sort.Direction.DESC, CommonConstants.PUBLISHDATEFIELD),
                                        new Sort.Order(Sort.Direction.DESC, CommonConstants.IDFIELD)
                                )
                        )
                );
            }
        } else {
            newsDetailPage = newsDetailDAO.findNextNewsDetail(publishDate, category,
                    CommonConstants.PUBLISHED,
                    PageRequest.of(0, 1,
                            Sort.by(
                                    new Sort.Order(Sort.Direction.DESC, CommonConstants.PUBLISHDATEFIELD),
                                    new Sort.Order(Sort.Direction.DESC, CommonConstants.IDFIELD)
                            )
                    )
            );
        }
        if (newsDetailPage.getTotalElements() > 0) {
            return newsDetailPage.getContent().get(0);
        } else {
            return null;
        }
    }

    private NewsOverview previousPublishedNewsDetail(long publishDate, String category, Long id) {
        Page<NewsOverview> detailPage;
        if (null != id) {
            detailPage = newsDetailDAO.findPreviousNewsDetail(publishDate, category,
                    CommonConstants.PUBLISHED,
                    id,
                    PageRequest.of(0, 1,
                            Sort.by(
                                    new Sort.Order(Sort.Direction.ASC, CommonConstants.PUBLISHDATEFIELD),
                                    new Sort.Order(Sort.Direction.ASC, CommonConstants.IDFIELD)
                            )
                    )
            );
            if ((detailPage.getTotalElements() > 0
                    && !detailPage.getContent().get(0).getPublishDate().equals(publishDate))
                    || detailPage.getTotalElements() == 0) {
                detailPage = newsDetailDAO.findPreviousNewsDetail(publishDate, category,
                        CommonConstants.PUBLISHED,
                        PageRequest.of(0, 1,
                                Sort.by(
                                        new Sort.Order(Sort.Direction.ASC, CommonConstants.PUBLISHDATEFIELD),
                                        new Sort.Order(Sort.Direction.ASC, CommonConstants.IDFIELD)
                                )
                        )
                );
            }
        } else {
            detailPage = newsDetailDAO.findPreviousNewsDetail(publishDate, category,
                    CommonConstants.PUBLISHED,
                    PageRequest.of(0, 1,
                            Sort.by(
                                    new Sort.Order(Sort.Direction.ASC, CommonConstants.PUBLISHDATEFIELD),
                                    new Sort.Order(Sort.Direction.ASC, CommonConstants.IDFIELD)
                            )
                    )
            );
        }
        if (detailPage.getTotalElements() > 0) {
            return detailPage.getContent().get(0);
        } else {
            return null;
        }
    }
}
