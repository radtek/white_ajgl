package com.taiji.pubsec.ajqlc.baq.model;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.MappedSuperclass;

import com.taiji.pubsec.businesscomponent.organization.model.Unit;

import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * ActivityRoom与Locker相同的字段和get/set方法
 * @author chengkai
 *
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class CommonFileds {
	
	@ManyToOne
	@JoinColumn(name = "dw_id")
	private Unit unit;	// 单位
	
	@Column(name = "bz", length = 200)
	private String note;	// 备注
	
	@Column(name = "bm", nullable = false, length = 80)
	private String code;	// 编码
	
	@Column(name = "zt", nullable = false, length = 20)
	private String status;	// 状态，0:空闲；1:使用中；2:不可用
	
	/**
	 * @return unit 单位
	 */
	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	/**
	 * @return note 备注
	 */
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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
	 * @return status 状态，0:空闲；1:使用中；2:不可用
	 */
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
