package com.taiji.pubsec.ajqlc.baq.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 设备阶段关系表
 * @author chengkai
 */
@Entity
@Table(name = "t_baq_sbjdgxb")
public class MachineStageRelation implements Serializable {
	
	@Id
	@Column(name = "sbid", nullable = false, length = 50)
	private String machineId;	//设备id
	
	@Id
	@Column(name = "bajd", nullable = false, length = 255)
	private String stageUrl;	//办案阶段对应页面url
	
	@Column(name = "bz", length = 50)
	private String remark;	//办案阶段中文说明

	@Column(name = "lx", length = 50)
	private String type;	//设备类型
	
	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public String getStageUrl() {
		return stageUrl;
	}

	public void setStageUrl(String stageUrl) {
		this.stageUrl = stageUrl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
