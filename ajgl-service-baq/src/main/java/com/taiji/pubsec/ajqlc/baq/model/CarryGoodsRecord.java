package com.taiji.pubsec.ajqlc.baq.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 随身物品入库信息
 * @author chengkai
 */
@Entity
@Table(name = "t_baq_sswprkxx")
public class CarryGoodsRecord {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;	// id
	
	@Column(name = "wpmc", nullable = false, length = 600)
	private String goodsName;	// 物品名称
	
	@Column(name = "tz", nullable = false, length = 600)
	private String features;	// 特征

	@Column(name = "bh", nullable = false, length = 80)
	private String num;	// 编号
	
	@Column(name = "sl", nullable = false, length = 80)
	private String quantity;	// 数量
	
	@Column(name = "ccwz", nullable = true, length = 80)
	private String position;	// 存储位置
	
	@Column(name = "zt", nullable = false, length = 80)
	private String status;	// 状态：在库、离库
	
	@Column(name = "bgcs", length = 200)
	private String takeCareType;	// 保管措施 （弃用）
	
	@Column(name = "jldw", nullable = false, length = 80)
	private String unitOfMeasurement;	// 计量单位

	//随身物品信息
	@ManyToOne
	@JoinColumn(name = "sswpxx_id")
	private CarryGoodsInfo carryGoodsInfo;
	
	@ManyToOne
	@JoinColumn(name = "cwg_id", nullable = true)
	private Locker locker;	//储物柜  
	
	@Column(name = "bz")
	private String remark;	//备注
	
	@OrderBy(value = "operateTime")
	@OneToMany(mappedBy = "carryGoodsRecord", cascade = CascadeType.ALL)
	private List<CarryGoodsReturnRecord> carryGoodsReturnRecordList = new ArrayList<CarryGoodsReturnRecord>();
	
	public CarryGoodsInfo getCarryGoodsInfo() {
		return carryGoodsInfo;
	}

	public void setCarryGoodsInfo(CarryGoodsInfo carryGoodsInfo) {
		this.carryGoodsInfo = carryGoodsInfo;
	}

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
	 * @return goodsNameAndFeatures 物品名称以及特征
	 */
	
	
	/**
	 * @return num 编号
	 */
	public String getNum() {
		return num;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}


	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	/**
	 * @return quantity 数量
	 */
	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * @return status 状态：在库、离库
	 */
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * @return takeCareType 保管措施
	 */
	public String getTakeCareType() {
		return takeCareType;
	}

	public void setTakeCareType(String takeCareType) {
		this.takeCareType = takeCareType;
	}
	
	/**
	 * @return unitOfMeasurement 计量单位
	 */
	public String getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(String unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}
	
	
	/**
	 * @return locker 储物柜
	 */
	public Locker getLocker() {
		return locker;
	}

	public void setLocker(Locker locker) {
		this.locker = locker;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CarryGoodsRecord other = (CarryGoodsRecord) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public List<CarryGoodsReturnRecord> getCarryGoodsReturnRecordList() {
		return carryGoodsReturnRecordList;
	}

	public void setCarryGoodsReturnRecordList(List<CarryGoodsReturnRecord> carryGoodsReturnRecordList) {
		this.carryGoodsReturnRecordList = carryGoodsReturnRecordList;
	}
	
}