package com.taiji.pubsec.ajqlc.sawp.service.impl;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.taiji.pubsec.ajqlc.sawp.bean.TempObjectBean;
import com.taiji.pubsec.ajqlc.sawp.bean.TempStorageInInfoBean;
import com.taiji.pubsec.ajqlc.sawp.model.StoragePosition;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageArea;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageIn;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageShelf;
import com.taiji.pubsec.ajqlc.sawp.service.ITemporaryStorageInService;
import com.taiji.pubsec.businesscomponent.organization.model.Organization;
import com.taiji.pubsec.businesscomponent.organization.model.Person;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class TemporaryStorageInServiceImplTest {
	@Resource
	private ITemporaryStorageInService temporaryStorageInServiceImpl;
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	@Test
	public void testFindAllTemporaryStorageInByConditions() {
	}

	@Test
	@DatabaseSetup("classpath:dataset/tmpStorageInDataSet/save-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/tmpStorageInDataSet/save-expected.xml", table = "t_sawp_zcrkd", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void testSaveTemporaryStorageIn() {
		TemporaryStorageIn temporaryStorageIn = new TemporaryStorageIn();
		temporaryStorageIn.setCode("bm");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			temporaryStorageIn.setCreateDate(sdf.parse("2017-01-01 00:00:00"));
			temporaryStorageIn.setModifyTime(sdf.parse("2017-01-01 00:00:00"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		temporaryStorageIn.setCreatePerson((Person) this.dao.findById(Person.class, "ryid"));
		temporaryStorageIn.setHarCode("123");
		List<StoragePosition> storagePosition = new ArrayList<StoragePosition>();
		StoragePosition  tmp = new StoragePosition();
		tmp.setBgg((TemporaryStorageShelf) this.dao.findById(TemporaryStorageShelf.class, "wpgid"));
		tmp.setRkd(temporaryStorageIn);
		storagePosition.add(tmp);
		temporaryStorageIn.setStoragePosition(storagePosition);
		temporaryStorageInServiceImpl.saveTemporaryStorageIn(temporaryStorageIn);
		this.dao.flush();
	}

	@Test
	@DatabaseSetup("classpath:dataset/tmpStorageInDataSet/update-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/tmpStorageInDataSet/update-expected.xml", table = "t_sawp_zcrkd", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void testUpdateTemporaryStorageIn() {
		TemporaryStorageIn temporaryStorageIn = (TemporaryStorageIn)this.dao.findById(TemporaryStorageIn.class,"rkdid");
		List<StoragePosition> storagePosition = new ArrayList<StoragePosition>();
		StoragePosition  tmp = new StoragePosition();
		tmp.setBgg((TemporaryStorageShelf) this.dao.findById(TemporaryStorageShelf.class, "wpgid"));
		tmp.setRkd(temporaryStorageIn);
		storagePosition.add(tmp);
		temporaryStorageIn.setStoragePosition(storagePosition);
		temporaryStorageInServiceImpl.updateTemporaryStorageIn(temporaryStorageIn);
		this.dao.flush();
	}

	@Test
	@DatabaseSetup("classpath:dataset/tmpStorageInDataSet/finddata.xml")
	public void testFindTemporaryStorageInByPage() {
		Map<String, Object> paramMa = new HashMap<String, Object>();
		paramMa.put("code", "bm");
		Pager<TempStorageInInfoBean>  result = temporaryStorageInServiceImpl.findTemporaryStorageInByPage(paramMa, 0, 10);
		Assert.assertEquals("bwxrxm", result.getPageList().get(0).getObjectOwnerPerson());
	}
	@Test
	@DatabaseSetup("classpath:dataset/tmpStorageInDataSet/find-zcwpg.xml")
	public void testFindTemporaryStorageInById(){
		TemporaryStorageIn in = temporaryStorageInServiceImpl.findTemporaryStorageInById("zcrkd");
		Assert.assertEquals("bm", in.getCode());
		Assert.assertEquals("wpgid", in.getStoragePosition().get(0).getBgg().getId());
	}
	@Test
	@DatabaseSetup("classpath:dataset/tmpStorageInDataSet/find-sawp.xml")
	public void testFindTemporaryObjectByStorageId(){
		List<TempObjectBean>  list = temporaryStorageInServiceImpl.findTemporaryObjectByStorageByCode("sydbh");
	    Assert.assertEquals("wpmc", list.get(0).getObjectName());
	    Assert.assertEquals(2, list.size());
	}
	@Test
	@DatabaseSetup("classpath:dataset/tmpStorageInDataSet/find-sawp.xml")
	public void testFindTemporaryStorageInByStrorageId(){
		TempStorageInInfoBean tib = temporaryStorageInServiceImpl
				.findTemporaryStorageInDeatailByCode("bm");
		Assert.assertEquals("ssaj", tib.getCaseCode());
		Assert.assertEquals("wpmc", tib.getTempObjectBeanList().get(0).getObjectName());
		Assert.assertEquals(2, tib.getTempObjectBeanList().size());
		Assert.assertEquals(1, tib.getInSaveSelfList().size());
	}
	@Test
	@DatabaseSetup("classpath:dataset/tmpStorageInDataSet/find-overTime.xml")
	public void testFindTimeoutTemporaryInByPage(){
		Map<String, Object> paramMa = new HashMap<String, Object>();
		Pager<TempStorageInInfoBean> pageStorage = temporaryStorageInServiceImpl
				.findTimeoutTemporaryInByPage(paramMa, 0, 10);
		Assert.assertEquals(2, pageStorage.getTotalNum());
		List<String> params = new ArrayList<String>();
		params.add("wpgbm");
		paramMa.put("shelfCodeList", params);
		Pager<TempStorageInInfoBean> pageStorage1 = temporaryStorageInServiceImpl
				.findTimeoutTemporaryInByPage(paramMa, 0, 10);
		Assert.assertEquals(1, pageStorage1.getTotalNum());
	}
}
