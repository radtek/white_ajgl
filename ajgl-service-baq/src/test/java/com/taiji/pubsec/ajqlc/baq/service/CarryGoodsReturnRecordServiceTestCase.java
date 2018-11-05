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
import com.taiji.pubsec.ajqlc.baq.model.CarryGoodsRecord;
import com.taiji.pubsec.ajqlc.baq.model.CarryGoodsReturnRecord;
import com.taiji.pubsec.ajqlc.test.TestBase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class CarryGoodsReturnRecordServiceTestCase extends TestBase {
	
	@Resource
	private ICarryGoodsReturnRecordService carryGoodsReturnRecordService;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@Test
	@DatabaseSetup("classpath:dataset/carryGoodsReturnRecord/findReturnRecordByGoodsRecordId-setup.xml")
	public void findReturnRecordByGoodsRecordId(){
		CarryGoodsReturnRecord cgrr = carryGoodsReturnRecordService.findReturnRecordByGoodsRecordId("1");
		System.out.println(cgrr.getOperateTime());
		Assert.assertEquals("3", cgrr.getId());
		CarryGoodsReturnRecord cgrr1 = carryGoodsReturnRecordService.findReturnRecordByGoodsRecordId("2");
		Assert.assertEquals(null, cgrr1);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/carryGoodsReturnRecord/saveCarryGoodsReturnRecord-setup.xml")
	@ExpectedDatabases({ 
						 @ExpectedDatabase(value = "classpath:dataset/carryGoodsReturnRecord/saveCarryGoodsReturnRecord-expected.xml", table = "t_baq_sswprkxx", assertionMode = DatabaseAssertionMode.NON_STRICT),
						 @ExpectedDatabase(value = "classpath:dataset/carryGoodsReturnRecord/saveCarryGoodsReturnRecord-expected.xml", table = "t_baq_sswpfhjl", query="select wfhyy, czr, czsj, lqr,lqrzjhm, jjsj, fhzt, sswprkxx_id from t_baq_sswpfhjl where id is not null order by czr", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void saveCarryGoodsReturnRecord() throws ParseException{
		List<CarryGoodsReturnRecord> cgrrList = new ArrayList<CarryGoodsReturnRecord>();
		CarryGoodsReturnRecord cgrr1 = new CarryGoodsReturnRecord();
		CarryGoodsRecord cgr1 = (CarryGoodsRecord) this.dao.findById(CarryGoodsRecord.class, "1");
		cgrr1.setCarryGoodsRecord(cgr1);
		cgrr1.setNoReturnReason("wfhyy");
		cgrr1.setOperater("czr");
		cgrr1.setOperateTime(DateUtils.parseDate("2012-10-10 11:10:10", "yyyy-MM-dd HH:mm:ss"));
		cgrr1.setReceiver("lqr");
		cgrr1.setReceiverIdCard("lqrzjhm");
		cgrr1.setReceiveTime(DateUtils.parseDate("2010-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		cgrr1.setReturnStatus("0000000010003");
		cgrrList.add(cgrr1);
		
		CarryGoodsReturnRecord cgrr2 = new CarryGoodsReturnRecord();
		CarryGoodsRecord cgr2 = (CarryGoodsRecord) this.dao.findById(CarryGoodsRecord.class, "2");
		cgrr2.setCarryGoodsRecord(cgr2);
		cgrr2.setNoReturnReason("wfhyy2");
		cgrr2.setOperater("czr2");
		cgrr2.setOperateTime(DateUtils.parseDate("2012-10-10 11:10:10", "yyyy-MM-dd HH:mm:ss"));
		cgrr2.setReceiver("lqr2");
		cgrr2.setReceiverIdCard("lqrzjhm2");
		cgrr2.setReceiveTime(DateUtils.parseDate("2010-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		cgrr2.setReturnStatus("0000000010003");
		cgrrList.add(cgrr2);
		System.out.println(cgrrList.size());
		System.out.println(cgrrList.get(1).getCarryGoodsRecord().getQuantity());
		carryGoodsReturnRecordService.saveCarryGoodsReturnRecord(cgrrList);
		this.dao.flush();
	}
	
}