package com.taiji.pubsec.ajqlc.sawp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.dao.Pager;
import com.taiji.persistence.service.AbstractBaseService;
import com.taiji.pubsec.ajqlc.sawp.model.ArticleLocker;
import com.taiji.pubsec.ajqlc.sawp.model.Storage;
import com.taiji.pubsec.ajqlc.sawp.service.IArticleLockerService;

/**
 * @author chengkai
 */
@Service("articleLockerService")
public class ArticleLockerServiceImpl extends AbstractBaseService<ArticleLocker, String> implements IArticleLockerService {
	
	@Autowired
	public ArticleLockerServiceImpl(Dao<ArticleLocker, String> dao) {
		setDao(dao);
	}
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;

	@SuppressWarnings("unchecked")
	@Override
	public void save(ArticleLocker locker) {
		this.dao.save(locker);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(ArticleLocker locker) {
		this.dao.update(locker);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void delete(ArticleLocker locker) {
		this.dao.delete(locker);
	}

	@SuppressWarnings("unchecked")
	public List<ArticleLocker> findLockersByStorageAreaId(String storageAreaId) {
		String xql = "select l from ArticleLocker as l where l.area.id = ? order by l.code";
		return this.dao.findAllByParams(ArticleLocker.class, xql, new Object[]{storageAreaId});	
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArticleLocker findById(String lockerId) {
		return (ArticleLocker) this.dao.findById(ArticleLocker.class, lockerId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArticleLocker findByName(String areaId, String name) {
		String xql = "select al from ArticleLocker as al where al.area.id = ? and al.location = ?";
		return (ArticleLocker) this.dao.findByParams(ArticleLocker.class, xql, new Object[]{areaId, name});
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArticleLocker findByCode(String areaId, String code) {
		StringBuilder xql = new StringBuilder("select al from ArticleLocker as al where al.area.id = ? and al.code = ?");
		
		return (ArticleLocker) this.dao.findByParams(ArticleLocker.class, xql.toString(), new Object[]{areaId, code});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pager<ArticleLocker> findArticleLockersByStorageAreaIdForPage(String storageAreaId, int pageNo, int pageSize) {
		String xql = "select al from ArticleLocker as al where al.area.id = ? order by al.code";
		return this.dao.findByPage(ArticleLocker.class, new Object[]{storageAreaId}, xql, pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArticleLocker findByCode(String code) {
		String xql = "select al from ArticleLocker as al where al.code = ?";
		return (ArticleLocker) this.dao.findByParams(ArticleLocker.class, xql, new Object[]{code});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ArticleLocker> findLockersByCaseCodeAndAreaId(String caseCode, String areaId) {
		String xql = "select l from ArticleLocker as l where l.id not in (select distinct s.locker.id from Storage as s where s.article.caseCode != ? and s.locker.area.id = ? and s.existingNumber > 0) and l.area.id = ? order by l.code";
//		String xql = "select distinct s.locker from Storage as s where s.article.caseCode = ? and s.locker.area.id = ? and s.existingNumber > 0";
		List<ArticleLocker> lockerList = this.dao.findAllByParams(ArticleLocker.class, xql, new Object[]{caseCode, areaId, areaId});
		return lockerList == null ? new ArrayList<ArticleLocker>() : lockerList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<ArticleLocker, String> findUsedLockersByCaseCodes(List<String> caseCodes) {
		Map<ArticleLocker, String> resultMap = new HashMap<ArticleLocker, String>();
		String xql = "select distinct s.locker from Storage as s where s.existingNumber > 0 and s.article.caseCode in (:caseCodes)";
	    Map<String, Object> xqlMap = new HashMap<String, Object>();
	    xqlMap.put("caseCodes", caseCodes);
		List<ArticleLocker> lockers = this.dao.findAllByParams(Storage.class, xql, xqlMap);
		for(ArticleLocker locker: lockers){
			String xql1 = "select s from Storage as s where s.existingNumber > 0 and s.locker.id = ?";
			List<Storage> storages = this.dao.findAllByParams(Storage.class, xql1, new Object[]{locker.getId()});
			resultMap.put(locker, storages.get(0).getArticle().getCaseCode());
		}
		return resultMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String findCaseCodeByLockerCode(String lockerCode) {
		String caseCode = "";
		String xql = "select s from Storage as s where s.existingNumber > 0 and s.locker.code = ?";
		List<Storage> storages = this.dao.findAllByParams(Storage.class, xql, new Object[]{lockerCode});
		if(storages.size() > 0){
			caseCode = storages.get(0).getArticle().getCaseCode();
		}
		return caseCode;
	}

}
