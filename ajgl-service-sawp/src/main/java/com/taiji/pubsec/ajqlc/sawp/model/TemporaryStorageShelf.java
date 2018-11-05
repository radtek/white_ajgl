package com.taiji.pubsec.ajqlc.sawp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * 保存物品柜
 * @author xinfan
 *
 */
@Entity
@Table(name = "t_sawp_zcwpg")
public class TemporaryStorageShelf {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	@Column(name = "xxwz")
	private String address;	//详细位置
	
	@Column(name = "bm")
	private String code;	//编码
	
	@Column(name = "bz")
	private String remark;	//备注
	
	@ManyToOne
	@JoinColumn(name = "area_id")
	private TemporaryStorageArea area;	//保存区
	
	@Column(name = "zt")
	private String status;//储物柜状态  【001:在用 、002:空闲 、003:停用】
	
	@OneToMany(mappedBy = "bgg")
	private List<StoragePosition> storagePosition = new ArrayList<StoragePosition>();
	
	

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public TemporaryStorageArea getArea() {
		return area;
	}

	public void setArea(TemporaryStorageArea area) {
		this.area = area;
	}

	public List<StoragePosition> getStoragePosition() {
		return storagePosition;
	}

	public void setStoragePosition(List<StoragePosition> storagePosition) {
		this.storagePosition = storagePosition;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
