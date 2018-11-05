package com.taiji.pubsec.ajqlc.sawp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.taiji.pubsec.businesscomponent.organization.model.Organization;
/**
 * 暂存物品区
 * @author xinfan
 *
 */
@Entity
@Table(name = "t_sawp_zcwpq")
public class TemporaryStorageArea {
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
	private String status;	//状态，字典项id
	
	@ManyToOne
	@JoinColumn(name = "org_id")
	private Organization org;	//单位

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}

}
