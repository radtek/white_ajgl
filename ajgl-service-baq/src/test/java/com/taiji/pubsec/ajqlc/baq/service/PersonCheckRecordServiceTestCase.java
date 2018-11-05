package com.taiji.pubsec.ajqlc.baq.service;

import java.text.ParseException;

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
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.baq.model.PersonCheckRecord;
import com.taiji.pubsec.ajqlc.test.TestBase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class PersonCheckRecordServiceTestCase extends TestBase {
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@Resource
	private IPersonCheckRecordService personCheckRecordService;
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/personCheckRecord/testSavePersonCheckRecord-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/personCheckRecord/testSavePersonCheckRecord-expected.xml", table = "t_baq_rsjcjl", 
	 					 query = "select jcqk, jcmj, jzr, bjcr, zxxgr_id, xgsj, zszz, baqsyd_id from t_baq_rsjcjl where id is not null", 
	 					 assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void testSavePersonCheckRecord () throws ParseException {
		setLoinInfoAdmin();
		
		HandlingAreaReceipt handlingAreaReceipt = (HandlingAreaReceipt) this.dao.findById(HandlingAreaReceipt.class, "sydId");
		PersonCheckRecord personCheckRecord = new PersonCheckRecord ();
		personCheckRecord.setHandlingAreaReceipt(handlingAreaReceipt);
		personCheckRecord.setSelfReportedSymptoms("newzszz");
		personCheckRecord.setCheckCondition("newjcqk");
		personCheckRecord.setCheckPolice("newjcmj");
		personCheckRecord.setEyewitness("newjzr");
		personCheckRecord.setIsCheckedPerson("newbjcr");
		personCheckRecord.setUpdatedTime(DateUtils.parseDate("2016-01-02 12:00:00", "yyyy-MM-dd HH:mm:ss"));
//		personCheckRecord.setModifyPeople((Person) this.dao.findById(Person.class, "personId"));
		personCheckRecordService.savePersonCheckRecord(personCheckRecord);
		this.dao.flush();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/personCheckRecord/testUpdatePersonCheckRecord-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/personCheckRecord/testUpdatePersonCheckRecord-expected.xml", table = "t_baq_rsjcjl", 
	 					 query = "select id, jcqk, jcmj, jzr, bjcr, V, xgsj, zszz, baqsyd_id from t_baq_rsjcjl where id is not null", 
	 					 assertionMode = DatabaseAssertionMode.NON_STRICT),
						 @ExpectedDatabase(value = "classpath:dataset/personCheckRecord/testUpdatePersonCheckRecord-expected.xml", table = "t_baq_baqsyd", 
						 query = "select id, zxxgr_id, cjr, cjsj, cjbm, zt from t_baq_baqsyd where id is not null ", 
						 assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void testUpdatePersonCheckRecord () throws ParseException {
		setLoinInfoAdmin();
		
		PersonCheckRecord personCheckRecord = (PersonCheckRecord) this.dao.findById(PersonCheckRecord.class, "jcjlId");
		personCheckRecord.setSelfReportedSymptoms("newzszz");
		personCheckRecord.setCheckCondition("newjcqk");
		personCheckRecord.setCheckPolice("newjcmj");
		personCheckRecord.setEyewitness("newjzr");
		personCheckRecord.setIsCheckedPerson("newbjcr");
//		personCheckRecord.setModifyPeople((Person) this.dao.findById(Person.class, "personId"));
		HandlingAreaReceipt handlingAreaReceipt = personCheckRecord.getHandlingAreaReceipt();
		handlingAreaReceipt.setModifyPeople(personCheckRecord.getModifyPeople());
		personCheckRecord.setHandlingAreaReceipt(handlingAreaReceipt);
		this.personCheckRecordService.updatePersonCheckRecord(personCheckRecord);
		this.dao.flush();
	}

}
