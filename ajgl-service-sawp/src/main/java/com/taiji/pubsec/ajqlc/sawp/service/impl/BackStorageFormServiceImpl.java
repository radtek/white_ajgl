package com.taiji.pubsec.ajqlc.sawp.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.generatenum.service.IGenerateNumService;
import com.taiji.pubsec.ajqlc.sawp.bean.BackStorageFormItemServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.BackStorageFormServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.StorageServiceBean;
import com.taiji.pubsec.ajqlc.sawp.model.ArticleLocker;
import com.taiji.pubsec.ajqlc.sawp.model.BackStorageForm;
import com.taiji.pubsec.ajqlc.sawp.model.BackStorageFormItem;
import com.taiji.pubsec.ajqlc.sawp.model.BackStorageRecord;
import com.taiji.pubsec.ajqlc.sawp.model.InvolvedArticle;
import com.taiji.pubsec.ajqlc.sawp.model.OutStorageForm;
import com.taiji.pubsec.ajqlc.sawp.model.OutStorageFormItem;
import com.taiji.pubsec.ajqlc.sawp.model.Storage;
import com.taiji.pubsec.ajqlc.sawp.model.StorageArea;
import com.taiji.pubsec.ajqlc.sawp.service.IArticleLockerService;
import com.taiji.pubsec.ajqlc.sawp.service.IBackStorageFormService;
import com.taiji.pubsec.ajqlc.sawp.service.IInvolvedArticleService;
import com.taiji.pubsec.ajqlc.sawp.service.IOutStorageFormService;
import com.taiji.pubsec.ajqlc.sawp.service.IStorageService;
import com.taiji.pubsec.ajqlc.sawp.util.ParamMapUtil;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.common.tool.sql.SQLTool;
@Service("backStorageFormService")
public class BackStorageFormServiceImpl implements IBackStorageFormService {
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@Resource
	private IInvolvedArticleService  involvedSrticleService;
	
	@Resource
	private IStorageService storageService;
	
	@Resource
	private IGenerateNumService generateNumService;
	
	@Resource
	private IArticleLockerService articleLockerService;
	@Resource
	private IOutStorageFormService outStorageFormService;
	
	@Resource
	private IDictionaryItemService dictionaryItemService;// 字典项接口
	
	public static final String ISSHOWMYFORM_YES = "1";
	public static final String ISSHOWMYFORM_NO = "1";
	public static final String ISRETURNALL_YES = "0000000007002"; //字典项是否全部返还:是

