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
 * 调整单项
 * @author chengkai
 */
@Entity
@Table(name = "t_sawp_tzdx")
public class AdjustmentStorageFormItem {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id; // id
	
	@ManyToOne
	@JoinColumn(name = "tzd_id")
	private AdjustgmentStorageForm form;	// 调整单
	
	@ManyToOne
	@JoinColumn(name = "sawp_id")
	private InvolvedArticle article;	// 涉案物品
	
	@Column(name = "tzsl")
	private Double adjustmentNumber;	// 涉案物品调整前的在库数量
	
	@Column(name = "bgwzId")
	private String storageId;	//原保管位置id
	
	@Column(name = "bz")
	private String remark;	// 备注
	
	@OneToMany(mappedBy = "formItem")
	private List<AdjustmentStorageRecord> adjustmentStorageRecords = new ArrayList<AdjustmentStorageRecord>();
	
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
	 * @return form 调整单
	 */
	public AdjustgmentStorageForm getForm() {
		return form;
	}

	public void setForm(AdjustgmentStorageForm form) {
		this.form = form;
	}
	
	/**
	 * @return storageId 原保管位置id
	 */
	public String getStorageId() {
		return storageId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}

	/**
	 * @return remark 备注 
	 */
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * @return article 涉案物品
	 */
	public InvolvedArticle getArticle() {
		return article;
	}

	public void setArticle(InvolvedArticle article) {
		this.article = article;
	}
	
	public List<AdjustmentStorageRecord> getAdjustmentStorageRecords() {
		return adjustmentStorageRecords;
	}

	public Double getAdjustmentNumber() {
		return adjustmentNumber;
	}

	/**
	 * @return adjustmentNumber 调整数量
	 */
	public void setAdjustmentNumber(Double adjustmentNumber) {
		this.adjustmentNumber = adjustmentNumber;
	}

	public void setAdjustmentStorageRecords(List<AdjustmentStorageRecord> adjustmentStorageRecords) {
		this.adjustmentStorageRecords = adjustmentStorageRecords;
	}

	

}