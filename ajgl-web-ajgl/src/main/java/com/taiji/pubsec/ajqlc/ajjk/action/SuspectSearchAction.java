package com.taiji.pubsec.ajqlc.ajjk.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajqlc.ajjk.action.bean.CaseExecutionProcessBean;
import com.taiji.pubsec.ajqlc.ajjk.action.bean.CaseSupectRelationBean;
import com.taiji.pubsec.ajqlc.ajjk.action.bean.CriminalBasicCaseBean;
import com.taiji.pubsec.ajqlc.ajjk.action.bean.CriminalPersonBean;
import com.taiji.pubsec.ajqlc.ajjk.action.bean.CriminalPersonSearchBean;
import com.taiji.pubsec.ajqlc.sla.model.CaseExecutionProcess;
import com.taiji.pubsec.ajqlc.sla.model.CaseSupectRelation;
import com.taiji.pubsec.ajqlc.sla.model.CriminalBasicCase;
import com.taiji.pubsec.ajqlc.sla.model.CriminalPerson;
import com.taiji.pubsec.ajqlc.sla.service.ICaseExecutionProcessService;
import com.taiji.pubsec.ajqlc.sla.service.ICriminalCaseService;
import com.taiji.pubsec.ajqlc.sla.service.ICriminalPersonService;
import com.taiji.pubsec.ajqlc.sla.service.ICriminalSuspectService;
import com.taiji.pubsec.ajqlc.util.CaseBeanCopierConverter;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryItem;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.common.tool.datetime.DateTimeUtil;

/**
 * 嫌疑人查询Action
 * 
 * @author huangda
 *
 */
