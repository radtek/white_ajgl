package com.taiji.pubsec.ajqlc.baq.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
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
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.annotation.ExpectedDatabases;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.test.TestBase;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class HandlingAreaReceiptServiceTestCase extends TestBase {
	
	@Resource
	private IHandlingAreaReceiptService handlingAreaReceiptService;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@Test
	@DatabaseSetup("classpath:dataset/handlingAreaReceipt/testSaveHandlingAreaReceipt-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/handlingAreaReceipt/testSaveHandlingAreaReceipt-expected.xml", table = "t_baq_baqsyd", query = "select cjr, cjbm, zxxgr_id, zt from t_baq_baqsyd where cjsj is not null and xgsj is not null", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void testSaveHandlingAreaReceipt () throws ParseException {
		setLoinInfoAdmin();
		
		HandlingAreaReceipt handlingAreaReceipt = new HandlingAreaReceipt ();
		handlingAreaReceipt.setCreatePeopleId("cjr");
		handlingAreaReceipt.setCreateUnitId("cjbm");
		handlingAreaReceipt.setCreatedTime(DateUtils.parseDate("2009-01-01 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		handlingAreaReceipt.setUpdatedTime(DateUtils.parseDate("2009-01-01 10:10:10", "yyyy-MM-dd HH:mm:ss"));
//		handlingAreaReceipt.setModifyPeople((Person) this.dao.findById(Person.class, "personId"));
		handlingAreaReceipt.setStatus("zt");
		this.handlingAreaReceiptService.saveHandlingAreaReceipt(handlingAreaReceipt);
		this.dao.flush();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/handlingAreaReceipt/testDeleteHandlingAreaReceipt-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/handlingAreaReceipt/testDeleteHandlingAreaReceipt-expected.xml", table = "t_baq_baqsyd", query = "select cjr, cjbm, zxxgr_id, zt from t_baq_baqsyd where cjsj is not null and xgsj is not null", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void testDeleteHandlingAreaReceipt () {
		setLoinInfoAdmin();
		
		this.handlingAreaReceiptService.deleteHandlingAreaReceipt("sydId");
		this.dao.flush();
		Assert.assertEquals(0, this.dao.findAll(HandlingAreaReceipt.class).size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/handlingAreaReceipt/testUpdateHandlingAreaReceipt-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/handlingAreaReceipt/testUpdateHandlingAreaReceipt-expected.xml", table = "t_baq_baqsyd", query = "select id, cjr, cjbm, zxxgr_id, zt from t_baq_baqsyd where cjsj is not null and xgsj is not null", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void testUpdateHandlingAreaReceipt () {
		setLoinInfoAdmin();
		
		HandlingAreaReceipt handlingAreaReceipt = (HandlingAreaReceipt) this.dao.findById(HandlingAreaReceipt.class, "sydId");
		handlingAreaReceipt.setCreatePeopleId("newcjr");
		handlingAreaReceipt.setCreateUnitId("newcjbm");
//		handlingAreaReceipt.setModifyPeople((Person) this.dao.findById(Person.class, "personId"));
		handlingAreaReceipt.setStatus("newzt");
		this.handlingAreaReceiptService.updateHandlingAreaReceipt(handlingAreaReceipt);
		this.dao.flush();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/handlingAreaReceipt/testFindHandlingAreaReceiptById-setup.xml")
	public void testFindHandlingAreaReceiptById () {
		HandlingAreaReceipt handlingAreaReceipt = (HandlingAreaReceipt) this.dao.findById(HandlingAreaReceipt.class, "sydId");
		Assert.assertEquals("cjr", handlingAreaReceipt.getCreatePeopleId());
		Assert.assertNotNull(handlingAreaReceipt.getCreatedTime());
		Assert.assertEquals("cjbm", handlingAreaReceipt.getCreateUnitId());
		Assert.assertEquals("personId", handlingAreaReceipt.getModifyPeople().getId());
		Assert.assertEquals("zt", handlingAreaReceipt.getStatus());
		Assert.assertNotNull(handlingAreaReceipt.getUpdatedTime());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/handlingAreaReceipt/testFindHandlingAreaReceiptByKey-setup.xml")
	public void testFindHandlingAreaReceiptByKey () {
//		Assert.assertEquals(1, this.handlingAreaReceiptService.findHandlingAreaReceiptByKey("cjbm1", 0, 10).getPageList().size());
//		Assert.assertEquals(2, this.handlingAreaReceiptService.findHandlingAreaReceiptByKey("cjbm", 0, 10).getPageList().size());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/handlingAreaReceipt/testAcquireNum-setup.xml")
	public void testAcquireNum () {
		String str = this.handlingAreaReceiptService.acquireNum();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
        String formatDate = sdf.format(date);
		Assert.assertEquals("SQ" + formatDate + "00001", str);
		System.out.println(str);
		
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/handlingAreaReceipt/findHandlingAreaReceiptByKey-setup.xml")
	public void findHandlingAreaReceiptByKey () throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> queryConditions = new HashMap<String, Object>();
		queryConditions.put("unitId", "cjbm");
		queryConditions.put("handlingAreaReceiptNum", "sydbh");
		queryConditions.put("askRoomName", "wxsId");
		queryConditions.put("handlingPolice", "bamj");
		queryConditions.put("haveRecord", "0000000007001");
		queryConditions.put("status", "zt");
		queryConditions.put("causeOfLawCase", "ay");
		queryConditions.put("byQuestioningPeopleName", "bwxrxm");
		queryConditions.put("byQuestioningPeopleIdentifyNum", "sfzjhm");
		queryConditions.put("lawCase", "ssaj");
		queryConditions.put("enterIntoTimeStart", sdf.parse("2009-01-01 10:10:10"));
		queryConditions.put("enterIntoTimeEnd", sdf.parse("2020-01-10 10:10:10"));
		queryConditions.put("modifyPeopleName", "123");
		
		Pager<HandlingAreaReceipt> harPager = handlingAreaReceiptService.findHandlingAreaReceiptByKey(queryConditions, 0, 10);
		
		Assert.assertEquals(1, harPager.getTotalNum());
		
		Map<String, Object> queryConditions1 = new HashMap<String, Object>();
		queryConditions1.put("unitId", "cjbm");
		queryConditions1.put("handlingAreaReceiptNum", "");
		queryConditions1.put("askRoomName", null);
		queryConditions1.put("handlingPolice", null);
		queryConditions1.put("haveRecord", null);
		queryConditions1.put("status", null);
		queryConditions1.put("causeOfLawCase", null);
		queryConditions1.put("byQuestioningPeopleName", null);
		queryConditions1.put("byQuestioningPeopleIdentifyNum", null);
		queryConditions1.put("lawCase", null);
		queryConditions1.put("enterIntoTimeStart", null);
		queryConditions1.put("enterIntoTimeEnd", null);
		queryConditions1.put("modifyPeopleName", null);
		
		Pager<HandlingAreaReceipt> harPager1 = handlingAreaReceiptService.findHandlingAreaReceiptByKey(queryConditions1, 0, 10);
		org.junit.Assert.assertEquals(2, harPager1.getTotalNum());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/handlingAreaReceipt/finishHandlingAreaReceipt-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/handlingAreaReceipt/finishHandlingAreaReceipt-expected.xml", table = "t_baq_baqsyd", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void finishHandlingAreaReceipt () {
		setLoinInfoAdmin();
		
		handlingAreaReceiptService.finishHandlingAreaReceipt("sydId");
		this.dao.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/handlingAreaReceipt/testFindHandlingAreaReceiptByConditions-setup.xml")
	public void testFindHandlingAreaReceiptByConditions () {
		Assert.assertEquals(3, this.handlingAreaReceiptService.findHandlingAreaReceiptByConditions(new HashMap<String, Object>()).size());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/handlingAreaReceipt/findAccountsByRoleId-setup.xml")
	public void findAccountsByRoleId() {
		List<String> strLst = handlingAreaReceiptService.findAccountsByRoleId("jsId");
		Assert.assertEquals(2, strLst.size());
	}
}
