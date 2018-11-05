package com.taiji.pubsec.ajqlc.baq.action.bean;


/**
 * 采集信息情况Bean
 * 
 * @author WL
 *
 */
public class CollectInfoSituationBean {

	private String id;
	
	private String collectProject;// 收集项目,选择多个用逗号隔开
	
	private String collectProjectOther;// 其他的内容
	
	private String infoIntoString;// 信息入库，0:是；1:否
	
	private String infoIntoStringName;// 信息入库字典项名称
	
	private String inspectComparison;// 核查对比，0:是；1:否
	
	private String inspectComparisonName;// 核查对比字典项名称
	
	private String isCollect;// 是否收集，0:是；1:否
	
	private String isCollectName;// 是否收集字典项名称
	
	private PersonBean modifyPeopleBean;// 最新修改人
	
	private String updateTime;// 最新修改时间
	
	private String handlingAreaReceiptId;// 办案区使用单id
	
	private String qqNum;	//qq号码（，隔开多个）
	
	private String weixinNum;	//微信号（，隔开多个）

	private String phoneInfo;	//手机信息（手机号，imei，mac；手机号，imei，mac）
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCollectProject() {
		return collectProject;
	}

	public void setCollectProject(String collectProject) {
		this.collectProject = collectProject;
	}

	public String getCollectProjectOther() {
		return collectProjectOther;
	}

	public void setCollectProjectOther(String collectProjectOther) {
		this.collectProjectOther = collectProjectOther;
	}

	public String getInfoIntoString() {
		return infoIntoString;
	}

	public void setInfoIntoString(String infoIntoString) {
		this.infoIntoString = infoIntoString;
	}

	public String getInspectComparison() {
		return inspectComparison;
	}

	public void setInspectComparison(String inspectComparison) {
		this.inspectComparison = inspectComparison;
	}

	public String getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(String isCollect) {
		this.isCollect = isCollect;
	}

	public PersonBean getModifyPeopleBean() {
		return modifyPeopleBean;
	}

	public void setModifyPeopleBean(PersonBean modifyPeopleBean) {
		this.modifyPeopleBean = modifyPeopleBean;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getHandlingAreaReceiptId() {
		return handlingAreaReceiptId;
	}

	public void setHandlingAreaReceiptId(String handlingAreaReceiptId) {
		this.handlingAreaReceiptId = handlingAreaReceiptId;
	}

	public String getInfoIntoStringName() {
		return infoIntoStringName;
	}

	public void setInfoIntoStringName(String infoIntoStringName) {
		this.infoIntoStringName = infoIntoStringName;
	}

	public String getInspectComparisonName() {
		return inspectComparisonName;
	}

	public void setInspectComparisonName(String inspectComparisonName) {
		this.inspectComparisonName = inspectComparisonName;
	}

	public String getIsCollectName() {
		return isCollectName;
	}

	public void setIsCollectName(String isCollectName) {
		this.isCollectName = isCollectName;
	}

	public String getQqNum() {
		return qqNum;
	}

	public void setQqNum(String qqNum) {
		this.qqNum = qqNum;
	}

	public String getWeixinNum() {
		return weixinNum;
	}

	public void setWeixinNum(String weixinNum) {
		this.weixinNum = weixinNum;
	}

	public String getPhoneInfo() {
		return phoneInfo;
	}

	public void setPhoneInfo(String phoneInfo) {
		this.phoneInfo = phoneInfo;
	}

}
