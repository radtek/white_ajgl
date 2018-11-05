package com.taiji.pubsec.ajqlc.httpinterface.action;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.pubsec.ajgl.util.OperationRecordConstant;
import com.taiji.pubsec.ajgl.util.PageCommonAction;
import com.taiji.pubsec.ajqlc.httpinterface.action.bean.AdjustmentStorageItemBean;
import com.taiji.pubsec.ajqlc.httpinterface.action.bean.AdjustmentStorageRecordBean;
import com.taiji.pubsec.ajqlc.httpinterface.action.bean.AjustmentFormBean;
import com.taiji.pubsec.ajqlc.httpinterface.action.bean.AjustmentFormItemBean;
import com.taiji.pubsec.ajqlc.httpinterface.action.bean.BackStorageItemBean;
import com.taiji.pubsec.ajqlc.httpinterface.action.bean.BackStorageRecordBean;
import com.taiji.pubsec.ajqlc.httpinterface.action.bean.FormBean;
import com.taiji.pubsec.ajqlc.httpinterface.action.bean.FormItemBean;
import com.taiji.pubsec.ajqlc.httpinterface.action.bean.InStorageItemBean;
import com.taiji.pubsec.ajqlc.httpinterface.action.bean.InStorageRecordBean;
import com.taiji.pubsec.ajqlc.httpinterface.action.bean.InvovledArticleBean;
import com.taiji.pubsec.ajqlc.httpinterface.action.bean.OutStorageItemBean;
import com.taiji.pubsec.ajqlc.httpinterface.action.bean.StorageBean;
import com.taiji.pubsec.ajqlc.operationrecord.model.OperationRecord;
import com.taiji.pubsec.ajqlc.operationrecord.service.IOperationRecordService;
import com.taiji.pubsec.ajqlc.sawp.bean.AdjustmentStorageFormItemServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.BackStorageFormItemServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.BackStorageFormServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.InStorageFormItemServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.InStorageFormServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.OutStorageFormItemServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.StorageServiceBean;
import com.taiji.pubsec.ajqlc.sawp.model.AdjustgmentStorageForm;
import com.taiji.pubsec.ajqlc.sawp.model.AdjustmentStorageFormItem;
import com.taiji.pubsec.ajqlc.sawp.model.AdjustmentStorageRecord;
import com.taiji.pubsec.ajqlc.sawp.model.ArticleLocker;
import com.taiji.pubsec.ajqlc.sawp.model.BackStorageForm;
import com.taiji.pubsec.ajqlc.sawp.model.BackStorageFormItem;
import com.taiji.pubsec.ajqlc.sawp.model.InStorageForm;
import com.taiji.pubsec.ajqlc.sawp.model.InStorageFormItem;
import com.taiji.pubsec.ajqlc.sawp.model.InStorageRecord;
import com.taiji.pubsec.ajqlc.sawp.model.InvolvedArticle;
import com.taiji.pubsec.ajqlc.sawp.model.OutStorageForm;
import com.taiji.pubsec.ajqlc.sawp.model.OutStorageFormItem;
import com.taiji.pubsec.ajqlc.sawp.model.Storage;
import com.taiji.pubsec.ajqlc.sawp.model.StorageArea;
import com.taiji.pubsec.ajqlc.sawp.service.IAdjustmentStorageFormService;
import com.taiji.pubsec.ajqlc.sawp.service.IArticleLockerService;
import com.taiji.pubsec.ajqlc.sawp.service.IBackStorageFormService;
import com.taiji.pubsec.ajqlc.sawp.service.IInStorageFormItemService;
import com.taiji.pubsec.ajqlc.sawp.service.IInStorageFormService;
import com.taiji.pubsec.ajqlc.sawp.service.IInvolvedArticleService;
import com.taiji.pubsec.ajqlc.sawp.service.IOutStorageFormService;
import com.taiji.pubsec.ajqlc.sawp.service.IStorageAreaService;
import com.taiji.pubsec.ajqlc.sawp.service.IStorageService;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryItem;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;

/**
 * 涉案物品app管理Action
 * 
 * @author chengkai
 */
@Controller("inOutStorageAction")
@Scope("prototype")
public class InOutStorageAction extends PageCommonAction {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(InOutStorageAction.class);
	
	@Resource
	private IInvolvedArticleService involvedArticleService;	//涉案物品接口
	@Resource
	private IBackStorageFormService backStorageFormService;	//返还单接口
	@Resource
	private IInStorageFormService inStorageFormService;	//入库单接口
	@Resource
	private IInStorageFormItemService inStorageFormItemService;// 入库单项接口
	@Resource
	private IOutStorageFormService outStorageFormService;	//出库单接口
	@Resource
	private IAdjustmentStorageFormService adjustmentStorageFormService;	//调整单接口
	@Resource
	private IArticleLockerService articleLockerService;	//储物柜接口
	@Resource
	private IOperationRecordService operationRecordService;//操作记录接口
	@Resource
	private IStorageAreaService storageAreaService;	//保管区接口
	@Resource
	private IDictionaryItemService dictionaryItemService;
	
