package com.taiji.pubsec.ajqlc.sawp.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * 调整单
 * @author chengkai
 */
@Entity
@Table(name = "t_sawp_tzd")
public class AdjustgmentStorageForm extends CommonForm {

	@Column(name = "tzyy")
	private String reason; // 调整原因
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "tzsj")
	private Date adjustTime;	// 调整时间
	
	@OneToMany(mappedBy="form")
	private Set<AdjustmentStorageFormItem> adjustmentStorageFormItems = new HashSet<AdjustmentStorageFormItem>();	// 调整单项
	
	/**
	 * @return reason 调整原因
	 */
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	/**
	 * @return adjustmentStorageFormItems 调整单项
	 */
	public Set<AdjustmentStorageFormItem> getAdjustmentStorageFormItems() {
		return adjustmentStorageFormItems;
	}

	public void setAdjustmentStorageFormItems(Set<AdjustmentStorageFormItem> adjustmentStorageFormItems) {
		this.adjustmentStorageFormItems = adjustmentStorageFormItems;
	}

	public Date getAdjustTime() {
		return adjustTime;
	}

	public void setAdjustTime(Date adjustTime) {
		this.adjustTime = adjustTime;
	}
	
}