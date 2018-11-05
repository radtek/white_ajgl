package com.taiji.pubsec.ajqlc.sawp.action.bean;

/**
 * 暂存出库单Bean
 * 
 * @author lyp
 *
 */
public class TemporaryStorageOutBean {
	
	private String id; // id

	private String code; // 编码

	private Long outStorageTime;	// 出库时间

	private String operator; // 最后更新人姓名

	private String remark; // 备注

	private Long updatedTime; // 更新时间

	private String caseCode; // 案件编号

	private String caseName; // 案件名称
	
	private String papers; // 附件

	private String suspectName; // 嫌疑人姓名

	private String suspectId; // 嫌疑人身份证号

	private String polices; // 办案民警
	
	private String inStorageArea;//所在物证保管区
	
	private String storageRack;//所在储物箱
	
	private  String harCode;//使用单编号
	
	private String receivePerson;	//领取人
	
	private String status;	//状态

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getOutStorageTime() {
		return outStorageTime;
	}

	public void setOutStorageTime(Long outStorageTime) {
		this.outStorageTime = outStorageTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Long updatedTime) {
		this.updatedTime = updatedTime;
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

	public String getPapers() {
		return papers;
	}

	public void setPapers(String papers) {
		this.papers = papers;
	}

	public String getSuspectName() {
		return suspectName;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}

	public String getSuspectId() {
		return suspectId;
	}

	public void setSuspectId(String suspectId) {
		this.suspectId = suspectId;
	}

	public String getPolices() {
		return polices;
	}

	public void setPolices(String polices) {
		this.polices = polices;
	}


	public String getStorageRack() {
		return storageRack;
	}

	public void setStorageRack(String storageRack) {
		this.storageRack = storageRack;
	}

	public String getHarCode() {
		return harCode;
	}

	public void setHarCode(String harCode) {
		this.harCode = harCode;
	}

	public String getInStorageArea() {
		return inStorageArea;
	}

	public void setInStorageArea(String inStorageArea) {
		this.inStorageArea = inStorageArea;
	}

	public String getReceivePerson() {
		return receivePerson;
	}

	public void setReceivePerson(String receivePerson) {
		this.receivePerson = receivePerson;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
