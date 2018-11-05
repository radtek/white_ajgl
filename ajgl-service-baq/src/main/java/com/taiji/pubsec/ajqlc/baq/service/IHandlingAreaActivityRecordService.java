package com.taiji.pubsec.ajqlc.baq.service;

import org.springframework.security.access.annotation.Secured;

import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaActivityRecord;

/**
 * 办案区活动记录服务接口
 * @author chengkai
 *
 */
public interface IHandlingAreaActivityRecordService {
	
	/**
	 * 新增办案区活动记录
	 * @param handlingAreaActivityRecord 办案区活动记录实体
	 */
	public void save(HandlingAreaActivityRecord handlingAreaActivityRecord);
	
	@Secured("ACL_HandlingAreaActivityRecord_WRITE")
	/**
	 * 修改办案区活动记录
	 * @param handlingAreaActivityRecord 办案区活动记录实体
	 */
	public void update(HandlingAreaActivityRecord handlingAreaActivityRecord);
	
	@Secured("ACL_HandlingAreaActivityRecord_DELETE")
	/**
	 * 删除办案区活动记录
	 * @param handlingAreaActivityRecordId 办案区活动记录id
	 */
	public void delete(String handlingAreaActivityRecordId);
	
	/**
	 * 通过id查询办案区活动记录
	 * @param handlingAreaActivityRecordId 办案区活动记录id
	 * @return
	 */
	public HandlingAreaActivityRecord findById(String handlingAreaActivityRecordId);
}
