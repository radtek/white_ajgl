package com.taiji.pubsec.ajqlc.sawp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.taiji.pubsec.businesscomponent.organization.model.Person;

/**
 * 单据公共内容
 * @author chengkai
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class CommonForm {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;	// id
	
	@Column(name = "bm", nullable = false)
	private String code;	// 编码
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cjsj", nullable = false)
	private Date createdTime;	// 创建时间
	
	@ManyToOne
	@JoinColumn(name = "czr")
	private Person modifyPeople;	// 最新修改人
	
	@Column(name = "bz")
	private String remark;	// 备注
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gxsj")
	private Date updatedTime;	// 更新时间	
	
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
	 * @return code 编码
	 */
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
	 * @return modifyPeople 最新修改人
	 */
	public Person getModifyPeople() {
		return modifyPeople;
	}

	public void setModifyPeople(Person modifyPeople) {
		this.modifyPeople = modifyPeople;
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
	 * @return updatedTime 更新时间
	 */
	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	
}