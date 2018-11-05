package com.taiji.pubsec.ajqlc.xtgl.log;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.operationLog.model.OperationLog;
import com.taiji.pubsec.ajqlc.operationLog.service.IOperationLogService;
import com.taiji.pubsec.ajqlc.util.DateFmtUtil;
import com.taiji.pubsec.ajqlc.xtgl.PageCommonSysManageAction;


/**
 * 案件管理日志查询
 * 
 * @author 
 *
 */
@Controller("operationLogAction")
@Scope("prototype")
public class OperationLogAction extends PageCommonSysManageAction {
	@Resource
	private IOperationLogService operationLogService ;// 案件管理平台操作日志接口
	
	private Map<String,Object> conditionMap = new HashMap<String,Object>();// 查询条件map
	
	private List<OperationLogBean> list=new ArrayList<OperationLogBean>();
	
	private String userName;
	
	private String menuContent;
	
	private String clientIp;
	
	private String startTime;
	
	private String endTime;
	
	private  int sumCount;
	
	/**
	 * 查找所有的资源
	 * @return "success" List<ResourceBean> 资源Bean的list对象
	 */
	public String findOperationLog() {
		this.conditionMap();
		Pager<OperationLog> pager=operationLogService.findOperationLogByConditions(conditionMap, this.getStart()/this.getLength(), this.getLength());
		if(pager == null){
			return SUCCESS;
		}
		sumCount=pager.getTotalNum();
		list=changeBean(pager.getPageList());
		return SUCCESS;
	}


	/**
	 * model转bean
	 * @param pageList
	 * @return
	 */
	private List<OperationLogBean> changeBean(List<OperationLog> pageList) {
		List<OperationLogBean> lstBean=new ArrayList<OperationLogBean>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(OperationLog log:pageList){
			OperationLogBean bean=new OperationLogBean();
			bean.setClientIp(log.getClientIp());
			bean.setFunctionMenuName(log.getFunctionMenuName());
			bean.setOperateContent(log.getOperateContent());
			bean.setOperatingTime(log.getOperatingTime()==null?"":sdf.format(log.getOperatingTime()));
			bean.setPersonName(log.getPersonName());
			bean.setUserName(log.getUserName());
			lstBean.add(bean);
		}
		return lstBean;
	}


	/**
	 * 装配查询条件map
	 */
	private void conditionMap(){
		conditionMap.put("userName", userName);
		conditionMap.put("functionMenuName", menuContent);
		conditionMap.put("clientIp", clientIp);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			conditionMap.put("queryTimeStart", (startTime==null||"".equals(startTime))?null:sdf.parse(startTime));
			conditionMap.put("queryTimeEnd", (endTime==null||"".equals(endTime))?null:sdf.parse(endTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		conditionMap.put("queryTimeStart", startTime==null?null:DateFmtUtil.StringToDate(startTime));
//		conditionMap.put("queryTimeEnd", endTime==null?null:DateFmtUtil.longToDate(endTime));
	}


	public Map<String, Object> getConditionMap() {
		return conditionMap;
	}


	public void setConditionMap(Map<String, Object> conditionMap) {
		this.conditionMap = conditionMap;
	}


	public List<OperationLogBean> getList() {
		return list;
	}


	public void setList(List<OperationLogBean> list) {
		this.list = list;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getMenuContent() {
		return menuContent;
	}


	public void setMenuContent(String menuContent) {
		this.menuContent = menuContent;
	}


	public String getClientIp() {
		return clientIp;
	}


	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}


	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	public int getSumCount() {
		return sumCount;
	}


	public void setSumCount(int sumCount) {
		this.sumCount = sumCount;
	}

}
