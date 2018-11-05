package com.taiji.pubsec.ajqlc.wrapper.dhpt.service.impl;

import static org.junit.Assert.*;

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
import com.taiji.pubsec.ajqlc.wrapper.dhpt.Bean.BurnRecordBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IBurnRecordService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-basic.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class BurnRecordServiceImplTest {
	@Resource
	private IBurnRecordService burnRecordServiceImpl;
	@Test 
	public void testQueryBurnRecord() throws DahuaException {
		String caseFormCode = "393933322-22223-2222-22222";
		//String startTime = "2016-02-02 00:00:00";
		String endTime = "2017-03-07 18:00:00";
		List<BurnRecordBean> burnRecordList = burnRecordServiceImpl.queryBurnRecord(caseFormCode, null, endTime);
	    if(null != burnRecordList){
	    	for(int i = 0 ; i < burnRecordList.size() ; i++){
	    		System.out.println(burnRecordList.get(i).getUserName());
	    	}
	    }
	}

}
