package com.taiji.pubsec.ajqlc.sawp.service;

import java.util.List;

import com.taiji.persistence.service.BaseService;
import com.taiji.pubsec.ajqlc.sawp.model.Storage;

/**
 * 保管位置服务接口
 * @author wangfx
 *
 */
public interface IStorageService extends BaseService<Storage, String>{
	
	/**
	 * 新增保管位置信息
	 * @param storage 保管位置
	 * @param lockerId 储物柜id
	 * @param involvedArticleId 涉案物品id
	 */
	void createStorage(Storage storage, String lockerId, String involvedArticleId);
	
	/**
	 * 根据涉案物品编码查询保管位置
	 * @param involvedArticleCode 涉案物品编码
	 * @return 保管位置列表
	 */
	List<Storage> findStoragesByInvolvedArticleCode(String involvedArticleCode);
	
	/**
	 * 通过位置id查找位置信息
	 * @param storageId 位置id
	 * @return 返回相应的位置信息
	 */
	Storage findStorageByid(String storageId);
	
	/**
	 * 修改保管位置信息
	 * @param storageId 保管位置id
	 */
	public void update(String storageId);
	
	/**
	 * 删除保管位置信息
	 * @param storageId 保管位置id
	 */
	public void delete(String storageId);
	
	/**
	 * 通过物品编号和货架位置编号查找位置信息
	 * @param articleCode 物品编号
	 * @param lockerCode 货架位置编号
	 * @return 返回位置信息
	 */
	public Storage findStorageByArticleCodeAndLockerCode(String articleCode, String lockerCode);
	
	/**
	 * 通过物品编号和货架位置id查找位置信息
	 * @param articleCode 物品编号
	 * @param lockerId 货架位置id
	 * @return 返回位置信息
	 */
	public Storage findStorageByArticleCodeAndLockerId(String articleCode, String lockerId);
	
	
}
