package com.taiji.pubsec.ajqlc.wrapper.dhpt.service.impl;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.ICaseFormService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-basic.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class CaseFormServiceImplTest {
    @Resource
    private ICaseFormService     caseFormServiceImpl;
	@Test
	public void testSyncCaseFormInfo() throws DahuaException {
		String formCode = "393933322-22223-2222-22222";
		String caseCode = "23423-2342342-23423234";
		String suspectId = "23423-2342342-23423234";
		List<Map> polices   = new ArrayList<Map>();
		Map masterPolice = new HashMap<String,String>();
		masterPolice.put("userUUID", "034627");
		masterPolice.put("userRole", "1");
		polices.add(masterPolice);
		Map assistPolice = new HashMap<String,String>();
		assistPolice.put("userUUID", "034628");
		assistPolice.put("userRole", "2");
		polices.add(assistPolice);
		ResultBean  res = caseFormServiceImpl.syncCaseFormInfo(formCode, caseCode, suspectId, polices);
		System.out.println(res.isResult());
	}

	@Test
	public void testSyncAddCaseInfo() throws DahuaException {
		String caseCode = "23423-2342342-23423234";
		String caseName = "同步案件基本信息";
		String unitId =   "7050c937-8a02-4215-a491-5bf06e8ff22a";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ResultBean res = caseFormServiceImpl.syncAddCaseInfo(caseCode, caseName, unitId,sdf.format(new Date()));
	}

	@Test
	public void testSyncAddSuspectInfo() throws DahuaException {
		String idcard = "140624199220222222222";
		String  name = "fanxin";
		String fromCode = "	034628";
		ResultBean res = caseFormServiceImpl.syncAddSuspectInfo(idcard, name, fromCode);
		System.out.println(res.isResult());
	}

	@Test
	public void testSyncUpdateCaseInfo() throws DahuaException {
		String caseCode = "23423-2342342-23423234";
		String caseName = "同步案件基本信息_修改测试";
		String unitId =   "01";
		String strDate = "2017-03-10 10:00:00";
		ResultBean res = caseFormServiceImpl.syncUpdateCaseInfo(caseCode, caseName, unitId,strDate);
	}

	@Test
	public void testSyncUpdateSuspectInfo() throws DahuaException {
		String idcard = "140624199220222234334";
		String  name = "fanxin_修改测试";
		String fromCode = "23423-2342342-23423234";
		ResultBean res = caseFormServiceImpl.syncAddSuspectInfo(idcard, name, fromCode);
		System.out.println(res.isResult());
	}

	@Test
	public void testAllocateRoom() throws DahuaException {
		String formCode = "393933322-22223-2222-22222";
		String caseCode	= "23423-2342342-23423234";
		String suspectId = "23423-2342342-23423234";
		String roomId = "a786d26cc2c4464aa1ec58998c7659f3";
		String mainUserId = "034627";
		String assistUserId = "034628";
		ResultBean res = caseFormServiceImpl.allocateRoom(formCode, caseCode, suspectId, roomId, mainUserId, assistUserId);
	    System.out.println(res.isResult());
	}

}
