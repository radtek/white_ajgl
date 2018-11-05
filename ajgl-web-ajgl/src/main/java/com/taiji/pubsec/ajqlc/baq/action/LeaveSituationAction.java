package com.taiji.pubsec.ajqlc.baq.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajqlc.acl.CustomizedAclService;
import com.taiji.pubsec.ajqlc.baq.acl.service.BaqAclService;
import com.taiji.pubsec.ajqlc.baq.action.bean.FinallyLeaveInfoBean;
import com.taiji.pubsec.ajqlc.baq.action.bean.PersonBean;
import com.taiji.pubsec.ajqlc.baq.action.bean.TmpLeaveInfoBean;
import com.taiji.pubsec.ajqlc.baq.bean.PoliceInfoBean;
import com.taiji.pubsec.ajqlc.baq.model.ActivityRecord;
import com.taiji.pubsec.ajqlc.baq.model.ActivityRoom;
import com.taiji.pubsec.ajqlc.baq.model.AskRoomAllocationRecord;
import com.taiji.pubsec.ajqlc.baq.model.FinallyLeaveInfo;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceiptToPoliceInfo;
import com.taiji.pubsec.ajqlc.baq.model.OperateRecord;
import com.taiji.pubsec.ajqlc.baq.model.PoliceInfo;
import com.taiji.pubsec.ajqlc.baq.model.TmpLeaveInfo;
import com.taiji.pubsec.ajqlc.baq.service.IActivityRecordService;
import com.taiji.pubsec.ajqlc.baq.service.IAskRoomAllocationRecordService;
import com.taiji.pubsec.ajqlc.baq.service.IAskRoomService;
import com.taiji.pubsec.ajqlc.baq.service.IFinallyLeaveInfoService;
import com.taiji.pubsec.ajqlc.baq.service.IHandlingAreaReceiptService;
import com.taiji.pubsec.ajqlc.baq.service.IOperateRecordService;
import com.taiji.pubsec.ajqlc.baq.service.IPoliceInfoService;
import com.taiji.pubsec.ajqlc.baq.service.ITmpLeaveInfoService;
import com.taiji.pubsec.ajqlc.operationrecord.model.StandardLogRecord;
import com.taiji.pubsec.ajqlc.wrapper.common.bean.ResultBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IEntranceControlService;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.ILocationCardService;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IWristbandService;
import com.taiji.pubsec.common.tool.datetime.DateTimeUtil;
import com.taiji.pubsec.common.tool.spring.BeanCopierFmtUtil;
import com.taiji.pubsec.springsecurity.resource.util.MySecureUser;
import com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil;
import com.taiji.pubsec.springsecurity.util.Constant;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 离开办案区情况
 * 
 * @author huangda
 *
 */
