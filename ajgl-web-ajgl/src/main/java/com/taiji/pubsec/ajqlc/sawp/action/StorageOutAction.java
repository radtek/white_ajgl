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
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajgl.util.OperationRecordConstant;
import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajgl.util.bean.FileObjectBean;
import com.taiji.pubsec.ajgl.util.bean.OptionItemBean;
import com.taiji.pubsec.ajqlc.attachment.model.Attachment;
import com.taiji.pubsec.ajqlc.attachment.service.IAttachmentCustomizedService;
import com.taiji.pubsec.ajqlc.operationrecord.model.OperationRecord;
import com.taiji.pubsec.ajqlc.operationrecord.service.IOperationRecordService;
import com.taiji.pubsec.ajqlc.sawp.action.bean.StorageOutBean;
import com.taiji.pubsec.ajqlc.sawp.action.bean.StorageOutExcelBean;
import com.taiji.pubsec.ajqlc.sawp.bean.CriminalBasicCaseBean;
import com.taiji.pubsec.ajqlc.sawp.bean.OutStorageFormItemServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.OutStorageFormServiceBean;
import com.taiji.pubsec.ajqlc.sawp.model.ImpoundedObject;
import com.taiji.pubsec.ajqlc.sawp.model.InvolvedArticle;
import com.taiji.pubsec.ajqlc.sawp.model.OutStorageForm;
import com.taiji.pubsec.ajqlc.sawp.model.OutStorageFormItem;
import com.taiji.pubsec.ajqlc.sawp.model.Storage;
import com.taiji.pubsec.ajqlc.sawp.service.IImpoundedObjectService;
import com.taiji.pubsec.ajqlc.sawp.service.IInvolvedArticleService;
import com.taiji.pubsec.ajqlc.sawp.service.IOutStorageFormService;
import com.taiji.pubsec.ajqlc.util.ExcelUtil;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryItem;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.common.tools.doc.core.model.ReportConfig;
import com.taiji.pubsec.common.tools.doc.msoffice.impl.poicr.builder.PoiCrReportBuilder;
import com.taiji.pubsec.common.tools.doc.msoffice.impl.poicr.reporter.PoiCrReport;
import com.taiji.pubsec.common.web.action.ExportInfoReq;

/**
 * 涉案物品--出库单
 * 
 * @author XIEHF
 *
 */
@Controller("storageOutAction")
@Scope("prototype")
public class StorageOutAction extends ReturnMessageAction {
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(StorageOutAction.class);
	@Resource
	private PoiCrReportBuilder poiCrReportBuilder;
	@Resource
	private IDictionaryItemService dictionaryItemService;
	@Resource
	private IOutStorageFormService outStorageFormService;
	@Resource
	private IAttachmentCustomizedService attachmentCustomizedService;
	@Resource
	private IInvolvedArticleService involvedArticleService;
	@Resource
	private IImpoundedObjectService impoundedObjectService;
	@Resource
    private IOperationRecordService operationRecordService;// 操作记录接口
	
	private String id;
	private String code;
	private String caseCode;
	private String caseName;
	private String polices;
	private List<StorageOutBean> storageOutBeanList = new ArrayList<StorageOutBean>(); // 出库单BeanList
	private StorageOutBean storageOutBean = new StorageOutBean(); // 出库单Bean
	private List<StorageOutExcelBean> storageOutExcelBeanList = new ArrayList<StorageOutExcelBean>(); // 出库单导出BeanList
	private String sf; // 是否显示我的出库单 1：是 0：否
	private Long startTime; // 入库时间 （查询区域开始时间）
	private Long endTime; // 入库时间 （查询区域结束时间）
	private List<OptionItemBean> optionItemBeanLst = new ArrayList<OptionItemBean>();
	private OutStorageFormServiceBean outStorageFormBean = new OutStorageFormServiceBean();
	private List<OutStorageFormItemServiceBean> outStorageFormItemBeanList = new ArrayList<OutStorageFormItemServiceBean>();
	private List<FileObjectBean> fileBeanLst = new ArrayList<FileObjectBean>();
	private String modifyPeople;
	private Long updateTime;
	private ExportInfoReq exportInfoReq = new ExportInfoReq();

