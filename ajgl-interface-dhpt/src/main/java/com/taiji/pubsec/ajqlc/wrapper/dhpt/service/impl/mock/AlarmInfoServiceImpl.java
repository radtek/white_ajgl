package com.taiji.pubsec.ajqlc.wrapper.dhpt.service.impl.mock;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.pubsec.ajqlc.exceptionLog.service.IExceptionLogService;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.Bean.AlarmInfoBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.common.service.ConstantBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IAlarmService;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.mock.IAlarmServiceSuspend;

@Service("alarmInfoServiceImpl-suspend")
@Transactional(rollbackFor = Exception.class)
public class AlarmInfoServiceImpl  implements IAlarmServiceSuspend{

	@Resource
	private IExceptionLogService exceptionLogService;
	

	@Override
	public List<AlarmInfoBean> queryAlarmWirstInfo(String startTime,
			String endTime, String paramId) throws DahuaException {
		List <Object> params = new ArrayList<Object>();
		params.add(ConstantBean.YJLX_SHBJ);
		params.add(startTime);
		params.add(endTime);
		params.add(paramId);
		return queryAlarmInfo(params);
	}

	@Override
	public List<AlarmInfoBean> queryAlarmAnalyzeInfo(String startTime,
			String endTime, String paramId) throws DahuaException {
		List <Object> params = new ArrayList<Object>();
		params.add(ConstantBean.YJLX_ZNFX);
		params.add(startTime);
		params.add(endTime);
		params.add(paramId);
		return queryAlarmInfo(params);
	}

	@Override
	public List<AlarmInfoBean> queryAlarmInquestInfo(String startTime,
			String endTime, String paramId) throws DahuaException {
		List <Object> params = new ArrayList<Object>();
		params.add(ConstantBean.YJLX_SXSSB);
		params.add(startTime);
		params.add(endTime);
		params.add(paramId);
		return queryAlarmInfo(params);
		
	}
	
	private List<AlarmInfoBean> queryAlarmInfo(List <Object> params) throws DahuaException{
		List<AlarmInfoBean> alarms = new ArrayList<AlarmInfoBean>();
		AlarmInfoBean alarmInfoBean = new AlarmInfoBean();
		alarmInfoBean.setAlarmDate("2017-06-13 13:56:33");
		alarmInfoBean.setAlarmType("2013");
		alarmInfoBean.setBraceletPhysicalId((String)params.get(2));
		alarmInfoBean.setChannelCode("1000008$1$0$0");
		alarmInfoBean.setDeviceCode("1000002");
		alarmInfoBean.setDeviceName("信息采集RFID");
		alarms.add(alarmInfoBean);
		
		
		AlarmInfoBean alarmInfoBean1 = new AlarmInfoBean();
		alarmInfoBean1.setAlarmDate("2017-06-13 13:56:33");
		alarmInfoBean1.setAlarmType("2013");
		alarmInfoBean1.setBraceletPhysicalId((String)params.get(2));
		alarmInfoBean1.setChannelCode("000005$1$0$0");
		alarmInfoBean1.setDeviceCode("1000001");
		alarmInfoBean1.setDeviceName("审讯室RFID");
		alarms.add(alarmInfoBean);
		
		AlarmInfoBean alarmInfoBean2 = new AlarmInfoBean();
		alarmInfoBean2.setAlarmDate("2017-06-13 13:56:33");
		alarmInfoBean2.setAlarmType("2013");
		alarmInfoBean2.setBraceletPhysicalId((String)params.get(2));
		alarmInfoBean2.setChannelCode("1000005$1$0$0");
		alarmInfoBean2.setDeviceCode("1000002");
		alarmInfoBean2.setDeviceName("信息采集RFID");
		alarms.add(alarmInfoBean);
		return alarms;
	}

}
