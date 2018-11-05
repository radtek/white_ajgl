package com.taiji.pubsec.ajqlc.baq.service;

import java.util.List;

import com.taiji.pubsec.ajqlc.baq.model.ActivityRecord;

/**
 * 活动记录服务接口
 * @author chengkai
 *
 */
public interface IActivityRecordService {

	/**
	 * 通过使用单编号查询对应的活动记录list
	 * @param receiptCode 使用单编号
	 * @return 返回活动记录list
	 */
	public List<ActivityRecord> findActivityRecordsByReceiptCode(String receiptCode);
	
    /**
	 * 通过使用单编号 和 上级id 查询对应的活动记录list 按时间升序排序
	 * @param receiptCode 使用单编号
	 * @return 返回活动记录list
	 */
	public List<ActivityRecord> findActivityRecordsByReceiptCodeAndSupRecordId(String receiptCode,String supRecordId);	
	
	/**
	 * 新增活动记录（大阶段）
	 * @param record 活动记录
	 */
	public void save(ActivityRecord record);

	/**
    * 保存活动记录 
    * @param activationRecord
    * @param receiptNum 使用单编号
    * @return 返回活动记录ID
    */
	public String saveActivationRecord(String result, String receiptNum);
	/**
	 * 更新活动记录
	 * @param activationRecord
	 * @return
	 */
	public void updateActivationRecord(ActivityRecord activationRecord);
	
	/**
	 * 新增或更新“讯询问过程”大阶段的下级活动记录
	 * @param activationRecord 活动记录
	 * @param bigStageDescription 大阶段名称
	 * @param receiptNum 使用单编号
	 * @return 大阶段
	 */
	public ActivityRecord saveOrUpdateRecordOfAskProcess(ActivityRecord activationRecord, String bigStageDescription, String receiptNum);
	
	/**
	 * 查询大阶段
	 * @param receiptNum 使用单编号
	 * @param bigStageDescription 大阶段描述
	 */
	public ActivityRecord findActivityRecordsByHarCodeAndBigStageDescription(String receiptNum, String bigStageDescription);
	
	/**
	 * 新增或更新“离开过程”大阶段的下级活动记录
	 * @param activationRecord 子阶段记录
	 * @param bigStageDescription 大阶段描述
	 * @param receiptNum 使用单号
	 */
	public void saveOrUpdateRecordOfLeaveRegister(ActivityRecord activationRecord, String bigStageDescription, String receiptNum);

	/**
	 * 查询网格id集合
	 */
	public List<String> findActivityRecordsGridId();
}
