package com.taiji.pubsec.ajqlc.baq.service;

import java.util.List;

import com.taiji.pubsec.ajqlc.baq.model.CarryGoodsManageSnapshot;
import com.taiji.pubsec.ajqlc.baq.model.GoodsSnapshot;

public interface ICarryGoodsManageSnapshotService {
	
	/**
	 * 新增维护随身物品返还情况快照记录
	 * @param carryGoodsManageSnapshot 维护随身物品返还情况快照记录
	 * @param goodsSnapshots 物品返还情况快照记录
	 */
	public void saveCarryGoodsManageSnapshot(CarryGoodsManageSnapshot carryGoodsManageSnapshot, List<GoodsSnapshot> goodsSnapshots);
	
	/**
	 * 通过使用单id查询最新的维护随身物品返还情况快照记录
	 * @param receiptId 使用单id
	 * @param operateType 操作类型
	 * @return 返回维护随身物品返还情况快照记录
	 */
	public CarryGoodsManageSnapshot findCarryGoodsManageSnapshotByReceiptId(String receiptId, String operateType);
}
