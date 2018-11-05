package com.taiji.pubsec.ajqlc.wrapper.dhpt.service.impl;

import static org.junit.Assert.*;

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
import com.taiji.pubsec.ajqlc.wrapper.common.bean.ResultBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IWristbandService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-basic.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class WristbandServiceImplTest {
	@Resource 
	private IWristbandService wristbandServiceImpl;
	@Test
	public void testActive() throws DahuaException {
		String wristId = "fan13579";
		String suspectId = "SQ201700209";
		String operatorId = "034628";
		ResultBean res = wristbandServiceImpl.active(wristId, suspectId, operatorId);
		System.out.println(res.isResult());
	}

	@Test
	public void testSuspend() throws DahuaException {
		String wristId = "01000439";
		ResultBean res = wristbandServiceImpl.suspend(wristId);
		System.out.println(res.isResult());
	}

	@Test
	public void testResume() throws DahuaException {
		String wristId = "01000439";
		ResultBean res = wristbandServiceImpl.resume(wristId);
		System.out.println(res.isResult());
	}

	@Test
	public void testReclaim() throws DahuaException {
		String wristId = "0300ce72";
		String suspectId = "SQ201706000";
		String operatorId = "4ca97e97-7e32-4c53-9d0f-47e4bf9f4a81";
		ResultBean res = wristbandServiceImpl.reclaim(wristId, suspectId, operatorId);
		System.out.println(res.isResult());
	}
}
