package com.taiji.pubsec.ajqlc.baq.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 物品返还情况快照记录
 * @author chengkai
 */
@Entity
@Table(name = "t_baq_wpfhkzjl")
public class GoodsSnapshot {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id; // id
	
	@ManyToOne
	@JoinColumn(name = "whsswpfhkzjl_id")
	private CarryGoodsManageSnapshot carryGoodsManageSnapshot; // 维护随身物品返还情况快照记录
	
	@Column(name = "sswprkxx_id")
	private String carryGoodsRecordId;	//随身物品入库信息id
	
	@Column(name = "czlx")
	private String operationType;	//操作类型-- 临时取出 0 &移交取出 1

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CarryGoodsManageSnapshot getCarryGoodsManageSnapshot() {
		return carryGoodsManageSnapshot;
	}

	public void setCarryGoodsManageSnapshot(CarryGoodsManageSnapshot carryGoodsManageSnapshot) {
		this.carryGoodsManageSnapshot = carryGoodsManageSnapshot;
	}

	public String getCarryGoodsRecordId() {
		return carryGoodsRecordId;
	}

	public void setCarryGoodsRecordId(String carryGoodsRecordId) {
		this.carryGoodsRecordId = carryGoodsRecordId;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	

}
