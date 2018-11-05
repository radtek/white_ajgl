package com.taiji.pubsec.ajqlc.baq.service;

import java.util.List;

import com.taiji.pubsec.ajqlc.baq.model.CarryGoodsReturnRecord;

/**
 * 随身物品返还记录服务接口
 * @author chengkai
 *
 */
public interface ICarryGoodsReturnRecordService {
	
	/**
	 * 根据随身物品记录id查找最新的随身物品返还记录，没有记录则返回null
	 * @param carryGoodsRecordId 随身物品信息id
	 * @return 随身物品返还记录实体
	 */
	public CarryGoodsReturnRecord findReturnRecordByGoodsRecordId(String carryGoodsRecordId);
	
	/**
	 * 根据id查询随身物品返还记录
	 * @param id
	 * @return
	 */
	public CarryGoodsReturnRecord findById(String id);
	
	/**
	 * 修改随身物品返还记录
	 * @param carryGoodsReturnRecord 随身物品返还记录信息
	 */
	public void update(CarryGoodsReturnRecord carryGoodsReturnRecord);
	
	/**
	 * 保存随身物品返还记录,当随身物品记录状态为已返还时，相应的随身物品记录状态更改为离库
	 * @param cgrrList 随身物品返还记录list
	 */
	public void saveCarryGoodsReturnRecord(List<CarryGoodsReturnRecord> cgrrList);
}
