package com.taiji.pubsec.ajqlc.baq.action.bean;

public class HarInfoBean {

	private String harId;// 使用单ID

	private String harCode;// 使用单编码

	private String byQuestioningPeopleName;// 被询问人

	private String handlingPolice;// 主办民警

	private Long allocateTime;// 分配时间

	public String getHarId() {
		return harId;
	}

	public void setHarId(String harId) {
		this.harId = harId;
	}

	public String getHarCode() {
		return harCode;
	}

	public void setHarCode(String harCode) {
		this.harCode = harCode;
	}

	public String getByQuestioningPeopleName() {
		return byQuestioningPeopleName;
	}

	public void setByQuestioningPeopleName(String byQuestioningPeopleName) {
		this.byQuestioningPeopleName = byQuestioningPeopleName;
	}

	public String getHandlingPolice() {
		return handlingPolice;
	}

	public void setHandlingPolice(String handlingPolice) {
		this.handlingPolice = handlingPolice;
	}

	public Long getAllocateTime() {
		return allocateTime;
	}

	public void setAllocateTime(Long allocateTime) {
		this.allocateTime = allocateTime;
	}	
}
