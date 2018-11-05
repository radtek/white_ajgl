package com.taiji.pubsec.ajqlc.baq.service;


import java.util.List;
import java.util.Map;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.baq.model.AskRoomIllegalRecord;

/**
	 * 活动室违规记录服务接口
	 * @author XIEHF
	 *
	 */
public interface IAskRoomIllegalRecordService {
	
	/**
	 * 新增违规记录
	 * @param askRoomIllegalRecord 活动室违规记录实体
	 */
	public void saveAskRoomIllegalRecord(AskRoomIllegalRecord askRoomIllegalRecord);
	
	/**
	 * 违规记录分页查询
	 * @return Pager<AskRoomIllegalRecord> 活动室违规记录实体List
	 */
	public Pager<AskRoomIllegalRecord> findAskRoomUseAbnormalRecordByKey(Map<String, String> queryCondtidions,int pageNo, int pageSize);
}