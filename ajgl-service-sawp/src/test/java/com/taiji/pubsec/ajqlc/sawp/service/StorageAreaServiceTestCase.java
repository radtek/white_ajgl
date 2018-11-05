package com.taiji.pubsec.ajqlc.sawp.service;

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
import com.taiji.pubsec.ajqlc.sawp.model.StorageArea;
import com.taiji.pubsec.ajqlc.test.TestBase;
import com.taiji.pubsec.businesscomponent.organization.model.Unit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class StorageAreaServiceTestCase extends TestBase { 
	
	@Resource
	private IStorageAreaService storageAreaService;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/storageArea/save-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/storageArea/save-expected.xml", table = "t_sawp_bgq", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void save(){
		StorageArea sa = new StorageArea();
		sa.setAddress("xxwz");
		sa.setCode("bm");
		sa.setName("mc");
		sa.setRemark("bz");
		sa.setState("zt");
		sa.setUnit((Unit) this.dao.findById(Unit.class, "unitId"));
		storageAreaService.save(sa);
		this.dao.flush();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/storageArea/update-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/storageArea/update-expected.xml", table = "t_sawp_bgq", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void update(){
		StorageArea sa = (StorageArea) this.dao.findById(StorageArea.class, "bgqId");
		sa.setAddress("xxwz2");
		sa.setCode("bm2");
		sa.setName("mc2");
		sa.setRemark("bz2");
		sa.setState("zt2");
		storageAreaService.update(sa);
		this.dao.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/storageArea/delete-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/storageArea/delete-expected.xml", table = "t_sawp_bgq", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void delete(){
		storageAreaService.deleteStorageAreas("bgqId", "bgqId2");
		this.dao.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/storageArea/updateStatus-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/storageArea/updateStatus-expected.xml", table = "t_sawp_bgq", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void updateStatus(){
		storageAreaService.updateStatus("bgqId", "1");
		this.dao.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/storageArea/findAllStorageAreas-setup.xml")
	public void findAllStorageAreas(){
		Pager<StorageArea> saList = storageAreaService.findAllStorageAreas(0, 10);
		Assert.assertEquals(7, saList.getTotalNum());
		for(int i = 0; i < saList.getPageList().size(); i++){
			System.out.println(saList.getPageList().get(i).getCode());
		}
		Assert.assertEquals("2", saList.getPageList().get(1).getCode());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/storageArea/findStorageAreasByStatus-setup.xml")
	public void findStorageAreasByStatus(){
		List<StorageArea> saList = storageAreaService.findStorageAreasByStatus("unitId", "1");
		Assert.assertEquals(4, saList.size());
		Assert.assertEquals("1", saList.get(0).getState());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/storageArea/findById-setup.xml")
	public void findById(){
		StorageArea storageArea = storageAreaService.findById("bgqId7");
		Assert.assertEquals("7", storageArea.getCode());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/storageArea/findByUnitIdAndName-setup.xml")
	public void findByUnitIdAndName(){
		StorageArea sa = storageAreaService.findByUnitIdAndName("unitId", "mc");
		Assert.assertEquals("bz22", sa.getRemark());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/storageArea/findByUnitIdAndCode-setup.xml")
	public void findByUnitIdAndCode(){
		StorageArea storageArea = storageAreaService.findByUnitIdAndCode("unitId", "5");
		Assert.assertEquals("mc2", storageArea.getName());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/storageArea/findStorageAreaByArticleId-setup.xml")
	public void findStorageAreaByArticleId(){
		StorageArea storageArea = storageAreaService.findStorageAreaByArticleId("wpId");
		Assert.assertEquals("bz22", storageArea.getRemark());
	}
}