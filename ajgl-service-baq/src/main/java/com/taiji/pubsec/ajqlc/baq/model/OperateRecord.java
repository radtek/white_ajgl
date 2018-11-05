package com.taiji.pubsec.ajqlc.baq.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 操作记录
 * @author chengkai
 */
@Entity
@Table(name = "t_baq_czjl")
public class OperateRecord {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;	// id
	
	@Column(name = "czdx", length = 100)
	private String modelObject;	// 操作对象
	
	@Column(name = "czdx_id", length = 100)
	private String modelObjectId;	// 操作对象id
	
	@Column(name = "bznr", length = 200)
	private String noteText;	// 备注内容

	@Column(name = "bzlx", length = 100)
	private String noteType;	// 备注类型，0:无附件，1有附件
	
	@Column(name = "cznr", length = 100)
	private String operateContent;	// 操作内容
	
	@Column(name = "czr", length = 100)
	private String operator;	// 操作人
	
	@Column(name = "czsj")
	@Temporal(TemporalType.TIMESTAMP)
	private Date operateTime;	// 操作时间
	
	@Column(name = "hdjlId")
	private String handlingAreaActivityRecordIds;	// 活动记录ids逗号分隔
	
	/**
	 * @return id id
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return modelObject 操作对象
	 */
	public String getModelObject() {
		return modelObject;
	}

	public void setModelObject(String modelObject) {
		this.modelObject = modelObject;
	}
	
	/**
	 * @return modelObjectId 操作对象id
	 */
	public String getModelObjectId() {
		return modelObjectId;
	}

	public void setModelObjectId(String modelObjectId) {
		this.modelObjectId = modelObjectId;
	}
	
	/**
	 * @return noteText 备注内容
	 */
	public String getNoteText() {
		return noteText;
	}

	public void setNoteText(String noteText) {
		this.noteText = noteText;
	}
	
	/**
	 * @return noteType 备注类型，0:表示文字从noteText中取，1表示附件从附件表中取
	 */
	public String getNoteType() {
		return noteType;
	}

	public void setNoteType(String noteType) {
		this.noteType = noteType;
	}
	
	/**
	 * @return operateContent 操作内容
	 */
	public String getOperateContent() {
		return operateContent;
	}

	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}
	
	/**
	 * @return operator 操作人
	 */
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	/**
	 * @return operateTime 操作时间
	 */
	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	/**
	 * @return handlingAreaActivityRecordIds 活动记录id逗号分隔
	 */
	public String getHandlingAreaActivityRecordIds() {
		return handlingAreaActivityRecordIds;
	}

	public void setHandlingAreaActivityRecordIds(
			String handlingAreaActivityRecordIds) {
		this.handlingAreaActivityRecordIds = handlingAreaActivityRecordIds;
	}
	
	
	
	
	
}