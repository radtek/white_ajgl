package com.taiji.pubsec.ajqlc.baq.action.bean;

import java.util.ArrayList;
import java.util.List;

import com.taiji.pubsec.ajqlc.baq.model.AskRoomIllegalRecord;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;

/**
 * 询问室分配Bean
 * @author sunjd
 *
 */
public class AskRoomAllocationBean {
	
	private String id;
	private String allocationPeople;	// 分配人
	private String allocationTime;	// 分配时间
	private HandlingAreaReceipt handlingAreaReceipt;	// 办案区使用单id
	private ActivityRoomBean askRoom;	// 问询室id
	private List<AskRoomIllegalRecord> askRoomIllegalRecords = new ArrayList<AskRoomIllegalRecord>();	//问询室违规记录
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAllocationPeople() {
		return allocationPeople;
	}
	public void setAllocationPeople(String allocationPeople) {
		this.allocationPeople = allocationPeople;
	}
	public String getAllocationTime() {
		return allocationTime;
	}
	public void setAllocationTime(String allocationTime) {
		this.allocationTime = allocationTime;
	}
	public HandlingAreaReceipt getHandlingAreaReceipt() {
		return handlingAreaReceipt;
	}
	public void setHandlingAreaReceipt(HandlingAreaReceipt handlingAreaReceipt) {
		this.handlingAreaReceipt = handlingAreaReceipt;
	}
	public ActivityRoomBean getAskRoom() {
		return askRoom;
	}
	public void setAskRoom(ActivityRoomBean askRoom) {
		this.askRoom = askRoom;
	}
	public List<AskRoomIllegalRecord> getAskRoomIllegalRecords() {
		return askRoomIllegalRecords;
	}
	public void setAskRoomIllegalRecords(
			List<AskRoomIllegalRecord> askRoomIllegalRecords) {
		this.askRoomIllegalRecords = askRoomIllegalRecords;
	}
	

	
}