	@SuppressWarnings("unchecked")
	@Override
	public String createForm(BackStorageFormServiceBean backStorageFormBean, List<BackStorageFormItemServiceBean> backStorageFormItemList) {
		if(backStorageFormBean.getId() == null){
			BackStorageForm backStorageForm = new BackStorageForm ();
			//新增返还单
			backStorageForm.setCaseCode(backStorageFormBean.getCaseCode());
			backStorageForm.setCaseName(backStorageFormBean.getCaseName());
			backStorageForm.setHandlePolices(backStorageFormBean.getPolices());
			backStorageForm.setCode(backStorageFormBean.getCode());
			backStorageForm.setCreatedTime(new Date(backStorageFormBean.getCreatedTime()));
			backStorageForm.setBackStorageTime(new Date(backStorageFormBean.getCreatedTime()));
			backStorageForm.setOutStorageFormCode(backStorageFormBean.getOutStorageFormCode());
			backStorageForm.setRemark(backStorageFormBean.getRemark());
			this.dao.save(backStorageForm);
			
			//出库单是否全部再入库
			boolean ifReturnAll = true;
			if(backStorageFormItemList == null || backStorageFormItemList.size() == 0){
				ifReturnAll = false;
			}
			for(BackStorageFormItemServiceBean backStorageFormItemServiceBean: backStorageFormItemList){
				if("否".equals(backStorageFormItemServiceBean.getIsReturend())){
					ifReturnAll=false;
				}
				
				OutStorageFormItem outStorageFormItem = (OutStorageFormItem) this.dao.findById(OutStorageFormItem.class, backStorageFormItemServiceBean.getOutItemId());
				
				InvolvedArticle ia = involvedSrticleService.findInvolvedArticleById(backStorageFormItemServiceBean.getInvolvedArticleId());
				
				StorageArea storageArea = new StorageArea();
				if(backStorageFormItemServiceBean.getAreaId() == null){
					storageArea = null;
				}else{
					storageArea = (StorageArea) this.dao.findById(StorageArea.class, backStorageFormItemServiceBean.getAreaId());
				}
				
				BackStorageFormItem backStorageFormItem = new BackStorageFormItem();
				//新增返还单项
				backStorageFormItem.setForm(backStorageForm);
				backStorageFormItem.setOutItem(outStorageFormItem);
				backStorageFormItem.setRemark(backStorageFormItemServiceBean.getRemark());
				backStorageFormItem.setArticleId(backStorageFormItemServiceBean.getInvolvedArticleId());
				backStorageFormItem.setMaintainTime(new Date(backStorageFormItemServiceBean.getMaintainTime()));
				
				this.dao.save(backStorageFormItem);
				double returnCount = 0;
				for(StorageServiceBean storageServiceBean: backStorageFormItemServiceBean.getStorageServiceBeans()){
					//更新位置信息
					Storage storage = new Storage();
					String xql = "select s from Storage as s where s.article.id = ? and s.locker.id = ?";
					storage = (Storage) this.dao.findByParams(Storage.class, xql, new Object[]{backStorageFormItemServiceBean.getInvolvedArticleId(), storageServiceBean.getLockerId()});
					if(storage == null){
						Storage storage2 = new Storage();
						storage2.setArticle((InvolvedArticle) this.dao.findById(InvolvedArticle.class, backStorageFormItemServiceBean.getInvolvedArticleId()));
						storage2.setExistingNumber(storageServiceBean.getReturnNumber());
						storage2.setIncomingNumber(storageServiceBean.getReturnNumber());
						storage2.setLocker((ArticleLocker) this.dao.findById(ArticleLocker.class, storageServiceBean.getLockerId()));
						this.dao.save(storage2);
					}else{
						storage.setExistingNumber(storage.getExistingNumber() + storageServiceBean.getReturnNumber());
						storage.setIncomingNumber(storage.getIncomingNumber() + storageServiceBean.getReturnNumber());
						this.dao.update(storage);
					}
					returnCount += storageServiceBean.getReturnNumber();
					//新增返还记录
					BackStorageRecord backStorageRecord = new BackStorageRecord();
					backStorageRecord.setFormItem(backStorageFormItem);
					backStorageRecord.setLocker(articleLockerService.findById(storageServiceBean.getLockerId()));
					backStorageRecord.setReturnedNumber(storageServiceBean.getReturnNumber());
					this.dao.save(backStorageRecord);
				}
				backStorageFormItem.setReturnedNumber(returnCount);
				this.dao.update(backStorageFormItem);
				
				ia.setNumberInStorage(ia.getNumberInStorage() + returnCount);
				involvedSrticleService.update(ia);
				
			}
			
			//更新出库单是否全部再入库
			OutStorageForm outStorageForm = outStorageFormService.findByCode(backStorageFormBean.getOutStorageFormCode());
			if(ifReturnAll){
				outStorageForm.setIsReturned(ISRETURNALL_YES);
				dao.update(outStorageForm);
			}
			
			return backStorageForm.getId();
		}else{
			this.updateForm(backStorageFormBean, backStorageFormItemList);
			return null;
		}
		
		
}

