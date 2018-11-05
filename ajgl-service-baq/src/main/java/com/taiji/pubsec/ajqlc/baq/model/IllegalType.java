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
 * 违规类型
 * @author XIEHF
 */
@Entity
@Table(name = "t_baq_wglx")
public class IllegalType {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;	// id
	
	@Column(name="mc", length=100, nullable=false)
	private String name;

	@Column(name="bm", length=80, nullable=false)
	private String code;

	@Column(name="zt", length=80, nullable=false)
	private String status;

	@Column(name="bz", length=250)
	private String description;
	
	@Column(name="czr", length=80, nullable=false)
	private String operator;
	
	@Column(name="sfys", length=250)
	private String isSystemData;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatedTime")
	private Date updatedTime;	//最新修改时间
	

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
	 * @return name 名称
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	/**
	 * @return code 编码
	 */
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	/**
	 * @return state 状态
	 */
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return description 备注
	 */
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return isSystemData 是否系统预设数据
	 */
	public String getIsSystemData() {
		return isSystemData;
	}

	public void setIsSystemData(String isSystemData) {
		this.isSystemData = isSystemData;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	
}