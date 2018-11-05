package com.taiji.pubsec.ajqlc.baq.bean;


/**
 * 储物柜在存物品Bean
 * @author 
 */
public class StoredGoodsBean{

	private String name;	// 物品名称
	
	private String num;	// 物品编号

	private String code;	// 图片转base64位编码

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}