package com.taiji.pubsec.ajqlc.baq.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.test.TestBase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class HandleAreaActivityRecordInfoServiceTestCase extends TestBase {
	
	@Resource
	private IHandleAreaActivityRecordInfoService handleAreaActivityRecordInfoService;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/handleAreaActivityRecordInfo/saveHandleAreaActivityRecordInfo-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/handleAreaActivityRecordInfo/saveHandleAreaActivityRecordInfo-expected.xml", table = "t_baq_hdjlxx", assertionMode = DatabaseAssertionMode.NON_STRICT),
		@ExpectedDatabase(value = "classpath:dataset/handleAreaActivityRecordInfo/saveHandleAreaActivityRecordInfo-expected.xml", table = "t_baq_baqhdjl", query = "select hdnr, hdlx, zcy, jssj, kssj, whr, whsj, bz, wxs_id from t_baq_baqhdjl where id is not null", assertionMode = DatabaseAssertionMode.NON_STRICT)
	})
	public void saveHandleAreaActivityRecordInfo() throws ParseException {
		setLoinInfoAdmin();
		
		HandleAreaActivityRecordInfo haari = new HandleAreaActivityRecordInfo();
		haari.setHandlingAreaReceipt((HandlingAreaReceipt) this.dao.findById(HandlingAreaReceipt.class, "baqsydId"));
//		haari.setModifyPeople((Person) this.dao.findById(Person.class, "personId"));
		haari.setUpdatedTime(DateUtils.parseDate("2010-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		List<HandlingAreaActivityRecord> haarList = new ArrayList<HandlingAreaActivityRecord>();
		HandlingAreaActivityRecord haar1 = new HandlingAreaActivityRecord();
		haar1.setActivityContent("hdnr");
		haar1.setActivityType("hdlx");
		haar1.setAskRoom((ActivityRoom) this.dao.findById(ActivityRoom.class, "wxsId"));
		haar1.setCaretaker("zcy");
		haar1.setEndTime(DateUtils.parseDate("2010-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		haar1.setMaintainer("whr");
		haar1.setMaintainTime(DateUtils.parseDate("2010-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		haar1.setNote("bz");
		haarList.add(haar1);
		
		handleAreaActivityRecordInfoService.saveHandleAreaActivityRecordInfo(haari, haarList);
		this.dao.flush();
		System.out.println(haari.getUpdatedTime());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/handleAreaActivityRecordInfo/updateHandleAreaActivityRecordInfo-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/handleAreaActivityRecordInfo/updateHandleAreaActivityRecordInfo-expected.xml", table = "t_baq_hdjlxx", assertionMode = DatabaseAssertionMode.NON_STRICT),
		@ExpectedDatabase(value = "classpath:dataset/handleAreaActivityRecordInfo/updateHandleAreaActivityRecordInfo-expected.xml", table = "t_baq_baqhdjl", query = "select hdnr, hdlx, zcy, jssj, kssj, whr, whsj, bz, wxs_id, baqhdjlxx_id from t_baq_baqhdjl where id is not null", assertionMode = DatabaseAssertionMode.NON_STRICT)
	})
	public void updateHandleAreaActivityRecordInfo() throws ParseException {
		setLoinInfoAdmin();
		
		HandleAreaActivityRecordInfo haari = (HandleAreaActivityRecordInfo) this.dao.findById(HandleAreaActivityRecordInfo.class, "hdjlxxId");
//		haari.setModifyPeople((Person) this.dao.findById(Person.class, "personId"));
		List<HandlingAreaActivityRecord> haarList = new ArrayList<HandlingAreaActivityRecord>();
		HandlingAreaActivityRecord haar1 = (HandlingAreaActivityRecord) this.dao.findById(HandlingAreaActivityRecord.class, "baqhdjlId1");
		haar1.setCaretaker("zcy11");
		HandlingAreaActivityRecord haar2 = new HandlingAreaActivityRecord();
		haar2.setActivityContent("hdnr22");
		haar2.setActivityType("hdlx22");
		haar2.setAskRoom((ActivityRoom) this.dao.findById(ActivityRoom.class, "wxsId"));
		haar2.setCaretaker("zcy22");
		haar2.setEndTime(DateUtils.parseDate("2010-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		haar2.setMaintainer("whr22");
		haar2.setMaintainTime(DateUtils.parseDate("2010-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		haar2.setNote("bz22");
		haarList.add(haar1);
		haarList.add(haar2);
		List<String> deleteIds = new ArrayList<String>();
		deleteIds.add("baqhdjlId2");
		handleAreaActivityRecordInfoService.updateHandleAreaActivityRecordInfo(haari, haarList, deleteIds);
		this.dao.flush();
		System.out.println(haari.getUpdatedTime());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/handleAreaActivityRecordInfo/findHandleAreaActivityRecordInfoById-setup.xml")
	public void findHandleAreaActivityRecordInfoById() {
		setLoinInfoAdmin();
		
		HandleAreaActivityRecordInfo haari = handleAreaActivityRecordInfoService.findHandleAreaActivityRecordInfoById("hdjlxxId");
		Assert.assertEquals("personId", haari.getModifyPeople().getId());
		Assert.assertEquals(2, haari.getHandlingAreaActivityRecords().size());
		Assert.assertEquals("zcy2", haari.getHandlingAreaActivityRecords().get(1).getCaretaker());
		HandleAreaActivityRecordInfo haari1 = handleAreaActivityRecordInfoService.findHandleAreaActivityRecordInfoById("1");
		Assert.assertEquals(null, haari1);
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/handleAreaActivityRecordInfo/findHandleAreaActivityRecordInfoByHandlingAreaReceiptId-setup.xml")
	public void findHandleAreaActivityRecordInfoByHandlingAreaReceiptId() {
		HandleAreaActivityRecordInfo haari = handleAreaActivityRecordInfoService.findHandleAreaActivityRecordInfoByHandlingAreaReceiptId("baqsydId");
		Assert.assertEquals("personId", haari.getModifyPeople().getId());
		Assert.assertEquals(2, haari.getHandlingAreaActivityRecords().size());
		Assert.assertEquals("zcy2", haari.getHandlingAreaActivityRecords().get(1).getCaretaker());
		HandleAreaActivityRecordInfo haari1 = handleAreaActivityRecordInfoService.findHandleAreaActivityRecordInfoById("1");
		Assert.assertEquals(null, haari1);
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/handleAreaActivityRecordInfo/changeRecordCount-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/handleAreaActivityRecordInfo/changeRecordCount-expected.xml", table = "t_baq_hdjlxx", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void changeRecordCount() {
		setLoinInfoAdmin();
		
		handleAreaActivityRecordInfoService.changeRecordCount("hdjlxxId", 3);
		this.dao.flush();
		Assert.assertEquals(3, handleAreaActivityRecordInfoService.findHandleAreaActivityRecordInfoById("hdjlxxId").getRecordCount());
	}
}