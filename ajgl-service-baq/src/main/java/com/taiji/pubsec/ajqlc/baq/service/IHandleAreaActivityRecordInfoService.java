package com.taiji.pubsec.ajqlc.baq.service;

import java.util.List;

import com.taiji.pubsec.ajqlc.baq.model.HandleAreaActivityRecordInfo;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaActivityRecord;

/**
 * 办案区活动记录信息服务接口
 * @author chengkai
 *
 */
public interface IHandleAreaActivityRecordInfoService {
	
	/**
	 * 保存办案区活动记录信息
	 * @param handleAreaActivityRecordInfo 办案区活动记录信息实体
	 * @param haarList List<HandlingAreaActivityRecord>，办案区活动记录实体list
	 */
	public void saveHandleAreaActivityRecordInfo(HandleAreaActivityRecordInfo handleAreaActivityRecordInfo, List<HandlingAreaActivityRecord> haarList);
	
	/**
	 * 修改办案区活动记录信息
	 * @param handleAreaActivityRecordInfo 办案区活动记录信息实体
	 * @param haarList List<HandlingAreaActivityRecord>，办案区活动记录实体list
	 * @param deleteIds 要删除的办案区活动记录的id
	 */
	public void updateHandleAreaActivityRecordInfo(HandleAreaActivityRecordInfo handleAreaActivityRecordInfo, List<HandlingAreaActivityRecord> haarList, List<String> deleteIds);
	
	/**
	 * 根据办案区活动记录信息id查询办案区活动记录信息
	 * @param activityRecordInfoId 办案区活动记录信息id
	 * @return 办案区活动记录信息实体
	 */
	public HandleAreaActivityRecordInfo findHandleAreaActivityRecordInfoById(String activityRecordInfoId);
	
	/**
	 * 根据使用单id查询办案区活动记录信息
	 * @param handlingAreaReceiptId 办案区使用单id
	 * @return 办案区活动记录信息实体
	 */
	public HandleAreaActivityRecordInfo findHandleAreaActivityRecordInfoByHandlingAreaReceiptId(String handlingAreaReceiptId);
	
	/**
	 * 增加或减少笔录的数量
	 * @param activityRecordInfoId 活动记录信息id
	 * @param num 当num为正数时，增加笔录数量；当num为负数时，减少笔录数量
	 */
	public void changeRecordCount(String activityRecordInfoId, int num);
	
}
