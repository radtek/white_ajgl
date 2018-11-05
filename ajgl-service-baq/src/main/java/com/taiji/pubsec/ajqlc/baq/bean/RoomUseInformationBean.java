package com.taiji.pubsec.ajqlc.baq.bean;

import java.util.List;

public class RoomUseInformationBean {
	
	private String roomName;	//房间名称
	
	private String handlingAreaReceiptNum;	//使用单编号
	
	private String  handlingAreaReceiptId; //使用单id
	
	private List<String> mainPoliceNumLst;	//主办民警警号集合
	
	private List<String> mainPoliceNameLst;	//主办民警名称集合
	
	private String byQuestioningPeopleName;	//嫌疑人名称
	
	private String byQuestioningPeopleIdentifyNum;	//嫌疑人身份证号
	
	private String HostID; //主机的ip 地址

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getHandlingAreaReceiptNum() {
		return handlingAreaReceiptNum;
	}

	public void setHandlingAreaReceiptNum(String handlingAreaReceiptNum) {
		this.handlingAreaReceiptNum = handlingAreaReceiptNum;
	}

	public String getByQuestioningPeopleName() {
		return byQuestioningPeopleName;
	}

	public void setByQuestioningPeopleName(String byQuestioningPeopleName) {
		this.byQuestioningPeopleName = byQuestioningPeopleName;
	}

	public String getByQuestioningPeopleIdentifyNum() {
		return byQuestioningPeopleIdentifyNum;
	}

	public void setByQuestioningPeopleIdentifyNum(String byQuestioningPeopleIdentifyNum) {
		this.byQuestioningPeopleIdentifyNum = byQuestioningPeopleIdentifyNum;
	}

	public String getHandlingAreaReceiptId() {
		return handlingAreaReceiptId;
	}

	public void setHandlingAreaReceiptId(String handlingAreaReceiptId) {
		this.handlingAreaReceiptId = handlingAreaReceiptId;
	}

	public String getHostID() {
		return HostID;
	}

	public void setHostID(String hostID) {
		HostID = hostID;
	}

	public List<String> getMainPoliceNumLst() {
		return mainPoliceNumLst;
	}

	public void setMainPoliceNumLst(List<String> mainPoliceNumLst) {
		this.mainPoliceNumLst = mainPoliceNumLst;
	}

	public List<String> getMainPoliceNameLst() {
		return mainPoliceNameLst;
	}

	public void setMainPoliceNameLst(List<String> mainPoliceNameLst) {
		this.mainPoliceNameLst = mainPoliceNameLst;
	}
	
	
}
