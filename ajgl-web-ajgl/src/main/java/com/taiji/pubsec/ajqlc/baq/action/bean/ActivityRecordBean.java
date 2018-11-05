package com.taiji.pubsec.ajqlc.baq.action.bean;

import java.util.List;

import com.taiji.pubsec.ajgl.util.bean.FileObjectBean;
import com.taiji.pubsec.ajqlc.baq.model.ActivityRoom;

/**
 * 办案区活动记录
 * @author huangda
 */
public class ActivityRecordBean{

	private String activityContent;	// 活动内容
	
	private String activityType;	// 活动类型

	private String caretaker;	// 侦查员（看管人）
	
	private String maintainer;	// 维护人
	
	private Long maintainTime;	// 维护时间
	
	private String note;	// 备注
	
	private ActivityRoom askRoom;	// 活动房间

	private List<FileObjectBean> files;	// 笔录
	
	private boolean changePermissions; //修改权限

	
	private String id;	// id
	
	private String formCode;  //办案区使用单编码
	
	private String activityTypeName;  //活动描述中文说明，大阶段固定

	private Long endTime;	// 结束时间
	
	private Long startTime;	// 开始时间
	
	private  String  roomId; // 网格id
	
	private String  roomType;//网格类型
	
	private String roomName; //网格名称
	
	private String tdid; //通道id，调看视频用
	
	private String  sjid; //上级记录id  
	
	private int ifOverTimeCount;//是否超时的条数
	
	private int mesCount;//预警消息的条数
	
	private  String cuffId ;//手环物理id
	
	private String hearingRoomId; //审讯室id
	
	private String attId; //附件id
	
	/**
	 * @return	id	id
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return	activityContent	活动内容
	 */
	public String getActivityContent() {
		return activityContent;
	}

	public void setActivityContent(String activityContent) {
		this.activityContent = activityContent;
	}

	/**
	 * @return	activityType	活动类型
	 */
	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	/**
	 * @return	caretaker	侦查员（看管人）
	 */
	public String getCaretaker() {
		return caretaker;
	}

	public void setCaretaker(String caretaker) {
		this.caretaker = caretaker;
	}

	/**
	 * @return	endTime	结束时间
	 */
	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return	maintainer	维护人
	 */
	public String getMaintainer() {
		return maintainer;
	}

	public void setMaintainer(String maintainer) {
		this.maintainer = maintainer;
	}

	/**
	 * @return	maintainTime	维护时间
	 */
	public Long getMaintainTime() {
		return maintainTime;
	}

	public void setMaintainTime(Long maintainTime) {
		this.maintainTime = maintainTime;
	}

	/**
	 * @return	note	备注
	 */
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	
	/**
	 * @return askRoom 活动房间
	 */
	public ActivityRoom getAskRoom() {
		return askRoom;
	}

	public void setAskRoom(ActivityRoom askRoom) {
		this.askRoom = askRoom;
	}

	/**
	 * @return 活动类型名称
	 */
	public String getActivityTypeName() {
		return activityTypeName;
	}

	public void setActivityTypeName(String activityTypeName) {
		this.activityTypeName = activityTypeName;
	}

	public List<FileObjectBean> getFiles() {
		return files;
	}

	public void setFiles(List<FileObjectBean> files) {
		this.files = files;
	}

	/**
	 * @return	startTime	开始时间
	 */
	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public boolean isChangePermissions() {
		return changePermissions;
	}

	public void setChangePermissions(boolean changePermissions) {
		this.changePermissions = changePermissions;
	}

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

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getTdid() {
		return tdid;
	}

	public void setTdid(String tdid) {
		this.tdid = tdid;
	}

	public String getSjid() {
		return sjid;
	}

	public void setSjid(String sjid) {
		this.sjid = sjid;
	}


	
	public int getMesCount() {
		return mesCount;
	}

	public void setMesCount(int mesCount) {
		this.mesCount = mesCount;
	}

	public int getIfOverTimeCount() {
		return ifOverTimeCount;
	}

	public void setIfOverTimeCount(int ifOverTimeCount) {
		this.ifOverTimeCount = ifOverTimeCount;
	}

	public String getCuffId() {
		return cuffId;
	}

	public void setCuffId(String cuffId) {
		this.cuffId = cuffId;
	}

	public String getHearingRoomId() {
		return hearingRoomId;
	}

	public void setHearingRoomId(String hearingRoomId) {
		this.hearingRoomId = hearingRoomId;
	}

	public String getAttId() {
		return attId;
	}

	public void setAttId(String attId) {
		this.attId = attId;
	}


}