	@Resource
	private IStorageService storageService;// 保管位置接口
	
	private String formCode;//  单子编号 
	
	private List<InStorageItemBean> inStorageItemBeanList = new ArrayList<InStorageItemBean>();// 入库单项Bean集合
	
	private List<OutStorageItemBean> outStorageItemBeanList = new ArrayList<OutStorageItemBean>();// 出库单项Bean集合
	
	private List<BackStorageItemBean> backStorageItemBeanList = new ArrayList<BackStorageItemBean>();// 返还单项Bean集合
	
	private List<AdjustmentStorageItemBean> adjustmentStorageItemBeanList = new ArrayList<AdjustmentStorageItemBean>();// 调整单项Bean集合
	
	private String articleCode;	//涉案物品编号
	private String articleLockerCode;	//储物柜编码
	private InvovledArticleBean invovledArticleBean ;	//涉案物品bean
	private FormBean formBean = new FormBean();	//单子bean	
	private AjustmentFormBean ajustmentFormBean = new AjustmentFormBean();	//调整单bean
	
	private List<FormItemBean> formItemBeans = new ArrayList<FormItemBean>();	//单项bean的list
	private List<FormItemBean> items = new ArrayList<>();
	private List<AjustmentFormItemBean> ajustmentFormItemBeans = new ArrayList<AjustmentFormItemBean>();	//调整单项bean的list
	private String flag;
	private Map<String, Object> map = new HashMap<String, Object>();	//	储物柜信息
	
	/**
	 * 获取返还单详情及细项信息
	 * @param formCode    返还单编码
	 */
	public String acquireBackStorageForm(){
		BackStorageForm backStorageForm = backStorageFormService.findBackStorageFormsByFormCode(formCode);
		formBean.setCaseCode(backStorageForm.getCaseCode());
		formBean.setCaseName(backStorageForm.getCaseName());
		formBean.setFormCode(backStorageForm.getCode());
		formBean.setOperateTime(backStorageForm.getCreatedTime().getTime());
		formBean.setOutStorageFormCode(backStorageForm.getOutStorageFormCode());
		OutStorageForm outStorageForm = outStorageFormService.findByCode(backStorageForm.getOutStorageFormCode());
		if (null != outStorageForm) {
			DictionaryItem di = dictionaryItemService.findById(outStorageForm.getType());
			if (null != di) {
				formBean.setType(di.getName());
			}
		}
		
		List<FormItemBean> items = new ArrayList<FormItemBean>();
		List<BackStorageFormItemServiceBean> bsfisbList = backStorageFormService.findBackStorageFormItemServiceBeanByOutFormId(outStorageForm.getId());
		for(BackStorageFormItemServiceBean bsfisb : bsfisbList){
			FormItemBean formItemBean = new FormItemBean();
			
			formItemBean.setAreaName(bsfisb.getAreaName());
			formItemBean.setArticleCode(bsfisb.getInvolvedArticleCode());
			formItemBean.setArticleName(bsfisb.getInvolvedArticleName());
			formItemBean.setFeature(bsfisb.getInvolvedArticleFeature());
			formItemBean.setItemId(bsfisb.getBackItemId());
			formItemBean.setMeasureUnit(bsfisb.getMeasurementUnit());
			formItemBean.setUpdatedTime(bsfisb.getMaintainTime());
			formItemBean.setRequestNumber(bsfisb.getOutcomingNumber());
			formItemBean.setNumberReturned(bsfisb.getReturnedNumber());
			StorageArea sa = storageAreaService.findById(bsfisb.getAreaId());
			if(sa != null){
				formItemBean.setAreaCode(sa.getCode());
				formItemBean.setAreaName(sa.getName());
			}
			List<BackStorageRecordBean> backStorageRecordBeans = new ArrayList<>();
			for (StorageServiceBean ssb : bsfisb.getStorageServiceBeans()) {
				BackStorageRecordBean backStorageRecordBean = new BackStorageRecordBean();
				backStorageRecordBean.setBackStorageFormItemId(ssb.getBackFormItemId());
				backStorageRecordBean.setId(ssb.getId());
				backStorageRecordBean.setReturnedNumber(ssb.getReturnNumber());
				backStorageRecordBean.setLockerCode(ssb.getLockerCode());
				backStorageRecordBean.setLockerName(ssb.getLockerLocation());
				backStorageRecordBean.setLockerStatus(dictionaryToString(ssb.getLockerStatus()));//将状态转换成编码
				backStorageRecordBeans.add(backStorageRecordBean);
			}
			formItemBean.setBackStorageRecordBeans(backStorageRecordBeans);
			items.add(formItemBean);
		}
		formBean.setItems(items);
		
		return SUCCESS;
	}
	
