package com.taiji.pubsec.ajqlc.wrapper.dhpt.wirstrollback;

public class ErrorWrist implements ErrorDahuaDevice {

	@Override
	public String getId() {
		return id;
	}
	private String id;
	private String suspectId;
	private String operatorid;
	private Integer retried;
	private String operatorType;
	
	public ErrorWrist(String id, String suspectId, String operatorid,String operatorType) {
		super();
		this.id = id;
		this.suspectId = suspectId;
		this.operatorid = operatorid;
		this.operatorType = operatorType;
		retried = 0;
	}

	@Override
	public Integer getRetried() {
		return retried;
	}

	@Override
	public void setRetried(Integer retried) {
		this.retried = retried;
	}

	public String getOperatorid() {
		return operatorid;
	}

	public String getSuspectId() {
		return suspectId;
	}

	public String getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}

}