@Controller("suspectSearchAction")
@Scope("prototype")
public class SuspectSearchAction extends ReturnMessageAction {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SuspectSearchAction.class);
	@Resource
	private ICriminalCaseService criminalCaseService;
	@Resource
	private ICriminalSuspectService criminalSuspectService;
	@Resource
	private IDictionaryItemService dictionaryItemService;
	@Resource
	private ICriminalPersonService criminalPersonService;
	/**
	 * 案件执行过程接口
	 */
	@Resource
	private ICaseExecutionProcessService caseExecutionProcessService;
	
	private CriminalPersonSearchBean criminalPersonSearchBean = new CriminalPersonSearchBean();
	private List<CriminalPersonSearchBean> cpsBeanLst = new ArrayList<CriminalPersonSearchBean>();
	private String dataId;
	private String personState;
	private String dispose;
	private String suspectName;	//嫌疑人名字
	private CriminalPersonBean criminalPersonBean = new CriminalPersonBean();
	private List<CriminalBasicCaseBean> criminalBasicCaseBeanLst = new ArrayList<CriminalBasicCaseBean>();
	private CaseSupectRelationBean caseSupectRelationBean = new CaseSupectRelationBean();
	private Map<String,List<CaseExecutionProcessBean>> caseExecutionProcessBeanMap = new HashMap<String,List<CaseExecutionProcessBean>>();
	
	private static void copyBean(Object source, Object target, String dateFmt) {
		BeanCopier copier = BeanCopier.create(source.getClass(),
				target.getClass(), true);
		CaseBeanCopierConverter bc = new CaseBeanCopierConverter(dateFmt);
		copier.copy(source, target, bc);
	}

	public String searchAllSuspectByPage() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", criminalPersonSearchBean.getName());
		map.put("sex", criminalPersonSearchBean.getSex());
		if(criminalPersonSearchBean.getBirthdayStart() == null ){
			map.put("birthdayStart", criminalPersonSearchBean.getBirthdayStart());
		}else{
			map.put("birthdayStart", new Date(Long.parseLong(criminalPersonSearchBean.getBirthdayStart())));
		}
		if(criminalPersonSearchBean.getBirthdayEnd() == null){
			map.put("birthdayEnd", criminalPersonSearchBean.getBirthdayEnd());
		}else{
			map.put("birthdayEnd", new Date(Long.parseLong(criminalPersonSearchBean.getBirthdayEnd())));
		}
		map.put("idNumber", criminalPersonSearchBean.getIdNumber());
		map.put("address", criminalPersonSearchBean.getAddress());
		map.put("nativePlace", criminalPersonSearchBean.getNativePlace());
		map.put("tone", criminalPersonSearchBean.getTone());
		map.put("cases", criminalPersonSearchBean.getCases());
		
		map.put("caseCode", criminalPersonSearchBean.getCaseCode());
		map.put("caseName", criminalPersonSearchBean.getCaseName());
		
		map.put("disposePerson", criminalPersonSearchBean.getDisposePerson());
		
		Pager<CaseSupectRelation> pager = criminalSuspectService.findCriminalSuspectByCondition(map,
						this.getStart() / this.getLength(), this.getLength());
		for (CaseSupectRelation cbc : pager.getPageList()) {
			cpsBeanLst.add(criminalPersonToBean(cbc));
		}
		this.setTotalNum(pager.getTotalNum());
		return SUCCESS;
	}
	
	private CriminalPersonSearchBean criminalPersonToBean(CaseSupectRelation cr){
		CriminalPersonSearchBean bean = new CriminalPersonSearchBean();
		CriminalPerson cp=new CriminalPerson();
		cp=cr.getPerson();
		bean.setId(cp.getPersonId());
		bean.setName(cp.getName() == null ? "" : cp.getName());
//		List<CaseSupectRelation> csrLst = cp.getCaseSupectRelations();
//		if(!csrLst.isEmpty()){
//			CaseSupectRelation csr = csrLst.get(0);
//			bean.setDisposed(StringUtils.isBlank(csr.getApproach())?"否":"是");
//			bean.setCatched(csr.getPersonState());
////			DictionaryItem di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("ryzt", csr.getPersonState(), null);
////			if(di != null){
////				bean.setCatched(di.getName());
////			}
//		}
		bean.setSex(cp.getSex() == null ? "" : cp.getSex());
//		//性别
//		DictionaryItem di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("xb", bean.getSex(), null);
//		if(di != null){
//			bean.setSex(di.getName());
//		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if(cp.getBirthdayStart() != null){
			bean.setBirthday(df.format(cp.getBirthdayStart()));
		}else{
			bean.setBirthday("");
		}
		bean.setIdNumber(cp.getIdcardNo() == null ? "" : cp.getIdcardNo());
		bean.setTelephone(cp.getTelephone() == null ? "" : cp.getTelephone());
		bean.setNativePlace(cp.getDoorDetail() == null ? "" : cp.getDoorDetail());
		bean.setAddress(cp.getAddressDetail() == null ? "" : cp.getAddressDetail());
		bean.setTone(StringUtils.isBlank(cp.getTone()) ? "无" : cp.getTone());
		bean.setStature(cp.getStaturest() == null ? "无" : String.valueOf(cp.getStaturest()));
		bean.setWeight(cp.getAvoirdupois() == null ? "无" : String.valueOf(cp.getAvoirdupois()));
		bean.setCaseCode(cr.getBasicCase().getCaseCode());
		bean.setCaseName(cr.getBasicCase().getCaseName());
		return bean;
	}

	private CriminalBasicCaseBean entityToBean(CaseSupectRelation csr, CriminalBasicCase cbc){
		CriminalBasicCaseBean bean = new CriminalBasicCaseBean();
		bean.setCaseCode(cbc.getCaseCode() == null ? "" : cbc.getCaseCode());
		bean.setCaseName(cbc.getCaseName() == null ? "" : cbc.getCaseName());
		bean.setCaseState(cbc.getCaseState() == null ? "" : cbc
				.getCaseState());
//		DictionaryItem di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("ajzt", cbc.getCaseState(), null);
//		if(di != null){
//			bean.setCaseState(di.getName());
//		}
		if (cbc.getCaseTimeStart() != null) {
			bean.setCaseTimeStart(cbc.getCaseTimeStart().getTime());
		}
		bean.setSuspectPersonId(csr.getId());
		return bean;
	}
	
	public String searchPersonInfo() {
		CriminalPerson cp = criminalSuspectService.findCriminalSuspectById(dataId);
		copyBean(cp, criminalPersonBean, null);
//		//性别
//		DictionaryItem di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("xb", criminalPersonBean.getSex(), null);
//		if(di != null){
//			criminalPersonBean.setSex(di.getName());
//		}
//		//出生地代码
//		di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("xzqh", criminalPersonBean.getBirthCode(), null);
//		if(di != null){
//			criminalPersonBean.setBirthCode(di.getName());
//		}
//		//户籍地代码
//		di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("xzqh", criminalPersonBean.getDoor(), null);
//		if(di != null){
//			criminalPersonBean.setDoor(di.getName());
//		}
//		//口音
//		di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("xzqh", criminalPersonBean.getTone(), null);
//		if(di != null){
//			criminalPersonBean.setTone(di.getName());
//		}
//		//现住址代码
//		di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("xzqh", criminalPersonBean.getAddress(), null);
//		if(di != null){
//			criminalPersonBean.setAddress(di.getName());
//		}
		List<CaseSupectRelation> csrLst = criminalSuspectService.findCaseSupectRelationBySuspectId(cp.getPersonId());
		if(!csrLst.isEmpty()){
			CaseSupectRelation csr = csrLst.get(0);
			personState = csr.getPersonState() == null ? "" : csr.getPersonState();
//			di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("ryzt", csr.getPersonState(), null);
//			if(di != null){
//				personState = di.getName();
//			}
			dispose = StringUtils.isBlank(csr.getApproach())?"无":csr.getApproach();
			
			List<CaseSupectRelation> lst = criminalSuspectService.findCaseSupectRelationBySuspectId(dataId);
			for(CaseSupectRelation csrTemp : lst){
				CriminalBasicCase cbc = criminalSuspectService.findCriminalBasicCaseByCaseSupectRelationId(csrTemp.getId());
				if(cbc!=null){
					criminalBasicCaseBeanLst.add(entityToBean(csrTemp, cbc));
				}
			}
			//查询嫌疑人过程信息
			List<CaseExecutionProcess> caseExecutionProcess = caseExecutionProcessService.findCaseExecutionProcessByPerson(cp.getPersonId());
			for(CaseExecutionProcess c : caseExecutionProcess){
				CaseExecutionProcessBean bean = new CaseExecutionProcessBean();
				copyBean(c, bean, "yyyy-MM-dd HH:mm");
				CriminalBasicCase cbc = criminalCaseService.findCriminalCaseByCaseId(bean.getCaseId());
				if(cbc != null){
					bean.setCaseName(cbc.getCaseName());
				}
				if(bean.getPersonId() != null && criminalPersonService.findById(bean.getPersonId()) != null){
					bean.setPersonName(criminalPersonService.findById(bean.getPersonId()).getName());
				}else{
					bean.setPersonName("无");
				}
				if(caseExecutionProcessBeanMap.size() == 0 || !caseExecutionProcessBeanMap.containsKey(bean.getCaseId())){
					List<CaseExecutionProcessBean> cepBlst = new ArrayList<CaseExecutionProcessBean>();
					cepBlst.add(bean);
					caseExecutionProcessBeanMap.put(bean.getCaseId(), cepBlst);
				}else{
					caseExecutionProcessBeanMap.get(bean.getCaseId()).add(bean);
				}
			}
		}
		this.creatCaseManagementLog("案件监控管理/涉案嫌疑人查询", "打开嫌疑人详情页面(涉案嫌疑人id:"+dataId+")");
		return SUCCESS;
	}
	
	public String searchPersonDetail() {
		CriminalPerson cp = criminalSuspectService.findCriminalSuspectById(dataId);
		copyBean(cp, criminalPersonBean, null);
//		//性别
//		DictionaryItem di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("xb", criminalPersonBean.getSex(), null);
//		if(di != null){
//			criminalPersonBean.setSex(di.getName());
//		}
//		//出生地代码
//		di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("xzqh", criminalPersonBean.getBirthCode(), null);
//		if(di != null){
//			criminalPersonBean.setBirthCode(di.getName());
//		}
//		//户籍地代码
//		di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("xzqh", criminalPersonBean.getDoor(), null);
//		if(di != null){
//			criminalPersonBean.setDoor(di.getName());
//		}
//		//口音
//		di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("xzqh", criminalPersonBean.getTone(), null);
//		if(di != null){
//			criminalPersonBean.setTone(di.getName());
//		}
//		//现住址代码
//		di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("xzqh", criminalPersonBean.getAddress(), null);
//		if(di != null){
//			criminalPersonBean.setAddress(di.getName());
//		}
		return SUCCESS;
	}
	
	public String searchSuspectDetail() {
		CaseSupectRelation csr = criminalSuspectService.findCaseSupectRelationById(dataId);
		copyBean(csr, caseSupectRelationBean, null);
//		DictionaryItem di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("ryzt", caseSupectRelationBean.getPersonState(), null);
//		if(di != null){
//			caseSupectRelationBean.setPersonState(di.getName());
//		}
		this.creatCaseManagementLog("案件监控管理/涉案嫌疑人全流程监控查看", "打开人员涉案信息页面(案件嫌疑人关系id:"+dataId+")");
		return SUCCESS;
	}
	
	
	
	
	public CriminalPersonSearchBean getCriminalPersonSearchBean() {
		return criminalPersonSearchBean;
	}

	public void setCriminalPersonSearchBean(
			CriminalPersonSearchBean criminalPersonSearchBean) {
		this.criminalPersonSearchBean = criminalPersonSearchBean;
	}

	public List<CriminalPersonSearchBean> getCpsBeanLst() {
		return cpsBeanLst;
	}

	public void setCpsBeanLst(List<CriminalPersonSearchBean> cpsBeanLst) {
		this.cpsBeanLst = cpsBeanLst;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public CriminalPersonBean getCriminalPersonBean() {
		return criminalPersonBean;
	}

	public void setCriminalPersonBean(CriminalPersonBean criminalPersonBean) {
		this.criminalPersonBean = criminalPersonBean;
	}

	public CaseSupectRelationBean getCaseSupectRelationBean() {
		return caseSupectRelationBean;
	}

	public void setCaseSupectRelationBean(CaseSupectRelationBean caseSupectRelationBean) {
		this.caseSupectRelationBean = caseSupectRelationBean;
	}

	public List<CriminalBasicCaseBean> getCriminalBasicCaseBeanLst() {
		return criminalBasicCaseBeanLst;
	}

	public void setCriminalBasicCaseBeanLst(
			List<CriminalBasicCaseBean> criminalBasicCaseBeanLst) {
		this.criminalBasicCaseBeanLst = criminalBasicCaseBeanLst;
	}

	public String getPersonState() {
		return personState;
	}

	public void setPersonState(String personState) {
		this.personState = personState;
	}

	public String getDispose() {
		return dispose;
	}

	public void setDispose(String dispose) {
		this.dispose = dispose;
	}

	public String getSuspectName() {
		return suspectName;
	}

	public void setSuspectName(String suspectName) {
		this.suspectName = suspectName;
	}

	public Map<String, List<CaseExecutionProcessBean>> getCaseExecutionProcessBeanMap() {
		return caseExecutionProcessBeanMap;
	}

	public void setCaseExecutionProcessBeanMap(
			Map<String, List<CaseExecutionProcessBean>> caseExecutionProcessBeanMap) {
		this.caseExecutionProcessBeanMap = caseExecutionProcessBeanMap;
	}

}
