package com.taiji.pubsec.ajqlc.sawp.action.bean;

import java.util.List;

import com.taiji.pubsec.ajgl.util.bean.FileObjectBean;


/**
 * 入库单Bean
 * @author 
 */
public class TransitStorageOutBackBean {
	
	private String id;	// id
	
	private String code;	// 编码
	
	private Long createdTime;	// 创建时间(出库时间)

	private String caseCode;	// 案件编号
	
	private String caseName;	// 案件名称
	
	private String police;	// 办案民警

	private String type;	// 类型
	
	private String typeStr;	// 备注
	
	private String suspectName;	// 嫌疑人姓名
	
	private String restitution;	// 是否返还
	
	private String restitutionStr;	// 是否返还
	
	private String remark;	// 备注
	
	private String papers; // 对应文书
	
	private List<FileObjectBean> attachLst;	// 对应文书
	
	
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
	 * @return	remark	备注
	 */
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	/**
	 * @return	createdTime	创建时间(出库时间)
	 */	
	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeStr() {
		return typeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	public String getRestitution() {
		return restitution;
	}

	public void setRestitution(String restitution) {
		this.restitution = restitution;
	}


	public List<FileObjectBean> getAttachLst() {
		return attachLst;
	}

	public void setAttachLst(List<FileObjectBean> attachLst) {
		this.attachLst = attachLst;
	}

	public String getPapers() {
		return papers;
	}

	public void setPapers(String papers) {
		this.papers = papers;
	}

	public String getRestitutionStr() {
		return restitutionStr;
	}

	public void setRestitutionStr(String restitutionStr) {
		this.restitutionStr = restitutionStr;
	}

	
	
	
	
}
