package com.taiji.pubsec.ajqlc.baq.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 问询室分配记录
 * @author chengkai
 */
@Entity
@Table(name = "t_baq_wxsfpjl")
public class  AskRoomAllocationRecord {
	
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;	// id
	
	
	@Column(name = "fpr", length = 60)
	private String allocationPeople;	// 分配人
	
	@Column(name = "jh", length = 80)
	private String policeNum;	// 警号
	
	@Column(name = "fpsj")
	@Temporal(TemporalType.TIMESTAMP)
	private Date allocationTime;	// 分配时间
	
	@Column(name = "lksj")
	@Temporal(TemporalType.TIMESTAMP)
	private Date leaveTime;	// 离开时间
	
	@ManyToOne
	@JoinColumn(name = "baqsyd_id")
	private HandlingAreaReceipt handlingAreaReceipt;	// 办案区使用单id
	
	
	@ManyToOne
	@JoinColumn(name = "wxs_id")
	private ActivityRoom askRoom;	// 问询室id
	
	
	@OrderBy(value = "illegalTime")
	@OneToMany(mappedBy = "askRoomAllocationRecord", cascade = CascadeType.ALL)
	private List<AskRoomIllegalRecord> askRoomIllegalRecords = new ArrayList<AskRoomIllegalRecord>();	// 问询室违规记录
    
	@Column(name = "sxjlId", length = 60)
	private String trialRecordId; //审讯记录Id
	
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
	 * @return allocationPeople 分配人
	 */
	public String getAllocationPeople() {
		return allocationPeople;
	}

	public void setAllocationPeople(String allocationPeople) {
		this.allocationPeople = allocationPeople;
	}

	/**
	 * @return allocationTime	分配时间
	 */ 
	public Date getAllocationTime() {
		return allocationTime;
	}

	public void setAllocationTime(Date allocationTime) {
		this.allocationTime = allocationTime;
	}

	/**
	 * @return handlingAreaReceipt	办案区使用单
	 */
	public HandlingAreaReceipt getHandlingAreaReceipt() {
		return handlingAreaReceipt;
	}

	public void setHandlingAreaReceipt(HandlingAreaReceipt handlingAreaReceipt) {
		this.handlingAreaReceipt = handlingAreaReceipt;
	}
	
	/**
	 * @return askRoom	问询室
	 */
	public ActivityRoom getActivityRoom() {
		return askRoom;
	}

	public void setAskRoom(ActivityRoom askRoom) {
		this.askRoom = askRoom;
	}
	
	/**
	 * @return askRoomIllegalRecords 问询室违规记录
	 */
	public List<AskRoomIllegalRecord> getAskRoomIllegalRecords() {
		return askRoomIllegalRecords;
	}

	public void setAskRoomIllegalRecords(List<AskRoomIllegalRecord> askRoomIllegalRecords) {
		this.askRoomIllegalRecords = askRoomIllegalRecords;
	}
	
	/** 
	 * @param leaveTime 离开时间
	 */
	public Date getLeaveTime() {
		return leaveTime;
	}
	
	
	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}

	public ActivityRoom getAskRoom() {
		return askRoom;
	}

	public String getTrialRecordId() {
		return trialRecordId;
	}

	public void setTrialRecordId(String trialRecordId) {
		this.trialRecordId = trialRecordId;
	}

	public String getPoliceNum() {
		return policeNum;
	}

	public void setPoliceNum(String policeNum) {
		this.policeNum = policeNum;
	}

}