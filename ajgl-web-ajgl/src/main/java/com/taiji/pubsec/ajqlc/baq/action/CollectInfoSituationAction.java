package com.taiji.pubsec.ajqlc.baq.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajqlc.baq.action.bean.CollectInfoSituationBean;
import com.taiji.pubsec.ajqlc.baq.action.bean.PersonBean;
import com.taiji.pubsec.ajqlc.baq.model.CollectInfoSituation;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.baq.model.OperateRecord;
import com.taiji.pubsec.ajqlc.baq.service.ICollectInfoSituationService;
import com.taiji.pubsec.ajqlc.baq.service.IHandlingAreaReceiptService;
import com.taiji.pubsec.ajqlc.baq.service.IOperateRecordService;
import com.taiji.pubsec.ajqlc.operationrecord.model.StandardLogRecord;
import com.taiji.pubsec.ajqlc.util.Constant;
import com.taiji.pubsec.common.tool.datetime.DateTimeUtil;
import com.taiji.pubsec.springsecurity.resource.util.MySecureUser;
import com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil;

/**
 * 信息采集情况Action
 * 
 * @author WL
 *
 */
@Controller("collectInfoSituationAction")
@Scope("prototype")
public class CollectInfoSituationAction extends ReturnMessageAction {

	private static final long serialVersionUID = 1L;

	private static final String SHI = "是";

	private static final String FOU = "否";

	private String handlingAreaReceiptId;// 办案区使用单id
	
	private CollectInfoSituationBean collectInfoSituationBean;// 信息采集情况对象Bean

	@Resource
	private IHandlingAreaReceiptService handlingAreaReceiptService;// 办案区使用单接口
	
	@Resource
	private ICollectInfoSituationService collectInfoSituationService;// 信息采集情况接口
	
	@Resource
	private IOperateRecordService operateRecordService;// 操作记录接口

	private String harId;
	
	private boolean modifyBtnFlag;
	
	private boolean printBtnFlag;
	
	private boolean finishBtnFlag;
	
	private String justShow;
	
	@SuppressWarnings("unchecked")
	public String showLookCollectInfoSituationPage(){
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
			CollectInfoSituation cis = har.getCollectInfoSituation();
			mPerson = (Map<String, String>)userMap.get("person");
			if(cis !=null && !mPerson.get("code").equals(cis.getModifyPeople().getCode())){
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
		return SUCCESS;
	}
	
	/**
	 * 根据办案区使用单id查询信息采集情况
	 * 
	 * @param handlingAreaReceiptId 办案区使用单id
	 * @return "success",成功返回collectInfoSituationBean：信息采集情况bean对象
	 */
	public String searchCollectInfoSituationByHandlingAreaReceiptId() {
		HandlingAreaReceipt har = handlingAreaReceiptService.findHandlingAreaReceiptById(handlingAreaReceiptId);
		if (har == null) {
			this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "id="+harId);
			return SUCCESS;
		}
		CollectInfoSituation cis = har.getCollectInfoSituation();
		collectInfoSituationBean = collectInfoSituationToCollectInfoSituationBean(cis);
		this.createStandardLog(StandardLogRecord.OPERATETYPE_QUERY, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", "id="+harId);
		this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "打开维护信息采集情况页面(使用单ID:"+handlingAreaReceiptId+")");
		return SUCCESS;
	}

	/**
	 * 新增采集信息情况
	 * 
	 * @param collectInfoSituationBean 信息采集情况bean对象
	 * @return "success",成功返回handlingAreaReceiptId：办案区使用单id
	 */
	public String saveCollectInfoSituation() {
		CollectInfoSituation cis = collectInfoSituationBeanToCollectInfoSituation(collectInfoSituationBean);
		handlingAreaReceiptId = collectInfoSituationService.saveCollectInfoSituation(cis);
		//保存操作记录
		saveOperateRecord("","0","创建采集信息情况");
		
		this.setModifyPerson(cis.getModifyPeople().getName());
		this.setModifyTime(DateTimeUtil.formatDateTime(cis.getUpdatedTime(), Constant.DATE_FORMAT));
		this.createStandardLog(StandardLogRecord.OPERATETYPE_ADD, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "保存维护信息采集情况页面(使用单ID:"+handlingAreaReceiptId+")--新增采集信息情况");
		return SUCCESS;
	}

	/**
	 * 修改采集信息情况
	 * 
	 * @param collectInfoSituationBean 信息采集情况bean对象
	 * @return "success",成功无返回值
	 */
	public String updateCollectInfoSituation() {
		CollectInfoSituation cis = collectInfoSituationBeanToCollectInfoSituation(collectInfoSituationBean);
		collectInfoSituationService.updateCollectInfoSituation(cis);
		//保存操作记录
		saveOperateRecord("","1","更新采集信息情况");
		HandlingAreaReceipt har = handlingAreaReceiptService.findHandlingAreaReceiptById(cis.getHandlingAreaReceipt().getId());
		if(har!=null){
			CollectInfoSituation c = har.getCollectInfoSituation();
			if(c!=null){
				this.setModifyPerson(c.getModifyPeople().getName());
				this.setModifyTime(DateTimeUtil.formatDateTime(c.getUpdatedTime(), Constant.DATE_FORMAT));
			}
			
		}
		this.createStandardLog(StandardLogRecord.OPERATETYPE_UPDATE, StandardLogRecord.OPERATERESULT_SUCCESS, null, "案件管理-办案区管理", null);
		this.creatCaseManagementLog("办案区管理 /办案区使用/ 办案区使用单/办案区使用单详情", "保存维护信息采集情况页面(使用单ID:"+cis.getHandlingAreaReceipt().getId()+")--修改采集信息情况");
		return SUCCESS;
	}
	
