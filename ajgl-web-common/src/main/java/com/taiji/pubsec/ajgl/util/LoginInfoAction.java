package com.taiji.pubsec.ajgl.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.taiji.pubsec.ajqlc.operationLog.model.OperationLog;
import com.taiji.pubsec.ajqlc.operationLog.service.IOperationLogService;
import com.taiji.pubsec.ajqlc.operationrecord.model.StandardLogRecord;
import com.taiji.pubsec.ajqlc.operationrecord.service.StandardLogRecordService;
import com.taiji.pubsec.businesscomponent.organization.model.Organization;
import com.taiji.pubsec.businesscomponent.organization.model.Person;
import com.taiji.pubsec.businesscomponent.organization.service.IOrganizationService;
import com.taiji.pubsec.businesscomponent.organization.service.IPersonService;
import com.taiji.pubsec.common.web.action.BaseAction;
import com.taiji.pubsec.springsecurity.resource.util.MySecureUser;
import com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil;

public class LoginInfoAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private IOrganizationService organizationService;
	@Resource
	private IPersonService personService;
	
	@Resource
	private StandardLogRecordService standardLogRecordService ;// 警综平台操作日志接口
	
	@Resource
	private IOperationLogService operationLogService ;// 案件管理平台操作日志接口
	
	@SuppressWarnings("unchecked")
	protected Person findCurrentPerson(){
		MySecureUser user = SessionUserDetailsUtil.getMySecureUser() ;
		Map<String, Object> userMap = user.getUserMap() ;
		Map<String, String> mPerson = new HashMap<String, String>(0) ;
		if(userMap.get("person")!=null){
			mPerson = (Map<String, String>)userMap.get("person");
		}
		return personService.findById(mPerson.get("id"));
	}
	@SuppressWarnings("unchecked")
	protected Organization findCurrentOrganization(){
		MySecureUser user = SessionUserDetailsUtil.getMySecureUser() ;
		Map<String, Object> userMap = user.getUserMap() ;
		Map<String, String> mOrg = new HashMap<String, String>(0) ;
		if(userMap.get("org")!=null){
			mOrg = (Map<String, String>)userMap.get("org");
		}
		return organizationService.findOrganizationById(mOrg.get("id"));
	}
	
	/**
	 * 创建警综平台操作日志
	 * 
	 * @param operationType 操作类型
	 * @param result 操作结果
	 * @param errorCode  错误代码
	 * @param functionModule  功能模块名称
	 * @param condition  查询条件
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	protected void createStandardLog(int operationType, String result, String errorCode, String functionModule, String condition){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		MySecureUser user = SessionUserDetailsUtil.getMySecureUser() ;
		if(user==null){
			return;
		}
		Map<String, Object> userMap = user.getUserMap() ;
		Map<String, String> mPerson = new HashMap<String, String>(0) ;
		mPerson = (Map<String, String>)userMap.get("person");
		Map<String, String> mOrg = new HashMap<String, String>(0) ;
		mOrg = (Map<String, String>)userMap.get("org");
		
		StandardLogRecord slr = new StandardLogRecord();
		if(mPerson != null){
			slr.setUserId(mPerson.get("idNumber"));
			slr.setOperator(mPerson.get("name"));
		}
		if(mOrg != null){
			slr.setUnitName(mOrg.get("shortName"));
			slr.setUnitCode(mOrg.get("code"));
		}
		slr.setOperateTime(sdf.format(new Date()));
		slr.setTerminalId(request.getRemoteAddr());
		slr.setOperationType(operationType);
		slr.setResult(result);
		slr.setErrorCode(errorCode);
		slr.setFunctionModule(functionModule);
		slr.setCondition(condition);
		
		standardLogRecordService.save(slr);
	}
	
	/**
	 * 创建案件管理平台操作日志
	 * @param menuContent  功能菜单名称(左侧菜单内容)
	 * @param operationContent  操作内容(带参数具体干什么,)
	 */
	@SuppressWarnings("unchecked")
	protected void  creatCaseManagementLog(String menuContent, String operationContent){
		OperationLog log=new OperationLog();
		
		MySecureUser user = SessionUserDetailsUtil.getMySecureUser() ;
		if(user==null){
			return;
		}
		Map<String, Object> userMap = user.getUserMap() ;
		Map<String, String> mPerson = new HashMap<String, String>(0) ;
		mPerson = (Map<String, String>)userMap.get("person");
		if(mPerson != null){
			log.setPersonName(mPerson.get("name"));//操作人员姓名
		}
		log.setOperatingTime(new Date());//操作时间
		log.setOperateContent(operationContent); //操作内容
		log.setClientIp(getIp());//客户端ip
		log.setFunctionMenuName(menuContent);//功能菜单名称
		log.setUserName(user.getUsername());//用户名
		operationLogService.save(log);
	}
	
	/**
	 * 获取客户端的ip
	 * @param request
	 * @return
	 */
	private  String getIp() {
		HttpServletRequest request =  ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }
}
