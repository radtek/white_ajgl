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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 入库单项
 * @author chengkai
 */
@Entity
@Table(name = "t_sawp_rkdx")
public class InStorageFormItem {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id; // id
	
	@Column(name = "yqrksl")
	private Double numberRequested;	//要求入库数量
	
	@ManyToOne
	@JoinColumn(name = "rkd_id")
	private InStorageForm form; // 入库单
	
	@ManyToOne
	@JoinColumn(name = "sawp_id")
	private InvolvedArticle article;	// 涉案物品

	@Column(name = "yrksl")
	private Double numberIncome; // 已入库数量
	
	@Column(name = "bz")
	private String remark; // 备注

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gxsj")
	private Date updatedTime; // 更新时间
	
	
	@OneToMany(mappedBy = "formItem")
	private List<InStorageRecord> inStorageRecords = new ArrayList<>();
	
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
	 * @return form 入库单
	 */
	public InStorageForm getForm() {
		return form;
	}

	public void setForm(InStorageForm form) {
		this.form = form;
	}
	
	
	/**
	 * @return remark 备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @return numberIncome 入库数量
	 */
	
	public Double getNumberIncome() {
		Double num = 0D;
		for (InStorageRecord ir : inStorageRecords) {
			num += ir.getIncomingNumber();
		}
		return num;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * @return updatedTime 更新时间
	 */
	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
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
	
	public List<InStorageRecord> getInStorageRecords() {
		return inStorageRecords;
	}

	public void setInStorageRecords(List<InStorageRecord> inStorageRecords) {
		this.inStorageRecords = inStorageRecords;
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
		InStorageFormItem other = (InStorageFormItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Double getNumberRequested() {
		return numberRequested;
	}

	public void setNumberRequested(Double numberRequested) {
		this.numberRequested = numberRequested;
	}

	public void setNumberIncome(Double numberIncome) {
		this.numberIncome = numberIncome;
	}

}