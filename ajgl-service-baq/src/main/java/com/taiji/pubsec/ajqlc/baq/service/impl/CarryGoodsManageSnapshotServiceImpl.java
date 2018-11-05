package com.taiji.pubsec.ajqlc.baq.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajqlc.baq.model.CarryGoodsManageSnapshot;
import com.taiji.pubsec.ajqlc.baq.model.GoodsSnapshot;
import com.taiji.pubsec.ajqlc.baq.service.ICarryGoodsManageSnapshotService;

@Service("carryGoodsManageSnapshotService")
public class CarryGoodsManageSnapshotServiceImpl implements ICarryGoodsManageSnapshotService {

	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@SuppressWarnings("unchecked")
	@Override
	public void saveCarryGoodsManageSnapshot(CarryGoodsManageSnapshot carryGoodsManageSnapshot,
			List<GoodsSnapshot> goodsSnapshots) {
		//新增本次入库数量快照记录
		dao.save(carryGoodsManageSnapshot);
		
		//新增入库快照记录
		for(GoodsSnapshot goodsSnapshot: goodsSnapshots){
			goodsSnapshot.setCarryGoodsManageSnapshot(carryGoodsManageSnapshot);
			dao.save(goodsSnapshot);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public CarryGoodsManageSnapshot findCarryGoodsManageSnapshotByReceiptId(String receiptId, String operateType) {
		String xql = "select c from CarryGoodsManageSnapshot as c where c.HandlingAreaReceiptId = ? order by c.snapshotTime desc";
		List<CarryGoodsManageSnapshot> carryGoodsManageSnapshots = dao.findAllByParams(CarryGoodsManageSnapshot.class, xql, new Object[]{receiptId});
		
		if(carryGoodsManageSnapshots == null || carryGoodsManageSnapshots.size() == 0){
			return null;
		}else{
			CarryGoodsManageSnapshot manageSnapshot = carryGoodsManageSnapshots.get(0);
			String xql1 = "select s from GoodsSnapshot as s where s.carryGoodsManageSnapshot.id = ? and s.operationType = ?";
			List<GoodsSnapshot> snapshots = dao.findAllByParams(GoodsSnapshot.class, xql1, new Object[]{manageSnapshot.getId(), operateType});
			manageSnapshot.setGoodsSnapshots(new HashSet<GoodsSnapshot>(snapshots));
			
			return manageSnapshot;
		}
	}

}
