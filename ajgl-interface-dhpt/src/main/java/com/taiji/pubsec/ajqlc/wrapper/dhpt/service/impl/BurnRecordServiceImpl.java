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
import com.taiji.pubsec.ajqlc.wrapper.dhpt.Bean.BurnRecordBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.common.service.ConstantBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.common.service.CxfClientServiceImpl;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.common.service.JsonToBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IBurnRecordService;

import net.sf.json.JSONObject;
@Service("burnRecordServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class BurnRecordServiceImpl implements IBurnRecordService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BurnRecordServiceImpl.class);
	
	@Resource
	private IExceptionLogService exceptionLogService;
	
	@Override
	public List<BurnRecordBean> queryBurnRecord(String caseFormCode,
			String startTime, String endTime) throws DahuaException {
		List<Object> params = new ArrayList<Object>();
//		String wsdl = "http://52.4.1.210/itc/ws/ExternalWebService?wsdl";//发布地址
//		String targetNameSpace = "http://external.webservice.dip.dahua.com";//命名空间
		String method = "queryRecords";
		String result = "";
		params.add(startTime);
		params.add(endTime);
		params.add(caseFormCode);
		try {
			String wsdl = "http://" + ServiceConstant.DHSERVERIP + "/itc/ws/ExternalWebService?wsdl";
				 result =  CxfClientServiceImpl.getDateFromWs(wsdl, method, ConstantBean.TARGET_NAME_SPACE, params);
			} catch (Exception e) {
				LOGGER.error("查询刻录记录接口调用错误", e);
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
		return JsonToBean.json2BurnReordBean(result);
	}

}
