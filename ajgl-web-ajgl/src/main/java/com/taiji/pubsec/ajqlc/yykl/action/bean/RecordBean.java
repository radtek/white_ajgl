package com.taiji.pubsec.ajqlc.yykl.action.bean;

/**
 * 刻录bean
 * @author 
 */
public class RecordBean{
	
	private String id;//使用单id
	
	private String  formCode; //使用单编号
	
	private Long inTime; //进入办案区时间
	
	private String policeName; //办案民警
	
	private String  caseName; //所属案件
	
	private String askPerson; //被询问人姓名
	
	private String  askPersonID; //被询问人身份证号
	
	private String sex; //被问询人性别
	
	private  String ifRecord; //是否已刻录
	
	private String photo;	//照片
	
	private String photoId;	//照片Id
	

	public String getFormCode() {
		return formCode;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}

	public Long getInTime() {
		return inTime;
	}

	public void setInTime(Long inTime) {
		this.inTime = inTime;
	}

	public String getPoliceName() {
		return policeName;
	}

	public void setPoliceName(String policeName) {
		this.policeName = policeName;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getAskPerson() {
		return askPerson;
	}

	public void setAskPerson(String askPerson) {
		this.askPerson = askPerson;
	}

	public String getAskPersonID() {
		return askPersonID;
	}

	public void setAskPersonID(String askPersonID) {
		this.askPersonID = askPersonID;
	}

	public String getIfRecord() {
		return ifRecord;
	}

	public void setIfRecord(String ifRecord) {
		this.ifRecord = ifRecord;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	

}