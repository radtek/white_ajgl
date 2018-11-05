package com.taiji.pubsec.ajqlc.baq.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 活动记录表
 * @author chengkai
 */
@Entity
@Table(name = "t_baq_hdjlbs")
public class ActivityRecord implements Serializable {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;	// id
	
	@Column(name = "baqsydbm", length = 50)
	private String handlingAreaReceiptNum;	//办案区使用单编码
	
	
	@Column(name = "hdmc", length = 50)
	private String description;	//活动描述中文说明，大阶段固定
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "kssj")
	private Date startTime;	//开始时间
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "jssj")
	private Date endTime;	//结束时间
	
	@Column(name = "wgid", length = 50)
	private String gridId;	//网格id
	
	@Column(name = "wglx", length = 50)
	private String gridType;	//网格类型
		
	@Column(name = "wgmc", length = 100)
	private String gridName;	//网格名称
	
	@Column(name = "tdid", length = 50)
	private String passageId;	//通道id，调看视频用
	
	@Column(name = "sjid", length = 36)
	private String supRecordId;	//上级记录id
	
	@Column(name = "qt", length = 36)
	private String other;	//其他

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHandlingAreaReceiptNum() {
		return handlingAreaReceiptNum;
	}

	public void setHandlingAreaReceiptNum(String handlingAreaReceiptNum) {
		this.handlingAreaReceiptNum = handlingAreaReceiptNum;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getGridId() {
		return gridId;
	}

	public void setGridId(String gridId) {
		this.gridId = gridId;
	}

	public String getGridType() {
		return gridType;
	}

	public void setGridType(String gridType) {
		this.gridType = gridType;
	}

	public String getGridName() {
		return gridName;
	}

	public void setGridName(String gridName) {
		this.gridName = gridName;
	}

	public String getPassageId() {
		return passageId;
	}

	public void setPassageId(String passageId) {
		this.passageId = passageId;
	}

	public String getSupRecordId() {
		return supRecordId;
	}

	public void setSupRecordId(String supRecordId) {
		this.supRecordId = supRecordId;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}
	
	
}
