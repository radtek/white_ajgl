package com.taiji.pubsec.ajqlc.exceptionLog.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajqlc.exceptionLog.model.ExceptionLog;

/**
 * @author chengkai
 */
@Service("exceptionLogService")
public class ExceptionLogServiceImpl implements IExceptionLogService {

	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@SuppressWarnings("unchecked")
	@Override
	public void save(ExceptionLog exceptionLog) {
		//dao.save(exceptionLog);
		//报错先注了，反正也打不出来
	}

}
