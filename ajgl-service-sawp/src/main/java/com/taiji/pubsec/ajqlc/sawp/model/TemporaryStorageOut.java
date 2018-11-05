package com.taiji.pubsec.ajqlc.sawp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.taiji.pubsec.businesscomponent.organization.model.Person;

/**
 * 暂存出库单
 * @author xinfan
 *
 */
@Entity
@Table(name = "t_sawp_zcckd")
public class TemporaryStorageOut {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id; 
	
	@OneToOne
	@JoinColumn(name = "zcrkd_id")
	private TemporaryStorageIn storageIn;	// 暂存入库单id

	
	@Column(name = "bm", nullable = false)
	private String code;	// 编码
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cksj", nullable = false)
	private Date createDate;	// 出库单创建时间
	
	@ManyToOne
	@JoinColumn(name = "cjr_id", nullable = false)
	private Person createPerson;//创建人
	
	@ManyToOne
	@JoinColumn(name = "xgr_id", nullable = false)
	private Person modifyPerson;//最新修改人
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "xgsj", nullable = false)
	private Date modifyTimeDate;	// 出库单修改时间
	
	@Column(name = "bz" , length=255)
	private String remark;	//备注
	
	@Column(name = "lqr" )
	private String receivePerson;	//领取人
	
	
	@Column(name = "zt" )
	private String status;	//状态 字典项是否的id


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public TemporaryStorageIn getStorageIn() {
		return storageIn;
	}


	public void setStorageIn(TemporaryStorageIn storageIn) {
		this.storageIn = storageIn;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReceivePerson() {
		return receivePerson;
	}


	public void setReceivePerson(String receivePerson) {
		this.receivePerson = receivePerson;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Person getCreatePerson() {
		return createPerson;
	}


	public void setCreatePerson(Person createPerson) {
		this.createPerson = createPerson;
	}


	public Person getModifyPerson() {
		return modifyPerson;
	}


	public void setModifyPerson(Person modifyPerson) {
		this.modifyPerson = modifyPerson;
	}


	public Date getModifyTimeDate() {
		return modifyTimeDate;
	}


	public void setModifyTimeDate(Date modifyTimeDate) {
		this.modifyTimeDate = modifyTimeDate;
	}

}
