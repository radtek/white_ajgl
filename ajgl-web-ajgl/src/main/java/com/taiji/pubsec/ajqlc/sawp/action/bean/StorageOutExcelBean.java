package com.taiji.pubsec.ajqlc.sawp.action.bean;


/**
 * 出库单导出Bean
 * @author huangda
 */
public class StorageOutExcelBean {
	private String count;
	
	private String code;	// 编码
	
	private String createdTimeStr;	// 创建时间
	
    private String caseCode;	// 案件编号
	
	private String caseName;	// 案件名称
	
	private String police;	// 办案民警

	private String typeStr;	// 出库类型
	
	private String papers;	// 文书名称，可多个，分号隔开
	
	private String suspectName;	// 嫌疑人姓名
	
	private String restitutionStr;	// 出库类型

	private String remark;	// 备注
	

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

	public String getCount() {
		return count;
	}
	
	public void setCount(String count) {
		this.count = count;
	}

	public String getRestitutionStr() {
		return restitutionStr;
	}

	public void setRestitutionStr(String restitutionStr) {
		this.restitutionStr = restitutionStr;
	}

	public String getTypeStr() {
		return typeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	public String getPolice() {
		return police;
	}

	public void setPolice(String police) {
		this.police = police;
	}

	
	
	
	
}