	/**
	 * Date时间转化成打印字符串
	 * 
	 * @param Date
	 *            毫秒数
	 * @param fmt
	 *            输出格式，默认为精确到分钟
	 * @return
	 */
	private String dateToStr(Date date, String fmt) {
		String str = "";
		if (date == null) {
			return str;
		}
		if (StringUtils.isBlank(fmt)) {
			fmt = "yyyy-MM-dd HH:mm";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		try {
			str = sdf.format(date);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return str;
	}

	/**
	 * 打印出库单
	 * 
	 * @return
	 */
	public String printStorageOutById() {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> mapN1 = new HashMap<String, String>();
		OutStorageForm osf = outStorageFormService.findById(id);
		mapN1.put("n0", dateToStr(new Date(), null));
		mapN1.put("n1", osf.getCode() == null ? "" : osf.getCode());
		mapN1.put("n2", null == osf.getOutStorageTime() ? "" : dateToStr(osf.getOutStorageTime(), null));
		mapN1.put("n3", osf.getCaseCode() == null ? "" : osf.getCaseCode());
		mapN1.put("n4",osf.getCaseName() == null ? "" :osf.getCaseName());
		mapN1.put("n5",osf.getHandlePolices() == null ? "" : osf.getHandlePolices()); 
		DictionaryItem di = dictionaryItemService.findById(osf.getType());
		if (di != null) {
			mapN1.put("n6", di.getName() == null ? "" : di.getName());
		} else {
			mapN1.put("n6", "");
		}
		List<Attachment> attLst = attachmentCustomizedService
				.findByTargetIdAndType(id, OutStorageForm.class.getName());
		String papers = "";
		for (Attachment att : attLst) {
			papers += att.getName();
		}
		if (papers != "") {
			papers.substring(0, papers.length() - 1);
		}
		mapN1.put("n7", papers);
		mapN1.put("n8", osf.getRemark() == null ? "" : osf.getRemark());
		
		if (osf.getModifyPeople() != null) {
			mapN1.put("n9", osf.getModifyPeople().getName() == null ? "" : osf
					.getModifyPeople().getName());
		} else {
			mapN1.put("n9", "");
		}
		map.put("n1", mapN1);
		for (OutStorageFormItem osfi : osf.getOutStorageFormItems()) {
			boolean existFlag = false;
			for (OutStorageFormItemServiceBean tempBean : outStorageFormItemBeanList) {
				if (tempBean.getInvolvedArticleId().equals(osfi.getId())) {
					tempBean.setOutcomingNumber(tempBean.getOutcomingNumber()
							+ osfi.getOutcomingNumber());
					existFlag = true;
					break;
				}
			}
			if (existFlag) {
				continue;
			}
			if(osfi.getOutcomingNumber()==0.0){
				continue;//查询出库数量
			}
			OutStorageFormItemServiceBean osfiBean = new OutStorageFormItemServiceBean();
			osfiBean.setInvolvedArticleId(null == osfi.getId() ? "" :osfi.getId());// 涉案物品id
			osfiBean.setInvolvedArticleName(null == osfi.getArticle().getName() ? "" : osfi.getArticle().getName());// 涉案物品名称
			osfiBean.setInvolvedArticleFeature(null == osfi.getArticle().getFeature() ? "" : osfi.getArticle().getFeature());// 涉案物品特征
			osfiBean.setInvolvedArticleCode(null == osfi.getArticle().getCode() ? "" : osfi.getArticle().getCode());// 涉案物品编号
			osfiBean.setDetentionNumber(null == osfi.getArticle().getDetentionNumber() ? "" : osfi.getArticle().getDetentionNumber());// 扣押数量
			osfiBean.setOutcomingNumber(osfi.getOutcomingNumber());// 出库数量
			osfiBean.setMeasurementUnit(null == osfi.getArticle().getMeasurementUnit() ? "" : osfi.getArticle().getMeasurementUnit());// 计量单位
			osfiBean.setSuspectedName(osfi.getArticle().getSuspectName() + (osfi.getArticle().getSuspectIdentityNumber() == null ? "" :("," + osfi.getArticle().getSuspectIdentityNumber())));// 嫌疑人姓名
			outStorageFormItemBeanList.add(osfiBean);
		}
		List<Map<String, String>> mapT1 = new ArrayList<Map<String, String>>();
		 if(outStorageFormItemBeanList.size() > 0) {
			int index = 1;
			for (OutStorageFormItemServiceBean tempBean : outStorageFormItemBeanList) {
					Map<String, String> mapLine = new HashMap<String, String>();
					mapLine.put("c1", String.valueOf(index));
					mapLine.put("c2", tempBean.getInvolvedArticleName() == null ? ""
							: tempBean.getInvolvedArticleName());
					mapLine.put("c3", tempBean.getInvolvedArticleFeature() == null ? ""
							: tempBean.getInvolvedArticleFeature());
					mapLine.put("c4", tempBean.getInvolvedArticleCode() == null ? ""
							: tempBean.getInvolvedArticleCode());
					mapLine.put("c5", tempBean.getDetentionNumber() == null ? ""
							: tempBean.getDetentionNumber());
					mapLine.put("c6", String.valueOf(tempBean.getOutcomingNumber()));
					mapLine.put("c7", tempBean.getMeasurementUnit() == null ? ""
							: tempBean.getMeasurementUnit());
					String[] arr = tempBean.getSuspectedName().split(",");
					if(arr.length >0){
						mapLine.put("c8", arr[0] == null ? "" : arr[0]);
						if (arr.length > 1) {
							mapLine.put("c9", arr[1] == null ? "" : arr[1]);
						} else {
							mapLine.put("c9", "");
						}
					}
					mapT1.add(mapLine);
					index++;
			}
		}
		if (mapT1.size() <= 0) {
			Map<String, String> mapLine = new HashMap<String, String>();
			mapLine.put("c1", "");
			mapLine.put("c2", "");
			mapLine.put("c3", "");
			mapLine.put("c4", "");
			mapLine.put("c5", "");
			mapLine.put("c6", "");
			mapLine.put("c7", "");
			mapLine.put("c8", "");
			mapLine.put("c9", "");
			mapT1.add(mapLine);
		}
		map.put("t1", mapT1);
		ReportConfig rc = poiCrReportBuilder.createFromXmlFile(map, this
				.getRequest().getServletContext().getRealPath("/")
				+ "WEB-INF"
				+ File.separator
				+ "printAbout"
				+ File.separator
				+ "printXml" + File.separator + "storageOutRecord.xml");
		PoiCrReport report = new PoiCrReport(rc);
		poiCrReportBuilder.build(report);
		InputStream is = report.generateReportInputStream();
		try {
			exportInfoReq
					.setLength(Long.valueOf(String.valueOf(is.available())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		exportInfoReq.setIn(is);
		exportInfoReq.setName("出库单" + osf.getCode() + ".docx");
		this.creatCaseManagementLog("涉案物品管理 /涉案物品出库", "打印涉案物品出库单(出库单id:"+id+")");
		return "done";
	}

	/**
	 * 查询出库单
	 * 
	 * @return
	 */
	public String queryStorageOutById() {
		OutStorageForm osf = outStorageFormService.findById(id);
		if (osf.getModifyPeople() != null) {
			modifyPeople = osf.getModifyPeople().getName();
			updateTime = osf.getUpdatedTime().getTime();
		}
		outStorageFormBean.setCode(osf.getCode());
		outStorageFormBean.setOutStorageTime(osf.getOutStorageTime().getTime());
		DictionaryItem di = dictionaryItemService.findById(osf.getType());
		if (di != null) {
			outStorageFormBean.setType(di.getName());
		} else {
			outStorageFormBean.setType("");
		}
		outStorageFormBean.setCaseName(osf.getCaseName());
		outStorageFormBean.setCaseCode(osf.getCaseCode());
		outStorageFormBean.setPolices(osf.getHandlePolices());
		outStorageFormBean.setRemark(osf.getRemark());
		di = dictionaryItemService.findById(osf.getIsReturned());
		if (di != null) {
			outStorageFormBean.setIsReturned(di.getName());
		} else {
			outStorageFormBean.setIsReturned("");
		}
		outStorageFormBean.setReceiver(osf.getReceiver());
		List<Attachment> attLst = attachmentCustomizedService
				.findByTargetIdAndType(id, OutStorageForm.class.getName());
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
				LOGGER.debug("出库单查询照片附件转Base64异常",e);
			}
			
//			try {
//				base64Str = Base64CryptFmtUtil.encode(FileFmtUtils.readByByteBuffer(att.getAttachmentMeta().getAttachmentCopys().get(0).getInputStream()), false);
//				fBeam.setBase64Str(base64Str);
//			} catch (UnsupportedEncodingException e) {
//				LOGGER.debug("出库单查询照片附件转Base64异常",e);
//			}
			fileBeanLst.add(fBeam);
		}
		for (OutStorageFormItem osfi : osf.getOutStorageFormItems()) {
			if(osfi.getOutcomingNumber()==0.0){
				continue;
			}
			OutStorageFormItemServiceBean osfiBean = new OutStorageFormItemServiceBean();
			osfiBean.setInvolvedArticleId(osfi.getId());// 涉案物品id
			osfiBean.setInvolvedArticleName(osfi.getArticle().getName());// 涉案物品名称
			osfiBean.setInvolvedArticleFeature(osfi.getArticle().getFeature());// 涉案物品特征
			osfiBean.setInvolvedArticleCode(osfi.getArticle().getCode());// 涉案物品编号
			osfiBean.setDetentionNumber(osfi.getArticle().getDetentionNumber());// 扣押数量
			if(osfi.getArticle()!=null){
				osfiBean.setInvolvedArticleType(findXZName(osfi.getArticle().getType()));// 涉案物品性质
			}else{
				osfiBean.setInvolvedArticleType("");// 涉案物品性质
			}
			osfiBean.setNumberInStorage(osfi.getExistingNumber());// 在库数量
			osfiBean.setOutcomingNumber(osfi.getOutcomingNumber());// 出库数量
			osfiBean.setMeasurementUnit(osfi.getArticle().getMeasurementUnit());// 计量单位
			osfiBean.setAreaName(osfi.getLocker().getArea().getName());// 保管区名称
			osfiBean.setLockerId(osfi.getLocker().getId());// 储物箱
			osfiBean.setLockerName(osfi.getLocker().getLocation());// 储物箱
			osfiBean.setSuspectedName(osfi.getArticle().getSuspectName()
					+ (osfi.getArticle().getSuspectIdentityNumber() == null ? ""
							: ("<br/>" + osfi.getArticle()
									.getSuspectIdentityNumber())));// 嫌疑人姓名
			osfiBean.setPapersId(osfi.getArticle().getPaperId());// 文书Id
			osfiBean.setPapers(osfi.getArticle().getPaper());// 文书
			osfiBean.setPapersType(osfi.getArticle().getPaperType());// 文书类型
			osfiBean.setRemark(osfi.getArticle().getRemark());// 备注
			outStorageFormItemBeanList.add(osfiBean);
		}
		this.setTotalNum(outStorageFormItemBeanList.size());
		this.creatCaseManagementLog("涉案物品管理 /涉案物品出库", "涉案物品出库单详情(出库单id:"+id+")");
		return SUCCESS;
	}
	
	/**
	 * 初始化新建页面
	 * 
	 * @return
	 */
	public String queryStorageByCase() {
		if (StringUtils.isBlank(caseCode)) {
			this.setTotalNum(0);
			return SUCCESS;
		}
		ImpoundedObject io = this.impoundedObjectService.findCaseNameByCaseId(caseCode);
		if (null != io) {
			caseName = io.getCaseName();
			polices = io.getPolice();
		}else{
			caseName = "";
			polices = "";
		}
		List<InvolvedArticle> involvedArticleLst = involvedArticleService.findInvolvedArticlesByCase(caseCode);
		for (InvolvedArticle ia : involvedArticleLst) {
			for (Storage storage : ia.getStorages()) {
				if (storage.getExistingNumber() <= 0) {
					continue;
				}
				OutStorageFormItemServiceBean bean = new OutStorageFormItemServiceBean();
				bean.setInvolvedArticleId(ia.getId());// 涉案物品id
				bean.setInvolvedArticleName(ia.getName());// 涉案物品名称
				bean.setInvolvedArticleFeature(ia.getFeature());// 涉案物品特征
				bean.setInvolvedArticleCode(ia.getCode());// 涉案物品编号
				bean.setDetentionNumber(ia.getDetentionNumber());// 扣押数量
				bean.setInvolvedArticleType(findXZName(ia.getType()));// 涉案物品性质
				bean.setNumberInStorage(storage.getExistingNumber());// 在库数量
				bean.setOutcomingNumber(0.0);// 出库数量
				bean.setMeasurementUnit(ia.getMeasurementUnit());// 计量单位
				bean.setAreaName(storage.getLocker().getArea().getName());// 保管区名称
				bean.setLockerId(storage.getLocker().getId());// 储物箱
				bean.setLockerName(storage.getLocker().getLocation());// 储物箱
				bean.setSuspectedName((ia.getSuspectName() == null ? "" : ia
						.getSuspectName())
						+ (ia.getSuspectIdentityNumber() == null ? ""
								: ("<br/>" + ia.getSuspectIdentityNumber())));// 嫌疑人姓名
				bean.setPapersId(ia.getPaperId());
				bean.setPapers(ia.getPaper());
				bean.setPapersType(ia.getPaperType());
				bean.setRemark(ia.getRemark());// 备注
				outStorageFormItemBeanList.add(bean);
			}
		}
		this.setTotalNum(outStorageFormItemBeanList.size());
		return SUCCESS;
	}
	
	
	/**
	 * 通过涉案物品新增的编号获取涉案物品性质的名称集合以逗号分隔
	 * @param strxz
	 * @return
	 */
	private String findXZName(String strxz){
		if(strxz!=null){
			String[] str=strxz.split(",");
			String s="";
			for(int i=0;i<str.length;i++){
				DictionaryItem dic=dictionaryItemService.findById(str[i]);
				if(dic!=null){
					s+=dic.getName()+",";
				}
			}
			if(!"".equals(s)){
				return s.substring(0, s.length()-1);// 涉案物品性质
			}else{
				return "";
			}
		}
		return "";
	}


	/**
	 * 保存出库单
	 * 
	 * @return
	 */
	public String saveStorageOut() {
		id = outStorageFormService.createForm(outStorageFormBean, outStorageFormItemBeanList);
		for (FileObjectBean fob : fileBeanLst) {
			Attachment att = attachmentCustomizedService.findById(fob.getId());
			att.setTargetId(id);
			att.setType(OutStorageForm.class.getName());
			attachmentCustomizedService.save(att);
		}
		
		this.setFlag("true");
		//创建操作记录
		for(OutStorageFormItemServiceBean osfisb : outStorageFormItemBeanList){
			if(osfisb.getOutcomingNumber() < 1){
				continue;
			}
			OperationRecord or = new OperationRecord();
			or.setArticleCode(osfisb.getInvolvedArticleCode());
			or.setFormCode(outStorageFormBean.getCode());
			or.setFormId(id);
			or.setFormType(OutStorageForm.class.getName());
			or.setNumber(String.valueOf(osfisb.getOutcomingNumber()));
			or.setOperation(OperationRecordConstant.WP_CZJL_CK);
			or.setOperationTime(new Date());
			or.setOperator(this.findCurrentPerson().getName());
			operationRecordService.saveOperationRecord(or);
		}
		this.creatCaseManagementLog("涉案物品管理 /涉案物品出库", "新增涉案物品出库单(出库单id:"+id+")");
		return SUCCESS;
	}

	/**
	 * 初始化新建页面
	 * 
	 * @return
	 */
	public String findAllCaseCodeAndfindCode() {
		code = outStorageFormService.acquireNum();
		return SUCCESS;
	}

	/**
	 * 删除出库单
	 * 
	 * @return
	 */
	public String delStorageOut() {
		try {
			outStorageFormService.deleteForm(id);
			this.setFlag("true");
			this.creatCaseManagementLog("涉案物品管理 /涉案物品出库", "根据出库单id删除出库单(出库单id:"+id+")");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			this.setFlag("false");
			this.setMsg("删除失败，此出库单已返还！");
			this.creatCaseManagementLog("涉案物品管理 /涉案物品出库", "根据出库单id删除出库单(出库单id:"+id+"),删除失败，此出库单已返还！");
		}
		return SUCCESS;
	}

	/**
	 * 出库单列表导出EXCEL
	 * 
	 * @param
	 * @return
	 */
	public String exportExcel() throws IOException {
		Map<String, Object> map = searchMap();
		map.put("excel", "1");
		Pager<OutStorageForm> pager = outStorageFormService.queryByCondition(
				map, 0, Integer.MAX_VALUE);

		this.setTotalNum(pager.getTotalNum());
		int i = 1;
		if (null == pager.getPageList() || pager.getPageList().size() > 0) {
			for (OutStorageForm form : pager.getPageList()) {
				StorageOutExcelBean bean = storageOutToExcelBean(form);
				bean.setCount(i + "");
				i++;
				storageOutExcelBeanList.add(bean);
			}
		}
		ExcelUtil<StorageOutExcelBean> ex = new ExcelUtil<StorageOutExcelBean>();
		String[] headers = { "序号", "出库单编号", "出库时间", "对应案件编号", "对应案件名称", "办案民警",
				"出库类型", "对应出库文书", "出库物品对应嫌疑人或物品持有人", "是否返还完成", "备注" };
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String name = sdf.format(new Date());
		name = name + ".xls";
		// 创建ByteArrayOutputStream字节流
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		ex.exportExcel(headers, storageOutExcelBeanList, bos);
		byte[] bytes = bos.toByteArray();
		bos.close();
		ByteArrayInputStream ins=new ByteArrayInputStream(bytes);
		exportInfoReq.setIn(ins);
		exportInfoReq.setName(name);
		exportInfoReq.setLength(Long.valueOf(bytes.length));
		ins.close();
		String query="";
		if(sf==null){
			query="否";
		}else{
			query ="是";
		}
		this.creatCaseManagementLog("涉案物品管理 /涉案物品出库", "导出查出的出库单,查询条件(出库单编号:"+storageOutBean.getCode()+"对应案件编号："+storageOutBean.getCaseCode()+"对应嫌疑人/物品持有人:"+storageOutBean.getSuspectName()+"对应文书:"+storageOutBean.getPapers()+"备注:"+storageOutBean.getRemark()+"入库时间:"+new Date(startTime)+"--"+new Date(endTime)+"只显示我的入库单:"+query+"借出或其他出库物品是否再入库:"+storageOutBean.getRestitution()+"出库类型:"+storageOutBean.getType()+")");
		return "done";
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

	private StorageOutExcelBean storageOutToExcelBean(OutStorageForm entity) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		StorageOutExcelBean bean = new StorageOutExcelBean();
		bean.setCode(entity.getCode());
		bean.setCreatedTimeStr(sdf.format(entity.getCreatedTime()));
		bean.setCaseCode(entity.getCaseCode());
		bean.setCaseName(entity.getCaseName());
		
		CriminalBasicCaseBean cbcBean = impoundedObjectService.findCriminalBasicCaseBeanForInStorageByCaseId(entity.getCaseCode());
		if(cbcBean == null){
			bean.setPolice("");
		}else{
			bean.setPolice(cbcBean.getHandlePolices());
		}
		
		DictionaryItem di = dictionaryItemService.findById(entity.getType());
		if (di != null) {
			bean.setTypeStr(di.getName());
		} else {
			bean.setTypeStr("");
		}
		bean.setPapers(entity.getPapers());
		Set<OutStorageFormItem> ofiSet = entity.getOutStorageFormItems();
		List<String> nameLst = new ArrayList<String>();
		for (OutStorageFormItem ofi : ofiSet) {
			if (!nameLst.contains(ofi.getArticle().getSuspectName())) {
				nameLst.add(ofi.getArticle().getSuspectName());
			}
		}
		String names = "";
		for (String str : nameLst) {
			names += str;
		}
		if (names != "") {
			bean.setSuspectName(names.substring(0, names.length() - 1));
		} else {
			bean.setSuspectName("");
		}
		DictionaryItem di2 = dictionaryItemService.findById(entity
				.getIsReturned());
		if (di2 != null) {
			bean.setRestitutionStr(di2.getName());
		} else {
			bean.setRestitutionStr("");
		}
		bean.setRemark(entity.getRemark());
		return bean;
	}

	private Map<String, Object> searchMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", storageOutBean.getCode());
		if (startTime != null) {
			map.put("outStorageDateStart", new Date(startTime));
		}
		if (endTime != null) {
			map.put("outStorageDateEnd", new Date(endTime));
		}
		map.put("caseCode", storageOutBean.getCaseCode());
		map.put("suspectName", storageOutBean.getSuspectName());
		map.put("papers", storageOutBean.getPapers());
		map.put("remark", storageOutBean.getRemark());
		map.put("sf", sf);
		map.put("isReturned", storageOutBean.getRestitution());
		map.put("type", storageOutBean.getType());
		return map;
	}

	/**
	 * 查询出库单列表
	 * 
	 * @return SUCCESS
	 */
	public String queryStorageOutRecord() {
		storageOutBeanList = new ArrayList<StorageOutBean>();
		Pager<OutStorageForm> pager = outStorageFormService.queryByCondition(
				searchMap(), this.getStart() / this.getLength(),
				this.getLength());
		this.setTotalNum(pager.getTotalNum());
		for (OutStorageForm osf : pager.getPageList()) {
			storageOutBeanList.add(assembleStorageOutBean(osf));
		}
		return SUCCESS;
	}

	/**
	 * 私有方法 装配出库单bean
	 * 
	 * @param outStorageFormList
	 *            出库单List
	 * @return
	 */
	private StorageOutBean assembleStorageOutBean(OutStorageForm entity) {
		StorageOutBean bean = new StorageOutBean();
		bean.setId(entity.getId());
		bean.setCode(entity.getCode());
		bean.setCreatedTime(entity.getCreatedTime().getTime());
		bean.setCaseCode(entity.getCaseCode());
		ImpoundedObject io = impoundedObjectService.findCaseNameByCaseId(entity.getCaseCode());
		if(io != null){
			bean.setCaseName(io.getCaseName());
			bean.setPolice(io.getPolice());
		}else{
			bean.setCaseName("");
			bean.setPolice("");
		}
		bean.setType(entity.getType());
		DictionaryItem di = dictionaryItemService.findById(entity.getType());
		if (di != null) {
			bean.setTypeStr(di.getName());
		} else {
			bean.setTypeStr("");
		}
		Set<OutStorageFormItem> ofiSet = entity.getOutStorageFormItems();
		List<String> nameLst = new ArrayList<String>();
		for (OutStorageFormItem ofi : ofiSet) {
			if (!nameLst.contains(ofi.getArticle().getSuspectName())) {
				nameLst.add(ofi.getArticle().getSuspectName());
			}
		}
		String names = "";
		for (String str : nameLst) {
			names += str + "，";
		}
		if (names != "" && names.indexOf("，") != -1) {
			bean.setSuspectName(names.substring(0, names.length() - 1));
		} else {
			bean.setSuspectName("");
		}
		bean.setRestitution(entity.getIsReturned());
		DictionaryItem di2 = dictionaryItemService.findById(entity
				.getIsReturned());
		if (di2 != null) {
			bean.setRestitutionStr(di2.getName());
		} else {
			bean.setRestitutionStr("");
		}
		bean.setRemark(entity.getRemark());
		List<FileObjectBean> fobLst = new ArrayList<FileObjectBean>();
		List<Attachment> attLst = attachmentCustomizedService
				.findByTargetIdAndType(entity.getId(),
						OutStorageForm.class.getName());
		for (Attachment att : attLst) {
			FileObjectBean fBean = new FileObjectBean();
			fBean.setId(att.getId());
			fBean.setName(att.getName());
			fBean.setSize(String.valueOf(att.getSize()));
			String base64Str;
			InputStream is = att.getAttachmentMeta().getAttachmentCopys().get(0).getInputStream();
			try {
				byte[] buffer = new byte[is.available()];
				is.read(buffer, 0, is.available());
				base64Str=new String (Base64.encodeBase64(buffer, false));
				fBean.setBase64Str(base64Str);
			} catch (Exception e) {
				LOGGER.debug("出库单查询照片附件转Base64异常",e);
			}
			
//			try {
//				base64Str = Base64CryptFmtUtil.encode(FileFmtUtils.readByByteBuffer(att.getAttachmentMeta().getAttachmentCopys().get(0).getInputStream()), false);
//				fBean.setBase64Str(base64Str);
//			} catch (UnsupportedEncodingException e) {
//				LOGGER.debug("出库单查询照片附件转Base64异常",e);
//			}
			fobLst.add(fBean);
		}
		bean.setAttachLst(fobLst);
		return bean;
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

	public OutStorageFormServiceBean getOutStorageFormBean() {
		return outStorageFormBean;
	}

	public void setOutStorageFormBean(
			OutStorageFormServiceBean outStorageFormBean) {
		this.outStorageFormBean = outStorageFormBean;
	}

	public List<StorageOutBean> getStorageOutBeanList() {
		return storageOutBeanList;
	}

	public void setStorageOutBeanList(List<StorageOutBean> storageOutBeanList) {
		this.storageOutBeanList = storageOutBeanList;
	}

	public StorageOutBean getStorageOutBean() {
		return storageOutBean;
	}

	public void setStorageOutBean(StorageOutBean storageOutBean) {
		this.storageOutBean = storageOutBean;
	}

	public List<StorageOutExcelBean> getStorageOutExcelBeanList() {
		return storageOutExcelBeanList;
	}

	public void setStorageOutExcelBeanList(
			List<StorageOutExcelBean> storageOutExcelBeanList) {
		this.storageOutExcelBeanList = storageOutExcelBeanList;
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
}