	@SuppressWarnings("unchecked")
	@Override
	public BackStorageForm findById(String backStorageFormId) {
		return (BackStorageForm) this.dao.findById(BackStorageForm.class, backStorageFormId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pager<BackStorageForm> queryByCondition(Map<String, Object> paramMap, int pageNo, int pageSize) {
		StringBuilder xqlSelect = new StringBuilder("select b from BackStorageForm as b ");
		StringBuilder xqlWhere =new StringBuilder(" where 1 = 1");
		StringBuilder xqlCondition = new StringBuilder();
		Map<String, Object> xqlMap = new HashMap<String, Object> (0);
		if (ParamMapUtil.isNotBlank(paramMap.get("backStorageFormCode"))) {
			xqlCondition.append(" and b.code like :backStorageFormCode");
			SQLTool.SQLAddEscape(xqlCondition);
			xqlMap.put("backStorageFormCode", "%" + SQLTool.SQLSpecialChTranfer((String) paramMap.get("backStorageFormCode")) + "%");
		}
 		if (ParamMapUtil.isNotBlank(paramMap.get("backTimeStart"))) {
			xqlCondition.append(" and b.backStorageTime >= :backTimeStart");
			xqlMap.put("backTimeStart", paramMap.get("backTimeStart"));
			
		}
		if (ParamMapUtil.isNotBlank(paramMap.get("backTimeEnd"))) {
			xqlCondition.append(" and b.backStorageTime < :backTimeEnd");
			xqlMap.put("backTimeEnd", paramMap.get("backTimeEnd"));
		}
		if (ParamMapUtil.isNotBlank(paramMap.get("caseCode"))) {
			xqlCondition.append(" and b.caseCode like :caseCode");
			SQLTool.SQLAddEscape(xqlCondition);
			xqlMap.put("caseCode", "%" + SQLTool.SQLSpecialChTranfer((String) paramMap.get("caseCode")) + "%");
		}
		if (ParamMapUtil.isNotBlank(paramMap.get("remark"))) {
			xqlCondition.append(" and b.remark like :remark");
			SQLTool.SQLAddEscape(xqlCondition);
			xqlMap.put("remark", "%" + SQLTool.SQLSpecialChTranfer((String) paramMap.get("remark")) + "%");
		}
		if (ParamMapUtil.isNotBlank(paramMap.get("isShowMyBackStorage"))) {
			if(ISSHOWMYFORM_YES.equals(paramMap.get("isShowMyBackStorage"))) {
				xqlCondition.append(" and b.operator = :loginUser");
				xqlMap.put("loginUser", paramMap.get("isShowMyBackStorage"));
			}
		}
		if (ParamMapUtil.isNotBlank(paramMap.get("outStorageFormCode")) || ParamMapUtil.isNotBlank(paramMap.get("type"))) {
			if (ParamMapUtil.isNotBlank(paramMap.get("outStorageFormCode"))) {
				xqlCondition.append(" and b.outStorageFormCode like :outStorageFormCode");
				SQLTool.SQLAddEscape(xqlCondition);
				xqlMap.put("outStorageFormCode", "%" + SQLTool.SQLSpecialChTranfer((String) paramMap.get("outStorageFormCode")) + "%");
			}
			if (ParamMapUtil.isNotBlank(paramMap.get("type"))) {
				xqlSelect.append(" , OutStorageForm as o");
				xqlWhere.append(" and b.outStorageFormCode = o.code");
				xqlCondition.append(" and o.type = :type");
				xqlMap.put("type", paramMap.get("type"));
			}
		}
		return this.dao.findByPage(BackStorageForm.class, xqlSelect.append(xqlWhere).append(xqlCondition).toString(), xqlMap, pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateForm(BackStorageFormServiceBean backStorageFormBean, List<BackStorageFormItemServiceBean> backStorageFormItemList) {
		BackStorageForm backStorageForm = (BackStorageForm) this.dao.findById(BackStorageForm.class, backStorageFormBean.getId());
		//更新返还单
		backStorageForm.setCaseCode(backStorageForm.getCaseCode());
		backStorageForm.setCode(backStorageForm.getCode());
		backStorageForm.setCreatedTime(backStorageForm.getCreatedTime());
		if(backStorageFormBean.getCreatedTime() == null){
			backStorageForm.setBackStorageTime(new Date());
		}else{
			backStorageForm.setBackStorageTime(new Date(backStorageFormBean.getCreatedTime()));
		}
		backStorageForm.setOutStorageFormCode(backStorageForm.getOutStorageFormCode());
		backStorageForm.setRemark(backStorageFormBean.getRemark());
		this.dao.update(backStorageForm);
		
		//出库单是否全部再入库
		boolean ifReturnAll = true;
		if(backStorageFormItemList == null || backStorageFormItemList.size() == 0){
			ifReturnAll = false;
		}
		
		for(BackStorageFormItemServiceBean backStorageFormItemServiceBean: backStorageFormItemList){
			OutStorageFormItem outStorageFormItem = (OutStorageFormItem) this.dao.findById(OutStorageFormItem.class, backStorageFormItemServiceBean.getOutItemId());
			
			if("否".equals(backStorageFormItemServiceBean.getIsReturend())){
				ifReturnAll=false;
			}
			
			InvolvedArticle ia = involvedSrticleService.findInvolvedArticleById(backStorageFormItemServiceBean.getInvolvedArticleId());
			
			//更新返还单项
			BackStorageFormItem backStorageFormItem = (BackStorageFormItem) this.dao.findById(BackStorageFormItem.class, backStorageFormItemServiceBean.getBackItemId());
			backStorageFormItem.setNumberRequested(backStorageFormItemServiceBean.getOutcomingNumber());
			backStorageFormItem.setRemark(backStorageFormItemServiceBean.getRemark());
			this.dao.save(backStorageFormItem);
			
			int backCount = 0;
			
			for(StorageServiceBean storageServiceBean: backStorageFormItemServiceBean.getStorageServiceBeans()){
				if(StringUtils.isBlank(storageServiceBean.getBackFormRecordId())){
					//新增返还记录
					BackStorageRecord backStorageRecord = new BackStorageRecord();
					backStorageRecord.setFormItem(backStorageFormItem);
					backStorageRecord.setLocker(articleLockerService.findById(storageServiceBean.getLockerId()));
					backStorageRecord.setReturnedNumber(storageServiceBean.getReturnNumber());
					this.dao.save(backStorageRecord);
				}else{
					//更新返还记录
					BackStorageRecord backStorageRecord2 = (BackStorageRecord) this.dao.findById(BackStorageRecord.class, storageServiceBean.getBackFormRecordId());
					backStorageRecord2.setReturnedNumber(backStorageRecord2.getReturnedNumber() + storageServiceBean.getReturnNumber());
					this.dao.update(backStorageRecord2);
				}
				
				backCount += storageServiceBean.getReturnNumber();
				backStorageFormItem.setReturnedNumber(backStorageFormItem.getReturnedNumber() + backCount);
				this.dao.save(backStorageFormItem);
				
				//更新位置信息
				Storage storage = new Storage();
				String xql1 = "select s from Storage as s where s.article.id = ? and s.locker.id = ?";
				storage = (Storage) this.dao.findByParams(Storage.class, xql1, new Object[]{backStorageFormItemServiceBean.getInvolvedArticleId(), storageServiceBean.getLockerId()});
				if(storage == null){
					Storage storage2 = new Storage();
					storage2.setArticle((InvolvedArticle) this.dao.findById(InvolvedArticle.class, backStorageFormItemServiceBean.getInvolvedArticleId()));
					storage2.setExistingNumber(storageServiceBean.getReturnNumber());
					storage2.setIncomingNumber(storageServiceBean.getReturnNumber());
					storage2.setLocker((ArticleLocker) this.dao.findById(ArticleLocker.class, storageServiceBean.getLockerId()));
					this.dao.save(storage2);
				}else{
					storage.setExistingNumber(storage.getExistingNumber() + storageServiceBean.getReturnNumber());
					storage.setIncomingNumber(storage.getIncomingNumber() + storageServiceBean.getReturnNumber());
					this.dao.update(storage);
				}
					
			}
			
			//更新出库单是否全部再入库
			OutStorageForm outStorageForm = outStorageFormService.findByCode(backStorageForm.getOutStorageFormCode());
			if(ifReturnAll){
				outStorageForm.setIsReturned(ISRETURNALL_YES);
				dao.update(outStorageForm);
			}
			
			ia.setNumberInStorage(ia.getNumberInStorage() + backCount);
			involvedSrticleService.update(ia);
		}
	}
	
	@Override
	public String acquireNum() {
		String code = "FH";
		String num = generateNumService.acquireNum(code);
		int iNum = Integer.parseInt(num);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String nowYear = sdf.format(new Date());
		return code + nowYear + String.format("%04d", iNum);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BackStorageFormItem> findBackStorageFormItemsByOutStorageFormItemId(String outStorageFormItemId) {
		String xql = "select bsfi from BackStorageFormItem as bsfi where bsfi.outItem.id = ?";
		return this.dao.findAllByParams(BackStorageFormItem.class, xql, new Object[]{outStorageFormItemId});
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteForm(String formId) {
		BackStorageForm backStorageForm = (BackStorageForm) this.dao.findById(BackStorageForm.class, formId);
		Set<BackStorageFormItem> backStorageFormItems = backStorageForm.getBackStorageFormItems();
		
		for(BackStorageFormItem backStorageFormItem: backStorageFormItems){
			InvolvedArticle ia = involvedSrticleService.findInvolvedArticleById(backStorageFormItem.getArticleId());
			int backNum = 0;
			
			for(BackStorageRecord backStorageRecord: backStorageFormItem.getBackStorageRecords()){
				//更新位置信息
				Storage storage = new Storage();
				String xql = "select s from Storage as s where s.article.id = ? and s.locker.id = ?";
				storage = (Storage) this.dao.findByParams(Storage.class, xql, new Object[]{backStorageFormItem.getArticleId(), backStorageRecord.getLocker().getId()});
				storage.setExistingNumber(storage.getExistingNumber() - backStorageRecord.getReturnedNumber());
				storage.setIncomingNumber(storage.getIncomingNumber() - backStorageRecord.getReturnedNumber());
				this.dao.update(storage);
				this.dao.delete(backStorageRecord);
				
				backNum += backStorageRecord.getReturnedNumber();
			}
			this.dao.delete(backStorageFormItem);
			
			ia.setNumberInStorage(ia.getNumberInStorage() - backNum);
			involvedSrticleService.update(ia);
		}
		
		//删除返还单
		this.dao.delete(backStorageForm);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BackStorageForm findByOutStorageForm(String outStorageFormCode) {
		String hql = "select b from BackStorageForm as b where b.outStorageFormCode = :outStorageFormCode";
		Map<String, Object> xqlMap = new HashMap<String, Object>(0);
		xqlMap.put("outStorageFormCode", outStorageFormCode);
		List<BackStorageForm> list = this.dao.findAllByParams(BackStorageForm.class, hql, xqlMap);
		return list.isEmpty() ? null : list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BackStorageFormItemServiceBean> findBackStorageFormItemServiceBeanByOutFormId(String outFormId) {
		String xql = "select o, sum(o.outcomingNumber) from OutStorageFormItem as o where o.outcomingNumber > 0 and o.form.id = ? group by o.article.id order by o.article.code";
		List<Object> obj = this.dao.findAllByParams(OutStorageFormItem.class, xql, new Object[]{outFormId});
		List<OutStorageFormItem> outStorageFormItems = new ArrayList<OutStorageFormItem>();
		List<Number> numbers = new ArrayList<Number>();
		for(int i = 0; i < obj.size(); i++){
			Object[] o = (Object[]) obj.get(i);
			OutStorageFormItem outStorageFormItem = (OutStorageFormItem) o[0];
			Number number = (Number) o[1];
			outStorageFormItems.add(outStorageFormItem);
			numbers.add(number);
		}
		List<BackStorageFormItemServiceBean> backStorageFormItemServiceBeans = new ArrayList<BackStorageFormItemServiceBean>();
		for(int i = 0; i < outStorageFormItems.size(); i++){
			String xql1 = "select b from BackStorageFormItem as b where b.articleId = ? and b.outItem.id = ?";
			BackStorageFormItem backStorageFormItem = (BackStorageFormItem) this.dao.findByParams(BackStorageFormItem.class, xql1, new Object[]{outStorageFormItems.get(i).getArticle().getId(), outStorageFormItems.get(i).getId()});
			List<BackStorageRecord> backStorageRecords = new ArrayList<BackStorageRecord>();
			if(backStorageFormItem != null){
				backStorageRecords = backStorageFormItem.getBackStorageRecords();
			}
			Double returnNumber = 0.0;
			String remark = "";
			String areaId = "";
			String areaName = "";
			List<StorageServiceBean> storageServiceBeans = new ArrayList<StorageServiceBean>();
			
			for(BackStorageRecord backStorageRecord: backStorageRecords){
				returnNumber += backStorageRecord.getReturnedNumber();
				StorageServiceBean storageServiceBean = new StorageServiceBean();
				storageServiceBean.setLockerId(backStorageRecord.getLocker().getId());
				storageServiceBean.setLockerCode(backStorageRecord.getLocker().getCode());
				storageServiceBean.setLockerStatus(backStorageRecord.getLocker().getStatus());//状态
				storageServiceBean.setBackFormItemId(backStorageFormItem.getId());
				storageServiceBean.setLockerLocation(backStorageRecord.getLocker().getLocation());
				storageServiceBean.setAreaId(backStorageRecord.getLocker().getArea().getId());
				storageServiceBean.setAreaName(backStorageRecord.getLocker().getArea().getName());
				storageServiceBean.setAreaCode(backStorageRecord.getLocker().getArea().getCode());
				storageServiceBean.setBackFormRecordId(backStorageRecord.getId());
				storageServiceBean.setId(backStorageRecord.getId());
				areaId = backStorageRecord.getLocker().getArea().getId();
				areaName = backStorageRecord.getLocker().getArea().getName();
				storageServiceBean.setReturnNumber(backStorageRecord.getReturnedNumber());
				storageServiceBeans.add(storageServiceBean);
			}
			BackStorageFormItemServiceBean backStorageFormItemServiceBean = new BackStorageFormItemServiceBean();
			if(!"".equals(areaId)){
				List<ArticleLocker> lockerList = articleLockerService.findLockersByCaseCodeAndAreaId(outStorageFormItems.get(i).getArticle().getCaseCode(), areaId);
				backStorageFormItemServiceBean.setArticleLockerList(lockerList);
			}
			
			if(backStorageFormItem == null){
				backStorageFormItemServiceBean.setMaintainTime(null);
				backStorageFormItemServiceBean.setBackItemId("");
			}else{
				backStorageFormItemServiceBean.setMaintainTime(backStorageFormItem.getMaintainTime().getTime());
				backStorageFormItemServiceBean.setBackItemId(backStorageFormItem.getId());
			}
			backStorageFormItemServiceBean.setStorageServiceBeans(storageServiceBeans);
			backStorageFormItemServiceBean.setAreaId(areaId);
			backStorageFormItemServiceBean.setAreaName(areaName);
			backStorageFormItemServiceBean.setDetentionNumber(outStorageFormItems.get(i).getArticle().getDetentionNumber());
			backStorageFormItemServiceBean.setInvolvedArticleCode(outStorageFormItems.get(i).getArticle().getCode());
			backStorageFormItemServiceBean.setInvolvedArticleFeature(outStorageFormItems.get(i).getArticle().getFeature());
			backStorageFormItemServiceBean.setInvolvedArticleId(outStorageFormItems.get(i).getArticle().getId());
			backStorageFormItemServiceBean.setInvolvedArticleName(outStorageFormItems.get(i).getArticle().getName());
			backStorageFormItemServiceBean.setInvolvedArticleType(outStorageFormItems.get(i).getArticle().getType());
			String[] strArray = outStorageFormItems.get(i).getArticle().getType().split(",");
			String typeName = "";
			for(String str: strArray){
				typeName += dictionaryItemService.findById(str).getName() + " ";
			}
			backStorageFormItemServiceBean.setInvolvedArticleTypeName(typeName);
			backStorageFormItemServiceBean.setMeasurementUnit(outStorageFormItems.get(i).getArticle().getMeasurementUnit());
			backStorageFormItemServiceBean.setOutcomingNumber(Double.parseDouble(String.valueOf(numbers.get(i))));
			backStorageFormItemServiceBean.setOutItemId(outStorageFormItems.get(i).getId());
			
			backStorageFormItemServiceBean.setReturnedNumber(0.0);
			backStorageFormItemServiceBean.setSuspectedName(outStorageFormItems.get(i).getArticle().getSuspectName());
			backStorageFormItemServiceBean.setSuspectIdentityNumber(outStorageFormItems.get(i).getArticle().getSuspectIdentityNumber());
			if(backStorageFormItem != null){
				backStorageFormItemServiceBean.setBackItemId(backStorageFormItem.getId());
				backStorageFormItemServiceBean.setRemark(backStorageFormItem.getRemark());
			}else{
				backStorageFormItemServiceBean.setRemark(remark);
			}
//			if(backStorageFormItems.isEmpty()){
//				backStorageFormItemServiceBeans.get(i).setReturnedNumber(0);
//			}else{
//				backStorageFormItemServiceBeans.get(i).setReturnedNumber(backStorageFormItems.get(0).getReturnedNumber());;
//			}
			backStorageFormItemServiceBean.setReturnedNumber(returnNumber);
			
			backStorageFormItemServiceBeans.add(backStorageFormItemServiceBean);
		}
		
		return backStorageFormItemServiceBeans;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BackStorageForm findBackStorageFormByOutFormCode(String outFormCode) {
		String xql = "select bsf from BackStorageForm as bsf where bsf.outStorageFormCode = ?";
		return (BackStorageForm) this.dao.findByParams(BackStorageForm.class, xql, new Object[]{outFormCode});
	}

	@SuppressWarnings("unchecked")
	@Override
	public BackStorageFormItem findBackStorageFormItemByArticleIdAndLockerId(String articleId, String lockerId) {
		String xql = "select bsr from BackStorageRecord as bsr where bsr.formItem.articleId = ? and bsr.locker.id = ?";
		List<BackStorageRecord> backStorageRecords = this.dao.findAllByParams(BackStorageRecord.class, xql, new Object[]{articleId, lockerId});
		return backStorageRecords.isEmpty() ? null : backStorageRecords.get(0).getFormItem();
	}

	@SuppressWarnings("unchecked")
	@Override
	public BackStorageForm findBackStorageFormsByFormCode(String formCode) {
		String xql = "select bsf from BackStorageForm as bsf where bsf.code = ?";
		return (BackStorageForm) this.dao.findByParams(BackStorageForm.class, xql, new Object[]{formCode});
	}

	@SuppressWarnings("unchecked")
	@Override
	public BackStorageFormItem findItemById(String itemId) {
		return (BackStorageFormItem) this.dao.findById(BackStorageFormItem.class, itemId);
	}
}
