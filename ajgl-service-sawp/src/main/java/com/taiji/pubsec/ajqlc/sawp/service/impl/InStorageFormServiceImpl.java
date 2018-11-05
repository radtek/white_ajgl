package com.taiji.pubsec.ajqlc.sawp.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.dao.Pager;
import com.taiji.persistence.service.AbstractBaseService;
import com.taiji.pubsec.ajqlc.generatenum.service.IGenerateNumService;
import com.taiji.pubsec.ajqlc.sawp.bean.InStorageFormItemServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.InStorageFormServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.StorageServiceBean;
import com.taiji.pubsec.ajqlc.sawp.model.ArticleLocker;
import com.taiji.pubsec.ajqlc.sawp.model.BackStorageFormItem;
import com.taiji.pubsec.ajqlc.sawp.model.ImpoundedObject;
import com.taiji.pubsec.ajqlc.sawp.model.InStorageForm;
import com.taiji.pubsec.ajqlc.sawp.model.InStorageFormItem;
import com.taiji.pubsec.ajqlc.sawp.model.InStorageRecord;
import com.taiji.pubsec.ajqlc.sawp.model.InvolvedArticle;
import com.taiji.pubsec.ajqlc.sawp.model.OutStorageFormItem;
import com.taiji.pubsec.ajqlc.sawp.model.Storage;
import com.taiji.pubsec.ajqlc.sawp.service.IArticleLockerService;
import com.taiji.pubsec.ajqlc.sawp.service.IInStorageFormItemService;
import com.taiji.pubsec.ajqlc.sawp.service.IInStorageFormService;
import com.taiji.pubsec.ajqlc.sawp.service.IInStorageRecordService;
import com.taiji.pubsec.ajqlc.sawp.service.IInvolvedArticleService;
import com.taiji.pubsec.ajqlc.sawp.service.IStorageQueryService;
import com.taiji.pubsec.ajqlc.sawp.service.IStorageService;
import com.taiji.pubsec.ajqlc.sawp.util.ParamMapUtil;
import com.taiji.pubsec.ajqlc.util.DateFmtUtil;
import com.taiji.pubsec.businesscomponent.organization.service.IPersonService;
import com.taiji.pubsec.common.tool.sql.SQLTool;

/**
 * @author chengkai
 */
@Service("inStorageFormService")
public class InStorageFormServiceImpl extends AbstractBaseService<InStorageForm, String> implements IInStorageFormService {
	
	@Autowired
	public InStorageFormServiceImpl(Dao<InStorageForm, String> dao){
		setDao(dao);
	}
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@Resource
	private IStorageService storageService;
	
	@Resource
	private IPersonService personService;
	
	@Resource
	private IInvolvedArticleService involvedArticleService;
	
	@Resource
	private IGenerateNumService generateNumService;
	
	@Resource
	private IStorageQueryService storageQueryService;
	
	@Resource
	private IInStorageFormItemService inStorageFormItemSerice;
	
	@Resource
	private IInStorageRecordService inStorageRecordService;
	
	@Resource
	private IArticleLockerService articleLockerService;
	
