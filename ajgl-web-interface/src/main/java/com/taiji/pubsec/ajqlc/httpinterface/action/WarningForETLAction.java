package com.taiji.pubsec.ajqlc.httpinterface.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.pubsec.ajgl.dbtx.task.util.CommonData;
import com.taiji.pubsec.ajgl.dbtx.task.util.arrest.pojo.ArrestData;
import com.taiji.pubsec.ajgl.dbtx.task.util.bail.pojo.BailData;
import com.taiji.pubsec.ajgl.dbtx.task.util.detained.pojo.DetainedData;
import com.taiji.pubsec.ajgl.dbtx.task.util.extendedDetention.pojo.ExtendedDetentionData;
import com.taiji.pubsec.ajgl.dbtx.task.util.residentialSurveillance.pojo.ResidentialSurveillanceData;
import com.taiji.pubsec.ajgl.dbtx.task.util.subpoena.pojo.SubpoenaData;
import com.taiji.pubsec.ajgl.dbtx.task.util.susobj.pojo.SusObjData;
import com.taiji.pubsec.ajgl.util.PageCommonAction;
import com.taiji.pubsec.ajqlc.alert.model.AlertRule;
import com.taiji.pubsec.ajqlc.alert.service.IAlertRuleService;
import com.taiji.pubsec.ajqlc.sla.model.CriminalBasicCase;
import com.taiji.pubsec.ajqlc.sla.model.CriminalPerson;
import com.taiji.pubsec.ajqlc.sla.model.DocBianGengJiYaQiXianTongZhiShu;
import com.taiji.pubsec.ajqlc.sla.model.DocChuanHuanNew;
import com.taiji.pubsec.ajqlc.sla.model.DocChuanHuanNewXingShi2012;
import com.taiji.pubsec.ajqlc.sla.model.DocDaiBuNew;
import com.taiji.pubsec.ajqlc.sla.model.DocJianShiJueDingZhiXingTongZhiShuNew;
import com.taiji.pubsec.ajqlc.sla.model.DocJvLiuNew;
import com.taiji.pubsec.ajqlc.sla.model.DocKouYaJueDingNew2012;
import com.taiji.pubsec.ajqlc.sla.model.DocQuBaoHouShenNew;
import com.taiji.pubsec.ajqlc.sla.model.DocYanChangJvLiuQiXianNew;
import com.taiji.pubsec.ajqlc.sla.model.DocZhengJvBaoQuanJueDingNew;
import com.taiji.pubsec.ajqlc.sla.service.IArchivedFileService;
import com.taiji.pubsec.ajqlc.sla.service.ICriminalCaseService;
import com.taiji.pubsec.ajqlc.sla.service.ICriminalPersonService;
import com.taiji.pubsec.common.tool.mission.client.core.TaskClient;
import com.taiji.pubsec.common.tool.mission.task.util.TaskPojoJSONFmtUtil;

/**
 * 预警Action
 * @author huangda
 */
@Controller("warningForETLAction")
@Scope("prototype")
public class WarningForETLAction extends PageCommonAction {

	private static final long serialVersionUID = 1L;
	
	@Resource(name="dbtxTaskClient")
	private TaskClient taskClient;
	@Resource	// 预警规则接口
	private IAlertRuleService alertRuleService;
	@Resource	// 预警规则接口
	private IArchivedFileService archivedFileService;
	@Resource
	private ICriminalCaseService criminalCaseService;
	@Resource
	private ICriminalPersonService criminalPersonService;
	
	private static final String WARNING_STATUS_START = "0000000002002"; //预警规则状态：启用
	
