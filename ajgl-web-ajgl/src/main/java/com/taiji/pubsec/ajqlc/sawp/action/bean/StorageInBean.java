package com.taiji.pubsec.ajqlc.sawp.action.bean;


/**
 * 入库单Bean
 * @author XIEHF
 */
public class StorageInBean {
	private String count;
	
	private String id;	// id
	
	private String code;	// 编码
	
	private String createdTimeStr;	// 创建时间
	
	private String inStorageTimeStr;	// 入库时间
	
	private String operator;	// 最后更新人姓名
	
	private String remark;	// 备注
	
	private String updatedTime;	// 更新时间	
	
	private String caseCode;	// 案件编号
	
	private String caseName;	// 案件名称
	
	private String papers;	// 文书名称，可多个，分号隔开
	
	private String suspectName;	// 嫌疑人姓名
	
	private String police;	// 办案民警
	
	/**
	 * @return	id	id
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return	code	入库单编号
	 */
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return	operator	最后更新人姓名
	 */
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return	remark	备注
	 */
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return	papers	文书名称，可多个，分号隔开
	 */
	public String getPapers() {
		return papers;
	}

	public void setPapers(String papers) {
		this.papers = papers;
	}
	
	/**
	 * @return	suspectName	嫌疑人姓名
	 */
	public String getSuspectName() {
		return suspectName;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}

	/**
	 * @return	createdTime	创建时间(入库时间)
	 */	
	public String getCreatedTimeStr() {
		return createdTimeStr;
	}

	public void setCreatedTimeStr(String createdTimeStr) {
		this.createdTimeStr = createdTimeStr;
	}

	/**
	 * @return	updatedTime	更新时间
	 */	
	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}
	
	/**
	 * @return	caseCode	案件编号
	 */
	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	/**
	 * @return	caseName	案件名称
	 */
	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	/**
	 * @return	police	办案民警
	 */
	public String getPolice() {
		return police;
	}

	public void setPolice(String police) {
		this.police = police;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getInStorageTimeStr() {
		return inStorageTimeStr;
	}

	public void setInStorageTimeStr(String inStorageTimeStr) {
		this.inStorageTimeStr = inStorageTimeStr;
	}

	
	
	
	
}
