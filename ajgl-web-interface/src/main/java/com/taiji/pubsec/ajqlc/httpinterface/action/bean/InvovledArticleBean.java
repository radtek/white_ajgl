package com.taiji.pubsec.ajqlc.httpinterface.action.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 涉案物品bean
 * @author chengkai
 */
public class InvovledArticleBean {

	
	private String caseCode;	//案件编码
	
	private String caseName;	//案件名称
	
	private String suspectName;	//嫌疑人姓名
	
	private String papers;	//文书名称，多个逗号隔开
	
	private String articleCode;	//物品编码
	
	private String articleName;	//物品名称
	
	private String articleFeature;	//物品特征
	
	private List<StorageBean> locations = new ArrayList<StorageBean>();	//保管位置

	public InvovledArticleBean(){

	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getSuspectName() {
		return suspectName;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}

	public String getPapers() {
		return papers;
	}

	public void setPapers(String papers) {
		this.papers = papers;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
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

	public List<StorageBean> getLocations() {
		return locations;
	}

	public void setLocations(List<StorageBean> locations) {
		this.locations = locations;
	}
	
	

}