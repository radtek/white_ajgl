package com.taiji.pubsec.ajqlc.sawp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.service.AbstractBaseService;
import com.taiji.pubsec.ajqlc.sawp.model.ArticleLocker;
import com.taiji.pubsec.ajqlc.sawp.model.InvolvedArticle;
import com.taiji.pubsec.ajqlc.sawp.model.Storage;
import com.taiji.pubsec.ajqlc.sawp.service.IStorageService;

@Service("storageService")
public class StorageServiceImpl extends AbstractBaseService<Storage, String> implements IStorageService {
	
	@Autowired
	public StorageServiceImpl (Dao<Storage, String> dao) {
		setDao(dao);
	}
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;

	@SuppressWarnings("unchecked")
	@Override
	public void createStorage(Storage storage, String lockerId, String involvedArticleId) {
		ArticleLocker locker = (ArticleLocker) this.dao.findById(ArticleLocker.class, lockerId);
		storage.setLocker(locker);
		InvolvedArticle involvedArticle = (InvolvedArticle) this.dao.findById(InvolvedArticle.class, involvedArticleId);
		storage.setArticle(involvedArticle);
		this.dao.save(storage);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Storage> findStoragesByInvolvedArticleCode(String involvedArticleCode) {
		String xql = "select s from Storage as s where s.article.code = :involvedArticleCode";
		Map<String, Object> xqlMap = new HashMap<String, Object>(0);
		xqlMap.put("involvedArticleCode", involvedArticleCode);
		return this.dao.findAllByParams(Storage.class, xql, xqlMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Storage findStorageByid(String storageId) {
		return (Storage) this.dao.findById(Storage.class, storageId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(String storageId) {
		this.dao.update(this.dao.findById(Storage.class, storageId));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void delete(String storageId) {
		this.dao.delete(Storage.class, storageId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Storage findStorageByArticleCodeAndLockerCode(String articleCode, String lockerCode) {
		String xql = "select s from Storage as s where s.locker.code = ? and s.article.code = ?";
		List<Storage> storages = this.dao.findAllByParams(Storage.class, xql, new Object[]{articleCode, lockerCode});
		return storages.isEmpty() ? null : storages.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Storage findStorageByArticleCodeAndLockerId(String articleCode, String lockerId) {
		String xql = "select s from Storage as s where s.locker.id = ? and s.article.code = ?";
		List<Storage> storages = this.dao.findAllByParams(Storage.class, xql, new Object[]{lockerId, articleCode});
		return storages.isEmpty() ? null : storages.get(0);
	}
	
	
}
