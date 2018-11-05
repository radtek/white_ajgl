package com.taiji.pubsec.ajqlc.baq.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.taiji.pubsec.businesscomponent.organization.model.Person;

/**
 * 办案区使用单
 * @author chengkai 
 */
@Entity
@Table(name = "t_baq_baqsyd")
public class HandlingAreaReceipt {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;	// id
	
	@Column(name = "cjr")
	private String createPeopleId;	// 创建人
	
	@Column(name = "klzt", nullable = false)
	private String recordStatus;	// 刻录状态
	
	@Column(name = "cjsj", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;	// 创建时间
	
	@Column(name = "cjbm")
	private String createUnitId;	// 创建单位
	
	@ManyToOne
	@JoinColumn(name = "zxxgr_id")
	private Person modifyPeople;	// 最新修改人

	@Column(name = "xgsj", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedTime;	// 最新修改时间

	@Column(name = "zt", nullable = false, length = 20)
	private String status; //状态
	
	@Column(name = "wcsj")
	@Temporal(TemporalType.TIMESTAMP)
	private Date doneTime;	// 完成时间
	
	@OneToOne(mappedBy = "handlingAreaReceipt", cascade = CascadeType.ALL)
	private BasicCase basicCase;	// 基本情况
	
	@OneToOne(mappedBy = "handlingAreaReceipt", cascade = CascadeType.ALL)
	private CollectInfoSituation collectInfoSituation;	//采集信息情况
	
	@OneToOne(mappedBy = "handlingAreaReceipt", cascade = CascadeType.ALL)
	private PersonCheckRecord personCheckRecord;	//人身检查记录
	
	@OneToOne(mappedBy = "handlingAreaReceipt", cascade = CascadeType.ALL)
	private CarryGoodsInfo carryGoodsInfo;	//随身物品入库信息
	
	@OneToOne(mappedBy = "handlingAreaReceipt")
	private FinallyLeaveInfo finallyLeaveInfo;	// 最终离开信息
	
	@OneToOne(mappedBy = "handlingAreaReceipt", cascade = CascadeType.ALL)
	private HandleAreaActivityRecordInfo handleAreaActivityRecordInfo;	// 办案区活动记录信息
	
	@OneToMany(mappedBy = "handlingAreaReceipt")
	private List<AskRoomAllocationRecord> askRoomAllocationRecords = new ArrayList<AskRoomAllocationRecord> ();	//问询室分配记录
	
	@OneToMany(mappedBy = "handlingAreaReceipt",cascade=CascadeType.ALL)
	private Set<HandlingAreaReceiptToPoliceInfo> HandlingAreaReceiptToPoliceInfoList;
	
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
	 * @return modifyPeople 最新修改人
	 */
	public Person getModifyPeople() {
		return modifyPeople;
	}

	public void setModifyPeople(Person modifyPeople) {
		this.modifyPeople = modifyPeople;
	}

	/**
	 * @return updatedTime 最新修改时间
	 */
	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	/**
	 * @return status 状态，0:进行中，1：已完成
	 */
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * @return createPeopleId 创建人id
	 */
	public String getCreatePeopleId() {
		return createPeopleId;
	}

	public void setCreatePeopleId(String createPeopleId) {
		this.createPeopleId = createPeopleId;
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

	/**
	 * @return createUnitId 创建单位id
	 */
	public String getCreateUnitId() {
		return createUnitId;
	}

	public void setCreateUnitId(String createUnitId) {
		this.createUnitId = createUnitId;
	}
	
	/**
	 * @return basicCase 基本情况
	 */
	public BasicCase getBasicCase() {
		return basicCase;
	}

	

	public void setBasicCase(BasicCase basicCase) {
		this.basicCase = basicCase;
	}

	

	/**
	 * @return	collectInfoSituation	采集信息情况
	 */
	public CollectInfoSituation getCollectInfoSituation() {
		return collectInfoSituation;
	}

	public void setCollectInfoSituation(CollectInfoSituation collectInfoSituation) {
		this.collectInfoSituation = collectInfoSituation;
	}

	/**
	 * @return	personCheckRecord	人身检查记录
	 */
	public PersonCheckRecord getPersonCheckRecord() {
		return personCheckRecord;
	}

	public void setPersonCheckRecord(PersonCheckRecord personCheckRecord) {
		this.personCheckRecord = personCheckRecord;
	}

	/**
	 * 
	 * @return	carryGoodsInfo	随身物品入库信息
	 */
	public CarryGoodsInfo getCarryGoodsInfo() {
		return carryGoodsInfo;
	}

	public void setCarryGoodsInfo(CarryGoodsInfo carryGoodsInfo) {
		this.carryGoodsInfo = carryGoodsInfo;
	}
	
	/**
	 * 
	 * @return finallyLeaveInfo 最终离开信息
	 */
	public FinallyLeaveInfo getFinallyLeaveInfo() {
		return finallyLeaveInfo;
	}

	public void setFinallyLeaveInfo(FinallyLeaveInfo finallyLeaveInfo) {
		this.finallyLeaveInfo = finallyLeaveInfo;
	}
	
	/**
	 * 
	 * @return handleAreaActivityRecordInfo 办案区活动记录信息
	 */
	public HandleAreaActivityRecordInfo getHandleAreaActivityRecordInfo() {
		return handleAreaActivityRecordInfo;
	}

	public void setHandleAreaActivityRecordInfo(HandleAreaActivityRecordInfo handleAreaActivityRecordInfo) {
		this.handleAreaActivityRecordInfo = handleAreaActivityRecordInfo;
	}

	/**
	 * @return askRoomAllocationRecords 问询室分配记录
	 */
	public List<AskRoomAllocationRecord> getAskRoomAllocationRecords() {
		return askRoomAllocationRecords;
	}

	public void setAskRoomAllocationRecords(List<AskRoomAllocationRecord> askRoomAllocationRecords) {
		this.askRoomAllocationRecords = askRoomAllocationRecords;
	}

	/**
	 * @return doneTime 完成时间
	 */
	public Date getDoneTime() {
		return doneTime;
	}

	public void setDoneTime(Date doneTime) {
		this.doneTime = doneTime;
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	public Set<HandlingAreaReceiptToPoliceInfo> getHandlingAreaReceiptToPoliceInfoList() {
		return HandlingAreaReceiptToPoliceInfoList;
	}

	public void setHandlingAreaReceiptToPoliceInfoList(
			Set<HandlingAreaReceiptToPoliceInfo> handlingAreaReceiptToPoliceInfoList) {
		HandlingAreaReceiptToPoliceInfoList = handlingAreaReceiptToPoliceInfoList;
	}

}
