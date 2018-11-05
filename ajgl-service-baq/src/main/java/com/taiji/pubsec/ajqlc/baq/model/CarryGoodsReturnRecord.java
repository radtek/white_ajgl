package com.taiji.pubsec.ajqlc.baq.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 随身物品返回记录
 * @author wangfx
 *
 */
@Entity
@Table(name = "t_baq_sswpfhjl")
public class CarryGoodsReturnRecord {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	
	@Column(name = "wfhyy")
	private String noReturnReason;	//未返还原因
	
	
	@Column(name = "czr")
	private String operater;	//操作人
	
	@Column(name = "czsj")
	@Temporal(TemporalType.TIMESTAMP)
	private Date operateTime; 	//操作时间
	
	
	@Column(name = "lqr")
	private String receiver;	//领取人

	
	@Column(name = "lqrzjhm")
	private String receiverIdCard;	//领取人证件号码
	
	
	@Column(name = "jjsj")
	@Temporal(TemporalType.TIMESTAMP)
	private Date receiveTime;	//接收时间
	
	
	@Column(name = "fhzt")
	private String returnStatus;	//返还状态 0：返还；1：未返还
	
	
	@ManyToOne
	@JoinColumn(name = "sswprkxx_id")	
	private CarryGoodsRecord carryGoodsRecord;	//随身物品入库信息
		
	
	/**
	 * @return	id	id
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return	noReturnReason	未返还原因
	 */
	public String getNoReturnReason() {
		return noReturnReason;
	}

	public void setNoReturnReason(String noReturnReason) {
		this.noReturnReason = noReturnReason;
	}

	/**
	 * @return	operater	操作人
	 */
	public String getOperater() {
		return operater;
	}

	public void setOperater(String operater) {
		this.operater = operater;
	}

	/**
	 * @return	operateTime	操作时间
	 */
	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	/**
	 * @return	receiver	领取人
	 */
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	/**
	 * @return	receiverIdCard	领取人证件号码
	 */
	public String getReceiverIdCard() {
		return receiverIdCard;
	}

	public void setReceiverIdCard(String receiverIdCard) {
		this.receiverIdCard = receiverIdCard;
	}

	/**
	 * @return	receiveTime	接收时间
	 */
	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	/**
	 * @return	returnStatus	返还状态 0：返还；1：未返还
	 */
	public String getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}

	/**
	 * @return	carryGoodsRecord	随身物品入库信息
	 */
	public CarryGoodsRecord getCarryGoodsRecord() {
		return carryGoodsRecord;
	}

	public void setCarryGoodsRecord(CarryGoodsRecord carryGoodsRecord) {
		this.carryGoodsRecord = carryGoodsRecord;
	}

	
}
