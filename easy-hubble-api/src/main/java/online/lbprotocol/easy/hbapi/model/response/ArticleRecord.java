package online.lbprotocol.easy.hbapi.model.response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ArticleRecord {

	/**
	 * 发布媒体
	 */
	private String hbPublicMedia;
	/**
	 * 原文链接
	 */
	private String hbUrlName;
	/**
	 * 相似文章篇数
	 */
	private String hbRelateNum;
	/**
	 * 网站
	 */
	private String hbSiteName;
	/**
	 * sid主键
	 */
	private String hbSid;
	/**
	 * 主键
	 */
	private String hbRid;
	/**
	 * 发稿日期
	 */
	private String hbUrlTime;
	/**
	 * 热度值
	 */
	private String hbHotNum;
	private String hbDcSid;
	/**
	 * 榜单时间戳
	 * yyyy/MM/dd hh:mm:ss
	 */
	private String hbRankDate;
	/**
	 * 标题
	 */
	private String hbUrlTitle;
	/**
	 * 热度趋势
	 */
	private String hbHotTrend;

}
