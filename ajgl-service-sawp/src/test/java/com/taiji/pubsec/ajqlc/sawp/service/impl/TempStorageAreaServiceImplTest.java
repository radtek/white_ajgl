package com.taiji.pubsec.ajqlc.sawp.service.impl;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

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
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageArea;
import com.taiji.pubsec.ajqlc.sawp.service.ITemporaryStorageAreaService;
import com.taiji.pubsec.ajqlc.test.TestBase;
import com.taiji.pubsec.businesscomponent.organization.model.Organization;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class TempStorageAreaServiceImplTest extends TestBase {
	@Resource
	private ITemporaryStorageAreaService temporaryStorageAreaServiceImpl;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	@Test
	@DatabaseSetup("classpath:/dataset/tmporaryStorageAreaDataSet/save-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:/dataset/tmporaryStorageAreaDataSet/save-expected.xml", table = "t_sawp_zcwpq", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void testSaveTemporaryStorageArea() {
		TemporaryStorageArea sa = new TemporaryStorageArea();
		sa.setAddress("xxwz");
		sa.setCode("bm");
		sa.setName("mc");
		sa.setRemark("bz");
		sa.setStatus("zt");
		sa.setOrg((Organization) this.dao.findById(Organization.class, "unitId"));
		temporaryStorageAreaServiceImpl.saveTemporaryStorageArea(sa);
		this.dao.flush();
	}
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/tmporaryStorageAreaDataSet/update-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/tmporaryStorageAreaDataSet/update-expected.xml", table = "t_sawp_zcwpq", assertionMode = DatabaseAssertionMode.NON_STRICT)})
    public void testUpdateTemporaryStorageArea(){
		TemporaryStorageArea sa = (TemporaryStorageArea) this.dao.findById(TemporaryStorageArea.class, "bgqId");
		sa.setAddress("xxwz1");
		sa.setCode("bm1");
		sa.setName("mc1");
		sa.setRemark("bz1");
		sa.setStatus("zt1");
		temporaryStorageAreaServiceImpl.updateTemporaryStorageArea(sa);
		this.dao.flush();
    }
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/tmporaryStorageAreaDataSet/update-setup.xml")
	public void testFindTemporaryStorageAreaById(){
		TemporaryStorageArea sa = temporaryStorageAreaServiceImpl.findTemporaryStorageAreaById("bgqId");
		Assert.assertEquals("xxwz",sa.getAddress());
	}
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/tmporaryStorageAreaDataSet/update-setup.xml")
	public void testFindTemporaryStorageAreaByCode(){
		TemporaryStorageArea sa = temporaryStorageAreaServiceImpl.findTemporaryStorageAreaByCode("bm");
		Assert.assertEquals("xxwz",sa.getAddress());
	}
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/tmporaryStorageAreaDataSet/update-setup.xml")
	public void testFindAllTemporaryStorageArea(){
		List<TemporaryStorageArea>  lists = temporaryStorageAreaServiceImpl.findAllTemporaryStorageArea("zt");
		Assert.assertEquals(1,lists.size());
	}
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/tmporaryStorageAreaDataSet/update-setup.xml")
	public void testFindAllTemporaryStorageAreaByPage(){
		Pager<TemporaryStorageArea> pagerList = temporaryStorageAreaServiceImpl.findAllTemporaryStorageAreaByPage(0, 10);
		Assert.assertEquals(1,pagerList.getTotalNum());
	}
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/tmporaryStorageAreaDataSet/update-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/tmporaryStorageAreaDataSet/updatestatus-expected.xml", table = "t_sawp_zcwpq", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void testUpdateTemporaryStorageAreaStatus(){
		temporaryStorageAreaServiceImpl.updateTemporaryStorageAreaStatus("bgqId","zt1");
		TemporaryStorageArea sa = (TemporaryStorageArea) this.dao.findById(TemporaryStorageArea.class, "bgqId");
		this.dao.flush();
	}
}
