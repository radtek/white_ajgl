package com.taiji.pubsec.ajqlc.sla.bean;

public class PersonServiceBean {
	
	private String personId;	//涉案人员id
	
	private String personName;	//涉案人员名字
	
	private Long personBirthDate;	//涉案人员出生日期
	
	private String personSex;	//涉案人员性别

	private String personSexCode;	//涉案人员性别编码
	
	private String personContact;	//涉案人员联系方式
	
	private String personAddress;	//涉案人员家庭住址
	
	private String personIdType;	//涉案人员证件种类
	
	private String personIdNo;	//涉案人员证件号码
	
	private String caseCode;	//案件编号

	private String door;	//户籍地名称
	
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public Long getPersonBirthDate() {
		return personBirthDate;
	}

	public void setPersonBirthDate(Long personBirthDate) {
		this.personBirthDate = personBirthDate;
	}

	public String getPersonSex() {
		return personSex;
	}

	public void setPersonSex(String personSex) {
		this.personSex = personSex;
	}

	public String getPersonContact() {
		return personContact;
	}

	public void setPersonContact(String personContact) {
		this.personContact = personContact;
	}

	public String getPersonAddress() {
		return personAddress;
	}

	public void setPersonAddress(String personAddress) {
		this.personAddress = personAddress;
	}

	public String getPersonIdType() {
		return personIdType;
	}

	public void setPersonIdType(String personIdType) {
		this.personIdType = personIdType;
	}

	public String getPersonIdNo() {
		return personIdNo;
	}

	public void setPersonIdNo(String personIdNo) {
		this.personIdNo = personIdNo;
	}

	public String getPersonSexCode() {
		return personSexCode;
	}

	public void setPersonSexCode(String personSexCode) {
		this.personSexCode = personSexCode;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getDoor() {
		return door;
	}

	public void setDoor(String door) {
		this.door = door;
	}
	
	
	
}
