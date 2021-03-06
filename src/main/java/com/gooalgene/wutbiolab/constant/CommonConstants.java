package com.gooalgene.wutbiolab.constant;

public interface CommonConstants {
    //头条新闻
    long TOUTIAO = 30L;
    //综合新闻
    long ZONGHE = 31L;
    //学术活动
    long XUESHU = 33;
    //学术资源
    long ZIYUAN = 11;


    //学术资源
    long ZIYUAN2 = 37;
    long ZIYUAN3 = 38;
    long ZIYUAN4 = 39;



    //科研动态
    long KEYAN = 32;
    //通知公告
    long TONGZHI = 4;
    //招聘招生
    long ZHAOPIN = 36;


    /**
     * 发布
     */
    Integer PUBLISHED = 1;
    /**
     * 草稿（未发布）
     */
    Integer UNPUBLISHED = 0;
    /**
     * 发布时间的字段名
     */
    String PUBLISHDATEFIELD = "publishDate";
    String ID = "id";

    /**
     * 类别Id字段名
     */
    String CATEGORYIDFIELD = "categoryId";

    /**
     * 主键自增id字段名
     */
    String IDFIELD = "id";

    /**
     * 模块分类
     */
    Integer LAB = 1;
    Integer SCIENTIFIC = 2;

    /**
     * 实验室下相关
     */
    //机构概况
    Integer ORGANIZATION_PROFILE = 1;
    //研究方向
    Integer RESEARCHDIRECTION = 2;


    String DB_CONFIG_CACHE = "db_config_cache";
    String TOKEN_CACHE = "token_cache";

    /**
     * 所有一级分类名称
     */
    String CATEGORY_LAB = "lab";
    String CATEGORY_SCIENTIFICRESEARCH = "scientificResearch";
    String CATEGORY_NEWS = "news";
    String CATEGORY_RESOURCE = "resource";
    String CATEGORY_NOTICE = "notice";
    String CATEGORY_CONTACTUS = "contactus";


    /***
     * 排序字段
     */

    String ORDER_CATEGORY = "categoryOrder";

}
