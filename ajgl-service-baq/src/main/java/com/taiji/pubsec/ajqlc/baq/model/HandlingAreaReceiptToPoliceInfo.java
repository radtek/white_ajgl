package com.taiji.pubsec.ajqlc.baq.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_baq_syd_bamjxx")
public class HandlingAreaReceiptToPoliceInfo {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;	// id
	
	@ManyToOne
	@JoinColumn(name = "syd_id")
	private HandlingAreaReceipt handlingAreaReceipt;	//办案区使用单
	
	@ManyToOne
	@JoinColumn(name = "bamjxx_id")
	private PoliceInfo policeInfo;	//办案民警信息
	
	@Column(name = "sfzbmj", length = 80)
	private String ifMainPolice;	// 是否主办民警

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIfMainPolice() {
		return ifMainPolice;
	}

	public void setIfMainPolice(String ifMainPolice) {
		this.ifMainPolice = ifMainPolice;
	}

	public HandlingAreaReceipt getHandlingAreaReceipt() {
		return handlingAreaReceipt;
	}

	public void setHandlingAreaReceipt(HandlingAreaReceipt handlingAreaReceipt) {
		this.handlingAreaReceipt = handlingAreaReceipt;
	}

	public PoliceInfo getPoliceInfo() {
		return policeInfo;
	}

	public void setPoliceInfo(PoliceInfo policeInfo) {
		this.policeInfo = policeInfo;
	}
}
