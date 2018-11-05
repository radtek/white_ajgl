package com.taiji.pubsec.ajqlc.baq.service;

import java.util.List;

import com.taiji.pubsec.ajqlc.baq.model.OperateRecord;

/**
 * 操作记录服务接口
 * @author chengkai
 *
 */
public interface IOperateRecordService {
	
	/**
	 * 新增操作记录
	 * @param operateRecord 操作记录实体
	 */
	public void saveOperateRecord(OperateRecord operateRecord);
	
	/**
	 * 根据操作对象id查找相应的操作记录list
	 * @param modelObjectId 操作对象id
	 * @param modelObject 操作对象包类名
	 * @return List<OperateRecord> 操作记录list
	 */
	public List<OperateRecord> findOperateRecordByModelObjectId(String modelObjectId, String modelObject);
	
}
