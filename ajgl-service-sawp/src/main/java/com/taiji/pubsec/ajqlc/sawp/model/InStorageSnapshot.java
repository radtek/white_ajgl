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
 * 入库快照记录
 * @author chengkai
 */
@Entity
@Table(name = "t_sawp_rkkzjl")
public class InStorageSnapshot {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id; // id
	
	@ManyToOne
	@JoinColumn(name = "bcslkzjl_id")
	private IncomingSnapshot incomingSnapshot; // 本次入库数量快照记录
	
	@Column(name = "wpmc")
	private String articleName;	//物品名称
	
	@Column(name = "wptz")
	private String articleFeature;	//物品特征
	
	@Column(name = "wpbh")
	private String articleCode;	//物品编号
	
	@Column(name = "kyyrksl")
	private Double numberRequested;	//扣押应入库数量
	
	@Column(name = "wpxz")
	private String articleType;	//物品性质，字典项名称
	
	@Column(name = "bcrksl")
	private Double thisIncomingNum;	//本次入库数量
	
	@Column(name = "jldw")
	private String measurementUnit;	//计量单位
	
	@Column(name = "wsmc")
	private String paper;	//文书名称

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public IncomingSnapshot getIncomingSnapshot() {
		return incomingSnapshot;
	}

	public void setIncomingSnapshot(IncomingSnapshot incomingSnapshot) {
		this.incomingSnapshot = incomingSnapshot;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getArticleFeature() {
		return articleFeature;
	}

	public void setArticleFeature(String articleFeature) {
		this.articleFeature = articleFeature;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public String getArticleType() {
		return articleType;
	}

	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}

	public String getMeasurementUnit() {
		return measurementUnit;
	}

	public void setMeasurementUnit(String measurementUnit) {
		this.measurementUnit = measurementUnit;
	}

	public String getPaper() {
		return paper;
	}

	public void setPaper(String paper) {
		this.paper = paper;
	}

	public Double getNumberRequested() {
		return numberRequested;
	}

	public void setNumberRequested(Double numberRequested) {
		this.numberRequested = numberRequested;
	}

	public Double getThisIncomingNum() {
		return thisIncomingNum;
	}

	public void setThisIncomingNum(Double thisIncomingNum) {
		this.thisIncomingNum = thisIncomingNum;
	}
}
