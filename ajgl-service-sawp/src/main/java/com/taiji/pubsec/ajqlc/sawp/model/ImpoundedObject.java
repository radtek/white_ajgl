package com.taiji.pubsec.ajqlc.sawp.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 扣押物品
 * @author chengkai
 * @version 1.0
 * @created 08-9月-2016 10:15:47
 */
@Entity
@Table(name = "t_sawp_kywp")
public class ImpoundedObject {
	
	@Id
	private String id;	// id
	
	private String caseId;//案件编号
	
	private String caseName;	//案件名称
	
	private String code;	//编码
	
	private String feature;	//特征
	
	private String name;	//名称
	
	private String number;	//数量
	
	private String paper;	//文书id
	
	private String paperType;	//文书类型
	
	private String paperName;	//文书名称
	
	private String police;	//办案民警
	
	private String policeNumber1;	//办案民警1警号
	
	private String policeNumber2;	//办案民警2警号
	
	private String suspectName;	//嫌疑人姓名
	
	private String suspectId;	//嫌疑人id
	
	private String suspectIDCardNo;	//嫌疑人身份证号

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPolice() {
		return police;
	}

	public void setPolice(String police) {
		this.police = police;
	}

	public String getSuspectName() {
		return suspectName;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}

	public String getSuspectId() {
		return suspectId;
	}

	public void setSuspectId(String suspectId) {
		this.suspectId = suspectId;
	}

	public String getPaperName() {
		return paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getPaper() {
		return paper;
	}

	public void setPaper(String paper) {
		this.paper = paper;
	}

	public String getPaperType() {
		return paperType;
	}

	public void setPaperType(String paperType) {
		this.paperType = paperType;
	}

	public String getSuspectIDCardNo() {
		return suspectIDCardNo;
	}

	public void setSuspectIDCardNo(String suspectIDCardNo) {
		this.suspectIDCardNo = suspectIDCardNo;
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