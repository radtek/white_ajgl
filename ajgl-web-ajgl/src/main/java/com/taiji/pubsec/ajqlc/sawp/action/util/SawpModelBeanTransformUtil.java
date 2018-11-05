package com.taiji.pubsec.ajqlc.sawp.action.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.taiji.pubsec.ajqlc.operationrecord.model.OperationRecord;
import com.taiji.pubsec.ajqlc.sawp.action.bean.ArticleLockerBean;
import com.taiji.pubsec.ajqlc.sawp.action.bean.InvolvedArticleBean;
import com.taiji.pubsec.ajqlc.sawp.action.bean.OperationRecordBean;
import com.taiji.pubsec.ajqlc.sawp.action.bean.StorageAreaBean;
import com.taiji.pubsec.ajqlc.sawp.action.bean.StorageBean;
import com.taiji.pubsec.ajqlc.sawp.bean.AdjustmentStorageFormItemServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.AdjustmentStorageFormServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.BackStorageFormItemServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.BackStorageFormServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.CriminalBasicCaseBean;
import com.taiji.pubsec.ajqlc.sawp.bean.OutStorageFormItemServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.StorageServiceBean;
import com.taiji.pubsec.ajqlc.sawp.model.AdjustgmentStorageForm;
import com.taiji.pubsec.ajqlc.sawp.model.AdjustmentStorageFormItem;
import com.taiji.pubsec.ajqlc.sawp.model.AdjustmentStorageRecord;
import com.taiji.pubsec.ajqlc.sawp.model.ArticleLocker;
import com.taiji.pubsec.ajqlc.sawp.model.BackStorageForm;
import com.taiji.pubsec.ajqlc.sawp.model.BackStorageFormItem;
import com.taiji.pubsec.ajqlc.sawp.model.ImpoundedObject;
import com.taiji.pubsec.ajqlc.sawp.model.InStorageFormItem;
import com.taiji.pubsec.ajqlc.sawp.model.InStorageRecord;
import com.taiji.pubsec.ajqlc.sawp.model.InvolvedArticle;
import com.taiji.pubsec.ajqlc.sawp.model.OutStorageForm;
import com.taiji.pubsec.ajqlc.sawp.model.OutStorageFormItem;
import com.taiji.pubsec.ajqlc.sawp.model.Storage;
import com.taiji.pubsec.ajqlc.sawp.model.StorageArea;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageArea;
import com.taiji.pubsec.ajqlc.sawp.service.IArticleLockerService;
import com.taiji.pubsec.ajqlc.sawp.service.IBackStorageFormService;
import com.taiji.pubsec.ajqlc.sawp.service.IImpoundedObjectService;
import com.taiji.pubsec.ajqlc.sawp.service.IInStorageFormService;
import com.taiji.pubsec.ajqlc.sawp.service.IInvolvedArticleService;
import com.taiji.pubsec.ajqlc.sawp.service.IOutStorageFormService;
import com.taiji.pubsec.ajqlc.sawp.service.IStorageService;
import com.taiji.pubsec.ajqlc.util.DateFmtUtil;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryItem;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.businesscomponent.organization.model.Organization;
import com.taiji.pubsec.businesscomponent.organization.model.Person;
import com.taiji.pubsec.businesscomponent.organization.model.Unit;
import com.taiji.pubsec.businesscomponent.organization.service.IPersonService;
import com.taiji.pubsec.businesscomponent.organization.service.IUnitService;
import com.taiji.pubsec.common.tool.spring.BeanCopierFmtUtil;

/**
 * 涉案物品模块Model和Bean互转工具类
 * @author WangLei
 *
 */
