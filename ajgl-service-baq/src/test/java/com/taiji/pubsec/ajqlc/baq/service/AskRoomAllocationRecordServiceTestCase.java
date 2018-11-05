package com.taiji.pubsec.ajqlc.baq.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class AskRoomAllocationRecordServiceTestCase extends TestBase{
	
	@Resource
	private IAskRoomAllocationRecordService askRoomAllocationRecordService;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/askRoomAllocationRecord/saveAskRoomAllocationRecord-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/askRoomAllocationRecord/saveAskRoomAllocationRecord-expected.xml", table = "t_baq_wxsfpjl", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void saveAskRoomAllocationRecord() throws ParseException{
		AskRoomAllocationRecord arar = new AskRoomAllocationRecord();
		ActivityRoom ar = (ActivityRoom) this.dao.findById(ActivityRoom.class, "1");
		List<AskRoomIllegalRecord> arirList = new ArrayList<AskRoomIllegalRecord>();
		arirList.add((AskRoomIllegalRecord) this.dao.findById(AskRoomIllegalRecord.class, "1"));
		arirList.add((AskRoomIllegalRecord) this.dao.findById(AskRoomIllegalRecord.class, "2"));
		HandlingAreaReceipt har = (HandlingAreaReceipt) this.dao.findById(HandlingAreaReceipt.class, "1");
		arar.setHandlingAreaReceipt(har);
		arar.setAskRoom(ar);
		arar.setAllocationPeople("fpr");
		arar.setAllocationTime(DateUtils.parseDate("2010-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		arar.setAskRoomIllegalRecords(arirList);
		askRoomAllocationRecordService.saveAskRoomAllocationRecord(arar);
		this.dao.flush();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/askRoomAllocationRecord/findAskRoomAllocationRecordById-setup.xml")
	public void findAskRoomAllocationRecordById() {
		AskRoomAllocationRecord arar = (AskRoomAllocationRecord) this.dao.findById(AskRoomAllocationRecord.class, "1");
		Assert.assertEquals("fpr", arar.getAllocationPeople());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/askRoomAllocationRecord/findAskRoomAllocationRecordByKey-setup.xml")
	public void findAskRoomAllocationRecordByKey() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> queryConditions = new HashMap<String, Object>();
		queryConditions.put("askRoomName", "mc");
		queryConditions.put("handlingAreaReceiptNum", "sydbh");
		queryConditions.put("causeOfLawCase", "ay");
		queryConditions.put("byQuestioningPeopleName", "bwxrxm");
		queryConditions.put("byQuestioningPeopleIdentifyNum", "sfzjhm");
		queryConditions.put("allocationStartTime", sdf.parse("2016-07-01 21:18:05"));
		queryConditions.put("allocationEndTime", sdf.parse("2016-07-20 21:18:05"));
		List<AskRoomAllocationRecord> ararList = askRoomAllocationRecordService.findAskRoomAllocationRecordByKey(queryConditions);
		Assert.assertEquals(2, ararList.size());
		Assert.assertEquals("2016-07-10 21:18:10", sdf.format(ararList.get(1).getAllocationTime()));
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/askRoomAllocationRecord/findAskRoomAllocationRecordByHandlingAreaReceiptId-setup.xml")
	public void findAskRoomAllocationRecordByHandlingAreaReceiptId() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<AskRoomAllocationRecord> ararList = askRoomAllocationRecordService.findAskRoomAllocationRecordByHandlingAreaReceiptId("sydId");
		Assert.assertEquals(3, ararList.size());
		Assert.assertEquals("2016-07-10 21:18:15", sdf.format(ararList.get(2).getAllocationTime()));
	}
}