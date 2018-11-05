package com.taiji.pubsec.ajqlc.sawp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;

/**
 * 保管位置
 * @author wangfx
 *
 */
@Entity
@Table(name = "t_sawp_bgwz")
public class Storage {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;	//id

	@Column(name = "zksl")
	private Double existingNumber;	// 在库数量
	
	@Column(name = "bcsl")
	private Double incomingNumber;	//入库数量
	
	@ManyToOne
	@JoinColumn(name = "sawp_id")
	private InvolvedArticle article;	//涉案物品
	
	@ManyToOne
	@JoinColumn(name = "cwg_id")
	private ArticleLocker locker;	//储物柜

	/**
	 * @return id id
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getExistingNumber() {
		return existingNumber;
	}

	public void setExistingNumber(Double existingNumber) {
		this.existingNumber = existingNumber;
	}

	public Double getIncomingNumber() {
		return incomingNumber;
	}

	public void setIncomingNumber(Double incomingNumber) {
		this.incomingNumber = incomingNumber;
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
	 * @return locker 储物柜
	 */
	public ArticleLocker getLocker() {
		return locker;
	}

	public void setLocker(ArticleLocker locker) {
		this.locker = locker;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
