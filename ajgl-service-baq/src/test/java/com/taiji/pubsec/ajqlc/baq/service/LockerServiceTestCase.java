package com.taiji.pubsec.ajqlc.baq.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

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
import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.baq.model.Locker;
import com.taiji.pubsec.ajqlc.test.TestBase;
import com.taiji.pubsec.businesscomponent.organization.model.Unit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class LockerServiceTestCase extends TestBase { 
	
	@Resource
	private ILockerService lockerService;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/locker/saveLocker-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/locker/saveLocker-expected.xml", table = "t_baq_cwg", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void saveLocker(){
		Locker loc = new Locker();
		Unit unit = (Unit) this.dao.findById(Unit.class, "1");
		loc.setUnit(unit);
		loc.setNote("bz");
		loc.setCode("bm");
		loc.setLockerCode("wz");
		loc.setStatus("zt");
		loc.setAreaCode("qh");
		loc.setLockerCode("gh");
		String id = lockerService.saveLocker(loc);
		System.out.println(id);
		this.dao.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/locker/deleteLocker-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/locker/deleteLocker-expected.xml", table = "t_baq_cwg", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void deleteLocker(){
		lockerService.deleteLocker("1", "2");
		this.dao.flush();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/locker/updateLocker-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/locker/updateLocker-expected.xml", table = "t_baq_cwg", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void updateLocker(){
		Locker loc = (Locker) this.dao.findById(Locker.class, "1");
		Unit unit = (Unit) this.dao.findById(Unit.class, "2");
		loc.setUnit(unit);
		loc.setNote("bz2");
		loc.setCode("bm2");
		loc.setLockerCode("wz2");
		loc.setStatus("zt2");
		lockerService.saveLocker(loc);
		this.dao.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/locker/updateLockerStatus-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/locker/updateLockerStatus-expected.xml", table = "t_baq_cwg", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void updateLockerStatus(){
		lockerService.updateLockerStatus("1", "zt2");
		this.dao.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/locker/findLockerById-setup.xml")
	public void findLockerById(){
		Locker loc = lockerService.findLockerById("1");
		Assert.assertEquals("bz", loc.getNote());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/locker/findLockerByCode-setup.xml")
	public void findLockerByCode(){
		Locker loc = lockerService.findLockerByCode("bm2");
		Assert.assertEquals("bz2", loc.getNote());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/locker/findLockerByUnitIdAndPosition-setup.xml")
	public void findLockerByUnitIdAndPosition(){
		Locker loc = lockerService.findLockerByUnitIdAndPosition("1", "wz");
		Assert.assertEquals("bz", loc.getNote());
	}     
	
	@Test
	@DatabaseSetup("classpath:dataset/locker/findLockerByUnitIdAndCode-setup.xml")
	public void findLockerByUnitIdAndCode(){
		Locker loc = lockerService.findLockerByUnitIdAndCode("1", "bm");
		Assert.assertEquals("bz", loc.getNote());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/locker/findLockerByKey-setup.xml")
	public void findLockerByKey(){
		Pager<Locker> locPager = lockerService.findLockerByKey("bm2", "zt2", "2", "wz2", "bz2", 0, 10);
		Assert.assertEquals(3, locPager.getTotalNum());
		Assert.assertEquals("bm2", locPager.getPageList().get(2).getCode());
		Pager<Locker> locPager1 = lockerService.findLockerByKey("bm8", "zt8", null, "wz8", "", 0, 10);
		Assert.assertEquals(1, locPager1.getTotalNum());
		Assert.assertEquals("bz8", locPager1.getPageList().get(0).getNote());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/locker/findLockerByUnitIdAndStatus-setup.xml")
	public void findLockerByUnitIdAndStatus(){
		List<Locker> locList = lockerService.findLockerByUnitIdAndStatus("1", "zt");
		Assert.assertEquals(1, locList.size());
		List<Locker> locList1 = lockerService.findLockerByUnitIdAndStatus("2", "zt2");
		Assert.assertEquals(3, locList1.size());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/locker/findOneRandomLocker-setup.xml")
	public void findOneRandomLocker(){
		List<String> strList = new ArrayList<String>();
		for(int i = 0; i < 100; i++){
			Locker locker = lockerService.findOneRandomEmptyLocker();
			strList.add(locker.getId());
		}
		for(String str: strList){
			System.out.println(str);
		}
		System.out.println();
	}
}
