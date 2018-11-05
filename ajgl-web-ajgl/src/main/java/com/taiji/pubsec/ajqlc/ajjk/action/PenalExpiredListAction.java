package com.taiji.pubsec.ajqlc.ajjk.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajgl.dbtx.task.util.Constant;
import com.taiji.pubsec.ajgl.dbtx.task.util.replenishDetectBuilder.pojo.ReplenishDetectBuilderData;
import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajgl.util.bean.OptionItemBean;
import com.taiji.pubsec.ajqlc.ajjk.action.bean.CaseDataBean;
import com.taiji.pubsec.ajqlc.ajjk.action.bean.SearchBean;
import com.taiji.pubsec.ajqlc.alert.model.AlertRule;
import com.taiji.pubsec.ajqlc.alert.service.IAlertRuleService;
import com.taiji.pubsec.ajqlc.sla.bean.PenalPersonServiceBean;
import com.taiji.pubsec.ajqlc.sla.model.CaseAttachedInfo;
import com.taiji.pubsec.ajqlc.sla.model.CriminalBasicCase;
import com.taiji.pubsec.ajqlc.sla.service.IArchivedFileService;
import com.taiji.pubsec.ajqlc.sla.service.ICaseAttachedInfoService;
import com.taiji.pubsec.ajqlc.sla.service.ICriminalCaseService;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.businesscomponent.organization.model.Person;
import com.taiji.pubsec.businesscomponent.organization.model.Unit;
import com.taiji.pubsec.businesscomponent.organization.service.IPersonService;
import com.taiji.pubsec.businesscomponent.organization.service.IUnitService;
import com.taiji.pubsec.common.tool.mission.client.core.TaskClient;
import com.taiji.pubsec.common.tool.mission.task.util.TaskPojoJSONFmtUtil;
import com.taiji.pubsec.common.web.action.ExportInfoReq;
import com.taiji.pubsec.common.web.bean.ZTreeBean;

/**
 * 刑事案件到期提醒查询Action
 * 
 * @author huangda
 *
 */
