package com.taiji.pubsec.ajqlc.baq.action.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taiji.pubsec.ajgl.util.bean.FileObjectBean;

/**
 * 操作记录
 * @author huangda
 */
public class OperateRecordBean {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OperateRecordBean.class);
	
	private String id;	// id
	
	private String modelObject;	// 操作对象
	
	private String modelObjectId;	// 操作对象id
	
	private String noteText;	// 备注内容

	private String noteType;	// 备注类型，0:无附件，1有附件
	
	private String operateContent;	// 操作内容
	
	private String operator;	// 操作人
	
	private Date operateTime;	// 操作时间
	
	private List<FileObjectBean> attLst; //附件
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

	public String getOperateTimeStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try{
			return sdf.format(operateTime);
		}catch(Exception e) {
			LOGGER.debug(e.getMessage(), e);
			return "";
		}
	}
	
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public List<FileObjectBean> getAttLst() {
		return attLst;
	}

	public void setAttLst(List<FileObjectBean> attLst) {
		this.attLst = attLst;
	}

	
}