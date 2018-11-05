package com.taiji.pubsec.ajqlc.sawp.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.generatenum.service.IGenerateNumService;
import com.taiji.pubsec.ajqlc.sawp.bean.AdjustmentStorageFormItemServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.AdjustmentStorageFormServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.StorageServiceBean;
import com.taiji.pubsec.ajqlc.sawp.model.AdjustgmentStorageForm;
import com.taiji.pubsec.ajqlc.sawp.model.AdjustmentStorageFormItem;
import com.taiji.pubsec.ajqlc.sawp.model.AdjustmentStorageRecord;
import com.taiji.pubsec.ajqlc.sawp.model.Storage;
import com.taiji.pubsec.ajqlc.sawp.service.IAdjustmentStorageFormService;
import com.taiji.pubsec.ajqlc.sawp.service.IArticleLockerService;
import com.taiji.pubsec.ajqlc.sawp.service.IInvolvedArticleService;
import com.taiji.pubsec.ajqlc.sawp.service.IStorageService;
import com.taiji.pubsec.ajqlc.sawp.util.ParamMapUtil;
import com.taiji.pubsec.common.tool.sql.SQLTool;

/**
 * @author chengkai
 */
@Service("adjustmentStorageFormService")
public class AdjustmentStorageFormServiceImpl implements IAdjustmentStorageFormService {
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@Resource
	private IGenerateNumService generateNumService;
	
	@Resource
	private IInvolvedArticleService involvedArticleService;
	
	@Resource
	private IArticleLockerService articleLockerService;
	
