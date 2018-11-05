package com.taiji.pubsec.ajqlc.wrapper.dhpt.wirstrollback;

/**
 * 定位卡错误回滚
 * @author xinfan
 *
 */
public class ErrorLocationCard implements ErrorDahuaDevice{
	private String id;
	private String policenumber;
	private String operatorid;
	private Integer retried;
	private String operatorType;
	@Override
	public String getId() {
		return id;
	}

	@Override
	public Integer getRetried() {
		return retried;
	}

	@Override
	public void setRetried(Integer retried) {
		this.retried = retried;
	}

	public String getPolicenumber() {
		return policenumber;
	}

	public String getOperatorid() {
		return operatorid;
	}

	public ErrorLocationCard(String id, String policenumber, String operatorid,String operatorType) {
		super();
		this.id = id;
		this.policenumber = policenumber;
		this.operatorid = operatorid;
		this.operatorType = operatorType;
		retried = 0;
	}

	public String getOperatorType() {
		return operatorType;
	}

}
