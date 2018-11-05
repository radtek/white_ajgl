package com.taiji.pubsec.ajqlc.sawp.service;

import java.util.List;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageArea;

/**
 * 暂存储物区 servie				
 * @author xinfan
 *
 */
public interface ITemporaryStorageAreaService {
	/**
	 * 保存 暂存储物区
	 * @param area
	 * @return
	 */
	String  saveTemporaryStorageArea(TemporaryStorageArea area);
	/**
	 * 更新暂存储物区
	 * @param area
	 */
	void updateTemporaryStorageArea(TemporaryStorageArea area);
	
	 /**
	   * 根据Id查找 储物区
	   * @param id
	   * @return
	   */
	TemporaryStorageArea findTemporaryStorageAreaById(String id);
	  /**
	   * 根据编码查找储物区
	   * @param code
	   * @return
	   */
	TemporaryStorageArea findTemporaryStorageAreaByCode(String code);
	
	/**
	 * 根据名称查找储物区
	 * @param name
	 * @return
	 */
	TemporaryStorageArea findTemporaryStorageAreaByName(String name);
	 /**
	  * 根据状态查找所有储物区 按编码升序排
	  * @param status
	  * @return
	  */
	 List<TemporaryStorageArea> findAllTemporaryStorageArea(String status);
	 /**
	  * 分页查找所有的储物区 按编码升序排
	  * @param pageNo
	  * @param pageSize
	  * @return
	  */
	 Pager<TemporaryStorageArea> findAllTemporaryStorageAreaByPage(int pageNo, int pageSize);
	 /**
	  * 删除储物区
	  * @param id
	  */
	 void deleteTemporaryStorageArea(String  id); 
	 /**
	  * 更新储物区状态
	  * @param id
	  * @param status
	  */
	 void updateTemporaryStorageAreaStatus(String id ,String status);
		
}
