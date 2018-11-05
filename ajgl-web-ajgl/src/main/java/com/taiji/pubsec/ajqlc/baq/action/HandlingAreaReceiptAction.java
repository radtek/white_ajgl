package com.taiji.pubsec.ajqlc.baq.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajgl.dbtx.task.util.entercasearea.pojo.EnterCaseAreaData;
import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajgl.util.bean.FileObjectBean;
import com.taiji.pubsec.ajgl.util.bean.OptionItemBean;
import com.taiji.pubsec.ajqlc.alert.model.AlertRule;
import com.taiji.pubsec.ajqlc.alert.service.IAlertRuleService;
import com.taiji.pubsec.ajqlc.attachment.model.Attachment;
import com.taiji.pubsec.ajqlc.attachment.service.IAttachmentCustomizedService;
import com.taiji.pubsec.ajqlc.baq.action.bean.BasicCaseBean;
import com.taiji.pubsec.ajqlc.baq.action.bean.HarSearchBean;
import com.taiji.pubsec.ajqlc.baq.action.bean.PersonBean;
import com.taiji.pubsec.ajqlc.baq.bean.PoliceInfoBean;
import com.taiji.pubsec.ajqlc.baq.model.ActivityRecord;
import com.taiji.pubsec.ajqlc.baq.model.ActivityRoom;
import com.taiji.pubsec.ajqlc.baq.model.AskRoomAllocationRecord;
import com.taiji.pubsec.ajqlc.baq.model.BasicCase;
import com.taiji.pubsec.ajqlc.baq.model.CarryGoodsRecord;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceiptToPoliceInfo;
import com.taiji.pubsec.ajqlc.baq.model.MachineStageRelation;
import com.taiji.pubsec.ajqlc.baq.model.OperateRecord;
import com.taiji.pubsec.ajqlc.baq.model.PoliceInfo;
import com.taiji.pubsec.ajqlc.baq.service.IActivityRecordService;
import com.taiji.pubsec.ajqlc.baq.service.IAskRoomAllocationRecordService;
import com.taiji.pubsec.ajqlc.baq.service.IAskRoomService;
import com.taiji.pubsec.ajqlc.baq.service.IBasicCaseService;
import com.taiji.pubsec.ajqlc.baq.service.IHandlingAreaReceiptService;
import com.taiji.pubsec.ajqlc.baq.service.IMachineStageRelationService;
import com.taiji.pubsec.ajqlc.baq.service.IOperateRecordService;
import com.taiji.pubsec.ajqlc.baq.service.IPoliceInfoService;
import com.taiji.pubsec.ajqlc.operationrecord.model.StandardLogRecord;
import com.taiji.pubsec.ajqlc.sla.model.CriminalBasicCase;
import com.taiji.pubsec.ajqlc.sla.service.ICriminalCaseService;
import com.taiji.pubsec.ajqlc.util.Constant;
import com.taiji.pubsec.ajqlc.wrapper.common.bean.ResultBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IEntranceControlService;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.ILocationCardService;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.wirstrollback.ErrorLocationCard;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.wirstrollback.ErrorRollbackDataMap;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.wirstrollback.ErrorWrist;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.wirstrollback.RollBackProcessor;
import com.taiji.pubsec.ajqlc.wrapper.jzpt.service.SuspectService;
import com.taiji.pubsec.ajqlc.wrapper.jzpt.service.bean.BasicPersonBean;
import com.taiji.pubsec.ajqlc.wrapper.jzpt.service.bean.CriminalRecordBean;
import com.taiji.pubsec.ajqlc.wrapper.szpt.exception.SzptException;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryItem;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.businesscomponent.organization.model.Account;
import com.taiji.pubsec.businesscomponent.organization.model.Person;
import com.taiji.pubsec.businesscomponent.organization.model.Unit;
import com.taiji.pubsec.businesscomponent.organization.service.IAccountService;
import com.taiji.pubsec.businesscomponent.organization.service.IPersonService;
import com.taiji.pubsec.businesscomponent.organization.service.IUnitService;
import com.taiji.pubsec.common.tool.base64.Base64CryptFmtUtil;
import com.taiji.pubsec.common.tool.datetime.DateTimeUtil;
import com.taiji.pubsec.common.tool.mission.client.core.TaskClient;
import com.taiji.pubsec.common.tool.mission.task.util.TaskPojoJSONFmtUtil;
import com.taiji.pubsec.common.tool.spring.BeanCopierFmtUtil;
import com.taiji.pubsec.common.tool.spring.web.SpringWebContextUtil;
import com.taiji.pubsec.springsecurity.resource.util.MySecureUser;
import com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil;

/**
 * 办案区使用单Action
 * 
 * @author huangda
 *
 */
@Controller("handlingAreaReceiptAction")
@Scope("prototype")
public class HandlingAreaReceiptAction extends ReturnMessageAction {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(HandlingAreaReceiptAction.class);
	
	private static final String WARNING_STATUS_START = "0000000002002"; //预警规则状态：启用
	
	private static final String SBZT_SSWPFH_BZ = "随身物品日常管理"; //设备阶段关系表 状态 备注
	
	@Resource
	private IAskRoomService askRoomService;
	@Resource
	private IHandlingAreaReceiptService handlingAreaReceiptService;
	@Resource
	private IBasicCaseService basicCaseService;
	@Resource
	private IDictionaryItemService dictionaryItemService;
	@Resource
	private IAttachmentCustomizedService attachmentCustomizedService;
	@Resource
	private IOperateRecordService operateRecordService;
	@Resource	//问询室分配服务接口
	private IAskRoomAllocationRecordService askRoomAllocationRecordService;
	@Resource(name="dbtxTaskClient")
	private TaskClient taskClient;
	@Resource
	private IAccountService accountService;
	@Resource	// 预警规则接口
	private IAlertRuleService alertRuleService;
	@Resource
	private ICriminalCaseService criminalCaseService;
	@Resource
	private SuspectService suspectService;
	@Resource
	private IPoliceInfoService policeInfoService;
	@Resource
	private IMachineStageRelationService machineStageRelationService;
	@Resource
	private IPersonService personService;
	@Resource
	private IUnitService unitService;
	@Resource
	private IActivityRecordService activityRecordService;
	@Resource
	private ErrorRollbackDataMap errorRollbackDataMap;
	@Resource
	private RollBackProcessor rollBackProcessor;
	@Resource
	private ILocationCardService locationCardService;
	@Resource
	private IEntranceControlService entranceControlService;
	
	private List<OptionItemBean> roomLst = new ArrayList<OptionItemBean>();
	private HarSearchBean harSearchBean = new HarSearchBean();
	private List<HarSearchBean> harSearchBeanlst = new ArrayList<HarSearchBean>();
	private String id;
	private String code;
	private String userName;
	private BasicCaseBean basicCaseBean = new BasicCaseBean();
	private List<PoliceInfoBean> policeInfoBeanLst = new ArrayList<PoliceInfoBean>();
	private PoliceInfoBean piBean = new PoliceInfoBean();
	private List<String> deletePoliceId = new ArrayList<String>();
	private String photo;
	private String deleteFileId;
	private boolean fullSearch;
	private String caseCode;
	private String policeModifyFlag;
	private BasicPersonBean person;
	private List<CriminalRecordBean> criminalRecordLst = new ArrayList<CriminalRecordBean>();
	private String harId;
	private String policeNum;
	
	private boolean modifyBtnFlag;
	
	private boolean printBtnFlag;
	
	private boolean finishBtnFlag;
	
	private String justShow;
	
	private String cardId;
	private String machineId;
	private String url;
	private List<String> icoLst = new ArrayList<String>();
	private String errorMessage;
	

