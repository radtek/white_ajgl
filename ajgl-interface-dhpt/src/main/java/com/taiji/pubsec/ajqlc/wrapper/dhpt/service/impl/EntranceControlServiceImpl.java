package com.taiji.pubsec.ajqlc.wrapper.dhpt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import net.sf.json.JSONObject;
@Service("entranceControlServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EntranceControlServiceImpl implements IEntranceControlService{

	private static final Logger LOGGER = LoggerFactory.getLogger(EntranceControlServiceImpl.class);

	@Resource
	private IExceptionLogService exceptionLogService;
	
	@Override
	public ResultBean authorize(String cardId, String roomId) throws DahuaException {
		String method = "addRoomAuthorityToCard";
		List<Object> parameterValue = new ArrayList<Object>();
		parameterValue.add(cardId);
		parameterValue.add(roomId);
		String result = "";
		try {
			String wsdl = "http://" + ServiceConstant.DHSERVERIP + "/itc/ws/ExternalWebService?wsdl";
			 result =  CxfClientServiceImpl.getDateFromWs(wsdl, method, ConstantBean.TARGET_NAME_SPACE, parameterValue);
		} catch (Exception e) {
			LOGGER.error("授予门禁接口调用错误", e);
			throw new DahuaException(e);
		}
		JSONObject  jasonObject = JSONObject.fromObject(result);
		if("false".equals(jasonObject.get("success").toString())){
			ExceptionLog log = new ExceptionLog();
			log.setHappeningTime(new Date());
			log.setExceptionInfo(jasonObject.get("msg").toString());
			log.setFunctionName(this.getClass().getName() + Thread.currentThread().getStackTrace()[1].getMethodName());
			exceptionLogService.save(log);
			throw new DahuaException(jasonObject.get("msg").toString());
		}
		return JsonToBean.json2ResultBean(result);
	}

	@Override
	public ResultBean cancel(String cardId) throws DahuaException {
		String method = "removeRoomAuthorityFromCard";
		List<Object> parameterValue = new ArrayList<Object>();
		parameterValue.add(cardId);
		String result = "";
		try {
			String wsdl = "http://" + ServiceConstant.DHSERVERIP + "/itc/ws/ExternalWebService?wsdl";
			 result =  CxfClientServiceImpl.getDateFromWs(wsdl, method, ConstantBean.TARGET_NAME_SPACE, parameterValue);
		} catch (Exception e) {
			LOGGER.error("取消门禁接口调用错误", e);
			throw new DahuaException(e);
		}
		JSONObject  jasonObject = JSONObject.fromObject(result);
		if("false".equals(jasonObject.get("success").toString())){
			ExceptionLog log = new ExceptionLog();
			log.setHappeningTime(new Date());
			log.setExceptionInfo(jasonObject.get("msg").toString());
			log.setFunctionName(this.getClass().getName() + Thread.currentThread().getStackTrace()[1].getMethodName());
			exceptionLogService.save(log);
			throw new DahuaException(jasonObject.get("msg").toString());
		}
		return JsonToBean.json2ResultBean(result);
	}

}
