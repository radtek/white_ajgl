package com.taiji.pubsec.ajqlc.sawp.service;

import java.util.HashMap;
import java.util.Map;

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
import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.sawp.bean.ArticleInvolvedServiceBean;
import com.taiji.pubsec.ajqlc.test.TestBase;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class StorageQueryServiceTestCase extends TestBase { 
	
	@Resource
	private IStorageQueryService storageQueryService;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@Test
	@DatabaseSetup("classpath:dataset/storageQuery/acquireOutcomingCount-setup.xml")
	public void acquireOutcomingCount() {
		int count = storageQueryService.acquireOutcomingCount("sawpId");
		Assert.assertEquals(5, count);
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/storageQuery/acquireReturnedCount-setup.xml")
	public void acquireReturnedCount() {
		int count = storageQueryService.acquireReturnedCount("sawpId");
		Assert.assertEquals(5, count);
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/storageQuery/isAvailableWithoutAnything-setup.xml")
	public void isAvailableWithoutAnything() {
		boolean flag = storageQueryService.isAvailableWithoutAnything("cwgId1");
		Assert.assertEquals(false, flag);
		
		boolean flag1 = storageQueryService.isAvailableWithoutAnything("cwgId2");
		Assert.assertEquals(false, flag1);
		
		boolean flag2 = storageQueryService.isAvailableWithoutAnything("cwgId3");
		Assert.assertEquals(true, flag2);
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/storageQuery/queryArticleInvolved-setup.xml")
	public void queryArticleInvolved() {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("code", "bh1");
//		conditions.put("name", "mc");
//		conditions.put("caseCode", "ajbh");
//		conditions.put("suspectName", "xyrxm");
//		conditions.put("paper", "wsmc");
//		conditions.put("polices", "bamjxm");
		Pager<ArticleInvolvedServiceBean> iaPager =  storageQueryService.queryArticleInvolved(conditions, 0, 5);
		Assert.assertEquals(1, iaPager.getTotalNum());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/storageQuery/queryArticleInvolvedInStorage-setup.xml")
	public void queryArticleInvolvedInStorage() {
		
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/storageQuery/queryStorageInLocker-setup.xml")
	public void queryStorageInLocker() {
		
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/storageQuery/findLockersByConditions-setup.xml")
	public void findLockersByConditions() {
		Map<String, Object> conditions = new HashMap<String, Object>();
//		conditions.put("caseCodeOrName", "ajbh");
		conditions.put("isOrNotFree", "0000000007002");
		storageQueryService.findLockersByConditions(conditions, 0, 5);
		Assert.assertEquals("1", "1");
	}
	
}