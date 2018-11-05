package com.taiji.pubsec.ajqlc.sawp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.annotations.Where;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.sawp.bean.ArticleInvolvedServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.LockerStatisticsServiceBean;
import com.taiji.pubsec.ajqlc.sawp.model.ArticleLocker;
import com.taiji.pubsec.ajqlc.sawp.model.BackStorageFormItem;
import com.taiji.pubsec.ajqlc.sawp.model.InvolvedArticle;
import com.taiji.pubsec.ajqlc.sawp.model.OutStorageFormItem;
import com.taiji.pubsec.ajqlc.sawp.model.Storage;
import com.taiji.pubsec.ajqlc.sawp.service.IStorageQueryService;
import com.taiji.pubsec.ajqlc.sawp.util.ParamMapUtil;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryItem;
import com.taiji.pubsec.businesscomponent.dictionary.service.IDictionaryItemService;
import com.taiji.pubsec.common.tool.sql.SQLTool;

import javassist.LoaderClassPath;
import javassist.compiler.ast.NewExpr;

/**
 * @author chengkai
 */
@Service("storageQueryService")
public class StorageQueryServiceImpl implements IStorageQueryService {
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@Resource
	private IDictionaryItemService dictionaryItemService;	
	
	private static final String IS_AREA_ENABLED_YES = "0000000002002"; // 保管区是否启用：是
	private static final String ISORNOTFREE_YES = "0000000007002";	//储物柜是否空闲：是
	private static final String ISORNOTFREE_NO = "0000000007001";	//储物柜是否空闲：否
	
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StorageQueryServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public int acquireOutcomingCount(String articleId) {
		String xql = "select osfi from OutStorageFormItem as osfi where osfi.article.id = ?";
		List<OutStorageFormItem> osfiList = this.dao.findAllByParams(OutStorageFormItem.class, xql, new Object[]{articleId});
		int count = 0;
		for(OutStorageFormItem outStorageFormItem: osfiList){
			count += outStorageFormItem.getOutcomingNumber();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int acquireReturnedCount(String articleId) {
		String xql = "select bsfi from BackStorageFormItem as bsfi where bsfi.articleId = ?";
		List<BackStorageFormItem> bsfiList = this.dao.findAllByParams(BackStorageFormItem.class, xql, new Object[]{articleId});
		int count = 0;
		for(BackStorageFormItem backStorageFormItem: bsfiList){
			count += backStorageFormItem.getReturnedNumber();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isAvailableWithoutAnything(String lockerId) {
		String xql = "select s from Storage as s where s.locker.id = ? ";
		List<Storage> sList = this.dao.findAllByParams(Storage.class, xql, new Object[]{lockerId});
		if(sList.isEmpty()){
			ArticleLocker l = (ArticleLocker) this.dao.findById(ArticleLocker.class, lockerId);
			if(IS_AREA_ENABLED_YES.equals(l.getArea().getState())){
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pager<ArticleInvolvedServiceBean> queryArticleInvolved(Map<String, Object> conditions, int pageNo, int pageSize) {
		StringBuilder xql = new StringBuilder("select ia from InvolvedArticle as ia where 1 = 1 ");
		Map<String, Object> xqlMap = new HashMap<String, Object>();
		if(ParamMapUtil.isNotBlank(conditions.get("code"))){
			xql.append(" and ia.code like :code ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("code", "%" + SQLTool.SQLSpecialChTranfer((String) conditions.get("code")) + "%");
		}
		if(ParamMapUtil.isNotBlank(conditions.get("nameOrFeature"))){
			xql.append(" and (ia.name like :name ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("name", "%" + SQLTool.SQLSpecialChTranfer((String) conditions.get("nameOrFeature")) + "%");
			xql.append(" or ia.feature like :feature ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("feature", "%" + SQLTool.SQLSpecialChTranfer((String) conditions.get("nameOrFeature")) + "%");
			xql.append(") ");
		}
		if(ParamMapUtil.isNotBlank(conditions.get("caseCodeOrName"))){
			xql.append(" and (ia.caseCode like :caseCode ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("caseCode", "%" + SQLTool.SQLSpecialChTranfer((String) conditions.get("caseCodeOrName")) + "%");
			xql.append(" or ia.caseName like :caseName ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("caseName", "%" + SQLTool.SQLSpecialChTranfer((String) conditions.get("caseCodeOrName")) + "%");
			xql.append(") ");
		}
		if(ParamMapUtil.isNotBlank(conditions.get("suspectNameOrIdCardNo"))){
			xql.append(" and (ia.suspectName like :suspectName ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("suspectName", "%" + SQLTool.SQLSpecialChTranfer((String) conditions.get("suspectNameOrIdCardNo")) + "%");
			xql.append(" or ia.suspectIdentityNumber like :suspectIdentityNumber ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("suspectIdentityNumber", "%" + SQLTool.SQLSpecialChTranfer((String) conditions.get("suspectNameOrIdCardNo")) + "%");
			xql.append(") ");
		}
		if(ParamMapUtil.isNotBlank(conditions.get("polices"))){
			xql.append(" and ia.polices like :polices ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("polices", "%" + SQLTool.SQLSpecialChTranfer((String) conditions.get("polices")) + "%");
		}
		if(ParamMapUtil.isNotBlank(conditions.get("paper"))){
			xql.append(" and ia.paper like :paper ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("paper", "%" + SQLTool.SQLSpecialChTranfer((String) conditions.get("paper")) + "%");
		}
		
		Pager<InvolvedArticle> iaPager = this.dao.findByPage(InvolvedArticle.class, xql.toString(), xqlMap, pageNo, pageSize);
		Pager<ArticleInvolvedServiceBean> aibPager = new Pager<ArticleInvolvedServiceBean>();
		aibPager.setTotalNum(iaPager.getTotalNum());
		for(int i = 0; i < iaPager.getPageList().size(); i++){
			ArticleInvolvedServiceBean articleInvolvedServiceBean = new ArticleInvolvedServiceBean();
			articleInvolvedServiceBean.setArticleId(iaPager.getPageList().get(i).getId());
			articleInvolvedServiceBean.setCaseCode(iaPager.getPageList().get(i).getCaseCode());
			articleInvolvedServiceBean.setCaseName(iaPager.getPageList().get(i).getCaseName());
			articleInvolvedServiceBean.setArticleCode(iaPager.getPageList().get(i).getCode());
			articleInvolvedServiceBean.setDetentionNumber(iaPager.getPageList().get(i).getDetentionNumber());
			articleInvolvedServiceBean.setFeature(iaPager.getPageList().get(i).getFeature());
			articleInvolvedServiceBean.setType(iaPager.getPageList().get(i).getType());
			if(iaPager.getPageList().get(i).getType() != null){
				DictionaryItem item = dictionaryItemService.findById(iaPager.getPageList().get(i).getType());
				if(item == null){
					articleInvolvedServiceBean.setTypeName("");
				}else{
					articleInvolvedServiceBean.setTypeName(item.getName());
				}
			}
			articleInvolvedServiceBean.setMeasurementUnit(iaPager.getPageList().get(i).getMeasurementUnit());
			articleInvolvedServiceBean.setArticleName(iaPager.getPageList().get(i).getName());
			articleInvolvedServiceBean.setNumberInStorage(iaPager.getPageList().get(i).getNumberInStorage());
			articleInvolvedServiceBean.setPaper(iaPager.getPageList().get(i).getPaper());
			articleInvolvedServiceBean.setPapersId(iaPager.getPageList().get(i).getPaperId());
			articleInvolvedServiceBean.setPapersType(iaPager.getPageList().get(i).getPaperType());
			articleInvolvedServiceBean.setCaseHandler(iaPager.getPageList().get(i).getPolices());
			articleInvolvedServiceBean.setSuspectName(iaPager.getPageList().get(i).getSuspectName());
			articleInvolvedServiceBean.setSuspectIdentifier(iaPager.getPageList().get(i).getSuspectIdentityNumber());
			articleInvolvedServiceBean.setOutComingNumber(this.acquireOutcomingCount(iaPager.getPageList().get(i).getId()));
			articleInvolvedServiceBean.setReturnedNumber(this.acquireReturnedCount(iaPager.getPageList().get(i).getId()));
			aibPager.getPageList().add(articleInvolvedServiceBean);
		}
		return aibPager;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pager<Storage> queryArticleInvolvedInStorage(Map<String, Object> conditions, int pageNo,
			int pageSize) {
		StringBuilder xql = new StringBuilder("select s from Storage as s where 1 = 1 ");
		Map<String, Object> xqlMap = new HashMap<String, Object>();
		if(ParamMapUtil.isNotBlank(conditions.get("nameAndFeature"))){
			xql.append("and (s.article.name like :name ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("name", "%" + SQLTool.SQLSpecialChTranfer((String) conditions.get("nameAndFeature")) + "%");
			xql.append("or s.article.feature like :feature ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("feature", "%" + SQLTool.SQLSpecialChTranfer((String) conditions.get("nameAndFeature")) + "%");
			xql.append(") ");
		}
		if(ParamMapUtil.isNotBlank(conditions.get("caseCode"))){
			xql.append(" and s.article.caseCode like :caseCode ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("caseCode", "%" + SQLTool.SQLSpecialChTranfer((String) conditions.get("caseCode")) + "%");
		}
		if(ParamMapUtil.isNotBlank(conditions.get("disposePerson"))){
			xql.append(" and (s.article.polices like :disposePerson1 ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("disposePerson1", "%" + SQLTool.SQLSpecialChTranfer((String)conditions.get("disposePerson")) + "%");
			xql.append(" or s.article.policeNumber1 like :disposePerson2 ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("disposePerson2", "%" + SQLTool.SQLSpecialChTranfer((String)conditions.get("disposePerson")) + "%");
			xql.append(" or s.article.policeNumber2 like :disposePerson3 ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("disposePerson3", "%" + SQLTool.SQLSpecialChTranfer((String)conditions.get("disposePerson")) + "%");
			xql.append(")");
		}
		if(ParamMapUtil.isNotBlank(conditions.get("suspectNameOrNumber"))){
			xql.append("and (s.article.suspectName like :suspectName ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("suspectName", "%" + SQLTool.SQLSpecialChTranfer((String) conditions.get("suspectNameOrNumber")) + "%");
			xql.append("or s.article.suspectIdentityNumber like :suspectIdentityNumber ");
			SQLTool.SQLAddEscape(xql);
			xqlMap.put("suspectIdentityNumber", "%" + SQLTool.SQLSpecialChTranfer((String) conditions.get("suspectNameOrNumber")) + "%");
			xql.append(") ");
		}
		if(ParamMapUtil.isNotBlank(conditions.get("lockArea"))){
			xql.append(" and s.locker.area.id = :lockArea ");
			xqlMap.put("lockArea", conditions.get("lockArea"));
		}
		if(!((ArrayList<String>)conditions.get("lockerIds")).isEmpty()){
			xql.append(" and s.locker.id in (:lockerIds) ");
			xqlMap.put("lockerIds", conditions.get("lockerIds"));
		}
		if (ParamMapUtil.isNotBlank(conditions.get("startTime"))) {
			xql.append(" and s.article.inStorageTime >= :startTime ");
			xqlMap.put("startTime", conditions.get("startTime"));
		}
		if (ParamMapUtil.isNotBlank(conditions.get("endTime"))) {
			xql.append( "and s.article.inStorageTime <= :endTime ");
			xqlMap.put("endTime", conditions.get("endTime"));
		}
		if(!(boolean)conditions.get("isZero")){
			xql.append("and s.existingNumber > 0 ");
		}
		xql.append(" order by s.article.code");
		
		List<Storage> storages = this.dao.findAllByParams(Storage.class, xql.toString(), xqlMap);
		
		//查询未分配柜架的物品
		List<InvolvedArticle> articles = findInvolvedArticleNotInLokcers(conditions);
		
		if((boolean)conditions.get("isZero")){
			for(InvolvedArticle article: articles){
				Storage storage = new Storage();
				storage.setArticle(article);
				storages.add(storage); 
			}
		}
		
		Pager<Storage> sPager = new Pager<Storage>();
		sPager.setTotalNum(storages.size());
		for(int i = pageNo * pageSize; i < (pageNo + 1) * pageSize; i++){
			if(i < storages.size()){
				sPager.getPageList().add(storages.get(i));
			}
		}
		
		return sPager;
	}

	@SuppressWarnings("unchecked")
	private List<InvolvedArticle> findInvolvedArticleNotInLokcers(Map<String, Object> conditions){
		StringBuilder xql = new StringBuilder("select i from InvolvedArticle as i where i.id not in (select distinct s.article.id from Storage as s) ");
		Map<String, Object> xqlMap = new HashMap<String, Object>();
		List<InvolvedArticle> articles = new ArrayList<InvolvedArticle>();
		
		if(ParamMapUtil.isNotBlank(conditions.get("lockArea"))){
			return articles;
		}else{
			if(ParamMapUtil.isNotBlank(conditions.get("nameAndFeature"))){
				xql.append("and (i.name like :name ");
				SQLTool.SQLAddEscape(xql);
				xqlMap.put("name", "%" + SQLTool.SQLSpecialChTranfer((String) conditions.get("nameAndFeature")) + "%");
				xql.append("or i.feature like :feature ");
				SQLTool.SQLAddEscape(xql);
				xqlMap.put("feature", "%" + SQLTool.SQLSpecialChTranfer((String) conditions.get("nameAndFeature")) + "%");
				xql.append(") ");
			}
			if(ParamMapUtil.isNotBlank(conditions.get("caseCode"))){
				xql.append(" and i.caseCode like :caseCode ");
				SQLTool.SQLAddEscape(xql);
				xqlMap.put("caseCode", "%" + SQLTool.SQLSpecialChTranfer((String) conditions.get("caseCode")) + "%");
			}
			if(ParamMapUtil.isNotBlank(conditions.get("disposePerson"))){
				xql.append(" and (i.polices like :disposePerson1 ");
				SQLTool.SQLAddEscape(xql);
				xqlMap.put("disposePerson1", "%" + SQLTool.SQLSpecialChTranfer((String)conditions.get("disposePerson")) + "%");
				xql.append(" or i.policeNumber1 like :disposePerson2 ");
				SQLTool.SQLAddEscape(xql);
				xqlMap.put("disposePerson2", "%" + SQLTool.SQLSpecialChTranfer((String)conditions.get("disposePerson")) + "%");
				xql.append(" or i.policeNumber2 like :disposePerson3 ");
				SQLTool.SQLAddEscape(xql);
				xqlMap.put("disposePerson3", "%" + SQLTool.SQLSpecialChTranfer((String)conditions.get("disposePerson")) + "%");
				xql.append(")");
			}
			if(ParamMapUtil.isNotBlank(conditions.get("suspectNameOrNumber"))){
				xql.append("and (i.suspectName like :suspectName ");
				SQLTool.SQLAddEscape(xql);
				xqlMap.put("suspectName", "%" + SQLTool.SQLSpecialChTranfer((String) conditions.get("suspectNameOrNumber")) + "%");
				xql.append("or i.suspectIdentityNumber like :suspectIdentityNumber ");
				SQLTool.SQLAddEscape(xql);
				xqlMap.put("suspectIdentityNumber", "%" + SQLTool.SQLSpecialChTranfer((String) conditions.get("suspectNameOrNumber")) + "%");
				xql.append(") ");
			}
			if (ParamMapUtil.isNotBlank(conditions.get("startTime"))) {
				xql.append(" and i.inStorageTime >= :startTime ");
				xqlMap.put("startTime", conditions.get("startTime"));
			}
			if (ParamMapUtil.isNotBlank(conditions.get("endTime"))) {
				xql.append( "and i.inStorageTime <= :endTime ");
				xqlMap.put("endTime", conditions.get("endTime"));
			}
			xql.append(" order by i.code");
			
			articles = this.dao.findAllByParams(InvolvedArticle.class, xql.toString(), xqlMap);
			return articles;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Pager<LockerStatisticsServiceBean> findLockersByConditions(Map<String, Object> conditions, int pageNo,
			int pageSize) {
		StringBuilder xql = new StringBuilder("select distinct l from ArticleLocker as l ");
		Map<String, Object> xqlMap = new HashMap<String, Object>();
		if(ISORNOTFREE_NO.equals(conditions.get("isOrNotFree")) || ParamMapUtil.isNotBlank(conditions.get("caseCodeOrName")) 
				|| ParamMapUtil.isNotBlank(conditions.get("polices")) || ParamMapUtil.isNotBlank(conditions.get("suspectNameOrIdCardNo"))){
			xql.append(", Storage as s where s.locker.id = l.id and s.existingNumber > 0 ");
			if(ParamMapUtil.isNotBlank(conditions.get("caseCodeOrName"))){
				xql.append(" and (s.article.caseCode like :caseCode ");
				SQLTool.SQLAddEscape(xql);
				xqlMap.put("caseCode", "%" + conditions.get("caseCodeOrName") + "%");
				xql.append(" or s.article.caseName like :caseName ");
				SQLTool.SQLAddEscape(xql);
				xqlMap.put("caseName", "%" + conditions.get("caseCodeOrName") + "%");
				xql.append(")");
			}
			if(ParamMapUtil.isNotBlank(conditions.get("polices"))){
				xql.append(" and s.article.polices like :polices ");
				SQLTool.SQLAddEscape(xql);
				xqlMap.put("polices", "%" + conditions.get("polices") + "%");
			}
			if(ParamMapUtil.isNotBlank(conditions.get("suspectNameOrIdCardNo"))){
				xql.append(" and (s.article.suspectName like :suspectName ");
				SQLTool.SQLAddEscape(xql);
				xqlMap.put("suspectName", "%" + conditions.get("suspectNameOrIdCardNo") + "%");
				xql.append(" or s.article.suspectIdentityNumber like :suspectIdentityNumber ");
				SQLTool.SQLAddEscape(xql);
				xqlMap.put("suspectIdentityNumber", "%" + conditions.get("suspectNameOrIdCardNo") + "%");
				xql.append(")");
			}
			if(ISORNOTFREE_NO.equals(conditions.get("isOrNotFree"))){
				xql.append(" and s.existingNumber > 0");
			}
		}else{
			xql.append(" where 1 = 1 ");
		}
		if(ISORNOTFREE_YES.equals(conditions.get("isOrNotFree"))){
			xql.append(" and l.id not in (select al.id from ArticleLocker as al, Storage as st where al.id = st.locker.id and st.existingNumber > 0)");
		}
		if(ParamMapUtil.isNotBlank(conditions.get("areaId"))){
			xql.append(" and l.area.id = :areaId ");
			xqlMap.put("areaId", conditions.get("areaId"));
		}
		if(ParamMapUtil.isNotBlank(conditions.get("lockerIds"))){
			xql.append(" and l.id in (:lockerIds) ");
			xqlMap.put("lockerIds", conditions.get("lockerIds"));
		}
		
		xql.append(" order by l.area");
		
		Pager<ArticleLocker> alPager = this.dao.findByPage(ArticleLocker.class, xql.toString(), xqlMap, pageNo, pageSize);
		
		Pager<LockerStatisticsServiceBean> lssbPager = new Pager<LockerStatisticsServiceBean>();
		for(ArticleLocker al: alPager.getPageList()){
			LockerStatisticsServiceBean lockerStatisticsServiceBean = new LockerStatisticsServiceBean();
			lockerStatisticsServiceBean.setAreaName(al.getArea().getName());
			lockerStatisticsServiceBean.setLockerId(al.getId());
			lockerStatisticsServiceBean.setLockerName(al.getLocation());
			String xql1 = "select s from Storage as s where s.locker.id = ?";
			List<Storage> sList = this.dao.findAllByParams(Storage.class, xql1, new Object[]{al.getId()});
			
			String status = "是";
			Double existingNumber = 0.0;
			if(sList.isEmpty()){
				status = "是";
			}
			for(Storage s: sList){
				if(s.getExistingNumber() > 0){
					status = "否";
				}
				existingNumber += s.getExistingNumber();
			}
			lockerStatisticsServiceBean.setStatutsName(status);
			lockerStatisticsServiceBean.setNumberInStorage(existingNumber);
			
			List<String> articleList = new ArrayList<String>();
			if(status == "否"){
				String xql2 = "select distinct s.article from Storage as s where s.locker.id = ?";
				List<InvolvedArticle> iaList = this.dao.findAllByParams(InvolvedArticle.class, xql2, new Object[]{al.getId()});
				lockerStatisticsServiceBean.setCaseCode(iaList.get(0).getCaseCode());
				lockerStatisticsServiceBean.setCaseName(iaList.get(0).getCaseName());
				lockerStatisticsServiceBean.setPolices(iaList.get(0).getPolices());
				lockerStatisticsServiceBean.setSuspectIdentityNumber(iaList.get(0).getSuspectIdentityNumber() == null ? "" : iaList.get(0).getSuspectIdentityNumber());
				for(InvolvedArticle ia: iaList){
					String article = ia.getCode() + "/";
					if(ia.getName() != null){
						article += ia.getName();
					}else {
						article += "空";
					}
					articleList.add(article);
				} 
			}
			lockerStatisticsServiceBean.setArticleList(articleList);
			
			lssbPager.getPageList().add(lockerStatisticsServiceBean);
		}
		lssbPager.setTotalNum(alPager.getTotalNum());
		
		return lssbPager;
	}

	
//	@SuppressWarnings("unchecked")
//	@Override
//	public Pager<StorageServiceBean> queryStorageInLocker(Map<String, Object> conditions, int pageNo, int pageSize) {
//		StringBuilder xql = new StringBuilder("select s from Storage as s where 1 = 1 ");
//		Map<String, Object> xqlMap = new HashMap<String, Object>();
//		if(ParamMapUtil.isNotBlank(conditions.get("lockerId"))){
//			xql.append("s.locker.id = :lockerId ");
//			xqlMap.put("lockerId", conditions.get("lockerId"));
//		}
//		if(ParamMapUtil.isNotBlank(conditions.get("suspectName"))){
//			xql.append("s.article.suspectName like :suspectName ");
//			SQLTool.SQLAddEscape(xql);
//			xqlMap.put("suspectName", "%" + SQLTool.SQLSpecialChTranfer((String) conditions.get("suspectName")) + "%");
//		}
//		if(ParamMapUtil.isNotBlank(conditions.get("caseCode"))){
//			xql.append("s.article.caseCode like :caseCode ");
//			SQLTool.SQLAddEscape(xql);
//			xqlMap.put("caseCode", "%" + SQLTool.SQLSpecialChTranfer((String) conditions.get("caseCode")) + "%");
//		}
//		if(ParamMapUtil.isNotBlank(conditions.get("polices"))){
//			xql.append("s.article.polices like :polices ");
//			SQLTool.SQLAddEscape(xql);
//			xqlMap.put("polices", "%" + SQLTool.SQLSpecialChTranfer((String) conditions.get("polices")) + "%");
//		}
//		Pager<Storage> sPager = this.dao.findByPage(Storage.class, xql.toString(), xqlMap, pageNo, pageSize);
//		Pager<StorageServiceBean> sbPager = new Pager<StorageServiceBean>();
//		sbPager.setTotalNum(sPager.getTotalNum());
//		for(int i = 0; i < sPager.getPageList().size(); i++){
//			sbPager.getPageList().get(i).setAreaName(sPager.getPageList().get(i).getLocker().getArea().getName());
//			sbPager.getPageList().get(i).setArticleCode(sPager.getPageList().get(i).getArticle().getCode());
//			sbPager.getPageList().get(i).setArticleName(sPager.getPageList().get(i).getArticle().getName());
//			sbPager.getPageList().get(i).setCaseCode(sPager.getPageList().get(i).getArticle().getCaseCode());
//			sbPager.getPageList().get(i).setCaseHandler(sPager.getPageList().get(i).getArticle().getPolices());
//			sbPager.getPageList().get(i).setLockerId(sPager.getPageList().get(i).getLocker().getId());
//			sbPager.getPageList().get(i).setLockerName(sPager.getPageList().get(i).getLocker().getLocation());
//			sbPager.getPageList().get(i).setNumberInStorage(sPager.getPageList().get(i).getArticle().getNumberInStorage());
//			sbPager.getPageList().get(i).setSuspectName(sPager.getPageList().get(i).getArticle().getSuspectName());
//			sbPager.getPageList().get(i).setEmpty(this.isAvailableWithoutAnything(sPager.getPageList().get(i).getLocker().getId()));
//		}
//		return sbPager;
//	}
}
