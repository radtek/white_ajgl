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
import com.taiji.pubsec.ajqlc.wrapper.dhpt.Bean.AlarmInfoBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.common.service.ConstantBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.common.service.CxfClientServiceImpl;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.common.service.JsonToBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IAlarmService;

import net.sf.json.JSONObject;

@Service("alarmInfoServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class AlarmInfoServiceImpl  implements IAlarmService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AlarmInfoServiceImpl.class);


	@Resource
	private IExceptionLogService exceptionLogService;
	

	@Override
	public List<AlarmInfoBean> queryAlarmWirstInfo(String startTime,
			String endTime, String paramId) throws DahuaException {
		List <Object> params = new ArrayList<Object>();
		params.add(ConstantBean.YJLX_SHBJ);
		params.add(startTime);
		params.add(endTime);
		params.add(paramId);
		return queryAlarmInfo(params);
	}

	@Override
	public List<AlarmInfoBean> queryAlarmAnalyzeInfo(String startTime,
			String endTime, String paramId) throws DahuaException {
		List <Object> params = new ArrayList<Object>();
		params.add(ConstantBean.YJLX_ZNFX);
		params.add(startTime);
		params.add(endTime);
		params.add(paramId);
		return queryAlarmInfo(params);
	}

	@Override
	public List<AlarmInfoBean> queryAlarmInquestInfo(String startTime,
			String endTime, String paramId) throws DahuaException {
		List <Object> params = new ArrayList<Object>();
		params.add(ConstantBean.YJLX_SXSSB);
		params.add(startTime);
		params.add(endTime);
		params.add(paramId);
		return queryAlarmInfo(params);
		
	}
	
	private List<AlarmInfoBean> queryAlarmInfo(List <Object> params) throws DahuaException{
		String method = "getAlarmRecords";
		String result = "";
		try {
			String wsdl = "http://" + ServiceConstant.DHSERVERIP + "/itc/ws/ExternalWebService?wsdl";
			 result =  CxfClientServiceImpl.getDateFromWs(wsdl, method, ConstantBean.TARGET_NAME_SPACE, params);
		} catch (Exception e) {
			LOGGER.error("查询预警消息调用错误", e);
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
		return JsonToBean.json2AlarmBean(result);
	}

}
