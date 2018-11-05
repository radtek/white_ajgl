package com.taiji.pubsec.ajqlc.sawp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;

import com.taiji.pubsec.businesscomponent.organization.model.Unit;

/**
 * 保管区
 * @author wangfx
 *
 */
@Entity
@Table(name = "t_sawp_bgq")
public class StorageArea {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	@Column(name = "xxwz")
	private String address;	//详细位置
	
	@Column(name = "bm")
	private String code;	//编码
	
	@Column(name = "mc")
	private String name;	//名称
	
	@Column(name = "bz")
	private String remark;	//备注
	
	@Column(name = "zt", length = 36)
	private String state;	//状态，字典项id
	
	@ManyToOne
	@JoinColumn(name = "dw_id")
	private Unit unit;	//单位

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
	 * @return address 详细位置
	 */
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
	 * @return name 名称
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return remark 备注
	 */
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return state 状态，字典项id
	 */
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return unit 单位
	 */
	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
