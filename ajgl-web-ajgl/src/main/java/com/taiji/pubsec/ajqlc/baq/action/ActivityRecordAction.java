package com.taiji.pubsec.ajqlc.baq.action;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;

import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajqlc.acl.CustomizedAclService;
import com.taiji.pubsec.ajqlc.alert.model.AlertMessage;
import com.taiji.pubsec.ajqlc.alert.service.IAlertMessageService;
import com.taiji.pubsec.ajqlc.attachment.model.Attachment;
import com.taiji.pubsec.ajqlc.attachment.service.IAttachmentCustomizedService;
import com.taiji.pubsec.ajqlc.baq.action.bean.ActivityRecordBean;
import com.taiji.pubsec.ajqlc.baq.action.bean.ActivityRoomBean;
import com.taiji.pubsec.ajqlc.baq.action.bean.WarningMessageBean;
import com.taiji.pubsec.ajqlc.baq.model.ActivityRecord;
import com.taiji.pubsec.ajqlc.baq.model.ActivityRoom;
import com.taiji.pubsec.ajqlc.baq.model.AskRoomAllocationRecord;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceiptToPoliceInfo;
import com.taiji.pubsec.ajqlc.baq.model.PoliceInfo;
import com.taiji.pubsec.ajqlc.baq.service.IActivityRecordService;
import com.taiji.pubsec.ajqlc.baq.service.IAlarmRecordService;
import com.taiji.pubsec.ajqlc.baq.service.IAskRoomAllocationRecordService;
import com.taiji.pubsec.ajqlc.baq.service.IAskRoomService;
import com.taiji.pubsec.ajqlc.baq.service.IHandleAreaActivityRecordInfoService;
import com.taiji.pubsec.ajqlc.baq.service.IHandlingAreaActivityRecordService;
import com.taiji.pubsec.ajqlc.baq.service.IHandlingAreaReceiptService;
import com.taiji.pubsec.ajqlc.baq.service.IOperateRecordService;
import com.taiji.pubsec.ajqlc.operationrecord.model.StandardLogRecord;
import com.taiji.pubsec.ajqlc.util.Constant;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.Bean.AlarmInfoBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.common.service.ConstantBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IAlarmService;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryItem;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.businesscomponent.organization.model.Person;
import com.taiji.pubsec.springsecurity.resource.util.MySecureUser;
import com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil;
import com.taiji.pubsec.weboffice.iweboffice2000.service.IWebOffice2000Service;

import net.sf.json.JSONObject;

/**
 * 活动记录
 * 
 * @author huangda
 *
 */