@Controller("leaveSituationAction")
@Scope("prototype")
public class LeaveSituationAction extends ReturnMessageAction {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LeaveSituationAction.class);

	@Resource
	private IFinallyLeaveInfoService finallyLeaveInfoService; // 离开办案区接口

	@Resource
	private IHandlingAreaReceiptService handlingAreaReceiptService;

	@Resource
	private IAskRoomService askRoomService;

	@Resource
	private IAskRoomAllocationRecordService askRoomAllocationRecordService;

	@Resource
	private IPoliceInfoService policeInfoService;
	
	@Resource
	private ITmpLeaveInfoService tmpLeaveInfoService;

	@Resource
	private IOperateRecordService operateRecordService;

	@Resource
	private CustomizedAclService customizedAclService;

	@Resource
	private BaqAclService baqAclService;

	@Resource
	private IWristbandService wristbandService;
	
	@Resource
	private IActivityRecordService activityRecordService;
	
	private FinallyLeaveInfoBean fliBean = new FinallyLeaveInfoBean();

	private List<TmpLeaveInfoBean> tliBeanLst = new ArrayList<TmpLeaveInfoBean>();

	private String id;

	private String type;
	
	private String useRoom;

	private String useRoomId;
	
	private String useRoomStatus;

	private List<String> deleteIdLst = new ArrayList<String>();

	private boolean modifyFlag;

	private String harId;

	private boolean modifyBtnFlag;

	private boolean printBtnFlag;

	private boolean finishBtnFlag;

	private String justShow;
	
	private String errorMessage;
	
	private List<PoliceInfoBean> piBeanLst = new ArrayList<PoliceInfoBean>();
	
	private boolean boundStatusFlag; //解绑状态
	
	private String cuffStatusMessage; //手环挂起状态

	private String bracelet;//是否手环刷卡进来
	
	private String allowModify;
	/**
	 * 手环操作
	 * @return
	 */
	public String braceletControl(){
		HandlingAreaReceipt har = handlingAreaReceiptService.findHandlingAreaReceiptById(id);
		String bangleCode = har.getBasicCase().getBangleCode();
		if("gq".equals(type)){
			ResultBean res;
			try {
				res = wristbandService.suspend(bangleCode);
				this.setFlag(Boolean.toString(res.isResult()));
				
				ActivityRecord bangleTemporaryHang = new ActivityRecord();
				bangleTemporaryHang.setDescription(com.taiji.pubsec.ajqlc.util.Constant.ACTIVITY_STAGE_HANDUP_BANGLE);
				bangleTemporaryHang.setHandlingAreaReceiptNum(har.getBasicCase().getHandlingAreaReceiptNum());
				bangleTemporaryHang.setStartTime(new Date());
				bangleTemporaryHang.setEndTime(new Date());
				activityRecordService.saveOrUpdateRecordOfLeaveRegister(bangleTemporaryHang, com.taiji.pubsec.ajqlc.util.Constant.ACTIVITY_STAGE_DESCRIPTION_LKDJ, har.getBasicCase().getHandlingAreaReceiptNum());
				
				this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "手环临时挂起(使用单ID:"+id+"手环编号:"+bangleCode+"),挂起成功.");
			} catch (DahuaException e) {
				this.setFlag(Boolean.toString(Boolean.FALSE));
				this.errorMessage = e.getMessage();
				this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "手环临时挂起(使用单ID:"+id+"手环编号:"+bangleCode+"),挂起失败,原因:"+e.getMessage());
				LOGGER.debug(e.getMessage());
			}
		}else if("qx".equals(type)){
			ResultBean res;
			try {
				res = wristbandService.resume(bangleCode);
				
				//进入办案区大阶段和取消手环挂起小阶段 
				//进入办案区大阶段
				ActivityRecord firstPhrase  = new ActivityRecord();
				firstPhrase.setDescription(com.taiji.pubsec.ajqlc.util.Constant.ACTIVITY_STAGE_DESCRIPTION_JRBAQ);
				firstPhrase.setHandlingAreaReceiptNum(har.getBasicCase().getHandlingAreaReceiptNum());
				firstPhrase.setStartTime(har.getBasicCase().getEnterIntoTime());
				firstPhrase.setEndTime(new Date());
				activityRecordService.save(firstPhrase);
				
				//手环取消挂起
				ActivityRecord cancelBangleHang = new ActivityRecord();
				cancelBangleHang.setDescription(com.taiji.pubsec.ajqlc.util.Constant.ACTIVITY_STAGE_BIND_BANGLE);
				cancelBangleHang.setHandlingAreaReceiptNum(har.getBasicCase().getHandlingAreaReceiptNum());
				cancelBangleHang.setStartTime(new Date());
				cancelBangleHang.setEndTime(new Date());
				cancelBangleHang.setSupRecordId(firstPhrase.getId());
				firstPhrase.setEndTime(cancelBangleHang.getEndTime());
				activityRecordService.save(cancelBangleHang);
				activityRecordService.updateActivationRecord(firstPhrase);
				
				this.setFlag(Boolean.toString(res.isResult()));
				this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "手环取消挂起(使用单ID:"+id+"手环编号:"+bangleCode+"),取消挂起成功.");
			} catch (DahuaException e) {
				this.setFlag(Boolean.toString(Boolean.FALSE));
				this.errorMessage = e.getMessage();
				LOGGER.debug(e.getMessage());
				this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "手环取消挂起(使用单ID:"+id+"手环编号:"+bangleCode+"),取消挂起失败,原因:"+e.getMessage());
			}
		}else if("jb".equals(type)){
			try {
       	 		ResultBean wriRes = wristbandService.reclaim(har.getBasicCase().getBangleCode(), har.getBasicCase().getHandlingAreaReceiptNum(),har.getModifyPeople().getCode());
       	 		
	       	 	ActivityRecord unbindBangle = new ActivityRecord();
				unbindBangle.setDescription(com.taiji.pubsec.ajqlc.util.Constant.ACTIVITY_STAGE_UNBIND_BANGLE);
				unbindBangle.setHandlingAreaReceiptNum(har.getBasicCase().getHandlingAreaReceiptNum());
				unbindBangle.setStartTime(new Date());
				unbindBangle.setEndTime(new Date());
				activityRecordService.saveOrUpdateRecordOfLeaveRegister(unbindBangle, com.taiji.pubsec.ajqlc.util.Constant.ACTIVITY_STAGE_DESCRIPTION_LKDJ, har.getBasicCase().getHandlingAreaReceiptNum());

       	 		
       	 		this.setFlag(Boolean.toString(wriRes.isResult()));
       	 	this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "解绑手环(使用单ID:"+id+"手环编号:"+bangleCode+"),解绑成功.");
			} catch (DahuaException e) {
				this.setFlag(Boolean.toString(Boolean.FALSE));
				this.errorMessage = e.getMessage();
				LOGGER.debug(e.getMessage());
				this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "解绑手环(使用单ID:"+id+"手环编号:"+bangleCode+"),解绑失败,原因:"+e.getMessage());
			}
		}
		return SUCCESS;
	}
	
	public String queryPoliceCardList(){
		HandlingAreaReceipt har = handlingAreaReceiptService.findHandlingAreaReceiptById(id);
		if(har != null && har.getHandlingAreaReceiptToPoliceInfoList() != null){
			for(HandlingAreaReceiptToPoliceInfo temp : har.getHandlingAreaReceiptToPoliceInfoList()){
				PoliceInfo pi = temp.getPoliceInfo();
				if(StringUtils.isBlank(pi.getIcNum())){
					continue;
				}
				PoliceInfoBean bean = new PoliceInfoBean();
				bean.setId(pi.getId());
				bean.setIcNum(pi.getIcNum());
				bean.setPoliceName(pi.getPoliceName());
				bean.setPoliceNum(pi.getPoliceNum());
				bean.setIfMainPolice(temp.getIfMainPolice());
				bean.setSendCardTime(pi.getSendCardTime().getTime());
				bean.setRemark(pi.getRemark());
				piBeanLst.add(bean);
			}
		}
		
		return SUCCESS;
	}
	/**
	 * 查询手环状态
	 * @return
	 */
	public String queryUnboundStatus(){
		boundStatusFlag=false;
		boundStatusFlag=true;	
		return SUCCESS;
	}
	/**
	 * 查询手环挂起状态
	 * @return
	 */
	public String queryCuffStatus(){
		HandlingAreaReceipt har =handlingAreaReceiptService.findHandlingAreaReceiptById(id);
		ResultBean locRes;
		try{
			locRes= wristbandService.getBraceletInfoByPcId(har.getBasicCase().getBangleCode());
   	 		this.setFlag(Boolean.toString(Boolean.TRUE));
   	 		JSONArray jsonArray = JSONArray.fromObject(locRes.getData());
   	 		if(jsonArray != null && jsonArray.size()>0 && jsonArray.get(0) != null){
   	 			JSONObject jso = JSONObject.fromObject(jsonArray.get(0));
   	 			String bStatus = jso.get("isFreeze").toString();
   	 			this.setCuffStatusMessage(bStatus);
   	 		}else{
	   	 		this.setFlag(Boolean.toString(Boolean.FALSE));
				this.errorMessage = "操作失败！";
				LOGGER.error("手环状态数据解析操作失败！");
   	 		}
		} catch (DahuaException e) {
			this.setFlag(Boolean.toString(Boolean.FALSE));
			this.errorMessage = e.getMessage();
			LOGGER.error(e.getMessage());
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String showLeaveSituation() {
		MySecureUser user = SessionUserDetailsUtil.getMySecureUser();
		Map<String, Object> userMap = user.getUserMap();
		if ("true".equals(justShow)) {
			setModifyBtnFlag(false);
			setPrintBtnFlag(false);
			setFinishBtnFlag(false);
			return SUCCESS;
		}
		HandlingAreaReceipt har = handlingAreaReceiptService
				.findHandlingAreaReceiptById(harId);
		if (com.taiji.pubsec.ajqlc.util.Constant.SYDZT_YWC.equals(har
				.getStatus())) {
			setFinishBtnFlag(false);
			setModifyBtnFlag(false);
			setPrintBtnFlag(true);
		}else if(har.getFinallyLeaveInfo() != null && har.getFinallyLeaveInfo().getFinallyLeaveTime() != null){
			setPrintBtnFlag(false);
			setModifyBtnFlag(false);
			setFinishBtnFlag(true);
		}else{
			//TODO SessionUserDetailsUtil.isResourceAccess("/handlingAreaReceipt/*");
			setModifyBtnFlag(true);
			setFinishBtnFlag(true);
			setPrintBtnFlag(false);
		}
		List<Map<String, String>> roles = (List<Map<String, String>>) userMap
				.get("roles");
		for (Map<String, String> role : roles) {
			if (SUPER_ROLE_CODE.equals(role.get("code"))) {
				setModifyBtnFlag(true);
				break;
			}
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "id="+id);
		return SUCCESS;
	}
	
	
	public String showUpdateLeaveSituation() {
		if ("used".equals(bracelet)) {
			HandlingAreaReceipt har = handlingAreaReceiptService
					.findHandlingAreaReceiptById(harId);
			if(har.getFinallyLeaveInfo() != null && har.getFinallyLeaveInfo().getFinallyLeaveTime() != null){
				setModifyBtnFlag(false);
				return SUCCESS;
			}
			setModifyBtnFlag(true);
			return SUCCESS;
		}
		setModifyBtnFlag(true);
		return SUCCESS;
	}

	private boolean acquirePermissions(TmpLeaveInfo obj) {
		MySecureUser user = SessionUserDetailsUtil.getMySecureUser();
		Map<String, Object> userMap = user.getUserMap();
		@SuppressWarnings("unchecked")
		List<String> authorities = (List<String>) userMap
				.get("authorityCodes");
		for (String auth : authorities) {
			for(String AdminAuth:com.taiji.pubsec.ajqlc.baq.acl.Constant.LEAVE_INFO_ADMIN_AUTH){
				if(auth.equals(AdminAuth)){
					return true;
				}
			}
		}
		
		List<String> permissionNames = customizedAclService
				.findBasePermissionNamesByCurrentUserAndAllPrincipal(obj);
		if (permissionNames
				.contains(Constant.BasePermissionNames.ADMINISTRATION.name())
				|| permissionNames.contains(Constant.BasePermissionNames.WRITE
						.name())) {
			return true;
		}
		return false;
	}

	private boolean acquirePermissions(FinallyLeaveInfo obj) {
		List<String> permissionNames = baqAclService
				.findFinallyLeaveInfoBasePermissionNamesByCurrentUserAndAllPrincipal(obj);
		if (permissionNames.isEmpty()
				|| permissionNames
						.contains(Constant.BasePermissionNames.ADMINISTRATION
								.name())
				|| permissionNames.contains(Constant.BasePermissionNames.WRITE
						.name())) {
			return true;
		}
		return false;
	}

	/**
	 * 查询离开信息
	 * 
	 * @return
	 */
	public String queryLeaveSituationList() {
		HandlingAreaReceipt har = handlingAreaReceiptService
				.findHandlingAreaReceiptById(id);
		if (har == null) {
			return SUCCESS;
		}
		FinallyLeaveInfo fli = har.getFinallyLeaveInfo();
		if (fli == null) {
			fli = new FinallyLeaveInfo();
		}
		List<TmpLeaveInfo> tliLst = fli.getTmpLeaveInfos();
		if (tliLst != null) {
			for (TmpLeaveInfo tli : tliLst) {
				tliBeanLst.add(tmpLeaveInfoToBean(tli));
			}
		}
		fliBean = finallyLeaveInfoToBean(fli);
		ActivityRoom ar = askRoomAllocationRecordService
				.findAskRoomByHandlingAreaReceiptId(id);
		if (ar != null) {
			useRoom = ar.getName();
			useRoomId = ar.getId();
			useRoomStatus = ar.getStatus();
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "id="+id);
		this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "打开维护离开办案区情况页面(使用单ID:"+id+")");
		return SUCCESS;
	}

	/**
	 * 保存修改离开信息
	 * 
	 * @return
	 */
	public String saveorUpdateLeaveSituation() {
		FinallyLeaveInfo fli = beanToFinallyLeaveInfo(fliBean);
		List<TmpLeaveInfo> tliLst = new ArrayList<TmpLeaveInfo>();
		for (TmpLeaveInfoBean tliBean : tliBeanLst) {
			tliLst.add(beanToTmpLeaveInfo(tliBean));
		}
		FinallyLeaveInfo fli2 = finallyLeaveInfoService.findFinallyLeaveInfoByHandlingAreaReceiptId(fli.getHandlingAreaReceipt().getId());
		boolean hasFinallyLeave = false;
		if(fli2 != null && fli2.getFinallyLeaveTime() != null){
			hasFinallyLeave = true;
		}
		HandlingAreaReceipt har2 = handlingAreaReceiptService
				.findHandlingAreaReceiptById(fli.getHandlingAreaReceipt()
						.getId());
		
		int sizeOld = 0;
		int sizeNew = 0;
		if(fli2 != null && fli2.getTmpLeaveInfos() != null){
			sizeOld = fli2.getTmpLeaveInfos().size();
		}
		if(tliLst != null){
			sizeNew = tliLst.size();
		}
		if(sizeOld < sizeNew){
			ActivityRecord temporaryLeave = new ActivityRecord();
//			ActivityRecord registerLeave = activityRecordService.findActivityRecordsByHarCodeAndBigStageDescription(har2.getBasicCase().getHandlingAreaReceiptNum(), com.taiji.pubsec.ajqlc.util.Constant.ACTIVITY_STAGE_DESCRIPTION_LKDJ);
			temporaryLeave.setDescription(com.taiji.pubsec.ajqlc.util.Constant.ACTIVITY_STAGE_TEMP_LSLK);
			temporaryLeave.setHandlingAreaReceiptNum(har2.getBasicCase().getHandlingAreaReceiptNum());
			temporaryLeave.setStartTime(new Date());
			temporaryLeave.setEndTime(new Date());
			activityRecordService.saveOrUpdateRecordOfLeaveRegister(temporaryLeave, com.taiji.pubsec.ajqlc.util.Constant.ACTIVITY_STAGE_DESCRIPTION_LKDJ, har2.getBasicCase().getHandlingAreaReceiptNum());
			
		}
		if (modifyFlag) {
			try{
				finallyLeaveInfoService.updateFinallyLeaveInfo(fli, tliLst, deleteIdLst);
			}catch(DahuaException e){
			        String msg = e.getMessage();
			        this.errorMessage = msg;
					return SUCCESS;
			}
			HandlingAreaReceipt har = handlingAreaReceiptService
					.findHandlingAreaReceiptById(fli.getHandlingAreaReceipt()
							.getId());
			if (har != null) {
				FinallyLeaveInfo f = har.getFinallyLeaveInfo();
				if (f != null) {
					this.setModifyPerson(f.getModifyPeople().getName());
					this.setModifyTime(DateTimeUtil.formatDateTime(
							f.getUpdatedTime(), "yyyy-MM-dd HH:mm"));
				}
			}
		} else {
			try{
				if(null != fli.getFinallyLeaveTime() && !hasFinallyLeave){
					ActivityRecord lastLeave = new ActivityRecord();
//					ActivityRecord registerLeave = activityRecordService.findActivityRecordsByHarCodeAndBigStageDescription(har2.getBasicCase().getHandlingAreaReceiptNum(), com.taiji.pubsec.ajqlc.util.Constant.ACTIVITY_STAGE_DESCRIPTION_LKDJ);
					lastLeave.setDescription(com.taiji.pubsec.ajqlc.util.Constant.ACTIVITY_STAGE_LAST_ZZLK);
					lastLeave.setHandlingAreaReceiptNum(har2.getBasicCase().getHandlingAreaReceiptNum());
					lastLeave.setStartTime(new Date());
					lastLeave.setEndTime(new Date());
					activityRecordService.saveOrUpdateRecordOfLeaveRegister(lastLeave, com.taiji.pubsec.ajqlc.util.Constant.ACTIVITY_STAGE_DESCRIPTION_LKDJ, har2.getBasicCase().getHandlingAreaReceiptNum());

				}
				finallyLeaveInfoService.saveFinallyLeaveInfo(fli, tliLst);
			}catch(DahuaException e){
			        String msg = e.getMessage();
			        this.errorMessage = msg;
					return SUCCESS;
			}
			this.setModifyPerson(fli.getModifyPeople().getName());
			this.setModifyTime(DateTimeUtil.formatDateTime(
					fli.getUpdatedTime(), "yyyy-MM-dd HH:mm"));
		}
		OperateRecord operateRecord = new OperateRecord();
		operateRecord.setModelObject(HandlingAreaReceipt.class.getName());
		operateRecord.setModelObjectId(fli.getHandlingAreaReceipt().getId());
		operateRecord.setNoteText("");
		operateRecord.setNoteType("0");
		operateRecord.setOperateContent("维护离开记录");
		operateRecord.setOperateTime(new Date());
		operateRecord.setOperator(this.findCurrentPerson().getName());
		operateRecordService.saveOperateRecord(operateRecord);
		
		this.setFlag("true");
		this.createStandardLog(StandardLogRecord.OPERATETYPE_UPDATE, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "保存维护人身检查记录页面(使用单ID:"+fliBean.getHandlingAreaReceiptId()+")");
		return SUCCESS;
	}

	private FinallyLeaveInfoBean finallyLeaveInfoToBean(FinallyLeaveInfo fli) {
		if (fli == null) {
			return null;
		}
		FinallyLeaveInfoBean bean = new FinallyLeaveInfoBean();
		try {
			BeanCopierFmtUtil.copyBean(fli, bean, null);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		if (!StringUtils.isBlank(fli.getId())) {
			bean.setChangePermissions(acquirePermissions(fli));
		} else {
			bean.setChangePermissions(true);
		}
		if (fli.getModifyPeople() != null) {
			PersonBean pBean = new PersonBean();
			pBean.setId(fli.getModifyPeople().getId());
			pBean.setCode(fli.getModifyPeople().getCode());
			pBean.setName(fli.getModifyPeople().getName());
			bean.setModifyPeopleBean(pBean);
		}
		return bean;
	}

	private FinallyLeaveInfo beanToFinallyLeaveInfo(FinallyLeaveInfoBean bean) {
		if (bean == null) {
			return null;
		}
		FinallyLeaveInfo fli = new FinallyLeaveInfo();
		fli.setHandlingAreaReceipt(handlingAreaReceiptService
				.findHandlingAreaReceiptById(bean.getHandlingAreaReceiptId()));
		if (!StringUtils.isBlank(bean.getId())) {
			fli = finallyLeaveInfoService
					.findFinallyLeaveInfoById(bean.getId());
		}
		try {
			BeanCopierFmtUtil.copyBean(bean, fli, null);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		if (bean.getAskRoom() != null
				&& !StringUtils.isEmpty(bean.getAskRoom().getId())) {
			fli.setAskRoom(askRoomService.findAskRoomById(bean.getAskRoom()
					.getId()));
		}
		return fli;
	}

	private TmpLeaveInfoBean tmpLeaveInfoToBean(TmpLeaveInfo tli) {
		if (tli == null) {
			return null;
		}
		TmpLeaveInfoBean bean = new TmpLeaveInfoBean();
		try {
			BeanCopierFmtUtil.copyBean(tli, bean, null);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		//if (!StringUtils.isBlank(tli.getId())) {
		//	bean.setChangePermissions(acquirePermissions(tli));
		//} else {
			bean.setChangePermissions(true);
		//}
		return bean;
	}

	private TmpLeaveInfo beanToTmpLeaveInfo(TmpLeaveInfoBean bean) {
		if (bean == null) {
			return null;
		}
		TmpLeaveInfo tli = new TmpLeaveInfo();
		if (!StringUtils.isBlank(bean.getId())) {
			tli = tmpLeaveInfoService.findById(bean.getId());
		}
		try {
			BeanCopierFmtUtil.copyBean(bean, tli, null);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		if (bean.getAskRoom() != null
				&& !StringUtils.isEmpty(bean.getAskRoom().getId())) {
			tli.setAskRoom(askRoomService.findAskRoomById(bean.getAskRoom()
					.getId()));
		}
		return tli;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public FinallyLeaveInfoBean getFliBean() {
		return fliBean;
	}

	public void setFliBean(FinallyLeaveInfoBean fliBean) {
		this.fliBean = fliBean;
	}

	public List<TmpLeaveInfoBean> getTliBeanLst() {
		return tliBeanLst;
	}

	public void setTliBeanLst(List<TmpLeaveInfoBean> tliBeanLst) {
		this.tliBeanLst = tliBeanLst;
	}

	public boolean isModifyFlag() {
		return modifyFlag;
	}

	public void setModifyFlag(boolean modifyFlag) {
		this.modifyFlag = modifyFlag;
	}

	public String getUseRoom() {
		return useRoom;
	}

	public void setUseRoom(String useRoom) {
		this.useRoom = useRoom;
	}

	public String getUseRoomId() {
		return useRoomId;
	}

	public void setUseRoomId(String useRoomId) {
		this.useRoomId = useRoomId;
	}

	public List<String> getDeleteIdLst() {
		return deleteIdLst;
	}

	public void setDeleteIdLst(List<String> deleteIdLst) {
		this.deleteIdLst = deleteIdLst;
	}

	public static void main(String[] args) {
		System.out
				.println(com.taiji.pubsec.springsecurity.util.Constant.BasePermissionNames.ADMINISTRATION
						.getAllJSONStr());
	}

	public String getHarId() {
		return harId;
	}

	public void setHarId(String harId) {
		this.harId = harId;
	}

	public boolean isModifyBtnFlag() {
		return modifyBtnFlag;
	}

	public void setModifyBtnFlag(boolean modifyBtnFlag) {
		this.modifyBtnFlag = modifyBtnFlag;
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

	public String getJustShow() {
		return justShow;
	}

	public void setJustShow(String justShow) {
		this.justShow = justShow;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<PoliceInfoBean> getPiBeanLst() {
		return piBeanLst;
	}

	public void setPiBeanLst(List<PoliceInfoBean> piBeanLst) {
		this.piBeanLst = piBeanLst;
	}

	public boolean isBoundStatusFlag() {
		return boundStatusFlag;
	}

	public void setBoundStatusFlag(boolean boundStatusFlag) {
		this.boundStatusFlag = boundStatusFlag;
	}

	public String getCuffStatusMessage() {
		return cuffStatusMessage;
	}

	public void setCuffStatusMessage(String cuffStatusMessage) {
		this.cuffStatusMessage = cuffStatusMessage;
	}

	public String getUseRoomStatus() {
		return useRoomStatus;
	}

	public void setUseRoomStatus(String useRoomStatus) {
		this.useRoomStatus = useRoomStatus;
	}

	public String getBracelet() {
		return bracelet;
	}

	public void setBracelet(String bracelet) {
		this.bracelet = bracelet;
	}

	public String getAllowModify() {
		return allowModify;
	}

	public void setAllowModify(String allowModify) {
		this.allowModify = allowModify;
	}
	
	
}
