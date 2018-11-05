package com.taiji.pubsec.ajqlc.baq.service;

import java.text.ParseException;

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
import com.taiji.pubsec.ajqlc.baq.model.CollectInfoSituation;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.test.TestBase;
import com.taiji.pubsec.businesscomponent.organization.model.Person;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class CollectInfoSituationServiceTestCase extends TestBase {
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@Resource
	private ICollectInfoSituationService collectInfoSituationService;
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/collectInfoSituation/testSaveCollectInfoSituation-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/collectInfoSituation/testSaveCollectInfoSituation-expected.xml", table = "t_baq_cjxxqk", 
						 query = "select sjxm, qtnr, xxrk, hcdb, sfsj, zxxgr_id, zxxgsj, from t_baq_cjxxqk where id is not null and baqsyd_id is not null", 
						 assertionMode = DatabaseAssertionMode.NON_STRICT),
						 @ExpectedDatabase(value = "classpath:dataset/collectInfoSituation/testSaveCollectInfoSituation-expected.xml", table = "t_baq_baqsyd", 
						 query = "select id, zxxgr_id, xgsj, cjr, cjsj, cjbm, zt from t_baq_baqsyd where id is not null ", 
						 assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void testSaveCollectInfoSituation () throws ParseException {
		HandlingAreaReceipt handlingAreaReceipt = (HandlingAreaReceipt) this.dao.findById(HandlingAreaReceipt.class, "sydId");
		CollectInfoSituation collectInfoSituation = new CollectInfoSituation ();
		collectInfoSituation.setCollectProject("sjxm");
		collectInfoSituation.setCollectProjectOther("qtnr");
		collectInfoSituation.setInfoIntoString("xxrk");
		collectInfoSituation.setInspectComparison("hcdb");
		collectInfoSituation.setIsCollect("sfsj");
		collectInfoSituation.setModifyPeople((Person) this.dao.findById(Person.class, "personId"));
		collectInfoSituation.setHandlingAreaReceipt(handlingAreaReceipt);
		this.collectInfoSituationService.saveCollectInfoSituation(collectInfoSituation);
		this.dao.flush();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/collectInfoSituation/testUpdateCollectInfoSituation-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/collectInfoSituation/testUpdateCollectInfoSituation-expected.xml", table = "t_baq_cjxxqk", 
						 query = "select id, sjxm, qtnr, xxrk, hcdb, sfsj, zxxgr_id, zxxgsj, from t_baq_cjxxqk where id is not null and baqsyd_id is not null", 
						 assertionMode = DatabaseAssertionMode.NON_STRICT),
						 @ExpectedDatabase(value = "classpath:dataset/collectInfoSituation/testUpdateCollectInfoSituation-expected.xml", table = "t_baq_baqsyd", 
						 query = "select id, zxxgr_id, cjr, cjsj, cjbm, zt from t_baq_baqsyd where id is not null ", 
						 assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void testUpdateCollectInfoSituation () throws ParseException {
		CollectInfoSituation collectInfoSituation = (CollectInfoSituation) this.dao.findById(CollectInfoSituation.class, "xxqkId");
		collectInfoSituation.setCollectProject("newsjxm");
		collectInfoSituation.setCollectProjectOther("newqtnr");
		collectInfoSituation.setInfoIntoString("newxxrk");
		collectInfoSituation.setInspectComparison("newhcdb");
		collectInfoSituation.setIsCollect("newsfsj");
		collectInfoSituation.setModifyPeople((Person) this.dao.findById(Person.class, "personId"));
		HandlingAreaReceipt handlingAreaReceipt = collectInfoSituation.getHandlingAreaReceipt();
		handlingAreaReceipt.setModifyPeople(collectInfoSituation.getModifyPeople());
		this.collectInfoSituationService.updateCollectInfoSituation(collectInfoSituation);
		this.dao.flush();
		
	}

}
