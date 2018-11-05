package com.taiji.pubsec.ajqlc.sawp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 出库单项
 * @author chengkai
 */
@Entity
@Table(name = "t_sawp_ckdx")
public class OutStorageFormItem {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;	// id
	
	@ManyToOne
	@JoinColumn(name = "sawp_id")
	private InvolvedArticle article;	// 涉案物品
	
	@ManyToOne
	@JoinColumn(name = "ckd_id")
	private OutStorageForm form;	// 出库单
	
	@ManyToOne
	@JoinColumn(name = "cwg_id")
	private ArticleLocker locker;	// 储物架
	
	@Column(name = "cksl")
	private Double outcomingNumber;	// 已出库数量
	
	@Column(name = "zksl")
	private Double existingNumber;	//可出库数量，等于生成出库单时在库数量
	
	@Column(name = "bz")
	private String remark;	// 备注
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gxsj")
	private Date updatedTime;	// 更新时间
	
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
	 * @return form 出库单
	 */
	public OutStorageForm getForm() {
		return form;
	}

	public void setForm(OutStorageForm form) {
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
	
	/**
	 * @return locker 储物架
	 */
	public ArticleLocker getLocker() {
		return locker;
	}

	public void setLocker(ArticleLocker locker) {
		this.locker = locker;
	}
	
	public Double getOutcomingNumber() {
		return outcomingNumber;
	}

	public void setOutcomingNumber(Double outcomingNumber) {
		this.outcomingNumber = outcomingNumber;
	}

	public Double getExistingNumber() {
		return existingNumber;
	}

	public void setExistingNumber(Double existingNumber) {
		this.existingNumber = existingNumber;
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
		OutStorageFormItem other = (OutStorageFormItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}