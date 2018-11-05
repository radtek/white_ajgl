package com.taiji.pubsec.ajqlc.ajjk.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajgl.util.bean.OptionItemBean;
import com.taiji.pubsec.ajqlc.ajjk.action.bean.AlarmInfoBean;
import com.taiji.pubsec.ajqlc.ajjk.action.bean.CaseExecutionProcessBean;
import com.taiji.pubsec.ajqlc.ajjk.action.bean.CaseSearchBean;
import com.taiji.pubsec.ajqlc.ajjk.action.bean.CaseSupectRelationBean;
import com.taiji.pubsec.ajqlc.ajjk.action.bean.CriminalBasicCaseBean;
import com.taiji.pubsec.ajqlc.ajjk.action.bean.CriminalBasicCaseDetailBean;
import com.taiji.pubsec.ajqlc.ajjk.action.bean.CriminalObjectBean;
import com.taiji.pubsec.ajqlc.ajjk.action.bean.ImpoundedObjectBean;
import com.taiji.pubsec.ajqlc.ajjk.action.bean.SufferCaseRelationBean;
import com.taiji.pubsec.ajqlc.ajjk.action.bean.docbean.DocZhengJvBiLuPhotoBean;
import com.taiji.pubsec.ajqlc.sawp.model.ImpoundedObject;
import com.taiji.pubsec.ajqlc.sawp.service.IImpoundedObjectService;
import com.taiji.pubsec.ajqlc.sla.model.AlarmInfo;
import com.taiji.pubsec.ajqlc.sla.model.ArchivedFile;
import com.taiji.pubsec.ajqlc.sla.model.CaseExecutionProcess;
import com.taiji.pubsec.ajqlc.sla.model.CaseSupectRelation;
import com.taiji.pubsec.ajqlc.sla.model.CriminalBasicCase;
import com.taiji.pubsec.ajqlc.sla.model.CriminalObject;
import com.taiji.pubsec.ajqlc.sla.model.CriminalPerson;
import com.taiji.pubsec.ajqlc.sla.model.DocZhengJvBiLuPhoto;
import com.taiji.pubsec.ajqlc.sla.model.SufferCaseRelation;
import com.taiji.pubsec.ajqlc.sla.service.IArchivedFileService;
import com.taiji.pubsec.ajqlc.sla.service.ICaseExecutionProcessService;
import com.taiji.pubsec.ajqlc.sla.service.ICriminalCaseService;
import com.taiji.pubsec.ajqlc.sla.service.ICriminalPersonService;
import com.taiji.pubsec.ajqlc.util.CaseBeanCopierConverter;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryItem;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.businesscomponent.organization.model.Organization;
import com.taiji.pubsec.businesscomponent.organization.model.Unit;
import com.taiji.pubsec.businesscomponent.organization.service.IDepartmentService;
import com.taiji.pubsec.businesscomponent.organization.service.IUnitService;
import com.taiji.pubsec.common.web.bean.ZTreeBean;

/**
 * 案件查询Action
 * 
 * @author huangda
 *
 */