	/**
	 * 逮捕到期预警
	 */
	public String arrestWarning(){
		CommonData businessObj = (CommonData)TaskPojoJSONFmtUtil.getPojo(read(), CommonData.class);
		AlertRule ar = alertRuleService.findAlertRuleByCode("0000000013006");
	    if(WARNING_STATUS_START.equals(ar.getStatus())){
	    	ArrestData data = new ArrestData();
			data.setBusinessId(businessObj.getBusinessId());
			data.setBusinessType(businessObj.getBusinessType());
			data.setType(businessObj.getType());
			data.setShowTime(ar.getPopWindowDuaring());
			data.setWay(ar.getWay());
			data.setAlertTimeAt(ar.getAlertTimeAt());
			System.out.println(businessObj.getBusinessId());
			DocDaiBuNew doc = archivedFileService.findDocDaiBuNewById(businessObj.getBusinessId());
			if(doc == null || doc.getA20() == null){
				return SUCCESS;
			}
			data.setSendTime(doc.getA20().getTime());
			data.setCaseCode(doc.getCaseid());
			data.setCaseName(doc.getA7());
			data.setPersonName(doc.getA9());
			data.setPersonId(doc.getPersonid());
			taskClient.launchTask(TaskPojoJSONFmtUtil.getJSONStr(data));
			return SUCCESS;
	    }else {
	    	 return SUCCESS;
	    }
		
	}
	
	/**
	 * 取保到期预警
	 */
	public String bailWarning(){
		CommonData businessObj = (CommonData)TaskPojoJSONFmtUtil.getPojo(read(), CommonData.class);
		AlertRule ar = alertRuleService.findAlertRuleByCode("0000000013004");
		if(WARNING_STATUS_START.equals(ar.getStatus())){
			BailData data = new BailData();
			data.setBusinessId(businessObj.getBusinessId());
			data.setBusinessType(businessObj.getBusinessType());
			data.setType(businessObj.getType());
			data.setShowTime(ar.getPopWindowDuaring());
			data.setWay(ar.getWay());
			data.setAlertTimeAt(ar.getAlertTimeAt());
			DocQuBaoHouShenNew doc = archivedFileService.findDocQuBaoHouShenNewById(businessObj.getBusinessId());
			if(doc == null || doc.getA13() == null){
				return SUCCESS;
			}
			data.setSendDate(doc.getA13().getTime());
			data.setCaseCode(doc.getCaseid());
			data.setCaseName(doc.getA7());
			data.setPersonId(doc.getPersonid());
			data.setPersonName(doc.getB7());
			taskClient.launchTask(TaskPojoJSONFmtUtil.getJSONStr(data));
			return SUCCESS;
		}else{
			return SUCCESS;
		}
		
	}
	
	/**
	 * 刑拘到期预警
	 */
	public String detainedWarning(){
		CommonData businessObj = (CommonData)TaskPojoJSONFmtUtil.getPojo(read(), CommonData.class);
		AlertRule ar = alertRuleService.findAlertRuleByCode("0000000013002");
		if(WARNING_STATUS_START.equals(ar.getStatus())){
			DetainedData data = new DetainedData();
			data.setBusinessId(businessObj.getBusinessId());
			data.setBusinessType(businessObj.getBusinessType());
			data.setType(businessObj.getType());
			data.setShowTime(ar.getPopWindowDuaring());
			data.setWay(ar.getWay());
			data.setAlertTimeAt(ar.getAlertTimeAt());
			DocJvLiuNew doc = archivedFileService.findDocJvLiuNewById(businessObj.getBusinessId());
			if(doc == null || doc.getA16() == null){
				return SUCCESS;
			}
			data.setApprovalTime(doc.getA16().getTime());
			data.setCaseCode(doc.getCaseid());
			data.setCaseName(doc.getA7());
			data.setPersonId(doc.getPersonid());
			data.setPersonName(doc.getA9());
			taskClient.launchTask(TaskPojoJSONFmtUtil.getJSONStr(data));
			return SUCCESS;
		}else{
			return SUCCESS;
		}
		
	}
	
