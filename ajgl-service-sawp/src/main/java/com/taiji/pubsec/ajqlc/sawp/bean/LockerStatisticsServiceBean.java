package com.taiji.pubsec.ajqlc.sawp.bean;

import java.util.List;

/**
 * 储物架存储情况Bean
 * 
 * @author WangLei
 *
 */
public class LockerStatisticsServiceBean {

	private String areaId;// 保管区id
	
	private String areaName;// 保管区名称
	
	private String lockerId;// 储物架id
	
	private String lockerName;// 储物架名称
	
	private String statuts;// 状态，是否空闲，字典项id
	
	private String statutsName;// 状态，是否空闲，是或否
	
	private List<String> articleList;// 当前存放的物品编号+名称
	
	private Double numberInStorage;// 在库总数量
	
	private String caseCode;// 案件编号
	
	private String caseName;// 案件名称
	
	private String polices;// 办案民警
	
	private String suspectedName;// 嫌疑人姓名
	
	private String suspectIdentityNumber;// 嫌疑人身份证号

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getLockerId() {
		return lockerId;
	}

	public void setLockerId(String lockerId) {
		this.lockerId = lockerId;
	}

	public String getLockerName() {
		return lockerName;
	}

	public void setLockerName(String lockerName) {
		this.lockerName = lockerName;
	}

	public String getStatuts() {
		return statuts;
	}

	public void setStatuts(String statuts) {
		this.statuts = statuts;
	}

	public String getStatutsName() {
		return statutsName;
	}

	public void setStatutsName(String statutsName) {
		this.statutsName = statutsName;
	}

	public List<String> getArticleList() {
		return articleList;
	}

	public void setArticleList(List<String> articleList) {
		this.articleList = articleList;
	}

	public Double getNumberInStorage() {
		return numberInStorage;
	}

	public void setNumberInStorage(Double numberInStorage) {
		this.numberInStorage = numberInStorage;
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

	public String getPolices() {
		return polices;
	}

	public void setPolices(String polices) {
		this.polices = polices;
	}

	public String getSuspectedName() {
		return suspectedName;
	}

	public void setSuspectedName(String suspectedName) {
		this.suspectedName = suspectedName;
	}

	public String getSuspectIdentityNumber() {
		return suspectIdentityNumber;
	}

	public void setSuspectIdentityNumber(String suspectIdentityNumber) {
		this.suspectIdentityNumber = suspectIdentityNumber;
	}
	
	
}
