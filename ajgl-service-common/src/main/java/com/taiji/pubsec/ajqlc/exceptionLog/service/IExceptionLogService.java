package com.taiji.pubsec.ajqlc.exceptionLog.service;

import com.taiji.pubsec.ajqlc.exceptionLog.model.ExceptionLog;

public interface IExceptionLogService {
	
	/**
	 * 保存异常日志记录
	 * @param exceptionLog 异常日志记录
	 */
	public void save(ExceptionLog exceptionLog);
	
}
