package com.taiji.pubsec.ajqlc.sawp.action.bean;


/**
 * 再入库快照物品列bean
 * 
 * @author
 *
 */
public class BackStorageSnapshotBean {
	
	private String id; // id
	
//	private IncomingSnapshot incomingSnapshot; // 本次入库数量快照记录
	
	private String articleName;	//物品名称
	
	private String articleFeature;	//物品特征
	
	private String articleCode;	//物品编号
	
	private String numberRequested;	//扣押数量
	
	private Double outComingNum;	//出库数量
	
	private Double thisIncomingNum;	//本次再入库数量
	
	private String measurementUnit;	//计量单位
	
	
	private String suspectName; //嫌疑人/物品持有人姓名
	
	private String suspectIdCardNo;	//嫌疑人/物品持有人证件号码
	
	private String ifInStorageAll;	//是否全部再入库

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

	public String getNumberRequested() {
		return numberRequested;
	}

	public void setNumberRequested(String numberRequested) {
		this.numberRequested = numberRequested;
	}

	public Double getOutComingNum() {
		return outComingNum;
	}

	public void setOutComingNum(Double outComingNum) {
		this.outComingNum = outComingNum;
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


	public String getSuspectName() {
		return suspectName;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}

	public String getSuspectIdCardNo() {
		return suspectIdCardNo;
	}

	public void setSuspectIdCardNo(String suspectIdCardNo) {
		this.suspectIdCardNo = suspectIdCardNo;
	}

	public String getIfInStorageAll() {
		return ifInStorageAll;
	}

	public void setIfInStorageAll(String ifInStorageAll) {
		this.ifInStorageAll = ifInStorageAll;
	}
	
}
