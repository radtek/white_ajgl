package com.taiji.pubsec.ajqlc.sawp.action.bean;

/**
 * 储物箱存储情况Excel Bean
 * 
 * @author WangLei
 *
 */
public class LockerStatisticsExcelBean {

	private String count;
	
	private String areaName;// 保管区名称
	
	private String lockerName;// 储物箱名称
	
	private String statutsName;// 状态，是否空闲，是或否
	
	private String article;// 存放的物品
	
	private String numberInStorage;// 在库总数量
	
	private String caseCode;// 案件编号
	
	private String caseName;// 案件名称
	
	private String polices;// 办案民警

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getLockerName() {
		return lockerName;
	}

	public void setLockerName(String lockerName) {
		this.lockerName = lockerName;
	}

	public String getStatutsName() {
		return statutsName;
	}

	public void setStatutsName(String statutsName) {
		this.statutsName = statutsName;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public String getNumberInStorage() {
		return numberInStorage;
	}

	public void setNumberInStorage(String numberInStorage) {
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
	
	
}
