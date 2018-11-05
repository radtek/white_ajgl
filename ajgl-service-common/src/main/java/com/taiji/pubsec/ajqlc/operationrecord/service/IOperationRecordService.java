package com.taiji.pubsec.ajqlc.operationrecord.service;

import java.util.List;

import com.taiji.pubsec.ajqlc.operationrecord.model.OperationRecord;


/**
 * 操作记录接口
 * @author wangfx
 *
 */
public interface IOperationRecordService {
	
	/**
	 * 保存操作记录
	 * @param operationRecord
	 * @return
	 */
	String saveOperationRecord(OperationRecord operationRecord);
	
	/**
//	 * 根据物品编号查找相关的操作记录
	 * @param articleCode articleCode
	 * @return 返回操作记录list信息
	 */
	List<OperationRecord> findOperationRecordByarticleCode(String articleCode);

}
