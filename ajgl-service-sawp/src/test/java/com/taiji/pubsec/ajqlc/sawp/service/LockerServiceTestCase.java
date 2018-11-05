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
import com.taiji.pubsec.ajqlc.sawp.model.ArticleLocker;
import com.taiji.pubsec.ajqlc.sawp.model.StorageArea;
import com.taiji.pubsec.ajqlc.test.TestBase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class LockerServiceTestCase extends TestBase { 
	
	@Resource
	private IArticleLockerService lockerService;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/locker/save-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/locker/save-expected.xml", table = "t_sawp_cwg", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void save(){
		ArticleLocker l = new ArticleLocker();
		l.setArea((StorageArea) this.dao.findById(StorageArea.class, "bgqId"));
		l.setCode("bm");
		l.setLocation("wzms");
		l.setRemark("bz");
		lockerService.save(l);
		this.dao.flush();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/locker/update-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/locker/update-expected.xml", table = "t_sawp_cwg", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void update(){
		ArticleLocker l = (ArticleLocker) this.dao.findById(ArticleLocker.class, "cwgId");
		l.setCode("bm2");
		l.setLocation("wzms2");
		l.setRemark("bz2");
		lockerService.update(l);
		this.dao.flush();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/locker/delete-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/locker/delete-expected.xml", table = "t_sawp_cwg", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void delete(){
		lockerService.delete((ArticleLocker) this.dao.findById(ArticleLocker.class, "cwgId"));
		this.dao.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/locker/findLockersByStorageAreaId-setup.xml")
	public void findLockersByStorageAreaId(){
		List<ArticleLocker> lList = lockerService.findLockersByStorageAreaId("bgqId");
		Assert.assertEquals(7, lList.size());
		Assert.assertEquals("2", lList.get(1).getCode());
		for(int i = 0; i < lList.size(); i++){
			System.out.println(lList.get(i).getCode());
		}
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/locker/findById-setup.xml")
	public void findById(){
		ArticleLocker l = lockerService.findById("cwgId3");
		Assert.assertEquals("bz3", l.getRemark());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/locker/findByName-setup.xml")
	public void findByName(){
		ArticleLocker l = lockerService.findByName("bgqId", "wzms6");
		Assert.assertEquals("7", l.getCode());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/locker/findByCode-setup.xml")
	public void findByCode(){
		ArticleLocker l = lockerService.findByCode("bgqId", "7");
		Assert.assertEquals("wzms6", l.getLocation());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/locker/findArticleLockersByStorageAreaIdForPage-setup.xml")
	public void findArticleLockersByStorageAreaIdForPage(){
		Pager<ArticleLocker> alPager = lockerService.findArticleLockersByStorageAreaIdForPage("bgqId", 0, 5);
		Assert.assertEquals(5, alPager.getPageList().size());
	}
}
