package com.taiji.pubsec.ajqlc.sawp.service.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.dao.Pager;
import com.taiji.persistence.service.AbstractBaseService;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageArea;
import com.taiji.pubsec.ajqlc.sawp.service.ITemporaryStorageAreaService;

@Service("temporaryStorageAreaServiceImpl")
public class TemporaryStorageAreaServiceImpl extends
		AbstractBaseService<TemporaryStorageArea, String> implements
		ITemporaryStorageAreaService {
	@Autowired
	public TemporaryStorageAreaServiceImpl(Dao<TemporaryStorageArea, String> dao) {
		setDao(dao);
	}

	@Resource
	private Dao<TemporaryStorageArea, String> dao;

	@Override
	public String saveTemporaryStorageArea(TemporaryStorageArea area) {
		this.dao.save(area);
		return area.getId();
	}

	
	@Override
	public void updateTemporaryStorageArea(TemporaryStorageArea area) {
		this.dao.update(area);
	}

	
	@Override
	public TemporaryStorageArea findTemporaryStorageAreaById(String id) {
		return (TemporaryStorageArea) this.dao.findById(
				TemporaryStorageArea.class, id);
	}

	
	@Override
	public TemporaryStorageArea findTemporaryStorageAreaByCode(String code) {
		String xql = "select tsa from TemporaryStorageArea as tsa where tsa.code = ?";
		return (TemporaryStorageArea) this.dao.findByParams(
				TemporaryStorageArea.class, xql, new String[] { code });
	}

	
	@Override
	public List<TemporaryStorageArea> findAllTemporaryStorageArea(String status) {
		String xql = "select tsa from TemporaryStorageArea as tsa where tsa.status = ?  order by code";
		return (List<TemporaryStorageArea>) this.dao.findAllByParams(
				TemporaryStorageArea.class, xql, new String[]{status});
	}

	
	@Override
	public Pager<TemporaryStorageArea> findAllTemporaryStorageAreaByPage(
			int pageNo, int pageSize) {
		String xql = "select tsa from TemporaryStorageArea as tsa where 1 = 1 order by tsa.code";
		return this.dao.findByPage(TemporaryStorageArea.class, xql, pageNo,
				pageSize);
	}

	
	@Override
	public void deleteTemporaryStorageArea(String id) {
		this.dao.delete(TemporaryStorageArea.class, id);
	}

	
	@Override
	public void updateTemporaryStorageAreaStatus(String id, String status) {
		TemporaryStorageArea tsa = this.findTemporaryStorageAreaById(id);
		tsa.setStatus(status);
		this.dao.update(tsa);
	}
	
	@Override
	public TemporaryStorageArea findTemporaryStorageAreaByName(String name) {
		String xql = "select tsa from TemporaryStorageArea as tsa where tsa.name = ?";
		return  (TemporaryStorageArea)this.dao.findByParams(TemporaryStorageArea.class, xql, new String[] {name});
	}

}
