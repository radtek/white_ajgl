package com.taiji.pubsec.ajqlc.sla;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.sla.model.CaseSupectRelation;
import com.taiji.pubsec.ajqlc.sla.model.CriminalBasicCase;
import com.taiji.pubsec.ajqlc.sla.model.CriminalPerson;
import com.taiji.pubsec.ajqlc.sla.service.ICriminalSuspectService;
import com.taiji.pubsec.ajqlc.test.TestBase;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class CriminalSuspectServiceTestCase extends TestBase{
	
	@Resource
	private ICriminalSuspectService criminalSuspectService;
	
	@Test
	@DatabaseSetup("classpath:dataset/criminalSuspect/testFindCriminalSuspectByCondition-setup.xml")
	public void testFindCriminalSuspectByCondition() throws ParseException {
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		Pager<CriminalPerson> cpPager = criminalSuspectService.findCriminalSuspectByCondition(paramMap, 0, 5);
//		Assert.assertEquals(2, cpPager.getTotalNum());
//		Assert.assertEquals(2, cpPager.getPageList().size());
		
		Map<String, Object> paramMap1 = new HashMap<String, Object>();
		paramMap1.put("name", "name2");
		paramMap1.put("sex", "sex");
		paramMap1.put("birthdayStart", DateUtils.parseDate("2010-10-1 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		paramMap1.put("birthdayEnd", DateUtils.parseDate("2010-10-30 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		paramMap1.put("idNumber", "idcardNo");
		paramMap1.put("address", "address");
		paramMap1.put("nativePlace", "doorDetail");
		paramMap1.put("tone", "tone");
		paramMap1.put("cases", "caseId");
		Pager<CaseSupectRelation> cpPager1 = criminalSuspectService.findCriminalSuspectByCondition(paramMap1, 0, 5);
		Assert.assertEquals(1, cpPager1.getTotalNum());
//		Assert.assertEquals(1, cpPager1.getPageList().size());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/criminalSuspect/testFindCriminalSuspectById-setup.xml")
	public void testFindCriminalSuspectById() {
		CriminalPerson criminalPerson = criminalSuspectService.findCriminalSuspectById("personId");
		Assert.assertEquals("address", criminalPerson.getAddress());
	}
	
	
	@Test
	@DatabaseSetup("classpath:dataset/criminalSuspect/findCriminalBasicCasesBySuspectId-setup.xml")
	public void findCriminalBasicCasesBySuspectId() {
		List<CriminalBasicCase> caseList = criminalSuspectService.findCriminalBasicCasesBySuspectId("personId");
		Assert.assertEquals(2, caseList.size());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/criminalSuspect/findCriminalBasicCaseByCaseSupectRelationId-setup.xml")
	public void findCriminalBasicCaseByCaseSupectRelationId() {
		CriminalBasicCase criminalBasicCase = criminalSuspectService.findCriminalBasicCaseByCaseSupectRelationId("xyrgxId");
		Assert.assertEquals("caseSort", criminalBasicCase.getCaseSort());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/criminalSuspect/findCaseSupectRelationById-setup.xml")
	public void findCaseSupectRelationById() {
		CaseSupectRelation caseSupectRelation = criminalSuspectService.findCaseSupectRelationById("xyrgxId2");
		Assert.assertEquals("caseId2", caseSupectRelation.getBasicCase().getCaseCode());
	}
}
