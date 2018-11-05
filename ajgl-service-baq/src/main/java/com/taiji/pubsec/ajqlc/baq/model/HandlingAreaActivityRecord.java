package com.taiji.pubsec.ajqlc.baq.model;

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

/**
 * 办案区活动记录
 * @author chengkai
 */
@Entity
@Table(name = "t_baq_baqhdjl")
public class HandlingAreaActivityRecord {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;	// id
	
	@Column(name = "hdnr", length = 200)
	private String activityContent;	// 活动内容
	
	@Column(name = "hdlx", length = 100)
	private String activityType;	// 活动类型
	
	@Column(name = "zcy", length = 100)
	private String caretaker;	// 侦查员（看管人）
	
	@Column(name = "jssj")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;	// 结束时间
	
	@Column(name = "kssj")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;	// 开始时间
	
	@Column(name = "cjsj")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;	// 创建时间
	
	@Column(name = "zxxgsj")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedTime;	// 最新修改时间
	
	
	/**
	 * 
	 * @return updatedTime 最新修改时间
	 */
	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	/**
	 * 
	 * @return startTime 开始时间
	 */
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	/**
	 * @return createdTime 创建时间
	 */
	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Column(name = "whr", length = 100)
	private String maintainer;	// 维护人
	
	@Column(name = "whsj")
	@Temporal(TemporalType.TIMESTAMP)
	private Date maintainTime;	// 维护时间
	
	@Column(name = "bz", length = 200)
	private String note;	// 备注
	
	@ManyToOne
	@JoinColumn(name = "wxs_id")
	private ActivityRoom askRoom;	// 活动房间

	@ManyToOne
	@JoinColumn(name = "baqhdjlxx_id")
	private HandleAreaActivityRecordInfo handleAreaActivityRecordInfo;	// 办案区活动记录信息id
	
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
	 * @return	activityContent	活动内容
	 */
	public String getActivityContent() {
		return activityContent;
	}

	public void setActivityContent(String activityContent) {
		this.activityContent = activityContent;
	}

	/**
	 * @return	activityType	活动类型
	 */
	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	/**
	 * @return	caretaker	侦查员（看管人）
	 */
	public String getCaretaker() {
		return caretaker;
	}

	public void setCaretaker(String caretaker) {
		this.caretaker = caretaker;
	}

	/**
	 * @return	endTime	结束时间
	 */
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	

	/**
	 * @return	maintainer	维护人
	 */
	public String getMaintainer() {
		return maintainer;
	}

	public void setMaintainer(String maintainer) {
		this.maintainer = maintainer;
	}

	/**
	 * @return	maintainTime	维护时间
	 */
	public Date getMaintainTime() {
		return maintainTime;
	}

	public void setMaintainTime(Date maintainTime) {
		this.maintainTime = maintainTime;
	}

	/**
	 * @return	note	备注
	 */
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	
	/**
	 * @return askRoom 活动房间
	 */
	public ActivityRoom getAskRoom() {
		return askRoom;
	}

	public void setAskRoom(ActivityRoom askRoom) {
		this.askRoom = askRoom;
	}

	/**
	 * @return	handleAreaActivityRecordInfo	办案区活动记录信息
	 */
	public HandleAreaActivityRecordInfo getHandleAreaActivityRecordInfo() {
		return handleAreaActivityRecordInfo;
	}

	public void setHandleAreaActivityRecordInfo(HandleAreaActivityRecordInfo handleAreaActivityRecordInfo) {
		this.handleAreaActivityRecordInfo = handleAreaActivityRecordInfo;
	}


	
}