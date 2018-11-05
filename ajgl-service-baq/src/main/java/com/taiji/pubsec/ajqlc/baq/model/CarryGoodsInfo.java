package com.taiji.pubsec.ajqlc.baq.model;

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
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.taiji.pubsec.businesscomponent.organization.model.Person;

/**
 * 随身物品信息
 * @author wangfx
 *
 */
@Entity
@Table(name = "t_baq_sswpxx")
public class CarryGoodsInfo {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	
	@Column(name = "sswpgly")
	private String carryGoodsAdmin;	//随身物品管理员
	
	
	@Column(name = "bary")
	private String handleCasePolice;	//办案人员
	
	@Column(name = "sary")
	private String personsInvolved;	//涉案人员
	
	@ManyToOne
	@JoinColumn(name = "zxxgr_id")
	private Person modifyPeople;	// 最新修改人

	@Column(name = "xgsj", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedTime;		// 最新修改时间
	
	@Column(name = "jlwhxgsj")      
	@Temporal(TemporalType.TIMESTAMP)
	private Date recordUpdatedTime;	// 随身物品记录维护最新修改时间
	
	@ManyToOne
	@JoinColumn(name = "jlwhzxxgr_id")
	private Person recordModifyPeople;	// 随身物品记录维护最新修改人
	
	@Column(name = "fhwhxgsj")      
	@Temporal(TemporalType.TIMESTAMP)
	private Date returnUpdatedTime;	// 随身物品返还维护最新修改时间
	
	@ManyToOne
	@JoinColumn(name = "fhwhzxxgr_id")
	private Person returnModifyPeople;	// 随身物品返还维护最新修改人
	
	
	@OneToOne
	@JoinColumn(name = "baqsyd_id")
	private HandlingAreaReceipt handlingAreaReceipt; 	//办案区使用单
	
	@OrderBy(value = "num")
	@OneToMany(mappedBy = "carryGoodsInfo",cascade = CascadeType.ALL)
	private List<CarryGoodsRecord> carryGoodsRecords;	// 随身物品入库信息
	
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
	 * @return	carryGoodsAdmin	随身物品管理员
	 */
	public String getCarryGoodsAdmin() {
		return carryGoodsAdmin;
	}

	public void setCarryGoodsAdmin(String carryGoodsAdmin) {
		this.carryGoodsAdmin = carryGoodsAdmin;
	}

	/**
	 * @return	handleCasePolice	办案人员
	 */
	public String getHandleCasePolice() {
		return handleCasePolice;
	}

	public void setHandleCasePolice(String handleCasePolice) {
		this.handleCasePolice = handleCasePolice;
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
	 * @return carryGoodsRecords 随身物品入库信息
	 */
	public List<CarryGoodsRecord> getCarryGoodsRecords() {
		return carryGoodsRecords;
	}

	public void setCarryGoodsRecords(List<CarryGoodsRecord> carryGoodsRecords) {
		this.carryGoodsRecords = carryGoodsRecords;
	}
	
	/**
	 * @return personsInvolved 涉案人员
	 */
	public String getPersonsInvolved() {
		return personsInvolved;
	}
	
	public void setPersonsInvolved(String personsInvolved) {
		this.personsInvolved = personsInvolved;
	}
	
	/**
	 * @return recordUpdatedTime 随身物品记录维护最新修改时间
	 */
	public Date getRecordUpdatedTime() {
		return recordUpdatedTime;
	}

	public void setRecordUpdatedTime(Date recordUpdatedTime) {
		this.recordUpdatedTime = recordUpdatedTime;
	}
	
	/**
	 * @return recordModifyPeople 随身物品记录维护最新修改人
	 */
	public Person getRecordModifyPeople() {
		return recordModifyPeople;
	}

	public void setRecordModifyPeople(Person recordModifyPeople) {
		this.recordModifyPeople = recordModifyPeople;
	}
	
	/**
	 * @return returnUpdatedTime 随身物品返还维护最新修改时间
	 */
	public Date getReturnUpdatedTime() {
		return returnUpdatedTime;
	}

	public void setReturnUpdatedTime(Date returnUpdatedTime) {
		this.returnUpdatedTime = returnUpdatedTime;
	}
	
	/**
	 * @return returnModifyPeople 随身物品返还维护最新修改人
	 */
	public Person getReturnModifyPeople() {
		return returnModifyPeople;
	}

	public void setReturnModifyPeople(Person returnModifyPeople) {
		this.returnModifyPeople = returnModifyPeople;
	}
	
	

	

}
