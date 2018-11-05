package com.taiji.pubsec.ajqlc.sawp.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.annotation.ExpectedDatabases;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.sawp.bean.AdjustmentStorageFormItemServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.AdjustmentStorageFormServiceBean;
import com.taiji.pubsec.ajqlc.sawp.model.AdjustgmentStorageForm;
import com.taiji.pubsec.ajqlc.test.TestBase;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class AdjustmentStorageFormServiceTestCase extends TestBase { 
	
	@Resource
	private IAdjustmentStorageFormService adjustmentStorageFormService;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@Test
	@DatabaseSetup("classpath:dataset/adjustmentStorageForm/createForm-setup.xml")
	@ExpectedDatabases({ 
		@ExpectedDatabase(value = "classpath:dataset/adjustmentStorageForm/createForm-expected.xml", table = "t_sawp_tzd", query = "select bm, tzyy from t_sawp_tzd where id is not null", assertionMode = DatabaseAssertionMode.NON_STRICT),
		@ExpectedDatabase(value = "classpath:dataset/adjustmentStorageForm/createForm-expected.xml", table = "t_sawp_tzdx", assertionMode = DatabaseAssertionMode.NON_STRICT),
		@ExpectedDatabase(value = "classpath:dataset/adjustmentStorageForm/createForm-expected.xml", table = "t_sawp_bgwz", assertionMode = DatabaseAssertionMode.NON_STRICT)
	})
	public void createForm() {
		AdjustmentStorageFormServiceBean form = new AdjustmentStorageFormServiceBean();
		form.setCode("bm");
		form.setCreatedTime(new Date().getTime());
		form.setReason("tzyy");
		List<AdjustmentStorageFormItemServiceBean> items = new ArrayList<AdjustmentStorageFormItemServiceBean>();
		AdjustmentStorageFormItemServiceBean adjustmentStorageFormItemServiceBean = new AdjustmentStorageFormItemServiceBean();
		adjustmentStorageFormItemServiceBean.setAdjustmentNumber(5D);
		adjustmentStorageFormItemServiceBean.setArticleId("sawpId");
		adjustmentStorageFormItemServiceBean.setRemark("bz");
		items.add(adjustmentStorageFormItemServiceBean);
		adjustmentStorageFormService.createForm(form, items);
		this.dao.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/adjustmentStorageForm/findById-setup.xml")
	public void findById() {
		AdjustgmentStorageForm adjustgmentStorageForm = adjustmentStorageFormService.findById("tzdId");
		Assert.assertEquals("bz", adjustgmentStorageForm.getRemark());
		Assert.assertEquals("tzyy", adjustgmentStorageForm.getReason());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/adjustmentStorageForm/queryByCondition-setup.xml")
	public void queryByCondition() throws ParseException {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("code", "bm");
		conditions.put("startTime", DateUtils.parseDate("2016-10-1 10:10:10", "yyyy-MM-dd HH:mm:ss")); 
		conditions.put("endTime ", DateUtils.parseDate("2016-10-20 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		Pager<AdjustgmentStorageForm> adjustgmentStorageForms = adjustmentStorageFormService.queryByCondition(conditions, 0, 5);
		Assert.assertEquals(2, adjustgmentStorageForms.getTotalNum());
		Assert.assertEquals("tzyy2", adjustgmentStorageForms.getPageList().get(1).getReason());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/adjustmentStorageForm/testAcquireNum-setup.xml")
	public void testAcquireNum () {
		String str = this.adjustmentStorageFormService.acquireNum();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date date = new Date();
        String formatDate = sdf.format(date);
		Assert.assertEquals("TZ" + formatDate + "0001", str);
		System.out.println(str);
		
	}
	
}
	