@Controller("caseSearchAction")
@Scope("prototype")
public class CaseSearchAction extends ReturnMessageAction {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CaseSearchAction.class);

	@Resource
	private ICriminalCaseService criminalCaseService;
	@Resource
	private ICriminalPersonService criminalPersonService;
	@Resource
	private IDictionaryItemService dictionaryItemService;
	@Resource
	private IDepartmentService departmentService;
	@Resource
	private IUnitService unitService;
	@Resource
	private IImpoundedObjectService impoundedObjectService;
	@Resource
	private ICaseExecutionProcessService caseExecutionProcessService;
	@Resource
	private IArchivedFileService archivedFileService;
	
	private String caseCode;
	private String code;
	private List<OptionItemBean> writBeanLst = new ArrayList<OptionItemBean>();
	private CaseSearchBean caseSearchBean = new CaseSearchBean();
	private List<CriminalBasicCaseBean> cbcBeanLst = new ArrayList<CriminalBasicCaseBean>();
	private CriminalBasicCaseDetailBean criminalBasicCaseDetailBean = new CriminalBasicCaseDetailBean();
	private List<OptionItemBean> dictionaryItemLst = new ArrayList<OptionItemBean>();
	private List<DocZhengJvBiLuPhotoBean> zjblBeanLst = new ArrayList<DocZhengJvBiLuPhotoBean>();
	private List<ZTreeBean> ztreeList = new ArrayList<ZTreeBean>();
	
	private String blid;
	private String base64Str;
	
	public String searchZjbl(){
		//通过blid查询base64Str
		DocZhengJvBiLuPhoto photo = archivedFileService.findDocZhengJvBiLuPhotoByPhotoId(blid);
		base64Str = new String(Base64.encodeBase64(photo.getPhoto(), false));
		return SUCCESS;
	}
	
	private static void copyBean(Object source, Object target, String dateFmt) {
		BeanCopier copier = BeanCopier.create(source.getClass(),
				target.getClass(), true);
		CaseBeanCopierConverter bc = new CaseBeanCopierConverter(dateFmt);
		copier.copy(source, target, bc);
	}
	
	public String findCommunityByUnitCode(){
		List<DictionaryItem> list = criminalCaseService.findCommunityByUnitCode(code);
		for(DictionaryItem item: list){
			OptionItemBean bean = new OptionItemBean();
			bean.setId(item.getId());
			bean.setName(item.getName());
			bean.setCode(item.getCode());
			dictionaryItemLst.add(bean);
		}
		return SUCCESS;
	}
	
	public String queryPopedom(){
		List<Unit> unitList = unitService.findAllByParams("select u from Unit as u where u.type = ?", new Object[]{"0"});
		for(Unit unit: unitList){
			OptionItemBean bean = new OptionItemBean();
			bean.setId(unit.getId());
			bean.setName(unit.getShortName());
			bean.setCode(unit.getCode());
			dictionaryItemLst.add(bean);
		}
		return SUCCESS;
	}
	
	public String queryUnit(){
		List<Unit> unitList = unitService.findAll();//AllByParams("select u from Unit as u where u.type = ?", new Object[]{"0"});
		for(Unit unit: unitList){
			ZTreeBean bean = new ZTreeBean();
			bean.setId(unit.getId());
			Map<Object,Object> diyMap = new HashMap<Object,Object>();
			diyMap.put("code", unit.getCode());
			bean.setDiyMap(diyMap);
			if(unit.getSuperOrg() != null){
				bean.setpId(unit.getSuperOrg().getId());
			}else{
				bean.setpId("");
			}
			List<Organization> orgs = unit.getSubOrgs();
			boolean flag = true;
			int i = 0;
			for(Organization org: orgs){
				if(departmentService.findById(org.getId()) != null){
					i++;
				}
			}
			if(i == orgs.size()){
				flag = false;
			}
			if(unit.getSubOrgs() != null && unit.getSubOrgs().size() > 0 && flag){
				bean.setIsParent("true");
			}else{
				bean.setIsParent("false");
			}
			bean.setName(unit.getShortName());
			bean.setIcon("../images/xtgl_icon/ztree-icon_dw.png");
			ztreeList.add(bean);
		}
		return SUCCESS;
	}
	
	
	public String initCase() {
		CriminalBasicCase cbc = criminalCaseService
				.findCriminalCaseByCaseId(caseCode);
		Map<CriminalBasicCase, String> map = criminalCaseService.findRelatedCriminalBasicCaseById(cbc.getCaseCode());
		Iterator<Map.Entry<CriminalBasicCase, String>> entries = map.entrySet().iterator();
		if(entries != null){
			while (entries.hasNext()) {  
			    Map.Entry<CriminalBasicCase, String> entry = entries.next();  
			    CriminalBasicCaseBean bean = criminalBasicCaseToBean(entry.getKey());
			    bean.setPrincipal(entry.getValue());
			    cbcBeanLst.add(bean);
			}
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
			copyBean(cbc, criminalBasicCaseDetailBean, null);
			if(cbc.getCaseAttachedInfo() != null){
				if(cbc.getCaseAttachedInfo().getAcceptingTime() != null){
					criminalBasicCaseDetailBean.setFilingTime(sdf.format(cbc.getCaseAttachedInfo().getAcceptingTime()));
				}
				if(cbc.getCaseAttachedInfo().getFilingTime() != null){
					criminalBasicCaseDetailBean.setFilingTime(sdf.format(cbc.getCaseAttachedInfo().getFilingTime()));
				}
			}
			
			AlarmInfoBean aiBean = new AlarmInfoBean();
			AlarmInfo ai = cbc.getAlarmInfo();
			if(ai != null){
				copyBean(ai, aiBean, null);
			}else{
				copyBean(aiBean, aiBean, null);
			}
			criminalBasicCaseDetailBean.setAlarmInfoObj(aiBean);
//			//状态
//			DictionaryItem di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("ajzt", criminalBasicCaseDetailBean.getCaseState(), null);
//			if(di != null){
//				criminalBasicCaseDetailBean.setCaseState(di.getName());
//			}
//			//类别
//			di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("ajlb", criminalBasicCaseDetailBean.getCaseSort(), null);
//			if(di != null){
//				criminalBasicCaseDetailBean.setCaseSort(di.getName());
//			}
//			//性质
//			di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("ajxz", criminalBasicCaseDetailBean.getCaseKind(), null);
//			if(di != null){
//				criminalBasicCaseDetailBean.setCaseKind(di.getName());
//			}
//			//辖区
//			Unit u = unitService.findUnitByCode(criminalBasicCaseDetailBean.getAlarmInfoObj().getPopedom());
//			if(u != null){
//				criminalBasicCaseDetailBean.getAlarmInfoObj().setPopedom(u.getShortName());
//			}
//			//发案社区
//			di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("ajsq", criminalBasicCaseDetailBean.getCommunity(), null);
//			if(di != null){
//				criminalBasicCaseDetailBean.setCommunity(di.getName());
//			}
//			//办理单位
//			if(!"".equals(criminalBasicCaseDetailBean.getDqbldw())){
//				Unit unit = unitService.findUnitByCode(criminalBasicCaseDetailBean.getDqbldw());
//				if(unit != null){
//					criminalBasicCaseDetailBean.setDqbldw(unit.getShortName());
//				}
//				
//			}
			for (CriminalObject obj : cbc.getCriminalObjects()) {
				CriminalObjectBean temp = new CriminalObjectBean();
				copyBean(obj, temp, null);
				criminalBasicCaseDetailBean.getCriminalObjectLst().add(temp);
			}
			for (SufferCaseRelation obj : cbc.getSufferCaseRelations()) {
				SufferCaseRelationBean temp = new SufferCaseRelationBean();
				copyBean(obj, temp, null);
				if(obj.getPerson() != null){
					copyBean(obj.getPerson(), temp.getCriminalPerson(), null);
//					//性别
//					di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("xb", temp.getCriminalPerson().getSex(), null);
//					if(di != null){
//						temp.getCriminalPerson().setSex(di.getName());
//					}
//					//出生地代码
//					di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("xzqh", temp.getCriminalPerson().getBirthCode(), null);
//					if(di != null){
//						temp.getCriminalPerson().setBirthCode(di.getName());
//					}
//					//户籍地代码
//					di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("xzqh", temp.getCriminalPerson().getDoor(), null);
//					if(di != null){
//						temp.getCriminalPerson().setDoor(di.getName());
//					}
//					//口音
//					di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("xzqh", temp.getCriminalPerson().getTone(), null);
//					if(di != null){
//						temp.getCriminalPerson().setTone(di.getName());
//					}
//					//现住址代码
//					di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("xzqh", temp.getCriminalPerson().getAddress(), null);
//					if(di != null){
//						temp.getCriminalPerson().setAddress(di.getName());
//					}
				}else{
					copyBean(temp.getCriminalPerson(), temp.getCriminalPerson(), null);
				}
				criminalBasicCaseDetailBean.getSufferCaseRelationLst()
				.add(temp);
			}
			for (CaseSupectRelation obj : cbc.getCaseSupectRelations()) {
				CaseSupectRelationBean temp = new CaseSupectRelationBean();
				copyBean(obj, temp, null);
				//查询嫌疑人过程信息
				List<CaseExecutionProcess> caseExecutionProcess = caseExecutionProcessService.findCaseExecutionProcessByPerson(obj.getPerson_id());
				for(CaseExecutionProcess c : caseExecutionProcess){
					if(!caseCode.equals(c.getCaseId())){
						continue;
					}
					CaseExecutionProcessBean bean = new CaseExecutionProcessBean();
					copyBean(c, bean, "yyyy-MM-dd HH:mm");
					bean.setCaseName(criminalCaseService.findCriminalCaseByCaseId(bean.getCaseId()).getCaseName());
					if(bean.getPersonId() != null && criminalPersonService.findById(bean.getPersonId()) != null){
						bean.setPersonName(criminalPersonService.findById(bean.getPersonId()).getName());
					}else{
						bean.setPersonName("无");
					}
					if(temp.getCaseExecutionProcessBeanMap().size() == 0 || !temp.getCaseExecutionProcessBeanMap().containsKey(bean.getCaseId())){
						List<CaseExecutionProcessBean> cepBlst = new ArrayList<CaseExecutionProcessBean>();
						cepBlst.add(bean);
						temp.getCaseExecutionProcessBeanMap().put(bean.getCaseId(), cepBlst);
					}else{
						temp.getCaseExecutionProcessBeanMap().get(bean.getCaseId()).add(bean);
					}
				}
//				di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("ryzt", temp.getPersonState(), null);
//				if(di != null){
//					temp.setPersonState(di.getName());
//				}
				if(obj.getPerson() != null){
					copyBean(obj.getPerson(), temp.getCriminalPerson(), null);
//					//性别
//					di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("xb", temp.getCriminalPerson().getSex(), null);
//					if(di != null){
//						temp.getCriminalPerson().setSex(di.getName());
//					}
//					//出生地代码
//					di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("xzqh", temp.getCriminalPerson().getBirthCode(), null);
//					if(di != null){
//						temp.getCriminalPerson().setBirthCode(di.getName());
//					}
//					//户籍地代码
//					di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("xzqh", temp.getCriminalPerson().getDoor(), null);
//					if(di != null){
//						temp.getCriminalPerson().setDoor(di.getName());
//					}
//					//口音
//					di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("xzqh", temp.getCriminalPerson().getTone(), null);
//					if(di != null){
//						temp.getCriminalPerson().setTone(di.getName());
//					}
//					//现住址代码
//					di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("xzqh", temp.getCriminalPerson().getAddress(), null);
//					if(di != null){
//						temp.getCriminalPerson().setAddress(di.getName());
//					}
				}else{
					copyBean(temp.getCriminalPerson(), temp.getCriminalPerson(), null);
				}
				
				criminalBasicCaseDetailBean.getCaseSupectRelationLst().add(temp);
			}
			Map<String, List<ImpoundedObject>> tempMap = impoundedObjectService.findImpoundedObjectsByCaseId(cbc.getCaseCode());
			Iterator<Entry<String, List<ImpoundedObject>>> iter = tempMap.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, List<ImpoundedObject>> entry = (Entry<String, List<ImpoundedObject>>) iter.next();
				;
				List<ImpoundedObjectBean> tempBeanLst = new ArrayList<ImpoundedObjectBean>();
				for(ImpoundedObject temp : entry.getValue()){
					ImpoundedObjectBean tempBean = new ImpoundedObjectBean();
					copyBean(temp, tempBean, "yyyy-MM-dd HH:mm");
					tempBeanLst.add(tempBean);
				}
				criminalBasicCaseDetailBean.getImpoundedObjectMap().put(entry.getKey(), tempBeanLst);
			}
			for (CaseExecutionProcess obj : cbc.getCaseExecutionProcesses()) {
				CaseExecutionProcessBean temp = new CaseExecutionProcessBean();
				copyBean(obj, temp, "yyyy-MM-dd");
				temp.setCaseName(criminalCaseService.findCriminalCaseByCaseId(obj.getCaseId()).getCaseName());
				if(obj.getPersonId() != null){
					CriminalPerson cp = criminalPersonService.findById(obj.getPersonId());
					if(cp != null && !StringUtils.isBlank(cp.getName())){
						temp.setPersonName(cp.getName());
					}else{
						temp.setPersonName("无");
					}
				}else{
					temp.setPersonName("无");
				}
				criminalBasicCaseDetailBean.getCaseExecutionProcessLst().add(temp);
			}
			//查询所有文书
			List<ArchivedFile> afLst = cbc.getArchivedFiles();
			if(afLst != null){
				for(ArchivedFile af : afLst){
					OptionItemBean oib = new OptionItemBean();
					oib.setId(af.getId());
					oib.setName(af.getName());
					writBeanLst.add(oib);
				}
			}
			//查询所有证据笔录
			List<DocZhengJvBiLuPhoto> zjblLst = cbc.getDocZhengJvBiLuPhotos();
			if(zjblLst != null){
				for(DocZhengJvBiLuPhoto doc: zjblLst){
					DocZhengJvBiLuPhotoBean zjblBean = new DocZhengJvBiLuPhotoBean();
					zjblBean.setId(doc.getId());
					zjblBean.setBaseName(doc.getBaseName());
					zjblBean.setSort(doc.getSort());
					try {
						zjblBean.setPhotoCopy(new String (Base64.encodeBase64(doc.getPhoto(), false)));
					} catch (Exception e) {
						LOGGER.debug("证据笔录查询照片附件转Base64异常",e);
					}
					zjblBeanLst.add(zjblBean);
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		this.creatCaseManagementLog("案件监控管理/案件查询", "打开案件详情页面(案件编号:"+caseCode+")");
		return SUCCESS;
	}
	
	public String searchCaseByPageForHAR() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("caseCode", caseSearchBean.getCaseCode());
		map.put("caseName", caseSearchBean.getCaseName());
		map.put("disposePerson", caseSearchBean.getDisposePerson());
		Pager<CriminalBasicCase> pager = criminalCaseService
				.findCriminalBasicCasesByQueryConditions(map,
						this.getStart() / this.getLength(), this.getLength());
		for (CriminalBasicCase cbc : pager.getPageList()) {
			cbcBeanLst.add(criminalBasicCaseToBean(cbc));
		}
		this.setTotalNum(pager.getTotalNum());
		return SUCCESS;
	}
	public String searchAllCaseByPage() {
		Map<String, Object> map = new HashMap<String, Object>();
		if (caseSearchBean.getCaseTimeStart() != null) {
			map.put("caseTimeStart",
					new Date(caseSearchBean.getCaseTimeStart()));
		}
		if (caseSearchBean.getCaseTimeEnd() != null) {
			map.put("caseTimeEnd", new Date(caseSearchBean.getCaseTimeEnd()));
		}
		map.put("caseCode", caseSearchBean.getCaseCode());
		map.put("caseName", caseSearchBean.getCaseName());
		map.put("caseKind", caseSearchBean.getCaseKind());
		map.put("caseSort", caseSearchBean.getCaseSort());
		map.put("caseTimeFrame", caseSearchBean.getCaseTimeFrame());
		map.put("handingUnit", caseSearchBean.getHandingUnit());
		map.put("popedom", caseSearchBean.getPopedom());
		map.put("community", caseSearchBean.getCommunity());
		map.put("caseState", caseSearchBean.getCaseState());
		map.put("disposePerson", caseSearchBean.getDisposePerson());
		map.put("caseAddress", caseSearchBean.getCaseAddress());
		map.put("caseClass", caseSearchBean.getCaseClass());
		Pager<CriminalBasicCase> pager = criminalCaseService
				.findCriminalCaseByCondition(map,
						this.getStart() / this.getLength(), this.getLength());
		for (CriminalBasicCase cbc : pager.getPageList()) {
			cbcBeanLst.add(criminalBasicCaseToBean(cbc));
		}
		this.setTotalNum(pager.getTotalNum());
		return SUCCESS;
	}

	private CriminalBasicCaseBean criminalBasicCaseToBean(CriminalBasicCase cbc){
		CriminalBasicCaseBean bean = new CriminalBasicCaseBean();
		bean.setCaseCode(cbc.getCaseCode() == null ? "" : cbc.getCaseCode());
		bean.setCaseName(cbc.getCaseName() == null ? "" : cbc.getCaseName());
		bean.setCaseSort(cbc.getCaseSort() == null ? "" : cbc.getCaseSort());
		bean.setCaseClass(cbc.getCaseClass() == null ? "" : cbc.getCaseClass());
//		//案件类别
//		DictionaryItem di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("ajlb", bean.getCaseSort(), null);
//		if(di != null){
//			bean.setCaseSort(di.getName());
//		}
		bean.setCaseKind(cbc.getCaseKind() == null ? "" : cbc.getCaseKind());
//		//案件性质
//		di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("ajxz", bean.getCaseKind(), null);
//		if(di != null){
//			bean.setCaseKind(di.getName());
//		}
		bean.setCaseState(cbc.getCaseState() == null ? "" : cbc
				.getCaseState());
		String state = cbc.getCaseState();
//		//案件状态
//		di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("ajzt", state, null);
//		if(di != null){
//			bean.setCaseState(di.getName());
//		}
		if (cbc.getCaseTimeStart() != null) {
			bean.setCaseTimeStart(cbc.getCaseTimeStart().getTime());
		}
		if (cbc.getAlarmInfo() != null) {
			bean.setPopedom(cbc.getAlarmInfo().getPopedom() == null ? ""
					: cbc.getAlarmInfo().getPopedom());
			//辖区
			Unit u = unitService.findUnitByCode(bean.getPopedom());
			if(u != null){
				bean.setPopedom(u.getShortName());
			}
		} else {
			bean.setPopedom("");
		}
		
		bean.setCommunity(cbc.getCommunity() == null ? "" : cbc
				.getCommunity());
//		//案件社区
//		di = dictionaryItemService.findDictionaryItemByDicTypeCodeAndItemCode("ajsq", bean.getCommunity(), null);
//		if(di != null){
//			bean.setCommunity(di.getName());
//		}
		bean.setCaseAddress(cbc.getCaseAddress() == null ? "" : cbc
				.getCaseAddress());
		bean.setCaseTimeFrame("");
		bean.setDisposePerson((cbc.getHandlingPeople1() == null ? "": cbc.getHandlingPeople1()) + (cbc.getHandlingPeople2() == null ? "" : (" " + cbc.getHandlingPeople2())));
		bean.setHandingUnit(cbc.getDqbldw());
		return bean;
	}
	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public CaseSearchBean getCaseSearchBean() {
		return caseSearchBean;
	}

	public void setCaseSearchBean(CaseSearchBean caseSearchBean) {
		this.caseSearchBean = caseSearchBean;
	}

	public List<CriminalBasicCaseBean> getCbcBeanLst() {
		return cbcBeanLst;
	}

	public void setCbcBeanLst(List<CriminalBasicCaseBean> cbcBeanLst) {
		this.cbcBeanLst = cbcBeanLst;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public CriminalBasicCaseDetailBean getCriminalBasicCaseDetailBean() {
		return criminalBasicCaseDetailBean;
	}

	public void setCriminalBasicCaseDetailBean(
			CriminalBasicCaseDetailBean criminalBasicCaseDetailBean) {
		this.criminalBasicCaseDetailBean = criminalBasicCaseDetailBean;
	}

	public List<OptionItemBean> getWritBeanLst() {
		return writBeanLst;
	}

	public void setWritBeanLst(List<OptionItemBean> writBeanLst) {
		this.writBeanLst = writBeanLst;
	}

	public List<OptionItemBean> getDictionaryItemLst() {
		return dictionaryItemLst;
	}

	public void setDictionaryItemLst(List<OptionItemBean> dictionaryItemLst) {
		this.dictionaryItemLst = dictionaryItemLst;
	}

	public List<ZTreeBean> getZtreeList() {
		return ztreeList;
	}

	public void setZtreeList(List<ZTreeBean> ztreeList) {
		this.ztreeList = ztreeList;
	}

	public List<DocZhengJvBiLuPhotoBean> getZjblBeanLst() {
		return zjblBeanLst;
	}

	public void setZjblBeanLst(List<DocZhengJvBiLuPhotoBean> zjblBeanLst) {
		this.zjblBeanLst = zjblBeanLst;
	}


	public String getBase64Str() {
		return base64Str;
	}


	public void setBase64Str(String base64Str) {
		this.base64Str = base64Str;
	}


	public String getBlid() {
		return blid;
	}


	public void setBlid(String blid) {
		this.blid = blid;
	}

}
