package com.taiji.pubsec.ajqlc.baq.model;

import java.util.Date;
import java.util.Set;

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
 * 使用单办案民警信息
 * @author chengkai
 */
@Entity
@Table(name = "t_baq_sydbamjxx")
public class PoliceInfo {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;	// id
	
	@Column(name = "xm", length = 80)
	private String policeName;	// 姓名
	
	@Column(name = "jh", length = 80)
	private String policeNum;	// 警号
	
	@Column(name = "bz", length = 200)
	private String remark;	// 备注
	
	@Column(name = "ickh", length = 200)
	private String icNum;	// ic卡号
	
	@ManyToOne
	@JoinColumn(name = "fkr_id")
	private Person sendCardPeople;	// 发卡人
	
	@Column(name = "fksj")
	@Temporal(TemporalType.TIMESTAMP)
	private Date sendCardTime;	// 发卡时间
	
	@OneToMany(mappedBy = "policeInfo",cascade=CascadeType.ALL)
	private Set<HandlingAreaReceiptToPoliceInfo> HandlingAreaReceiptToPoliceInfoList;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPoliceName() {
		return policeName;
	}

	public void setPoliceName(String policeName) {
		this.policeName = policeName;
	}

	public String getPoliceNum() {
		return policeNum;
	}

	public void setPoliceNum(String policeNum) {
		this.policeNum = policeNum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIcNum() {
		return icNum;
	}

	public void setIcNum(String icNum) {
		this.icNum = icNum;
	}

	public Person getSendCardPeople() {
		return sendCardPeople;
	}

	public void setSendCardPeople(Person sendCardPeople) {
		this.sendCardPeople = sendCardPeople;
	}

	public Date getSendCardTime() {
		return sendCardTime;
	}

	public void setSendCardTime(Date sendCardTime) {
		this.sendCardTime = sendCardTime;
	}

	public Set<HandlingAreaReceiptToPoliceInfo> getHandlingAreaReceiptToPoliceInfoList() {
		return HandlingAreaReceiptToPoliceInfoList;
	}

	public void setHandlingAreaReceiptToPoliceInfoList(
			Set<HandlingAreaReceiptToPoliceInfo> handlingAreaReceiptToPoliceInfoList) {
		HandlingAreaReceiptToPoliceInfoList = handlingAreaReceiptToPoliceInfoList;
	}
}
