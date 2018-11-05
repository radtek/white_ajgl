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
import com.taiji.pubsec.ajqlc.wrapper.common.bean.ResultBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.common.service.ConstantBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.common.service.CxfClientServiceImpl;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.common.service.JsonToBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IBasicDataService;

import net.sf.json.JSONObject;
@Service("basicDataServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class BasicDataServiceImpl  implements IBasicDataService{

	private static final Logger LOGGER = LoggerFactory.getLogger(BasicDataServiceImpl.class);

	@Resource
	private IExceptionLogService exceptionLogService;
	
	@Override
	public ResultBean syncAddOrgnizationInfo(String id, String name,
			String parentId) throws DahuaException {
		JSONObject json = new JSONObject();  
	    json.put("orgName", name);
	    json.put("orgUUID", id);
	    json.put("parentOrgUUID", parentId);
	    List<Object> lists = new ArrayList<Object>();
	    lists.add(json.toString());
	    lists.add(ConstantBean.DATATYPE_ORG);
	    lists.add(ConstantBean.OPRTYPE_ADD);
		return syncData(lists);
	}

	@Override
	public ResultBean syncAddPersonInfo(String loginName, String loginPass,
			String orgId, String personName,String id) throws DahuaException {
		    JSONObject json = new JSONObject();  
		    json.put("loginName", loginName);
		    json.put("loginPass", loginPass);
		    json.put("orgUUID", orgId);
		    json.put("userName", personName);
		    json.put("uuid", id);
		    List<Object> lists = new ArrayList<Object>();
		    lists.add(json.toString());
		    lists.add(ConstantBean.DATATYPE_POLICE);
		    lists.add(ConstantBean.OPRTYPE_ADD);
			return syncData(lists);
	}
	 private ResultBean syncData(List<Object> params) throws DahuaException{
//		    String wsdl = "http://52.4.1.210/itc/ws/ExternalWebService?wsdl";//发布地址
//			String targetNameSpace = "http://external.webservice.dip.dahua.com";//命名空间
			String method = "synchOpr";
			String result = "";
			try {
//				String wsdl = "http://" + ServiceConstant.DHSERVERIP + "/itc/ws/ExternalWebService?wsdl";
				String wsdl = "http://10.161.90.55/itc/ws/ExternalWebService?wsdl";//发布地址
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

	@Override
	public ResultBean syncUpdateOrgnizationInfo(String id, String name,
			String parentId) throws DahuaException {
		JSONObject json = new JSONObject();  
	    json.put("orgName", name);
	    json.put("orgUUID", id);
	    json.put("parentOrgUUID", parentId);
	    List<Object> lists = new ArrayList<Object>();
	    lists.add(json.toString());
	    lists.add(ConstantBean.DATATYPE_ORG);
	    lists.add(ConstantBean.OPRTYPE_EDIT);
		return syncData(lists);
	}
    //大华判断，咱们都调新增接口。 若大华有这个警员则更新。
	@Override
	public ResultBean syncUpdatePersonInfo(String loginName, String loginPass,
			String orgId, String personName, String id) throws DahuaException {
		 JSONObject json = new JSONObject();  
		    json.put("loginName", loginName);
		    json.put("loginPass", loginPass);
		    json.put("orgUUID", orgId);
		    json.put("userName", personName);
		    json.put("uuid", id);
		    List<Object> lists = new ArrayList<Object>();
		    lists.add(json.toString());
		    lists.add(ConstantBean.DATATYPE_POLICE);
		    lists.add(ConstantBean.OPRTYPE_ADD);
			return syncData(lists);
	
	}
}
