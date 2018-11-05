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
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IWristbandService;

import net.sf.json.JSONObject;

@Service("wristbandServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class WristbandServiceImpl implements IWristbandService {
	private static final Logger LOGGER = LoggerFactory.getLogger(WristbandServiceImpl.class);
	
	@Resource
	private IExceptionLogService exceptionLogService;

	@Override
	public ResultBean active(String wristId, String suspectId, String operatorId) throws DahuaException {
		return activeOrReclaim(suspectId, operatorId, wristId, ConstantBean.OPRTYPE_ACTIVE,
				ConstantBean.PERSON_SUSPECT);

	}

	@Override
	public ResultBean suspend(String wristId) throws DahuaException {
		return suspendOrResume(wristId, ConstantBean.OPRTYPE_SUSPEND);
	}

	@Override
	public ResultBean resume(String wristId) throws DahuaException {
		return suspendOrResume(wristId, ConstantBean.OPRTYPE_RESUME);
	}

	@Override
	public ResultBean reclaim(String wristId, String suspectId, String operatorId) throws DahuaException {
		return activeOrReclaim(suspectId, operatorId, wristId, ConstantBean.OPRTYPE_RECLAIM,
				ConstantBean.PERSON_SUSPECT);
	}

	/**
	 * 挂起或取消挂起
	 * 
	 * @param wristId
	 *            手环id
	 * @param operater
	 *            操作类型
	 * @return
	 * @throws DahuaException
	 */
	private ResultBean suspendOrResume(String wristId, Integer operater) throws DahuaException {
		String method = "freezeOrUnfreezeBracelet";
		List<Object> parameterValue = new ArrayList<Object>();
		parameterValue.add(wristId);
		parameterValue.add(operater);
		String result = "";
		try {
			String wsdl = "http://" + ServiceConstant.DHSERVERIP + "/itc/ws/ExternalWebService?wsdl";
			result = CxfClientServiceImpl.getDateFromWs(wsdl, method, ConstantBean.TARGET_NAME_SPACE, parameterValue);
		} catch (Exception e) {
			LOGGER.error("挂起或取消挂起接口调用错误", e);
			throw new DahuaException(e);
		}
		JSONObject jasonObject = JSONObject.fromObject(result);
		if ("false".equals(jasonObject.get("success").toString())) {
			ExceptionLog log = new ExceptionLog();
			log.setHappeningTime(new Date());
			log.setExceptionInfo(jasonObject.get("msg").toString());
			log.setFunctionName(this.getClass().getName() + Thread.currentThread().getStackTrace()[1].getMethodName());
			exceptionLogService.save(log);
			throw new DahuaException(jasonObject.get("msg").toString());
		}
		return JsonToBean.json2ResultBean(result);
	}

	/**
	 * 
	 * @param suspectPersonId
	 *            嫌疑人id
	 * @param operaterId
	 *            操作人id
	 * @param wristId
	 *            手环id
	 * @param operaterType
	 *            操作类型
	 * @param personType
	 *            人员类型 1为嫌疑人 5 为民警
	 * @return
	 * @throws DahuaException
	 */
	private ResultBean activeOrReclaim(String suspectPersonId, String operaterId, String wristId, String operaterType,
			String personType) throws DahuaException {
		
		String method = "operateRfid";
		List<Object> parameterValue = new ArrayList<Object>();
		parameterValue.add(suspectPersonId);
		parameterValue.add(wristId);
		parameterValue.add(operaterId);
		parameterValue.add(operaterType);
		parameterValue.add(personType);
		String result = "";
		try {
			String wsdl = "http://" + ServiceConstant.DHSERVERIP + "/itc/ws/ExternalWebService?wsdl";
			result = CxfClientServiceImpl.getDateFromWs(wsdl, method, ConstantBean.TARGET_NAME_SPACE, parameterValue);
		} catch (Exception e) {
			LOGGER.error("激活或回收接口调用错误", e);
			throw new DahuaException(e);
		}
		JSONObject jasonObject = JSONObject.fromObject(result);
		if ("false".equals(jasonObject.get("success").toString())) {
			ExceptionLog log = new ExceptionLog();
			log.setHappeningTime(new Date());
			log.setExceptionInfo(jasonObject.get("msg").toString());
			log.setFunctionName(this.getClass().getName() + Thread.currentThread().getStackTrace()[1].getMethodName());
			exceptionLogService.save(log);
			throw new DahuaException(jasonObject.get("msg").toString());
		}
		return JsonToBean.json2ResultBean(result);
	}

	/**
	 * 查询手环状态信息
	 * @param braceletPhysicalId 手环物理编号，为null时返回所有手环信息
	 * @return 
	 * @throws DahuaException
	 */
	public ResultBean getBraceletInfoByPcId(String braceletPhysicalId) throws DahuaException {
		String method = "getBraceletInfoByPcId";
		List<Object> parameterValue = new ArrayList<Object>();
		parameterValue.add(braceletPhysicalId);
		String result = "";
		try {
			String wsdl = "http://" + ServiceConstant.DHSERVERIP + "/itc/ws/ExternalWebService?wsdl";
			result = CxfClientServiceImpl.getDateFromWs(wsdl, method, ConstantBean.TARGET_NAME_SPACE, parameterValue);
		} catch (Exception e) {
			LOGGER.error("查询手环状态接口调用错误", e);
			throw new DahuaException(e);
		}
		JSONObject jasonObject = JSONObject.fromObject(result);
		if ("false".equals(jasonObject.get("success").toString())) {
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
