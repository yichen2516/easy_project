package online.lbprotocol.easy.hbapi.model.response;

import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
public class NlpResponseData {
	/**
	 * 情感分析
	 */
	private Emotion emotion;
	/**
	 * 关键词
	 */
	private Map<String, Double> keywords;
	/**
	 * 新闻分类
	 */
	private Map<String, Integer> category;
	/**
	 * 分词
	 */
	private Map<String, String> lexer;
	/**
	 * 地点
	 */
	private Map<String, String> loc;
	/**
	 * 实体
	 * ORG
	 * PER
	 * TIME
	 */
	private Map<String, String> plo;
	/**
	 * 摘要
	 */
	private String abs;

	@Getter
	@ToString
	public static class Emotion {
		private double confidence;
		private double negativeProb;
		private double positiveProb;
		private int sentiment;
	}
}
