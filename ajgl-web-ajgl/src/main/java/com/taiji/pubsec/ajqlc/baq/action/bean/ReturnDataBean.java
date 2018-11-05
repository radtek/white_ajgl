package com.taiji.pubsec.ajqlc.baq.action.bean;

import java.util.ArrayList;
import java.util.List;

public class ReturnDataBean {
	
	private List<String> goods = new ArrayList<String>();
	private List<String> temporary = new ArrayList<String>();
	private List<String> detain = new ArrayList<String>();
	private List<String> keep = new ArrayList<String>();
	private List<String> returnObj = new ArrayList<String>();
	
	public List<String> getGoods() {
		return goods;
	}
	public void setGoods(List<String> goods) {
		this.goods = goods;
	}
	public List<String> getTemporary() {
		return temporary;
	}
	public void setTemporary(List<String> temporary) {
		this.temporary = temporary;
	}
	public List<String> getDetain() {
		return detain;
	}
	public void setDetain(List<String> detain) {
		this.detain = detain;
	}
	public List<String> getKeep() {
		return keep;
	}
	public void setKeep(List<String> keep) {
		this.keep = keep;
	}
	public List<String> getReturnObj() {
		return returnObj;
	}
	public void setReturnObj(List<String> returnObj) {
		this.returnObj = returnObj;
	}
	
}
