package online.lbprotocol.easy.hbapi.model.response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SocialMediaRecord {
    /**
     * 微博转载数
     */
    private String hbShare;
    /**
     * 原文链接
     */
    private String hbUrl;
    /**
     * 微信阅读数
     */
    private String hbRead;
    private String hbArtTag;
    /**
     * 榜单时间戳
     */
    private String hbRankTime;
    /**
     * 标题
     */
    private String hbTitle;
    /**
     * 微信在看数
     */
    private String hbWatch;
}