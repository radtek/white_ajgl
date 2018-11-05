package com.taiji.pubsec.ajqlc.wrapper.dhpt.service.mock;

/**
 * 活动记录信息
 * @author xinfan
 *
 */
public interface IActivationRecordServiceSuspend {
   
	/**
	 * 同步活动记录
	 * @param registerUUID 使用单编号
	 * @param startTime 
	 * @param endTime
	 * @return
	 */
	public String syncVideoRelayHistoryInfo(String registerUUID,String startTime,String endTime);
}
