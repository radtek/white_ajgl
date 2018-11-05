package com.taiji.pubsec.ajqlc.sawp.service.impl;

import java.text.ParseException;
import java.util.HashMap;
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
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.annotation.ExpectedDatabases;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.sawp.bean.TempStorageOutInfoBean;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageIn;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageOut;
import com.taiji.pubsec.ajqlc.sawp.service.ITemporaryStorageOutService;
import com.taiji.pubsec.ajqlc.test.TestBase;
import com.taiji.pubsec.businesscomponent.organization.model.Person;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class TemporaryStorageOutServiceImplTest extends TestBase {
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@Resource
	private ITemporaryStorageOutService temporaryStorageOutService;
	
	@Test
	@DatabaseSetup("classpath:dataset/temporaryStorageOut/acquireNum-setup.xml")
	public void acquireNum() {
		String outCode = temporaryStorageOutService.acquireNum();
		Assert.assertEquals("ZCCK2017040001", outCode);
		System.out.println(outCode);
		
		String outCode2 = temporaryStorageOutService.acquireNum();
		Assert.assertEquals("ZCCK2017040002", outCode2);
		System.out.println(outCode2);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/temporaryStorageOut/save-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/temporaryStorageOut/save-expected.xml", table = "t_sawp_zcckd", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void save() throws ParseException {
		Person person = (Person) dao.findById(Person.class, "ry_id");
		TemporaryStorageIn in = (TemporaryStorageIn) dao.findById(TemporaryStorageIn.class, "zcrkd_id");
		TemporaryStorageOut out = new TemporaryStorageOut();
		out.setCode("bm");
		out.setCreateDate(DateUtils.parseDate("2017-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		out.setReceivePerson("lqr");
		out.setRemark("bz");
		out.setStatus("zt");
		out.setStorageIn(in);
		out.setCreatePerson(person);
		out.setModifyPerson(person);
		out.setModifyTimeDate(DateUtils.parseDate("2017-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		String outId = temporaryStorageOutService.save(out);
		this.dao.flush();
		System.out.println(outId);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/temporaryStorageOut/update-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/temporaryStorageOut/update-expected.xml", table = "t_sawp_zcckd", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void update() throws ParseException {
		TemporaryStorageOut out = (TemporaryStorageOut) dao.findById(TemporaryStorageOut.class, "zcckd_id");
		out.setCode("bm2");
		out.setReceivePerson("lqr2");
		out.setRemark("bz2");
		out.setStatus("zt2");
		temporaryStorageOutService.update(out);
		this.dao.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/temporaryStorageOut/findById-setup.xml")
	public void findById() throws ParseException {
		TemporaryStorageOut out = temporaryStorageOutService.findById("zcckd_id");
		Assert.assertEquals("bm", out.getCode());
		Assert.assertEquals("lqr", out.getReceivePerson());
		Assert.assertEquals("bz", out.getRemark());
		Assert.assertEquals("zt", out.getStatus());
		Assert.assertEquals(DateUtils.parseDate("2017-10-10 10:10:10", "yyyy-MM-dd HH:mm:ss"), out.getCreateDate());
		Assert.assertEquals("zcrkd_id", out.getStorageIn().getId());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/temporaryStorageOut/delete-setup.xml")
	@ExpectedDatabases({ @ExpectedDatabase(value = "classpath:dataset/temporaryStorageOut/delete-expected.xml", table = "t_sawp_zcckd", assertionMode = DatabaseAssertionMode.NON_STRICT)})
	public void delete() throws ParseException {
		temporaryStorageOutService.delete("zcckd_id");
		this.dao.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/temporaryStorageOut/findTempStorageOutInfosByQueryConditions-setup.xml")
	public void findTempStorageOutInfosByQueryConditions() throws ParseException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("temporaryStorageOutCode", "bm");
		paramMap.put("queryOutDateStart", DateUtils.parseDate("2017-10-1 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		paramMap.put("queryOutDateEnd", DateUtils.parseDate("2017-10-20 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		paramMap.put("caseCode", "ssaj");
		paramMap.put("ownerName", "sary");
		paramMap.put("myId", "ry_id");
		Pager<TempStorageOutInfoBean> beanPager = temporaryStorageOutService.findTempStorageOutInfosByQueryConditions(paramMap, 0, 2);
		Assert.assertEquals(1, beanPager.getTotalNum());
	}
}
