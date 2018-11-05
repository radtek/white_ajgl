package com.taiji.pubsec.ajqlc.sla.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 行政案件人员记录
 * @author chengkai
 *
 */
@Entity
@Table(name = "t_sla_xzajryjl")
public class AdministrationPersonRecord {
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	String id;
	
	/**
	 * 嫌疑人id
	 */
	private String suspectId;
	
	/**
	 * 嫌疑人名字
	 */
	private String suspectName;
	
	/**
	 * 嫌疑人身份证号
	 */
	private String suspectIdNo;
	
	/**
	 * 进入办案区时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date enterAreaTime;
	
	/**
	 * 案件编号
	 */
	private String caseCode;
	
	/**
	 * 行政拘留描述
	 */
	@Column(length = 2000)
	private String administrationArrest;
	
	/**
	 * 行政拘留处罚文书填发时间（或批准时间或录入时间）
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date administrationArrestTime;
	
	/**
	 * 责令社区戒毒开始时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date communityDrugStartTime;
	
	/**
	 * 责令社区戒毒截止时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date communityDrugDeadline;
	
	/**
	 * 强制隔离戒毒开始时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date isolationDrugStartTime;
	
	/**
	 * 强制隔离戒毒截止时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date isolationDrugDeadline;
	
	/**
	 * 是否结案
	 */
	private String ifCloseCase;	//是否结案，结案为“是”，否则为“否”

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSuspectId() {
		return suspectId;
	}

	public void setSuspectId(String suspectId) {
		this.suspectId = suspectId;
	}

	public String getSuspectName() {
		return suspectName;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getAdministrationArrest() {
		return administrationArrest;
	}

	public void setAdministrationArrest(String administrationArrest) {
		this.administrationArrest = administrationArrest;
	}

	public Date getCommunityDrugDeadline() {
		return communityDrugDeadline;
	}

	public void setCommunityDrugDeadline(Date communityDrugDeadline) {
		this.communityDrugDeadline = communityDrugDeadline;
	}

	public Date getIsolationDrugDeadline() {
		return isolationDrugDeadline;
	}

	public void setIsolationDrugDeadline(Date isolationDrugDeadline) {
		this.isolationDrugDeadline = isolationDrugDeadline;
	}

	public Date getAdministrationArrestTime() {
		return administrationArrestTime;
	}

	public void setAdministrationArrestTime(Date administrationArrestTime) {
		this.administrationArrestTime = administrationArrestTime;
	}

	public Date getCommunityDrugStartTime() {
		return communityDrugStartTime;
	}

	public void setCommunityDrugStartTime(Date communityDrugStartTime) {
		this.communityDrugStartTime = communityDrugStartTime;
	}

	public Date getIsolationDrugStartTime() {
		return isolationDrugStartTime;
	}

	public void setIsolationDrugStartTime(Date isolationDrugStartTime) {
		this.isolationDrugStartTime = isolationDrugStartTime;
	}

	public String getSuspectIdNo() {
		return suspectIdNo;
	}

	public void setSuspectIdNo(String suspectIdNo) {
		this.suspectIdNo = suspectIdNo;
	}

	public Date getEnterAreaTime() {
		return enterAreaTime;
	}

	public void setEnterAreaTime(Date enterAreaTime) {
		this.enterAreaTime = enterAreaTime;
	}

	public String getIfCloseCase() {
		return ifCloseCase;
	}

	public void setIfCloseCase(String ifCloseCase) {
		this.ifCloseCase = ifCloseCase;
	}
	
	
	
}
