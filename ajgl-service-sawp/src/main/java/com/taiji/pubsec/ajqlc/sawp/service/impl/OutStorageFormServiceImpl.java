package com.taiji.pubsec.ajqlc.sawp.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.generatenum.service.IGenerateNumService;
import com.taiji.pubsec.ajqlc.sawp.bean.OutStorageFormItemServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.OutStorageFormServiceBean;
import com.taiji.pubsec.ajqlc.sawp.model.ArticleLocker;
import com.taiji.pubsec.ajqlc.sawp.model.BackStorageForm;
import com.taiji.pubsec.ajqlc.sawp.model.BackStorageFormItem;
import com.taiji.pubsec.ajqlc.sawp.model.InStorageForm;
import com.taiji.pubsec.ajqlc.sawp.model.InvolvedArticle;
import com.taiji.pubsec.ajqlc.sawp.model.OutStorageForm;
import com.taiji.pubsec.ajqlc.sawp.model.OutStorageFormItem;
import com.taiji.pubsec.ajqlc.sawp.model.Storage;
import com.taiji.pubsec.ajqlc.sawp.service.IBackStorageFormService;
import com.taiji.pubsec.ajqlc.sawp.service.IInvolvedArticleService;
import com.taiji.pubsec.ajqlc.sawp.service.IOutStorageFormService;
import com.taiji.pubsec.ajqlc.sawp.service.IStorageService;
import com.taiji.pubsec.ajqlc.sawp.util.ParamMapUtil;
import com.taiji.pubsec.common.tool.sql.SQLTool;

@Service("outStorageFormService")
public class OutStorageFormServiceImpl implements IOutStorageFormService {
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao; 
	
	@Resource
	private IStorageService storageService;
	
	@Resource
	private IGenerateNumService generateNumService;
	
	@Resource
	private IInvolvedArticleService involvedArticleService;
	
	@Resource
	private IBackStorageFormService backStorageFormService;
	
	//TODO 修改为对应的字典项编码
	private static final String ISRETURNED_YES = "0000000007002";
	private static final String ISRETURNED_NO = "0000000007001";
	public static final String ISSHOWMYFORM_YES = "1";
	public static final String ISSHOWMYFORM_NO = "0";

