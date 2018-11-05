package com.taiji.pubsec.ajqlc.baq.service;

import java.text.ParseException;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
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
import com.taiji.pubsec.ajqlc.baq.model.BasicCase;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.test.TestBase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class BasicCaseServiceTestCase extends TestBase {
	
	@Resource
	private IBasicCaseService basicCaseService;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@Test
	@DatabaseSetup("classpath:dataset/basicCase/testCreateBasicCase-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/basicCase/testCreateBasicCase-expected.xml", table = "t_baq_jbqk", 
						 assertionMode = DatabaseAssertionMode.NON_STRICT),
						 @ExpectedDatabase(value = "classpath:dataset/basicCase/testCreateBasicCase-expected.xml", table = "t_baq_baqsyd", 
						 assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void testCreateBasicCase() throws ParseException {
		setLoinInfoAdmin();
		
		HandlingAreaReceipt handlingAreaReceipt = new HandlingAreaReceipt();
//		handlingAreaReceipt.setModifyPeople((Person) this.dao.findById(Person.class, "personId"));
		handlingAreaReceipt.setCreatePeopleId("cjrId");
		handlingAreaReceipt.setCreateUnitId("cjdwId");
		handlingAreaReceipt.setCreatedTime(DateUtils.parseDate("2016-01-01 12:00:00", "yyyy-MM-dd HH:mm:ss"));
		handlingAreaReceipt.setUpdatedTime(DateUtils.parseDate("2016-01-01 12:00:00", "yyyy-MM-dd HH:mm:ss"));
		handlingAreaReceipt.setStatus("zt");
		BasicCase basicCase = new BasicCase();
		basicCase.setHandlingAreaReceipt(handlingAreaReceipt);
		basicCase.setBirthday(DateUtils.parseDateStrictly("2016-01-01 12:00:00", "yyyy-MM-dd HH:mm:ss"));
		basicCase.setByQuestioningPeopleAddress("jtzz");
		basicCase.setByQuestioningPeopleIdentifyNum("sfzjhm");
		basicCase.setByQuestioningPeopleIdentifyType("sfzjzl");
		basicCase.setByQuestioningPeopleName("bwxrxm");
		basicCase.setByQuestioningPeopleTelphoneNumber("bwxrlxfs");
		basicCase.setCauseOfLawCase("ay");
		basicCase.setEnterIntoTime(DateUtils.parseDateStrictly("2016-01-01 12:00:00", "yyyy-MM-dd HH:mm:ss"));
		basicCase.setHandlingPolice("bamj");
		basicCase.setSex("xb");
		basicCase.setHandlingAreaReceiptNum("sydbh");
		basicCase.setLawCase("ssaj");
		basicCase.setUpdatedTime(DateUtils.parseDate("2016-01-01 12:00:00", "yyyy-MM-dd HH:mm:ss"));
		basicCase.setModifyPeople(handlingAreaReceipt.getModifyPeople());
		this.basicCaseService.createBasicCase(basicCase);
		this.dao.flush();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/basicCase/testUpdateBasicCase-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/basicCase/testCreateBasicCase-expected.xml", table = "t_baq_jbqk",
						 query = "select id, csrq, jtzz, sfzjhm, sfzjzl, bwxrxm, bwxrlxfs, ay, qtay, jrbaqsj, bamj, xb, sydbh, ssaj, zxxgr_id from t_baq_jbqk where id is not null and baqsyd_id is not null and xgsj is not null",
						 assertionMode = DatabaseAssertionMode.NON_STRICT),
						 @ExpectedDatabase(value = "classpath:dataset/basicCase/testUpdateBasicCase-expected.xml", table = "t_baq_baqsyd",
						 query = "select id, zxxgr_id, cjr, cjsj, cjbm, zt from t_baq_baqsyd where id is not null and xgsj is not null", 
						 assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void testUpdateBasicCase () throws ParseException {
		setLoinInfoAdmin();
		
		BasicCase basicCase = (BasicCase) this.dao.findById(BasicCase.class, "jbqkId");
		basicCase.setBirthday(DateUtils.parseDateStrictly("2016-01-02 12:00:00", "yyyy-MM-dd HH:mm:ss"));
		basicCase.setByQuestioningPeopleAddress("newjtzz");
		basicCase.setByQuestioningPeopleIdentifyNum("newsfzjhm");
		basicCase.setByQuestioningPeopleIdentifyType("newsfzjzl");
		basicCase.setByQuestioningPeopleName("newbwxrxm");
		basicCase.setByQuestioningPeopleTelphoneNumber("newbwxrlxfs");
		basicCase.setCauseOfLawCase("neway");
		basicCase.setEnterIntoTime(DateUtils.parseDateStrictly("2016-01-02 12:00:00", "yyyy-MM-dd HH:mm:ss"));
		basicCase.setHandlingPolice("newbamj");
		basicCase.setSex("newxb");
		basicCase.setHandlingAreaReceiptNum("newsydbh");
		basicCase.setLawCase("newssaj");
//		basicCase.setModifyPeople((Person) this.dao.findById(Person.class, "personId"));
		HandlingAreaReceipt handlingAreaReceipt = basicCase.getHandlingAreaReceipt();
		handlingAreaReceipt.setModifyPeople(basicCase.getModifyPeople());
		basicCase.setHandlingAreaReceipt(handlingAreaReceipt);
		this.basicCaseService.updateBasicCase(basicCase);
		this.dao.flush();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/basicCase/testFindById-setup.xml")
	public void testFindById () throws ParseException {
		BasicCase basicCase = (BasicCase) this.dao.findById(BasicCase.class, "jbqkId");
		Assert.assertEquals(DateUtils.parseDateStrictly("2016-01-01 12:00:00", "yyyy-MM-dd HH:mm:ss"), basicCase.getBirthday());
		Assert.assertEquals("jtzz", basicCase.getByQuestioningPeopleAddress());
		Assert.assertEquals("sfzjhm", basicCase.getByQuestioningPeopleIdentifyNum());
		Assert.assertEquals("sfzjzl", basicCase.getByQuestioningPeopleIdentifyType());
		Assert.assertEquals("bwxrxm", basicCase.getByQuestioningPeopleName());
		Assert.assertEquals("bwxrlxfs", basicCase.getByQuestioningPeopleTelphoneNumber());
		Assert.assertEquals("ay", basicCase.getCauseOfLawCase());
		Assert.assertEquals(DateUtils.parseDateStrictly("2016-01-01 12:00:00", "yyyy-MM-dd HH:mm:ss"), basicCase.getEnterIntoTime());
		Assert.assertEquals("sydbh", basicCase.getHandlingAreaReceiptNum());
		Assert.assertEquals("bamj", basicCase.getHandlingPolice());
		Assert.assertEquals("ssaj", basicCase.getLawCase());
		Assert.assertEquals("personId", basicCase.getModifyPeople().getId());
		Assert.assertNotNull(basicCase.getUpdatedTime());
		Assert.assertEquals("xb", basicCase.getSex());
		Assert.assertEquals("cjrId", basicCase.getHandlingAreaReceipt().getCreatePeopleId());
		Assert.assertEquals("cjdwId", basicCase.getHandlingAreaReceipt().getCreateUnitId());
		Assert.assertEquals("personId", basicCase.getHandlingAreaReceipt().getModifyPeople().getId());
		Assert.assertNotNull(basicCase.getHandlingAreaReceipt().getUpdatedTime());
		Assert.assertEquals("zt", basicCase.getHandlingAreaReceipt().getStatus());
	}

}
