package com.taiji.pubsec.ajqlc.baq.service;

import java.util.List;

import com.taiji.pubsec.ajqlc.baq.model.MachineStageRelation;

/**
 * 设备阶段关系-服务接口
 * @author chengkai
 *
 */
public interface IMachineStageRelationService {
	
	/**
	 * 通过machineId查询设备阶段关系
	 * @param machineId 设备id
	 * @return 返回设备阶段关系list
	 */
	public List<MachineStageRelation> findMachineStageRelationsByMachineId(String machineId);
	
}