	/**
	 * 获取入库单详情及细项信息
	 * @param formCode    入库单编码
	 */
	public String acquireInStorageForm(){
		InStorageForm inStorageForm = inStorageFormService.findInStorageFormByFormCode(formCode);
		formBean.setCaseCode(inStorageForm.getCaseCode());
		formBean.setCaseName(inStorageForm.getCaseName());
		formBean.setFormCode(inStorageForm.getCode());
		formBean.setOperateTime(inStorageForm.getUpdatedTime().getTime());
		formBean.setPapers(inStorageForm.getPapers());
		formBean.setSuspectName(inStorageForm.getSuspectName());
		List<FormItemBean> formItemBeans = new ArrayList<FormItemBean>();
		for(InStorageFormItem inStorageFormItem: inStorageForm.getInStorageFormItems()){
			FormItemBean formItemBean = new FormItemBean();
			String areaCode = "";
			String areaName = "";
			formItemBean.setAreaName(areaName);
			formItemBean.setArticleCode(inStorageFormItem.getArticle().getCode());
			formItemBean.setArticleName(inStorageFormItem.getArticle().getName());
			formItemBean.setOperationNumber(inStorageFormItem.getNumberIncome());
			formItemBean.setRequestNumber(inStorageFormItem.getNumberRequested());
			formItemBean.setFeature(inStorageFormItem.getArticle().getFeature());
			formItemBean.setItemId(inStorageFormItem.getId());
			formItemBean.setNumDesc(inStorageFormItem.getArticle().getNumberInStorage());
			formItemBean.setMeasureUnit(inStorageFormItem.getArticle().getMeasurementUnit());
			formItemBean.setUpdatedTime(inStorageFormItem.getUpdatedTime().getTime());
			for(InStorageRecord isr : inStorageFormItem.getInStorageRecords()) {
				InStorageRecordBean isrb = new InStorageRecordBean();
				isrb.setId(isr.getId());
				isrb.setIncomingNumber(isr.getIncomingNumber());
				ArticleLocker l = isr.getLocker();
				if(null != l) {
					areaCode = l.getArea().getCode();
					areaName = l.getArea().getName();	
					isrb.setLockerCode(l.getCode());
					isrb.setLockerName(l.getLocation());
					isrb.setLockerStatus(dictionaryToString(l.getStatus()));//转换成编码
				}
				formItemBean.getInStorageRecordBeans().add(isrb);
			}
			formItemBean.setAreaName(areaName);
			formItemBean.setAreaCode(areaCode);
			formItemBeans.add(formItemBean);
		}
		formBean.setItems(formItemBeans);
		return SUCCESS;
	}
	
	/**
	 * 获取出库单详情及细项信息
	 * 
	 * @param formCode    出库单编码
	 */
	public String acquireOutStorageForm(){
		OutStorageForm outStorageForm = outStorageFormService.findByCode(formCode);
		formBean.setFormCode(outStorageForm.getCode());
		formBean.setCaseCode(outStorageForm.getCaseCode());
		formBean.setCaseName(outStorageForm.getCaseName());
		formBean.setOperateTime(outStorageForm.getOutStorageTime().getTime());
		formBean.setPapers(outStorageForm.getPapers());
		DictionaryItem di = dictionaryItemService.findById(outStorageForm.getType());
		if (null != di) {
			formBean.setType(di.getName());
		}
		
		//循环出库项
		List<OutStorageItemBean> osibList = new ArrayList<OutStorageItemBean>();
		for(OutStorageFormItem osfi : outStorageForm.getOutStorageFormItems()){
			OutStorageItemBean osib = new OutStorageItemBean();
			osib.setItemId(osfi.getId());
			osib.setArticleName(osfi.getArticle().getName());
			osib.setArticleCode(osfi.getArticle().getCode());
			osib.setFeature(osfi.getArticle().getFeature());
			osib.setNumberInStorage(osfi.getExistingNumber());
			osib.setMeasureUnit(osfi.getArticle().getMeasurementUnit());
			osib.setOutcomingNumber(osfi.getOutcomingNumber());
			osib.setLockerCode(osfi.getLocker().getCode());
			osib.setLockerName(osfi.getLocker().getLocation());
		    osib.setLockerStatus(dictionaryToString(osfi.getLocker().getStatus()));//抓换成编码
			osib.setAreaCode(osfi.getLocker().getArea().getCode());
			osib.setAreaName(osfi.getLocker().getArea().getName());
			osibList.add(osib);
		}
		
		formBean.setOutItems(osibList);
		return SUCCESS;
	}
	
