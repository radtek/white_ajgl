package com.taiji.pubsec.ajqlc.alert.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 预警规则
 * @author XIEHF
 */
@Entity
@Table(name = "t_baq_yjgz")
public class AlertRule {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;	// id
	
	@Column(name = "ddyjsj", length = 200)
	private String alertTimeAt;	// 定点预警时间
	
	@Column(name = "tqyjsj")
	private Integer alertTimeBefore;	// 提前预警时间
	
	@Column(name = "ssmk")
	private String theirModulu;		//所属模块
	
	@Column(name = "code", length = 200)
	private String code;	// 预警规则编码
	
	@Column(name = "ms", length = 200)
	private String description;	// 预警条件描述
	
	@Column(name = "gzmc", length = 200)
	private String name;	// 预警规则名称
	
	@Column(name = "tcbcsj")
	private Integer popWindowDuaring;	// 弹窗保持时间
	
	@Column(name = "yjfs", length = 80)
	private String way;	// 预警方式，字典项id
	
	@Column(name = "cffs", length = 80)
	private String trigger;	// 触发方式，字典项id
	
	@Column(name = "zt", length = 80)
	private String status;	// 预警规则状态。
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdTime")
	private Date createdTime;	//创建时间
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateTime")
	private Date updateTime;	//更新时间
	
	@OneToMany(mappedBy = "alertRule",cascade = CascadeType.ALL)
	private List<AlertMessage> alertMessages = new ArrayList<AlertMessage>();	//预警消息
	
	@Column(name = "yjyh")
	private String alertUsers;	//要预警的用户id，多个用逗号隔开
	
	@Column(name = "yjjs")
	private String alertRoles;	//要预警的角色id，多个用逗号隔开

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
	 *  @return	创建时间
	 */
	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	/**
	 *  @return	定点预警时间
	 */
	public String getAlertTimeAt() {
		return alertTimeAt;
	}

	public void setAlertTimeAt(String alertTimeAt) {
		this.alertTimeAt = alertTimeAt;
	}

	/**
	 *  @return	提前预警时间
	 */
	public Integer getAlertTimeBefore() {
		return alertTimeBefore;
	}

	public void setAlertTimeBefore(Integer alertTimeBefore) {
		this.alertTimeBefore = alertTimeBefore;
	}

	/**
	 *  @return	预警规则编码
	 */
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 *  @return	预警条件描述
	 */
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 *  @return	预警规则名称
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 *  @return	弹窗保持时间
	 */
	public Integer getPopWindowDuaring() {
		return popWindowDuaring;
	}

	public void setPopWindowDuaring(Integer popWindowDuaring) {
		this.popWindowDuaring = popWindowDuaring;
	}

	/**
	 *  @return	预警方式，字典项id
	 */
	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}

	/**
	 *  @return	更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 *  @return	关联预警消息
	 */
	public List<AlertMessage> getAlertMessages() {
		return alertMessages;
	}

	public void setAlertMessages(List<AlertMessage> alertMessages) {
		this.alertMessages = alertMessages;
	}

	/**
	 *  @return	预警规则状态
	 */
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTrigger() {
		return trigger;
	}

	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}
	
	/**
	 * 
	 * @return	所属模块
	 */
	public String getTheirModulu() {
		return theirModulu;
	}

	public void setTheirModulu(String theirModulu) {
		this.theirModulu = theirModulu;
	}

	public String getAlertUsers() {
		return alertUsers;
	}

	public void setAlertUsers(String alertUsers) {
		this.alertUsers = alertUsers;
	}

	public String getAlertRoles() {
		return alertRoles;
	}

	public void setAlertRoles(String alertRoles) {
		this.alertRoles = alertRoles;
	}
	
	
	
}