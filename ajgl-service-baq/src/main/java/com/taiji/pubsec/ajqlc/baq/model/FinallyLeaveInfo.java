package com.taiji.pubsec.ajqlc.baq.model;

import java.util.ArrayList;
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
 * 最终离开信息
 * @author wangfx
 *
 */
@Entity
@Table(name = "t_baq_zzlkxx")
public class FinallyLeaveInfo {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	@Column(name = "zzlkyy")
	private String finallyLeaveCause;	//最终离开原因
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "zzlksj")
	private Date finallyLeaveTime;	//最终离开时间
	
	@OneToOne
	@JoinColumn(name = "wxs_id")
	private ActivityRoom askRoom;	// 最终离开前使用的问询室
	
	@ManyToOne
	@JoinColumn(name = "zxxgr_id")
	private Person modifyPeople;	// 最新修改人
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "zxxgsj")
	private Date updatedTime;	//最新修改时间
	
	@OneToOne
	@JoinColumn(name = "baqsyd_id")
	private HandlingAreaReceipt handlingAreaReceipt;	//办案区使用单
	
	@OrderBy(value = "leaveTime")
	@OneToMany(mappedBy = "finallyLeaveInfo")
	private List<TmpLeaveInfo> tmpLeaveInfos = new ArrayList<TmpLeaveInfo>();	//临时离开信息列表
	
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
	 * @return	finallyLeaveCause	最终离开原因
	 */
	public String getFinallyLeaveCause() {
		return finallyLeaveCause;
	}

	public void setFinallyLeaveCause(String finallyLeaveCause) {
		this.finallyLeaveCause = finallyLeaveCause;
	}

	/**
	 * @return	finallyLeaveTime	最终离开时间
	 */
	public Date getFinallyLeaveTime() {
		return finallyLeaveTime;
	}

	public void setFinallyLeaveTime(Date finallyLeaveTime) {
		this.finallyLeaveTime = finallyLeaveTime;
	}

	
	/**
	 * @return askRoom 最终离开前使用的问询室
	 */
	public ActivityRoom getAskRoom() {
		return askRoom;
	}

	public void setAskRoom(ActivityRoom askRoom) {
		this.askRoom = askRoom;
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
	 * @return	tmpLeaveInfos	临时离开信息列表
	 */	
	public List<TmpLeaveInfo> getTmpLeaveInfos() {
		return tmpLeaveInfos;
	}

	public void setTmpLeaveInfos(List<TmpLeaveInfo> tmpLeaveInfos) {
		this.tmpLeaveInfos = tmpLeaveInfos;
	}
	
	

}
