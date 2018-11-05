package com.taiji.pubsec.ajqlc.sawp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.sawp.model.ArticleLocker;
import com.taiji.pubsec.ajqlc.sawp.model.Storage;
import com.taiji.pubsec.ajqlc.sawp.model.StorageArea;
import com.taiji.pubsec.ajqlc.sawp.service.IStorageAreaService;

/**
 * @author chengkai
 */
@Service("storageAreaService")
public class StorageAreaServiceImpl implements IStorageAreaService {
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	private static final String STATE_ON = "1";	// 保管区启用状态
	private static final String STATE_OFF = "0";	// 保管区停用状态

	@SuppressWarnings("unchecked")
	@Override
	public void save(StorageArea storageArea) {
		this.dao.save(storageArea);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(StorageArea storageArea) {
		this.dao.update(storageArea);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> deleteStorageAreas(String... storageAreaIds) {
		List<String> cannotDeleteAreas = new ArrayList<String>();
		for(String id: storageAreaIds){
			String xql = "select s from ArticleLocker as s where s.area.id = ?";
			List<ArticleLocker> articleLockers = this.dao.findAllByParams(ArticleLocker.class, xql, new Object[]{id});
			if(articleLockers.isEmpty()){
				this.dao.delete(StorageArea.class, id);
			}else{
				StorageArea storageArea = (StorageArea) this.dao.findById(StorageArea.class, id);
				cannotDeleteAreas.add(storageArea.getName());
			}
		}
		return cannotDeleteAreas;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<StorageArea> findStorageAreasByStatus(String unitId, String status) {
		StringBuilder xql = new StringBuilder("select sa from StorageArea as sa where 1 = 1 ");
		Map<String, String> xqlMap = new HashMap<String, String>();
		if(StringUtils.isNotBlank(unitId)){
			xql.append(" and sa.unit.id = :unitId ");
			xqlMap.put("unitId", unitId);
		}
		if(StringUtils.isNotBlank(status)){
			xql.append(" and sa.state = :state");
			xqlMap.put("state", status);
		}
		xql.append(" order by sa.code asc ");
		return this.dao.findAllByParams(StorageArea.class, xql.toString(), xqlMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public StorageArea findById(String areaId) {
		return (StorageArea) this.dao.findById(StorageArea.class, areaId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public StorageArea findByUnitIdAndName(String unitId, String name) {
		String xql = "select sa from StorageArea as sa where sa.unit.id = ? and sa.name = ?";
		return (StorageArea) this.dao.findByParams(StorageArea.class, xql, new Object[]{unitId, name});
	}

	@SuppressWarnings("unchecked")
	@Override
	public StorageArea findByUnitIdAndCode(String unitId, String code) {
		String xql = "select sa from StorageArea as sa where sa.unit.id = ? and sa.code = ?";
		return (StorageArea) this.dao.findByParams(StorageArea.class, xql, new Object[]{unitId, code});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pager<StorageArea> findAllStorageAreas(int pageNo, int pageSize) {
		String xql = "select sa from StorageArea as sa order by sa.code";
		return this.dao.findByPage(StorageArea.class, xql, pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateStatus(String storageAreaId, String status) {
		StorageArea sa = (StorageArea) this.dao.findById(StorageArea.class, storageAreaId);
		sa.setState(status);
		this.update(sa);
	}

	@SuppressWarnings("unchecked")
	@Override
	public StorageArea findStorageAreaByArticleId(String articleId) {
		String xql = "select s from Storage as s where s.article.id = ?";
		List<Storage> storages = this.dao.findAllByParams(Storage.class, xql, new Object[]{articleId});
		if(!storages.isEmpty()){
			return storages.get(0).getLocker().getArea();
		}else{
			return null;
		}
	}

	
	
}
