package com.taiji.pubsec.ajqlc.sawp.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajgl.util.bean.FileObjectBean;
import com.taiji.pubsec.ajgl.util.bean.OptionItemBean;
import com.taiji.pubsec.ajqlc.attachment.model.Attachment;
import com.taiji.pubsec.ajqlc.attachment.service.IAttachmentCustomizedService;
import com.taiji.pubsec.ajqlc.baq.model.BasicCase;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceiptToPoliceInfo;
import com.taiji.pubsec.ajqlc.baq.model.PoliceInfo;
import com.taiji.pubsec.ajqlc.baq.service.IHandlingAreaReceiptService;
import com.taiji.pubsec.ajqlc.operationrecord.service.IOperationRecordService;
import com.taiji.pubsec.ajqlc.sawp.action.bean.TemporaryStorageOutBean;
import com.taiji.pubsec.ajqlc.sawp.action.bean.TransitStorageOutBackBean;
import com.taiji.pubsec.ajqlc.sawp.action.bean.TransitStorageOutBackExcelBean;
import com.taiji.pubsec.ajqlc.sawp.bean.OutStorageFormItemServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.TempObjectBean;
import com.taiji.pubsec.ajqlc.sawp.bean.TempStorageInInfoBean;
import com.taiji.pubsec.ajqlc.sawp.bean.TempStorageOutInfoBean;
import com.taiji.pubsec.ajqlc.sawp.model.StoragePosition;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageIn;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageOut;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageShelf;
import com.taiji.pubsec.ajqlc.sawp.service.IImpoundedObjectService;
import com.taiji.pubsec.ajqlc.sawp.service.IInvolvedArticleService;
import com.taiji.pubsec.ajqlc.sawp.service.ITemporaryStorageInService;
import com.taiji.pubsec.ajqlc.sawp.service.ITemporaryStorageOutService;
import com.taiji.pubsec.ajqlc.sla.model.CriminalBasicCase;
import com.taiji.pubsec.ajqlc.sla.service.ICriminalCaseService;
import com.taiji.pubsec.ajqlc.util.Constant;
import com.taiji.pubsec.ajqlc.util.ExcelUtil;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.businesscomponent.organization.model.Person;
import com.taiji.pubsec.common.tools.doc.msoffice.impl.poicr.builder.PoiCrReportBuilder;
import com.taiji.pubsec.common.web.action.ExportInfoReq;
import com.taiji.pubsec.springsecurity.resource.util.MySecureUser;
import com.taiji.pubsec.springsecurity.resource.util.SessionUserDetailsUtil;


/**
 * 涉案物品--暂存暂存出库单
 * 
 * @author XIEHF
 *
 */