	/**
	 * 延长拘留到期预警
	 */
	public String extendedDetentionWarning(){
		CommonData businessObj = (CommonData)TaskPojoJSONFmtUtil.getPojo(read(), CommonData.class);
		AlertRule ar = alertRuleService.findAlertRuleByCode("0000000013003");
		if(WARNING_STATUS_START.equals(ar.getStatus())){
			ExtendedDetentionData data = new ExtendedDetentionData();
			data.setBusinessId(businessObj.getBusinessId());
			data.setBusinessType(businessObj.getBusinessType());
			data.setType(businessObj.getType());
			data.setShowTime(ar.getPopWindowDuaring());
			data.setWay(ar.getWay());
			data.setAlertTimeAt(ar.getAlertTimeAt());
			
			if(businessObj.getBusinessType().equals(DocYanChangJvLiuQiXianNew.class.getName())){
				DocYanChangJvLiuQiXianNew doc = archivedFileService.findDocYanChangJvLiuQiXianNewById(businessObj.getBusinessId());
				if(doc == null || doc.getA14() == null){
					return SUCCESS;
				}
				data.setExtendedDetentionTime(doc.getA14().getTime());
				data.setExtendedTime((int)((doc.getA14().getTime() - doc.getA13().getTime()) / 1000 / 60 / 60 / 24));
				data.setCaseCode(doc.getCaseid());
				data.setCaseName(doc.getA7());
				data.setPersonId(doc.getPersonid());
				data.setPersonName(doc.getA9());
			}else if(businessObj.getBusinessType().equals(DocBianGengJiYaQiXianTongZhiShu.class.getName())){
				DocBianGengJiYaQiXianTongZhiShu doc = archivedFileService.findDocBianGengJiYaQiXianTongZhiShuById(businessObj.getBusinessId());
				if(doc == null || doc.getA17() == null){
					return SUCCESS;
				}
				data.setExtendedDetentionTime(doc.getA17().getTime());
				data.setExtendedTime((int)((doc.getA17().getTime() - doc.getA16().getTime()) / 1000 / 60 / 60 / 24));
				data.setCaseCode(doc.getCaseid());
				data.setCaseName(doc.getB8());
				data.setPersonId(doc.getPersonid());
				data.setPersonName(doc.getB9());
			}
			taskClient.launchTask(TaskPojoJSONFmtUtil.getJSONStr(data));
			return SUCCESS;
		}else{
			return SUCCESS;
		}
		
	}
	
	/**
	 * 监视居住到期预警
	 */
	public String residentialSurveillanceWarning(){
		CommonData businessObj = (CommonData)TaskPojoJSONFmtUtil.getPojo(read(), CommonData.class);
		AlertRule ar = alertRuleService.findAlertRuleByCode("0000000013005");
		if(WARNING_STATUS_START.equals(ar.getStatus())){
			ResidentialSurveillanceData data = new ResidentialSurveillanceData();
			data.setBusinessId(businessObj.getBusinessId());
			data.setBusinessType(businessObj.getBusinessType());
			data.setType(businessObj.getType());
			data.setShowTime(ar.getPopWindowDuaring());
			data.setWay(ar.getWay());
			data.setAlertTimeAt(ar.getAlertTimeAt());
			DocJianShiJueDingZhiXingTongZhiShuNew doc = archivedFileService.findDocJianShiJueDingZhiXingTongZhiShuNewById(businessObj.getBusinessId());
			if(doc == null || doc.getA17() == null){
				return SUCCESS;
			}
			data.setSendDate(doc.getA17().getTime());
			data.setCaseCode(doc.getCaseid());
			data.setCaseName(doc.getA7());
			data.setPersonId(doc.getPersonid());
			data.setPersonName(doc.getA9());
			taskClient.launchTask(TaskPojoJSONFmtUtil.getJSONStr(data));
			return SUCCESS;
		}else{
			return SUCCESS;
		}
		
	}
	