@Controller("activityRecordAction")
@Scope("prototype")
public class ActivityRecordAction extends ReturnMessageAction {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityRecordAction.class);
	
	@Resource
	private IDictionaryItemService dictionaryItemService;
	
	@Resource
	private IHandlingAreaReceiptService handlingAreaReceiptService;

	@Resource
	private IAskRoomService askRoomService;
	
	@Resource
	private IAskRoomAllocationRecordService askRoomAllocationRecordService;
	
	@Resource
	private IHandleAreaActivityRecordInfoService handleAreaActivityRecordInfoService;
	
	@Resource
	private IHandlingAreaActivityRecordService handlingAreaActivityRecordService;
	
	@Resource
	private CustomizedAclService customizedAclService;
	
	@Resource
	private IAttachmentCustomizedService attachmentCustomizedService;
	
	@Resource
	private IOperateRecordService operateRecordService;
	
	@Resource
	private IActivityRecordService activityRecordService;
	
	@Resource
	private IAlertMessageService alertMessageService;
	
	@Resource
	private IAlarmRecordService alarmRecordService;
	
	@Resource
	private IAlarmService alarmService;
	
	@Resource
	private IWebOffice2000Service iWebOffice2000Service;
	
	private Map<String , Object> resultMap=new HashMap<String,Object>();
	
	private String id;
	
	private String haarInfoId;
	
	private Person modifyPeople;
	
	private Long updatedTime;
	
	private List<String> deleteIdLst = new ArrayList<String>();
	
	private List<String> delFileLst = new ArrayList<String>();
	
	private boolean modifyFlag;
	
	private List<ActivityRoomBean> activityRoomBeanLst = new ArrayList<ActivityRoomBean>();
	
	private List<ActivityRoomBean> otherRoomBeanLst = new ArrayList<ActivityRoomBean>();
	
	private String harId;
	
	private String roomId;
	
	private boolean modifyBtnFlag;
	
	private boolean printBtnFlag;
	
	private boolean finishBtnFlag;
	
	private String justShow;
	
	private String queryStr;
	
	private String wordName;
	
	private String userName;
	
	private String password;
	
	
	public String findUserMessage(){
		MySecureUser user = SessionUserDetailsUtil.getMySecureUser() ;
		Map<String, Object> userMap = user.getUserMap() ;
		userName=(String)userMap.get("userName");
		password=(String)userMap.get("password");
		return SUCCESS;
	}
		
	@SuppressWarnings("unchecked")
	public String showActivityRecord(){
		MySecureUser user = SessionUserDetailsUtil.getMySecureUser() ;
		Map<String, Object> userMap = user.getUserMap() ;
		if("true".equals(justShow)){
			setModifyBtnFlag(false);
			setPrintBtnFlag(false);
			setFinishBtnFlag(false);
			return SUCCESS;
		}
		HandlingAreaReceipt har = handlingAreaReceiptService
				.findHandlingAreaReceiptById(harId);
		if(Constant.SYDZT_YWC.equals(har.getStatus())){
			setFinishBtnFlag(false);
			setModifyBtnFlag(false);
			setPrintBtnFlag(true);
		}else{
			setPrintBtnFlag(false);
			setModifyBtnFlag(true);
			//TODO SessionUserDetailsUtil.isResourceAccess("/handlingAreaReceipt/*");
			setFinishBtnFlag(true);
		}
		List<Map<String, String>> roles = (List<Map<String, String>>) userMap
				.get("roles");
		for (Map<String, String> role : roles) {
			if (SUPER_ROLE_CODE.equals(role.get("code"))) {
				setModifyBtnFlag(true);
				break;
			}
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "id="+harId);
		return SUCCESS;
	}
	
	/**
	 * 私有方法  组装分配记录
	 * @param  roomId	问询室id
	 * @param  id 	使用单id
	 * @return askRoomAllocationRecord 问询室分配记录
	 */
	private AskRoomAllocationRecord askRoomAllocationConversionReceipt(String roomId ,String id){
		ActivityRoom askRoom = askRoomService.findAskRoomById(roomId);
		HandlingAreaReceipt handlingAreaReceipt = handlingAreaReceiptService.findHandlingAreaReceiptById(id);
		AskRoomAllocationRecord askRoomAllocationRecord = new AskRoomAllocationRecord();
		if(askRoom !=null && handlingAreaReceipt != null){
			askRoomAllocationRecord.setAllocationPeople(this.findCurrentPerson().getName());
			askRoomAllocationRecord.setAllocationTime(new Date());
			askRoomAllocationRecord.setAskRoom(askRoom);
			askRoomAllocationRecord.setHandlingAreaReceipt(handlingAreaReceipt);
			Set<HandlingAreaReceiptToPoliceInfo> htpSet = handlingAreaReceipt.getHandlingAreaReceiptToPoliceInfoList();
			String str = "";
			for(HandlingAreaReceiptToPoliceInfo htp : htpSet){
				str += htp.getPoliceInfo().getPoliceNum() + ",";
			}
			askRoomAllocationRecord.setPoliceNum(str.substring(0, str.length()-1));
		}
		return askRoomAllocationRecord;
	}
	
	/**
	 * 新增分配记录
	 * 
	 * @return SUCCESS
	 * 
	 */
	public String addAskRoomAllocationRecord(){
		if(StringUtils.isBlank(roomId) || StringUtils.isBlank(harId)){
			this.setMsg("操作失败，数据错误！");
			this.setFlag("false");
			this.createStandardLog(StandardLogRecord.OPERATETYPE_ADD, StandardLogRecord.OPERATERESULT_FAIL, null, "案件管理-办案区管理", null);
			this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "对该使用单分配询问室(使用单ID:"+harId+"),操作失败，数据错误！");
			return SUCCESS;
		}
		HandlingAreaReceipt har = handlingAreaReceiptService.findHandlingAreaReceiptById(harId);
		Set<HandlingAreaReceiptToPoliceInfo> hrtp = har.getHandlingAreaReceiptToPoliceInfoList();
		for (HandlingAreaReceiptToPoliceInfo set : hrtp) {
			PoliceInfo pollice = set.getPoliceInfo() ;
			String policeNum = pollice.getPoliceNum() ;
			List<AskRoomAllocationRecord> list = askRoomAllocationRecordService.findAskRoomAllocationRecordByPoliceNum(policeNum) ;
			if(list != null && !list.isEmpty()){
				this.setMsg("警号："+ policeNum +"民警已分配过讯（询）问室，不能再进行分配！");
				this.createStandardLog(StandardLogRecord.OPERATETYPE_ADD, StandardLogRecord.OPERATERESULT_FAIL, null, "案件管理-办案区管理", null);
				this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "对该使用单分配询问室(使用单ID:"+harId+"),操作失败，数据错误！");
				return SUCCESS;
			}
		}
		if(har.getFinallyLeaveInfo() != null && har.getFinallyLeaveInfo().getAskRoom() != null){
			this.setMsg("操作失败，使用单已分配讯问室！");
			this.setFlag("false");
			this.createStandardLog(StandardLogRecord.OPERATETYPE_ADD, StandardLogRecord.OPERATERESULT_FAIL, null, "案件管理-办案区管理", null);
			this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "对该使用单分配询问室(使用单ID:"+harId+"),操作失败，使用单已分配讯问室！");
			return SUCCESS;
		}
		if(har.getFinallyLeaveInfo() != null && har.getFinallyLeaveInfo().getFinallyLeaveTime() != null){
			this.setMsg("操作失败，嫌疑人已办理离开手续！");
			this.setFlag("false");
			this.createStandardLog(StandardLogRecord.OPERATETYPE_ADD, StandardLogRecord.OPERATERESULT_FAIL, null, "案件管理-办案区管理", null);
			this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "对该使用单分配询问室(使用单ID:"+harId+"),操作失败，嫌疑人已办理离开手续！");
			return SUCCESS;
		}
		try {
			AskRoomAllocationRecord arar = askRoomAllocationConversionReceipt(roomId, harId);
			askRoomAllocationRecordService.saveAskRoomAllocationRecord(arar);
			
			//分配询（讯）问室
			ActivityRecord allocationInquiryRoom = new ActivityRecord();
			allocationInquiryRoom.setDescription(Constant.ACTIVITY_STAGE_ALLOT_FPXXWS);
			allocationInquiryRoom.setHandlingAreaReceiptNum(har.getBasicCase().getHandlingAreaReceiptNum());
			allocationInquiryRoom.setStartTime(new Date());
			allocationInquiryRoom.setEndTime(new Date());
			ActivityRecord supRecord = activityRecordService.saveOrUpdateRecordOfAskProcess(allocationInquiryRoom, Constant.ACTIVITY_STAGE_DESCRIPTION_XXWGC, har.getBasicCase().getHandlingAreaReceiptNum());
			if(supRecord != null){
				supRecord.setOther(arar.getId());
				activityRecordService.updateActivationRecord(supRecord);
			}
			askRoomService.updateAskRoomStatus(roomId, Constant.SYZT_SYZ);
			this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "对该使用单分配询问室(使用单ID:"+harId+"),结果分配成功");
			this.setFlag("true");
		} catch (DahuaException e) {
			this.setFlag("false");
			LOGGER.error(e.getMessage(), e);
	      this.setMsg(e.getMessage());
	      this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "对该使用单分配询问室(使用单ID:"+harId+"),"+e.getMessage());
			return SUCCESS;
		
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_ADD, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		return SUCCESS;
	}
	
	public String findAllActivityRoom(){
		List<ActivityRoom> activityRoomList = askRoomService.findAskRoomByType(null);
		for(ActivityRoom ar : activityRoomList){
			ActivityRoomBean activityRoomBean = new ActivityRoomBean();
			activityRoomBean.setId(ar.getId());
			activityRoomBean.setName(ar.getName());
			activityRoomBean.setType(ar.getType());
			activityRoomBean.setStatus(ar.getStatus());
			if(ar.getStatus().equals(Constant.SYZT_SYZ) && Constant.FJLX_WXS.equals(ar.getType())){
				AskRoomAllocationRecord arar = askRoomAllocationRecordService.getLastAskRoomAllocation(ar.getId());
				if(arar!=null){
					activityRoomBean.setSxId(arar.getTrialRecordId());
					HandlingAreaReceipt har = arar.getHandlingAreaReceipt();
					activityRoomBean.getHarInfo().setHarId(har.getId());
					activityRoomBean.getHarInfo().setHarCode(har.getBasicCase().getHandlingAreaReceiptNum());
					activityRoomBean.getHarInfo().setByQuestioningPeopleName(har.getBasicCase().getByQuestioningPeopleName());
					activityRoomBean.getHarInfo().setHandlingPolice(har.getBasicCase().getHandlingPolice());
					activityRoomBean.getHarInfo().setAllocateTime(arar.getAllocationTime().getTime());
				}
			}
			if(Constant.FJLX_WXS.equals(ar.getType())){
				activityRoomBeanLst.add(activityRoomBean);
			}else{
				DictionaryItem di = dictionaryItemService.findById(ar.getType());
				if(di != null){
					activityRoomBean.setXyrMap(askRoomService.findSuspectsByConditions(di.getName(), ar.getName()));
				}
				otherRoomBeanLst.add(activityRoomBean);
			}
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "unitId="+this.findCurrentOrganization().getId());
		return SUCCESS;
	}
	
	/**
	 * 查询活动记录
	 * @return
	 */
	public String queryActivityRecordList() {
        HandlingAreaReceipt  hand=handlingAreaReceiptService.findHandlingAreaReceiptById(id);
		if(hand==null){
			return null;
		}
		List<ActivityRecord> lst=new ArrayList<ActivityRecord>();
		//查询指定使用单的大阶段
		List<ActivityRecord> listParent=activityRecordService.findActivityRecordsByReceiptCodeAndSupRecordId(hand.getBasicCase().getHandlingAreaReceiptNum(),null);
		for(int i=0;i<listParent.size();i++){
			lst.add(listParent.get(i));
			List<ActivityRecord> listSon=activityRecordService.findActivityRecordsByReceiptCodeAndSupRecordId(hand.getBasicCase().getHandlingAreaReceiptNum(),listParent.get(i).getId());
			lst.addAll(listSon);
		}
		List<ActivityRecordBean> listBean=changeBean(lst);
		resultMap.put("recBean", listBean);
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "id="+id);
		return SUCCESS;
	}
	
	/**
	 * 查询预警消息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String findWarningMessageByPage(){
		Map<String,Object> map=JSONObject.fromObject(queryStr);
		Date start=new Date(Long.parseLong(String.valueOf(map.get("startTime"))));
		Date end;
		if(map.get("endTime")==null){
			end=new Date();
		}else{
			end=new Date(Long.parseLong(String.valueOf(map.get("endTime"))));
		}
		String cuffId =String.valueOf(map.get("cuffId"));  //手环
		String hearingRoomId =String.valueOf(map.get("hearingRoomId"));//问询室
		String gridId =String.valueOf(map.get("gridId"));//网格id
		String place =String.valueOf(map.get("place"));//位置
		List<AlarmInfoBean> listAll=new ArrayList<AlarmInfoBean>();
		try {
			if(cuffId!=null&&!"".equals(cuffId)){
				listAll=addLst(listAll,alarmRecordService.queryMyAlarmRecord(start, end,cuffId,String.valueOf(ConstantBean.YJLX_SHBJ)));
			}
			if(hearingRoomId!=null&&!"".equals(hearingRoomId)){
				listAll=addLst(listAll,alarmRecordService.queryMyAlarmRecord(start, end,hearingRoomId,String.valueOf(ConstantBean.YJLX_SXSSB)));
			}
			if(gridId!=null&&!"".equals(gridId)){
				listAll=addLst(listAll,alarmRecordService.queryMyAlarmRecord(start, end,gridId,String.valueOf(ConstantBean.YJLX_ZNFX)));
			}
		} catch (DahuaException e) {
			LOGGER.debug(e.getMessage(), e);
			String msg = e.getMessage();
			resultMap.put("alertMsg", msg);//捕获异常
			return SUCCESS;
		}
		
		List<WarningMessageBean> beanList=changeAlertMessageBean(listAll,place);
//		for(int i=0;i<10;i++){
//			WarningMessageBean bean=new WarningMessageBean();
//			bean.setWarningContent("我是内容:"+i);
//			bean.setWarningPlace("我是位置"+i);
//			bean.setWarningTime(new Date().getTime());
//			beanList.add(bean);
//		}
		resultMap.put("mesList", beanList);
		resultMap.put("totalNum",beanList.size());
		return  SUCCESS;
	}
	
	/**
	 * 添加list
	 * @param lst1
	 * @param lst2
	 * @return
	 */
	private List<AlarmInfoBean> addLst(List<AlarmInfoBean> lst1,List<AlarmInfoBean> lst2){
		for(int i=0;i<lst2.size();i++){
			lst1.add(lst2.get(i));
		}
		return lst1;
	}
	

	/**
	 * 查询超时预警消息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String findTimeOutWarningMessageByPage(){
		Map<String,Object> map=JSONObject.fromObject(queryStr);
		String code= String.valueOf(map.get("harCode"));
		List<AlertMessage> lst= alertMessageService.findAlertMessagesByReceiptCode(code);
		List<WarningMessageBean> beanList=changeTimeOutBean(lst);
//		List<WarningMessageBean> beanList=new ArrayList<WarningMessageBean>();
//		for(int i=0;i<10;i++){
//			WarningMessageBean bean=new WarningMessageBean();
//			bean.setWarningContent("我是内容:"+i);
//			bean.setWarningPlace("我是位置"+i);
//			bean.setWarningTime(new Date().getTime());
//			beanList.add(bean);
//		}
		resultMap.put("mesList", beanList);
		resultMap.put("totalNum",beanList.size());
		return  SUCCESS;
	}
	
	/**
	 * 超时预警转换bean
	 * @param lst
	 * @return
	 */
	private List<WarningMessageBean> changeTimeOutBean(List<AlertMessage> lst) {
		if(lst==null){
			return null;
		}
		List<WarningMessageBean> lstBean=new ArrayList<WarningMessageBean>();
		for(int i=0;i<lst.size();i++){
			WarningMessageBean bean=new WarningMessageBean();
			bean.setWarningContent(lst.get(i).getContent());
			if(lst.get(i).getCreatedTime()!=null){
				bean.setWarningTime(lst.get(i).getCreatedTime().getTime());
			}
			lstBean.add(bean);
		}
		return lstBean;
	}
	
	/**
	 * 预警消息转换bean
	 * @param ainfoList
	 * @return
	 */
	private List<WarningMessageBean> changeAlertMessageBean(List<AlarmInfoBean> ainfoList,String place) {
		if(ainfoList==null){
			return null;
		}
		List<WarningMessageBean> listbean=new ArrayList<WarningMessageBean>();
		for(int i=0;i<ainfoList.size();i++){
			WarningMessageBean bean=new WarningMessageBean();
			DictionaryItem dic=dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode(Constant.HDJL_YJLX, ainfoList.get(i).getAlarmType(), null);
			if(dic != null){
				bean.setWarningContent(dic.getName());
			}else{
				bean.setWarningContent(ainfoList.get(i).getAlarmType());
			}
			bean.setWarningPlace(ainfoList.get(i).getDeviceName());
			bean.setWarningTimeStr(ainfoList.get(i).getAlarmDate());
			listbean.add(bean);
		}
		return listbean;
	}
	
	/**
	 * 活动记录转换bean
	 * @param listRecord
	 * @return
	 */
	private List<ActivityRecordBean> changeBean(List<ActivityRecord> listRecord) {
		List<ActivityRecordBean> list=new ArrayList<ActivityRecordBean>();
		for(int i=0;i<listRecord.size();i++){
			ActivityRecordBean rec=new ActivityRecordBean();
			if(null != listRecord.get(i).getDescription()){
				rec.setActivityTypeName(listRecord.get(i).getDescription());
			}else{
				rec.setActivityTypeName("");
			}
			rec.setId(listRecord.get(i).getId());
			rec.setRoomId(listRecord.get(i).getGridId());
			rec.setRoomName(listRecord.get(i).getGridName());
			rec.setTdid(listRecord.get(i).getPassageId());
			rec.setSjid(listRecord.get(i).getSupRecordId());
			if(listRecord.get(i).getEndTime()!=null){
				rec.setEndTime(listRecord.get(i).getEndTime().getTime());
			}else{
				rec.setEndTime(new Date().getTime());
			}
			if(listRecord.get(i).getStartTime()!=null){
				rec.setStartTime(listRecord.get(i).getStartTime().getTime());
			}
			if(!StringUtils.isBlank(listRecord.get(i).getOther())){
				List<Attachment> attLst = attachmentCustomizedService.findByTargetIdAndType(listRecord.get(i).getOther(), AskRoomAllocationRecord.class.getName());
				Long max=0L;
				Long time=0L;
				for(int k=0;k<attLst.size();k++){
					String attName = attLst.get(k).getName();
					String fileType = attName.substring(attName.lastIndexOf("."), attName.length());
					if(".doc".equals(fileType)){
						if(attLst.get(k).getUploadingTime()!=null){
							 time=attLst.get(k).getUploadingTime().getTime();
						}
						if(time>max){
							max=time;
							rec.setAttId(attLst.get(k).getId());//添加附件id
						}
					}
				}
			}
			
			if(i==0){
				List<AlertMessage> lst= alertMessageService.findAlertMessagesByReceiptCode(listRecord.get(i).getHandlingAreaReceiptNum());
				rec.setIfOverTimeCount(lst.size());
			}
		
			HandlingAreaReceipt har= handlingAreaReceiptService.findHandlingAreaReceiptByNum(listRecord.get(i).getHandlingAreaReceiptNum());
		    int count=0;
			if(Constant.YJ_WXS.equals(listRecord.get(i).getGridType())){
				String wxsid= askRoomService.findRoomIdByRoomName(listRecord.get(i).getGridName());
				try {
					count+=alarmRecordService.queryMyAlarmRecord(listRecord.get(i).getStartTime(),
							listRecord.get(i).getEndTime(),wxsid,String.valueOf(ConstantBean.YJLX_SXSSB)).size();
				} catch (Exception e) {
					LOGGER.debug(e.getMessage(), e);
					if(e instanceof TransactionSystemException){
						TransactionSystemException tse = (TransactionSystemException) e;
						if (tse.getApplicationException() instanceof DahuaException) {
							String msg = tse.getApplicationException().getMessage();
							resultMap.put("alertMsg", msg);//捕获异常
						}
					}
					return null;
				}
				rec.setHearingRoomId(wxsid);		
			}
			if(listRecord.get(i).getGridId()!=null){
				rec.setRoomId(listRecord.get(i).getGridId());
				try {
					count+=alarmRecordService.queryMyAlarmRecord(listRecord.get(i).getStartTime(),
							listRecord.get(i).getEndTime(),listRecord.get(i).getGridId(),String.valueOf(ConstantBean.YJLX_ZNFX)).size();
				} catch (Exception e) {
					LOGGER.debug(e.getMessage(), e);
			        if(e instanceof TransactionSystemException){
			        	TransactionSystemException tse = (TransactionSystemException) e;
			        	if (tse.getApplicationException() instanceof DahuaException) {
			        		String msg = tse.getApplicationException().getMessage();
			        		resultMap.put("alertMsg", msg);//捕获异常
			        	}
			        }
			        return null;
				}
			}
			if(har!=null){
				if(har.getBasicCase().getBangleCode()!=null){
					rec.setCuffId(har.getBasicCase().getBangleCode());
					try {
						count+=alarmRecordService.queryMyAlarmRecord(listRecord.get(i).getStartTime(),
								listRecord.get(i).getEndTime(),har.getBasicCase().getBangleCode(),String.valueOf(ConstantBean.YJLX_SHBJ)).size();
					} catch (Exception e) {
						LOGGER.debug(e.getMessage(), e);
				        if(e instanceof TransactionSystemException){
				        	TransactionSystemException tse = (TransactionSystemException) e;
				        	if (tse.getApplicationException() instanceof DahuaException) {
				        		String msg = tse.getApplicationException().getMessage();
				        		resultMap.put("alertMsg", msg);//捕获异常
				        	}
				        }
				        return null;
					}
				}
				
			}
			rec.setMesCount(count);
			rec.setFormCode(listRecord.get(i).getHandlingAreaReceiptNum());
			list.add(rec);
		}
		return list;
	}
	

	/**
	 * 查询笔录
	 */
	public String findWordByHarId() {
		Attachment att = attachmentCustomizedService.findById(id);				
		if (att != null) {
//			for(Attachment att : attlst){
				String attName = att.getName();
//				String fileType = attName.substring(attName.lastIndexOf("."), attName.length());
//				if(".doc".equals(fileType)){
					InputStream fromDoc = att.getAttachmentMeta()
							.getAttachmentCopys().get(0).getInputStream();
					String docType = attName.substring(
							attName.lastIndexOf("."), attName.length());
					try {
						setWordName(iWebOffice2000Service.copyDoc(fromDoc, docType));
					} catch (Exception e) {
						setWordName("");
						LOGGER.debug("产生异常：", e);
					}
//				}
//			}
		}
		return SUCCESS;
	}
	
	/**
	 * 格式化日期--开始&结束
	 */
	private String fmtTime(Date dateTime){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(dateTime==null){
			dateTime=new Date();
		}
		String dateString = formatter.format(dateTime);
		return dateString;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isModifyFlag() {
		return modifyFlag;
	}

	public void setModifyFlag(boolean modifyFlag) {
		this.modifyFlag = modifyFlag;
	}

	public List<String> getDeleteIdLst() {
		return deleteIdLst;
	}

	public void setDeleteIdLst(List<String> deleteIdLst) {
		this.deleteIdLst = deleteIdLst;
	}

	public Person getModifyPeople() {
		return modifyPeople;
	}

	public void setModifyPeople(Person modifyPeople) {
		this.modifyPeople = modifyPeople;
	}

	public Long getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Long updatedTime) {
		this.updatedTime = updatedTime;
	}

	public List<ActivityRoomBean> getActivityRoomBeanLst() {
		return activityRoomBeanLst;
	}

	public void setActivityRoomBeanLst(List<ActivityRoomBean> activityRoomBeanLst) {
		this.activityRoomBeanLst = activityRoomBeanLst;
	}

	public String getHaarInfoId() {
		return haarInfoId;
	}

	public void setHaarInfoId(String haarInfoId) {
		this.haarInfoId = haarInfoId;
	}

	public List<String> getDelFileLst() {
		return delFileLst;
	}

	public void setDelFileLst(List<String> delFileLst) {
		this.delFileLst = delFileLst;
	}

	public String getJustShow() {
		return justShow;
	}

	public void setJustShow(String justShow) {
		this.justShow = justShow;
	}

	public boolean isPrintBtnFlag() {
		return printBtnFlag;
	}

	public void setPrintBtnFlag(boolean printBtnFlag) {
		this.printBtnFlag = printBtnFlag;
	}

	public boolean isFinishBtnFlag() {
		return finishBtnFlag;
	}

	public void setFinishBtnFlag(boolean finishBtnFlag) {
		this.finishBtnFlag = finishBtnFlag;
	}

	public boolean isModifyBtnFlag() {
		return modifyBtnFlag;
	}

	public void setModifyBtnFlag(boolean modifyBtnFlag) {
		this.modifyBtnFlag = modifyBtnFlag;
	}

	public String getHarId() {
		return harId;
	}

	public void setHarId(String harId) {
		this.harId = harId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

	public String getQueryStr() {
		return queryStr;
	}

	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}

	public List<ActivityRoomBean> getOtherRoomBeanLst() {
		return otherRoomBeanLst;
	}

	public void setOtherRoomBeanLst(List<ActivityRoomBean> otherRoomBeanLst) {
		this.otherRoomBeanLst = otherRoomBeanLst;
	}

	public String getWordName() {
		return wordName;
	}

	public void setWordName(String wordName) {
		this.wordName = wordName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
