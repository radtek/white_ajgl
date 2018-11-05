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
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.INoteService;

import net.sf.json.JSONObject;
@Service("noteServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class NoteServiceImpl implements INoteService {

	private static final Logger LOGGER = LoggerFactory.getLogger(NoteServiceImpl.class);
	
	@Resource
	private IExceptionLogService exceptionLogService;
	
	@Override
	public String produceOcxParam(String ip, String userCode) throws DahuaException {
		List<Object> params = new ArrayList<Object>();
		params.add(ip);
		params.add(userCode);
		return initOcx(params,"getTrialingInfoByIp").getData();//应该返回data
	}

	@Override
	public ResultBean syncTrialNoteStat(String id, String stat, String strTime) throws DahuaException {
		List<Object> params = new ArrayList<Object>();
		params.add(id);
		params.add(stat);
		params.add(strTime);
		return initOcx(params,"syncTrialNoteStat");
	}
   private ResultBean  initOcx(List<Object> params,String method) throws DahuaException{
	 		String result = "";
	 		try {
	 			String wsdl = "http://" + ServiceConstant.DHSERVERIP + "/itc/ws/ExternalWebService?wsdl";
	 			 result =  CxfClientServiceImpl.getDateFromWs(wsdl, method, ConstantBean.TARGET_NAME_SPACE, params);
	 		} catch (Exception e) {
	 			LOGGER.error("初始化笔录控件授予门禁接口调用错误", e);
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
