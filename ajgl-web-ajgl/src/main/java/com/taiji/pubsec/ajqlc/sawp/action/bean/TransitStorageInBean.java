package com.taiji.pubsec.ajqlc.sawp.action.bean;

/**
 * 暂存物品入库
 * @author lenovo
 *
 */
public class TransitStorageInBean {
	
	private String id ;

	private String harCode;	// 使用单编号
	
	private String code;	// 入库单编号

	private String startDate; //入库单查询的开始时间 
	 
	 private String endDate;//入库单查询的结束时间 
	 
	 private String caseCode;// 案件编号
	 
	 private String ownerName;// 物品持有人  
	 
	 private String isShow; //是否显示
	 
	 private String createDate; //创建时间
	 
	 private String transitStoreLocker; //储物箱
	 
	 private String remark; //备注
	 
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHarCode() {
		return harCode;
	}

	public void setHarCode(String harCode) {
		this.harCode = harCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getTransitStoreLocker() {
		return transitStoreLocker;
	}

	public void setTransitStoreLocker(String transitStoreLocker) {
		this.transitStoreLocker = transitStoreLocker;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
