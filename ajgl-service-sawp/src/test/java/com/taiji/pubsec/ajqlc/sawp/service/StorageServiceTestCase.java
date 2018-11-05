package com.taiji.pubsec.ajqlc.sawp.service;

import java.util.List;

import javax.annotation.Resource;

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
import com.taiji.pubsec.ajqlc.sawp.model.Storage;
import com.taiji.pubsec.ajqlc.test.TestBase;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class StorageServiceTestCase extends TestBase { 
	
	@Resource
	private IStorageService storageService;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@Test
	@DatabaseSetup("classpath:dataset/storage/createStorage-setup.xml")
	@ExpectedDatabases({ 
		@ExpectedDatabase(value = "classpath:dataset/storage/createStorage-expected.xml", table = "t_sawp_bgwz", assertionMode = DatabaseAssertionMode.NON_STRICT)
	})
	public void createStorage() {
		Storage storage = new Storage();
		storage.setExistingNumber(2D);
		storage.setIncomingNumber(2D);
		storageService.createStorage(storage, "cwgId", "sawpId");
		this.dao.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/storage/findStoragesByInvolvedArticleCode-setup.xml")
	public void findStoragesByInvolvedArticleCode() {
		List<Storage> storages = storageService.findStoragesByInvolvedArticleCode("bh");
		Assert.assertEquals(2, storages.size());
		Assert.assertEquals("cwgId", storages.get(0).getLocker().getId());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/storage/findStorageByid-setup.xml")
	public void findStorageByid() {
		Storage storage = storageService.findStorageByid("bgwzId1");
		Assert.assertEquals(3, storage.getExistingNumber());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/storage/update-setup.xml")
	@ExpectedDatabases({ 
		@ExpectedDatabase(value = "classpath:dataset/storage/update-expected.xml", table = "t_sawp_bgwz", assertionMode = DatabaseAssertionMode.NON_STRICT)
	})
	public void update() {
		Storage storage = (Storage) this.dao.findById(Storage.class, "bgwzId");
		storage.setExistingNumber(2D);
		storageService.update(storage.getId());
		this.dao.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/storage/delete-setup.xml")
	@ExpectedDatabases({ 
		@ExpectedDatabase(value = "classpath:dataset/storage/delete-expected.xml", table = "t_sawp_bgwz", assertionMode = DatabaseAssertionMode.NON_STRICT)
	})
	public void delete() {
		storageService.delete("bgwzId");
		this.dao.flush();
	}
}
	