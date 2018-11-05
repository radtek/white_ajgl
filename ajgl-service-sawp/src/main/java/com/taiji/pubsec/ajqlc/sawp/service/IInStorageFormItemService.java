package com.taiji.pubsec.ajqlc.sawp.service;

import com.taiji.persistence.service.BaseService;
import com.taiji.pubsec.ajqlc.sawp.model.InStorageFormItem;

/**
 * 入库单项接口
 * @author wangfx
 *
 */
public interface IInStorageFormItemService extends BaseService<InStorageFormItem, String> {
	
	/**
	 * 通过入库单项id查找入库单项
	 * @param itemId 入库单项id
	 * @return 返回入库单项信息
	 */
	public InStorageFormItem findItemById(String itemId);
}
