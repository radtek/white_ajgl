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
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.ILocationCardService;

import net.sf.json.JSONObject;
@Service("locationCardServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class LocationCardServiceImpl implements ILocationCardService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LocationCardServiceImpl.class);

	@Resource
	private IExceptionLogService exceptionLogService;
	
	@Override
	public ResultBean active(String id,String suspectPersonId, String operaterId) throws DahuaException {
		return activeOrReclaim(suspectPersonId,operaterId,id,ConstantBean.OPRTYPE_ACTIVE,ConstantBean.PERSON_POLICE);
	}

	@Override     
	public ResultBean suspend(String locationCardId) throws DahuaException {
		return suspendOrResume(locationCardId,ConstantBean.OPRTYPE_SUSPEND);
	}

	@Override
	public ResultBean resume(String locationCardId) throws DahuaException {
		return suspendOrResume(locationCardId,ConstantBean.OPRTYPE_RESUME);
	}

	@Override
	public ResultBean reclaim(String id,String policeId, String operaterId) throws DahuaException {
		 	return activeOrReclaim(policeId,operaterId,id,ConstantBean.OPRTYPE_RECLAIM,ConstantBean.PERSON_POLICE);
	}
	/**
	 * 挂起或取消挂起
	 * @param locationCardId 定位卡id
	 * @param operater 操作类型
	 * @return
	 * @throws DahuaException 
	 */
   private ResultBean suspendOrResume(String locationCardId, Integer operater) throws DahuaException{
		String method = "freezeOrUnfreezeBracelet";
		List<Object> parameterValue = new ArrayList<Object>();
		parameterValue.add(locationCardId);
		parameterValue.add(operater);
		String result = "";
		try {
			String wsdl = "http://" + ServiceConstant.DHSERVERIP + "/itc/ws/ExternalWebService?wsdl";
			 result =  CxfClientServiceImpl.getDateFromWs(wsdl, method, ConstantBean.TARGET_NAME_SPACE, parameterValue);
		} catch (Exception e) {
			LOGGER.error("挂起或取消挂起定位卡接口调用错误", e);
			throw new DahuaException(e);
		}
		JSONObject  jasonObject = JSONObject.fromObject(result);
		if("false".equals(jasonObject.get("success").toString())){
			ExceptionLog log = new ExceptionLog();
			log.setHappeningTime(new Date());
			log.setExceptionInfo(jasonObject.get("msg").toString());
			log.setFunctionName(this.getClass().getName() + Thread.currentThread().getStackTrace()[1].getMethodName());
			exceptionLogService.save(log);
			String msg = jasonObject.get("msg").toString();
			msg = msg.replace("手环", "定位卡");
			throw new DahuaException(msg);
		}
		return JsonToBean.json2ResultBean(result);
   }
   /**
    * 激活或回收定位卡
    * @param suspectPersonId 嫌疑人id
    * @param operaterId  操作人id
    * @param locationCardId  定位卡id
    * @param operaterType 操作类型
    * @return
 * @throws DahuaException 
    */
   private ResultBean activeOrReclaim(String suspectPersonId, String operaterId,
			String locationCardId ,String operaterType,String personType) throws DahuaException{
		String method = "operateRfid";
		List<Object> parameterValue = new ArrayList<Object>();
		parameterValue.add(suspectPersonId);
		parameterValue.add(locationCardId);
		parameterValue.add(operaterId);
		parameterValue.add(operaterType);
		parameterValue.add(personType);
		String result = "";
		try {
			String wsdl = "http://" + ServiceConstant.DHSERVERIP + "/itc/ws/ExternalWebService?wsdl";
			 result =  CxfClientServiceImpl.getDateFromWs(wsdl, method, ConstantBean.TARGET_NAME_SPACE, parameterValue);
		} catch (Exception e) {
			LOGGER.error("激活或回收定位卡接口调用错误", e);
			throw new DahuaException(e);
		}
		JSONObject  jasonObject = JSONObject.fromObject(result);
		if("false".equals(jasonObject.get("success").toString())){
			ExceptionLog log = new ExceptionLog();
			log.setHappeningTime(new Date());
			log.setExceptionInfo(jasonObject.get("msg").toString());
			log.setFunctionName(this.getClass().getName() + Thread.currentThread().getStackTrace()[1].getMethodName());
			exceptionLogService.save(log);
			String msg = jasonObject.get("msg").toString();
			msg = msg.replaceAll("手环", "定位卡");
			throw new DahuaException(msg);
		}
		return JsonToBean.json2ResultBean(result);
   }
}
