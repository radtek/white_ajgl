package com.taiji.pubsec.ajqlc.sla.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 刑事案件人员记录
 * @author chengkai
 *
 */
@Entity
@Table(name = "t_sla_xsajryjl")
public class PenalPersonRecord {

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
	 * 刑拘截止日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date detainedDeadline;
	
	/**
	 * 延长拘留开始日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date extendedDetainedStartTime;
	
	/**
	 * 延长拘留截止日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date extendedDetainedDeadline;
	
	/**
	 * 逮捕证截止日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date arrestDeadline;
	
	/**
	 * 取保截止日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date bailDeadline; 
	
	/**
	 * 监视居住截止日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date residentialSurveillanceDeadline;

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

	public Date getDetainedDeadline() {
		return detainedDeadline;
	}

	public void setDetainedDeadline(Date detainedDeadline) {
		this.detainedDeadline = detainedDeadline;
	}

	public Date getExtendedDetainedDeadline() {
		return extendedDetainedDeadline;
	}

	public void setExtendedDetainedDeadline(Date extendedDetainedDeadline) {
		this.extendedDetainedDeadline = extendedDetainedDeadline;
	}

	public Date getArrestDeadline() {
		return arrestDeadline;
	}

	public void setArrestDeadline(Date arrestDeadline) {
		this.arrestDeadline = arrestDeadline;
	}

	public Date getBailDeadline() {
		return bailDeadline;
	}

	public void setBailDeadline(Date bailDeadline) {
		this.bailDeadline = bailDeadline;
	}

	public Date getResidentialSurveillanceDeadline() {
		return residentialSurveillanceDeadline;
	}

	public void setResidentialSurveillanceDeadline(Date residentialSurveillanceDeadline) {
		this.residentialSurveillanceDeadline = residentialSurveillanceDeadline;
	}

	public Date getExtendedDetainedStartTime() {
		return extendedDetainedStartTime;
	}

	public void setExtendedDetainedStartTime(Date extendedDetainedStartTime) {
		this.extendedDetainedStartTime = extendedDetainedStartTime;
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
	
	
}
