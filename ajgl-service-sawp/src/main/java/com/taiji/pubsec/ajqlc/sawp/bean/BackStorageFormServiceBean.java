package com.taiji.pubsec.ajqlc.sawp.bean;

/**
 * 
 * @author wangfx
 *
 */
public class BackStorageFormServiceBean {

	private String id; // id

	private String outStorageFormId;// 出库单id

	private String outStorageFormCode; // 出库单编码

	private String code; // 编码

	private Long createdTime; // 创建时间

	private String operator; // 最后更新人姓名

	private String remark; // 备注

	private Long updatedTime; // 更新时间

	private String caseCode; // 案件编号

	private String caseName; // 案件名称

	private String polices; // 办案民警

	private String isReturned; // 是否全部返回，只针对借出和其他类型出库单

	private String papers; // 文书名称，各物品关联的文书名称集合

	private String receiver; // 领取人

	private String suspectName; // 嫌疑人姓名

	private String type; // 出库类型，字典项id

	private String typeName; // 出库类型，字典项名称

	public String getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getOperator() {
		return operator;
	}

	public String getRemark() {
		return remark;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public String getIsReturned() {
		return isReturned;
	}

	public String getPapers() {
		return papers;
	}

	public String getReceiver() {
		return receiver;
	}

	public String getSuspectName() {
		return suspectName;
	}

	public String getType() {
		return type;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public void setIsReturned(String isReturned) {
		this.isReturned = isReturned;
	}

	public void setPapers(String papers) {
		this.papers = papers;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public Long getUpdatedTime() {
		return updatedTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public void setUpdatedTime(Long updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getOutStorageFormCode() {
		return outStorageFormCode;
	}

	public void setOutStorageFormCode(String outStorageFormCode) {
		this.outStorageFormCode = outStorageFormCode;
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

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getOutStorageFormId() {
		return outStorageFormId;
	}

	public void setOutStorageFormId(String outStorageFormId) {
		this.outStorageFormId = outStorageFormId;
	}

}
