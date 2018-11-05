package com.taiji.pubsec.ajqlc.operationrecord.service;

import com.taiji.pubsec.ajqlc.operationrecord.model.StandardLogRecord;

/**
 * 警综日志记录接口
 * @author dixf
 *
 */
public interface StandardLogRecordService {
	
	/**
	 * 保存操作记录
	 * @param record
	 * 
	 */
	void save(StandardLogRecord record);
	

}
