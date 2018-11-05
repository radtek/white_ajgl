package com.taiji.pubsec.ajqlc.sla;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
import com.taiji.pubsec.ajqlc.sla.bean.AdministrationPersonServiceBean;
import com.taiji.pubsec.ajqlc.sla.bean.PenalPersonServiceBean;
import com.taiji.pubsec.ajqlc.sla.service.ICaseAttachedInfoService;
import com.taiji.pubsec.ajqlc.test.TestBase;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class CaseAttachedInfoServiceTestCase extends TestBase {
	
	@Resource
	private ICaseAttachedInfoService caseAttachedInfoService;
	
	@Test
	@DatabaseSetup("classpath:dataset/caseAttachedInfo/findPenalDqtxByConditionsXj-setup.xml")
	public void findPenalDqtxByConditionsXj() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Pager<PenalPersonServiceBean> beanPager = caseAttachedInfoService.findPenalDqtxByConditions("xj", sdf.parse("2011-10-10 10:10:10"), sdf.parse("2011-10-15 10:10:10"), 0, 5);
		Assert.assertEquals(1, beanPager.getPageList().size());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/caseAttachedInfo/findPenalDqtxByConditionsBgjy-setup.xml")
	public void findPenalDqtxByConditionsBgjy() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Pager<PenalPersonServiceBean> beanPager = caseAttachedInfoService.findPenalDqtxByConditions("bgjy", sdf.parse("2011-10-10 10:10:10"), sdf.parse("2011-10-15 10:10:10"), 0, 5);
		Assert.assertEquals(2, beanPager.getPageList().size());
	}

	@Test
	@DatabaseSetup("classpath:dataset/caseAttachedInfo/findPenalDqtxByConditionsTqdb-setup.xml")
	public void findPenalDqtxByConditionsTqdb() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Pager<PenalPersonServiceBean> beanPager = caseAttachedInfoService.findPenalDqtxByConditions("tqdb", sdf.parse("2011-10-10 10:10:10"), sdf.parse("2011-10-15 10:10:10"), 0, 5);
		Assert.assertEquals(2, beanPager.getPageList().size());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/caseAttachedInfo/findPenalDqtxByConditionsPb-setup.xml")
	public void findPenalDqtxByConditionsPb() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Pager<PenalPersonServiceBean> beanPager = caseAttachedInfoService.findPenalDqtxByConditions("pb", sdf.parse("2011-10-15 10:10:10"), sdf.parse("2011-10-25 10:10:10"), 0, 5);
		Assert.assertEquals(2, beanPager.getPageList().size());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/caseAttachedInfo/findPenalDqtxByConditionsYsqs-setup.xml")
	public void findPenalDqtxByConditionsYsqs() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Pager<PenalPersonServiceBean> beanPager = caseAttachedInfoService.findPenalDqtxByConditions("ysqs", sdf.parse("2011-12-10 10:10:10"), sdf.parse("2011-12-15 10:10:10"), 0, 5);
		Assert.assertEquals(2, beanPager.getPageList().size());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/caseAttachedInfo/findPenalDqtxByConditionsQbhs-setup.xml")
	public void findPenalDqtxByConditionsQbhs() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Pager<PenalPersonServiceBean> beanPager = caseAttachedInfoService.findPenalDqtxByConditions("qbhs", sdf.parse("2011-10-10 10:10:10"), sdf.parse("2011-10-15 10:10:10"), 0, 5);
		Assert.assertEquals(2, beanPager.getPageList().size());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/caseAttachedInfo/findPenalDqtxByConditionsJsjz-setup.xml")
	public void findPenalDqtxByConditionsJsjz() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Pager<PenalPersonServiceBean> beanPager = caseAttachedInfoService.findPenalDqtxByConditions("jsjz", sdf.parse("2011-10-10 10:10:10"), sdf.parse("2011-10-15 10:10:10"), 0, 5);
		Assert.assertEquals(2, beanPager.getPageList().size());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/caseAttachedInfo/findAdminDqtxByConditionsXzjl-setup.xml")
	public void findAdminDqtxByConditionsXzjl() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Pager<AdministrationPersonServiceBean> beanPager = caseAttachedInfoService.findAdminDqtxByConditions("xzjl", sdf.parse("2011-10-10 10:10:10"), sdf.parse("2011-10-15 10:10:10"), 0, 5);
		Assert.assertEquals(2, beanPager.getPageList().size());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/caseAttachedInfo/findAdminDqtxByConditionsSqjd-setup.xml")
	public void findAdminDqtxByConditionsSqjd() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Pager<AdministrationPersonServiceBean> beanPager = caseAttachedInfoService.findAdminDqtxByConditions("sqjd", sdf.parse("2011-10-10 10:10:10"), sdf.parse("2011-10-15 10:10:10"), 0, 5);
		Assert.assertEquals(2, beanPager.getPageList().size());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/caseAttachedInfo/findAdminDqtxByConditionsQzjd-setup.xml")
	public void findAdminDqtxByConditionsQzjd() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Pager<AdministrationPersonServiceBean> beanPager = caseAttachedInfoService.findAdminDqtxByConditions("qzjd", sdf.parse("2011-10-10 10:10:10"), sdf.parse("2011-10-15 10:10:10"), 0, 5);
		Assert.assertEquals(2, beanPager.getPageList().size());
	}
}