	public static final String ISSHOWMYFORM_YES = "0000000007002";	//是否显示我的入库单：是
	public static final String ISSHOWMYFORM_NO = "0000000007001";	//是否显示我的入库单：否
	public static final String FMT = "yyyy-MM-dd HH:mm";  //时间格式
	public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(InStorageFormServiceImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public String createForm(InStorageFormServiceBean form, List<InStorageFormItemServiceBean> items) throws ParseException {
		// 保存入库单
		InStorageForm inStorageForm = new InStorageForm();
		inStorageForm.setCaseCode(form.getCaseCode());
		inStorageForm.setPapers(form.getPapers());
		inStorageForm.setSuspectName(form.getSuspectName());
		inStorageForm.setInStorageTime(SDF.parse(form.getCreatedTimeStr()));
		inStorageForm.setCode(form.getCode());
		inStorageForm.setCreatedTime(new Date());
		inStorageForm.setModifyPeople(personService.findById(form.getOperator()));
		inStorageForm.setRemark(form.getRemark());
		inStorageForm.setCaseName(form.getCaseName());
		inStorageForm.setHandlePolices(form.getCaseHandler());
		inStorageForm.setSuspectId(form.getSuspectId());
		
		this.dao.save(inStorageForm);
		
		// 保存入库单项、物品信息、位置信息
		for(InStorageFormItemServiceBean ifb : items){
			//保存物品信息
			InvolvedArticle involvedArticle = new InvolvedArticle();
			involvedArticle.setCaseCode(form.getCaseCode());
			involvedArticle.setCaseName(form.getCaseName());
			involvedArticle.setCode(ifb.getArticleCode());
			involvedArticle.setDetentionNumber(ifb.getDetentionNumber());
			involvedArticle.setFeature(ifb.getArticleFeature());
			involvedArticle.setMeasurementUnit(ifb.getMeasurementUnit());
			involvedArticle.setName(ifb.getArticleName());
			involvedArticle.setNumberInStorage(ifb.getNumberIncome());
			involvedArticle.setPaper(ifb.getPaperName());
			involvedArticle.setPaperId(ifb.getPaperId());
			involvedArticle.setPaperType(ifb.getPaperType());
			involvedArticle.setPolices(form.getCaseHandler());
			involvedArticle.setPoliceNumber1(form.getPoliceNumber1());
			involvedArticle.setPoliceNumber2(form.getPoliceNumber2());
			involvedArticle.setSuspectName(ifb.getSuspectName());
			involvedArticle.setType(ifb.getArticleType());
			involvedArticle.setRemark(ifb.getRemark() == null ? "" : ifb.getRemark());
			involvedArticle.setSuspectIdentityNumber(ifb.getSuspectIdentifier());
			involvedArticle.setInStorageTime(new Date());
			involvedArticleService.createInvolvedArticle(involvedArticle);
			
			//保存入库单项
			InStorageFormItem inStorageFormItem = new InStorageFormItem();
			inStorageFormItem.setNumberRequested(ifb.getNumberRequested());
			inStorageFormItem.setUpdatedTime(new Date());
			inStorageFormItem.setRemark(ifb.getRemark());
			inStorageFormItem.setForm(inStorageForm);
			inStorageFormItem.setArticle(involvedArticle);
			inStorageFormItemSerice.save(inStorageFormItem);
			
			double totalIncomingNumber = 0;
			
			//保存位置信息
			for(StorageServiceBean ssb : ifb.getStorageServiceBeans()){
				Storage storage = new Storage();
				storage.setExistingNumber(ssb.getIncomingNumber());
				storage.setIncomingNumber(ssb.getIncomingNumber());
				storageService.createStorage(storage, ssb.getLockerId(), involvedArticle.getId());
				
				totalIncomingNumber += ssb.getIncomingNumber();
				
				//保存入库操作
				InStorageRecord isr = new InStorageRecord();
				isr.setIncomingNumber(ssb.getIncomingNumber());
				isr.setLocker(articleLockerService.findById(ssb.getLockerId()));
				isr.setFormItem(inStorageFormItem);
				inStorageRecordService.save(isr);
				// 保存入库单项
//				InStorageFormItem inStorageFormItem = new InStorageFormItem();
//				inStorageFormItem.setArticle(involvedArticle);
//				inStorageFormItem.setForm(inStorageForm);
//				inStorageFormItem.setIncomingNumber(items.get(i).getStorageServiceBeans().get(j).getIncomingNumber());
//				inStorageFormItem.setLocker((ArticleLocker) this.dao.findById(ArticleLocker.class, items.get(i).getStorageServiceBeans().get(j).getLockerId()));
//				inStorageFormItem.setRemark(items.get(i).getRemark() == null ? "" : items.get(i).getRemark());
//				this.dao.save(inStorageFormItem);
			}
			
			inStorageFormItem.setNumberIncome(totalIncomingNumber);
			inStorageFormItemSerice.update(inStorageFormItem);
			
			involvedArticle.setNumberInStorage(totalIncomingNumber);
			involvedArticleService.update(involvedArticle);
		}
		
		return inStorageForm.getId();
	}

	@SuppressWarnings("unchecked")
	@Override 
	public Pager<InStorageForm> queryByCondition(Map<String, Object> conditions, int pageNo, int pageSize) {
		StringBuilder xql = new StringBuilder("select isf from InStorageForm as isf where 1 = 1 ");
		Map<String, Object> xqlMap = new HashMap<String, Object>();
		if(ParamMapUtil.isNotBlank(conditions.get("code"))){
			xql.append(" and isf.code like :code ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("code", "%" + conditions.get("code") + "%");
		}
		if(ParamMapUtil.isNotBlank(conditions.get("startTime"))){
			xql.append(" and isf.inStorageTime >= :startTime ");
				xqlMap.put("startTime", conditions.get("startTime"));
		}
		if(ParamMapUtil.isNotBlank(conditions.get("endTime"))){
			xql.append(" and isf.inStorageTime < :endTime ");
			xqlMap.put("endTime", conditions.get("endTime"));
		}
		if(ParamMapUtil.isNotBlank(conditions.get("caseCode"))){
			xql.append(" and isf.caseCode like :caseCode ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("caseCode", "%" + conditions.get("caseCode") + "%");
		}
		if(ParamMapUtil.isNotBlank(conditions.get("suspectName"))){
			xql.append(" and isf.suspectName like :suspectName ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("suspectName", "%" + conditions.get("suspectName") + "%");
		}
		if(ParamMapUtil.isNotBlank(conditions.get("papers"))){
			xql.append(" and isf.papers like :papers ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("papers", "%" + conditions.get("papers") + "%");
		}
		if(ParamMapUtil.isNotBlank(conditions.get("remark"))){
			xql.append(" and isf.remark like :remark ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("remark", "%" + conditions.get("remark") + "%");
		}
		if(ParamMapUtil.isNotBlank(conditions.get("isShowMyForm")) && ISSHOWMYFORM_YES.equals(conditions.get("isShowMyForm"))){
			xql.append(" and isf.modifyPeople.id = :modifyPeople ");
			xqlMap.put("modifyPeople", conditions.get("loginUser"));
		}
		xql.append(" order by isf.code");
		
		return this.dao.findByPage(InStorageForm.class, xql.toString(), xqlMap, pageNo, pageSize);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateForm(InStorageFormServiceBean form, List<InStorageFormItemServiceBean> items) throws ParseException{
		// 更新入库单信息
//		InStorageForm inStorageForm = (InStorageForm) this.dao.findById(InStorageForm.class, form.getId());
		InStorageForm inStorageForm = this.findInStorageFormByFormCode(form.getCode());
		if(form.getCreatedTimeStr() == null){
			inStorageForm.setInStorageTime(new Date());
		}else{
			inStorageForm.setInStorageTime(SDF.parse(form.getCreatedTimeStr()));
		}
		if(!StringUtils.isBlank(form.getPapers())){//因为手机端不传文书名称，所以需要加这个判断
			inStorageForm.setPapers(form.getPapers());
		}
		inStorageForm.setRemark(form.getRemark());
		inStorageForm.setUpdatedTime(new Date());
		this.update(inStorageForm);
		
		// 保存入库单项、物品信息、位置信息
//		for(int i = 0; i < items.size(); i++){
		for(InStorageFormItemServiceBean iib : items) {
			InStorageFormItem ifi;
			InvolvedArticle ia = involvedArticleService.findInvolvedArticleByCode(iib.getArticleCode());
			
			if (null == ia) {
				ia = new InvolvedArticle();
				ia.setCaseCode(inStorageForm.getCaseCode());
				ia.setCaseName(inStorageForm.getCaseName());
				ia.setCode(iib.getArticleCode());
				ia.setDetentionNumber(iib.getDetentionNumber());
				ia.setFeature(iib.getArticleFeature());
				ia.setMeasurementUnit(iib.getMeasurementUnit());
				ia.setName(iib.getArticleName());
				ia.setNumberInStorage(iib.getNumberIncome());
				ia.setPaper(iib.getPaperName());
				ia.setPolices(iib.getPolices());
				ia.setPoliceNumber1(iib.getPoliceNumber1());
				ia.setPoliceNumber2(iib.getPoliceNumber2());
				ia.setSuspectName(iib.getSuspectName());
				ia.setType(iib.getArticleType());
				ia.setRemark(iib.getRemark() == null ? "" : iib.getRemark());
				ia.setSuspectIdentityNumber(iib.getSuspectIdentifier());
				ia.setInStorageTime(new Date());
				involvedArticleService.createInvolvedArticle(ia);
			} else {
				ia.setType(iib.getArticleType());
				ia.setMeasurementUnit(iib.getMeasurementUnit());
				involvedArticleService.update(ia);
			}
			
			double inStorageNum = 0;
			
			//新入库单项物品
			if (null == iib.getId()) {
				ifi = new InStorageFormItem();
				ifi.setNumberRequested(iib.getNumberRequested());
				ifi.setUpdatedTime(new Date());
				ifi.setRemark(iib.getRemark());
				ifi.setForm(inStorageForm);
				ifi.setArticle(ia);
				inStorageFormItemSerice.save(ifi);
			}
			//旧入库单项物品
			else
			{
				ifi = inStorageFormItemSerice.findById(iib.getId());
				ifi.setNumberRequested(iib.getNumberRequested());
				ifi.setUpdatedTime(new Date());
				ifi.setRemark(iib.getRemark());
				ifi.setForm(inStorageForm);
				ifi.setArticle(ia);
				inStorageFormItemSerice.update(ifi);
			}
			
			//新入库物品和已入库物品
			if(!iib.getStorageServiceBeans().isEmpty()) {
				for(StorageServiceBean ssb : iib.getStorageServiceBeans()) {
					InStorageRecord ir = new InStorageRecord();
					if(StringUtils.isNotBlank(ssb.getId())){
						ir = inStorageRecordService.findById(ssb.getId());
					}else{
						ir = null;
					}
					ArticleLocker al = articleLockerService.findById(ssb.getLockerId());
					//新物品或旧物品新柜子
					if(null == ir) {
						InStorageRecord ird = new InStorageRecord();
						ird.setIncomingNumber(ssb.getIncomingNumber());
						ird.setLocker(al);
						ird.setFormItem(ifi);
						inStorageRecordService.save(ird);
					}
					//旧物品旧柜子更新数量
					else {
						ir.setIncomingNumber(ir.getIncomingNumber() + ssb.getIncomingNumber());
						inStorageRecordService.update(ir);
					}
					Storage s = storageService.findStorageByArticleCodeAndLockerId(ia.getCode(), ssb.getLockerId());
					//新的存储区域
					if(null == s) {
						Storage storage = new Storage();
						storage.setExistingNumber(ssb.getIncomingNumber());
						storage.setIncomingNumber(ssb.getIncomingNumber());
						storageService.createStorage(storage, ssb.getLockerId(), ia.getId());
					} 
					//旧的存储区域
					else {
						s.setLocker(al);
						s.setExistingNumber(s.getExistingNumber() + ssb.getIncomingNumber());
						s.setIncomingNumber(s.getIncomingNumber() + ssb.getIncomingNumber());
						storageService.update(s);
					}
					
					inStorageNum += ssb.getIncomingNumber();

				}
				
				ia.setNumberInStorage(ia.getNumberInStorage() + inStorageNum);
				ifi.setNumberIncome(ifi.getNumberIncome() + inStorageNum);
			}
		}
		
	}
	
	@Override
	public String acquireNum() {
		String code = "RK";
		String num = generateNumService.acquireNum(code);
		int iNum = Integer.parseInt(num);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String nowYear = sdf.format(new Date());
		return code + nowYear + String.format("%04d", iNum);
	}
	
	@Override
	public InStorageFormServiceBean inStorageFormToBean(InStorageForm inStorageForm) {
		Set<InStorageFormItem> inStorageFormItems = (Set<InStorageFormItem>) inStorageForm.getInStorageFormItems();
		InStorageFormServiceBean inStorageFormBean = new InStorageFormServiceBean();
		Iterator<InStorageFormItem> it = inStorageFormItems.iterator();
		while(it.hasNext()){
			InStorageFormItemServiceBean iStorageFormItemBean = this.inStorageFormItemToBean(it.next());
			inStorageFormBean.getInStorageFormItems().add(iStorageFormItemBean);
		}
		
		inStorageFormBean.setCaseCode(inStorageForm.getCaseCode());
		inStorageFormBean.setCaseHandler(inStorageForm.getHandlePolices());	
		inStorageFormBean.setCaseName(inStorageForm.getCaseName());	
		inStorageFormBean.setCode(inStorageForm.getCode());
//		inStorageFormBean.setCreatedTimeStr(dateToStr(inStorageForm.getCreatedTime(),FMT));
		inStorageFormBean.setCreatedTimeStr(dateToStr(inStorageForm.getInStorageTime(),FMT));
		inStorageFormBean.setEditOrNot(inStorageFormEditOrNot(inStorageForm.getId()));	
		inStorageFormBean.setId(inStorageForm.getId());
		inStorageFormBean.setPapers(inStorageForm.getPapers());
		inStorageFormBean.setRemark(inStorageForm.getRemark());
		inStorageFormBean.setSuspectName(inStorageForm.getSuspectName());
		inStorageFormBean.setUpdatedTimeStr(dateToStr(inStorageForm.getUpdatedTime(),FMT));
		
		return inStorageFormBean;
	}

	@Override
	public InStorageFormItemServiceBean inStorageFormItemToBean(InStorageFormItem inStorageFormItem) {
		InStorageFormItemServiceBean inStorageFormItemServiceBean = new InStorageFormItemServiceBean();
		inStorageFormItemServiceBean.setArticleCode(inStorageFormItem.getArticle().getCode());
		inStorageFormItemServiceBean.setArticleId(inStorageFormItem.getArticle().getId());
		inStorageFormItemServiceBean.setArticleName(inStorageFormItem.getArticle().getName());
		inStorageFormItemServiceBean.setCaseCode(inStorageFormItem.getArticle().getCaseCode());
		inStorageFormItemServiceBean.setPolices(inStorageFormItem.getArticle().getPolices());
		inStorageFormItemServiceBean.setCaseName(inStorageFormItem.getArticle().getCaseCode());	
		inStorageFormItemServiceBean.setDetentionNumber(inStorageFormItem.getArticle().getDetentionNumber());
		inStorageFormItemServiceBean.setArticleFeature(inStorageFormItem.getArticle().getFeature());
		inStorageFormItemServiceBean.setId(inStorageFormItem.getId());
		inStorageFormItemServiceBean.setInStorageTime(DateFmtUtil.dateToLong(inStorageFormItem.getArticle().getInStorageTime()));
		inStorageFormItemServiceBean.setMeasurementUnit(inStorageFormItem.getArticle().getMeasurementUnit());
		inStorageFormItemServiceBean.setNumberIncome(inStorageFormItem.getNumberIncome());
		inStorageFormItemServiceBean.setPaperName(inStorageFormItem.getArticle().getPaper());
		inStorageFormItemServiceBean.setPaperId(inStorageFormItem.getArticle().getPaperId());
		inStorageFormItemServiceBean.setPaperType(inStorageFormItem.getArticle().getPaperType());
		inStorageFormItemServiceBean.setRemark(inStorageFormItem.getArticle().getRemark());
		inStorageFormItemServiceBean.setSuspectIdentifier(inStorageFormItem.getArticle().getSuspectIdentityNumber());
		inStorageFormItemServiceBean.setSuspectName(inStorageFormItem.getArticle().getSuspectName());
		inStorageFormItemServiceBean.setArticleType(inStorageFormItem.getArticle().getType());
		inStorageFormItemServiceBean.setUpdatedTime(DateFmtUtil.dateToLong(inStorageFormItem.getUpdatedTime()));
		inStorageFormItemServiceBean.setStorageServiceBeans(null);
		inStorageFormItemServiceBean.setNumberRequested(inStorageFormItem.getNumberRequested());
		return inStorageFormItemServiceBean;
	}

	//TODO 
	@SuppressWarnings("unchecked")
	@Override
	public boolean inStorageFormEditOrNot(String inStorageFormId) {
		InStorageForm inStorageForm = (InStorageForm) this.dao.findById(InStorageForm.class, inStorageFormId);
		Set<InStorageFormItem> inStorageFormItems = inStorageForm.getInStorageFormItems();
		for(InStorageFormItem inStorageFormItem: inStorageFormItems){
			String xql = "select osfi from OutStorageFormItem as osfi where osfi.article.id = ?";
			List<OutStorageFormItem> outStorageFormItems = this.dao.findAllByParams(OutStorageFormItem.class, xql, new Object[]{inStorageFormItem.getArticle().getId()});
			String xql1 = "select asfi from AdjustmentStorageFormItem as asfi where asfi.article.id = ?";
			List<BackStorageFormItem> backStorageFormItems = this.dao.findAllByParams(BackStorageFormItem.class, xql1, new Object[]{inStorageFormItem.getArticle().getId()});
			if(!(outStorageFormItems.isEmpty() && backStorageFormItems.isEmpty())){
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteFormById(String formId) {
		InStorageForm inStorageForm = (InStorageForm) this.dao.findById(InStorageForm.class, formId);
		Set<InStorageFormItem> inStorageFormItems = inStorageForm.getInStorageFormItems();
		Set<String> articleIds = new HashSet<String>();
		//删除入库单项
		for(InStorageFormItem inStorageFormItem: inStorageFormItems){
			articleIds.add(inStorageFormItem.getArticle().getId());
			//删除入库操作
			for(InStorageRecord ir : inStorageFormItem.getInStorageRecords()) {
				inStorageRecordService.delete(ir.getId());
			}
			//删除保管位置
			for (Storage storage : inStorageFormItem.getArticle().getStorages()) {
				dao.delete(storage);
			}
			//删除入库单项
			this.dao.delete(inStorageFormItem);
		}
		//删除涉案物品
		for (String id : articleIds) {
			dao.delete(InvolvedArticle.class, id);
		}
		this.dao.delete(inStorageForm);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pager<InStorageFormItem> findInStorageFormItemsByFormIdForPage(String formId, int pageNo, int pageSize) {
		String xql = "select isfi from InStorageFormItem as isfi where isfi.form.id = ? order by isfi.article.code";
		return this.dao.findByPage(InStorageFormItem.class, new Object[]{formId}, xql, pageNo, pageSize);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<InStorageFormItem> findInStorageFormItemsByFormId(String formId) {
		String xql = "select isfi from InStorageFormItem as isfi where isfi.form.id = ? order by isfi.article.code,isfi.updatedTime,-isfi.article.paper desc";
		return this.dao.findAllByParams(InStorageFormItem.class, xql,new Object[]{formId});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public InStorageFormItem findInStorageFormItemsByArticleCodeAndLockerId(String articleCode,String lockerId) {
		String xql = "select isr.formItem from InStorageRecord as isr where isr.formItem.article.code = ? and isr.locker.id = ?";
		List<InStorageFormItem> list = this.dao.findAllByParams(InStorageRecord.class,  xql,new Object[]{articleCode,lockerId});
		return list.isEmpty() ? null : list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public InStorageForm findInStorageFormByCaseCodeAndSuspectId(String caseCode,String suspectId) {
		String xql = "select form from InStorageForm as form where form.caseCode = ? and form.suspectId = ?";
		List<InStorageForm> list = this.dao.findAllByParams(InStorageForm.class,  xql,new Object[]{caseCode, suspectId});
		return list.isEmpty() ? null : list.get(0);
	}
	
	
	/**
	 * long时间转化成打印字符串
	 * @param time 毫秒数
	 * @param fmt 输出格式，默认为精确到分钟
	 * @return 
	 */
	private String longDateToStr(Long time, String fmt){
		String str = "";
		if(time == null){
			return str;
		}
		if(StringUtils.isBlank(fmt)){
			fmt = "yyyy-MM-dd HH:mm";
		}
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(time);
		Date date = ca.getTime();
		return dateToStr(date, fmt);
	}
	
	/**
	 * Date时间转化成打印字符串
	 * @param Date 毫秒数
	 * @param fmt 输出格式，默认为精确到分钟
	 * @return 
	 */
	private String dateToStr(Date date, String fmt){
		String str = "";
		if(date == null){
			return str;
		}
		if(StringUtils.isBlank(fmt)){
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

	@SuppressWarnings("unchecked")
	@Override
	public InStorageForm findInStorageFormByFormCode(String formCode) {
		String xql = "select isf from InStorageForm as isf where isf.code = ?";
		return (InStorageForm) this.dao.findByParams(InStorageForm.class, xql, new Object[]{formCode});
	}

	@SuppressWarnings("unchecked")
	@Override
	public InStorageForm findInStorageFormByDocIdAndDocType(String docId, String docType) {
		String xql = "select i from ImpoundedObject as i where i.paper = ? and i.paperType = ?";
		List<ImpoundedObject> impoundedObjects = this.dao.findAllByParams(ImpoundedObject.class, xql, new Object[]{docId, docType});
		if(impoundedObjects.isEmpty()){
			return null;
		}else{
			String docName = impoundedObjects.get(0).getPaperName();
			Map<String, Object> xqlMap = new HashMap<String, Object>(0);
			String xql1 = "select i from InStorageForm as i where i.papers like :paperName";
			SQLTool.SQLAddEscape(xql1);
			xqlMap.put("paperName", "%" + SQLTool.SQLSpecialChTranfer(docName) + "%");
			List<InStorageForm> inStorageForms = this.dao.findAllByParams(InStorageForm.class, xql1, xqlMap);
			if(inStorageForms.isEmpty()){
				return null;
			}else{
				return inStorageForms.get(0);
			}
		}
	}

}
