package com.taiji.pubsec.ajqlc.sawp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;

/**
 * 涉案物品
 * @author wangfx
 *
 */
@Entity
@Table(name = "t_sawp_sawp")
public class InvolvedArticle {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	@Column(name = "ajbh")
	private String caseCode;	//案件编号
	
	@Column(name = "ajmc")
	private String caseName;	//案件名称
	
	@Column(name = "bh", unique = true)
	private String code;	//编号
	
	@Column(name = "kysl")
	private String detentionNumber;	//扣押数量，文字描述，从v3获取
	
	@Column(name = "tz")
	private String feature;	//特征
	
	@Column(name = "jldw")
	private String measurementUnit;	//计量单位
	
	@Column(name = "mc")
	private String name;	//名称
	
	@Column(name = "zksl")
	private Double numberInStorage;	//在库数量
	
	@Column(name = "wsid")
	private String paperId;	// 文书id
	
	@Column(name = "wslx")
	private String paperType;	//文书类型
	
	@Column(name = "wsmc")
	private String paper;	//文书名称
	
	@Column(name = "bamjxm")
	private String polices;	//办案民警姓名
	
	private String policeNumber1;	//办案民警1警号
	
	private String policeNumber2;	//办案民警2警号
	
	@Column(name = "xyrxm")
	private String suspectName;	//嫌疑人姓名
	
	@Column(name = "xyrsfzh")
	private String suspectIdentityNumber;	// 嫌疑人身份证号
	
	@Column(name = "wpxz", length = 36)
	private String type;	//物品性质，字典项id
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "rksj")
	private Date inStorageTime;	// 入库时间
	
	@Column(name = "bz")
	private String remark;	// 备注
	
	@OneToMany(mappedBy = "article")
	private List<Storage> storages = new ArrayList<Storage>();

	/**
	 * @return	id	id
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return	caseCode	案件编号
	 */
	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	/**
	 * @return	code	编号
	 */
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return	detentionNumber	扣押数量
	 */
	public String getDetentionNumber() {
		return detentionNumber;
	}

	public void setDetentionNumber(String detentionNumber) {
		this.detentionNumber = detentionNumber;
	}

	/**
	 * @return	feature	特征
	 */
	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	/**
	 * @return	measurementUnit	计量单位
	 */
	public String getMeasurementUnit() {
		return measurementUnit;
	}

	public void setMeasurementUnit(String measurementUnit) {
		this.measurementUnit = measurementUnit;
	}

	/**
	 * @return	name	名称
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getNumberInStorage() {
		return numberInStorage;
	}

	public void setNumberInStorage(Double numberInStorage) {
		this.numberInStorage = numberInStorage;
	}

	/**
	 * @return	paper	文书名称
	 */
	public String getPaper() {
		return paper;
	}

	public void setPaper(String paper) {
		this.paper = paper;
	}

	/**
	 * @return	polices	办案民警姓名
	 */
	public String getPolices() {
		return polices;
	}

	public void setPolices(String polices) {
		this.polices = polices;
	}

	/**
	 * @return	suspectName	嫌疑人姓名
	 */
	public String getSuspectName() {
		return suspectName;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}

	/**
	 * @return	type	物品性质
	 */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @return	storages 保管位置
	 */
	public List<Storage> getStorages() {
		return storages;
	}

	public void setStorages(List<Storage> storages) {
		this.storages = storages;
	}
	
	/**
	 * @return suspectIdentityNumber 嫌疑人证件号码
	 */
	public String getSuspectIdentityNumber() {
		return suspectIdentityNumber;
	}

	public void setSuspectIdentityNumber(String suspectIdentityNumber) {
		this.suspectIdentityNumber = suspectIdentityNumber;
	}
	
	/**
	 * @return inStorageTime 入库时间
	 */
	public Date getInStorageTime() {
		return inStorageTime;
	}

	public void setInStorageTime(Date inStorageTime) {
		this.inStorageTime = inStorageTime;
	}
	
	
	/**
	 * @return paperId 文书id
	 */
	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
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
	 * @return paperType 文书类型
	 */
	public String getPaperType() {
		return paperType;
	}

	public void setPaperType(String paperType) {
		this.paperType = paperType;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String getPoliceNumber1() {
		return policeNumber1;
	}

	public void setPoliceNumber1(String policeNumber1) {
		this.policeNumber1 = policeNumber1;
	}

	public String getPoliceNumber2() {
		return policeNumber2;
	}

	public void setPoliceNumber2(String policeNumber2) {
		this.policeNumber2 = policeNumber2;
	}

}
