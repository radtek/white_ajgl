package com.taiji.pubsec.ajqlc.sawp.action.bean;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ArticleLockerBean {

	private String id;	//id
	
	private String code;	//编码
	
	private StorageAreaBean area;	//保管区

	private String location;	//位置描述
	
	private String remark;	//备注
	
	private String status;  //状态

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
	 * @return code 编码
	 */
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return	location	位置描述
	 */
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	/**
	 * @return	area	保管区
	 */
	
	public StorageAreaBean getArea() {
		return area;
	}

	public void setArea(StorageAreaBean area) {
		this.area = area;
	}


	/**
	 * @return	remark	备注
	 */
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
