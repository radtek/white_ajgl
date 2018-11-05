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
 * 入库操作
 * @author wangfx
 *
 */
@Entity
@Table(name = "t_sawp_rkcz")
public class InStorageRecord {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	@Column(name = "rksl")
	private Double incomingNumber;	//入库数量
	
	@ManyToOne
	@JoinColumn(name = "cwg_id")
	private ArticleLocker locker;	// 储物架
	
	@ManyToOne
	@JoinColumn(name = "form_id")
	private InStorageFormItem formItem;

	public String getId() {
		return id;
	}

	public ArticleLocker getLocker() {
		return locker;
	}

	public InStorageFormItem getFormItem() {
		return formItem;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLocker(ArticleLocker locker) {
		this.locker = locker;
	}

	public void setFormItem(InStorageFormItem formItem) {
		this.formItem = formItem;
	}

	public Double getIncomingNumber() {
		return incomingNumber;
	}

	public void setIncomingNumber(Double incomingNumber) {
		this.incomingNumber = incomingNumber;
	}

}
