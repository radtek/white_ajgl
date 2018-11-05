package com.taiji.pubsec.ajqlc.sawp.action.bean;


/**
 * 入库快照物品列bean
 * 
 * @author 
 *
 */
public class InStorageSnapshotBean {
	
	private String id; // id
	
//	private IncomingSnapshot incomingSnapshot; // 本次入库数量快照记录
	
	private String articleName;	//物品名称
	
	private String articleFeature;	//物品特征
	
	private String articleCode;	//物品编号
	
	private Double numberRequested;	//扣押应入库数量
	
	private String articleType;	//物品性质，字典项名称
	
	private Double thisIncomingNum;	//本次入库数量
	
	private String measurementUnit;	//计量单位
	
	private String paper;	//文书名称

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getArticleFeature() {
		return articleFeature;
	}

	public void setArticleFeature(String articleFeature) {
		this.articleFeature = articleFeature;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public Double getNumberRequested() {
		return numberRequested;
	}

	public void setNumberRequested(Double numberRequested) {
		this.numberRequested = numberRequested;
	}

	public String getArticleType() {
		return articleType;
	}

	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}

	public Double getThisIncomingNum() {
		return thisIncomingNum;
	}

	public void setThisIncomingNum(Double thisIncomingNum) {
		this.thisIncomingNum = thisIncomingNum;
	}

	public String getMeasurementUnit() {
		return measurementUnit;
	}

	public void setMeasurementUnit(String measurementUnit) {
		this.measurementUnit = measurementUnit;
	}

	public String getPaper() {
		return paper;
	}

	public void setPaper(String paper) {
		this.paper = paper;
	}
	
}
