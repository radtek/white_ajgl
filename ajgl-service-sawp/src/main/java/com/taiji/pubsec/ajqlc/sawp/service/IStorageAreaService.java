package com.taiji.pubsec.ajqlc.sawp.service;

import java.util.List;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.sawp.model.StorageArea;

/**
 * 保管区服务接口
 * @author chengkai
 */
public interface IStorageAreaService {
	
	/**
	 * 新增保管区
	 * @param storageArea 保管区实体
	 */
	public void save(StorageArea storageArea);
	
	/**
	 * 修改保管区
	 * @param storageArea 保管区实体
	 */
	public void update(StorageArea storageArea);
	
	/**
	 * 删除保管区
	 * @param storageAreaIds 保管区名称list
	 * @return 返回不能删除的保管区的名称list
	 */
	public List<String> deleteStorageAreas(String... storageAreaIds);
	
	/**
	 * 更改保管区状态
	 * @param storageAreaId 保管区id
	 * @param status 保管区状态
	 */
	public void updateStatus(String storageAreaId, String status);
	
	/**
	 * 分页查询保管区
	 * @param pageNo 页码
	 * @param pageSize 页面大小
	 * @return 返回保管区分页信息
	 */
	public Pager<StorageArea> findAllStorageAreas(int pageNo, int pageSize);
	
	/**
	 * 根据状态查询保管区
	 * @param unitId 单位id
	 * @param status 保管区状态
	 * @return 保管区list
	 */
	public List<StorageArea> findStorageAreasByStatus(String unitId, String status);
	
	/**
	 * 通过id查询保管区
	 * @param areaId 保管区id
	 * @return 保管区实体信息
	 */
	public StorageArea findById(String areaId);
	
	/**
	 * 通过单位id和保管区名称查询保管区
	 * @param unitId 单位id 
	 * @param name 保管区名称
	 * @return 返回保管区信息
	 */
	public StorageArea findByUnitIdAndName(String unitId, String name);
	
	/**
	 * 通过单位id和保管区编码查询保管区
	 * @param unitId 单位id
	 * @param code 保管区编码
	 * @return 返回保管区信息
	 */
	public StorageArea findByUnitIdAndCode(String unitId, String code);
	
	/**
	 * 通过涉案物品id查询该物品所在保管区域
	 * @param articleId 涉案物品id
	 * @return 返回保管区域
	 */
	public StorageArea findStorageAreaByArticleId(String articleId);
}
