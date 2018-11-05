package com.taiji.pubsec.ajqlc.sawp.bean;

public class OutStorageFormServiceBean {

	private String id; // id

	private String code; // 编码

	private Long createdTime; // 创建时间
	
	private Long outStorageTime;	// 出库时间

	private String operator; // 最后更新人姓名

	private String remark; // 备注

	private Long updatedTime; // 更新时间

	private String caseCode; // 案件编号

	private String caseName; // 案件名称
	
	private String isReturned; // 是否全部返回，字典项id，只针对借出和其他类型出库单
	
	private String isReturnedName;// 是否全部返回，字典项名称

	private String papers; // 文书名称，各物品关联的文书名称集合

	private String receiver; // 领取人

	private String suspectName; // 嫌疑人姓名

	private String type; // 出库类型，字典项id
	
	private String typeName;// 出库类型，字典项名称

	private String polices; // 办案民警

	public OutStorageFormServiceBean(String id, String code, Long createdTime, String operator, String remark,
			Long updatedTime, String caseCode, String isReturned, String papers, String receiver, String suspectName,
			String type) {
		super();
		this.id = id;
		this.code = code;
		this.createdTime = createdTime;
		this.operator = operator;
		this.remark = remark;
		this.updatedTime = updatedTime;
		this.caseCode = caseCode;
		this.isReturned = isReturned;
		this.papers = papers;
		this.receiver = receiver;
		this.suspectName = suspectName;
		this.type = type;
	}

	public OutStorageFormServiceBean(){
		
	}
			
	public String getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getOperator() {
		return operator;
	}

	public String getRemark() {
		return remark;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public String getIsReturned() {
		return isReturned;
	}

	public String getPapers() {
		return papers;
	}

	public String getReceiver() {
		return receiver;
	}

	public String getSuspectName() {
		return suspectName;
	}

	public String getType() {
		return type;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public void setIsReturned(String isReturned) {
		this.isReturned = isReturned;
	}

	public void setPapers(String papers) {
		this.papers = papers;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPolices() {
		return polices;
	}

	public void setPolices(String polices) {
		this.polices = polices;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public Long getUpdatedTime() {
		return updatedTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public void setUpdatedTime(Long updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OutStorageFormServiceBean other = (OutStorageFormServiceBean) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getIsReturnedName() {
		return isReturnedName;
	}

	public void setIsReturnedName(String isReturnedName) {
		this.isReturnedName = isReturnedName;
	}

	public Long getOutStorageTime() {
		return outStorageTime;
	}

	public void setOutStorageTime(Long outStorageTime) {
		this.outStorageTime = outStorageTime;
	}
	
	
}