	/**
	 * 获取涉案物品在库详细信息
	 * @param articleCode	涉案物品编码
	 */
	public String acquireInvolvedArticle(){
		InvolvedArticle involvedArticle = involvedArticleService.findInvolvedArticleByCode(articleCode);
		List<StorageBean> storageBeans = new ArrayList<StorageBean>();
		if(involvedArticle != null){
			invovledArticleBean = new InvovledArticleBean();
			invovledArticleBean.setCaseCode(involvedArticle.getCaseCode());
			invovledArticleBean.setCaseName(involvedArticle.getCaseName());
			invovledArticleBean.setSuspectName(involvedArticle.getSuspectName());
			invovledArticleBean.setPapers(involvedArticle.getPaper());
			invovledArticleBean.setArticleCode(involvedArticle.getCode());
			invovledArticleBean.setArticleName(involvedArticle.getName());
			invovledArticleBean.setArticleFeature(involvedArticle.getFeature());
			
			for(int i = 0; i < involvedArticle.getStorages().size(); i++){
				if(involvedArticle.getStorages().get(i).getExistingNumber() < 1){
					continue;
				}
				StorageBean storageBean = new StorageBean();
				storageBean.setAreaName(involvedArticle.getStorages().get(i).getLocker().getArea().getName());
				storageBean.setAreaCode(involvedArticle.getStorages().get(i).getLocker().getArea().getCode());
				storageBean.setLockerCode(involvedArticle.getStorages().get(i).getLocker().getCode());
				storageBean.setLockerName(involvedArticle.getStorages().get(i).getLocker().getLocation());
				storageBean.setLockerStatus(dictionaryToString(involvedArticle.getStorages().get(i).getLocker().getStatus()));//转换成编码
				storageBean.setInTime(involvedArticle.getInStorageTime().getTime());
				storageBean.setNumDesc(involvedArticle.getStorages().get(i).getExistingNumber());
				storageBean.setMeasureUnit(involvedArticle.getMeasurementUnit());
				storageBeans.add(storageBean);
			}
			invovledArticleBean.setLocations(storageBeans);
		}
		return SUCCESS;
	}
	
