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
 * 返还记录
 * @author chengkai
 */
@Entity
@Table(name = "t_sawp_fhjl")
public class BackStorageRecord {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	@Column(name = "fhsl")
	private Double returnedNumber;	//返还数量
	
	@ManyToOne
	@JoinColumn(name = "cwg_id")
	private ArticleLocker locker;	// 储物架
	
	@ManyToOne
	@JoinColumn(name = "form_id")
	private BackStorageFormItem formItem;	//返还单项

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getReturnedNumber() {
		return returnedNumber;
	}

	public void setReturnedNumber(Double returnedNumber) {
		this.returnedNumber = returnedNumber;
	}

	public ArticleLocker getLocker() {
		return locker;
	}

	public void setLocker(ArticleLocker locker) {
		this.locker = locker;
	}

	public BackStorageFormItem getFormItem() {
		return formItem;
	}

	public void setFormItem(BackStorageFormItem formItem) {
		this.formItem = formItem;
	}
}
