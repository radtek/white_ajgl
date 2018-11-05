package com.taiji.pubsec.ajqlc.sawp.bean;

import java.util.ArrayList;
import java.util.List;

public class TemporaryStorageShelfBean {
	private String id;
	private String address;	//详细位置
	private String code;	//编码
	private String remark;	//备注
	private String status;  //状态
	
	private List<String> goodsList = new ArrayList<String>();	//当前存放物品list
	
	private String storageInCode;//暂存入库单编码
	
	private int inStorageNum;	//在库总件数
	
	private String caseCode;//案件编号
	
	private String caseName;	//案件名称
	
	private String handlingPolices;	//办案民警
	
	private String suspectName; //嫌疑人姓名
	
	private String suspectIdCardNum;	//嫌疑人身份证号
	
	private TemporaryStorageAreaBean  areaBean;	//保存区Bean
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public TemporaryStorageAreaBean getAreaBean() {
		return areaBean;
	}
	public void setAreaBean(TemporaryStorageAreaBean areaBean) {
		this.areaBean = areaBean;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<String> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(List<String> goodsList) {
		this.goodsList = goodsList;
	}
	public int getInStorageNum() {
		return inStorageNum;
	}
	public void setInStorageNum(int inStorageNum) {
		this.inStorageNum = inStorageNum;
	}
	public String getCaseCode() {
		return caseCode;
	}
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getHandlingPolices() {
		return handlingPolices;
	}
	public void setHandlingPolices(String handlingPolices) {
		this.handlingPolices = handlingPolices;
	}
	public String getSuspectName() {
		return suspectName;
	}
	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}
	public String getSuspectIdCardNum() {
		return suspectIdCardNum;
	}
	public void setSuspectIdCardNum(String suspectIdCardNum) {
		this.suspectIdCardNum = suspectIdCardNum;
	}
	public String getStorageInCode() {
		return storageInCode;
	}
	public void setStorageInCode(String storageInCode) {
		this.storageInCode = storageInCode;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	
}
