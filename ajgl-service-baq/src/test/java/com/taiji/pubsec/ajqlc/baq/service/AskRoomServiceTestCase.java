package com.taiji.pubsec.ajqlc.baq.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.taiji.pubsec.ajqlc.baq.model.ActivityRoom;
import com.taiji.pubsec.ajqlc.test.TestBase;
import com.taiji.pubsec.businesscomponent.organization.model.Unit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class AskRoomServiceTestCase extends TestBase {
	
	@Resource
	private IAskRoomService askRoomService;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/askRoom/saveAskRoom-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/askRoom/saveAskRoom-expected.xml", table = "t_baq_wxs", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void saveAskRoom(){
		ActivityRoom ar = new ActivityRoom();
		Unit unit = (Unit) this.dao.findById(Unit.class, "1");
		ar.setUnit(unit);
		ar.setMonitorNum("jksph");
		ar.setName("mc");
		ar.setNote("bz");
		ar.setCode("bm");
		ar.setType("lx");
		ar.setStatus("1");
		String id = askRoomService.saveAskRoom(ar);
		System.out.println(id);
		this.dao.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/askRoom/deleteAskRoom-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/askRoom/deleteAskRoom-expected.xml", table = "t_baq_wxs", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void deleteAskRoom(){
		askRoomService.deleteAskRoom("1", "2");
		this.dao.flush();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/askRoom/updateAskRoom-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/askRoom/updateAskRoom-expected.xml", table = "t_baq_wxs", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void updateAskRoom(){
		ActivityRoom ar = (ActivityRoom) this.dao.findById(ActivityRoom.class, "1");
		Unit unit = (Unit) this.dao.findById(Unit.class, "2");
		ar.setUnit(unit);
		ar.setMonitorNum("jksph2");
		ar.setName("mc2");
		ar.setNote("bz2");
		ar.setType("lx2");
		ar.setCode("bm2");
		ar.setStatus("2");
		askRoomService.updateAskRoom(ar);
		this.dao.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/askRoom/updateAskRoomStatus-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/askRoom/updateAskRoomStatus-expected.xml", table = "t_baq_wxs", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void updateAskRoomStatus(){
		askRoomService.updateAskRoomStatus("1", "2");
		this.dao.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/askRoom/findAskRoomById-setup.xml")
	public void findAskRoomById(){
		ActivityRoom ar = askRoomService.findAskRoomById("1");
		Assert.assertEquals("mc", ar.getName());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/askRoom/findAskRoomByCode-setup.xml")
	public void findAskRoomByCode(){
		ActivityRoom ar = askRoomService.findAskRoomByCode("bm2");
		Assert.assertEquals("mc2", ar.getName());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/askRoom/findAskRoomByUnitIdAndName-setup.xml")
	public void findAskRoomByUnitIdAndName(){
		ActivityRoom ar = askRoomService.findAskRoomByUnitIdAndName("1", "mc");
		Assert.assertEquals("bz", ar.getNote());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/askRoom/findAskRoomByUnitIdAndCode-setup.xml")
	public void findAskRoomByUnitIdAndCode(){
		ActivityRoom ar = askRoomService.findAskRoomByUnitIdAndCode("1", "bm");
		Assert.assertEquals("bz", ar.getNote());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/askRoom/findAskRoomByKey-setup.xml")
	public void findAskRoomByKey(){
		Map<String, String> query = new HashMap<String, String>();
		query.put("code", "bm6");
		query.put("name", "mc6");
		query.put("status", "6");
		query.put("unitId", "1");
		query.put("note", "bz6");
		query.put("type", "lx6");
		Pager<ActivityRoom> arPager = askRoomService.findAskRoomByKey(query, 0, 10);
		Assert.assertEquals(1, arPager.getTotalNum());
		Assert.assertEquals("bm6", arPager.getPageList().get(0).getCode());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/askRoom/findAskRoomByUnitIdAndType-setup.xml")
	public void findAskRoomByUnitIdAndType(){
		List<ActivityRoom> arList = askRoomService.findAskRoomByType("lx2");
		Assert.assertEquals(1, arList.size());
	}
}