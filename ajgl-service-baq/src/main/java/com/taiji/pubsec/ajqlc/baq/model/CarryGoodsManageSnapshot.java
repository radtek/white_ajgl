package com.taiji.pubsec.ajqlc.baq.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 维护随身物品返还情况快照记录
 * @author chengkai
 */
@Entity
@Table(name = "t_baq_whsswpfhkzjl")
public class CarryGoodsManageSnapshot {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id; // id
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "kzsj")
	private Date snapshotTime;	//快照时间
	
	@Column(name = "baqsyd_id")
	private String HandlingAreaReceiptId;	//办案区使用单id
	
	@OneToMany(mappedBy = "carryGoodsManageSnapshot")
	private Set<GoodsSnapshot> goodsSnapshots = new HashSet<GoodsSnapshot>();	//	物品返还情况快照记录

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getSnapshotTime() {
		return snapshotTime;
	}

	public void setSnapshotTime(Date snapshotTime) {
		this.snapshotTime = snapshotTime;
	}

	public String getHandlingAreaReceiptId() {
		return HandlingAreaReceiptId;
	}

	public void setHandlingAreaReceiptId(String handlingAreaReceiptId) {
		HandlingAreaReceiptId = handlingAreaReceiptId;
	}

	public Set<GoodsSnapshot> getGoodsSnapshots() {
		return goodsSnapshots;
	}

	public void setGoodsSnapshots(Set<GoodsSnapshot> goodsSnapshots) {
		this.goodsSnapshots = goodsSnapshots;
	}
	
}
