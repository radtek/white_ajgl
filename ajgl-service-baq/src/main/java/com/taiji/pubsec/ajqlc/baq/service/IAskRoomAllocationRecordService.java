package com.taiji.pubsec.ajqlc.baq.service;

import java.util.List;
import java.util.Map;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.baq.bean.RoomUseInformationBean;
import com.taiji.pubsec.ajqlc.baq.model.ActivityRoom;
import com.taiji.pubsec.ajqlc.baq.model.AskRoomAllocationRecord;

/**
	 * 活动室分配记录服务接口
	 * @author chengkai
	 *
	 */
public interface IAskRoomAllocationRecordService {
	
	/**
	 * 新增活动室分配记录
	 * @param askRoomAllocationRecord 活动室分配记录实体
	 */
	public void saveAskRoomAllocationRecord(AskRoomAllocationRecord askRoomAllocationRecord);
	
	/**
	 * 更新活动室分配记录 
	 * @param askRoomAllocationRecord 活动室分配记录实体
	 */
	public void updateAskRoomAllocationRecord(AskRoomAllocationRecord askRoomAllocationRecord);
	
	/**
	 * 根据id查找活动室分配记录
	 * @param id 活动室分配记录id
	 * @return 活动室分配记录实体
	 */
	public AskRoomAllocationRecord findAskRoomAllocationRecordById(String id);
	
	/**
	 * 根据条件查询活动室分配记录
	 * @param queryCondtidions 查询条件，Map<String, Object>类型:有
	 *  	askRoomName 活动室名称
	 *  	handlingAreaReceiptNum 使用单编号
	 *  	enterAreaReasons 进入办案区原由
	 *  	byQuestioningPeopleName 被问讯人姓名
	 *  	byQuestioningPeopleIdentifyNum 被问讯人身份证件号码
	 *  	allocationStartTime 开始时间
	 *  	allocationEndTime 结束时间
	 *    	unit 所属单位
	 *     	handlingPolice 办案民警
	 *  @return 活动室分配记录list
	 */
	public List<AskRoomAllocationRecord> findAskRoomAllocationRecordByKey(Map<String, Object> queryCondtidions); 
	
	/**
	 * 根据条件查询活动室分配记录  【分页】
	 * @param queryCondtidions 查询条件，Map<String, Object>类型:有
	 *  	askRoomName 活动室名称
	 *  	handlingAreaReceiptNum 使用单编号
	 *  	enterAreaReasons 进入办案区原由
	 *  	byQuestioningPeopleName 被问讯人姓名
	 *  	byQuestioningPeopleIdentifyNum 被问讯人身份证件号码
	 *  	allocationStartTime 开始时间
	 *  	allocationEndTime 结束时间
	 *    	unit 所属单位
	 *     	handlingPolice 办案民警
	 *      orderCondition 排序条件
	 *  @return 活动室分配记录Pager
	 */
	public Pager<AskRoomAllocationRecord> findAskRoomAllocationRecordByKeyForPage(Map<String, Object> queryCondtidions,int pageNo, int pageSize);
	
	/**
	 * 根据使用单id查找问询室分配记录
	 * @param handlingAreaReceiptId 使用单id
	 * @return 问询室分配记录list
	 */
	public List<AskRoomAllocationRecord> findAskRoomAllocationRecordByHandlingAreaReceiptId(String handlingAreaReceiptId);
	
	/**
	 * 通过使用单id查询正在使用的房间，当查询为空时返回null
	 * @param handlingAreaReceiptId 使用单id
	 * @return 房间实体
	 */
	public ActivityRoom findAskRoomByHandlingAreaReceiptId(String handlingAreaReceiptId);
	
	/**
	 * 通过使用单id将房间状态改为空闲
	 * @param handlingAreaReceiptId 使用单id
	 */
	public void updateAskRoomStatusByHandlingAreaReceiptId(String handlingAreaReceiptId);
	
	/**
	 * 通过房间ID查找最新的房间分配信息。
	 * @param activityRoomId 房间id
	 * @return 问询室分配记录
	 */
	public AskRoomAllocationRecord getLastAskRoomAllocation(String activityRoomId);
	
	/**
	 * 通过房间id查询当前使用信息
	 * @param roomId 房间id
	 * @return 返回该房间当前使用信息
	 */
	public RoomUseInformationBean getRoomInformationByRoomId(String roomId);
	
	/**
	 * 通过使用单id和房间id查询对应的最新分配记录
	 * @param receiptId 使用单id
	 * @param roomId 房间id
	 * @return 返回分配记录
	 */
	public AskRoomAllocationRecord findAskRoomAllocationRecordByReceiptIdAndRoomId(String receiptId, String roomId);
	/**
	 * 根据警号查找问询室分配记录
	 * @param policeNum 警号
	 * @return 问询室分配记录list
	 */
	public List<AskRoomAllocationRecord> findAskRoomAllocationRecordByPoliceNum(String policeNum);
}