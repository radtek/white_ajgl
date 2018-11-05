package com.taiji.pubsec.ajqlc.sawp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.taiji.pubsec.businesscomponent.organization.model.Person;

/**
 * 暂存入库单
 * @author xinfan
 *
 */
@Entity
@Table(name = "t_sawp_zcrkd")
public class TemporaryStorageIn {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id; 
	
	@Column(name = "sydbh", nullable = false)
	private String harCode;	// 使用单编号

	
	@Column(name = "bm", nullable = false)
	private String code;	// 编码
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "rksj", nullable = false)
	private Date createDate;	// 入库单创建时间
	
	
	@ManyToOne
	@JoinColumn(name = "cjr_id", nullable = false)
	private Person createPerson; //创建人
	
	@Column(name = "bz" , length=255)
	private String remark;	//备注
	
	@ManyToOne
	@JoinColumn(name = "czr_id")
	private Person modifyPerson;	//操作民警
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "czsj", nullable = false)
	private Date modifyTime;	// 操作时间
	
	@OneToMany(mappedBy = "rkd", cascade={CascadeType.MERGE})
	private List<StoragePosition> storagePosition = new ArrayList<StoragePosition>();


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getHarCode() {
		return harCode;
	}


	public void setHarCode(String harCode) {
		this.harCode = harCode;
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


	public Person getModifyPerson() {
		return modifyPerson;
	}


	public void setModifyPerson(Person modifyPerson) {
		this.modifyPerson = modifyPerson;
	}


	public Date getModifyTime() {
		return modifyTime;
	}


	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}


	public List<StoragePosition> getStoragePosition() {
		return storagePosition;
	}


	public void setStoragePosition(List<StoragePosition> storagePosition) {
		this.storagePosition = storagePosition;
	}


	public Person getCreatePerson() {
		return createPerson;
	}


	public void setCreatePerson(Person createPerson) {
		this.createPerson = createPerson;
	}
	
	
}
