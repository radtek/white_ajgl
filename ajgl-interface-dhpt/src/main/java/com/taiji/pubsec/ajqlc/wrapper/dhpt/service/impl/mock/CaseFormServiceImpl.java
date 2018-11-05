package com.taiji.pubsec.ajqlc.wrapper.dhpt.service.impl.mock;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONObject;

import com.taiji.pubsec.ajqlc.exceptionLog.service.IExceptionLogService;
import com.taiji.pubsec.ajqlc.wrapper.common.bean.ResultBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.common.service.ConstantBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.ICaseFormService;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.mock.ICaseFormServiceSuspend;
@Service("caseFormServiceImpl-suspend")
@Transactional(rollbackFor = Exception.class)
public class CaseFormServiceImpl implements ICaseFormServiceSuspend {
	
	@Resource
	private IExceptionLogService exceptionLogService;
	
	@Override
	public ResultBean syncCaseFormInfo(String formCode, String caseCode,
			String suspectId, List<Map> polices ) throws DahuaException {

		return new ResultBean(true);
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

		return new ResultBean(true);
		
	}
  private ResultBean syncData(List<Object> params) throws DahuaException{
	  
		return new ResultBean(true);
  }
 
}
