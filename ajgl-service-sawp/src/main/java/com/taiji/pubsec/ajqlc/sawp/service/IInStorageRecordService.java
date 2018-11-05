package com.taiji.pubsec.ajqlc.sawp.service;

import com.taiji.persistence.service.BaseService;
import com.taiji.pubsec.ajqlc.sawp.model.InStorageRecord;

/**
 * 入库操作服务借口
 * @author wangfx
 *
 */
public interface IInStorageRecordService extends BaseService<InStorageRecord, String>{
	
	/**
	 * 根据入库单项id和储物柜id查询物品入库记录
	 * @param inStorageFormItemId 入库单项id
	 * @param lockerCode 储物柜编码
	 * @return 物品入库记录
	 */
	InStorageRecord findArticleOperationRecordByInStorageFormItemAndLocker(String inStorageFormItemId, String lockerCode);

}
