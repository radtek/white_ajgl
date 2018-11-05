package com.taiji.pubsec.ajqlc.baq.service;

import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceiptToPoliceInfo;

/**
 * 办案区使用单和民警关系服务接口
 * @author huangda
 *
 */
public interface IHandlingAreaReceiptToPoliceInfoService {

	/**
	 * 查找关系
	 * @param entity
	 * @return
	 */
	public HandlingAreaReceiptToPoliceInfo findByHarIdAndPoliceInfoId(String harId, String policeInfoId);
	
	/**
	 * 保存关系
	 * @param entity
	 * @return
	 */
	public String save(HandlingAreaReceiptToPoliceInfo entity);
	
	/**
	 * 删除关系
	 * @param id
	 */
	public void delete(String id);
	
}
