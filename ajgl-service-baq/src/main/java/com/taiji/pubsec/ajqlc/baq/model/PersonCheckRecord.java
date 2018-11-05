package com.taiji.pubsec.ajqlc.baq.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.taiji.pubsec.businesscomponent.organization.model.Person;

/**
 * 人身检查记录
 * @author chengkai
 */
@Entity
@Table(name = "t_baq_rsjcjl")
public class PersonCheckRecord {
	
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;	// id
	
	
	@Column(name = "zszz", length = 200)
	private String selfReportedSymptoms;	// 自述病症
	
	
	@Column(name = "jcqk", length = 200)
	private String checkCondition;	// 检查情况
	
	
	@Column(name = "jcmj", length = 60)
	private String checkPolice;	// 检查民警
	
	
	@Column(name = "jzr", length = 60)
	private String eyewitness;	// 见证人
	
	
	@Column(name = "bjcr", length = 60)
	private String isCheckedPerson;	// 被检查人
	
	
	@ManyToOne
	@JoinColumn(name = "zxxgr_id")
	private Person modifyPeople;	// 最新修改人

	
	@Column(name = "xgsj")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedTime;	// 最新修改时间

	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "baqsyd_id")
	private HandlingAreaReceipt handlingAreaReceipt;	// 办案区使用单id

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
	 * @return	selfReportedSymptoms	自述病症
	 */
	public String getSelfReportedSymptoms() {
		return selfReportedSymptoms;
	}

	public void setSelfReportedSymptoms(String selfReportedSymptoms) {
		this.selfReportedSymptoms = selfReportedSymptoms;
	}

	/**
	 * @return	checkCondition	检查情况
	 */
	public String getCheckCondition() {
		return checkCondition;
	}

	public void setCheckCondition(String checkCondition) {
		this.checkCondition = checkCondition;
	}

	/**
	 * @return	checkPolice	检查民警
	 */
	public String getCheckPolice() {
		return checkPolice;
	}

	public void setCheckPolice(String checkPolice) {
		this.checkPolice = checkPolice;
	}

	/**
	 * @return	eyewitness	见证人
	 */
	public String getEyewitness() {
		return eyewitness;
	}

	public void setEyewitness(String eyewitness) {
		this.eyewitness = eyewitness;
	}

	/**
	 * @return	isCheckedPerson	被检查人
	 */
	public String getIsCheckedPerson() {
		return isCheckedPerson;
	}

	public void setIsCheckedPerson(String isCheckedPerson) {
		this.isCheckedPerson = isCheckedPerson;
	}
	
	
	/**
	 * @return modifyPeople 最新修改人
	 */
	public Person getModifyPeople() {
		return modifyPeople;
	}

	public void setModifyPeople(Person modifyPeople) {
		this.modifyPeople = modifyPeople;
	}

	/**|
	 * @return	updatedTime	最新修改时间
	 */
	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	/**
	 * @return	handlingAreaReceipt	办案区使用单
	 */
	public HandlingAreaReceipt getHandlingAreaReceipt() {
		return handlingAreaReceipt;
	}

	public void setHandlingAreaReceipt(HandlingAreaReceipt handlingAreaReceipt) {
		this.handlingAreaReceipt = handlingAreaReceipt;
	}
	
	
}