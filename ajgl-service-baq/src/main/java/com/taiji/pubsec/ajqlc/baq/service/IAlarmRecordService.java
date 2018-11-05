package com.taiji.pubsec.ajqlc.baq.service;

import java.util.Date;
import java.util.List;

import com.taiji.pubsec.ajqlc.baq.model.AlarmRecord;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.Bean.AlarmInfoBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;

/**
 * 本地预警消息接口
 * @author lenovo
 *
 */
public interface IAlarmRecordService {
	
	/**
	 * 查询预警消息
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param paramId ID
	 * @param typeCode 类型编码
	 * @return AlarmInfoBean 预警消息Bean的List
	 */
	public List<AlarmInfoBean> queryMyAlarmRecord(Date startTime,Date endTime,String paramId,String typeCode);
	
	/**
	 * 根据报警类型查询预警消息
	 * @param alarmType 报警类型
	 * @return AlarmInfoBean 预警消息Bean的List
	 */
	public List<AlarmInfoBean> queryMyAlarmRecordByalarmType(String alarmType);
	
	/**
	 * 保存预警消息
	 * @param AlarmRecord
	 * @return
	 */
	public String saveAlarmRecord(AlarmRecord alarmRecord) ;
	

}