	/**
	 * 手环触发跳转方法
	 * @return
	 */
	public String findUrl(){
		//接口1通过machineId查询刷卡机对应网页，如为物品那屋的在用cardId判断设备类型确定是保存是返还
		
		//下面是对应的网页，根据返回结果设置tempUrl
		String tempUrl = "";
		String harId = "";
		String remark="";
		List<MachineStageRelation> relations = machineStageRelationService.findMachineStageRelationsByMachineId(machineId);
		if(!StringUtils.isBlank(id)){//使用单id不为空时说明确定为某使用单的卡跳转
			List<String> harIdLst = handlingAreaReceiptService.findHandlingAreaReceiptIdByIcNum(cardId);//TODO 修改为多对多后这里返回IdLst
			harId = harIdLst.get(0);
			for(MachineStageRelation temp : relations){
				if(temp.getType().equals("card")){
					tempUrl = temp.getStageUrl();
					remark = temp.getRemark();
					break;
				}
			}
		}else{
			//如果是手环则按手环跳转
			harId = handlingAreaReceiptService.findHandlingAreaReceiptIdByBangleCode(cardId);
			if(harId != null){//是手环则跳转
				for(MachineStageRelation temp : relations){
					if(temp.getType().equals("bangle")){
						tempUrl = temp.getStageUrl();
						remark = temp.getRemark();
						break;
					}
				}
			}
		}
		//登记返回"/leaveSituation/showUpdateLeaveSituation.action?&&harId="	手环	
		//人身检查"/personCheckRecord/showNewOrUpdatePersonCheckRecordPage.action?&&harId=" 手环
		//信息采集"/collectInfoSituation/showNewOrUpdateCollectInfoSituationPage.action?&&harId=" 手环
		//随身物品保存"/carryGoodsInfo/shoNewOrUpdateCarryGoodsInfo.action?&&harId="手环
		//活动记录（讯问室分配）"/activityRecord/showUpdateActivityRecord.action?&&harId=" 手环
		//随身物品返还"/carryGoodsInfo/showUpdateCarryGoodsReturnRecord.action?&&harId="卡
		//登记离开"/leaveSituation/showUpdateLeaveSituation.action?&&harId=" 手环
		
		//接口2通过cardId查询【使用中】的使用单，可能是手环或者卡(使用单里关联手环编号，使用单办案民警信息里关联ic卡号)
		if(StringUtils.isBlank(harId)){
			//如果未找到使用单，返回""
			url = "";
		}else{
			if(SBZT_SSWPFH_BZ.equals(remark)){
				HandlingAreaReceipt har=handlingAreaReceiptService.findHandlingAreaReceiptById(harId);
				Set<HandlingAreaReceiptToPoliceInfo> list= har.getHandlingAreaReceiptToPoliceInfoList();
				String policeName="";
				for(HandlingAreaReceiptToPoliceInfo temp : list){
					if(cardId.equals(temp.getPoliceInfo().getIcNum())){
						policeName=temp.getPoliceInfo().getPoliceName();
					}
				}
				String pn = "";
				try {
					pn = Base64CryptFmtUtil.encode(policeName.getBytes(), true);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				url = tempUrl + harId+"&&policeName="+ pn;
			}else{
				//返回拼接好的网址
				url = tempUrl + harId;
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 选择使用单
	 * @return
	 */
	public String selectHandlingAreaReceipt(){
		
		List<String> harIdLst = handlingAreaReceiptService.findHandlingAreaReceiptIdByIcNum(cardId);
		for (String harId : harIdLst) {
			harSearchBeanlst.add(handlingAreaReceiptToHarSearchBean(handlingAreaReceiptService.findHandlingAreaReceiptById(harId)));
		}
		
		return SUCCESS;
	}
	
	
	public String findAllIco(){
		HandlingAreaReceipt har = handlingAreaReceiptService.findHandlingAreaReceiptById(harId);
		if(har.getBasicCase() != null && !StringUtils.isBlank(har.getBasicCase().getId())){
			icoLst.add("done");
		}else{
			icoLst.add("not");
		}
		if(har.getPersonCheckRecord() != null && !StringUtils.isBlank(har.getPersonCheckRecord().getId())){
			icoLst.add("done");
		}else{
			icoLst.add("not");
		}
		if(har.getCollectInfoSituation() != null && !StringUtils.isBlank(har.getCollectInfoSituation().getId())){
			icoLst.add("done");
		}else{
			icoLst.add("not");
		}
		if(har.getCarryGoodsInfo() != null && !StringUtils.isBlank(har.getCarryGoodsInfo().getId())){
			icoLst.add("done");
		}else{
			icoLst.add("not");
		}
		boolean returnIcoflag = false;
		if(har.getCarryGoodsInfo() != null && har.getCarryGoodsInfo().getCarryGoodsRecords() != null){
			for(CarryGoodsRecord cgr : har.getCarryGoodsInfo().getCarryGoodsRecords()){
				if(cgr.getCarryGoodsReturnRecordList() != null && cgr.getCarryGoodsReturnRecordList().size() > 0){
					returnIcoflag = true;
					break;
				}
			}
		}
		if(returnIcoflag){
			icoLst.add("done");
		}else{
			icoLst.add("not");
		}
		if(har.getFinallyLeaveInfo() != null && !StringUtils.isBlank(har.getFinallyLeaveInfo().getId())){
			icoLst.add("done");
		}else{
			icoLst.add("not");
		}
		return SUCCESS;
	}
	
	public String findPoliceInfoByPoliceCode(){
		PoliceInfo pi = policeInfoService.findByPoliceCode(code);
		if(pi != null){
			piBean = new PoliceInfoBean();
			BeanCopierFmtUtil.copyBean(pi, piBean, null);
			piBean.setSendCardTime(pi.getSendCardTime().getTime());
			piBean.setSendCardPeopleCode(pi.getSendCardPeople().getCode());
			piBean.setSendCardPeopleName(pi.getSendCardPeople().getName());
		}
		return SUCCESS;
	}
	
	public String findPoliceByBrandId(){
		PoliceInfo pi = policeInfoService.findByCardId(cardId);
		if(pi != null){
			piBean = new PoliceInfoBean();
			BeanCopierFmtUtil.copyBean(pi, piBean, null);
			piBean.setSendCardTime(pi.getSendCardTime().getTime());
			piBean.setSendCardPeopleCode(pi.getSendCardPeople().getCode());
			piBean.setSendCardPeopleName(pi.getSendCardPeople().getName());
		}
		return SUCCESS;
	}
	
	public String showHandlingAreaReceiptListPage(){
//		HttpSession session = SpringWebContextUtil.getSession();
//		Boolean b = (Boolean)session.getAttribute("fullSearch");
//		fullSearch = (b == null)? false : b;
//		harSearchBean = (HarSearchBean)session.getAttribute("harSearchBean");
		return SUCCESS;
	}
	@SuppressWarnings("unchecked")
	public String showLookHandlingAreaReceiptPage(){
		MySecureUser user = SessionUserDetailsUtil.getMySecureUser() ;
		Map<String, Object> userMap = user.getUserMap() ;
		Map<String, String> mPerson = new HashMap<String, String>(0) ;
		if("true".equals(justShow)){
			setModifyBtnFlag(false);
			setPrintBtnFlag(false);
			setFinishBtnFlag(false);
			return SUCCESS;
		}
		HandlingAreaReceipt har = handlingAreaReceiptService
				.findHandlingAreaReceiptById(harId);
		if(Constant.SYDZT_YWC.equals(har.getStatus())){
			setPrintBtnFlag(true);
			setFinishBtnFlag(false);
			setModifyBtnFlag(false);
		}else{
			setPrintBtnFlag(false);
			BasicCase bc = har.getBasicCase();
			mPerson = (Map<String, String>)userMap.get("person");
			if(bc !=null && !mPerson.get("code").equals(bc.getModifyPeople().getCode())){
				setModifyBtnFlag(false);
			}else{
				//TODO SessionUserDetailsUtil.isResourceAccess("/handlingAreaReceipt/*");
				setModifyBtnFlag(true);
			}
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
		this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单","查看使用单详情(使用单ID:"+harId+")");
		return SUCCESS;
	}
	
	private HarSearchBean handlingAreaReceiptToHarSearchBean(HandlingAreaReceipt har) {
		if (har == null) {
			return null;
		}
		HarSearchBean bean = new HarSearchBean();
		bean.setId(har.getId());
		bean.setState(har.getStatus());
		List<Attachment> attLst = attachmentCustomizedService.findByTargetIdAndType(har.getId(), HandlingAreaReceipt.class.getName());
		for(Attachment att : attLst){
			if(att.getAttachmentMeta().getName().contains(".png")){
				InputStream is = att.getAttachmentMeta().getAttachmentCopys().get(0).getInputStream();
				try {
					byte[] buffer = new byte[is.available()];
					is.read(buffer, 0, is.available());
					bean.setPhoto(new String (Base64.encodeBase64(buffer, false)));
				} catch (Exception e) {
					log.error("照片转换失败");
				}
			}
		}
		BasicCase bc = har.getBasicCase();
		if (bc != null) {
			bean.setCode(bc.getHandlingAreaReceiptNum());
			SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT);
			try {
				bean.setAccessDateStr(sdf.format(bc.getEnterIntoTime()));
			} catch (Exception e) {
				bean.setAccessDateStr("");
				LOGGER.error(e.getMessage(), e);
			}
			bean.setPolice(bc.getHandlingPolice());
			Person p = bc.getModifyPeople();
			if(p != null){
				bean.setProposer(p.getName());
			}
			if (Constant.AY_QT.equals(bc.getCauseOfLawCase())) {
				bean.setCauseOfLawCaseName(bc.getOtherCauseOfLawCase());
			} else if (bc.getCauseOfLawCase() != null) {
				bean.setCauseOfLawCaseName(dictionaryItemService.findById(bc.getCauseOfLawCase()).getName());
			} else {
				bean.setCauseOfLawCaseName("");
			}
			bean.setEnterAreaReasons(bc.getEnterAreaReasons());
			if(bc.getLawCase() != null){
				CriminalBasicCase cbc = criminalCaseService.findCriminalCaseByCaseId(bc.getLawCase());
				if(cbc != null){
					bean.setAboutCaseName(cbc.getCaseName());
					bean.setAboutCase(bc.getLawCase());
				}else{
					bean.setAboutCase("");
					bean.setAboutCaseName(bc.getLawCase());
				}
			}else{
				bean.setAboutCaseName("");
				bean.setAboutCase("");
			}
			bean.setBangleCode(bc.getBangleCode());
			bean.setAskPerson(bc.getByQuestioningPeopleName());
			bean.setIdNum((bc.getByQuestioningPeopleIdentifyNum() == null)? "": bc.getByQuestioningPeopleIdentifyNum());
		}
		List<AskRoomAllocationRecord> askRoom = askRoomAllocationRecordService.findAskRoomAllocationRecordByHandlingAreaReceiptId(har.getId());
		if(askRoom.isEmpty()){
			return bean;
		}
		AskRoomAllocationRecord aar = askRoom.get(askRoom.size()-1);
		if(aar.getLeaveTime() == null){
			bean.setRoomName(aar.getActivityRoom().getName());
		}
		return bean;
	}
	/**
	 * 初始化页面数据
	 * 
	 * @return
	 */
	public String initDataListPage() {
		List<ActivityRoom> arLst = askRoomService.findAskRoomByType(null);
		for (ActivityRoom ar : arLst) {
			OptionItemBean bean = new OptionItemBean();
			bean.setId(ar.getId());
			bean.setName(ar.getName());
			roomLst.add(bean);
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "unitId="+this.findCurrentOrganization().getId());
		return SUCCESS;
	}

	/**
	 * 初始化页面数据
	 * 
	 * @return
	 */
	public String initDataCreatePage() {
		code = handlingAreaReceiptService.acquireNum();
		setUserName(this.findCurrentPerson().getName());
		return SUCCESS;
	}

	/**
	 * 最终离开check
	 * @return
	 */
	public String finalLeaveCheck(){
		HandlingAreaReceipt har = handlingAreaReceiptService.findHandlingAreaReceiptById(id);
		BasicCase bc = har.getBasicCase();
		if(StringUtils.isBlank(bc.getByQuestioningPeopleIdentifyNum())
				|| StringUtils.isBlank(bc.getByQuestioningPeopleTelphoneNumber())
				|| StringUtils.isBlank(bc.getEnterAreaReasons())
				|| StringUtils.isBlank(bc.getCauseOfLawCase())
				|| (bc.getCauseOfLawCase().equals(Constant.AY_QT) && StringUtils.isBlank(bc.getOtherCauseOfLawCase()))){
			this.setMsg("人员基本情况未填写完整，不能办理离开手续！");
			this.setFlag("base");
			return SUCCESS;
		}
		if(har.getPersonCheckRecord() == null){
			this.setMsg("人身检查记录未填写，不能办理离开手续！");
			this.setFlag("");
			return SUCCESS;
		}
		if(har.getCollectInfoSituation() == null){
			this.setMsg("信息采集情况未填写，不能办理离开手续！");
			this.setFlag("");
			return SUCCESS;
		}
		if(har.getCarryGoodsInfo() != null && har.getCarryGoodsInfo().getCarryGoodsRecords() != null){
			int zk = 0;
			int lsqc = 0;
			for(CarryGoodsRecord cgr : har.getCarryGoodsInfo().getCarryGoodsRecords()){
				if(cgr.getStatus().equals(Constant.WPZT_ZK)){
					zk++;
				}else if(cgr.getStatus().equals(Constant.WPZT_LSQC)){
					lsqc++;
				}
			}
			if(zk != 0 && lsqc != 0){
				this.setMsg("有" + lsqc + "临时取出物品未归还，共" + (zk+lsqc) + "件随身物品未取出，不能办理离开手续！");
				this.setFlag("");
				return SUCCESS;
			}else if(zk != 0){
				this.setMsg("有" + zk + "件随身物品未取出，不能办理离开手续！");
				this.setFlag("");
				return SUCCESS;
			}else if(lsqc != 0){
				this.setMsg("有" + lsqc + "个物品临时取出未归还，不能办理离开手续！");
				this.setFlag("");
				return SUCCESS;
			}
		}
		this.setFlag("true");
		this.createStandardLog(StandardLogRecord.OPERATETYPE_UPDATE, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		return SUCCESS;
	}
	
	
	/**
	 * 完成使用单check
	 * @return
	 */
	public String handlingAreaReceiptCheck(){
		HandlingAreaReceipt har = handlingAreaReceiptService.findHandlingAreaReceiptById(id);
		BasicCase bc = har.getBasicCase();
		if(StringUtils.isBlank(bc.getByQuestioningPeopleIdentifyNum())
				|| StringUtils.isBlank(bc.getByQuestioningPeopleTelphoneNumber())
				|| StringUtils.isBlank(bc.getEnterAreaReasons())
				|| StringUtils.isBlank(bc.getCauseOfLawCase())
				|| (bc.getCauseOfLawCase().equals(Constant.AY_QT) && StringUtils.isBlank(bc.getOtherCauseOfLawCase()))){
			this.setMsg("人员基本情况未填写完整，不能完成本次问讯！");
			this.setFlag("base");
			return SUCCESS;
		}
		if(har.getPersonCheckRecord() == null){
			this.setMsg("人身检查记录未填写，不能完成本次问讯！");
			this.setFlag("");
			return SUCCESS;
		}
		if(har.getCollectInfoSituation() == null){
			this.setMsg("信息采集情况未填写，不能完成本次问讯！");
			this.setFlag("");
			return SUCCESS;
		}
		if(har.getCarryGoodsInfo() != null && har.getCarryGoodsInfo().getCarryGoodsRecords() != null){
			int zk = 0;
			int lsqc = 0;
			for(CarryGoodsRecord cgr : har.getCarryGoodsInfo().getCarryGoodsRecords()){
				if(cgr.getStatus().equals(Constant.WPZT_ZK)){
					zk++;
				}else if(cgr.getStatus().equals(Constant.WPZT_LSQC)){
					lsqc++;
				}
			}
			if(zk != 0 && lsqc != 0){
				this.setMsg("有" + lsqc + "临时取出物品未归还，" + zk + "个在库物品未归还，不能完成本次问讯！");
				this.setFlag("");
				return SUCCESS;
			}else if(zk != 0){
				this.setMsg("有" + zk + "个在库物品未归还，不能完成本次问讯！");
				this.setFlag("");
				return SUCCESS;
			}else if(lsqc != 0){
				this.setMsg("有" + lsqc + "个物品临时取出未归还，不能完成本次问讯！");
				this.setFlag("");
				return SUCCESS;
			}
		}
		if(har.getFinallyLeaveInfo() == null || har.getFinallyLeaveInfo().getFinallyLeaveTime() == null){
			this.setMsg("未办理最终离开手续，不能完成本次问讯！");
			this.setFlag("leave");
			return SUCCESS;
		}
		this.setFlag("true");
		this.createStandardLog(StandardLogRecord.OPERATETYPE_UPDATE, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		return SUCCESS;
	}
	
	/**
	 * 完成使用单
	 * @return
	 */
	public String doneHandlingAreaReceipt(){
		handlingAreaReceiptService.finishHandlingAreaReceipt(id);
		this.setFlag("true");
		this.createStandardLog(StandardLogRecord.OPERATETYPE_UPDATE, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "receiptId="+id);
		return SUCCESS;
	}
	/**
	 * 删除使用单
	 * 
	 * @return
	 */
	public String deleteHandlingAreaReceipt() {
		HandlingAreaReceipt har = handlingAreaReceiptService.findHandlingAreaReceiptById(id);
		if (har == null) {
			return SUCCESS;
		}
		if (har.getCarryGoodsInfo() != null 
				|| har.getCollectInfoSituation() != null
				|| har.getFinallyLeaveInfo() != null 
				|| har.getHandleAreaActivityRecordInfo() != null
				|| har.getPersonCheckRecord() != null) {
			return SUCCESS;
		}
		List<AskRoomAllocationRecord> lst = askRoomAllocationRecordService.findAskRoomAllocationRecordByHandlingAreaReceiptId(har.getId());
		if(lst.size() > 0){
			return SUCCESS;
		}
		String attId = har.getBasicCase().getId();
		handlingAreaReceiptService.deleteHandlingAreaReceipt(id);
		List<Attachment> attLst = attachmentCustomizedService.findByTargetIdAndType(attId, BasicCase.class.getName());
		if (attLst.isEmpty()) {
			this.setFlag("true");
			return SUCCESS;
		}
		attachmentCustomizedService.delete(attLst.get(0));
		this.setFlag("true");
		this.createStandardLog(StandardLogRecord.OPERATETYPE_DELETE, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		return SUCCESS;
	}

	/**
	 * 通过Id查询使用单
	 * 
	 * @return
	 */
	public String findBasicCaseByHarId() {
		HandlingAreaReceipt har = handlingAreaReceiptService.findHandlingAreaReceiptById(id);
		if (har == null) {
			this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "id="+id);
			return SUCCESS;
		}
		basicCaseBean = new BasicCaseBean();
		try {
			BeanCopierFmtUtil.copyBean(har.getBasicCase(), basicCaseBean, null);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		if(basicCaseBean.getLawCase() != null){
			CriminalBasicCase cbc = criminalCaseService.findCriminalCaseByCaseId(basicCaseBean.getLawCase());
			if(cbc != null){
				basicCaseBean.setLawCaseName(cbc.getCaseName());
			}else{
				basicCaseBean.setLawCaseName(basicCaseBean.getLawCase());
				basicCaseBean.setLawCase("");
			}
		}else{
			basicCaseBean.setLawCaseName("");
			basicCaseBean.setLawCase("");
		}
		
		basicCaseBean.setModifyTime(har.getBasicCase().getUpdatedTime().getTime());
		if(har.getBasicCase().getModifyPeople() != null){
			PersonBean bean = new PersonBean();
			bean.setId(har.getBasicCase().getModifyPeople().getId());
			bean.setName(har.getBasicCase().getModifyPeople().getName());
			bean.setCode(har.getBasicCase().getModifyPeople().getCode());
			basicCaseBean.setModifyPeopleBean(bean);
		}
		DictionaryItem di = dictionaryItemService.findById(basicCaseBean.getSex());
		if (di != null) {
			basicCaseBean.setSexStr(di.getName());
		} else {
			basicCaseBean.setSexStr("");
		}
		if (Constant.AY_QT.equals(basicCaseBean.getCauseOfLawCase())) {
			basicCaseBean.setCauseOfLawCaseStr(basicCaseBean.getOtherCauseOfLawCase());
		} else {
			if (basicCaseBean.getCauseOfLawCase() == null) {
				basicCaseBean.setCauseOfLawCaseStr("");
			} else {
				di = dictionaryItemService.findById(basicCaseBean.getCauseOfLawCase());
				if (di != null) {
					basicCaseBean.setCauseOfLawCaseStr(di.getName());
				} else {
					basicCaseBean.setCauseOfLawCaseStr("");
				}
			}
		}
		if(!StringUtils.isBlank(basicCaseBean.getSponsorUnitCode())){
			Unit u = unitService.findUnitByCode(basicCaseBean.getSponsorUnitCode());
			if(u != null){
				basicCaseBean.setSponsorUnitName(u.getShortName());
			}
		}
		for(HandlingAreaReceiptToPoliceInfo temp : har.getHandlingAreaReceiptToPoliceInfoList()){
			PoliceInfo pi = temp.getPoliceInfo();
			PoliceInfoBean bean = new PoliceInfoBean();
			BeanCopierFmtUtil.copyBean(pi, bean, null);
			bean.setSendCardTime(pi.getSendCardTime().getTime());
			bean.setSendCardPeopleName(pi.getSendCardPeople().getName());
			bean.setIfMainPolice(temp.getIfMainPolice());
			policeInfoBeanLst.add(bean);
		}
		List<Attachment> photoLst = attachmentCustomizedService.findByTargetIdAndType(har.getId(), HandlingAreaReceipt.class.getName());
		for(Attachment att : photoLst){
			if(att.getAttachmentMeta().getName().contains(".png")){
				InputStream is = att.getAttachmentMeta().getAttachmentCopys().get(0).getInputStream();
				try {
					byte[] buffer = new byte[is.available()];
					is.read(buffer, 0, is.available());
					basicCaseBean.setPhoto(new String(Base64.encodeBase64(buffer, false)));
					basicCaseBean.setPhotoId(att.getId());
				} catch (Exception e) {
					log.error("照片转换失败");
				}
				break;
			}
		}
		List<Attachment> attLst = attachmentCustomizedService.findByTargetIdAndType(basicCaseBean.getId(),
				BasicCase.class.getName());
		if (!attLst.isEmpty()) {
			FileObjectBean b = new FileObjectBean();
			b.setId(attLst.get(0).getId());
			b.setName(attLst.get(0).getName());
			basicCaseBean.setAttach(b);
		}
		
		//查询是否有过分配记录，确认是否可以删除警员
		if(har.getAskRoomAllocationRecords() == null || har.getAskRoomAllocationRecords().size() == 0){
			policeModifyFlag = "true";
		}else{
			policeModifyFlag = "false";
		}
		
		this.setFlag("true");
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "id="+id);
		this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "打开维护人员基本情况页面(使用单ID:"+id+")");
		return SUCCESS;
	}

	/**
	 * 新建使用单
	 * 
	 * @return
	 */
	public String saveHandlingAreaReceipt() {
		//验证手环是否正在使用
		String wristTempid = handlingAreaReceiptService.findHandlingAreaReceiptIdByBangleCode(basicCaseBean.getBangleCode());
		if(wristTempid != null){
			this.errorMessage = "此手环正在使用，无法绑定";
			return SUCCESS;
		}
		HandlingAreaReceipt har = new HandlingAreaReceipt();
		BasicCase bc = new BasicCase();
		basicCaseBean.setId(null);
		try {
			BeanCopierFmtUtil.copyBean(basicCaseBean, bc, null);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		if(StringUtils.isBlank(bc.getLawCase())){
			bc.setLawCase(basicCaseBean.getLawCaseName());
		}
		har.setRecordStatus(Constant.KLZT_F); //使用单的刻录状态
		har.setCreatePeopleId(this.findCurrentPerson().getId());
		har.setCreateUnitId(this.findCurrentOrganization().getId());
		har.setStatus(Constant.SYDZT_QT);
		har.setBasicCase(bc);
		bc.setHandlingAreaReceipt(har);
		boolean firstFlag = false;
		
		for(PoliceInfoBean piBean : policeInfoBeanLst){
			if(piBean.isRelationWithCard()){
				boolean tempIcNumCheckResult = handlingAreaReceiptService
						.checkIcNumIsFree(piBean.getIcNum());
				if(!tempIcNumCheckResult){
					this.errorMessage = "此民警卡"+ piBean.getIcNum() +"正在被使用，无法绑定";
					return SUCCESS;
				}
				boolean tempPoliceCheckResult = handlingAreaReceiptService
						.checkPoliceIsFree(piBean.getPoliceNum());
				if(!tempPoliceCheckResult){
					this.errorMessage = "此民警"+ piBean.getPoliceNum() +"存在未解绑的民警卡";
					return SUCCESS;
				}
			}
			if(!firstFlag && piBean.getIfMainPolice().equals(Constant.SF_S)){
				Person p = personService.findPersonByCode(piBean.getPoliceNum());
				if(p != null && p.getOrganization() != null){
					bc.setSponsorUnitCode(p.getOrganization().getCode());
					firstFlag = true;
				}
			}
			if(!firstFlag){
				bc.setSponsorUnitCode("520xndw");
			}
		}
		try{
			handlingAreaReceiptService.saveHandlingAreaReceiptWithPoliceInfo(har, policeInfoBeanLst);
		}catch(DahuaException | SzptException e){
			LOGGER.error(e.getMessage(), e);
			this.errorMessage = e.getMessage();
	        	//如果有一个异常，则回滚已经绑定的手环
	        	ErrorWrist errorWrist = new ErrorWrist(har.getBasicCase().getBangleCode(),har.getBasicCase().getHandlingAreaReceiptNum(),har.getModifyPeople().getCode(),"reclaim");
	        	errorRollbackDataMap.put(errorWrist);
	        	//如果有一个异常，则回滚已经绑定的定位卡
	        	for(PoliceInfoBean needReclaimPoliceLocationCard : policeInfoBeanLst){
	        		//本次进行绑定的卡才进行解绑回滚
	        		if(needReclaimPoliceLocationCard.isRelationWithCard()){
	        			ErrorLocationCard errorlocationcard = new ErrorLocationCard(needReclaimPoliceLocationCard.getIcNum(),needReclaimPoliceLocationCard.getPoliceNum(),har.getModifyPeople().getCode(),"reclaim");
	        			errorRollbackDataMap.put(errorlocationcard);
	        		}
	        	}
				rollBackProcessor.process();
			   this.setFlag("false");
			return SUCCESS;
		}
			
		
		try{
			if (basicCaseBean.getAttach() != null && basicCaseBean.getAttach().getId() != null) {
				Attachment att = attachmentCustomizedService.findById(basicCaseBean.getAttach().getId());
				att.setTargetId(har.getBasicCase().getId());
				att.setType(BasicCase.class.getName());
				attachmentCustomizedService.save(att);
			}
			if (!StringUtils.isBlank(basicCaseBean.getPhoto()) && !basicCaseBean.getPhoto().equals(".png")) {
				byte[] file = Base64CryptFmtUtil.decodeToByte(basicCaseBean.getPhoto().getBytes());
				InputStream is = new ByteArrayInputStream(file);
				attachmentCustomizedService.create(new Attachment(), har.getBasicCase().getByQuestioningPeopleIdentifyNum() + "照片.png", is, har.getId(), HandlingAreaReceipt.class.getName());
			}
		}catch (Exception e) {
			this.errorMessage = "使用单照片保存失败!";
		}
		try{
			if (basicCaseBean.getAttach() != null && basicCaseBean.getAttach().getId() != null) {
				Attachment att = attachmentCustomizedService.findById(basicCaseBean.getAttach().getId());
				att.setTargetId(har.getBasicCase().getId());
				att.setType(BasicCase.class.getName());
				attachmentCustomizedService.save(att);
			}
			if (!StringUtils.isBlank(basicCaseBean.getPhoto()) && !basicCaseBean.getPhoto().equals(".png")) {
				byte[] file = Base64CryptFmtUtil.decodeToByte(basicCaseBean.getPhoto().getBytes());
				InputStream is = new ByteArrayInputStream(file);
				attachmentCustomizedService.create(new Attachment(), har.getBasicCase().getByQuestioningPeopleIdentifyNum() + "照片.png", is, har.getId(), HandlingAreaReceipt.class.getName());
			}
		}catch (Exception e) {
			this.errorMessage = "使用单照片保存失败!";
		}
		
		//使用单保存结束！！！！
		try{
			//新建活动记录大阶段和绑定手环小阶段 
			//进入办案区大阶段
			ActivityRecord firstPhrase = new ActivityRecord();
			firstPhrase.setDescription(Constant.ACTIVITY_STAGE_DESCRIPTION_JRBAQ);
			firstPhrase.setHandlingAreaReceiptNum(har.getBasicCase().getHandlingAreaReceiptNum());
			firstPhrase.setStartTime(har.getBasicCase().getEnterIntoTime());
			firstPhrase.setEndTime(new Date());
			activityRecordService.save(firstPhrase);
		
			//绑定手环小阶段
			ActivityRecord bandingPhrase = new ActivityRecord();
			bandingPhrase.setDescription(Constant.ACTIVITY_STAGE_BIND_BANGLE);
			bandingPhrase.setHandlingAreaReceiptNum(har.getBasicCase().getHandlingAreaReceiptNum());
			bandingPhrase.setStartTime(new Date());
			bandingPhrase.setEndTime(new Date());
			bandingPhrase.setSupRecordId(firstPhrase.getId());
			firstPhrase.setEndTime(bandingPhrase.getEndTime());
			activityRecordService.save(bandingPhrase);
			activityRecordService.updateActivationRecord(firstPhrase);
			
		}catch (Exception e) {
			this.errorMessage = "活动记录保存失败!";
		}
		try{
			EnterCaseAreaData bhFirstBean = new EnterCaseAreaData();
			EnterCaseAreaData bhSecondBean = new EnterCaseAreaData();
			AlertRule ar = alertRuleService.findAlertRuleByCode("0000000013007");
			if(WARNING_STATUS_START.equals(ar.getStatus())){
				String[] alertTimeAts = ar.getAlertTimeAt().split(",");
				String[] alertUsers;
				String[] alertRoles;
				if(ar.getAlertUsers() == null || ar.getAlertUsers() == ""){
					alertUsers = new String[] {};
				}else{
					alertUsers = ar.getAlertUsers().split(";");
				}
				if(ar.getAlertRoles() == null || ar.getAlertRoles() == ""){
					alertRoles = new String[] {};
				}else{
					alertRoles = ar.getAlertRoles().split(";");
				}
				List<String> firstUsers = new ArrayList<String>();
				List<String> secondUsers = new ArrayList<String>();
				List<String> firstRoles = new ArrayList<String>();
				List<String> secondRoles = new ArrayList<String>();
				
				if(alertUsers.length == 2){
					String str1 = alertUsers[0];
					String str2 = alertUsers[1];
					if(!str1.isEmpty()){
						Collections.addAll(firstUsers, str1.split(","));
					}
					if(!str2.isEmpty()){
						Collections.addAll(secondUsers, str2.split(","));
					}
				}else if(alertUsers.length == 1){
					String str = alertUsers[0];
					if(!str.isEmpty()){
						Collections.addAll(firstUsers, str.split(","));
					}
				}
				
				if(alertRoles.length == 2){
					String str1 = alertRoles[0];
					String str2 = alertRoles[1];
					if(!str1.isEmpty()){
						Collections.addAll(firstRoles, str1.split(","));
					}
					if(!str2.isEmpty()){
						Collections.addAll(secondRoles, str2.split(","));
					}
				}else if(alertRoles.length == 1){
					String str = alertRoles[0];
					if(!str.isEmpty()){
						Collections.addAll(firstRoles, str.split(","));
					}
				}
				
				//第一次提醒
				bhFirstBean.setFromEnterIntoTime(Double.valueOf(alertTimeAts[0]));
				bhFirstBean.setType(com.taiji.pubsec.ajgl.dbtx.task.util.Constant.TASK_TYPE_ENTERCASEAREA);
				bhFirstBean.setBusinessId(har.getId());
				bhFirstBean.setBusinessType(HandlingAreaReceipt.class.getName());
				 
				//第一次提醒的用户
				Set<String> firstUserSet = new HashSet<String>();
				for(PoliceInfoBean piBean : policeInfoBeanLst){
					Person p = personService.findPersonByCode(piBean.getPoliceNum());
					if(p != null && p.getAccount() != null){
						firstUserSet.add(p.getAccount().getId());
					}
				}
				for(String str: firstUsers){
					Person p = personService.findById(str);
					if(p != null && p.getAccount() != null){
						firstUserSet.add(p.getAccount().getId());
					}
				}
				for(String str: firstRoles){
					List<String> users = handlingAreaReceiptService.findAccountsByRoleId(str);
					firstUserSet.addAll(users);
				}
				bhFirstBean.setReceiveIdLst(new ArrayList<>(firstUserSet));
	
				bhFirstBean.setReceiveType(Account.class.getName());
				bhFirstBean.setEnterIntoTime(har.getBasicCase().getEnterIntoTime().getTime());
				bhFirstBean.setAheadTime(ar.getAlertTimeBefore());
				bhFirstBean.setShowTime(ar.getPopWindowDuaring());
				bhFirstBean.setWay(ar.getWay());
				bhFirstBean.setHarCode(har.getBasicCase().getHandlingAreaReceiptNum());
				bhFirstBean.setPersonName(har.getBasicCase().getByQuestioningPeopleName());
				bhFirstBean.setCaseCode(har.getBasicCase().getLawCase());
				bhFirstBean.setSuspectId(har.getBasicCase().getByQuestioningPeopleId());
				taskClient.launchTask(TaskPojoJSONFmtUtil.getJSONStr(bhFirstBean));
				
				//第二次提醒
				bhSecondBean.setFromEnterIntoTime(Double.valueOf(alertTimeAts[1]));
				bhSecondBean.setType(com.taiji.pubsec.ajgl.dbtx.task.util.Constant.TASK_TYPE_ENTERCASEAREA);
				bhSecondBean.setBusinessId(har.getId());
				bhSecondBean.setBusinessType(HandlingAreaReceipt.class.getName());
				
				//第二次提醒的用户
				Set<String> secondUserSet = new HashSet<String>();
				for(PoliceInfoBean piBean : policeInfoBeanLst){
					Person p = personService.findPersonByCode(piBean.getPoliceNum());
					if(p != null && p.getAccount() != null){
						secondUserSet.add(p.getAccount().getId());
					}
				}
				for(String str: secondUsers){
					Person p = personService.findById(str);
					if(p != null && p.getAccount() != null){
						secondUserSet.add(p.getAccount().getId());
					}
				}
				for(String str: secondRoles){
					List<String> users = handlingAreaReceiptService.findAccountsByRoleId(str);
					secondUserSet.addAll(users);
				}
				bhSecondBean.setReceiveIdLst(new ArrayList<>(secondUserSet));
				
				bhSecondBean.setReceiveType(Account.class.getName()); 
				bhSecondBean.setEnterIntoTime(har.getBasicCase().getEnterIntoTime().getTime());
				bhSecondBean.setAheadTime(ar.getAlertTimeBefore());
				bhSecondBean.setShowTime(ar.getPopWindowDuaring());
				bhSecondBean.setWay(ar.getWay());
				bhSecondBean.setHarCode(har.getBasicCase().getHandlingAreaReceiptNum());
				bhSecondBean.setPersonName(har.getBasicCase().getByQuestioningPeopleName());
				bhSecondBean.setCaseCode(har.getBasicCase().getLawCase());
				bhSecondBean.setSuspectId(har.getBasicCase().getByQuestioningPeopleId());
				taskClient.launchTask(TaskPojoJSONFmtUtil.getJSONStr(bhSecondBean));
			}
		}catch (Exception e) {
			this.errorMessage = "办案超时预警启动失败!";
		}
		
		OperateRecord operateRecord = new OperateRecord();
		operateRecord.setModelObject(HandlingAreaReceipt.class.getName());
		operateRecord.setModelObjectId(har.getId());
		operateRecord.setNoteText("");
		operateRecord.setNoteType("0");
		operateRecord.setOperateContent("创建使用单");
		operateRecord.setOperateTime(new Date());
		operateRecord.setOperator(this.findCurrentPerson().getName());
		operateRecordService.saveOperateRecord(operateRecord);
		this.setFlag("true");
		this.createStandardLog(StandardLogRecord.OPERATETYPE_ADD, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		return SUCCESS;
	}

	/**
	 * 更新人员基本情况
	 * 
	 * @return
	 */
	public String updateBasicCase() {
		Map<String, String> addMap = new HashMap<String, String>();
		Map<String, String> delMap = new HashMap<String, String>();
		if (basicCaseBean == null) {
			return SUCCESS;
		}
		for(PoliceInfoBean piBean : policeInfoBeanLst){
			PoliceInfo pi = new PoliceInfo();
			if(piBean.isRelationWithCard()){
				Boolean tempIcNumCheckResult = handlingAreaReceiptService.checkIcNumIsFree(piBean.getIcNum());
				if(!tempIcNumCheckResult){
					this.errorMessage = "此民警卡" + piBean.getIcNum() + "正在被使用，无法绑定";
					return SUCCESS;
				}
				boolean tempPoliceCheckResult = handlingAreaReceiptService
						.checkPoliceIsFree(piBean.getPoliceNum());
				if(!tempPoliceCheckResult){
					this.errorMessage = "此民警" + piBean.getPoliceNum() + "存在未解绑的民警卡";
					return SUCCESS;
				}
			}
		}
		BasicCase bc = basicCaseService.findById(basicCaseBean.getId());
		String suc = bc.getSponsorUnitCode();
		Set<HandlingAreaReceiptToPoliceInfo> tempSet = bc.getHandlingAreaReceipt().getHandlingAreaReceiptToPoliceInfoList();
		List<HandlingAreaReceiptToPoliceInfo> htpTempLstForDelete = new ArrayList<HandlingAreaReceiptToPoliceInfo>();
		for(String str : deletePoliceId){
			for(HandlingAreaReceiptToPoliceInfo temp : tempSet) {
				PoliceInfo pi = temp.getPoliceInfo();
				if(pi.getId().equals(str)){
					if(!StringUtils.isBlank(pi.getIcNum())){
						delMap.put(pi.getPoliceNum(), pi.getIcNum());
					}
					htpTempLstForDelete.add(temp);
				}
			}
		}
		boolean firstFlag = false;
		for(PoliceInfoBean piBean : policeInfoBeanLst){
			if(!firstFlag && piBean.getIfMainPolice().equals(Constant.SF_S)){
				Person p = personService.findPersonByCode(piBean.getPoliceNum());
				if(p != null && p.getOrganization() != null){
					bc.setSponsorUnitCode(p.getOrganization().getCode());
					firstFlag = true;
				}
			}
			if(!firstFlag){
				bc.setSponsorUnitCode("520xndw");
			}
		}
		try {
			BeanCopierFmtUtil.copyBean(basicCaseBean, bc, null);
			bc.setSponsorUnitCode(suc);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		if(StringUtils.isBlank(bc.getLawCase())){
			bc.setLawCase(basicCaseBean.getLawCaseName());
		}
		HandlingAreaReceipt har = bc.getHandlingAreaReceipt();
		try{
			handlingAreaReceiptService.updateHandlingAreaReceiptWithPoliceInfo(har, policeInfoBeanLst, htpTempLstForDelete);
		}catch(DahuaException e){
			LOGGER.error(e.getMessage(), e);
	        		String msg = e.getMessage();
	        		this.errorMessage = msg;
	            	//回滚已经绑定的定位卡
	                for(PoliceInfoBean temp : policeInfoBeanLst){
	                	if(temp.isRelationWithCard()){
	                		ErrorLocationCard errorlocationcard = new ErrorLocationCard(temp.getIcNum(),
	                				temp.getPoliceNum(),har.getModifyPeople().getCode(),"reclaim");
	                		errorRollbackDataMap.put(errorlocationcard);
	                	}
	                }
	            	rollBackProcessor.process();
	            	this.setFlag("false");
	            	return SUCCESS;
		}
		if (basicCaseBean.getUploadNew()) {
			List<Attachment> attLst = attachmentCustomizedService.findByTargetIdAndType(bc.getId(),
					BasicCase.class.getName());
			if (!attLst.isEmpty()) {
				attachmentCustomizedService.delete(attLst.get(0));
			}
			if (basicCaseBean.getAttach() != null && basicCaseBean.getAttach().getId() != null) {
				Attachment att = attachmentCustomizedService.findById(basicCaseBean.getAttach().getId());
				att.setTargetId(bc.getId());
				att.setType(BasicCase.class.getName());
				attachmentCustomizedService.save(att);
			}
		}
		if("true".equals(basicCaseBean.getPhotoChange())){
			if(!StringUtils.isBlank(basicCaseBean.getPhotoId())){
				attachmentCustomizedService.delete(basicCaseBean.getPhotoId());
			}
			if (!StringUtils.isBlank(basicCaseBean.getPhoto())) {
				byte[] file = Base64CryptFmtUtil.decodeToByte(basicCaseBean.getPhoto().getBytes());
				InputStream is = new ByteArrayInputStream(file);
				attachmentCustomizedService.create(new Attachment(), bc.getByQuestioningPeopleIdentifyNum() + "照片.png", is, bc.getHandlingAreaReceipt().getId(), HandlingAreaReceipt.class.getName());
			}
		}
		OperateRecord operateRecord = new OperateRecord();
		operateRecord.setModelObject(HandlingAreaReceipt.class.getName());
		operateRecord.setModelObjectId(bc.getHandlingAreaReceipt().getId());
		operateRecord.setNoteText("");
		operateRecord.setNoteType("0");
		operateRecord.setOperateContent("更新人员基本情况");
		operateRecord.setOperateTime(new Date());
		operateRecord.setOperator(this.findCurrentPerson().getName());
		operateRecordService.saveOperateRecord(operateRecord);
		this.setFlag("true");
		BasicCase b = basicCaseService.findById(bc.getId());
		if(b!=null){
			this.setModifyPerson(b.getModifyPeople().getName());
			this.setModifyTime(DateTimeUtil.formatDateTime(b.getUpdatedTime(), Constant.DATE_FORMAT));
		}
		
		this.createStandardLog(StandardLogRecord.OPERATETYPE_UPDATE, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "保存维护进入办案区人员基本情况页面(使用单ID:"+har.getId()+")");
		return SUCCESS;
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 */
	public String searchAllHandlingAreaReceiptByPage() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("unitId", this.findCurrentOrganization().getId());
		map.put("handlingAreaReceiptNum", harSearchBean.getCode());
		map.put("askRoomName", harSearchBean.getRoom());
		map.put("handlingPolice", harSearchBean.getPolice());
		map.put("haveRecord", harSearchBean.getRecord());
		map.put("status", harSearchBean.getState());
		map.put("causeOfLawCase", harSearchBean.getCauseOfLawCase());
		map.put("byQuestioningPeopleName", harSearchBean.getAskPerson());
		map.put("byQuestioningPeopleIdentifyNum", harSearchBean.getIdNum());
		map.put("lawCase", harSearchBean.getAboutCase());
		map.put("enterIntoTimeStart", harSearchBean.getAccessDateStart());
		map.put("enterIntoTimeEnd", harSearchBean.getAccessDateEnd());
		if(!StringUtils.isBlank(harSearchBean.getAccessDateStart())){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			try {
				map.put("enterIntoTimeStart",sdf.parse(harSearchBean.getAccessDateStart()));
			} catch (ParseException e) {
				LOGGER.error(e.getMessage(), e);
			}  
		}
		if(!StringUtils.isBlank(harSearchBean.getAccessDateEnd())){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			try {
				map.put("enterIntoTimeEnd",sdf.parse(harSearchBean.getAccessDateEnd()));
			} catch (ParseException e) {
				LOGGER.error(e.getMessage(), e);
			}  
		}
		map.put("modifyPeopleName", harSearchBean.getModifyPeopleName());
		Pager<HandlingAreaReceipt> p = handlingAreaReceiptService.findHandlingAreaReceiptByKey(map, this.getStart() / this.getLength(), this.getLength());
		this.setTotalNum(p.getTotalNum());
		for (HandlingAreaReceipt har : p.getPageList()) {
			harSearchBeanlst.add(handlingAreaReceiptToHarSearchBean(har));
		}
		HttpSession session = SpringWebContextUtil.getSession();
		session.setAttribute("fullSearch", fullSearch);
		session.setAttribute("harSearchBean", harSearchBean);
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null,
				"案件管理-办案区管理", "unitId="+this.findCurrentOrganization().getId()+
				",handlingAreaReceiptNum="+harSearchBean.getCode()+
				",askRoomName="+harSearchBean.getRoom()+
				",handlingPolice="+harSearchBean.getPolice()+
				",status="+harSearchBean.getState()+
				",enterAreaReasons="+harSearchBean.getEnterAreaReasons()+
				",byQuestioningPeopleName="+harSearchBean.getAskPerson()+
				",byQuestioningPeopleIdentifyNum="+harSearchBean.getIdNum()+
				",lawCase="+harSearchBean.getAboutCase()+
				",enterIntoTimeStart="+harSearchBean.getAccessDateStart()+
				",enterIntoTimeStartEnd="+harSearchBean.getAccessDateEnd());
		return SUCCESS;
	}
	
	public String findPersonsByIdNum(){
		person = suspectService.findBasicInfo(code);
		if(person == null){
			setCriminalRecordLst(new ArrayList<CriminalRecordBean>());
			person.setSex(Constant.XB_WEIZHI);
		}else{
			if(person.getSex().equals("1")){
				person.setSex(Constant.XB_NAN);
			}else if(person.getSex().equals("2")){
				person.setSex(Constant.XB_NU);
			}else{
				person.setSex(Constant.XB_WEIZHI);
			}
			setCriminalRecordLst(suspectService.findCriminalRecord(code));
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "idNum="+code);
		this.creatCaseManagementLog("办案区管理 /办案区使用/办案区使用单/办案区使用单详情", "根据身份证ID查询嫌疑人信息(身份证ID:"+code+")");
		return SUCCESS;
	}
	
	public String showAllPoliceWhoHasCard(){
		PoliceInfo condition = new PoliceInfo();
		if(piBean.getIcNum() != null){
			condition.setIcNum(piBean.getIcNum());
		}
		if(piBean.getPoliceNum() != null){
			condition.setPoliceNum(piBean.getPoliceNum());
		}
		if(piBean.getPoliceName() != null){
			condition.setPoliceName(piBean.getPoliceName());
		}
		List<PoliceInfo> piLst = policeInfoService.findPoliceWhoHasCardByCondition(condition);
		if(piLst != null){
			for(PoliceInfo pi : piLst){
				if(StringUtils.isBlank(pi.getIcNum())){
					continue;
				}
				PoliceInfoBean bean = new PoliceInfoBean();
				bean.setId(pi.getId());
				bean.setIcNum(pi.getIcNum());
				bean.setPoliceName(pi.getPoliceName());
				bean.setPoliceNum(pi.getPoliceNum());
				bean.setSendCardTime(pi.getSendCardTime().getTime());
				bean.setSendCardPeopleName(pi.getSendCardPeople().getName());
				bean.setRemark(pi.getRemark());
				policeInfoBeanLst.add(bean);
			}
		}
		return SUCCESS;
	}
	
	public String locationCardControl(){
		PoliceInfo info = policeInfoService.findByPoliceCode(policeNum);
		try{
			ResultBean encRes = entranceControlService.cancel(info.getIcNum());
		} catch (DahuaException e) {
			LOGGER.error("定位卡解除门禁失败，卡号 " + info.getIcNum(),e);
		}
		try{
			ResultBean locRes = locationCardService.reclaim(info.getIcNum(),info.getPoliceNum(), this.findCurrentPerson().getCode());
   	 		this.setFlag(Boolean.toString(Boolean.TRUE));
   	 		info.setIcNum(null);
   	 		policeInfoService.update(info);
   	 		this.createStandardLog(StandardLogRecord.OPERATETYPE_UPDATE, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理-解绑民警卡", "idNum="+policeNum);
   	 		this.creatCaseManagementLog("办案区管理 /办案区使用/办案区使用单/民警卡管理", "解绑民警卡(警号:" + policeNum + ",卡号:"+info.getIcNum()+")");
		} catch (DahuaException e) {
			this.setFlag(Boolean.toString(Boolean.FALSE));
			this.errorMessage += e.getMessage();
			LOGGER.debug(e.getMessage());
		}
		return SUCCESS;
	}
	
	
	public String getPoliceNum() {
		return policeNum;
	}

	public void setPoliceNum(String policeNum) {
		this.policeNum = policeNum;
	}

	public List<OptionItemBean> getRoomLst() {
		return roomLst;
	}

	public void setRoomLst(List<OptionItemBean> roomLst) {
		this.roomLst = roomLst;
	}

	public HarSearchBean getHarSearchBean() {
		return harSearchBean;
	}

	public void setHarSearchBean(HarSearchBean harSearchBean) {
		this.harSearchBean = harSearchBean;
	}

	public List<HarSearchBean> getHarSearchBeanlst() {
		return harSearchBeanlst;
	}

	public void setHarSearchBeanlst(List<HarSearchBean> harSearchBeanlst) {
		this.harSearchBeanlst = harSearchBeanlst;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BasicCaseBean getBasicCaseBean() {
		return basicCaseBean;
	}

	public void setBasicCaseBean(BasicCaseBean basicCaseBean) {
		this.basicCaseBean = basicCaseBean;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeleteFileId() {
		return deleteFileId;
	}

	public void setDeleteFileId(String deleteFileId) {
		this.deleteFileId = deleteFileId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isFinishBtnFlag() {
		return finishBtnFlag;
	}

	public void setFinishBtnFlag(boolean finishBtnFlag) {
		this.finishBtnFlag = finishBtnFlag;
	}

	public boolean isPrintBtnFlag() {
		return printBtnFlag;
	}

	public void setPrintBtnFlag(boolean printBtnFlag) {
		this.printBtnFlag = printBtnFlag;
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

	public String getJustShow() {
		return justShow;
	}

	public void setJustShow(String justShow) {
		this.justShow = justShow;
	}
	public boolean isFullSearch() {
		return fullSearch;
	}
	public void setFullSearch(boolean fullSearch) {
		this.fullSearch = fullSearch;
	}
	public String getCaseCode() {
		return caseCode;
	}
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public List<PoliceInfoBean> getPoliceInfoBeanLst() {
		return policeInfoBeanLst;
	}
	public void setPoliceInfoBeanLst(List<PoliceInfoBean> policeInfoBeanLst) {
		this.policeInfoBeanLst = policeInfoBeanLst;
	}
	public List<String> getDeletePoliceId() {
		return deletePoliceId;
	}
	public void setDeletePoliceId(List<String> deletePoliceId) {
		this.deletePoliceId = deletePoliceId;
	}
	public BasicPersonBean getPerson() {
		return person;
	}
	public void setPerson(BasicPersonBean person) {
		this.person = person;
	}
	public List<CriminalRecordBean> getCriminalRecordLst() {
		return criminalRecordLst;
	}
	public void setCriminalRecordLst(List<CriminalRecordBean> criminalRecordLst) {
		this.criminalRecordLst = criminalRecordLst;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<String> getIcoLst() {
		return icoLst;
	}

	public void setIcoLst(List<String> icoLst) {
		this.icoLst = icoLst;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getPoliceModifyFlag() {
		return policeModifyFlag;
	}

	public void setPoliceModifyFlag(String policeModifyFlag) {
		this.policeModifyFlag = policeModifyFlag;
	}

	public PoliceInfoBean getPiBean() {
		return piBean;
	}

	public void setPiBean(PoliceInfoBean piBean) {
		this.piBean = piBean;
	}

}
