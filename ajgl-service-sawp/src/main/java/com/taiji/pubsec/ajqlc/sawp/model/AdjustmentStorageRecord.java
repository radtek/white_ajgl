package com.taiji.pubsec.ajqlc.sawp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 调整记录
 * @author chengkai
 */
@Entity
@Table(name = "t_sawp_tzjl")
public class AdjustmentStorageRecord {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id; // id
	
	@ManyToOne
	@JoinColumn(name = "tzdx_id")
	private AdjustmentStorageFormItem formItem;	//调整单项
	
	@Column(name = "tzsl")
	private Double adjustNumber;	//调整数量
	
	@ManyToOne
	@JoinColumn(name = "cwg_id")
	private ArticleLocker locker;	// 储物架

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AdjustmentStorageFormItem getFormItem() {
		return formItem;
	}

	public void setFormItem(AdjustmentStorageFormItem formItem) {
		this.formItem = formItem;
	}

	public Double getAdjustNumber() {
		return adjustNumber;
	}

	public void setAdjustNumber(Double adjustNumber) {
		this.adjustNumber = adjustNumber;
	}

	public ArticleLocker getLocker() {
		return locker;
	}

	public void setLocker(ArticleLocker locker) {
		this.locker = locker;
	}
	
}
