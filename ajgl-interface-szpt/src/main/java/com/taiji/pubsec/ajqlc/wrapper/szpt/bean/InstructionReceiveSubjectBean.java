package com.taiji.pubsec.ajqlc.wrapper.szpt.bean;

/**
 * 指令接收主体
 *
 */
public class InstructionReceiveSubjectBean {

	private String id;

	private String receiveSubjectType; // 接收主体类型 //Unit的包名类名

	private String receiveSubjectId; // 接收主体id //单位id

	private String receiveSubjectName; // 接收主体名称 //单位名称

	private String status; // 状态

	private String statusName; // 状态名称

	private Long receiveTime; // 接收时间

	private Long feedbackTime; // 最新反馈时间

	private Long signTime; // 签收时间

	private String signPeopleName; // 签收人姓名

//	private List<InstructionReceiveSubjectFeedbackBean> feedbackBeanList = new ArrayList<InstructionReceiveSubjectFeedbackBean>();

	private int isOverTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReceiveSubjectType() {
		return receiveSubjectType;
	}

	public void setReceiveSubjectType(String receiveSubjectType) {
		this.receiveSubjectType = receiveSubjectType;
	}

	public String getReceiveSubjectId() {
		return receiveSubjectId;
	}

	public void setReceiveSubjectId(String receiveSubjectId) {
		this.receiveSubjectId = receiveSubjectId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getReceiveSubjectName() {
		return receiveSubjectName;
	}

	public void setReceiveSubjectName(String receiveSubjectName) {
		this.receiveSubjectName = receiveSubjectName;
	}


	public Long getFeedbackTime() {
		return feedbackTime;
	}

	public void setFeedbackTime(Long feedbackTime) {
		this.feedbackTime = feedbackTime;
	}

	public Long getSignTime() {
		return signTime;
	}

	public void setSignTime(Long signTime) {
		this.signTime = signTime;
	}

	public Long getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Long receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getSignPeopleName() {
		return signPeopleName;
	}

	public void setSignPeopleName(String signPeopleName) {
		this.signPeopleName = signPeopleName;
	}

	public int getIsOverTime() {
		return isOverTime;
	}

	public void setIsOverTime(int isOverTime) {
		this.isOverTime = isOverTime;
	}
	
}