	/**
	 * 保存操作记录
	 * 
	 * @param noteText 备注内容
	 * @param noteType 备注类型
	 * @param operateContent 记录类容
	 */
	private void saveOperateRecord(String noteText, String noteType, String operateContent){
		OperateRecord operateRecord = new OperateRecord();
		operateRecord.setModelObject(HandlingAreaReceipt.class.getName());
		operateRecord.setModelObjectId(collectInfoSituationBean.getHandlingAreaReceiptId());
		operateRecord.setNoteText(noteText);
		operateRecord.setNoteType(noteType);
		operateRecord.setOperateContent(operateContent);
		operateRecord.setOperateTime(new Date());
		operateRecord.setOperator(this.findCurrentPerson().getName());
		operateRecordService.saveOperateRecord(operateRecord);
	}

	/**
	 * 采集信息情况bean转model
	 * 
	 * @param cisb 信息采集情况bean对象
	 * @return cis 信息采集情况model对象
	 */
	private CollectInfoSituation collectInfoSituationBeanToCollectInfoSituation(CollectInfoSituationBean cisb) {
		if (cisb == null) {
			return null;
		}
		CollectInfoSituation cis = new CollectInfoSituation();
		HandlingAreaReceipt har = handlingAreaReceiptService
				.findHandlingAreaReceiptById(cisb.getHandlingAreaReceiptId());
		if(har != null && har.getCollectInfoSituation() != null){
			cis = har.getCollectInfoSituation();
		}
		cis.setCollectProject(cisb.getCollectProject());
		cis.setCollectProjectOther(cisb.getCollectProjectOther());
		cis.setInfoIntoString(cisb.getInfoIntoString());
		cis.setInspectComparison(cisb.getInspectComparison());
		cis.setIsCollect(cisb.getIsCollect());
		cis.setQqNum(cisb.getQqNum());
		cis.setWeixinNum(cisb.getWeixinNum());
		cis.setPhoneInfo(cisb.getPhoneInfo());
		cis.setHandlingAreaReceipt(har);
		return cis;
	}

	/**
	 * 采集信息情况model转bean
	 * 
	 * @param cis 信息采集情况model对象
	 * @return cisb 信息采集情况bean对象
	 */
	private static CollectInfoSituationBean collectInfoSituationToCollectInfoSituationBean(CollectInfoSituation cis) {
		SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT);
		CollectInfoSituationBean cisb = new CollectInfoSituationBean();
		if (cis == null) {
			return null;
		}
		cisb.setId(cis.getId());
		cisb.setCollectProject(cis.getCollectProject());
		cisb.setCollectProjectOther(cis.getCollectProjectOther());
		cisb.setInfoIntoString(cis.getInfoIntoString());
		if (Constant.SF_S.equals(cis.getInfoIntoString())) {
			cisb.setInfoIntoStringName(SHI);
		} else if (Constant.SF_F.equals(cis.getInfoIntoString())) {
			cisb.setInfoIntoStringName(FOU);
		}

		cisb.setInspectComparison(cis.getInspectComparison());
		if (Constant.SF_S.equals(cis.getInspectComparison())) {
			cisb.setInspectComparisonName(SHI);
		} else if (Constant.SF_F.equals(cis.getInspectComparison())) {
			cisb.setInspectComparisonName(FOU);
		}
		cisb.setIsCollect(cis.getIsCollect());
		if (Constant.SF_S.equals(cis.getIsCollect())) {
			cisb.setIsCollectName(SHI);
		} else if (Constant.SF_F.equals(cis.getIsCollect())) {
			cisb.setIsCollectName(FOU);
		}
		cisb.setQqNum(cis.getQqNum());
		cisb.setWeixinNum(cis.getWeixinNum());
		cisb.setPhoneInfo(cis.getPhoneInfo());
		if(cis.getModifyPeople() != null){
			PersonBean bean = new PersonBean();
			bean.setId(cis.getModifyPeople().getId());
			bean.setName(cis.getModifyPeople().getName());
			bean.setCode(cis.getModifyPeople().getCode());
			cisb.setModifyPeopleBean(bean);
		}
		if (cis.getUpdatedTime() != null) {
			cisb.setUpdateTime(sdf.format(cis.getUpdatedTime()));
		}
		cisb.setHandlingAreaReceiptId(cis.getHandlingAreaReceipt().getId());
		return cisb;
	}

	public String getHandlingAreaReceiptId() {
		return handlingAreaReceiptId;
	}

	public void setHandlingAreaReceiptId(String handlingAreaReceiptId) {
		this.handlingAreaReceiptId = handlingAreaReceiptId;
	}

	public CollectInfoSituationBean getCollectInfoSituationBean() {
		return collectInfoSituationBean;
	}

	public void setCollectInfoSituationBean(CollectInfoSituationBean collectInfoSituationBean) {
		this.collectInfoSituationBean = collectInfoSituationBean;
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

}
