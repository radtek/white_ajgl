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
import com.taiji.pubsec.ajqlc.baq.model.CarryGoodsInfo;
import com.taiji.pubsec.ajqlc.baq.model.CarryGoodsRecord;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.test.TestBase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class CarryGoodsInfoServiceTestCase extends TestBase {
	
	@Resource
	private ICarryGoodsInfoService carryGoodsInfoService;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/carryGoodsInfo/saveCarryGoodsInfo-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/carryGoodsInfo/saveCarryGoodsInfo-expected.xml", table = "t_baq_sswpxx", assertionMode = DatabaseAssertionMode.NON_STRICT),
		@ExpectedDatabase(value = "classpath:dataset/carryGoodsInfo/saveCarryGoodsInfo-expected.xml", table = "t_baq_sswprkxx", assertionMode = DatabaseAssertionMode.NON_STRICT)
		})
	public void saveCarryGoodsInfo() throws ParseException{
		setLoinInfoAdmin();
		
		CarryGoodsInfo cgi = new CarryGoodsInfo();
		List<CarryGoodsRecord> cgrList = new ArrayList<CarryGoodsRecord>();
		CarryGoodsRecord cgr1 = (CarryGoodsRecord) this.dao.findById(CarryGoodsRecord.class, "1");
		CarryGoodsRecord cgr2 = (CarryGoodsRecord) this.dao.findById(CarryGoodsRecord.class, "2");
		cgrList.add(cgr1);
		cgrList.add(cgr2);
		HandlingAreaReceipt har = (HandlingAreaReceipt) this.dao.findById(HandlingAreaReceipt.class, "1");
		cgi.setCarryGoodsAdmin("sswpgly");
		cgi.setHandleCasePolice("bary");
		cgi.setPersonsInvolved("sary");
		cgi.setHandlingAreaReceipt(har);
//		cgi.setModifyPeople((Person) this.dao.findById(Person.class, "personId"));
		cgi.setUpdatedTime(DateUtils.parseDate("2010-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		carryGoodsInfoService.saveCarryGoodsInfo(cgi, cgrList);
		this.dao.flush();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/carryGoodsInfo/updateCarryGoodsInfo-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/carryGoodsInfo/updateCarryGoodsInfo-expected.xml", table = "t_baq_sswpxx", assertionMode = DatabaseAssertionMode.NON_STRICT),
		@ExpectedDatabase(value = "classpath:dataset/carryGoodsInfo/updateCarryGoodsInfo-expected.xml", table = "t_baq_sswprkxx", query = "select wpmc, tz, bh, sl, zt, bgcs, jldw, sswpxx_id from  t_baq_sswprkxx where id is not null", assertionMode = DatabaseAssertionMode.NON_STRICT)
		})
	public void updateCarryGoodsInfo() throws ParseException{ 
		setLoinInfoAdmin();
		
		CarryGoodsInfo cgi = (CarryGoodsInfo) this.dao.findById(CarryGoodsInfo.class, "1");
		List<CarryGoodsRecord> cgrList = new ArrayList<CarryGoodsRecord>();
		CarryGoodsRecord cgr2 = (CarryGoodsRecord) this.dao.findById(CarryGoodsRecord.class, "2");
		cgr2.setFeatures("tz2");
		cgr2.setGoodsName("wpmc2");
		cgr2.setNum("bh2");
		cgr2.setQuantity("sl2");
		cgr2.setStatus("zt2");
		cgr2.setTakeCareType("bgcs2");
		cgr2.setUnitOfMeasurement("jldw2");
		CarryGoodsRecord cgr3 = new CarryGoodsRecord();
		cgr3.setGoodsName("wpmc");
		cgr3.setFeatures("tz");
		cgr3.setNum("bh");
		cgr3.setQuantity("sl");
		cgr3.setStatus("zt");
		cgr3.setTakeCareType("bgcs");
		cgr3.setUnitOfMeasurement("jldw");
		CarryGoodsRecord cgr4 = new CarryGoodsRecord();
		cgr4.setGoodsName("wpmc4");
		cgr4.setFeatures("tz4");
		cgr4.setNum("bh4");
		cgr4.setQuantity("sl4");
		cgr4.setStatus("zt4");
		cgr4.setTakeCareType("bgcs4");
		cgr4.setUnitOfMeasurement("jldw4");
		cgrList.add(cgr2);
		cgrList.add(cgr3);
		cgrList.add(cgr4);
		HandlingAreaReceipt har = (HandlingAreaReceipt) this.dao.findById(HandlingAreaReceipt.class, "1");
		cgi.setCarryGoodsAdmin("sswpgly2");
		cgi.setHandleCasePolice("bary2");
		cgi.setPersonsInvolved("sary2");
		cgi.setHandlingAreaReceipt(har);
//		cgi.setModifyPeople((Person) this.dao.findById(Person.class, "personId2"));
		cgi.setUpdatedTime(DateUtils.parseDate("2010-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		carryGoodsInfoService.updateCarryGoodsInfo(cgi, cgrList);
		this.dao.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/carryGoodsInfo/findCarryGoodsInfoById-setup.xml")
	public void findCarryGoodsInfoById(){
		CarryGoodsInfo cgi = carryGoodsInfoService.findCarryGoodsInfoById("1");
		Assert.assertEquals("bary", cgi.getHandleCasePolice());
		Assert.assertEquals(2, cgi.getCarryGoodsRecords().size());
		Assert.assertEquals("tz2", cgi.getCarryGoodsRecords().get(1).getFeatures());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/carryGoodsInfo/findCarryGoodsInfoByHandlingAreaReceiptId-setup.xml")
	public void findCarryGoodsInfoByHandlingAreaReceiptId(){
		CarryGoodsInfo cgi = carryGoodsInfoService.findCarryGoodsInfoByHandlingAreaReceiptId("1");
		CarryGoodsInfo cgi1 = carryGoodsInfoService.findCarryGoodsInfoByHandlingAreaReceiptId("2");
		Assert.assertEquals(null, cgi1);
		Assert.assertEquals("bary", cgi.getHandleCasePolice());
		Assert.assertEquals(2, cgi.getCarryGoodsRecords().size());
		Assert.assertEquals("tz2", cgi.getCarryGoodsRecords().get(1).getFeatures());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/carryGoodsInfo/findCarryGoodsRecordByReceiptIdAndStatus-setup.xml")
	public void findCarryGoodsRecordByReceiptIdAndStatus(){
		List<CarryGoodsRecord> cgrList = carryGoodsInfoService.findCarryGoodsRecordByReceiptIdAndStatus("1", "zt");
		Assert.assertEquals(2, cgrList.size());
		Assert.assertEquals("tz2", cgrList.get(1).getFeatures());
		List<CarryGoodsRecord> cgrList1 = carryGoodsInfoService.findCarryGoodsRecordByReceiptIdAndStatus("2", "zt");
		Assert.assertEquals(0, cgrList1.size());
	}
}