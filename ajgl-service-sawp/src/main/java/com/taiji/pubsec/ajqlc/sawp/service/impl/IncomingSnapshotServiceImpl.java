package com.taiji.pubsec.ajqlc.sawp.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajqlc.sawp.model.BackStorageSnapshot;
import com.taiji.pubsec.ajqlc.sawp.model.InStorageSnapshot;
import com.taiji.pubsec.ajqlc.sawp.model.IncomingSnapshot;
import com.taiji.pubsec.ajqlc.sawp.service.IIncomingSnapshotService;

@Service("incomingSnapshotService")
public class IncomingSnapshotServiceImpl implements IIncomingSnapshotService {
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;

	@SuppressWarnings("unchecked")
	@Override
	public void saveInStorageSnapshots(IncomingSnapshot incomingSnapshot, List<InStorageSnapshot> inStorageSnapshots) {
		//新增本次入库数量快照记录
		incomingSnapshot.setSnapshotTime(new Date());
		dao.save(incomingSnapshot);
		
		//新增入库快照记录
		for(InStorageSnapshot snapshot: inStorageSnapshots){
			snapshot.setIncomingSnapshot(incomingSnapshot);
			dao.save(snapshot);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveBackStorageSnapshots(IncomingSnapshot incomingSnapshot,
			List<BackStorageSnapshot> backStorageSnapshots) {
		//新增本次入库数量快照记录
		incomingSnapshot.setSnapshotTime(new Date());
		dao.save(incomingSnapshot);
		
		//新增再入库快照记录
		for(BackStorageSnapshot snapshot: backStorageSnapshots){
			snapshot.setIncomingSnapshot(incomingSnapshot);
			dao.save(snapshot);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public IncomingSnapshot findLatestIncomingSnapshotByFormId(String formId) {
		String xql = "select i from IncomingSnapshot as i where i.formId = ? order by i.snapshotTime desc";
		List<IncomingSnapshot> incomingSnapshots = dao.findAllByParams(IncomingSnapshot.class, xql, new Object[]{formId});
		return (incomingSnapshots.size() > 0) ? incomingSnapshots.get(0) : null;
	}
}
