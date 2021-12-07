package online.lbprotocol.easy.hbapi.model.response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class VideoRecord {
    /**
     * 视频头图
     */
    private String hbVideoImg;
    /**
     * 发布媒体
     */
    private String hbCollector;
    /**
     * 账号名称
     */
    private String hbAuthor;
    /**
     * 标题
     */
    private String hbUrlTitle;
    /**
     * 视频链接
     */
    private String hbUrlVideo;
    /**
     * 内容
     */
    private String hbContent;
    /**
     * 账号ID
     */
    private String hbUid;
    /**
     * 视频链接
     */
    private String hbSiteName;
    private String hbSid;
    private String hbVia;
    /**
     * 主键
     */
    private String hbRid;
    /**
     * 账号头像
     */
    private String hbHeadImg;
    /**
     * 发稿日期
     */
    private String hbUrlTime;
    /**
     * 转发数
     */
    private String hbCount1;
    /**
     * 评论数
     */
    private String hbCount2;
    /**
     * 点赞数
     */
    private String hbCount3;
    private String hbCount4;
    /**
     * 原视频页面链接
     */
    private String hbUrlname;
}