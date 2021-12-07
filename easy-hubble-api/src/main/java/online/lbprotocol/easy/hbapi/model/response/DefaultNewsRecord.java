package online.lbprotocol.easy.hbapi.model.response;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class DefaultNewsRecord {
    /**
     * 文章内图片地址
     */
    private List<String> hbImageUrl;
    /**
     * 文章内视频地址
     */
    private List<String> hbVideoUrl;
    /**
     * 摘要
     */
    private String hbAbstract;
    private String hbAccountCity;
    private String hbAccountFans;
    private String hbAccountProvince;
    /**
     * APP转载数
     */
    private String hbAppReprint;
    /**
     * 作者
     */
    private String hbAuthors;
    /**
     * 情感标签
     */
    private String hbBbCommon;
    private String hbCatalog;
    /**
     * 头条号、企鹅号，搜狐号发布媒体
     */
    private String hbChannel;
    /**
     * 发布媒体
     */
    private String hbCollector;
    /**
     * 原文内容
     */
    private String hbContent;
    /**
     * 2视频 1图片 0文字 3既有图片又有视频
     */
    private String hbContentType;
    /**
     * 转发
     */
    private String hbCount1;
    /**
     * 评论
     */
    private String hbCount2;
    /**
     * 点赞
     */
    private String hbCount3;
    private String hbCount4;
    /**
     * 影响力指数
     */
    private String hbCountIndex;
    /**
     * 总转载数
     */
    private String hbCountReprint;
    /**
     * 缩略图
     */
    private String hbHeadImg;
    /**
     * 高亮词汇
     */
    private String hbHighLight;
    /**
     * 相关文章篇数
     */
    private String hbHotNum;
    /**
     * 媒体分类
     */
    private String hbInfoType;
    /**
     * 是否原创 1原创 0非原创
     */
    private String hbIsOrigin;
    /**
     * 地点标签
     */
    private String hbLoc;
    /**
     * 机构标签
     */
    private String hbOrg;
    /**
     * 人物标签
     */
    private String hbPeople;
    /**
     * 传播路径
     */
    private String hbReprintRoute;
    /**
     * 主键
     */
    private String hbRid;
    /**
     * 主键
     */
    private String hbSerial;
    private String hbSid;
    /**
     * 媒体名称
     */
    private String hbSource;
    /**
     * 媒体来源
     */
    private String hbSrcName;
    /**
     * 报刊转载数
     */
    private String hbSzbReprint;
    /**
     * 头条转载数
     */
    private String hbToutiaoReprint;
    /**
     * 原文链接
     */
    private String hbUrlname;
    /**
     * 发稿日期
     */
    private String hbUrlTime;
    /**
     * 标题
     */
    private String hbUrlTitle;
    private String hbVia;
    /**
     * 网站转载数
     */
    private String hbWebReprint;
    /**
     * 微博转载数
     */
    private String hbWeiboReprint;
    /**
     * 微信转载数
     */
    private String hbWeixinReprint;
}