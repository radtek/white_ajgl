package com.taiji.pubsec.ajgl.util.bean;

public class OptionItemBean {
	// 编码
	private String code;
	// id
	private String id;
	// 名称
	private String name;
	
	private String time;

	public OptionItemBean() {

	}

	public OptionItemBean(String id, String code, String name) {
		this.id = id;
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
