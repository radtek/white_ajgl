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
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.ILocationCardService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-basic.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class LocationCardServiceImplTest {
	@Resource
	private ILocationCardService locationCardServiceImpl;
	@Test
	public void testActive() throws DahuaException {//激活定位卡
		String id = "03001851";
		String policeId = "042018";
		String operatorId = "4ca97e97-7e32-4c53-9d0f-47e4bf9f4a81";
		ResultBean res = locationCardServiceImpl.active(id, policeId, operatorId);
		System.out.println(res.isResult());
	}

	@Test
	public void testSuspend() throws DahuaException {//挂起定位卡
		String id = "222222222222";
		ResultBean res = locationCardServiceImpl.suspend(id);
		System.out.println(res.isResult());
	}

	@Test
	public void testResume() throws DahuaException {//取消挂起
		String id = "222222222222";
		ResultBean res = locationCardServiceImpl.resume(id);
		System.out.println(res.isResult());
	}

	@Test
	public void testReclaim() throws DahuaException {
		String id = "ceshi2";
		String policeId = "741528";
		String operatorId = "4ca97e97-7e32-4c53-9d0f-47e4bf9f4a81";
		ResultBean res = locationCardServiceImpl.reclaim(id, policeId, operatorId);
		System.out.println(res.isResult());
	}

}
