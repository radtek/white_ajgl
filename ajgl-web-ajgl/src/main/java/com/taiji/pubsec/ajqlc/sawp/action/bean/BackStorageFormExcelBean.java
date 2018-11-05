package com.taiji.pubsec.ajqlc.sawp.action.bean;

/**
 * 返回单ExcelBean
 * 
 * @author WangLei
 *
 */
public class BackStorageFormExcelBean {

	private String count;

	private String code; // 编码

	private String updatedTime; // 更新时间

	private String outStorageFormCode; // 出库单编码

	private String caseCode; // 案件编号

	private String caseName; // 案件名称

	private String polices; // 办案民警

	private String typeName; // 出库类型，字典项名称

	private String remark; // 备注

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getOutStorageFormCode() {
		return outStorageFormCode;
	}

	public void setOutStorageFormCode(String outStorageFormCode) {
		this.outStorageFormCode = outStorageFormCode;
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

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
