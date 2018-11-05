package com.taiji.pubsec.ajqlc.baq.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.taiji.pubsec.businesscomponent.organization.model.Person;

/**
 * 办案区活动记录信息
 * @author wangfx
 *
 */
@Entity
@Table(name= "t_baq_hdjlxx")
public class HandleAreaActivityRecordInfo {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	@Column(name = "blsl", columnDefinition = "int default 0")
	private int recordCount;	// 笔录数量
	
	@ManyToOne
	@JoinColumn(name = "zxxgr_id")
	private Person modifyPeople;	// 最新修改人

	@Column(name = "xgsj", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedTime;	// 最新修改时间
	
	@OneToOne
	@JoinColumn(name = "baqsyd_id")
	private HandlingAreaReceipt handlingAreaReceipt;	//办案区使用单
	
	@OrderBy(value = "startTime")
	@OneToMany(mappedBy = "handleAreaActivityRecordInfo")
	private List<HandlingAreaActivityRecord> handlingAreaActivityRecords;	// 办案区活动记录

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
	 * @return modifyPeople 最新修改人
	 */
	public Person getModifyPeople() {
		return modifyPeople;
	}

	public void setModifyPeople(Person modifyPeople) {
		this.modifyPeople = modifyPeople;
	}

	/**
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
	
	/**
	 * @return handlingAreaActivityRecords 办案区活动记录
	 */
	public List<HandlingAreaActivityRecord> getHandlingAreaActivityRecords() {
		return handlingAreaActivityRecords;
	}

	public void setHandlingAreaActivityRecords(List<HandlingAreaActivityRecord> handlingAreaActivityRecords) {
		this.handlingAreaActivityRecords = handlingAreaActivityRecords;
	}
	
	/**
	 * @return RecordCount 笔录数量
	 */
	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	
	

}