@Controller("penalExpiredListAction")
@Scope("prototype")
public class PenalExpiredListAction extends ReturnMessageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String FAZHIDADUI_CODE = "520199180000";	//法制大队编码

	@Resource
	private ICaseAttachedInfoService caseAttachedInfoService;
	@Resource
	private IUnitService unitService;
	@Resource
	private IPersonService personService;
	@Resource
	private IArchivedFileService archivedFileService;
	@Resource
	private IDictionaryItemService dictionaryItemService;
	@Resource
	private ICriminalCaseService criminalCaseService;
	
	@Resource	// 预警规则接口
	private IAlertRuleService alertRuleService;
	
	@Resource(name="dbtxTaskClient")
	private TaskClient taskClient;
	
	private static final String WARNING_STATUS_START = "0000000002002"; //预警规则状态：启用
	
	private SearchBean searchBean;
	private ExportInfoReq exportInfoReq = new ExportInfoReq();
	private List<ZTreeBean> ztreeList = new ArrayList<ZTreeBean>();
	private List<OptionItemBean> personBeanLst = new ArrayList<OptionItemBean>();
	private List<CaseDataBean> dataLst = new ArrayList<CaseDataBean>();
	private List<PenalPersonServiceBean> psBeanLst = new ArrayList<PenalPersonServiceBean>();
	
	public String initData() {
		Unit unit = unitService.findUnitByCode(FAZHIDADUI_CODE);
		List<Person> personList = personService.findAllByParams("select p from Person as p where p.organization.id = ?", new Object[]{unit.getId()});
		for(Person person: personList){
			OptionItemBean bean = new OptionItemBean();
			bean.setName(person.getName());
			bean.setId(person.getId());
			personBeanLst.add(bean);
		}
		return SUCCESS;
	}
	
	public String searchPenalCaseByPage(){
		 Calendar calendar = new GregorianCalendar(); 
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("caseClass", "刑事案件");
		map.put("queryTimeStart", searchBean.getCaseTimeStart());
		map.put("queryTimeEnd", searchBean.getCaseTimeEnd());
		map.put("ifArchive", searchBean.getAttention());
		map.put("attendUnit", searchBean.getSponsor());
		map.put("caseName", searchBean.getCaseName()); 
		map.put("caseCode", searchBean.getCaseCode()); 
		
		map.put("disposePerson", searchBean.getDisposePerson());
		
		Pager<PenalPersonServiceBean> casePager = caseAttachedInfoService.findPenalPersonServiceBeanByConditions(map, this.getStart() / this.getLength(), this.getLength());
		psBeanLst = casePager.getPageList();
		this.setTotalNum(casePager.getTotalNum());
		return SUCCESS;
	}
	
	public String exportExcel() throws IOException {
//		Map<String, Object> map = new HashMap<String, Object>(); 
//		map.put("caseClass", "刑事案件");
//		map.put("queryTime", searchBean.getSearchDate());
//		map.put("caseAttentionState", searchBean.getAttention());
//		map.put("attendUnit", searchBean.getSponsor());
//		map.put("caseName", searchBean.getCaseName()); 
//		map.put("note", searchBean.getRemark());
//		Pager<CriminalBasicCase> casePager = caseAttachedInfoService.findCriminalBasicCasesByCondition(map, 0, Integer.MAX_VALUE);
//		int i = 1;
//		for(CriminalBasicCase criminalBasicCase: casePager.getPageList()){
//			
//		}
//		//查询案件，同楼上search，不分页
//		ExcelUtil<PenalCaseBean> ex = new ExcelUtil<PenalCaseBean>();
//		String[] headers = { "序号", "案件编号", "案件名称", "办案单位", "主办侦查员", "法制审核人",
//				"案件关注状态", "维护人/维护时间", "拘传", "刑拘", "延期", "提请逮捕", "取保",
//				"监视居住", "释放", "逮捕", "移送起诉", "是否已归档", "备注" };
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//		String name = sdf.format(new Date());
//		name = name + ".xls";
//		// 创建ByteArrayOutputStream字节流
//		ByteArrayOutputStream bos=new ByteArrayOutputStream();
//		ex.exportExcel(headers, pcBeanLst, bos);
//		byte[] bytes = bos.toByteArray();
//		bos.close();
//		ByteArrayInputStream ins=new ByteArrayInputStream(bytes);
//		exportInfoReq.setIn(ins);
//		exportInfoReq.setName(name);
//		exportInfoReq.setLength(Long.valueOf(bytes.length));
//		ins.close();
		return "done";
	}

	public String saveVerifierAndRemark(){
		String str="";
		for(CaseDataBean caseDataBean: dataLst){
			if(null == caseDataBean){
				continue;
			}
			str+=caseDataBean.getCaseCode()+",";
			CaseAttachedInfo caseAttachedInfo = new CaseAttachedInfo();
			caseAttachedInfo.setDoPerson(caseDataBean.getDoPerson());
			if(caseDataBean.getOneRefundInvestigationTime() != null){
				caseAttachedInfo.setOneRefundInvestigationTime(new Date(caseDataBean.getOneRefundInvestigationTime()));
			}else{
				caseAttachedInfo.setOneRefundInvestigationTime(null);
			}
			if(caseDataBean.getTwoRefundInvestigationTime() != null){
				caseAttachedInfo.setTwoRefundInvestigationTime(new Date(caseDataBean.getTwoRefundInvestigationTime()));
			}else{
				caseAttachedInfo.setTwoRefundInvestigationTime(null);
			}
			caseAttachedInfoService.createCaseAttachedInfo(caseAttachedInfo, caseDataBean.getCaseCode());
			
			//TODO 退侦后补充侦查到期预警
			ReplenishDetectBuilderData data = new ReplenishDetectBuilderData();
			AlertRule ar = alertRuleService.findAlertRuleByCode("0000000013009");
			if(WARNING_STATUS_START.equals(ar.getStatus())){
				String[] alertTimes = ar.getAlertTimeAt().split(";");
				data.setWay(ar.getWay());
				data.setShowTime(ar.getPopWindowDuaring());
				data.setBusinessId(caseDataBean.getCaseCode());
				data.setBusinessType(CaseAttachedInfo.class.getName());
				data.setType(Constant.TASK_TYPE_REPLENISHDETECT);
				data.setCaseCode(caseDataBean.getCaseCode());
				CriminalBasicCase basicCase = criminalCaseService.findCriminalCaseById(caseDataBean.getCaseCode());
				data.setCaseName(basicCase.getCaseName());
				if(caseAttachedInfo.getTwoRefundInvestigationTime() != null){
					data.setAlertTimeAt(alertTimes[1]);
					data.setSendDate(caseAttachedInfo.getTwoRefundInvestigationTime().getTime());
					
					taskClient.launchTask(TaskPojoJSONFmtUtil.getJSONStr(data));
				}else if(caseAttachedInfo.getOneRefundInvestigationTime() != null){
					data.setAlertTimeAt(alertTimes[0]);
					data.setSendDate(caseAttachedInfo.getOneRefundInvestigationTime().getTime());
					
					taskClient.launchTask(TaskPojoJSONFmtUtil.getJSONStr(data));
				}
			}
			
		}
		str.substring(0, str.length()-1);
		this.creatCaseManagementLog("案件监控管理 /刑事或行政案件到期提醒表", "修改案件到期提醒信息(案件ID:"+str+")");
		return SUCCESS;
	}
	
	public String acquireSuspectName(String s){
		String name = "";
		int start = 0;
		if(s == null){
			return null;
		}else{
			for(int i = 0; i < s.length(); i++){
				if(s.charAt(i) == '人'){
					start = i;
				}
				if(s.charAt(i) == '，'){
					name = s.substring(start+1, i);
					break;
				}
			}
		}
		return name;
	}
	
	public SearchBean getSearchBean() {
		return searchBean;
	}

	public void setSearchBean(SearchBean searchBean) {
		this.searchBean = searchBean;
	}

	public List<OptionItemBean> getPersonBeanLst() {
		return personBeanLst;
	}

	public void setPersonBeanLst(List<OptionItemBean> personBeanLst) {
		this.personBeanLst = personBeanLst;
	}

	public List<ZTreeBean> getZtreeList() {
		return ztreeList;
	}

	public void setZtreeList(List<ZTreeBean> ztreeList) {
		this.ztreeList = ztreeList;
	}

	public List<CaseDataBean> getDataLst() {
		return dataLst;
	}

	public void setDataLst(List<CaseDataBean> dataLst) {
		this.dataLst = dataLst;
	}

	public ExportInfoReq getExportInfoReq() {
		return exportInfoReq;
	}

	public void setExportInfoReq(ExportInfoReq exportInfoReq) {
		this.exportInfoReq = exportInfoReq;
	}

	public List<PenalPersonServiceBean> getPsBeanLst() {
		return psBeanLst;
	}

	public void setPsBeanLst(List<PenalPersonServiceBean> psBeanLst) {
		this.psBeanLst = psBeanLst;
	}

}
