package com.taiji.pubsec.ajqlc.baq.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 问询室违规记录
 * @author wangfx
 *
 */
@Entity
@Table(name = "t_baq_wxswgjl")
public class AskRoomIllegalRecord {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	
	@Column(name = "tjr")
	private String commitPeople;	//提交人
	
	
	@Column(name = "wgyy")
	private String illegalCause;	//违规原因
	
	@Column(name = "bz", length = 200)
	private String bz;	//备注
	
	@Column(name = "wgsj")
	private Date illegalTime;	//违规时间
	
	
	@Column(name = "spjssj")
	private Date videoEndTime;	//视频结束时间
	
	
	@Column(name = "spkssj")
	private Date videoStartTime;	//视频开始时间
	
	
	@ManyToOne
	@JoinColumn(name = "wxsfpjl_id")
	private AskRoomAllocationRecord askRoomAllocationRecord;	// 问询室分配记录
	
	@ManyToOne
	@JoinColumn(name = "askroom_id")
	private ActivityRoom activityRoom;	// 问询室
	
	/**
	 * @return id	id
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return	commitPeople	提交人
	 */
	public String getCommitPeople() {
		return commitPeople;
	}

	public void setCommitPeople(String commitPeople) {
		this.commitPeople = commitPeople;
	}

	/**
	 * @return	illegalCause	违规原因
	 */
	public String getIllegalCause() {
		return illegalCause;
	}

	public void setIllegalCause(String illegalCause) {
		this.illegalCause = illegalCause;
	}

	/**
	 * @return  illegalTime	违规时间
	 */
	public Date getIllegalTime() {
		return illegalTime;
	}

	public void setIllegalTime(Date illegalTime) {
		this.illegalTime = illegalTime;
	}

	/**
	 * @return	videoEndTime	视频结束时间
	 */
	public Date getVideoEndTime() {
		return videoEndTime;
	}

	public void setVideoEndTime(Date videoEndTime) {
		this.videoEndTime = videoEndTime;
	}

	/**
	 * @return	videoStartTime	视频开始时间
	 */
	public Date getVideoStartTime() {
		return videoStartTime;
	}

	public void setVideoStartTime(Date videoStartTime) {
		this.videoStartTime = videoStartTime;
	}
	
	/**
	 * @return	askRoomAllocationRecord 问询室分配记录
	 */
	public AskRoomAllocationRecord getAskRoomAllocationRecord() {
		return askRoomAllocationRecord;
	}

	public void setAskRoomAllocationRecord(AskRoomAllocationRecord askRoomAllocationRecord) {
		this.askRoomAllocationRecord = askRoomAllocationRecord;
	}

	/**
	 * @return	备注
	 */
	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public ActivityRoom getActivityRoom() {
		return activityRoom;
	}

	public void setActivityRoom(ActivityRoom activityRoom) {
		this.activityRoom = activityRoom;
	}

	

}
