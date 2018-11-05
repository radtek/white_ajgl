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
 * 临时离开信息
 * @author wangfx
 *
 */
@Entity
@Table(name = "t_baq_lslkxx")
public class TmpLeaveInfo {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	
	@Column(name = "babmfzr")
	private String handleCauseDepartmentLeader;	//办案部门负责人
	
	@Column(name = "lkyy")
	private String leaveCause;	//离开原因
	
	@Column(name = "cjsj")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;	// 创建时间
	
	@Column(name = "zxxgsj")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedTime;	// 最新修改时间
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lksj")
	private Date leaveTime;	//离开时间
	
	@ManyToOne
	@JoinColumn(name = "wxs_id")
	private ActivityRoom askRoom; // 离开前使用的问询室
	
	
	@Column(name = "whr")
	private String maintainer;	//维护人
	
	@Column(name = "whsj")
	private Date maintainTime;	//维护时间
	
	@Column(name = "fhsj")
	private Date returnTime;	//返回时间
	
	@ManyToOne
	@JoinColumn(name ="zzlkxx_id")
	private FinallyLeaveInfo finallyLeaveInfo;	//最终离开信息
	
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
	 * @return	handleCauseDepartmentLeader	办案部门负责人
	 */
	public String getHandleCauseDepartmentLeader() {
		return handleCauseDepartmentLeader;
	}

	public void setHandleCauseDepartmentLeader(String handleCauseDepartmentLeader) {
		this.handleCauseDepartmentLeader = handleCauseDepartmentLeader;
	}
	
	
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
	 * @return	leaveCause	离开原因
	 */
	public String getLeaveCause() {
		return leaveCause;
	}

	public void setLeaveCause(String leaveCause) {
		this.leaveCause = leaveCause;
	}

	/**
	 * @return	leaveTime	离开时间
	 */
	public Date getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}

	/**
	 * @return askRoom 离开前使用的问询室
	 */
	public ActivityRoom getAskRoom() {
		return askRoom;
	}

	public void setAskRoom(ActivityRoom askRoom) {
		this.askRoom = askRoom;
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
	 * @return	returnTime	返回时间
	 */
	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	/**
	 * @return	finallyLeaveInfo	最终离开信息
	 */
	public FinallyLeaveInfo getFinallyLeaveInfo() {
		return finallyLeaveInfo;
	}

	public void setFinallyLeaveInfo(FinallyLeaveInfo finallyLeaveInfo) {
		this.finallyLeaveInfo = finallyLeaveInfo;
	}
	
	/**
	 * 
	 * @return createdTime 创建时间
	 */
	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	
}
