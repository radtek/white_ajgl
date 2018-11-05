package com.taiji.pubsec.ajqlc.agt.action;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajqlc.agt.action.bean.QueryLoginDataBean;
import com.taiji.pubsec.ajqlc.baq.bean.RoomUseInformationBean;
import com.taiji.pubsec.ajqlc.baq.model.ActivityRoom;
import com.taiji.pubsec.ajqlc.baq.model.AskRoomAllocationRecord;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.baq.model.PoliceInfo;
import com.taiji.pubsec.ajqlc.baq.service.IAskRoomAllocationRecordService;
import com.taiji.pubsec.ajqlc.baq.service.IAskRoomService;
import com.taiji.pubsec.ajqlc.baq.service.IHandlingAreaReceiptService;
import com.taiji.pubsec.ajqlc.util.Constant;
import com.taiji.pubsec.ajqlc.wrapper.common.bean.ResultBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.INoteService;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.businesscomponent.organization.model.Person;
import com.taiji.pubsec.businesscomponent.organization.service.IAccountService;
import com.taiji.pubsec.businesscomponent.organization.service.IPersonService;

/**
 * 办案通登录action
 * 
 * @author 
 *
 */
@Controller("CaseManagementLoginAction")
@Scope("prototype")
public class CaseManagementLoginAction extends ReturnMessageAction {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(CaseManagementLoginAction.class);
	
	@Resource
	private IDictionaryItemService dictionaryItemService;
	
	@Resource
	private IAskRoomAllocationRecordService askRoomAllocationRecordService;

	@Resource
	private IHandlingAreaReceiptService handlingAreaReceiptService;
	
	@Resource
	private IAskRoomService askRoomService;
	
	@Resource
	private IPersonService personService;//人员
	
	@Resource
	private IAccountService accountService;//
	
	@Resource
	private INoteService noteService;
	
	private String roomId; //房间id
	
//	private String ip;//hostip
	
	private String code;//警号
	
	private String id;
	
	private String stat;
	
	private String strTime;
	
	private Map<String,Object> resultMap=new HashMap<String,Object>();
	
	
	/**
	 * 登录页面信息 
	 * @return
	 */
	public  String findData(){
		RoomUseInformationBean room= askRoomAllocationRecordService.getRoomInformationByRoomId(roomId);
		if(room==null){
			return SUCCESS;
		}
		room.setHostID(org.apache.struts2.ServletActionContext.getRequest().getRemoteAddr());
		resultMap.put("loginBean", room);
		return SUCCESS;
	}
	
	/**
	 * 获取登录警号的用户名和密码 
	 * @return
	 */
	public  String findUserMessage(){	
		
		Person per=personService.findPersonByCode(code);
		if(per!=null&&per.getAccount()!=null){
			resultMap.put("userName", per.getAccount().getAccountName());
			resultMap.put("password", per.getAccount().getPassword());
		}
		return SUCCESS;
	}
	
	/**
	 * 调用打开笔录接口
	 * @return
	 * @throws DahuaException 
	 */
	public String  findOCX() throws DahuaException{
		
		String data;
		try {
			data = noteService.produceOcxParam(findKHDIp(),code);
			resultMap.put("data", data);
		} catch (DahuaException e) {
			LOGGER.debug(e.getMessage(), e);
			resultMap.put("alertMsg", e.getMessage());//捕获异常
			return SUCCESS;
		}
		return SUCCESS;
	}
	
	/**
	 * 获取客户端的ip
	 * @param request
	 * @return
	 */
	private  String findKHDIp() {
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
	
	/**
	 * 调用回调接口
	 * @return
	 * @throws DahuaException 
	 */
	public String  findSyncTrialNoteStat() throws DahuaException{
		ResultBean bean;
		try {
			bean = noteService.syncTrialNoteStat(id,stat,strTime);
			resultMap.put("bean", bean);
		} catch (DahuaException e) {
			LOGGER.debug(e.getMessage(), e);
			String msg = e.getMessage();
			resultMap.put("alertMsg", msg);//捕获异常
			return SUCCESS;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 维护分配讯询问室活动记录离开时间
	 * @return
	 */
	public String updateAskroomAllocationRecord(){
		
		AskRoomAllocationRecord arar = askRoomAllocationRecordService.getLastAskRoomAllocation(id);
		if(null != arar){
			arar.setPoliceNum(null);
			arar.setLeaveTime(new Date());
			askRoomAllocationRecordService.updateAskRoomAllocationRecord(arar);
		}
		askRoomService.updateAskRoomStatus(id, Constant.SYZT_KX);
		return SUCCESS;
	}
	
	/**
	 * 登录
	 * @return
	 */
	public String loginMeth(){
		
		return SUCCESS;
	}
	/**
	 * 前台展示Bean
	 * @return
	 */
    public  QueryLoginDataBean	changeBean(HandlingAreaReceipt hand){
    	QueryLoginDataBean bean=new QueryLoginDataBean();
    	bean.setFormCode(hand.getId());
    	List<PoliceInfo> list=new ArrayList<PoliceInfo>();//判断是否主办民警
    	bean.setAuxiliaryPoliceCode("fz001");
    	bean.setAuxiliaryPoliceName("辅警小明");
    	bean.setHostPoliceCode("host001");
    	bean.setHostPoliceName("主办老李");
    	bean.setHostID(org.apache.struts2.ServletActionContext.getRequest().getRemoteAddr());
    	bean.setRoomId(roomId);
    	bean.setRoomName(askRoomService.findAskRoomById(roomId).getName());
    	bean.setSuspectName(hand.getBasicCase().getByQuestioningPeopleName());
    	bean.setSuspectID(hand.getBasicCase().getByQuestioningPeopleIdentifyNum());
	    return bean; 
   };
	
	
	public Map<String, Object> getResultMap() {
		return resultMap;
	}
	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}
//	public String getIp() {
//		return ip;
//	}
//	public void setIp(String ip) {
//		this.ip = ip;
//	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getStrTime() {
		return strTime;
	}

	public void setStrTime(String strTime) {
		this.strTime = strTime;
	}
}
