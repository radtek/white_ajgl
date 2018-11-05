package com.taiji.pubsec.ajqlc.sawp.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.taiji.pubsec.ajqlc.sawp.bean.TemporaryStorageShelfBean;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageArea;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageShelf;
import com.taiji.pubsec.ajqlc.sawp.service.ITempStorageSelfService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class TempStorageShelfServiceImplTest {
	@Resource
	private ITempStorageSelfService tempStorageShelfServiceImpl;
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	@Test
	@DatabaseSetup("classpath:dataset/tmpStorageSelfDataSet/save-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/tmpStorageSelfDataSet/save-expected.xml", table = "t_sawp_zcwpg", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void testSaveTemporaryStorageShelf() {
		TemporaryStorageShelf tss = new TemporaryStorageShelf();
		tss.setAddress("xxwz");
		tss.setArea((TemporaryStorageArea)this.dao.findById(TemporaryStorageArea.class, "bgqId"));
		tss.setCode("bm");
		tss.setRemark("bz");
		tempStorageShelfServiceImpl.saveTemporaryStorageShelf(tss);
		this.dao.flush();
	}

	@Test
	@DatabaseSetup("classpath:dataset/tmpStorageSelfDataSet/update-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/tmpStorageSelfDataSet/update-expected.xml", table = "t_sawp_zcwpg", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void testUpdateTemporaryStorageSelf() {
		TemporaryStorageShelf tss = (TemporaryStorageShelf)this.dao.findById(TemporaryStorageShelf.class, "bggId");
		tss.setAddress("xxwz1");
		tss.setArea((TemporaryStorageArea)this.dao.findById(TemporaryStorageArea.class, "bgqId1"));
		tss.setCode("bm1");
		tss.setRemark("bz1");
		tempStorageShelfServiceImpl.updateTemporaryStorageSelf(tss);
		this.dao.flush();
	}

	@Test
	@DatabaseSetup("classpath:dataset/tmpStorageSelfDataSet/update-setup.xml")
	public void testFindStorageSelfById() {
		TemporaryStorageShelf tss = tempStorageShelfServiceImpl.findStorageSelfById("bggId");
		Assert.assertEquals("xxwz",tss.getAddress());
	}

	@Test
	@DatabaseSetup("classpath:dataset/tmpStorageSelfDataSet/update-setup.xml")
	public void testFindStorageSelfByCode() {
		TemporaryStorageShelf tss = tempStorageShelfServiceImpl.findStorageSelfByCode("bm");
		Assert.assertEquals("xxwz",tss.getAddress());
	}
	@Test
	@DatabaseSetup("classpath:dataset/tmpStorageSelfDataSet/update-setup.xml")
   public void testFindAllTemporaryStorageShelfByPage(){
		Pager<TemporaryStorageShelf> listPageer = tempStorageShelfServiceImpl.findAllTemporaryStorageShelfByPage("bgqId",0, 10);
		Assert.assertEquals(1,listPageer.getTotalNum());
   }
	
	@Test
	@DatabaseSetup("classpath:dataset/tmpStorageSelfDataSet/findAllTemporaryStorageInByConditions-setup.xml")
   public void findAllTemporaryStorageInByConditions(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("areaId", "zcwpqId");
		List<String> strList = new ArrayList<String>();
		strList.add("bm");
		params.put("storageCodeList", strList);
		params.put("suspectNameOrIdCard", "bwxrxm");
		params.put("policeNameOrNum", "xm");
		params.put("caseNameOrCode", "caseId");
		params.put("isFree", "0000000007001");
		Pager<TemporaryStorageShelfBean> beanPager = tempStorageShelfServiceImpl.findAllTemporaryStorageInByConditions(params,0, 10);
		Assert.assertEquals(1,beanPager.getTotalNum());
   }
}
