package com.taiji.pubsec.ajqlc.agt.action.bean;

import com.taiji.pubsec.ajgl.util.bean.FileObjectBean;
import com.taiji.pubsec.ajqlc.baq.action.bean.PersonBean;

/**
 * 基本情况Bean
 * 
 * @author 
 */
public class BATBasicCaseBean {

	private String id; // id

	private Long birthday; // 出生日期

	private Long enterIntoTime; // 进入办案区时间

	private String byQuestioningPeopleAddress; // 家庭住址

	private String byQuestioningPeopleIdentifyNum; // 身份证件号码

	private String byQuestioningPeopleIdentifyType; // 身份证件种类

	private String byQuestioningPeopleIdentifyTypeStr; // 身份证件种类

	private String byQuestioningPeopleName; // 被问讯人姓名

	private String byQuestioningPeopleTelphoneNumber; // 被讯问人联系方式

	private String causeOfLawCase;	// 案由
	
	private String causeOfLawCaseStr;	// 案由
	
	private String otherCauseOfLawCase; // 其他案由

	private String enterAreaReasons; // 进入办案区原由

	private String byQuestioningPeopleId;	//被问讯人id

	private String lawCase; //所属案件
	
	private String lawCaseName; //所属案件名称

	private String handlingPolice; // 办案民警

	private String sex; // 性别

	private String sexStr; // 性别

	private String handlingAreaReceiptNum; // 使用单编号

	private String harId; // 办案区使用单id

	private FileObjectBean attach;

	private PersonBean modifyPeopleBean; // 最新修改人

	private Long modifyTime; // 最新修改时间

	private Boolean uploadNew;
	
	private String bangleCode;	//手环编号
	
	private String door;	//户籍

	private String photo;	//照片
	
	private String photoId;	//照片Id
	
	private String photoChange;	//照片修改标志
	
	private String updataPersonByAgt; //安管通修改人员
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getByQuestioningPeopleAddress() {
		return byQuestioningPeopleAddress;
	}

	public void setByQuestioningPeopleAddress(String byQuestioningPeopleAddress) {
		this.byQuestioningPeopleAddress = byQuestioningPeopleAddress;
	}

	public String getByQuestioningPeopleIdentifyNum() {
		return byQuestioningPeopleIdentifyNum;
	}

	public void setByQuestioningPeopleIdentifyNum(
			String byQuestioningPeopleIdentifyNum) {
		this.byQuestioningPeopleIdentifyNum = byQuestioningPeopleIdentifyNum;
	}

	public String getByQuestioningPeopleIdentifyType() {
		return byQuestioningPeopleIdentifyType;
	}

	public void setByQuestioningPeopleIdentifyType(
			String byQuestioningPeopleIdentifyType) {
		this.byQuestioningPeopleIdentifyType = byQuestioningPeopleIdentifyType;
	}

	public String getByQuestioningPeopleName() {
		return byQuestioningPeopleName;
	}

	public void setByQuestioningPeopleName(String byQuestioningPeopleName) {
		this.byQuestioningPeopleName = byQuestioningPeopleName;
	}

	public String getByQuestioningPeopleTelphoneNumber() {
		return byQuestioningPeopleTelphoneNumber;
	}

	public void setByQuestioningPeopleTelphoneNumber(
			String byQuestioningPeopleTelphoneNumber) {
		this.byQuestioningPeopleTelphoneNumber = byQuestioningPeopleTelphoneNumber;
	}


	public String getHandlingPolice() {
		return handlingPolice;
	}

	public void setHandlingPolice(String handlingPolice) {
		this.handlingPolice = handlingPolice;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getHandlingAreaReceiptNum() {
		return handlingAreaReceiptNum;
	}

	public void setHandlingAreaReceiptNum(String handlingAreaReceiptNum) {
		this.handlingAreaReceiptNum = handlingAreaReceiptNum;
	}

	public String getHarId() {
		return harId;
	}

	public void setHarId(String harId) {
		this.harId = harId;
	}

	public String getByQuestioningPeopleIdentifyTypeStr() {
		return byQuestioningPeopleIdentifyTypeStr;
	}

	public void setByQuestioningPeopleIdentifyTypeStr(
			String byQuestioningPeopleIdentifyTypeStr) {
		this.byQuestioningPeopleIdentifyTypeStr = byQuestioningPeopleIdentifyTypeStr;
	}

	public PersonBean getModifyPeopleBean() {
		return modifyPeopleBean;
	}

	public void setModifyPeopleBean(PersonBean modifyPeopleBean) {
		this.modifyPeopleBean = modifyPeopleBean;
	}

	public String getSexStr() {
		return sexStr;
	}

	public void setSexStr(String sexStr) {
		this.sexStr = sexStr;
	}

	public FileObjectBean getAttach() {
		return attach;
	}

	public void setAttach(FileObjectBean attach) {
		this.attach = attach;
	}

	public String getLawCase() {
		return lawCase;
	}

	public void setLawCase(String lawCase) {
		this.lawCase = lawCase;
	}

	public Boolean getUploadNew() {
		return uploadNew;
	}

	public void setUploadNew(Boolean uploadNew) {
		this.uploadNew = uploadNew;
	}

	public Long getBirthday() {
		return birthday;
	}

	public void setBirthday(Long birthday) {
		this.birthday = birthday;
	}

	public Long getEnterIntoTime() {
		return enterIntoTime;
	}

	public void setEnterIntoTime(Long enterIntoTime) {
		this.enterIntoTime = enterIntoTime;
	}

	public Long getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Long modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getEnterAreaReasons() {
		return enterAreaReasons;
	}

	public void setEnterAreaReasons(String enterAreaReasons) {
		this.enterAreaReasons = enterAreaReasons;
	}

	public String getCauseOfLawCase() {
		return causeOfLawCase;
	}

	public void setCauseOfLawCase(String causeOfLawCase) {
		this.causeOfLawCase = causeOfLawCase;
	}

	public String getByQuestioningPeopleId() {
		return byQuestioningPeopleId;
	}

	public void setByQuestioningPeopleId(String byQuestioningPeopleId) {
		this.byQuestioningPeopleId = byQuestioningPeopleId;
	}

	public String getLawCaseName() {
		return lawCaseName;
	}

	public void setLawCaseName(String lawCaseName) {
		this.lawCaseName = lawCaseName;
	}

	public String getBangleCode() {
		return bangleCode;
	}

	public void setBangleCode(String bangleCode) {
		this.bangleCode = bangleCode;
	}

	public String getDoor() {
		return door;
	}

	public void setDoor(String door) {
		this.door = door;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getPhotoChange() {
		return photoChange;
	}

	public void setPhotoChange(String photoChange) {
		this.photoChange = photoChange;
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public String getOtherCauseOfLawCase() {
		return otherCauseOfLawCase;
	}

	public void setOtherCauseOfLawCase(String otherCauseOfLawCase) {
		this.otherCauseOfLawCase = otherCauseOfLawCase;
	}

	public String getCauseOfLawCaseStr() {
		return causeOfLawCaseStr;
	}

	public void setCauseOfLawCaseStr(String causeOfLawCaseStr) {
		this.causeOfLawCaseStr = causeOfLawCaseStr;
	}

	public String getUpdataPersonByAgt() {
		return updataPersonByAgt;
	}

	public void setUpdataPersonByAgt(String updataPersonByAgt) {
		this.updataPersonByAgt = updataPersonByAgt;
	}
}