@Component
public class SawpModelBeanTransformUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(SawpModelBeanTransformUtil.class);
	
	@Resource
	private IUnitService unitService;// 单位接口
	
	@Resource
	private IPersonService personService;// 人员接口
	
	@Resource
	private IDictionaryItemService dictionaryItemService;// 字典项接口
	
	@Resource
	private IArticleLockerService articleLockerService;// 储物箱服务接口
	
	@Resource
	private IOutStorageFormService outStorageFormService;// 出库单服务接口
	
	@Resource
	private IInStorageFormService inStorageFormService;// 入库单接口
	
	@Resource
	private IBackStorageFormService backStorageFormService;// 返还单接口
	
	@Resource
	private IImpoundedObjectService impoundedObjectService;// 扣押物品查询接口
	
	@Resource
	private IInvolvedArticleService involvedArticleService;// 涉案物品接口
	
	@Resource
	private IStorageService storageService;// 保管位置接口
	
	
	/**
	 * 物证管理区Bean转Model
	 * 
	 * @param sab 物证管理区Bean
	 * @return sa 物证管理区Model
	 */
	public StorageArea storageAreaBeanToStorageArea(StorageAreaBean sab){
		if(sab == null){
			return null;
		}
		StorageArea sa = new StorageArea();
		sa.setId(sab.getId());
		sa.setCode(sab.getCode());
		sa.setName(sab.getName());
		sa.setAddress(sab.getAddress());
		sa.setRemark(sab.getRemark());
		sa.setState(sab.getState());
		Unit unit = unitService.findById(sab.getUnitId());
		sa.setUnit(unit);
		return sa;
	}
	
	/**
	 * 暂存物品保管区Bean转Model
	 * 
	 * @param sab 暂存物品保管区Bean
	 * @return sa 暂存物品保管区Model
	 */
	public TemporaryStorageArea storageAreaBeanToTransitStoreArea(StorageAreaBean sab){
		if(sab == null){
			return null;
		}
		TemporaryStorageArea sa = new TemporaryStorageArea();
		sa.setId(sab.getId());
		sa.setCode(sab.getCode());
		sa.setName(sab.getName());
		sa.setAddress(sab.getAddress());
		sa.setRemark(sab.getRemark());
		sa.setStatus(sab.getState());
		Organization unit = unitService.findById(sab.getUnitId());
		sa.setOrg(unit);
		return sa;
	}
	
	/**
	 * 物证管理区Model转Bean
	 * 
	 * @param sa 物证管理区Model
	 * @return sab 物证管理区Bean
	 */
	public StorageAreaBean storageAreaToStorageAreaBean(StorageArea sa){
		if(sa == null){
			return null;
		}
		StorageAreaBean sab = new StorageAreaBean();
		sab.setId(sa.getId());
		sab.setCode(sa.getCode());
		sab.setName(sa.getName());
		sab.setRemark(sa.getRemark());
		sab.setAddress(sa.getAddress());
		sab.setUnitId("");
		sab.setUnitName("");
		Unit unit = sa.getUnit();
		if(unit != null){
			sab.setUnitId(unit.getId());
			sab.setUnitName(unit.getShortName());
		}
		sab.setState(sa.getState());
		sab.setStateName(findDictionaryItemNameById(sa.getState()));
		return sab;
	}
	
	/**
	 * 暂存物品保管区Model转Bean
	 * 
	 * @param sa 暂存物品保管区Model
	 * @return sab 物证管理区Bean
	 */
	public StorageAreaBean transitStoreAreaToStorageAreaBean(TemporaryStorageArea sa){
		if(sa == null){
			return null;
		}
		StorageAreaBean sab = new StorageAreaBean();
		sab.setId(sa.getId());
		sab.setCode(sa.getCode());
		sab.setName(sa.getName());
		sab.setRemark(sa.getRemark());
		sab.setAddress(sa.getAddress());
		sab.setUnitId("");
		sab.setUnitName("");
		Organization org = sa.getOrg();
		if(org != null){
			sab.setUnitId(org.getId());
			sab.setUnitName(org.getShortName());
		}
		sab.setState(sa.getStatus());
		sab.setStateName(findDictionaryItemNameById(sa.getStatus()));
		return sab;
	}
	
	/**
	 * 涉案物品Model转Bean
	 * 
	 * @param ia 涉案物品Model
	 * @return iab 涉案物品Bean
	 */
	public InvolvedArticleBean involvedArticleToInvolvedArticleBean(InvolvedArticle ia){
		if(ia == null){
			return null;
		}
		InvolvedArticleBean iab = new InvolvedArticleBean();
		iab.setId(ia.getId());
		iab.setCaseCode(ia.getCaseCode());
		ImpoundedObject io = impoundedObjectService.findCaseNameByCaseId(ia.getCaseCode());
		iab.setCaseName("");
		if(io != null){
			iab.setCaseName(io.getCaseName());
		}
		iab.setCode(ia.getCode());
		iab.setDetentionNumber(ia.getDetentionNumber());
		iab.setFeature(ia.getFeature());
		iab.setMeasurementUnit(ia.getMeasurementUnit());
		iab.setName(ia.getName());
		iab.setNumberInStorage(ia.getNumberInStorage());
		iab.setPaper(ia.getPaper());
		iab.setPaperId(ia.getPaperId());
		iab.setPaperType(ia.getPaperType());
		iab.setPolices(ia.getPolices());
		iab.setSuspectName(ia.getSuspectName());
		iab.setType(ia.getType());
		iab.setTypeName(articleTypeIdToName(ia.getType()));
		iab.setInStorageTime(DateFmtUtil.dateToLong(ia.getInStorageTime()));
		iab.setSuspectIdentityNumber(ia.getSuspectIdentityNumber() == null?"":ia.getSuspectIdentityNumber());
		List<StorageBean> sbList = new ArrayList<StorageBean>();
		for(Storage s : ia.getStorages()){
			StorageBean sb = new StorageBean();
			sb.setId(s.getId());
			sb.setIncomingNumber(s.getIncomingNumber());
			sb.setExistingNumber(s.getExistingNumber());
			sbList.add(sb);
		}
		iab.setStorages(sbList);
		return iab;
	}
	
	/**
	 * 储物箱Model 转 Bean
	 * @param al 储物箱Model
	 * @return alb 储物箱Bean
	 */
	public ArticleLockerBean articleLockerToArticleLockerBean(ArticleLocker al){
		if(al == null){
			return null;
		}
		ArticleLockerBean alb = new ArticleLockerBean();
		alb.setCode(al.getCode());
		alb.setId(al.getId());
		alb.setLocation(al.getLocation());
		alb.setRemark(al.getRemark());
		alb.setStatus(al.getStatus());
		StorageAreaBean sab = storageAreaToStorageAreaBean(al.getArea());
		alb.setArea(sab);
		return alb;
	}
	
	/**
	 * 涉案物品存储位置Model转Bean
	 * 
	 * @param storage 涉案物品存储位置Model
	 * @return sb 涉案物品存储位置Bean
	 */
	public StorageBean storageToStorageBean(Storage storage){
		if(storage == null){
			return null;
		}
		StorageBean sb = new StorageBean();
		sb.setExistingNumber(storage.getExistingNumber());
		sb.setId(storage.getId());
		sb.setIncomingNumber(storage.getIncomingNumber());
		InvolvedArticleBean article = involvedArticleToInvolvedArticleBean(storage.getArticle());
		sb.setLocker(articleLockerToArticleLockerBean(storage.getLocker()));
		sb.setPoliceName(article.getPolices());
		BackStorageFormItem bsfi = null;
		if(sb.getLocker() != null){
			bsfi = backStorageFormService.findBackStorageFormItemByArticleIdAndLockerId(article.getId(), sb.getLocker().getId());
		}
		if(bsfi != null){
			article.setBackStorageTime(DateFmtUtil.dateToLong(bsfi.getMaintainTime()));
		}
		sb.setArticle(article);
		if(storage.getLocker() != null){
			InStorageFormItem item = inStorageFormService.findInStorageFormItemsByArticleCodeAndLockerId(storage.getArticle().getCode(), storage.getLocker().getId());
			sb.setItemId(item == null ? ""  : item.getId());
		}
		
		if(article != null && sb.getLocker() != null){
			List<ArticleLocker> alList = articleLockerService.findLockersByCaseCodeAndAreaId(article.getCaseCode(), sb.getLocker().getArea().getId());
			if(alList != null){
				sb.setArticleLockerList(alList);
			}
		}
		return sb;
	}
	
	/**
	 * 调整单Model转Bean
	 * 
	 * @param asf 调整单Model
	 * @return asfsb 调整单Bean
	 */
	/**
	 * @param asf
	 * @return
	 */
	public AdjustmentStorageFormServiceBean adjustgmentStorageFormToAdjustmentStorageFormServiceBean(AdjustgmentStorageForm asf){
		if(asf == null){
			return null;
		}
		AdjustmentStorageFormServiceBean asfsb = new AdjustmentStorageFormServiceBean();
		asfsb.setId(asf.getId());
		asfsb.setCode(asf.getCode());
		asfsb.setAdjustTime(asf.getAdjustTime() == null ? null : DateFmtUtil.dateToLong(asf.getAdjustTime()));
		asfsb.setCreatedTime(asf.getCreatedTime()==null?null:DateFmtUtil.dateToLong(asf.getCreatedTime()));
		Person person = asf.getModifyPeople();
		if(person != null){
			asfsb.setOperator(person.getName());
		}else{
			asfsb.setOperator("");
		}
		asfsb.setRemark(asf.getRemark());
		asfsb.setUpdatedTime(asf.getUpdatedTime()==null?null:DateFmtUtil.dateToLong(asf.getUpdatedTime()));
		asfsb.setReason(asf.getReason());
		List<AdjustmentStorageFormItemServiceBean> asfisbList = new ArrayList<AdjustmentStorageFormItemServiceBean>();
		Set<AdjustmentStorageFormItem> asfiMap = asf.getAdjustmentStorageFormItems();
		for(AdjustmentStorageFormItem asfi : asfiMap){
			AdjustmentStorageFormItemServiceBean asfisb = adjustmentStorageFormItemToAdjustmentStorageFormItemServiceBean(asfi);
			asfisbList.add(asfisb);
		}
		asfsb.setAsfiBeanList(asfisbList);
		return asfsb;
	}
	
	/**
	 * 调整单项Model转Bean
	 * 
	 * @param asfi 调整单项Model
	 * @return asfisb 调整单项Bean
	 */
	public AdjustmentStorageFormItemServiceBean adjustmentStorageFormItemToAdjustmentStorageFormItemServiceBean(AdjustmentStorageFormItem asfi){
		if(asfi == null){
			return null;
		}
		AdjustmentStorageFormItemServiceBean asfisb = new AdjustmentStorageFormItemServiceBean();
		asfisb.setId(asfi.getId());
		asfisb.setRemark(asfi.getRemark());
		asfisb.setAdjustmentNumber(asfi.getAdjustmentNumber());
		for(AdjustmentStorageRecord asr : asfi.getAdjustmentStorageRecords()){
			StorageServiceBean ssb = new StorageServiceBean();
			ssb.setAdjustNumber(asr.getAdjustNumber());;
			ssb.setLockerId(asr.getLocker().getId());
			ssb.setLockerCode(asr.getLocker().getCode());
			ssb.setLockerLocation(asr.getLocker().getLocation());
			ssb.setLockerRemark(asr.getLocker().getRemark());
			ssb.setAreaId(asr.getLocker().getArea().getId());
			ssb.setAreaName(asr.getLocker().getArea().getName());
			asfisb.getStorageServiceBeans().add(ssb);
		}
		InvolvedArticle ia = asfi.getArticle();
		if(ia != null){
			asfisb.setArticleId(ia.getId());
			asfisb.setArticleName(ia.getName());
			asfisb.setArticleFeature(ia.getFeature());
			asfisb.setArticleCode(ia.getCode());
			asfisb.setDetentionNumber(ia.getDetentionNumber());
			asfisb.setMeasurementUnit(ia.getMeasurementUnit());
			asfisb.setCaseCode(ia.getCaseCode());
			asfisb.setCaseName(ia.getCaseName());
			asfisb.setPolices(ia.getPolices());
			asfisb.setSuspectName(ia.getSuspectName());
			asfisb.setSuspectIdentityNumber(ia.getSuspectIdentityNumber());
		}
		Storage storage = storageService.findStorageByid(asfi.getStorageId());
		if(storage != null){
			asfisb.setExistingNumber(storage.getExistingNumber());
			asfisb.setOldAreaName(storage.getLocker().getArea().getName());
			asfisb.setOldLockerName(storage.getLocker().getLocation());
		}
		return asfisb;
	}
	
	/**
	 * 出库单Model转返还单ServiceBean
	 * 
	 * @param osf 出库单Model
	 * @return bsfsb 返还单ServiceBean
	 */
	public BackStorageFormServiceBean outStorageFormToBackStorageFormServiceBean(OutStorageForm osf){
		if(osf == null){
			return null;
		}
		BackStorageFormServiceBean bsfsb = new BackStorageFormServiceBean();
		bsfsb.setOutStorageFormId(osf.getId());
		bsfsb.setOutStorageFormCode(osf.getCode());
		bsfsb.setCaseCode(osf.getCaseCode());
		CriminalBasicCaseBean cbcb = impoundedObjectService.findCriminalBasicCaseBeanForInStorageByCaseId(osf.getCaseCode());
		bsfsb.setCaseName("");
		bsfsb.setPolices("");
		if(cbcb != null){
			bsfsb.setCaseName(cbcb.getCaseName());
			bsfsb.setPolices(cbcb.getHandlePolices());
		}
		bsfsb.setType(osf.getType());
		bsfsb.setTypeName(articleTypeIdToName(osf.getType()));
		return bsfsb;
	}
	
	/**
	 * 出库单项Model转ServiceBean
	 * 
	 * @param osfi 出库单项Model
	 * @return osfisb 出库单项ServiceBean
	 */
	public OutStorageFormItemServiceBean outStorageFormItemToOutStorageFormItemServiceBean(OutStorageFormItem osfi){
		if(osfi == null){
			return null;
		}
		OutStorageFormItemServiceBean osfisb = new OutStorageFormItemServiceBean();
		osfisb.setId(osfi.getId());
		osfisb.setOutcomingNumber(osfi.getOutcomingNumber());
		osfisb.setRemark(osfi.getRemark());
		osfisb.setUpdatedTime(DateFmtUtil.dateToLong(osfi.getUpdatedTime()));
		InvolvedArticle article = osfi.getArticle();
		if(article != null){
			osfisb.setInvolvedArticleId(article.getId());
			osfisb.setInvolvedArticleName(article.getName());
			osfisb.setInvolvedArticleFeature(article.getFeature());
			osfisb.setInvolvedArticleCode(article.getCode());
			osfisb.setDetentionNumber(article.getDetentionNumber());
			osfisb.setInvolvedArticleType(article.getType());
			osfisb.setInvolvedArticleTypeName(articleTypeIdToName(article.getType()));
			osfisb.setMeasurementUnit(article.getMeasurementUnit());
			osfisb.setNumberInStorage(article.getNumberInStorage());
			osfisb.setSuspectedName(article.getSuspectName());
			osfisb.setSuspectIdentityNumber(article.getSuspectIdentityNumber());
			osfisb.setPapers(article.getPaper());
			osfisb.setPapersId(article.getPaperId());
		}
		ArticleLocker al = osfi.getLocker();
		if(al != null){
			osfisb.setLockerId(al.getId());
			osfisb.setLockerName(al.getLocation());
			StorageArea sa = al.getArea();
			if(sa == null){
				return osfisb;
			}
			osfisb.setAreaId(sa.getId());
			osfisb.setAreaName(sa.getName());
		}
		return osfisb;
	}
	
	/**
	 * 返还单Model转Bean
	 * 
	 * @param bsf 返还单Model
	 * @return bsfsb 返还单Bean
	 */
	public BackStorageFormServiceBean backStorageFormToBackStorageFormServiceBean(BackStorageForm bsf){
		if(bsf == null){
			return null;
		}
		BackStorageFormServiceBean bsfsb = new BackStorageFormServiceBean();
		bsfsb.setId(bsf.getId());
		bsfsb.setOutStorageFormCode(bsf.getOutStorageFormCode());
		bsfsb.setCode(bsf.getCode());
		bsfsb.setCreatedTime(DateFmtUtil.dateToLong(bsf.getCreatedTime()));
		Person person = bsf.getModifyPeople();
		if(person != null){
			bsfsb.setOperator(person.getName());
		}else{
			bsfsb.setOperator("");
		}
		bsfsb.setRemark(bsf.getRemark());
//		bsfsb.setUpdatedTime(DateFmtUtil.dateToLong(bsf.getUpdatedTime()));
		bsfsb.setUpdatedTime(DateFmtUtil.dateToLong(bsf.getBackStorageTime()));
		bsfsb.setCaseCode(bsf.getCaseCode());
		CriminalBasicCaseBean cbcb = impoundedObjectService.findCriminalBasicCaseBeanForInStorageByCaseId(bsf.getCaseCode());
		bsfsb.setCaseName("");
		bsfsb.setPolices("");
		if(cbcb != null){
			bsfsb.setCaseName(cbcb.getCaseName());
			bsfsb.setPolices(cbcb.getHandlePolices());
		}
		//出库单信息
		OutStorageForm osf = outStorageFormService.findByCode(bsf.getOutStorageFormCode());
		if(osf != null){
			bsfsb.setType(osf.getType());
			bsfsb.setTypeName(articleTypeIdToName(osf.getType()));
			bsfsb.setOutStorageFormId(osf.getId());
		}
		return bsfsb;
	}
	
	/**
	 * 返还单项Model转Bean
	 * 
	 * @param bsfi 返还单项Model
	 * @return bsfisb 返还单项Bean
	 */
	public BackStorageFormItemServiceBean backStorageFormItemToBackStorageFormItemServiceBean(BackStorageFormItem bsfi){
		if(bsfi == null){
			return null;
		}
		BackStorageFormItemServiceBean bsfisb = new BackStorageFormItemServiceBean();
		bsfisb.setOutItemId("");
		bsfisb.setReturnedNumber(bsfi.getReturnedNumber());
		bsfisb.setMaintainTime(DateFmtUtil.dateToLong(bsfi.getMaintainTime()));
		bsfisb.setRemark(bsfi.getRemark());
		OutStorageFormItem outItem = bsfi.getOutItem();
		InvolvedArticleBean iab = null;
		if(outItem != null){
			iab = involvedArticleToInvolvedArticleBean(outItem.getArticle());
			bsfisb.setOutcomingNumber(outItem.getOutcomingNumber());
		}
		if(iab != null){
			bsfisb.setInvolvedArticleId(iab.getId());
			bsfisb.setInvolvedArticleName(iab.getName());
			bsfisb.setInvolvedArticleFeature(iab.getFeature());
			bsfisb.setInvolvedArticleCode(iab.getCode());
			bsfisb.setDetentionNumber(iab.getDetentionNumber());
			bsfisb.setMeasurementUnit(iab.getMeasurementUnit());
			bsfisb.setInvolvedArticleType(iab.getType());
			bsfisb.setInvolvedArticleTypeName(iab.getTypeName());
			bsfisb.setSuspectedName(iab.getSuspectName());
			bsfisb.setSuspectIdentityNumber(iab.getSuspectIdentityNumber());
		}
		return bsfisb;
	}
	
	/**
	 * 操作记录Model转Bean
	 * @param or 操作记录Model
	 * @param orb 操作记录Bean
	 */
	public OperationRecordBean operationRecordToOperationRecordBean(OperationRecord or){
		if(or == null){
			return null;
		}
		
		OperationRecordBean orb = new OperationRecordBean();
		try {
			BeanCopierFmtUtil.copyBean(or, orb, null);
		} catch (Exception e) {
			logger.debug("操作记录Model转Bean异常",e);
		}
		if(or.getOperationTime() != null){
			orb.setOperationTime(DateFmtUtil.dateToLong(or.getOperationTime()));
		}
		//查询涉案物品信息，取出文书信息
		InvolvedArticle ia = involvedArticleService.findInvolvedArticleByCode(or.getArticleCode());
		if(ia != null){
			orb.setPaperId(ia.getPaperId());
			orb.setPaperName(ia.getPaper());
			orb.setPaperType(ia.getPaperType());
		}
		return orb;
	}
	
	/**
	 * 入库单操作记录Model转保管位置Bean
	 * 
	 * @param isr 入库单操作记录Model
	 * @return ssb 保管位置Bean
	 */
	public StorageServiceBean inStorageRecordToStorageServiceBean(InStorageRecord isr){
		if(isr == null){
			return null;
		}
		StorageServiceBean ssb = new StorageServiceBean();
		ssb.setId(isr.getId());
		ssb.setIncomingNumber(isr.getIncomingNumber());
		ArticleLocker l = isr.getLocker();
		
		StorageArea sa = null;
		if(l != null){
			ssb.setLockerId(l.getId());
			ssb.setLockerCode(l.getCode());
			ssb.setLockerLocation(l.getLocation());
			ssb.setLockerRemark(l.getRemark());
//			ssb.setLockerStatus(l.getStatus());
			sa = l.getArea();
		}
		
		if(sa != null){
			ssb.setAreaId(sa.getId());
			ssb.setAreaAddress(sa.getAddress());
			ssb.setAreaName(sa.getName());
			ssb.setAreaCode(sa.getCode());
			ssb.setAreaRemark(sa.getRemark());
		}
		return ssb;
	}
	
	/**
	 * 物品性质id转名称，id“,”号分割
	 * @param ids
	 * @return
	 */
	public String articleTypeIdToName(String ids){
		String articleTypeName = "";
		String[] articleTypes = ids.split(",");
		for(String articleType : articleTypes){
			String strName = findDictionaryItemNameById(articleType);
			if(!"".equals(strName)){
				articleTypeName += strName + "、";
			}
		}
		articleTypeName = articleTypeName.substring(0, articleTypeName.length()-1);
		return articleTypeName;
	}
	

	/**
	 * 通过字典项id查字典项名称
	 * 
	 * @param dictionaryItemId 字典项id
	 * @return 字典项名称
	 */
	public String findDictionaryItemNameById(String dictionaryItemId) {
		if (dictionaryItemId != null && !dictionaryItemId.isEmpty()) {
			DictionaryItem item = dictionaryItemService.findById(dictionaryItemId);
			if (item != null) {
				return item.getName();
			}
		}
		return "";
	}
}
