package com.taiji.pubsec.ajqlc.baq.service;

import java.util.List;

import com.taiji.pubsec.ajqlc.baq.model.CarryGoodsInfo;
import com.taiji.pubsec.ajqlc.baq.model.CarryGoodsRecord;

/**
 * 随身物品信息服务接口
 * @author chengkai
 *
 */
public interface ICarryGoodsInfoService {
	
	/**
	 * 保存随身物品信息
	 * @param carryGoodsInfo 随身物品信息实体
	 * @param cgrList 随身物品入库信息list
	 */
	@Deprecated
	public void saveCarryGoodsInfo(CarryGoodsInfo carryGoodsInfo, List<CarryGoodsRecord> cgrList);
	
	/**
	 * 保存随身物品信息
	 * @param carryGoodsInfo 随身物品信息实体
	 */
	public String saveCarryGoodsInfo(CarryGoodsInfo carryGoodsInfo);
	
	/**
	 * 保存随身物品记录
	 * @param carryGoodsRecord 随身物品记录实体
	 */
	public String saveCarryGoodsRecord(CarryGoodsRecord carryGoodsRecord);
	
	/**
	 * 删除随身物品记录
	 * @param id 随身物品记录实体id
	 */
	public boolean deleteCarryGoodsRecord(String id);
	
	/**
	 * 查找随身物品记录
	 * @param id 随身物品记录实体id
	 */
	public CarryGoodsRecord findCarryGoodsRecordById(String id);
	
	/**
	 * 查找随身物品记录
	 * @param lockerId 储物柜Id
	 */
	public List<CarryGoodsRecord> findCarryGoodsRecordByLocker(String lockerId);
	
	/**
	 * 更新随身物品记录
	 * @param carryGoodsRecord 随身物品记录实体
	 */
	public boolean updateCarryGoodsRecord(CarryGoodsRecord carryGoodsRecord);
	
	/**
	 * 修改随身物品信息
	 * @param carryGoodsInfo 随身物品信息实体
	 */
	public void updateCarryGoodsInfo(CarryGoodsInfo carryGoodsInfo);
	
	/**
	 * 修改随身物品信息
	 * @param carryGoodsInfo 随身物品信息实体
	 * @param cgrList 随身物品入库信息list
	 */
	@Deprecated
	public void updateCarryGoodsInfo(CarryGoodsInfo carryGoodsInfo, List<CarryGoodsRecord> cgrList);
	
	/**
	 * 根据随身物品信息id查询随身物品信息
	 * @param carryGoodsInfoId 随身物品信息id
	 * @return 随身物品信息实体
	 */
	public CarryGoodsInfo findCarryGoodsInfoById(String carryGoodsInfoId);
	
	/**
	 * 根据使用单id查询随身物品信息
	 * @param handlingAreaReceiptId 使用单id
	 * @return 随身物品信息实体
	 */
	public CarryGoodsInfo findCarryGoodsInfoByHandlingAreaReceiptId(String handlingAreaReceiptId);
	
	/**
	 * 根据使用单id和随身物品记录状态查询随身物品记录
	 * @param handlingAreaReceiptId 使用单id
	 * @param status 随身物品记录状态
	 * @return 随身物品记录list
	 */
	public List<CarryGoodsRecord> findCarryGoodsRecordByReceiptIdAndStatus(String handlingAreaReceiptId, String status);
}