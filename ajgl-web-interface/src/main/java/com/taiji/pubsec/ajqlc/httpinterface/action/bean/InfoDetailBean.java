package com.taiji.pubsec.ajqlc.httpinterface.action.bean;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * app登录详情信息
 * 
 * @author wangfx
 *
 */
public class InfoDetailBean {

	private String personId;// 人员id

	private String personName;// 人员姓名

	private String personCode;// 人员编码(警号)
	
	private String position;// 职务code
	
	private String positionName;// 职务字典项名称

	private String personSex;// 人员性别

	private String personSexName;// 人员性别，字典项名称

	private String phoneNum;// 手机号
	
	private String officePhone;// 办公电话

	private String orgName; // 组织名称

	private String orgCode;// 组织编码

	private String pdaDeviceNum;// 设备IMEI号

	public InfoDetailBean() {

	}

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

	public String getOrganizationName() {
		return orgName;
	}

	public void setOrganizationName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrganizationCode() {
		return orgCode;
	}

	public void setOrganizationCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	public String getPdaDeviceNum() {
		return pdaDeviceNum;
	}

	public void setPdaDeviceNum(String pdaDeviceNum) {
		this.pdaDeviceNum = pdaDeviceNum;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String getPersonSex() {
		return personSex;
	}

	public void setPersonSex(String personSex) {
		this.personSex = personSex;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getPersonSexName() {
		return personSexName;
	}

	public void setPersonSexName(String personSexName) {
		this.personSexName = personSexName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

}