	@SuppressWarnings("unchecked")
	@Override
	public String createForm(OutStorageFormServiceBean outStorageFormBean, List<OutStorageFormItemServiceBean> outStorageFormItemBeanList) {
		OutStorageForm outStorageForm = new OutStorageForm ();
		//新增出库单
		outStorageForm.setCaseCode(outStorageFormBean.getCaseCode());
		outStorageForm.setCaseName(outStorageFormBean.getCaseName());
		outStorageForm.setCode(outStorageFormBean.getCode());
		outStorageForm.setIsReturned(ISRETURNED_NO);
		outStorageForm.setCreatedTime(new Date());
		outStorageForm.setOutStorageTime(new Date(outStorageFormBean.getOutStorageTime()));
		outStorageForm.setPapers(outStorageFormBean.getPapers());
		outStorageForm.setHandlePolices(outStorageFormBean.getPolices());
		outStorageForm.setReceiver(outStorageFormBean.getReceiver());
		outStorageForm.setRemark(outStorageFormBean.getRemark());
		outStorageForm.setType(outStorageFormBean.getType());
		this.dao.save(outStorageForm);
		
		for(int i = 0; i < outStorageFormItemBeanList.size(); i++){
			InvolvedArticle ia = involvedArticleService.findInvolvedArticleById(outStorageFormItemBeanList.get(i).getInvolvedArticleId());
			Double outNum = 0.0;
			
			if(outStorageFormItemBeanList.get(i).getOutcomingNumber() > 0){
				//更新物品位置数量信息
				Storage storage = new Storage();
				String xql = "select s from Storage as s where s.article.id = ? and s.locker.id = ?";
				storage = (Storage) this.dao.findByParams(Storage.class, xql, new Object[]{outStorageFormItemBeanList.get(i).getInvolvedArticleId(), outStorageFormItemBeanList.get(i).getLockerId()});
				if((storage.getExistingNumber()*1000 - outStorageFormItemBeanList.get(i).getOutcomingNumber()*1000)/1000 >= 0){
					outNum += outStorageFormItemBeanList.get(i).getOutcomingNumber();
					storage.setExistingNumber((storage.getExistingNumber()*1000 - outStorageFormItemBeanList.get(i).getOutcomingNumber()*1000)/1000);
					storageService.update(storage.getId());
				}
			}
			
			//更新物品在库数量
			ia.setNumberInStorage(ia.getNumberInStorage() - outNum);
			
			//新增出库单项
			OutStorageFormItem outStorageFormItem = new OutStorageFormItem();
			outStorageFormItem.setArticle((InvolvedArticle) this.dao.findById(InvolvedArticle.class, outStorageFormItemBeanList.get(i).getInvolvedArticleId()));
			outStorageFormItem.setForm(outStorageForm);
			outStorageFormItem.setExistingNumber(outStorageFormItemBeanList.get(i).getNumberInStorage());
			outStorageFormItem.setLocker((ArticleLocker) this.dao.findById(ArticleLocker.class, outStorageFormItemBeanList.get(i).getLockerId()));
			outStorageFormItem.setOutcomingNumber(outStorageFormItemBeanList.get(i).getOutcomingNumber());
			outStorageFormItem.setRemark(outStorageFormItemBeanList.get(i).getRemark());
			this.dao.save(outStorageFormItem);
		}
			
		
		return outStorageForm.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public OutStorageForm findById(String outStorageFormId) {
		return (OutStorageForm) this.dao.findById(OutStorageForm.class, outStorageFormId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pager<OutStorageForm> queryByCondition(Map<String, Object> paramMap, int pageNo, int pageSize){
		StringBuilder xql = new StringBuilder("select o from OutStorageForm as o where 1 = 1");
		Map<String, Object> xqlMap = new HashMap<String, Object> (0);
		if (ParamMapUtil.isNotBlank(paramMap.get("code"))) {
			xql.append(" and o.code like :code");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("code", "%" + SQLTool.SQLSpecialChTranfer((String) paramMap.get("code")) + "%");
		}
		if (ParamMapUtil.isNotBlank(paramMap.get("outStorageDateStart"))) {
			xql.append(" and o.outStorageTime >= :outStorageDateStart");
			xqlMap.put("outStorageDateStart", paramMap.get("outStorageDateStart"));
		}
		if (ParamMapUtil.isNotBlank(paramMap.get("outStorageDateEnd"))) {
			xql.append(" and o.outStorageTime < :outStorageDateEnd");
			xqlMap.put("outStorageDateEnd", paramMap.get("outStorageDateEnd"));
		}
		if (ParamMapUtil.isNotBlank(paramMap.get("caseCode"))) {
			xql.append(" and o.caseCode like :caseCode");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("caseCode", "%" + SQLTool.SQLSpecialChTranfer((String) paramMap.get("caseCode")) + "%");
		}
		if (ParamMapUtil.isNotBlank(paramMap.get("suspectName"))) {
			xql.append(" and o.caseName like :suspectName");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("suspectName", "%" + SQLTool.SQLSpecialChTranfer((String) paramMap.get("suspectName")) + "%");
		}
		if (ParamMapUtil.isNotBlank(paramMap.get("isReturned"))) {
			xql.append(" and o.isReturned = :isReturned");
			xqlMap.put("isReturned", paramMap.get("isReturned"));
		}
		if (ParamMapUtil.isNotBlank(paramMap.get("papers"))) {
			xql.append(" and o.papers like :papers");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("papers", "%" + SQLTool.SQLSpecialChTranfer((String) paramMap.get("papers")) + "%");
		}
		if (ParamMapUtil.isNotBlank(paramMap.get("remark"))) {
			xql.append(" and o.remark like :remark");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("remark", "%" + SQLTool.SQLSpecialChTranfer((String) paramMap.get("remark")) + "%");
		}
		if (ParamMapUtil.isNotBlank(paramMap.get("type"))) {
			xql.append(" and o.type = :type");
			xqlMap.put("type", paramMap.get("type"));
		}
		if (ParamMapUtil.isNotBlank(paramMap.get("isShowMyOutStorage"))) {
			if(ISSHOWMYFORM_YES.equals(paramMap.get("isShowMyOutStorage"))) {
				xql.append(" and o.modifyPeople = :modifyPeople");
				xqlMap.put("modifyPeople", paramMap.get("loginUser"));
			}
		}
		xql.append(" order by o.code");
		
		return this.dao.findByPageParams(OutStorageForm.class, xql.toString(), xqlMap, pageNo, pageSize);
	}

	@Override
	public String acquireNum() {
		String code = "CK";
		String num = generateNumService.acquireNum(code);
		int iNum = Integer.parseInt(num);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String nowYear = sdf.format(new Date());
		return code + nowYear + String.format("%04d", iNum);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteForm(String formId) {
		OutStorageForm outStorageForm = (OutStorageForm) this.dao.findById(OutStorageForm.class, formId);
		Set<OutStorageFormItem> outStorageFormItems = outStorageForm.getOutStorageFormItems();
		for(OutStorageFormItem outStorageFormItem: outStorageFormItems){
			//更新物品位置数量信息
			Storage storage = new Storage();
			String xql = "select s from Storage as s where s.article.id = ? and s.locker.id = ?";
			storage = (Storage) this.dao.findByParams(Storage.class, xql, new Object[]{outStorageFormItem.getArticle().getId(), outStorageFormItem.getLocker().getId()});
			if(storage != null){
				storage.setExistingNumber(storage.getExistingNumber() + outStorageFormItem.getOutcomingNumber());
				storageService.update(storage.getId());
			}else{
				Storage storage2 = new Storage();
				storage2.setArticle(outStorageFormItem.getArticle());
				storage2.setExistingNumber(outStorageFormItem.getOutcomingNumber());
				storage2.setIncomingNumber(outStorageFormItem.getOutcomingNumber());
				storage2.setLocker(outStorageFormItem.getLocker());
				this.dao.save(storage2);
			}
			
			//删除出库单项
			this.dao.delete(outStorageFormItem);
		}
			
		//删除出库单
		this.dao.delete(outStorageForm);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean outStorageFormEditOrNot(String outStorageFormId) {
		OutStorageForm outStorageForm = (OutStorageForm) this.dao.findById(OutStorageForm.class, outStorageFormId);
		String xql = "select bsf from BackStorageForm as bsf where bsf.outStorageFormCode = ?";
		BackStorageForm backStorageForm = (BackStorageForm) this.dao.findByParams(BackStorageForm.class, xql, new Object[]{outStorageForm.getCode()});
		if(backStorageForm != null){
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pager<OutStorageFormItem> findOutStorageFormItemsByFormIdForPage(String formId, int pageNo, int pageSize) {
		String xql = "select osfi from OutStorageFormItem as osfi where osfi.form.id = ? order by osfi.article.code";
		return this.dao.findByPage(OutStorageFormItem.class, new Object[]{formId}, xql, pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OutStorageForm> findOutStorageFormsByType(String caseCode, List<String> types) {
		StringBuilder xql =new StringBuilder("select distinct osf from OutStorageForm as osf, OutStorageFormItem as i where osf.id = i.form.id and i.outcomingNumber > 0"); 
		Map<String, Object> xqlMap = new HashMap<String, Object>();
		if(!types.isEmpty()){
			xql.append(" and osf.type in (:types) ");
			xqlMap.put("types", types);
		}
		if(caseCode != null){
			xql.append(" and osf.caseCode like :caseCode ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("caseCode", "%" + caseCode + "%");
		}
		xql.append(" and osf.isReturned = :isReturned ");
		xqlMap.put("isReturned", ISRETURNED_NO);
		List<OutStorageForm> osfList = this.dao.findAllByParams(OutStorageForm.class, xql.toString(), xqlMap);
		
		for(int i = osfList.size() - 1; i >= 0; i--){
			OutStorageForm outForm = osfList.get(i);
			BackStorageForm backForm = backStorageFormService.findBackStorageFormByOutFormCode(outForm.getCode());
			Set<OutStorageFormItem> outItemList = outForm.getOutStorageFormItems();
			//出库数量
			Double outNum = 0.0;
			for(OutStorageFormItem item: outItemList){
				outNum += item.getOutcomingNumber();
			}
			Double backNum = 0.0;
			if(backForm != null){
				Set<BackStorageFormItem> backItemList = backForm.getBackStorageFormItems();
				for(BackStorageFormItem item: backItemList){
					backNum += item.getReturnedNumber();
				}
			}
			
			if(outNum == backNum){
				osfList.remove(i);
			}
		}

		return osfList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public OutStorageForm findByCode(String outStorageFormCode) {
		String hql = "select o from OutStorageForm as o where o.code = :outStorageFormCode";
		Map<String, Object> xqlMap = new HashMap<String, Object>(0);
		xqlMap.put("outStorageFormCode", outStorageFormCode);
		List<OutStorageForm> list = this.dao.findAllByParams(OutStorageForm.class, hql, xqlMap);
		return list.isEmpty() ? null : list.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateForm(String formCode,List<OutStorageFormItemServiceBean> outStorageFormItemServiceBeans) {
		OutStorageForm outForm = this.findByCode(formCode);
		this.dao.save(outForm);
		for(OutStorageFormItemServiceBean outStorageFormItemServiceBean: outStorageFormItemServiceBeans){
			InvolvedArticle ia = involvedArticleService.findInvolvedArticleById(outStorageFormItemServiceBean.getInvolvedArticleId());
			if(outStorageFormItemServiceBean.getOutcomingNumber() > 0){
				//更新物品位置数量信息
				Storage storage = new Storage();
				String xql = "select s from Storage as s where s.article.code = ? and s.locker.code = ?";
				storage = (Storage) this.dao.findByParams(Storage.class, xql, new Object[]{outStorageFormItemServiceBean.getInvolvedArticleCode(), outStorageFormItemServiceBean.getLockerCode()});
				if(storage.getExistingNumber() - outStorageFormItemServiceBean.getOutcomingNumber() >= 0){
					storage.setExistingNumber((storage.getExistingNumber()*1000 - outStorageFormItemServiceBean.getOutcomingNumber()*1000)/1000);
					storageService.update(storage.getId());
					OutStorageFormItem outStorageFormItem = (OutStorageFormItem) this.dao.findById(OutStorageFormItem.class, outStorageFormItemServiceBean.getId());
					outStorageFormItem.setOutcomingNumber(outStorageFormItem.getOutcomingNumber() + outStorageFormItemServiceBean.getOutcomingNumber());
					this.dao.update(outStorageFormItem);
					
					ia.setNumberInStorage((ia.getNumberInStorage()*1000 - outStorageFormItemServiceBean.getOutcomingNumber()*1000)/1000);
					involvedArticleService.update(ia);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findCaseCodesForOutStorage() {
		List<String> caseList = new ArrayList<String>();
		List<InStorageForm> inStorageForms = this.dao.findAll(InStorageForm.class);
		for(InStorageForm inStorageForm: inStorageForms){
			String xql = "select ia from InvolvedArticle as ia where ia.caseCode = ?";
			List<InvolvedArticle> involvedArticles = this.dao.findAllByParams(InvolvedArticle.class, xql, new Object[]{inStorageForm.getCaseCode()});
			int count = 0;
			for(InvolvedArticle involvedArticle: involvedArticles){
				List<Storage> storages = involvedArticle.getStorages();
				for(Storage storage: storages){
					count += storage.getExistingNumber();
				}
			}
			if(count > 0){
				caseList.add(inStorageForm.getCaseCode());
			}
		}
		return caseList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OutStorageFormItem findItemById(String itemId) {
		return (OutStorageFormItem) this.dao.findById(OutStorageFormItem.class, itemId);
	}

	
}
