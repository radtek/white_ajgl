package com.taiji.pubsec.ajqlc.wrapper.jzpt.service.bean;

/**
 * 嫌疑人基本信息
 * @author dixiaofeng
 *
 */
public class BasicPersonBean {

	private String name; // 姓名
	private String sex; // 性别
	private String liveAddress; // 现住址
	private String doorAddress; // 户籍地址
	private String phone; // 联系方式
	private String picture; // 身份证照片

	/**
	 * 姓名
	 * @return 姓名
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 性别
	 * @return 性别
	 */
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * 现住址
	 * @return 现住址
	 */
	public String getLiveAddress() {
		return liveAddress;
	}

	public void setLiveAddress(String address) {
		this.liveAddress = address;
	}

	/**
	 * 户籍地址
	 * @return 户籍地址
	 */
	public String getDoorAddress() {
		return doorAddress;
	}

	public void setDoorAddress(String doorAddress) {
		this.doorAddress = doorAddress;
	}

	/**
	 * 联系方式
	 * @return 联系方式
	 */
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 身份证照片
	 * @return 图片base64字符串
	 */
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

}
