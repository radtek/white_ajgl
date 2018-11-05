package com.taiji.pubsec.ajqlc.alert.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.taiji.pubsec.businesscomponent.organization.model.Person;

/**
 * 预警消息
 * @author XIEHF
 */
@Entity
@Table(name = "t_baq_yjxx")
public class AlertMessage {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;	// id
	
	@ManyToOne
	@JoinColumn(name = "xxjsr_id")
	private Person receiver;	// 消息接收人
	
	
	@Column(name = "nr", length = 200)
	private String content;	// 消息内容
	
	@Column(name = "zt", length = 10)
	private String status;	// 消息状态，未签收：状态为“0”；已签收状态为“1”
	
	@Column(name = "ajbh")
	private String caseCode;	//案件编号
	
	@Column(name = "xyrbh")
	private String suspectId;	//嫌疑人编号
	
	@Column(name = "xyrxm")
	private String suspectName;	//嫌疑人姓名
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdTime")
	private Date createdTime;	//创建时间
	
	@ManyToOne
	@JoinColumn(name = "yjgz_id")
	private AlertRule alertRule;	// 预警规则
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "signTime")
	private Date signTime;	//签收时间

	/**
	 * @return id id
	 */
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 消息接收人
	 */
	public Person getReceiver() {
		return receiver;
	}

	public void setReceiver(Person receiver) {
		this.receiver = receiver;
	}

	/**
	 * 消息内容
	 */
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 最新时间
	 */
	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	/**
	 * 预警规则
	 */
	public AlertRule getAlertRule() {
		return alertRule;
	}

	public void setAlertRule(AlertRule alertRule) {
		this.alertRule = alertRule;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
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
	
	
	
}