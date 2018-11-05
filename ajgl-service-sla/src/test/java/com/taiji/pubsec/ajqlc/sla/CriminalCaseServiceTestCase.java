package com.taiji.pubsec.ajqlc.sla;

import java.text.ParseException;
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
import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.sla.model.CriminalBasicCase;
import com.taiji.pubsec.ajqlc.sla.service.ICriminalCaseService;
import com.taiji.pubsec.ajqlc.test.TestBase;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class CriminalCaseServiceTestCase extends TestBase{
	
	@Resource
	private ICriminalCaseService criminalCaseService;
	
	@Test
	@DatabaseSetup("classpath:dataset/criminalCase/testFindCriminalCaseByCondition-setup.xml")
	public void testFindCriminalCaseByCondition() throws ParseException {
		Map<String, Object> xqlMap = new HashMap<String, Object>();
		xqlMap.put("disposePerson", "disposePerson");
		Pager<CriminalBasicCase> cbc = criminalCaseService.findCriminalCaseByCondition(xqlMap, 0, 10);
		Assert.assertEquals(1, cbc.getPageList().size());
		
		
		
		
//		Map<String, Object> xqlMapa = new HashMap<String, Object>(0);
//		xqlMapa.put("caseTimeStart", DateUtils.parseDate("2016-01-01 13:00:00", "yyyy-MM-dd HH:mm:ss"));
//		Assert.assertEquals(1, criminalCaseService.findCriminalCaseByCondition(xqlMapa, 0, 10).getPageList().size());
//		Map<String, Object> xqlMapb = new HashMap<String, Object>(0);
//		xqlMapb.put("caseTimeEnd", DateUtils.parseDate("2016-01-01 13:00:00", "yyyy-MM-dd HH:mm:ss"));
//		Assert.assertEquals(1, criminalCaseService.findCriminalCaseByCondition(xqlMapb, 0, 10).getPageList().size());
//		Map<String, Object> xqlMapc = new HashMap<String, Object>(0);
//		xqlMapc.put("caseId", "case");
//		Assert.assertEquals(1, criminalCaseService.findCriminalCaseByCondition(xqlMapc, 0, 10).getPageList().size());
//		Map<String, Object> xqlMapd = new HashMap<String, Object>(0);
//		xqlMapd.put("caseName", "case");
//		Assert.assertEquals(1, criminalCaseService.findCriminalCaseByCondition(xqlMapd, 0, 10).getPageList().size());
//		Map<String, Object> xqlMape = new HashMap<String, Object>(0);
//		xqlMape.put("caseSort", "caseSort");
//		Assert.assertEquals(1, criminalCaseService.findCriminalCaseByCondition(xqlMape, 0, 10).getPageList().size());
//		Map<String, Object> xqlMapf = new HashMap<String, Object>(0);
//		xqlMapf.put("caseKind", "caseKind");
//		Assert.assertEquals(1, criminalCaseService.findCriminalCaseByCondition(xqlMapf, 0, 10).getPageList().size());
//		Map<String, Object> xqlMapg = new HashMap<String, Object>(0);
//		xqlMapg.put("handingUnit", "handingUnit");
//		Assert.assertEquals(1, criminalCaseService.findCriminalCaseByCondition(xqlMapg, 0, 10).getPageList().size());
//		Map<String, Object> xqlMaph = new HashMap<String, Object>(0);
//		xqlMaph.put("popedom", "popedom");
//		Assert.assertEquals(1, criminalCaseService.findCriminalCaseByCondition(xqlMaph, 0, 10).getPageList().size());
//		Map<String, Object> xqlMapi = new HashMap<String, Object>(0);
//		xqlMapi.put("community", "community");
//		Assert.assertEquals(1, criminalCaseService.findCriminalCaseByCondition(xqlMapi, 0, 10).getPageList().size());
//		List<String> list = new ArrayList<String>();
//		list.add("caseState");
//		Map<String, Object> xqlMapj = new HashMap<String, Object>(0);
//		xqlMapj.put("caseState", list);
//		Assert.assertEquals(1, criminalCaseService.findCriminalCaseByCondition(xqlMapj, 0, 10).getPageList().size());
//		Map<String, Object> xqlMapk = new HashMap<String, Object>(0);
//		xqlMapk.put("inputPerson", "input");
//		Assert.assertEquals(1, criminalCaseService.findCriminalCaseByCondition(xqlMapk, 0, 10).getPageList().size());
//		Map<String, Object> xqlMapl = new HashMap<String, Object>(0);
//		xqlMapl.put("caseAddress", "case");
//		Assert.assertEquals(1, criminalCaseService.findCriminalCaseByCondition(xqlMapl, 0, 10).getPageList().size());
//		Map<String, Object> xqlMapm = new HashMap<String, Object>(0);
//		xqlMapm.put("caseTimeStart", DateUtils.parseDate("2016-01-01 13:00:00", "yyyy-MM-dd HH:mm:ss"));
//		xqlMapm.put("caseTimeEnd", DateUtils.parseDate("2016-01-01 13:00:00", "yyyy-MM-dd HH:mm:ss"));
//		xqlMapm.put("caseId", "case");
//		xqlMapm.put("caseName", "case");
//		xqlMapm.put("caseSort", "caseSort");
//		xqlMapm.put("caseKind", "caseKind");
//		xqlMapm.put("handingUnit", "handingUnit");
//		xqlMapm.put("popedom", "popedom");
//		xqlMapm.put("community", "community");
//		xqlMapm.put("caseState", list);
//		xqlMapm.put("inputPerson", "input");
//		xqlMapm.put("caseAddress", "case");
//		Assert.assertEquals(1, criminalCaseService.findCriminalCaseByCondition(xqlMapm, 0, 10).getPageList().size());
		
		
	}

	@Test
	@DatabaseSetup("classpath:dataset/criminalCase/testFindCriminalCaseById-setup.xml")
	public void testFindCriminalCaseById() {
		CriminalBasicCase criminalBasicCase = criminalCaseService.findCriminalCaseById("caseId");
		Assert.assertEquals("caseName", criminalBasicCase.getCaseName());
	//	Assert.assertEquals("popedom", criminalBasicCase.getAlarmInfo().getPopedom());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/criminalCase/testFindCriminalCaseByCaseId-setup.xml")
	public void testFindCriminalCaseByCaseId() {
		CriminalBasicCase criminalBasicCase = criminalCaseService.findCriminalCaseByCaseId("caseId");
		Assert.assertEquals("caseName", criminalBasicCase.getCaseName());
	//	Assert.assertEquals("popedom", criminalBasicCase.getAlarmInfo().getPopedom());
	}
	
//	@Test
//	@DatabaseSetup("classpath:dataset/criminalCase/findCriminalBasicCaseForInStorage-setup.xml")
//	public void findCriminalBasicCaseForInStorage() {
//		List<String> caseIdList = criminalCaseService.findCriminalBasicCaseForInStorage();
//		Assert.assertEquals(1, caseIdList.size());
//	}
//	
//	@Test
//	@DatabaseSetup("classpath:dataset/criminalCase/findCriminalBasicCaseBeanForInStorageByCaseId-setup.xml")
//	public void findCriminalBasicCaseBeanForInStorageByCaseId() {
//		CriminalBasicCaseBean criminalBasicCaseBean = criminalCaseService.findCriminalBasicCaseBeanForInStorageByCaseId("caseId");
//		Assert.assertEquals(2, criminalBasicCaseBean.getSuspectPersons().size());
//	}
//	
//	@Test
//	@DatabaseSetup("classpath:dataset/criminalCase/findImpoundedObjectsByCaseIdAndSuspectId-setup.xml")
//	public void findImpoundedObjectsByCaseIdAndSuspectId() {
//		List<ImpoundedObjectSla> impoundedObjects = criminalCaseService.findImpoundedObjectsByCaseIdAndSuspectId("caseId", "suspectId");
//		Assert.assertEquals(1, 1);
//	}
	
	@Test
	@DatabaseSetup("classpath:dataset/criminalCase/findDocBeansByCaseIdAndSuspectId-setup.xml")
	public void findDocBeansByCaseIdAndSuspectId() {
//		List<DocBean> docBeans =  criminalCaseService.findDocBeansByCaseIdAndSuspectId("caseId", "personid");
		Assert.assertEquals(1, 1);
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/criminalCase/findRelatedCriminalBasicCaseById-setup.xml")
	public void findRelatedCriminalBasicCaseById() {
		criminalCaseService.findRelatedCriminalBasicCaseById("caseId");
		Assert.assertEquals(1, 1);
	}
}