	@Resource
	private IStorageService storageService;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AdjustmentStorageFormServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public String createForm(AdjustmentStorageFormServiceBean form, List<AdjustmentStorageFormItemServiceBean> items){
		AdjustgmentStorageForm asf = new AdjustgmentStorageForm();
	    asf.setCode(form.getCode());
//		asf.setCreatedTime(new Date(form.getCreatedTime()));
		asf.setReason(form.getReason());
		asf.setAdjustTime(new Date(form.getAdjustTime()));
		this.dao.save(asf); 
		
		for(AdjustmentStorageFormItemServiceBean formItem: items){
			//新增调整单项
			AdjustmentStorageFormItem item = new AdjustmentStorageFormItem();
			item.setArticle(involvedArticleService.findInvolvedArticleById(formItem.getArticleId()));
			item.setForm(asf);
			item.setRemark(formItem.getRemark());
			item.setStorageId(formItem.getStorageId());
			item.setAdjustmentNumber(formItem.getAdjustmentNumber());
			this.dao.save(item);
			
			int adjustNumber = 0;
			
			//修改调整位置，新增调整记录
			for(StorageServiceBean storage: formItem.getStorageServiceBeans()){
				//修改调整位置，入
				String xql1 = "select s from Storage as s where s.article.id = ? and s.locker.id = ?";
				Storage storage1 = (Storage) this.dao.findByParams(Storage.class,xql1, new Object[]{formItem.getArticleId(), storage.getLockerId()});
				if(storage1 == null){
					storage1 = new Storage();
					storage1.setArticle(involvedArticleService.findInvolvedArticleById(formItem.getArticleId()));
					storage1.setExistingNumber(storage.getAdjustNumber());
					storage1.setIncomingNumber(storage.getAdjustNumber());
					storage1.setLocker(articleLockerService.findById(storage.getLockerId()));
					storageService.save(storage1);
				}else{
					storage1.setExistingNumber(storage1.getExistingNumber() + storage.getAdjustNumber());
					storage1.setIncomingNumber(storage1.getIncomingNumber() + storage.getAdjustNumber());
					storageService.update(storage1);
				}
				
				adjustNumber += storage.getAdjustNumber();
				
				//新增调整记录
				AdjustmentStorageRecord adjustRecord = new AdjustmentStorageRecord();
				adjustRecord.setAdjustNumber(storage.getAdjustNumber());
				adjustRecord.setFormItem(item);
				adjustRecord.setLocker(articleLockerService.findById(storage.getLockerId()));
				this.dao.save(adjustRecord);
			}
			
			//修改调整位置，出
			Storage storage2 = storageService.findById(formItem.getStorageId());
			storage2.setExistingNumber(storage2.getExistingNumber() - adjustNumber);
			storageService.update(storage2);
			
		}
		
		return asf.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public AdjustgmentStorageForm findById(String formId) {
		return (AdjustgmentStorageForm) this.dao.findById(AdjustgmentStorageForm.class, formId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pager<AdjustgmentStorageForm> queryByCondition(Map<String, Object> conditions, int pageNo, int pageSize) {
		StringBuilder xql = new StringBuilder("select asf from AdjustgmentStorageForm as asf where 1 = 1 ");
		Map<String, Object> xqlMap = new HashMap<String, Object> (0);
		if(ParamMapUtil.isNotBlank(conditions.get("code"))){
			xql.append(" and asf.code like :code ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("code", "%" + conditions.get("code") + "%");
		}
		if(ParamMapUtil.isNotBlank(conditions.get("startTime"))){
			xql.append(" and asf.adjustTime >= :startTime ");
			xqlMap.put("startTime", conditions.get("startTime"));
		}
		if(ParamMapUtil.isNotBlank(conditions.get("endTime"))){
			xql.append(" and asf.adjustTime < :endTime ");
			xqlMap.put("endTime", conditions.get("endTime"));
		}
		xql.append(" order by asf.code");
		
		return this.dao.findByPage(AdjustgmentStorageForm.class, xql.toString(), xqlMap, pageNo, pageSize);
		
	}
	
	@Override
	public String acquireNum() {
		String code = "TZ";
		String num = generateNumService.acquireNum(code);
		int iNum = Integer.parseInt(num);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String nowYear = sdf.format(new Date());
		return code + nowYear + String.format("%04d", iNum);
	}

	@SuppressWarnings("unchecked")
	@Override
	public AdjustgmentStorageForm findByCode(String formCode) {
		String xql = "select asf from AdjustgmentStorageForm as asf where asf.code = ?";
		return (AdjustgmentStorageForm) this.dao.findByParams(AdjustgmentStorageForm.class, xql, new Object[]{formCode});
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateForm(String formCode,
			List<AdjustmentStorageFormItemServiceBean> adjustmentStorageFormItemServiceBeans) {
		String xql = "select asf from AdjustgmentStorageForm as asf where asf.code = ?";
		AdjustgmentStorageForm adjustgmentStorageForm = (AdjustgmentStorageForm) this.dao.findByParams(AdjustgmentStorageForm.class, xql, new Object[]{formCode});
		for(AdjustmentStorageFormItemServiceBean formItem: adjustmentStorageFormItemServiceBeans){
			AdjustmentStorageFormItem item = (AdjustmentStorageFormItem) this.dao.findById(AdjustmentStorageFormItem.class, formItem.getId());
			
			double adjustNumber = 0;
			for(StorageServiceBean storage: formItem.getStorageServiceBeans()){
				adjustNumber += storage.getAdjustNumber();
			}
			//修改调整位置，出
			Storage storage2 = storageService.findById(formItem.getStorageId());
			if(storage2.getExistingNumber() - adjustNumber >= 0){
				storage2.setExistingNumber(storage2.getExistingNumber() - adjustNumber);
				storageService.update(storage2);
				
				item.setAdjustmentNumber(adjustNumber);
				this.dao.update(item);
			}else{
				return ;
			}
			
			//修改调整位置，新增调整记录
			for(StorageServiceBean storage: formItem.getStorageServiceBeans()){
				if(StringUtils.isBlank(storage.getAdjustmentStorageRecordId())){
					//修改调整位置，入
					String xql1 = "select s from Storage as s where s.article.id = ? and s.locker.id = ?";
					Storage storage1 = (Storage) this.dao.findByParams(Storage.class,xql1, new Object[]{formItem.getArticleId(), storage.getLockerId()});
					if(storage1 == null){
						storage1 = new Storage();
						storage1.setArticle(involvedArticleService.findInvolvedArticleById(formItem.getArticleId()));
						storage1.setExistingNumber(storage.getAdjustNumber());
						storage1.setIncomingNumber(storage.getAdjustNumber());
						storage1.setLocker(articleLockerService.findById(storage.getLockerId()));
						storageService.save(storage1);
					}else{
						storage1.setExistingNumber(storage1.getExistingNumber() + storage.getAdjustNumber());
						storage1.setIncomingNumber(storage1.getIncomingNumber() + storage.getAdjustNumber());
						storageService.update(storage1);
					}
					
					adjustNumber += storage.getAdjustNumber();
					
					//新增调整记录
					AdjustmentStorageRecord adjustRecord = new AdjustmentStorageRecord();
					adjustRecord.setAdjustNumber(storage.getAdjustNumber());
					adjustRecord.setFormItem(item);
					adjustRecord.setLocker(articleLockerService.findById(storage.getLockerId()));
					this.dao.save(adjustRecord);
				}else{
					//修改调整位置，入
					String xql1 = "select s from Storage as s where s.article.id = ? and s.locker.id = ?";
					Storage storage1 = (Storage) this.dao.findByParams(Storage.class,xql1, new Object[]{formItem.getArticleId(), storage.getLockerId()});
					storage1.setExistingNumber(storage1.getExistingNumber() + storage.getAdjustNumber());
					storage1.setIncomingNumber(storage1.getIncomingNumber() + storage.getAdjustNumber());
					storageService.update(storage1);
					
					//更新调整记录
					AdjustmentStorageRecord adjustRecord = (AdjustmentStorageRecord) this.dao.findById(AdjustmentStorageRecord.class, storage.getAdjustmentStorageRecordId());
					adjustRecord.setAdjustNumber(adjustRecord.getAdjustNumber() + storage.getAdjustNumber());
					this.dao.update(adjustRecord);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public AdjustmentStorageFormItem findItemById(String itemId) {
		return (AdjustmentStorageFormItem) this.dao.findById(AdjustmentStorageFormItem.class, itemId);
	}
	
}