	/**
	 * 传唤到期预警
	 */
	public String subpoenaWarning(){
		CommonData businessObj = (CommonData)TaskPojoJSONFmtUtil.getPojo(read(), CommonData.class);
		AlertRule ar = alertRuleService.findAlertRuleByCode("0000000013001");
		if(WARNING_STATUS_START.equals(ar.getStatus())){
			SubpoenaData data = new SubpoenaData();
			data.setBusinessId(businessObj.getBusinessId());
			data.setBusinessType(businessObj.getBusinessType());
			data.setType(businessObj.getType());
			data.setShowTime(ar.getPopWindowDuaring());
			data.setWay(ar.getWay());
			data.setAheadTime(ar.getAlertTimeBefore());
			if(businessObj.getBusinessType().equals(DocChuanHuanNew.class.getName())){
				DocChuanHuanNew doc = archivedFileService.findDocChuanHuanNewById(businessObj.getBusinessId());
				if(doc == null || doc.getB14() == null){
					return SUCCESS;
				}
				data.setPersonName(doc.getA7());
				data.setSuspectId(doc.getPersonid());
				data.setCaseCode(doc.getCaseid());
				CriminalBasicCase cbc = criminalCaseService.findCriminalCaseByCaseId(doc.getCaseid());
				data.setCaseName(cbc == null ? "" : cbc.getCaseName());
				data.setSendTime(doc.getB14().getTime());
			}else if(businessObj.getBusinessType().equals(DocChuanHuanNewXingShi2012.class.getName())){
				DocChuanHuanNewXingShi2012 doc = archivedFileService.findDocChuanHuanNewXingShi2012ById(businessObj.getBusinessId());
				if(doc == null || doc.getA22() == null){
					return SUCCESS;
				}
				data.setPersonName(doc.getA9());
				data.setSuspectId(doc.getPersonid());
				data.setCaseCode(doc.getA8());
				data.setCaseName(doc.getA7());
				data.setSendTime(doc.getA22().getTime());
			}
			taskClient.launchTask(TaskPojoJSONFmtUtil.getJSONStr(data));
			return SUCCESS;
		}else{
			return SUCCESS;
		}
		
	}
	
	/**
	 * 涉案物品到期预警
	 */
	public String susObjWarning(){
		CommonData businessObj = (CommonData)TaskPojoJSONFmtUtil.getPojo(read(), CommonData.class);
		AlertRule ar = alertRuleService.findAlertRuleByCode("0000000013008");
		if(WARNING_STATUS_START.equals(ar.getStatus())){
			SusObjData data = new SusObjData();
			data.setBusinessId(businessObj.getBusinessId());
			data.setBusinessType(businessObj.getBusinessType());
			data.setType(businessObj.getType());
			data.setShowTime(ar.getPopWindowDuaring());
			data.setWay(ar.getWay());
			data.setAheadTime(ar.getAlertTimeBefore());
			if(businessObj.getBusinessType().equals(DocKouYaJueDingNew2012.class.getName())){
				DocKouYaJueDingNew2012 doc = archivedFileService.findDocKouYaJueDingNew2012ById(businessObj.getBusinessId());
				if(doc == null || doc.getA19() == null){
					return SUCCESS;
				}
				data.setSendTime(doc.getA19().getTime());
				data.setCaseCode(doc.getCaseid());
				data.setSuspectId(doc.getPersonid());
				data.setPersonName(doc.getA9());
			}else if(businessObj.getBusinessType().equals(DocZhengJvBaoQuanJueDingNew.class.getName())){
				DocZhengJvBaoQuanJueDingNew doc = archivedFileService.findDocZhengJvBaoQuanJueDingNewById(businessObj.getBusinessId());
				if(doc == null || doc.getInputtime() == null){
					return SUCCESS;
				}
				data.setSendTime(doc.getInputtime().getTime());
				data.setCaseCode(doc.getCaseid());
				data.setSuspectId(doc.getPersonid());
				CriminalPerson person = criminalPersonService.findById(doc.getPersonid());
				if(person != null){
					data.setPersonName(person.getName());
				}
			}
			taskClient.launchTask(TaskPojoJSONFmtUtil.getJSONStr(data));
			return SUCCESS;
		}else{
			return SUCCESS;
		}
		
	}
	
}