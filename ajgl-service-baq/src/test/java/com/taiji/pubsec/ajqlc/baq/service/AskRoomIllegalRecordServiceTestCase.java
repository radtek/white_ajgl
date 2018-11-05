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
import com.taiji.pubsec.ajqlc.baq.model.ActivityRoom;
import com.taiji.pubsec.ajqlc.baq.model.AskRoomAllocationRecord;
import com.taiji.pubsec.ajqlc.baq.model.AskRoomIllegalRecord;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.test.TestBase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class AskRoomIllegalRecordServiceTestCase extends TestBase {
	
	@Resource
	private IAskRoomIllegalRecordService askRoomIllegalRecordService;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/askRoomIllegalRecord/saveAskRoomIllegalRecord-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/askRoomIllegalRecord/saveAskRoomIllegalRecord-expected.xml", table = "t_baq_wxswgjl", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void saveAskRoomIllegalRecord() throws ParseException{
		AskRoomIllegalRecord illegal = new AskRoomIllegalRecord();		
		AskRoomAllocationRecord arar = (AskRoomAllocationRecord) this.dao.findById(AskRoomAllocationRecord.class,"allocationRecord1");
		ActivityRoom ar = (ActivityRoom) this.dao.findById(ActivityRoom.class, "room1");
		HandlingAreaReceipt har = (HandlingAreaReceipt) this.dao.findById(HandlingAreaReceipt.class, "receipt1");
		arar.setHandlingAreaReceipt(har);
		arar.setAskRoom(ar);
		illegal.setAskRoomAllocationRecord(arar);
		illegal.setCommitPeople("commitPeople");
		illegal.setIllegalCause("case1");
		illegal.setIllegalTime(DateUtils.parseDate("2010-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		askRoomIllegalRecordService.saveAskRoomIllegalRecord(illegal);
		this.dao.flush();
	}
	
	
}