package com.taiji.pubsec.ajqlc.wrapper.dhpt.service.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.ICaseFormService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service("caseFormServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class CaseFormServiceImpl implements ICaseFormService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CaseFormServiceImpl.class);

	
	@Resource
	private IExceptionLogService exceptionLogService;
	
	@Override
	public ResultBean syncCaseFormInfo(String formCode, String caseCode,
			String suspectId, List<Map> polices ) throws DahuaException {
//		String wsdl = "http://52.4.1.210/itc/ws/ExternalWebService?wsdl";//发布地址
//		String targetNameSpace = "http://external.webservice.dip.dahua.com";//命名空间
		JSONObject json = new JSONObject();  
		json.put("registerUUID", formCode);
		json.put("caseUUID", caseCode);
		json.put("suspectUUID", suspectId);
		JSONArray jsArray = new JSONArray();
		for(int z = 0 ; z < polices.size() ;  z++){
			JSONObject json1 = new JSONObject();
			HashMap policeMap = (HashMap) polices.get(z);
			Iterator it = policeMap.entrySet().iterator();
			while (it.hasNext()) {
				Entry entry = (java.util.Map.Entry)it.next();
				json1.put(entry.getKey(), entry.getValue());
				}
			jsArray.add(json1);
		}
		json.put("userInfo",jsArray);
		String result = "";
		try {
			String wsdl = "http://" + ServiceConstant.DHSERVERIP + "/itc/ws/ExternalWebService?wsdl";
			 result =  CxfClientServiceImpl.getDateFromWsByJson(wsdl, "syncRegisterInfo", ConstantBean.TARGET_NAME_SPACE, json.toString());
		} catch (Exception e) {
			LOGGER.error("同步使用单接口调用错误", e);
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
	public ResultBean syncAddCaseInfo(String caseCode, String caseName,
			String unitId,String createDate) throws DahuaException {
		JSONObject json = new JSONObject();  
		json.put("caseUUID", caseCode);
		json.put("caseName", caseName);
		json.put("orgUUID", unitId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		json.put("createDate", createDate);
		List<Object> params = new ArrayList<Object>();
		params.add(json.toString());
		params.add(ConstantBean.DATATYPE_CASE);//同步数据类型，{1：警员、2：嫌疑人、3：案件、4：组织机构}；
		params.add(ConstantBean.OPRTYPE_ADD);//同步操作类型，{1：add、2：edit、3：del}
		return syncData(params);
	}

	@Override
	public ResultBean syncAddSuspectInfo(String idcard, String name,String fromCode) throws DahuaException {
		JSONObject json = new JSONObject();  
		json.put("cardNumber", idcard);
		json.put("name", name);
		json.put("uuid", fromCode);
		List<Object> params = new ArrayList<Object>();
		params.add(json.toString());
		params.add(ConstantBean.DATATYPE_SUSPECT);
		params.add(ConstantBean.OPRTYPE_ADD);
		return syncData(params);
	}
	@Override
	public ResultBean syncUpdateCaseInfo(String caseCode, String caseName,
			String unitId,String createDate) throws DahuaException {
		JSONObject json = new JSONObject();  
		json.put("caseUUID", caseCode);
		json.put("caseName", caseName);
		json.put("orgUUID", unitId);
		json.put("createDate", createDate);
		List<Object> params = new ArrayList<Object>();
		params.add(json.toString());
		params.add(ConstantBean.DATATYPE_CASE);//同步数据类型，{1：警员、2：嫌疑人、3：案件、4：组织机构}；
		params.add(ConstantBean.OPRTYPE_EDIT);//同步操作类型，{1：add、2：edit、3：del}
		return syncData(params);
	}

	@Override
	public ResultBean syncUpdateSuspectInfo(String idcard, String name,String fromCode) throws DahuaException {
		JSONObject json = new JSONObject();  
		json.put("cardNumber", idcard);
		json.put("name", name);
		json.put("uuid", fromCode);
		List<Object> params = new ArrayList<Object>();
		params.add(json.toString());
		params.add(ConstantBean.DATATYPE_SUSPECT);
		params.add(ConstantBean.OPRTYPE_EDIT);
		return syncData(params);
	}
	@Override
	public ResultBean allocateRoom(String formCode, String caseCode,
			String suspectId, String roomId, String mainUserId,
			String assistUserId) throws DahuaException {
//		String wsdl = "http://52.4.1.210/itc/ws/ExternalWebService?wsdl";//发布地址
//	    String targetNameSpace = "http://external.webservice.dip.dahua.com";//命名空间
		String result = "";
		JSONObject json = new JSONObject();  
		json.put("registerUUID", formCode);
		json.put("caseUUID", caseCode);
		json.put("suspectUUID", suspectId);
		json.put("roomUUID", roomId);
		json.put("mainUserUUID", mainUserId);
		json.put("assistUserUUID", assistUserId);
		try {
			String wsdl = "http://" + ServiceConstant.DHSERVERIP + "/itc/ws/ExternalWebService?wsdl";
			 result =  CxfClientServiceImpl.getDateFromWsByJson(wsdl, "startTrial", ConstantBean.TARGET_NAME_SPACE, json.toString());
		} catch (Exception e) {
			LOGGER.error("分配审讯室接口调用错误", e);
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
  private ResultBean syncData(List<Object> params) throws DahuaException{
//	    String wsdl = "http://52.4.1.210/itc/ws/ExternalWebService?wsdl";//发布地址
//		String targetNameSpace = "http://external.webservice.dip.dahua.com";//命名空间
		String method = "synchOpr";
		String result = "";
		try {
			String wsdl = "http://" + ServiceConstant.DHSERVERIP + "/itc/ws/ExternalWebService?wsdl";
			 result =  CxfClientServiceImpl.getDateFromWs(wsdl, method, ConstantBean.TARGET_NAME_SPACE, params);
		} catch (Exception e) {
			LOGGER.error("同步基础数据接口调用错误", e);
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
