package com.taiji.pubsec.ajqlc.baq.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajqlc.baq.model.AlarmRecord;
import com.taiji.pubsec.ajqlc.baq.service.IAlarmRecordService;
import com.taiji.pubsec.ajqlc.util.ParamMapUtil;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.Bean.AlarmInfoBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;

@Service("alarmRecordService")
public class AlarmServiceImpl implements IAlarmRecordService {

	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;

	@SuppressWarnings("unchecked")
	@Override
	public List<AlarmInfoBean> queryMyAlarmRecord(Date startTime, Date endTime, String paramId,String typeCode) {
		List<AlarmRecord> list = new ArrayList<AlarmRecord>();
		StringBuilder xql = new StringBuilder("select a from AlarmRecord as a where 1 = 1 ");
		
		Map<String, Object> xqlMap = new HashMap<String, Object>();
		
		if(ParamMapUtil.isNotBlank(startTime)){
			xql.append(" and a.alarmDate >= :startTime");
			xqlMap.put("startTime", startTime);
		}
		if(ParamMapUtil.isNotBlank(endTime)){
			xql.append(" and a.alarmDate <= :endTime");
			xqlMap.put("endTime", endTime);
		}
		if(ParamMapUtil.isNotBlank(paramId)){
			xql.append(" and a.askDeviceId = :paramId");
			xqlMap.put("paramId", paramId);
		}
		if(ParamMapUtil.isNotBlank(typeCode)){
			xql.append(" and a.typeCode = :typeCode");
			xqlMap.put("typeCode", typeCode);
		}
		xql.append(" order by a.alarmDate desc");
		list = this.dao.findAllByParams(AlarmRecord.class, xql.toString(), xqlMap); 
		return modelBybean(list);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String saveAlarmRecord(AlarmRecord alarmRecord) {
		this.dao.save(alarmRecord);
        return alarmRecord.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AlarmInfoBean> queryMyAlarmRecordByalarmType(String alarmType) throws DahuaException {
		String xql = "select a from AlarmRecord as a where a.alarmType = ?   order by a.alarmDate desc ";
		List<AlarmRecord> modelList = dao.findAllByParams(AlarmRecord.class, xql, new Object[]{alarmType});
		if(!modelList.isEmpty()){
			return modelBybean(modelList);
		}
		return null ;
	}


	private List<AlarmInfoBean> modelBybean(List<AlarmRecord> List) {
		List<AlarmInfoBean> beanList = new ArrayList<AlarmInfoBean>();
		for (AlarmRecord alarmRecord : List) {
			AlarmInfoBean armBean = new AlarmInfoBean();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			armBean.setAlarmDate(alarmRecord.getAlarmDate()==null ? "" : sdf.format(alarmRecord.getAlarmDate()));
			armBean.setAlarmType(alarmRecord.getAlarmType() == null ? "" : alarmRecord.getAlarmType());
			armBean.setBraceletPhysicalId(alarmRecord.getTypeCode() == null ? "" : alarmRecord.getTypeCode());
			armBean.setChannelCode(alarmRecord.getChannelCode() == null ? "" : alarmRecord.getChannelCode());
			armBean.setChannelName(alarmRecord.getChannelName() == null ? "" : alarmRecord.getChannelName());
			armBean.setDeviceCode(alarmRecord.getDeviceCode() == null ? "" : alarmRecord.getDeviceCode());
			armBean.setDeviceName(alarmRecord.getDeviceName() == null ? "" : alarmRecord.getDeviceName());
			beanList.add(armBean);
		}
		return beanList ;
	}
}
