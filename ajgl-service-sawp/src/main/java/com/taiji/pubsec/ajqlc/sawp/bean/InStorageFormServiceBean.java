package com.taiji.pubsec.ajqlc.sawp.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 入库单Bean
 */
public class InStorageFormServiceBean {

	private String id; // id

	private String code; // 编码

	private Long createdTime; // 创建时间

	private String createdTimeStr; // 创建时间

	private String operator; // 最后更新人姓名

	private String remark; // 备注

	private Long updatedTime; // 更新时间

	private String updatedTimeStr; // 更新时间

	private String caseCode; // 案件编号

	private String caseName; // 案件名称

	private String caseHandler; // 办案民警姓名
	
	private String policeNumber1;	//办案民警1警号
	
	private String policeNumber2;	//办案民警2警号

	private String papers; // 文书名称，可多个，分号隔开
	
	private List<DocBean> docs;	//对应文书列表

	private String suspectName; // 嫌疑人姓名

	private String suspectId;// 嫌疑人
	
	private boolean editOrNot; // 是否进行调整，出库等操作。

	private List<InStorageFormItemServiceBean> inStorageFormItems = new ArrayList<InStorageFormItemServiceBean>(); // 入库单产品

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getPapers() {
		return papers;
	}

	public void setPapers(String papers) {
		this.papers = papers;
	}

	public String getSuspectName() {
		return suspectName;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public Long getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Long updatedTime) {
		this.updatedTime = updatedTime;
	}

	public List<InStorageFormItemServiceBean> getInStorageFormItems() {
		return inStorageFormItems;
	}

	public void setInStorageFormItems(List<InStorageFormItemServiceBean> inStorageFormItems) {
		this.inStorageFormItems = inStorageFormItems;
	}

	public boolean isEditOrNot() {
		return editOrNot;
	}

	public void setEditOrNot(boolean editOrNot) {
		this.editOrNot = editOrNot;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getCaseHandler() {
		return caseHandler;
	}

	public void setCaseHandler(String caseHandler) {
		this.caseHandler = caseHandler;
	}

	public String getCreatedTimeStr() {
		return createdTimeStr;
	}

	public void setCreatedTimeStr(String createdTimeStr) {
		this.createdTimeStr = createdTimeStr;
	}

	public String getUpdatedTimeStr() {
		return updatedTimeStr;
	}

	public void setUpdatedTimeStr(String updatedTimeStr) {
		this.updatedTimeStr = updatedTimeStr;
	}

	public String getSuspectId() {
		return suspectId;
	}

	public void setSuspectId(String suspectId) {
		this.suspectId = suspectId;
	}

	public List<DocBean> getDocs() {
		return docs;
	}

	public void setDocs(List<DocBean> docs) {
		this.docs = docs;
	}

	public String getPoliceNumber1() {
		return policeNumber1;
	}

	public void setPoliceNumber1(String policeNumber1) {
		this.policeNumber1 = policeNumber1;
	}

	public String getPoliceNumber2() {
		return policeNumber2;
	}

	public void setPoliceNumber2(String policeNumber2) {
		this.policeNumber2 = policeNumber2;
	}
}
