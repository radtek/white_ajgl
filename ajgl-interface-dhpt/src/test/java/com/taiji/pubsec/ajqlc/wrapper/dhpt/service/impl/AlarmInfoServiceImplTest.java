package com.taiji.pubsec.ajqlc.wrapper.dhpt.service.impl;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.Bean.AlarmInfoBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IAlarmService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-basic.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class AlarmInfoServiceImplTest {
	@Resource
	private  IAlarmService  alarmInfoServiceImpl;
	@Test
	public void testQueryAlarmWristInfo() throws DahuaException {
		String strStartTime = "2017-03-07 00:00:00";
		String strEndTime = "2017-03-07 18:00:00";
		SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date startTime = sdf.parse(strStartTime);
			Date endTime = sdf.parse(strEndTime);
			String wirstId = "11";
			List<AlarmInfoBean> alarmInfoBeanlist = alarmInfoServiceImpl.queryAlarmWirstInfo(null, strEndTime, wirstId);
		    for(int i = 0 ; i < alarmInfoBeanlist.size() ; i++){
		    	System.out.println(alarmInfoBeanlist.get(i).getChannelName());
		    }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testQueryAlarmAnalyzeInfo() throws DahuaException {
		String strStartTime = "2017-03-07 00:00:00";
		String strEndTime = "2017-03-07 18:00:00";
		SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date startTime = sdf.parse(strStartTime);
			Date endTime = sdf.parse(strEndTime);
			String wirstId = "11";
			List<AlarmInfoBean> 	alarmInfoBeanlist = alarmInfoServiceImpl.queryAlarmAnalyzeInfo(null, strEndTime, wirstId);
		    for(int i = 0 ; i < alarmInfoBeanlist.size() ; i++){
		    	System.out.println(alarmInfoBeanlist.get(i).getChannelName());
		    }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testQueryAlarmInquestInfo() throws DahuaException {
		String strStartTime = "2017-03-07 00:00:00";
		String strEndTime = "2017-03-07 18:00:00";
		SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date startTime = sdf.parse(strStartTime);
			Date endTime = sdf.parse(strEndTime);
			String wirstId = "11";
			List<AlarmInfoBean> 	alarmInfoBeanlist = alarmInfoServiceImpl.queryAlarmInquestInfo(null, strEndTime, wirstId);
		    for(int i = 0 ; i < alarmInfoBeanlist.size() ; i++){
		    	System.out.println(alarmInfoBeanlist.get(i).getChannelName());
		    }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
