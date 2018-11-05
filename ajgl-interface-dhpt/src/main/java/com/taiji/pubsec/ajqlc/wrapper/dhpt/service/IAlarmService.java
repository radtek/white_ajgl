package com.taiji.pubsec.ajqlc.wrapper.dhpt.service;
import java.util.List;

import com.taiji.pubsec.ajqlc.wrapper.dhpt.Bean.AlarmInfoBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
/**
 * 预警消息接口
 * @author xinfan
 *
 */
public interface IAlarmService  {
	/**
	 * 查询手环预警消息。
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param paramId 手环ID
	 * @return AlarmInfoBean 预警消息Bean的List
	 */
	public List<AlarmInfoBean> queryAlarmWirstInfo(String startTime,String endTime,String paramId) throws DahuaException;
	/**
	 * 查询智能分析服务器预警消息。
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param paramId 智能分析服务器ID
	 * @return AlarmInfoBean 预警消息Bean的List
	 */
	public List<AlarmInfoBean> queryAlarmAnalyzeInfo(String startTime,String endTime,String paramId) throws DahuaException;
	/**
	 * 查询审讯室预警消息。
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param paramId 审讯室Id
	 * @return AlarmInfoBean 预警消息Bean的List
	 */
	public List<AlarmInfoBean> queryAlarmInquestInfo(String startTime,String endTime,String paramId) throws DahuaException;
}
