package com.taiji.pubsec.ajqlc.ajjk.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajgl.util.bean.OptionItemBean;
import com.taiji.pubsec.ajqlc.ajjk.action.bean.CaseDataBean;
import com.taiji.pubsec.ajqlc.ajjk.action.bean.SearchBean;
import com.taiji.pubsec.ajqlc.sla.bean.AdministrationPersonServiceBean;
import com.taiji.pubsec.ajqlc.sla.model.CaseAttachedInfo;
import com.taiji.pubsec.ajqlc.sla.service.IArchivedFileService;
import com.taiji.pubsec.ajqlc.sla.service.ICaseAttachedInfoService;
import com.taiji.pubsec.businesscomponent.organization.model.Person;
import com.taiji.pubsec.businesscomponent.organization.model.Unit;
import com.taiji.pubsec.businesscomponent.organization.service.IPersonService;
import com.taiji.pubsec.businesscomponent.organization.service.IUnitService;
import com.taiji.pubsec.common.web.action.ExportInfoReq;
import com.taiji.pubsec.common.web.bean.ZTreeBean;

/**
 * 行政案件到期提醒查询Action
 * 
 * @author huangda
 *
 */
@Controller("administrativeExpiredListAction")
@Scope("prototype")
public class AdministrativeExpiredListAction extends ReturnMessageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private ICaseAttachedInfoService caseAttachedInfoService;
	@Resource
	private IUnitService unitService;
	@Resource
	private IArchivedFileService archivedFileService;
	@Resource
	private IPersonService personService;
	
	private static final String FAZHIDADUI_CODE = "520199180000";	//法制大队编码
	
	private SearchBean searchBean;
	private ExportInfoReq exportInfoReq = new ExportInfoReq();
	private List<ZTreeBean> ztreeList = new ArrayList<ZTreeBean>();
	private List<OptionItemBean> personBeanLst = new ArrayList<OptionItemBean>();
	private List<CaseDataBean> dataLst = new ArrayList<CaseDataBean>();
	private List<AdministrationPersonServiceBean> apsBeanLst = new ArrayList<AdministrationPersonServiceBean>();
	public String initData() {
		Unit unit = unitService.findUnitByCode(FAZHIDADUI_CODE);
		List<Person> personList = personService.findAllByParams("select p from Person as p where p.organization.id = ?", new Object[]{unit.getId()});
		for(Person person: personList){
			OptionItemBean bean = new OptionItemBean();
			bean.setName(person.getName());
			personBeanLst.add(bean);
		}
		return SUCCESS;
	}
	
	public String searchAdministrativeCaseByPage(){
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("caseClass", "行政案件");
		map.put("queryTimeStart", searchBean.getCaseTimeStart());
		map.put("queryTimeEnd", searchBean.getCaseTimeEnd());
		map.put("ifArchive", searchBean.getAttention());
		map.put("attendUnit", searchBean.getSponsor());
		map.put("caseName", searchBean.getCaseName()); 
		map.put("caseCode", searchBean.getCaseCode()); 
		
		map.put("disposePerson", searchBean.getDisposePerson()); 
		
		Pager<AdministrationPersonServiceBean> casePager = caseAttachedInfoService.findAdminPersonBeanByConditions(map, this.getStart() / this.getLength(), this.getLength());
		apsBeanLst = casePager.getPageList();
		this.setTotalNum(casePager.getTotalNum());
		return SUCCESS;
	}
	
	public String exportExcel() throws IOException {
//		Map<String, Object> map = new HashMap<String, Object>(); 
//		map.put("caseClass", "行政案件");
//		map.put("queryTime", searchBean.getSearchDate());
//		map.put("caseAttentionState", searchBean.getAttention());
//		map.put("attendUnit", searchBean.getSponsor());
//		map.put("caseName", searchBean.getCaseName()); 
//		map.put("note", searchBean.getRemark());
//		Pager<CriminalBasicCase> casePager = caseAttachedInfoService.findCriminalBasicCasesByCondition(map, 0, Integer.MAX_VALUE);
//		int i = 0;
//		for(CriminalBasicCase criminalBasicCase : casePager.getPageList()){
//			AdministrativeCaseBean bean = new AdministrativeCaseBean();
//			bean.setAnjianjieanshenpibiao("");//TODO 没有案件结案审批表
//			bean.setAttention(criminalBasicCase.getIfArchive() == "是" ? "否" : "是" );//TODO
//			bean.setCaseCode(criminalBasicCase.getCaseCode());
//			bean.setCaseName(criminalBasicCase.getCaseName());
//			bean.setServicingInfo(criminalBasicCase.getModifiedPerson() + "/" + criminalBasicCase.getModifiedTime());
//			bean.setSponsor(unitService.findUnitByCode(criminalBasicCase.getDqbldw()).getShortName());
//			
//			if(criminalBasicCase.getCaseAttachedInfo() == null){
//				bean.setRemark("");
//				bean.setVerifier("");
//			}else{
//				bean.setRemark(criminalBasicCase.getCaseAttachedInfo().getNote());
//				if(criminalBasicCase.getCaseAttachedInfo().getLegalperson().isEmpty()){
//					bean.setVerifier("");
//				}else{
//					bean.setVerifier(personService.findById(criminalBasicCase.getCaseAttachedInfo().getLegalperson()).getName());
//				}
//			}
//			
//			bean.setInvestigator(criminalBasicCase.getInputPerson());
//			
//			List<CaseSupectRelation> suspectList = criminalBasicCase.getCaseSupectRelations();
//			int countXzjl = 0;
//			int countJg = 0;
//			int countFk = 0;
//			String nameXzjl = "";
//			String nameJg = "";
//			String nameFk = "";
//			String xzjl = "";
//			String jg = "";
//			String fk = "";
//			for(CaseSupectRelation caseSupectRelation: suspectList){
//				if(("是").equals(caseSupectRelation.getIsadminDetention())){
//					countXzjl++;
//					if(caseSupectRelation.getPerson() != null){
//						nameXzjl += caseSupectRelation.getPerson().getName() + "<br/>";
//					}
//				}
//				if(("是").equals(caseSupectRelation.getIswarningPunish())){
//					countJg++;
//					if(caseSupectRelation.getPerson() != null){
//						nameJg += caseSupectRelation.getPerson().getName() + "<br/>";
//					}
//				}
//				if(("是").equals(caseSupectRelation.getIsFines())){
//					countFk++;
//					if(caseSupectRelation.getPerson() != null){
//						nameFk += caseSupectRelation.getPerson().getName() + "，" + caseSupectRelation.getFinesNo() + "元" + "<br/>";
//					}
//				}
//			}
//			if(countXzjl == 0){
//				xzjl += "行政拘留0人";
//			}else{
//				xzjl += "行政拘留" + countXzjl + "人<br/>" + nameXzjl;
//			}
//			bean.setXingzhengjuliu(xzjl);
//			if(countJg == 0){
//				jg += "警告0人";
//			}else{
//				jg += "警告" + countJg + "人<br/>" + nameJg;
//			}
//			bean.setJinggao(jg);
//			if(countFk == 0){
//				fk += "警告0人";
//			}else{
//				fk += "警告" + countFk + "人<br/>" + nameFk;
//			}
//			bean.setFakuan(jg);
//			
//			List<ArchivedFile> qzgljdFile = archivedFileService.findArchivedFileByCaseIdAndType(criminalBasicCase.getCaseCode(), TYPE_QZGLJD);
//			if(qzgljdFile.size() == 0){
//				bean.setQiangzhigelijiedu("");
//			}else{
//				String qzgljd = "强制隔离戒毒" + qzgljdFile.size() + "人<br/>";
//				 for(ArchivedFile archivedFile: qzgljdFile){
//					 DocQiangZhiGeLiJieDuNew doc = archivedFileService.findDocQiangZhiGeLiJieDuNewById(archivedFile.getSourceId());
//					 qzgljd += doc.getB6();
//					 qzgljd += " ";
//				 }
//				bean.setQiangzhigelijiedu(qzgljd);
//			}
//			
//			List<ArchivedFile> zlsqjdFile = archivedFileService.findArchivedFileByCaseIdAndType(criminalBasicCase.getCaseCode(), TYPE_ZLSQJD);
//			if(zlsqjdFile.size() == 0){
//				bean.setShequjiedu("");
//			}else{
//				String zlsqjd = "社区戒毒" + zlsqjdFile.size() + "人<br/>";
//				 for(ArchivedFile archivedFile: qzgljdFile){
//					 DocZeLingSheQuJieDuSheQuKangFuJueDingShuNew doc = archivedFileService.findDocZeLingSheQuJieDuSheQuKangFuJueDingShuNewById(archivedFile.getSourceId());
//					 zlsqjd += doc.getA6();
//					 zlsqjd += " ";
//				 }
//				bean.setQiangzhigelijiedu(zlsqjd);
//			}
//			
//			bean.setNum(String.valueOf(i++));
//			acBeanLst.add(bean);
//		}
//		//查询案件，同楼上search，不分页
//		ExcelUtil<AdministrativeCaseBean> ex = new ExcelUtil<AdministrativeCaseBean>();
//		String[] headers = { "序号", "案件编号", "案件名称", "办案单位", "主办侦查员", "法制审核人",
//				"案件关注状态", "维护人/维护时间", "行政拘留", "警告", "罚款", "社区戒毒", "隔离强制戒毒",
//				"案件结案审批表", "备注" };
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//		String name = sdf.format(new Date());
//		name = name + ".xls";
//		// 创建ByteArrayOutputStream字节流
//		ByteArrayOutputStream bos=new ByteArrayOutputStream();
//		ex.exportExcel(headers, acBeanLst, bos);
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
		for(CaseDataBean caseDataBean: dataLst){
			CaseAttachedInfo caseAttachedInfo = new CaseAttachedInfo();
			caseAttachedInfo.setDoPerson(caseDataBean.getDoPerson());
			caseAttachedInfoService.createCaseAttachedInfo(caseAttachedInfo, caseDataBean.getCaseCode());
		}
		return SUCCESS;
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

	public List<AdministrationPersonServiceBean> getApsBeanLst() {
		return apsBeanLst;
	}

	public void setApsBeanLst(List<AdministrationPersonServiceBean> apsBeanLst) {
		this.apsBeanLst = apsBeanLst;
	}

}
