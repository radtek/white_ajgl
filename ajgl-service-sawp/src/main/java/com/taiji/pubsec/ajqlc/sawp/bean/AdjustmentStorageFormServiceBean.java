package com.taiji.pubsec.ajqlc.sawp.bean;

import java.util.ArrayList;
import java.util.List;

public class AdjustmentStorageFormServiceBean {
	
	private String id;	// id
	
	private String code;	// 编码
	
	private Long createdTime;	// 创建时间
	
	private String operator;	// 最后更新人姓名
	
	private String remark;	// 备注
	
	private Long updatedTime;	// 更新时间	
	
	private String reason; // 调整原因
	
	private Long adjustTime; //调整时间
	
	private List<AdjustmentStorageFormItemServiceBean> asfiBeanList = new ArrayList<AdjustmentStorageFormItemServiceBean>();	// 调整单项bean

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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public List<AdjustmentStorageFormItemServiceBean> getAsfiBeanList() {
		return asfiBeanList;
	}

	public void setAsfiBeanList(List<AdjustmentStorageFormItemServiceBean> asfiBeanList) {
		this.asfiBeanList = asfiBeanList;
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

	public AdjustmentStorageFormServiceBean(){
		
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
		AdjustmentStorageFormServiceBean other = (AdjustmentStorageFormServiceBean) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getAdjustTime() {
		return adjustTime;
	}

	public void setAdjustTime(Long adjustTime) {
		this.adjustTime = adjustTime;
	}
	
	
}
