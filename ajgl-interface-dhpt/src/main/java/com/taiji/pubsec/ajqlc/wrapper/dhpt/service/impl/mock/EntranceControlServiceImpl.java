package com.taiji.pubsec.ajqlc.wrapper.dhpt.service.impl.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.pubsec.ajqlc.exceptionLog.model.ExceptionLog;
import com.taiji.pubsec.ajqlc.exceptionLog.service.IExceptionLogService;
import com.taiji.pubsec.ajqlc.util.ServiceConstant;
import com.taiji.pubsec.ajqlc.wrapper.common.bean.ResultBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.common.service.ConstantBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.common.service.CxfClientServiceImpl;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.common.service.JsonToBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IEntranceControlService;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.mock.IEntranceControlServiceSuspend;

import net.sf.json.JSONObject;
@Service("entranceControlServiceImpl-suspend")
@Transactional(rollbackFor = Exception.class)
public class EntranceControlServiceImpl implements IEntranceControlServiceSuspend{

	@Resource
	private IExceptionLogService exceptionLogService;
	
	@Override
	public ResultBean authorize(String cardId, String roomId) throws DahuaException {
        
		return new ResultBean(true);
	}

}
