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
 * 再入库快照记录
 * @author chengkai
 */
@Entity
@Table(name = "t_sawp_zrkkzjl")
public class BackStorageSnapshot {

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
	private String numberRequested;	//扣押数量
	
	@Column(name = "cksl")
	private Double outComingNum;	//出库数量
	
	@Column(name = "bcrksl")
	private Double thisIncomingNum;	//本次再入库数量
	
	@Column(name = "jldw")
	private String measurementUnit;	//计量单位
	
	@Column(name = "wsmc")
	private String paper;	//文书名称
	
	@Column(name = "xyrxm")
	private String suspectName; //嫌疑人/物品持有人姓名
	
	@Column(name = "xyrzjhm")
	private String suspectIdCardNo;	//嫌疑人/物品持有人证件号码
	
	@Column(name = "sfqbzrk")
	private String ifInStorageAll;	//是否全部再入库

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

	public String getNumberRequested() {
		return numberRequested;
	}

	public void setNumberRequested(String numberRequested) {
		this.numberRequested = numberRequested;
	}

	public Double getOutComingNum() {
		return outComingNum;
	}

	public void setOutComingNum(Double outComingNum) {
		this.outComingNum = outComingNum;
	}

	public Double getThisIncomingNum() {
		return thisIncomingNum;
	}

	public void setThisIncomingNum(Double thisIncomingNum) {
		this.thisIncomingNum = thisIncomingNum;
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

	public String getSuspectName() {
		return suspectName;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}

	public String getSuspectIdCardNo() {
		return suspectIdCardNo;
	}

	public void setSuspectIdCardNo(String suspectIdCardNo) {
		this.suspectIdCardNo = suspectIdCardNo;
	}

	public String getIfInStorageAll() {
		return ifInStorageAll;
	}

	public void setIfInStorageAll(String ifInStorageAll) {
		this.ifInStorageAll = ifInStorageAll;
	}
}