@Controller("transitStorageOutBackAction")
@Scope("prototype")
public class TransitStorageOutBackAction extends ReturnMessageAction {
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TransitStorageOutBackAction.class);
	@Resource
	private PoiCrReportBuilder poiCrReportBuilder;
	
	@Resource
	private IDictionaryItemService dictionaryItemService;
	
	@Resource
	private ITemporaryStorageOutService temporaryStorageOutService;
	
	@Resource
	private ITemporaryStorageInService temporaryStorageInService;
	
	@Resource
	private ICriminalCaseService criminalCaseService;
	
	@Resource
	private IAttachmentCustomizedService attachmentCustomizedService;
	
	@Resource
	private IInvolvedArticleService involvedArticleService;
	
	@Resource
	private IImpoundedObjectService impoundedObjectService;
	
	@Resource
	private IHandlingAreaReceiptService handlingAreaReceiptService;
	
	@Resource
    private IOperationRecordService operationRecordService;// 操作记录接口
	
	private String id;
	private String code;//出库单编号
	private String caseCode;
	private String caseName;
	private String polices;
	private List<TempStorageOutInfoBean> transitStorageOutBackBeanList = new ArrayList<TempStorageOutInfoBean>(); // 暂存出库单BeanList
	private TransitStorageOutBackBean transitStorageOutBackBean = new TransitStorageOutBackBean(); // 暂存出库单Bean
	private List<TransitStorageOutBackExcelBean> transitStorageOutBackExcelBeanList = new ArrayList<TransitStorageOutBackExcelBean>(); // 暂存出库单导出BeanList
	private String sf; // 是否显示我的暂存出库单 1：是 0：否
	private Long startTime; // 入库时间 （查询区域开始时间）
	private Long endTime; // 入库时间 （查询区域结束时间）
	private List<OptionItemBean> optionItemBeanLst = new ArrayList<OptionItemBean>();
	private TemporaryStorageOutBean temporaryStorageOutBean = new TemporaryStorageOutBean();
	private List<OutStorageFormItemServiceBean> outStorageFormItemBeanList = new ArrayList<OutStorageFormItemServiceBean>();
	private List<FileObjectBean> fileBeanLst = new ArrayList<FileObjectBean>();
	private String modifyPeople;
	private Long updateTime;
	private ExportInfoReq exportInfoReq = new ExportInfoReq();
	
	private List<TempObjectBean> tempObjectBeanList=new ArrayList<TempObjectBean>();
	
	
	/**
	 * 查询暂存出库单列表--通过条件
	 * 
	 * @return SUCCESS
	 */
	public String queryTransitStorageOutBackRecord() {
		transitStorageOutBackBeanList = new ArrayList<TempStorageOutInfoBean>();
		Pager<TempStorageOutInfoBean> pager = temporaryStorageOutService.findTempStorageOutInfosByQueryConditions(
				searchMap(), this.getStart() / this.getLength(),
				this.getLength());
		this.setTotalNum(pager.getTotalNum());
		changeForCaseName(pager.getPageList());
		return SUCCESS;
	}
	

	/**
	 * 查询暂存出库单详情--通过code
	 * 
	 * @return
	 */
	public String queryTransitStorageOutBackByCode() {
		TemporaryStorageOut osf = temporaryStorageOutService.findByCode(code);
		if(osf==null){
			return SUCCESS;
		}
		if (osf.getModifyPerson() != null) {
			modifyPeople = osf.getModifyPerson().getName(); //创建人
			updateTime = osf.getModifyTimeDate().getTime(); //创建时间
		}
		temporaryStorageOutBean.setCode(osf.getCode()); //出库返还单编号
		temporaryStorageOutBean.setStatus(osf.getStatus()); //是否返还完成
		
		temporaryStorageOutBean.setOutStorageTime(osf.getCreateDate().getTime()); //出库时间
		temporaryStorageOutBean.setHarCode(osf.getStorageIn().getHarCode()); //使用单编号
		TempStorageInInfoBean  inbean=temporaryStorageInService.findTemporaryStorageInDeatailByCode(osf.getStorageIn().getCode());
		if(inbean==null){
			return SUCCESS;
		}
		if(criminalCaseService.findCriminalCaseByCaseId(inbean.getCaseCode())!=null){
			temporaryStorageOutBean.setCaseName(criminalCaseService.findCriminalCaseByCaseId(inbean.getCaseCode()).getCaseName());
		}
		temporaryStorageOutBean.setCaseCode(inbean.getCaseCode());
		temporaryStorageOutBean.setPolices(inbean.getSolvePolice());
		temporaryStorageOutBean.setRemark(inbean.getRemark());
		temporaryStorageOutBean.setSuspectName(inbean.getObjectOwnerPerson());
		temporaryStorageOutBean.setSuspectId(inbean.getIdCard());
		
		temporaryStorageOutBean.setStorageRack(inbean.getInStorageSelf());
		temporaryStorageOutBean.setInStorageArea(inbean.getInStorageArea());
		
		List<Attachment> attLst = attachmentCustomizedService
				.findByTargetIdAndType(osf.getId(), TemporaryStorageOut.class.getName());
		for (Attachment att : attLst) {
			FileObjectBean fBeam = new FileObjectBean();
			fBeam.setId(att.getId());
			fBeam.setName(att.getName());
			fBeam.setSize(String.valueOf(att.getSize()));
			String base64Str;
			InputStream is = att.getAttachmentMeta().getAttachmentCopys().get(0).getInputStream();
			try {
				byte[] buffer = new byte[is.available()];
				is.read(buffer, 0, is.available());
				base64Str=new String (Base64.encodeBase64(buffer, false));
				fBeam.setBase64Str(base64Str);
			} catch (Exception e) {
				LOGGER.debug("暂存出库单查询照片附件转Base64异常",e);
			}
			fileBeanLst.add(fBeam);
		}
		tempObjectBeanList=changeLst(inbean.getTempObjectBeanList());
		this.setTotalNum(tempObjectBeanList.size());
		this.creatCaseManagementLog("涉案物品管理 /暂存物品出库返还", "涉案物品暂存物品出库返还单详情(暂存出库单Code:"+code+")");
		return SUCCESS;
	}
	
	/**
	 * 出库单新增查询界面
	 * @return
	 */
	public String findTransitStoreManageOutBackByMakingId(){
		temporaryStorageOutBean= new TemporaryStorageOutBean();
		
		HandlingAreaReceipt har = new HandlingAreaReceipt();
		if(null != id){
			har = handlingAreaReceiptService.findHandlingAreaReceiptById(id);
		}
		if(null != code){
			har = handlingAreaReceiptService.findHandlingAreaReceiptByNum(code);
		}
		
		TemporaryStorageIn storageIn = temporaryStorageInService.findTemporaryStorageInByHarCode(code);
		
		TemporaryStorageOut storageOut = temporaryStorageOutService.findByHarCode(code);
		
		if(null == storageIn){
			this.setMsg("此使用单未进行入库操作，请先入库！");
		}
		if(null != storageOut){
			this.setMsg("此使用单已进行出库操作，请勿重复操作！");
		}
		if(null != storageIn && null == storageOut){
			Set<HandlingAreaReceiptToPoliceInfo> police = har.getHandlingAreaReceiptToPoliceInfoList();
			if(police.size()>=0){
				temporaryStorageOutBean.setPolices(police.iterator().next().getPoliceInfo().getPoliceName());
			}else{
				temporaryStorageOutBean.setPolices("");
			}
			BasicCase basicCase = har.getBasicCase();
			CriminalBasicCase cbc = criminalCaseService.findCriminalCaseByCaseId(basicCase.getLawCase());
			temporaryStorageOutBean.setCaseCode(basicCase.getLawCase());
			temporaryStorageOutBean.setCaseName(cbc.getCaseName());
			temporaryStorageOutBean.setSuspectName(basicCase.getByQuestioningPeopleName());
			temporaryStorageOutBean.setSuspectId(basicCase.getByQuestioningPeopleIdentifyNum());
			List<StoragePosition> storagePosition = storageIn.getStoragePosition();
			if(!storagePosition.isEmpty()){
				TemporaryStorageShelf  shelf= storagePosition.get(0).getBgg();
				temporaryStorageOutBean.setInStorageArea(shelf.getArea().getAddress());
				temporaryStorageOutBean.setStorageRack(shelf.getAddress());
			}else{
				temporaryStorageOutBean.setInStorageArea("");
				temporaryStorageOutBean.setStorageRack("");
			}
			temporaryStorageOutBean.setCode(temporaryStorageOutService.acquireNum());
		}
		return SUCCESS;
	}
	
	/**
	 * 保存暂存物品出库返还新增 
	 * @return
	 */
	public String saveTransitStorageOutBack(){
		if (null != temporaryStorageOutBean) {
			id = temporaryStorageOutService.save(beanToModel(temporaryStorageOutBean));
		}
		for (FileObjectBean fob : fileBeanLst) {
			Attachment att = attachmentCustomizedService.findById(fob.getId());
			att.setTargetId(id);
			att.setType(TemporaryStorageOut.class.getName());
			attachmentCustomizedService.save(att);
		}
		return SUCCESS;
	}

	/**
	 * 删除暂存出库单
	 * 
	 * @return
	 */
	public String delTransitStorageOutBack() {
		try {
			temporaryStorageOutService.delete(id);
			this.setFlag("true");
			this.creatCaseManagementLog("涉案物品管理 /涉案物品出库", "根据暂存出库单id删除暂存出库单(暂存出库单id:"+id+")");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			this.setFlag("false");
			this.setMsg("删除失败，此暂存出库单已返还！");
			this.creatCaseManagementLog("涉案物品管理 /涉案物品出库", "根据暂存出库单id删除暂存出库单(暂存出库单id:"+id+"),删除失败，此暂存出库单已返还！");
		}
		return SUCCESS;
	}
	/**
	 * 确认出库
	 * 
	 * @return
	 */
	public String affirmStorageOut() {
		TemporaryStorageOut out=temporaryStorageOutService.findByCode(code);
		out.setStatus(Constant.SF_S);
		try {
			temporaryStorageOutService.update(out);
			this.setFlag("true");
			this.creatCaseManagementLog("涉案物品管理 /暂存物品管理/暂存物品出库返还", "将暂存出库单的状态修改为已出库(暂存出库单Code:"+code+")");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			this.setFlag("false");
			this.setMsg("确认出库状态修改失败");
			this.creatCaseManagementLog("案物品管理 /暂存物品管理/暂存物品出库返还", "将暂存出库单的状态修改为已出库(暂存出库单Code:"+code+"),修改失败");
		}
		return SUCCESS;
	}

	/**
	 * 暂存出库单列表导出EXCEL
	 * 
	 * @param
	 * @return
	 */
	public String exportExcel() throws IOException {
		Map<String, Object> map = searchMap();
		map.put("excel", "1");
		Pager<TempStorageOutInfoBean> pager = temporaryStorageOutService.findTempStorageOutInfosByQueryConditions(
				map, 0, Integer.MAX_VALUE);

		this.setTotalNum(pager.getTotalNum());
		int i = 1;
		if (null == pager.getPageList() || pager.getPageList().size() > 0) {
			for (TempStorageOutInfoBean form : pager.getPageList()) {
				TransitStorageOutBackExcelBean bean = transitStorageOutBackToExcelBean(form);
				bean.setCount(i + "");
				i++;
				transitStorageOutBackExcelBeanList.add(bean);
			}
		}
		ExcelUtil<TransitStorageOutBackExcelBean> ex = new ExcelUtil<TransitStorageOutBackExcelBean>();
		String[] headers = { "序号", "出库单编号", "出库时间", "对应案件编号", "对应案件名称", "办案民警",
				"出库物品对应嫌疑人或物品持有人", "是否返还完成", "备注" };
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String name = sdf.format(new Date());
		name = name + ".xls";
		// 创建ByteArrayOutputStream字节流
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		ex.exportExcel(headers, transitStorageOutBackExcelBeanList, bos);
		byte[] bytes = bos.toByteArray();
		bos.close();
		ByteArrayInputStream ins=new ByteArrayInputStream(bytes);
		exportInfoReq.setIn(ins);
		exportInfoReq.setName(name);
		exportInfoReq.setLength(Long.valueOf(bytes.length));
		ins.close();
		this.creatCaseManagementLog("涉案物品管理/暂存物品管理/暂存物品出库返还", "导出暂存物品出库返还excel,查询条件(暂存出库单编号:"+transitStorageOutBackBean.getCaseCode()+"对应案件编号："+transitStorageOutBackBean.getCaseCode()+"对应嫌疑人/物品持有人:"+transitStorageOutBackBean.getSuspectName()+"出库返还日期:"+new Date(startTime)+"--"+new Date(endTime)+"显示我的出库返还单:"+sf+")");
		return "done";
	}

	@SuppressWarnings("unchecked")
	private TemporaryStorageOut beanToModel(TemporaryStorageOutBean bean) {

		TemporaryStorageOut model = new TemporaryStorageOut();
		TemporaryStorageIn storageIn = temporaryStorageInService.findTemporaryStorageInByHarCode(bean.getHarCode());
		model.setStorageIn(storageIn);
		model.setCode(bean.getCode());
		model.setModifyTimeDate(new Date());
		model.setCreateDate(new Date(bean.getOutStorageTime()));
		model.setReceivePerson(bean.getReceivePerson());
		model.setRemark(bean.getRemark());
		model.setStatus(Constant.SF_F);
		MySecureUser user = SessionUserDetailsUtil.getMySecureUser();
		Map<String, Object> userMap = user.getUserMap();
		if (userMap.get("person") != null) {
			Map<String, Object>  map= (Map<String, Object>) userMap.get("person");
			Person person = new Person();
			if(null != map.get("id")){
				person.setId(map.get("id").toString());
			}
			if(null != map.get("name")){
				person.setName(map.get("name").toString());
			}
			if(null != map.get("code")){
				person.setCode(map.get("code").toString());
			}
			if(null != map.get("idNumber")){
				person.setIdNumber(map.get("idNumber").toString());
			}
			model.setModifyPerson(person);
			model.setCreatePerson(person);
		}
		return model;
	}

	/**
	 * 判断文件路径是否存在，不存在则创建路径
	 * 
	 * @param path
	 */
	public static void mkdir(String path) {
		File file = new File(path);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
	}

	/**
	 * 转换打印的bean
	 * @param entity
	 * @return
	 */
	private TransitStorageOutBackExcelBean transitStorageOutBackToExcelBean(TempStorageOutInfoBean entity) {
		TransitStorageOutBackExcelBean bean = new TransitStorageOutBackExcelBean();
		bean.setCode(entity.getStorageOutCode());
		bean.setCreatedTimeStr(entity.getStorageOutDateTime());
		bean.setCaseCode(entity.getCaseCode());
		bean.setCaseName(findCaseNameByCode(entity.getCaseCode()));
		bean.setPolice(entity.getSolvePolice());
		bean.setSuspectName(entity.getObjectOwnerPerson());
		bean.setIfAllOut(entity.getIfAllOut());
		bean.setRemark(entity.getRemark());
		return bean;
	}

	private Map<String, Object> searchMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("temporaryStorageOutCode", transitStorageOutBackBean.getCode());
		if (startTime != null) {
			map.put("queryOutDateStart", new Date(startTime));
		}
		if (endTime != null) {
			map.put("queryOutDateEnd", new Date(endTime));
		}
		map.put("caseCode", transitStorageOutBackBean.getCaseCode());
		map.put("ownerName", transitStorageOutBackBean.getSuspectName());
		if(Constant.SF_S.equals(sf)){
			map.put("myId", findPersonId());
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	private String findPersonId(){
		MySecureUser user = SessionUserDetailsUtil.getMySecureUser() ;
		Map<String, Object> userMap = user.getUserMap() ;
		Map<String, String> mPerson = new HashMap<String, String>(0) ;
		mPerson = (Map<String, String>)userMap.get("person");
		if(mPerson != null){
			return mPerson.get("id");
		}

		return null;
	}
	
	/**
	 * 详情页面 图片转换 64位 
	 * @param lst
	 * @return
	 */
	private List<TempObjectBean> changeLst(List<TempObjectBean> lst) {
		
		for(TempObjectBean bean:lst){
			bean.setObjectPicture(changeToBase64Str(bean.getObjectId(),bean.getObjectClassName()));
			
		}
		return lst;
	}

	/**
	 * 通过ID 和类名查询出附件,并转成64位字符串
	 * @param id
	 * @param name
	 * @return
	 */
	private String changeToBase64Str(String id,String name){
		List<Attachment> attLst = attachmentCustomizedService
				.findByTargetIdAndType(id, name);
		if(attLst!=null&&attLst.size()>0){
			Attachment att=attLst.get(0);
			String base64Str;
			InputStream is = att.getAttachmentMeta().getAttachmentCopys().get(0).getInputStream();
			try {
				byte[] buffer = new byte[is.available()];
				is.read(buffer, 0, is.available());
				base64Str=new String (Base64.encodeBase64(buffer, false));
				return base64Str;
			} catch (Exception e) {
				LOGGER.debug("暂存出库单查询照片附件转Base64异常",e);
			}
		}
		return null;
	}
	
	/**
	 * 查询出案件名称,并添加到list里
	 * @param pageList
	 * @return
	 */
	private void changeForCaseName(List<TempStorageOutInfoBean> pageList) {
		for(TempStorageOutInfoBean bean: pageList){
			bean.setCaseName(findCaseNameByCode(bean.getCaseCode()));
			transitStorageOutBackBeanList.add(bean);
		}
	}
	
	/**
	 * 通过案件编号查询案件名称
	 * @param code
	 * @return
	 */
	private String findCaseNameByCode(String code){
		CriminalBasicCase cri=criminalCaseService.findCriminalCaseByCaseId(code);
		if(cri==null){
			return "--";
		}
		return cri.getCaseName();
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<TempStorageOutInfoBean> getTransitStorageOutBackBeanList() {
		return transitStorageOutBackBeanList;
	}

	public void setTransitStorageOutBackBeanList(List<TempStorageOutInfoBean> transitStorageOutBackBeanList) {
		this.transitStorageOutBackBeanList = transitStorageOutBackBeanList;
	}

	public TransitStorageOutBackBean getTransitStorageOutBackBean() {
		return transitStorageOutBackBean;
	}

	public void setTransitStorageOutBackBean(TransitStorageOutBackBean transitStorageOutBackBean) {
		this.transitStorageOutBackBean = transitStorageOutBackBean;
	}

	public List<TransitStorageOutBackExcelBean> getTransitStorageOutBackExcelBeanList() {
		return transitStorageOutBackExcelBeanList;
	}

	public void setTransitStorageOutBackExcelBeanList( List<TransitStorageOutBackExcelBean> transitStorageOutBackExcelBeanList) {
		this.transitStorageOutBackExcelBeanList = transitStorageOutBackExcelBeanList;
	}

	public String getSf() {
		return sf;
	}

	public void setSf(String sf) {
		this.sf = sf;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public List<OptionItemBean> getOptionItemBeanLst() {
		return optionItemBeanLst;
	}

	public void setOptionItemBeanLst(List<OptionItemBean> optionItemBeanLst) {
		this.optionItemBeanLst = optionItemBeanLst;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getPolices() {
		return polices;
	}

	public void setPolices(String polices) {
		this.polices = polices;
	}

	public List<OutStorageFormItemServiceBean> getOutStorageFormItemBeanList() {
		return outStorageFormItemBeanList;
	}

	public void setOutStorageFormItemBeanList(
			List<OutStorageFormItemServiceBean> outStorageFormItemBeanList) {
		this.outStorageFormItemBeanList = outStorageFormItemBeanList;
	}

	public List<FileObjectBean> getFileBeanLst() {
		return fileBeanLst;
	}

	public void setFileBeanLst(List<FileObjectBean> fileBeanLst) {
		this.fileBeanLst = fileBeanLst;
	}

	public String getModifyPeople() {
		return modifyPeople;
	}

	public void setModifyPeople(String modifyPeople) {
		this.modifyPeople = modifyPeople;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public ExportInfoReq getExportInfoReq() {
		return exportInfoReq;
	}

	public void setExportInfoReq(ExportInfoReq exportInfoReq) {
		this.exportInfoReq = exportInfoReq;
	}

	public TemporaryStorageOutBean getTemporaryStorageOutBean() {
		return temporaryStorageOutBean;
	}

	public void setTemporaryStorageOutBean(TemporaryStorageOutBean temporaryStorageOutBean) {
		this.temporaryStorageOutBean = temporaryStorageOutBean;
	}

	public List<TempObjectBean> getTempObjectBeanList() {
		return tempObjectBeanList;
	}

	public void setTempObjectBeanList(List<TempObjectBean> tempObjectBeanList) {
		this.tempObjectBeanList = tempObjectBeanList;
	}


}
