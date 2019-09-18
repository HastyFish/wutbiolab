package com.gooalgene.wutbiolab.constant;

public interface CommonConstants {
    //头条新闻
    Integer TOUTIAO=30;
    //综合新闻
    Integer ZONGHE=2;
    //科研动态
    Integer KEYAN=3;
    //通知公告
    Integer TONGZHI=4;
    //学术活动
    Integer XUESHU=5;

    /**
     * 发布
     */
    Integer PUBLISHED=1;
    /**
     * 草稿（未发布）
     */
    Integer UNPUBLISHED=0;

    /**
    * 模块分类
     */
    Integer LAB=1;
    Integer SCIENTIFIC=2;

    /**
     * 实验室下相关
     */
    //机构概况
    Integer ORGANIZATION_PROFILE=1;
    //研究方向
    Integer RESEARCHDIRECTION=2;


    String DB_CONFIG_CACHE="db_config_cache";
    String TOKEN_CACHE="token_cache";

}
