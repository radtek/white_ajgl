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
import com.taiji.pubsec.ajqlc.baq.model.FinallyLeaveInfo;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.baq.model.TmpLeaveInfo;
import com.taiji.pubsec.ajqlc.test.TestBase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class FinallyLeaveInfoServiceTestCase extends TestBase {
	
	@Resource
	private IFinallyLeaveInfoService finallyLeaveInfoService;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/finallyLeaveInfo/saveFinallyLeaveInfo-setup.xml")
	@ExpectedDatabases({ 
						 @ExpectedDatabase(value = "classpath:dataset/finallyLeaveInfo/saveFinallyLeaveInfo-expected.xml", table = "t_baq_lslkxx", query = "select babmfzr, lkyy, lksj, wxs_id, whr, whsj, fhsj from t_baq_lslkxx where id is not null", assertionMode = DatabaseAssertionMode.NON_STRICT),
						 @ExpectedDatabase(value = "classpath:dataset/finallyLeaveInfo/saveFinallyLeaveInfo-expected.xml", table = "t_baq_zzlkxx", query = "select zzlkyy, zzlksj, wxs_id, zxxgr_id, baqsyd_id from t_baq_zzlkxx where id is not null", assertionMode = DatabaseAssertionMode.NON_STRICT),
						 })
	public void saveFinallyLeaveInfo() throws ParseException {
		setLoinInfoAdmin();
		
		FinallyLeaveInfo fli = new FinallyLeaveInfo();
		fli.setAskRoom((ActivityRoom) this.dao.findById(ActivityRoom.class, "wxsId"));
		fli.setFinallyLeaveCause("zzlkyy"); 
		fli.setFinallyLeaveTime(DateUtils.parseDate("2010-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		fli.setHandlingAreaReceipt((HandlingAreaReceipt) this.dao.findById(HandlingAreaReceipt.class, "baqsydId"));
//		fli.setModifyPeople((Person) this.dao.findById(Person.class, "personId"));
		List<TmpLeaveInfo> tliList = new ArrayList<TmpLeaveInfo>();
		TmpLeaveInfo tli1 = new TmpLeaveInfo();
		tli1.setAskRoom((ActivityRoom) this.dao.findById(ActivityRoom.class, "wxsId"));
		tli1.setHandleCauseDepartmentLeader("babmfzr");
		tli1.setLeaveCause("lkyy");
		tli1.setLeaveTime(DateUtils.parseDate("2010-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		tli1.setMaintainer("whr");
		tli1.setMaintainTime(DateUtils.parseDate("2010-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		tli1.setReturnTime(DateUtils.parseDate("2010-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		TmpLeaveInfo tli2 = new TmpLeaveInfo();
		tli2.setAskRoom((ActivityRoom) this.dao.findById(ActivityRoom.class, "wxsId"));
		tli2.setHandleCauseDepartmentLeader("babmfzr2");
		tli2.setLeaveCause("lkyy2");
		tli2.setLeaveTime(DateUtils.parseDate("2010-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		tli2.setMaintainer("whr2");
		tli2.setMaintainTime(DateUtils.parseDate("2010-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		tli2.setReturnTime(DateUtils.parseDate("2010-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		tliList.add(tli1);
		tliList.add(tli2);
		finallyLeaveInfoService.saveFinallyLeaveInfo(fli, tliList);
		this.dao.flush();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/finallyLeaveInfo/updateFinallyLeaveInfo-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/finallyLeaveInfo/updateFinallyLeaveInfo-expected.xml", table = "t_baq_zzlkxx", query = "select id, zzlkyy, zzlksj, wxs_id, zxxgr, baqsyd_id from t_baq_zzlkxx where id is not null", assertionMode = DatabaseAssertionMode.NON_STRICT),
		@ExpectedDatabase(value = "classpath:dataset/finallyLeaveInfo/updateFinallyLeaveInfo-expected.xml", table = "t_baq_lslkxx", query = "select babmfzr, lkyy, lksj, wxs_id, whr, whsj, fhsj, zzlkxx_id from t_baq_lslkxx where id is not null", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void updateFinallyLeaveInfo() throws ParseException {
		setLoinInfoAdmin();
		
		FinallyLeaveInfo fli = (FinallyLeaveInfo) this.dao.findById(FinallyLeaveInfo.class, "zzlkxxId");
		fli.setAskRoom((ActivityRoom) this.dao.findById(ActivityRoom.class, "wxsId"));
		fli.setFinallyLeaveCause("zzlkyy");
		fli.setFinallyLeaveTime(DateUtils.parseDate("2010-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
//		fli.setModifyPeople((Person) this.dao.findById(Person.class, "personId"));
		List<TmpLeaveInfo> tliList = new ArrayList<TmpLeaveInfo>();
		TmpLeaveInfo tli1 = (TmpLeaveInfo) this.dao.findById(TmpLeaveInfo.class, "lslkxxId1");
		tli1.setHandleCauseDepartmentLeader("babmfzr11");
		TmpLeaveInfo tli2 = new TmpLeaveInfo();
		tli2.setAskRoom((ActivityRoom) this.dao.findById(ActivityRoom.class, "wxsId"));
		tli2.setHandleCauseDepartmentLeader("babmfzr22");
		tli2.setLeaveCause("lkyy22");
		tli2.setLeaveTime(DateUtils.parseDate("2010-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		tli2.setMaintainer("whr22");
		tli2.setMaintainTime(DateUtils.parseDate("2010-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		tli2.setReturnTime(DateUtils.parseDate("2010-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		tliList.add(tli1);
		tliList.add(tli2);
		List<String> deleteIds = new ArrayList<String>();
		deleteIds.add("lslkxxId2");
		finallyLeaveInfoService.updateFinallyLeaveInfo(fli, tliList, deleteIds);
		this.dao.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/finallyLeaveInfo/findFinallyLeaveInfoById-setup.xml")
	public void findFinallyLeaveInfoById() {
		FinallyLeaveInfo fli = finallyLeaveInfoService.findFinallyLeaveInfoById("zzlkxxId");
		Assert.assertEquals("zzlkyy", fli.getFinallyLeaveCause());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/finallyLeaveInfo/findFinallyLeaveInfoByHandlingAreaReceiptId-setup.xml")
	public void findFinallyLeaveInfoByHandlingAreaReceiptId() {
		FinallyLeaveInfo fli = finallyLeaveInfoService.findFinallyLeaveInfoByHandlingAreaReceiptId("baqsydId");
		Assert.assertEquals("zzlkyy", fli.getFinallyLeaveCause());
		Assert.assertEquals(2, fli.getTmpLeaveInfos().size());
	}
}