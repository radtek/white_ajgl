package com.taiji.pubsec.ajqlc.wrapper.dhpt.service;

/**
 * 活动记录信息
 * @author xinfan
 *
 */
public interface IActivationRecordService {
   
	/**
	 * 同步活动记录
	 * @param registerUUID 使用单编号
	 * @param startTime 查询开始时间
	 * @param endTime 查询结束时间
	 * @return json格式数据
	 */
	public String syncVideoRelayHistoryInfo(String registerUUID,String startTime,String endTime);
}
