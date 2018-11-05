package com.taiji.pubsec.ajqlc.sawp.bean;

/**
 * 保存位置bean
 * 
 * @author chengkai
 */
public class StorageServiceBean {

	private String id; // id

	private int existingNumber; // 在库数量

	private Double incomingNumber; // 保存数量

	private String lockerId;// 储物柜id

	private String lockerCode;// 储物柜编号

	private String lockerLocation;// 储物柜描述，名称

	private String lockerRemark;// 储物柜备注
	
	private String lockerStatus; //储物架状态

	private String areaId; // 保管区Id

	private String areaAddress;// 保管区位置

	private String areaName; // 保管区域名称

	private String areaCode;// 保管区编码

	private String areaRemark;// 保管区备注

	private String articleId; // 物品id

	private String articleCode; // 物品编号

	private String caseCode; // 案件编号

	private String caseName; // 案件名称

	private String articleName; // 物品名称
	
	private Double returnNumber; // 返还数量
	
	private String backFormRecordId; //返还记录id
	
	private String backFormItemId;	//返还单项id
	
	private String adjustmentStorageRecordId; //调整记录id
	
	private Double adjustNumber; //调整数量
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getExistingNumber() {
		return existingNumber;
	}

	public void setExistingNumber(int existingNumber) {
		this.existingNumber = existingNumber;
	}

	public Double getIncomingNumber() {
		return incomingNumber;
	}

	public void setIncomingNumber(Double incomingNumber) {
		this.incomingNumber = incomingNumber;
	}

	public String getLockerId() {
		return lockerId;
	}

	public void setLockerId(String lockerId) {
		this.lockerId = lockerId;
	}

	public String getLockerCode() {
		return lockerCode;
	}

	public void setLockerCode(String lockerCode) {
		this.lockerCode = lockerCode;
	}

	public String getLockerLocation() {
		return lockerLocation;
	}

	public void setLockerLocation(String lockerLocation) {
		this.lockerLocation = lockerLocation;
	}

	public String getLockerRemark() {
		return lockerRemark;
	}

	public void setLockerRemark(String lockerRemark) {
		this.lockerRemark = lockerRemark;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaAddress() {
		return areaAddress;
	}

	public void setAreaAddress(String areaAddress) {
		this.areaAddress = areaAddress;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaRemark() {
		return areaRemark;
	}

	public void setAreaRemark(String areaRemark) {
		this.areaRemark = areaRemark;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public Double getReturnNumber() {
		return returnNumber;
	}

	public void setReturnNumber(Double returnNumber) {
		this.returnNumber = returnNumber;
	}

	public String getBackFormRecordId() {
		return backFormRecordId;
	}

	public void setBackFormRecordId(String backFormRecordId) {
		this.backFormRecordId = backFormRecordId;
	}

	public String getBackFormItemId() {
		return backFormItemId;
	}

	public void setBackFormItemId(String backFormItemId) {
		this.backFormItemId = backFormItemId;
	}

	public String getAdjustmentStorageRecordId() {
		return adjustmentStorageRecordId;
	}

	public void setAdjustmentStorageRecordId(String adjustmentStorageRecordId) {
		this.adjustmentStorageRecordId = adjustmentStorageRecordId;
	}

	public Double getAdjustNumber() {
		return adjustNumber;
	}

	public void setAdjustNumber(Double adjustNumber) {
		this.adjustNumber = adjustNumber;
	}

	public String getLockerStatus() {
		return lockerStatus;
	}

	public void setLockerStatus(String lockerStatus) {
		this.lockerStatus = lockerStatus;
	}

}