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
 * 返还单
 * @author chengkai
 */
@Entity
@Table(name = "t_sawp_fhd")
public class BackStorageForm extends CommonForm {
	
	@Column(name = "ajbh")
	private String caseCode;	// 案件编号
	
	@Column(name = "ckdbm")
	private String outStorageFormCode;	// 出库单编码
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cksj")
	private Date backStorageTime;	//再入库时间
	
	@Column(name = "ajmc")
	private String caseName;	//案件名称
	
	@Column(name = "bamj")
	private String handlePolices;	//办案民警
	
	@OneToMany(mappedBy = "form")
	private Set<BackStorageFormItem> backStorageFormItems = new HashSet<BackStorageFormItem>();	// 返还单项
	
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
	 * @return outStorageFormCode 出库单编码
	 */
	public String getOutStorageFormCode() {
		return outStorageFormCode;
	}

	public void setOutStorageFormCode(String outStorageFormCode) {
		this.outStorageFormCode = outStorageFormCode;
	}
	
	/**
	 * @return backStorageFormItems 返还单项
	 */
	public Set<BackStorageFormItem> getBackStorageFormItems() {
		return backStorageFormItems;
	}

	public void setBackStorageFormItems(Set<BackStorageFormItem> backStorageFormItems) {
		this.backStorageFormItems = backStorageFormItems;
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

	public Date getBackStorageTime() {
		return backStorageTime;
	}

	public void setBackStorageTime(Date backStorageTime) {
		this.backStorageTime = backStorageTime;
	}
	
}