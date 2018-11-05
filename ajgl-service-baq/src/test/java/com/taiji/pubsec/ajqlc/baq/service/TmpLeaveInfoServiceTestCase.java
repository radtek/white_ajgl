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
import com.taiji.pubsec.ajqlc.baq.model.FinallyLeaveInfo;
import com.taiji.pubsec.ajqlc.baq.model.TmpLeaveInfo;
import com.taiji.pubsec.ajqlc.test.TestBase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class TmpLeaveInfoServiceTestCase extends TestBase {
	
	@Resource
	private ITmpLeaveInfoService tmpLeaveInfoService;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/tmpLeaveInfo/save-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/tmpLeaveInfo/save-expected.xml", table = "t_baq_lslkxx", query = "select babmfzr, lkyy, lksj, wxs_id, whr, whsj, fhsj, zzlkxx_id from t_baq_lslkxx where id is not null", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void save() throws ParseException {
		TmpLeaveInfo tli = new TmpLeaveInfo();
		tli.setAskRoom((ActivityRoom) this.dao.findById(ActivityRoom.class, "wxsId"));
		tli.setFinallyLeaveInfo((FinallyLeaveInfo) this.dao.findById(FinallyLeaveInfo.class, "zzlkxxId"));
		tli.setHandleCauseDepartmentLeader("babmfzr");
		tli.setLeaveCause("lkyy");
		tli.setLeaveTime(DateUtils.parseDate("2010-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		tli.setMaintainer("whr");
		tli.setMaintainTime(DateUtils.parseDate("2010-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		tli.setReturnTime(DateUtils.parseDate("2010-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		
		tmpLeaveInfoService.save(tli);
		this.dao.flush();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/tmpLeaveInfo/update-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/tmpLeaveInfo/update-expected.xml", table = "t_baq_lslkxx", query = "select id, babmfzr, lkyy, lksj, wxs_id, whr, whsj, fhsj, zzlkxx_id from t_baq_lslkxx where id is not null", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void update() {
		TmpLeaveInfo tli = (TmpLeaveInfo) this.dao.findById(TmpLeaveInfo.class, "lslkxxId");
		tli.setFinallyLeaveInfo((FinallyLeaveInfo) this.dao.findById(FinallyLeaveInfo.class, "zzlkxxId"));
		tli.setHandleCauseDepartmentLeader("babmfzr2");
		tli.setLeaveCause("lkyy2");
		tli.setMaintainer("whr2");
		
		tmpLeaveInfoService.update(tli);
		this.dao.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/tmpLeaveInfo/delete-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/tmpLeaveInfo/delete-expected.xml", table = "t_baq_lslkxx", query = "select id, babmfzr, lkyy, lksj, wxs_id, whr, whsj, fhsj, zzlkxx_id from t_baq_lslkxx where id is not null", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void delete() {
		tmpLeaveInfoService.delete(this.tmpLeaveInfoService.findById("lslkxxId"));
		this.dao.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/tmpLeaveInfo/findById-setup.xml")
	public void findById() {
		TmpLeaveInfo tli = tmpLeaveInfoService.findById("lslkxxId");
		Assert.assertEquals("lkyy", tli.getLeaveCause());
	}
}