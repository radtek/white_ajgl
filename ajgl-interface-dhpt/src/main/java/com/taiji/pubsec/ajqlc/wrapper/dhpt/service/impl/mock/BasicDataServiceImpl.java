package com.taiji.pubsec.ajqlc.wrapper.dhpt.service.impl.mock;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONObject;

import com.taiji.pubsec.ajqlc.exceptionLog.service.IExceptionLogService;
import com.taiji.pubsec.ajqlc.wrapper.common.bean.ResultBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.common.service.ConstantBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IBasicDataService;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.mock.IBasicDataServiceSuspend;
@Service("basicDataServiceImpl-suspend")
@Transactional(rollbackFor = Exception.class)
public class BasicDataServiceImpl  implements IBasicDataServiceSuspend{

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

			return new ResultBean(true);
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
		    json.put("personName", personName);
		    json.put("uuid", id);
		    List<Object> lists = new ArrayList<Object>();
		    lists.add(json.toString());
		    lists.add(ConstantBean.DATATYPE_POLICE);
		    lists.add(ConstantBean.OPRTYPE_ADD);
			return syncData(lists);
	
	}
}
