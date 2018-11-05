package com.taiji.pubsec.ajqlc.sawp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.taiji.pubsec.ajqlc.sawp.service.IBackStorageFormService;
import com.taiji.pubsec.common.tool.spring.SpringContextUtil;

/**
 * 返还单项
 * 
 * @author chengkai
 */
@Entity
@Table(name = "t_sawp_fhdx")
public class BackStorageFormItem {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id; // id

	@ManyToOne
	@JoinColumn(name = "fhd_id")
	private BackStorageForm form; // 返还单
	
	@Column(name = "yqfhsl")
	private Double numberRequested;	//可返还数量，等于出库单项中的已出库数量
	
	@Column(name = "fhsl")
	private Double returnedNumber;	//已返还数量
	
	@OneToOne
	@JoinColumn(name = "ckdx_id")
	private OutStorageFormItem outItem;	// 出库单项
	
	@Column(name = "sawp_id")
	private String articleId;	// 涉案物品id

	@Column(name = "bz")
	private String remark; // 备注
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "whsj")
	private Date maintainTime; // 更新时间
	
	@OneToMany(mappedBy = "formItem")
	private List<BackStorageRecord> backStorageRecords = new ArrayList<BackStorageRecord>();
	
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
	 * @return form 返还单
	 */
	public BackStorageForm getForm() {
		return form;
	}

	public void setForm(BackStorageForm form) {
		this.form = form;
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
	 * @return outItem 出库单项
	 */
	public OutStorageFormItem getOutItem() {
		return outItem;
	}

	public void setOutItem(OutStorageFormItem outItem) {
		this.outItem = outItem;
	}
	
	/**
	 * @return maintainTime 更新时间
	 */
	public Date getMaintainTime() {
		return maintainTime;
	}

	/**
	 * @return numberRequested 要求返还数量，等于出库单项中的已出库数量
	 */
	public Double getNumberRequested() {
		return numberRequested;
	}

	public void setNumberRequested(Double numberRequested) {
		this.numberRequested = numberRequested;
	}

	public void setMaintainTime(Date maintainTime) {
		this.maintainTime = maintainTime;
	}

	/**
	 * @return articleId 涉案物品id
	 */
	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	/**
	 * @return returnedNumber 已返还数量
	 */
	public Double getReturnedNumber() {
		return returnedNumber;
	}

	public void setReturnedNumber(Double returnedNumber) {
		this.returnedNumber = returnedNumber;
	}

	public List<BackStorageRecord> getBackStorageRecords() {
		return backStorageRecords;
	}

	public void setBackStorageRecords(List<BackStorageRecord> backStorageRecords) {
		this.backStorageRecords = backStorageRecords;
	}

	/**
	 * 是否全部返换，查找对应的出库单项对应的所有返回单项返回数量和是否等于出库单项的出库数量。
	 */
	public boolean isReturnedAll() {
		IBackStorageFormService backStorageFormService = (IBackStorageFormService) SpringContextUtil.getBean("backStorageFormService");
		List<BackStorageFormItem> bsfiList = backStorageFormService.findBackStorageFormItemsByOutStorageFormItemId(outItem.getId());
		double backCount = 0;
		for(BackStorageFormItem backStorageFormItem: bsfiList){
			backCount += backStorageFormItem.getReturnedNumber();
		}
		if(backCount == outItem.getOutcomingNumber()){
			return true;
		}
		return false;
	}
	
}