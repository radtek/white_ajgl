package com.taiji.pubsec.ajqlc.baq.service;

import java.text.ParseException;
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
import com.taiji.pubsec.ajqlc.baq.model.OperateRecord;
import com.taiji.pubsec.ajqlc.test.TestBase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class OperateRecordServiceTestCase extends TestBase {
	
	@Resource
	private IOperateRecordService operateRecordService;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@Test
	@DatabaseSetup("classpath:dataset/operateRecord/saveOperateRecord-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/operateRecord/saveOperateRecord-expected.xml", table = "t_baq_czjl", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void saveOperateRecord() throws ParseException{
		OperateRecord or = new OperateRecord();
		or.setModelObject("czdx");
		or.setModelObjectId("czdx_id");
		or.setNoteText("bznr");
		or.setNoteType("bzlx");
		or.setOperateContent("cznr");
		or.setOperateTime(DateUtils.parseDate("2016-01-01 12:00:00", "yyyy-MM-dd HH:mm:ss"));
		or.setOperator("czr");
		operateRecordService.saveOperateRecord(or);
		this.dao.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/operateRecord/findOperateRecordByModelObjectId-setup.xml")
	public void findOperateRecordByModelObjectId(){
		List<OperateRecord> orList = operateRecordService.findOperateRecordByModelObjectId("czdx_id", "modelObject");
		Assert.assertEquals(16, orList.size());
		for(int i = 0; i < orList.size(); i++){
			System.out.println(orList.get(i).getOperateTime());
		}
	}
}
