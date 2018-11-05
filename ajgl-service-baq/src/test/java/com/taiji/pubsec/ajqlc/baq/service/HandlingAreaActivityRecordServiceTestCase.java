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
import com.taiji.pubsec.ajqlc.baq.model.ActivityRoom;
import com.taiji.pubsec.ajqlc.baq.model.HandleAreaActivityRecordInfo;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaActivityRecord;
import com.taiji.pubsec.ajqlc.test.TestBase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class HandlingAreaActivityRecordServiceTestCase extends TestBase {
	
	@Resource
	private IHandlingAreaActivityRecordService handlingAreaActivityRecordService;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/handlingAreaActivityRecord/save-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/handlingAreaActivityRecord/save-expected.xml", table = "t_baq_baqhdjl", query = "select hdnr, hdlx, zcy, jssj, whr, whsj, bz, wxs_id, baqhdjlxx_id from t_baq_baqhdjl where id is not null", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void save() throws ParseException {
		HandlingAreaActivityRecord haar = new HandlingAreaActivityRecord();
		haar.setActivityContent("hdnr");
		haar.setActivityType("hdlx");
		haar.setAskRoom((ActivityRoom) this.dao.findById(ActivityRoom.class, "wxsId"));
		haar.setCaretaker("zcy");
		haar.setCreatedTime(DateUtils.parseDate("2010-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		haar.setEndTime(DateUtils.parseDate("2010-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		haar.setHandleAreaActivityRecordInfo((HandleAreaActivityRecordInfo) this.dao.findById(HandleAreaActivityRecordInfo.class, "hdjlxxId"));
		haar.setMaintainer("whr");
		haar.setMaintainTime(DateUtils.parseDate("2010-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		haar.setNote("bz");
		
		handlingAreaActivityRecordService.save(haar);
		this.dao.flush();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/handlingAreaActivityRecord/update-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/handlingAreaActivityRecord/update-expected.xml", table = "t_baq_baqhdjl", query = "select id, hdnr, hdlx, zcy, jssj, whr, whsj, bz, wxs_id, baqhdjlxx_id from t_baq_baqhdjl where id is not null", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void update() {
		HandlingAreaActivityRecord haar = (HandlingAreaActivityRecord) this.dao.findById(HandlingAreaActivityRecord.class, "baqhdjlId");
		haar.setActivityContent("hdnr2");
		haar.setActivityType("hdlx2");
		haar.setCaretaker("zcy2");
		haar.setMaintainer("whr2");
		haar.setNote("bz2");
		
		handlingAreaActivityRecordService.update(haar);
		this.dao.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/handlingAreaActivityRecord/delete-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/handlingAreaActivityRecord/delete-expected.xml", table = "t_baq_baqhdjl", query = "select id, hdnr, hdlx, zcy, jssj, whr, whsj, bz, wxs_id, baqhdjlxx_id from t_baq_baqhdjl where id is not null", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void delete() {
		handlingAreaActivityRecordService.delete("baqhdjlId");
		this.dao.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/handlingAreaActivityRecord/findById-setup.xml")
	public void findById() {
		HandlingAreaActivityRecord haar = handlingAreaActivityRecordService.findById("baqhdjlId");
		Assert.assertEquals("zcy", haar.getCaretaker());
	}
}