package com.taiji.pubsec.ajqlc.sawp.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * 入库单
 * @author chengkai
 */
@Entity
@Table(name = "t_sawp_rkd")
public class InStorageForm extends CommonForm {
	
	@Column(name = "ajbh", nullable = false)
	private String caseCode;	// 案件编号
	
	@Column(name = "wsmc")
	private String papers;	// 文书名称，可多个，分号隔开
	
	@Column(name = "xyrxm", nullable = false)
	private String suspectName;	// 嫌疑人姓名
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "rksj", nullable = false)
	private Date inStorageTime;	// 入库时间
	
	@Column(name = "ajmc")
	private String caseName;	//案件名称
	
	@Column(name = "bamj")
	private String handlePolices;	//办案民警
	
	@Column(name = "xyrid")
	private String suspectId;	//嫌疑人id
	
	@OneToMany(mappedBy = "form")
	private Set<InStorageFormItem> inStorageFormItems = new HashSet<InStorageFormItem>();	//	入库单项
	
	/**
	 * @return caseCode 案件编号
	 */
	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	
	/**
	 * @return papers 文书名称，可多个，分号隔开
	 */
	public String getPapers() {
		return papers;
	}

	public void setPapers(String papers) {
		this.papers = papers;
	}
	
	/**
	 * @return suspectName 嫌疑人姓名
	 */
	public String getSuspectName() {
		return suspectName;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}
	
	/**
	 * @return inStorageFormItems 入库单项
	 */
	public Set<InStorageFormItem> getInStorageFormItems() {
		return inStorageFormItems;
	}

	public void setInStorageFormItems(Set<InStorageFormItem> inStorageFormItems) {
		this.inStorageFormItems = inStorageFormItems;
	}

	public Date getInStorageTime() {
		return inStorageTime;
	}

	public void setInStorageTime(Date inStorageTime) {
		this.inStorageTime = inStorageTime;
	}

	/**
	 * @return caseName 案件名称
	 */
	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	
	/**
	 * @return handlePolices 办案民警
	 */
	public String getHandlePolices() {
		return handlePolices;
	}

	public void setHandlePolices(String handlePolices) {
		this.handlePolices = handlePolices;
	}
	
	/**
	 * @return suspectId 嫌疑人id
	 */
	public String getSuspectId() {
		return suspectId;
	}

	public void setSuspectId(String suspectId) {
		this.suspectId = suspectId;
	}
	
	
}