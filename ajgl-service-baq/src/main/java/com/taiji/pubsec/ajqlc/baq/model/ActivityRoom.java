package com.taiji.pubsec.ajqlc.baq.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 问询室
 * @author chengkai
 */
@Entity
@Table(name = "t_baq_wxs")
public class ActivityRoom extends CommonFileds {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;	// id
	
	@Column(name = "jksph", length = 80)
	private String monitorNum;	// 监控视频号
	
	@Column(name = "mc", nullable = false, length = 80)
	private String name;	// 名称
	
	@Column(name = "lx", nullable = false, length = 80)
	private String type;	// 类型
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatedTime")
	private Date updatedTime;	//最新修改时间
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdTime")
	private Date createdTime;	//最新修改时间
	
	@Column(name = "dhfjid", length = 80)
	private String dahuaRoomId;	//大华系统的房间id

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
	 * @return monitorNum 监控视频号
	 */
	public String getMonitorNum() {
		return monitorNum;
	}
	
	public void setMonitorNum(String monitorNum) {
		this.monitorNum = monitorNum;
	}
	
	/**
	 * @return name 名称
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return type 类型
	 */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getDahuaRoomId() {
		return dahuaRoomId;
	}

	public void setDahuaRoomId(String dahuaRoomId) {
		this.dahuaRoomId = dahuaRoomId;
	}
	
	
}