	/**
	 * 调整操作
	 * 
	 * @param adjustmentStorageItemBeanList	调整单项,List<AdjustmentStorageItemBean>
	 * @param formCode	调整单编号
	 */
	public String putAjustmentStorage(){
		//循环转换调整单项
		List<AdjustmentStorageFormItemServiceBean> asfisbList = new ArrayList<AdjustmentStorageFormItemServiceBean>();
		for(AdjustmentStorageItemBean asib : adjustmentStorageItemBeanList){
			//判断当前循环项是否存在，存在更新项里的位置集合，不存在添加项
			boolean flag = false;
			for(int i=0;i<asfisbList.size();i++){
				if(asib.getItemId().equals(asfisbList.get(i).getId())){//存在，更新项里的位置集合
					StorageServiceBean ssb = new StorageServiceBean();
					ssb.setId(asib.getStorageId());
					ssb.setAdjustmentStorageRecordId(asib.getStorageId());
					ssb.setAdjustNumber(asib.getAdjustNumber());
					ArticleLocker al = articleLockerService.findByCode(asib.getLockerCode());//查询储物柜id
					if(al != null){
						ssb.setLockerId(al.getId());
					}
					asfisbList.get(i).getStorageServiceBeans().add(ssb);
					flag = true;
				}
			}
			//不存在，添加项
			if(!flag){
				AdjustmentStorageFormItemServiceBean asfisb = new AdjustmentStorageFormItemServiceBean();
				
				//通过调整单项id查询调整单项
				AdjustmentStorageFormItem asfi = adjustmentStorageFormService.findItemById(asib.getItemId());
				asfisb.setId(asib.getItemId());
				
				if(asfi != null){
					//存储页面可修改字段
					asfisb.setRemark(asfi.getRemark());
					asfisb.setArticleCode(asfi.getArticle().getCode());
					asfisb.setArticleId(asfi.getArticle().getId());
					asfisb.setStorageId(asfi.getStorageId());
					
					//存储柜子信息
					StorageServiceBean ssb = new StorageServiceBean();
					ssb.setId(asib.getStorageId());
					ssb.setAdjustmentStorageRecordId(asib.getStorageId());
					ssb.setAdjustNumber(asib.getAdjustNumber());
					ArticleLocker al = articleLockerService.findByCode(asib.getLockerCode());//查询储物柜id
					if(al != null){
						ssb.setLockerId(al.getId());						
					}
					asfisb.getStorageServiceBeans().add(ssb);
					asfisbList.add(asfisb);
				}
			}
		}
		
		adjustmentStorageFormService.updateForm(formCode, asfisbList);
		flag = "success";
		
		try {
			//创建操作记录
			for(AdjustmentStorageFormItemServiceBean asfisb : asfisbList){
				OperationRecord or = new OperationRecord();
				//涉案物品id查询code
				InvolvedArticle ia = involvedArticleService.findInvolvedArticleById(asfisb.getArticleId());
				if(ia != null){
					or.setArticleCode(ia.getCode());
				}
				AdjustgmentStorageForm  asf = adjustmentStorageFormService.findByCode(formCode);
				if(asf != null){
					or.setFormCode(asf.getCode());
					or.setFormId(asf.getId());
				}
				or.setFormType(AdjustgmentStorageForm.class.getName());
				
				//根据旧存储柜子id查询区域和柜子信息
				Storage storage = storageService.findStorageByid(asfisb.getStorageId());
				if(storage != null){
					or.setNumber(storage.getLocker().getArea().getName() + "，" + storage.getLocker().getLocation());
				}
				or.setOperation(OperationRecordConstant.WP_CZJL_TZ);
				or.setOperationTime(new Date());
				or.setOperator(this.findCurrentPerson().getName());
				operationRecordService.saveOperationRecord(or);
			}
		} catch (Exception e) {
			logger.debug("存储调整操作记录异常",e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 返还操作
	 * 
	 * @param backStorageItemBeanList	返还单项，List<BackStorageItemBean>
	 * @param formCode	返还单编号
	 */
	public String putBackStorage(){
		//根据返还单编号查询返还单
		BackStorageForm backStorageForm = backStorageFormService.findBackStorageFormsByFormCode(formCode);
		
		BackStorageFormServiceBean bsfsb = new BackStorageFormServiceBean();
		bsfsb.setCode(formCode);
		bsfsb.setId(backStorageForm.getId());
		bsfsb.setRemark(backStorageForm.getRemark());
		
		//循环转换返还单项
		List<BackStorageFormItemServiceBean> bsfisbList = new ArrayList<BackStorageFormItemServiceBean>();
		for(BackStorageItemBean bsib : backStorageItemBeanList){
			//判断当前循环项是否存在，存在更新项里的位置集合，不存在添加项
			boolean flag = false;
			for(int i=0;i<bsfisbList.size();i++){
				if(bsib.getItemId().equals(bsfisbList.get(i).getBackItemId())){//存在，更新项里的位置集合
					StorageServiceBean ssb = new StorageServiceBean();
					ssb.setId(bsib.getStorageId());
					ssb.setBackFormRecordId(bsib.getStorageId());
					ssb.setReturnNumber(bsib.getReturnNumber());
					ArticleLocker al = articleLockerService.findByCode(bsib.getLockerCode());//查询储物柜id
					if(al != null){
						ssb.setLockerId(al.getId());
					}
					bsfisbList.get(i).getStorageServiceBeans().add(ssb);
					flag = true;
				}
			}
			//不存在，添加项
			if(!flag){
				BackStorageFormItemServiceBean bsfisb = new BackStorageFormItemServiceBean();
				
				BackStorageFormItem bsfi = backStorageFormService.findItemById(bsib.getItemId());
				bsfisb.setBackItemId(bsib.getItemId());
				if(bsfi != null){
					//存储页面可修改字段
					bsfisb.setRemark(bsfi.getRemark());
					bsfisb.setInvolvedArticleCode(bsfi.getOutItem().getArticle().getCode());
					bsfisb.setInvolvedArticleId(bsfi.getOutItem().getArticle().getId());
					bsfisb.setOutItemId(bsfi.getOutItem().getId());
					
					//存储柜子信息
					StorageServiceBean ssb = new StorageServiceBean();
					ssb.setId(bsib.getStorageId());
					ssb.setBackFormRecordId(bsib.getStorageId());
					ssb.setReturnNumber(bsib.getReturnNumber());
					ArticleLocker al = articleLockerService.findByCode(bsib.getLockerCode());//查询储物柜id
					if(al != null){
						ssb.setLockerId(al.getId());
					}
					bsfisb.getStorageServiceBeans().add(ssb);
					bsfisbList.add(bsfisb);
				}
			}
		}
		
		backStorageFormService.updateForm(bsfsb, bsfisbList);
		flag = "success";
		
		try {
			//创建操作记录
			for(BackStorageFormItemServiceBean bsfisb : bsfisbList){
				OperationRecord or = new OperationRecord();
				or.setArticleCode(bsfisb.getInvolvedArticleCode());
				or.setFormCode(backStorageForm.getCode());
				or.setFormId(backStorageForm.getId());
				or.setFormType(BackStorageForm.class.getName());
				
				List<StorageServiceBean> ssbList = bsfisb.getStorageServiceBeans();
				if(ssbList != null && !ssbList.isEmpty()){
					int returnNumberCount = 0;
					for(StorageServiceBean ssb : ssbList){
						returnNumberCount += ssb.getReturnNumber();
					}
					or.setNumber(String.valueOf(returnNumberCount));
				}
				or.setOperation(OperationRecordConstant.WP_CZJL_FH);
				or.setOperationTime(new Date());
				or.setOperator(this.findCurrentPerson().getName());
				operationRecordService.saveOperationRecord(or);
			}
		} catch (Exception e) {
			logger.debug("存储返还操作记录异常",e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 入库操作
	 * 
	 * @param inStorageItemBeanList 入库单项，List<InStorageItemBean>
	 * @param formCode    入库单编号
	 */
	public String putInStorage(){
		//根据入库单编号查询入库单
		InStorageForm inStorageForm = inStorageFormService.findInStorageFormByFormCode(formCode);
		
		InStorageFormServiceBean isfsb = new InStorageFormServiceBean();
		isfsb.setCode(formCode);
		isfsb.setRemark(inStorageForm.getRemark());
		
		//循环转换入库单项
		List<InStorageFormItemServiceBean> isfisbList = new ArrayList<InStorageFormItemServiceBean>();
		for(InStorageItemBean isib : inStorageItemBeanList){
			//判断当前循环项是否存在，存在更新项里的位置集合，不存在添加项
			boolean flag = false;
			for(int i=0;i<isfisbList.size();i++){
				if(isib.getItemId().equals(isfisbList.get(i).getId())){//存在，更新项里的位置集合
					StorageServiceBean ssb = new StorageServiceBean();
					ssb.setId(isib.getStorageId());
					ssb.setIncomingNumber(isib.getIncomingNumber());
					ArticleLocker al = articleLockerService.findByCode(isib.getLockerCode());//查询储物柜id
					if(al != null){
						ssb.setLockerId(al.getId());
					}
					isfisbList.get(i).getStorageServiceBeans().add(ssb);
					flag = true;
				}
			}
			//不存在，添加项
			if(!flag){
				InStorageFormItemServiceBean isfisb = new InStorageFormItemServiceBean();
				
				InStorageFormItem isfi = inStorageFormItemService.findItemById(isib.getItemId());
				isfisb.setId(isib.getItemId());
				if(isfi != null){
					//存储页面可修改字段
					isfisb.setArticleType(isfi.getArticle().getType());
					isfisb.setNumberRequested(isfi.getNumberRequested());
					isfisb.setMeasurementUnit(isfi.getArticle().getMeasurementUnit());
					isfisb.setRemark(isfi.getRemark());
					isfisb.setArticleCode(isfi.getArticle().getCode());
					isfisb.setArticleId(isfi.getArticle().getId());
					
					//存储柜子信息
					StorageServiceBean ssb = new StorageServiceBean();
					ssb.setId(isib.getStorageId());
					ssb.setIncomingNumber(isib.getIncomingNumber());
					ArticleLocker al = articleLockerService.findByCode(isib.getLockerCode());//查询储物柜id
					if(al != null){
						ssb.setLockerId(al.getId());
					}
					isfisb.getStorageServiceBeans().add(ssb);
					isfisbList.add(isfisb);
				}
			}
		}
		try {
			inStorageFormService.updateForm(isfsb, isfisbList);
		} catch (ParseException e) {
			e.printStackTrace();
			flag = "error";
		}
		flag = "success";
		
		try {
			// 创建操作记录
			for (InStorageFormItemServiceBean isfisb : isfisbList) {
				OperationRecord or = new OperationRecord();
				or.setArticleCode(isfisb.getArticleCode());
				or.setFormCode(inStorageForm.getCode());
				or.setFormId(inStorageForm.getId());
				or.setFormType(InStorageForm.class.getName());
				List<StorageServiceBean> ssbList = isfisb.getStorageServiceBeans();
				if (ssbList != null && !ssbList.isEmpty()) {
					int incomingNumberCount = 0;
					for (StorageServiceBean ssb : ssbList) {
						incomingNumberCount += ssb.getIncomingNumber();
					}
					or.setNumber(String.valueOf(incomingNumberCount));
				}
				or.setOperation(OperationRecordConstant.WP_CZJL_RK);
				or.setOperationTime(new Date());
				or.setOperator(this.findCurrentPerson().getName());
				operationRecordService.saveOperationRecord(or);
			}
		} catch (Exception e) {
			logger.debug("存储入库操作记录异常",e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 出库操作
	 * 
	 * @param outStorageItemBeanList    出库单项，List<OutStorageItemBean>
	 * @param formCode    出库单编号
	 */
	public String putOutStorage(){
		//循环转换出库单项
		List<OutStorageFormItemServiceBean> osfisbList = new ArrayList<OutStorageFormItemServiceBean>();
		for(OutStorageItemBean osib : outStorageItemBeanList){
			
			OutStorageFormItem osfi = outStorageFormService.findItemById(osib.getItemId());
			if(osfi != null){
				OutStorageFormItemServiceBean osfisb = new OutStorageFormItemServiceBean();
				//存储页面可修改字段
				osfisb.setId(osib.getItemId());
				osfisb.setInvolvedArticleCode(osfi.getArticle().getCode());
				osfisb.setInvolvedArticleId(osfi.getArticle().getId());
				osfisb.setNumberInStorage(osfi.getExistingNumber());
				osfisb.setOutcomingNumber(osib.getOutcomingNumber());
				ArticleLocker al = articleLockerService.findByCode(osib.getLockerCode());//查询储物柜id
				if(al != null){
					osfisb.setLockerId(al.getId());
					osfisb.setLockerCode(al.getCode());
				}
				osfisbList.add(osfisb);
			}
		}
		outStorageFormService.updateForm(formCode, osfisbList);
		flag = "success";
		
		try {
			//创建操作记录
			for(OutStorageFormItemServiceBean osfisb : osfisbList){
				if(osfisb.getOutcomingNumber() < 1){
					continue;
				}
				OperationRecord or = new OperationRecord();
				or.setArticleCode(osfisb.getInvolvedArticleCode());
				OutStorageForm osf = outStorageFormService.findByCode(formCode);
				if(osf != null){
					or.setFormCode(osf.getCode());
					or.setFormId(osf.getId());
				}
				or.setFormType(OutStorageForm.class.getName());
				or.setNumber(String.valueOf(osfisb.getOutcomingNumber()));
				or.setOperation(OperationRecordConstant.WP_CZJL_CK);
				or.setOperationTime(new Date());
				or.setOperator(this.findCurrentPerson().getName());
				operationRecordService.saveOperationRecord(or);
			}
		} catch (Exception e) {
			logger.debug("存储出库操作记录异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 获取调整单详情及细项信息
	 * @param formCode    调整单号
	 */
	public String acquireAdjustmentForm(){
		AdjustgmentStorageForm adjustgmentStorageForm = adjustmentStorageFormService.findByCode(formCode);
		ajustmentFormBean.setFormCode(adjustgmentStorageForm.getCode());
		ajustmentFormBean.setOperateTime(adjustgmentStorageForm.getUpdatedTime().getTime());
		ajustmentFormBean.setReason(adjustgmentStorageForm.getReason());
		
		Set<AdjustmentStorageFormItem> asibSet =  adjustgmentStorageForm.getAdjustmentStorageFormItems();
		List<AjustmentFormItemBean> ajustmentFormItemBeans = new ArrayList<AjustmentFormItemBean>();
		
		List<String> caseCodeList = new ArrayList<String>();
		for(AdjustmentStorageFormItem asfi : asibSet){
			AjustmentFormItemBean ajustmentFormItemBean = new AjustmentFormItemBean();
			ajustmentFormItemBean.setArticleCode(asfi.getArticle().getCode());
			ajustmentFormItemBean.setArticleName(asfi.getArticle().getName());
			ajustmentFormItemBean.setCaseCode(asfi.getArticle().getCaseCode());
			
			if(!caseCodeList.contains(asfi.getArticle().getCaseCode())){
				caseCodeList.add(asfi.getArticle().getCaseCode());
			}
			
			ajustmentFormItemBean.setFeature(asfi.getArticle().getFeature());
			ajustmentFormItemBean.setItemId(asfi.getId());
			ajustmentFormItemBean.setMeasureUnit(asfi.getArticle().getMeasurementUnit());
			ajustmentFormItemBean.setUpdatedTime(adjustgmentStorageForm.getUpdatedTime().getTime());
			ajustmentFormItemBean.setNumDesc(asfi.getAdjustmentNumber());
			
			Storage storage = storageService.findStorageByid(asfi.getStorageId());
			if(storage != null){
				ajustmentFormItemBean.setLockerCode(storage.getLocker().getCode());
				ajustmentFormItemBean.setLockerName(storage.getLocker().getLocation());
				ajustmentFormItemBean.setLockerStatus(dictionaryToString(storage.getLocker().getStatus()));//获取储物箱状态编码
				ajustmentFormItemBean.setAreaName(storage.getLocker().getArea().getName());
				ajustmentFormItemBean.setAreaCode(storage.getLocker().getArea().getCode());
			}
			
			List<AdjustmentStorageRecordBean> adjustmentStorageRecordBeans = new ArrayList<>();
			for (AdjustmentStorageRecord adjustmentStorageRecord : asfi.getAdjustmentStorageRecords()) {
				AdjustmentStorageRecordBean adjustmentStorageRecordBean = new AdjustmentStorageRecordBean();
				adjustmentStorageRecordBean.setAdjustNumber(adjustmentStorageRecord.getAdjustNumber());
				adjustmentStorageRecordBean.setId(adjustmentStorageRecord.getId());
				ArticleLocker al = adjustmentStorageRecord.getLocker();
				if (null != al) {
					adjustmentStorageRecordBean.setLockerCode(al.getCode());
					adjustmentStorageRecordBean.setLockerName(al.getLocation());
					adjustmentStorageRecordBean.setLockerStatus(al.getStatus());
				}
				adjustmentStorageRecordBeans.add(adjustmentStorageRecordBean);
				
			}
			ajustmentFormItemBean.setAdjustmentStorageRecordBeans(adjustmentStorageRecordBeans);
			ajustmentFormItemBeans.add(ajustmentFormItemBean);
		}
		ajustmentFormBean.setItems(ajustmentFormItemBeans);
		
		Map<ArticleLocker, String> lockerMap = articleLockerService.findUsedLockersByCaseCodes(caseCodeList);
		for (ArticleLocker al : lockerMap.keySet()) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("caseCode", lockerMap.get(al));
			map.put("lockerCode", al.getCode());
			map.put("lockerName", al.getLocation());
			map.put("lockerStatus", dictionaryToString(al.getStatus()));
			ajustmentFormBean.getLockers().add(map);
		}
		return SUCCESS;
	}
	
	/**
	 * 获取储物柜信息
	 * @param articleLockerCode 储物柜编码
	 */
	public String acquireArticleLockerInfoByCode() {
		ArticleLocker al = articleLockerService.findByCode(articleLockerCode) ;
		if(null != al) {
			map.put("id", al.getId());
			map.put("code", al.getCode());
			map.put("location", al.getLocation());
			map.put("remark", al.getRemark());
			map.put("areaCode", al.getArea().getCode());
			map.put("areaName", al.getArea().getName());
			map.put("status", dictionaryToString(al.getStatus()));
		}
		map.put("caseCode", articleLockerService.findCaseCodeByLockerCode(articleLockerCode));
		return SUCCESS;
	}
	
	/**
	 * 获取字典项的编码
	 * @param str
	 * @return
	 */
	private String dictionaryToString(String str){
		DictionaryItem di = dictionaryItemService.findById(str);
		if (null != di) {
			return di.getCode();
		}
		return null;
	}
	
	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public InvovledArticleBean getInvovledArticleBean() {
		return invovledArticleBean;
	}

	public void setInvovledArticleBean(InvovledArticleBean invovledArticleBean) {
		this.invovledArticleBean = invovledArticleBean;
	}

	public FormBean getFormBean() {
		return formBean;
	}

	public void setFormBean(FormBean formBean) {
		this.formBean = formBean;
	}

	public AjustmentFormBean getAjustmentFormBean() {
		return ajustmentFormBean;
	}

	public void setAjustmentFormBean(AjustmentFormBean ajustmentFormBean) {
		this.ajustmentFormBean = ajustmentFormBean;
	}

	public String getFormCode() {
		return formCode;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}

	public List<FormItemBean> getFormItemBeans() {
		return formItemBeans;
	}

	public void setFormItemBeans(List<FormItemBean> formItemBeans) {
		this.formItemBeans = formItemBeans;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public List<AjustmentFormItemBean> getAjustmentFormItemBeans() {
		return ajustmentFormItemBeans;
	}

	public void setAjustmentFormItemBeans(List<AjustmentFormItemBean> ajustmentFormItemBeans) {
		this.ajustmentFormItemBeans = ajustmentFormItemBeans;
	}

	public String getArticleLockerCode() {
		return articleLockerCode;
	}

	public void setArticleLockerCode(String articleLockerCode) {
		this.articleLockerCode = articleLockerCode;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public List<FormItemBean> getItems() {
		return items;
	}

	public void setItems(List<FormItemBean> items) {
		this.items = items;
	}

	public List<InStorageItemBean> getInStorageItemBeanList() {
		return inStorageItemBeanList;
	}

	public void setInStorageItemBeanList(List<InStorageItemBean> inStorageItemBeanList) {
		this.inStorageItemBeanList = inStorageItemBeanList;
	}

	public List<BackStorageItemBean> getBackStorageItemBeanList() {
		return backStorageItemBeanList;
	}

	public void setBackStorageItemBeanList(List<BackStorageItemBean> backStorageItemBeanList) {
		this.backStorageItemBeanList = backStorageItemBeanList;
	}

	public List<AdjustmentStorageItemBean> getAdjustmentStorageItemBeanList() {
		return adjustmentStorageItemBeanList;
	}

	public void setAdjustmentStorageItemBeanList(List<AdjustmentStorageItemBean> adjustmentStorageItemBeanList) {
		this.adjustmentStorageItemBeanList = adjustmentStorageItemBeanList;
	}

	public List<OutStorageItemBean> getOutStorageItemBeanList() {
		return outStorageItemBeanList;
	}

	public void setOutStorageItemBeanList(List<OutStorageItemBean> outStorageItemBeanList) {
		this.outStorageItemBeanList = outStorageItemBeanList;
	}

	
}