package com.taiji.pubsec.ajqlc.agt.action.bean;

public class QueryLoginDataBean {
	/**
	 * 办案区使用单
	 */
	private String formCode;
	/**
	 * 房间id
	 */
	private String roomId;
	/**
	 * 房间名称
	 */
	private String roomName;
	/**
	 * 主办民警警号
	 */
	private String hostPoliceCode;
	/**
	 * 主办民警名称
	 */
	private String hostPoliceName;
	/**
	 * 辅助办案民警警号
	 */
	private String auxiliaryPoliceCode;
	/**
	 * 辅助办案民警名称
	 */
	private String auxiliaryPoliceName;
	/**
	 * 嫌疑人名称
	 */
	private String suspectName;
	/**
	 * 嫌疑人身份证号
	 */
	private String suspectID;
	
	/**
	 * 主机id
	 */
	private String hostID;

	public String getFormCode() {
		return formCode;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getHostPoliceCode() {
		return hostPoliceCode;
	}

	public void setHostPoliceCode(String hostPoliceCode) {
		this.hostPoliceCode = hostPoliceCode;
	}

	public String getHostPoliceName() {
		return hostPoliceName;
	}

	public void setHostPoliceName(String hostPoliceName) {
		this.hostPoliceName = hostPoliceName;
	}

	public String getAuxiliaryPoliceCode() {
		return auxiliaryPoliceCode;
	}

	public void setAuxiliaryPoliceCode(String auxiliaryPoliceCode) {
		this.auxiliaryPoliceCode = auxiliaryPoliceCode;
	}

	public String getAuxiliaryPoliceName() {
		return auxiliaryPoliceName;
	}

	public void setAuxiliaryPoliceName(String auxiliaryPoliceName) {
		this.auxiliaryPoliceName = auxiliaryPoliceName;
	}

	public String getSuspectName() {
		return suspectName;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}

	public String getSuspectID() {
		return suspectID;
	}

	public void setSuspectID(String suspectID) {
		this.suspectID = suspectID;
	}

	public String getHostID() {
		return hostID;
	}

	public void setHostID(String hostID) {
		this.hostID = hostID;
	}
	
	
	
}
