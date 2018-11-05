package com.taiji.pubsec.ajqlc.sawp.service;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
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
import com.taiji.pubsec.ajqlc.sawp.model.InvolvedArticle;
import com.taiji.pubsec.ajqlc.test.TestBase;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class InvolvedArticleServiceTestCase extends TestBase { 
	
	@Resource
	private IInvolvedArticleService involvedArticleService;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@Test
	@DatabaseSetup("classpath:dataset/involvedArticle/createInvolvedArticle-setup.xml")
	@ExpectedDatabases({ 
		@ExpectedDatabase(value = "classpath:dataset/involvedArticle/createInvolvedArticle-expected.xml", table = "t_sawp_sawp", assertionMode = DatabaseAssertionMode.NON_STRICT)
	})
	public void createInvolvedArticle() throws ParseException {
		InvolvedArticle involvedArticle = new InvolvedArticle();
		involvedArticle.setCaseCode("ajbh");
		involvedArticle.setCode("bh");
		involvedArticle.setDetentionNumber("kysl");
		involvedArticle.setFeature("tz");
		involvedArticle.setInStorageTime(DateUtils.parseDate("2016-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		involvedArticle.setMeasurementUnit("jldw");
		involvedArticle.setName("mc");
		involvedArticle.setNumberInStorage(5D);
		involvedArticle.setPaper("wsmc");
		involvedArticle.setPaperId("wsid");
		involvedArticle.setPolices("bamjxm");
		involvedArticle.setRemark("bz");
		involvedArticle.setSuspectIdentityNumber("xyrsfzh");
		involvedArticle.setSuspectName("xyrxm");
		involvedArticle.setType("wpxz");
		
		involvedArticleService.createInvolvedArticle(involvedArticle);
		this.dao.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/involvedArticle/findInvolvedArticleById-setup.xml")
	public void findInvolvedArticleById() {
		InvolvedArticle involvedArticle = involvedArticleService.findInvolvedArticleById("sawpId");
		Assert.assertEquals("mc", involvedArticle.getName());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/involvedArticle/findInvolvedArticleByCode-setup.xml")
	public void findInvolvedArticleByCode() {
		InvolvedArticle involvedArticle = involvedArticleService.findInvolvedArticleByCode("bh");
		Assert.assertEquals("mc", involvedArticle.getName());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/involvedArticle/findInvolvedArticlesByCase-setup.xml")
	public void findInvolvedArticlesByCase() {
		List<InvolvedArticle> involvedArticles = involvedArticleService.findInvolvedArticlesByCase("ajbh");
		Assert.assertEquals(2, involvedArticles.size());
		Assert.assertEquals("mc2", involvedArticles.get(1).getName());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/involvedArticle/delete-setup.xml")
	@ExpectedDatabases({ 
		@ExpectedDatabase(value = "classpath:dataset/involvedArticle/delete-expected.xml", table = "t_sawp_sawp", assertionMode = DatabaseAssertionMode.NON_STRICT)
	})
	public void delete() {
		involvedArticleService.delete("sawpId");
		this.dao.flush();
	}
	
}