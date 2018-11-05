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
import com.taiji.pubsec.ajqlc.sawp.bean.OutStorageFormItemServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.OutStorageFormServiceBean;
import com.taiji.pubsec.ajqlc.sawp.model.OutStorageForm;
import com.taiji.pubsec.ajqlc.sawp.model.OutStorageFormItem;
import com.taiji.pubsec.ajqlc.test.TestBase;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class OutStorageFormServiceTestCase extends TestBase{
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@Resource
	private IOutStorageFormService outStorageFormService;
	
	@Test
	@DatabaseSetup("classpath:dataset/outStorageForm/createForm-setup.xml")
	@ExpectedDatabases({ 
		@ExpectedDatabase(value = "classpath:dataset/outStorageForm/createForm-expected.xml", table = "t_sawp_ckd", assertionMode = DatabaseAssertionMode.NON_STRICT),
		@ExpectedDatabase(value = "classpath:dataset/outStorageForm/createForm-expected.xml", table = "t_sawp_ckdx", assertionMode = DatabaseAssertionMode.NON_STRICT),
		@ExpectedDatabase(value = "classpath:dataset/outStorageForm/createForm-expected.xml", table = "t_sawp_bgwz", assertionMode = DatabaseAssertionMode.NON_STRICT)
	})
	public void createForm() {
		OutStorageFormServiceBean outStorageFormBean = new OutStorageFormServiceBean();
		outStorageFormBean.setCaseCode("ajbh");
		outStorageFormBean.setCode("bm");
		outStorageFormBean.setOutStorageTime(new Date().getTime());
		outStorageFormBean.setCreatedTime(new Date().getTime());
		outStorageFormBean.setPapers("wsmc");
		outStorageFormBean.setReceiver("lqr");
		outStorageFormBean.setRemark("bz");
		outStorageFormBean.setType("cklx");
		List<OutStorageFormItemServiceBean> outStorageFormItemBeanList = new ArrayList<OutStorageFormItemServiceBean>();
		OutStorageFormItemServiceBean outStorageFormItemServiceBean = new OutStorageFormItemServiceBean();
		outStorageFormItemServiceBean.setInvolvedArticleId("sawpId");
		outStorageFormItemServiceBean.setLockerId("cwgId");
		outStorageFormItemServiceBean.setOutcomingNumber(2D);
		outStorageFormItemServiceBean.setRemark("bz");
		outStorageFormItemBeanList.add(outStorageFormItemServiceBean);
		outStorageFormService.createForm(outStorageFormBean, outStorageFormItemBeanList);
		this.dao.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/outStorageForm/findById-setup.xml")
	public void findById() {
		OutStorageForm outStorageForm = outStorageFormService.findById("ckdId");
		Assert.assertEquals("ajbh", outStorageForm.getCaseCode());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/outStorageForm/findByCode-setup.xml")
	public void findByCode() {
		OutStorageForm outStorageForm = outStorageFormService.findByCode("bm");
		Assert.assertEquals("ajbh", outStorageForm.getCaseCode());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/outStorageForm/queryByCondition-setup.xml")
	public void queryByCondition() throws ParseException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("code", "bm");
		paramMap.put("outStorageDateStart", DateUtils.parseDate("2016-10-1 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		paramMap.put("outStorageDateEnd", DateUtils.parseDate("2016-10-20 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		paramMap.put("caseCode", "ajbh");
		paramMap.put("papers", "wsmc");
		paramMap.put("remark", "bz");
		paramMap.put("type", "cklx");
		Pager<OutStorageForm> oPager = outStorageFormService.queryByCondition(paramMap, 0, 5);
		Assert.assertEquals(1, oPager.getTotalNum());
		Assert.assertEquals("ajbh", oPager.getPageList().get(0).getCaseCode());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/outStorageForm/testAcquireNum-setup.xml")
	public void testAcquireNum () {
		String str = this.outStorageFormService.acquireNum();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date date = new Date();
        String formatDate = sdf.format(date);
		Assert.assertEquals("CK" + formatDate + "0001", str);
		System.out.println(str);
		
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/outStorageForm/deleteForm-setup.xml")
	@ExpectedDatabases({ 
		@ExpectedDatabase(value = "classpath:dataset/outStorageForm/deleteForm-expected.xml", table = "t_sawp_ckd", assertionMode = DatabaseAssertionMode.NON_STRICT),
//		@ExpectedDatabase(value = "classpath:dataset/outStorageForm/deleteForm-expected.xml", table = "t_sawp_ckdx", assertionMode = DatabaseAssertionMode.NON_STRICT),
//		@ExpectedDatabase(value = "classpath:dataset/outStorageForm/deleteForm-expected.xml", table = "t_sawp_bgwz", assertionMode = DatabaseAssertionMode.NON_STRICT)
	})
	public void deleteForm() {
		outStorageFormService.deleteForm("ckdId");
		this.dao.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/outStorageForm/outStorageFormEditOrNot-setup.xml")
	public void outStorageFormEditOrNot() {
		boolean flag = outStorageFormService.outStorageFormEditOrNot("ckdId");
		Assert.assertEquals(false, flag);
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/outStorageForm/findOutStorageFormItemsByFormIdForPage-setup.xml")
	public void findOutStorageFormItemsByFormIdForPage() {
		Pager<OutStorageFormItem> osfiPager = outStorageFormService.findOutStorageFormItemsByFormIdForPage("ckdId", 0, 5);
		Assert.assertEquals(4, osfiPager.getTotalNum());
		Assert.assertEquals(2, osfiPager.getPageList().get(0).getOutcomingNumber());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/outStorageForm/findOutStorageFormsByType-setup.xml")
	public void findOutStorageFormsByType() {
		List<String> types = new ArrayList<String>();
		types.add("cklx");
		List<OutStorageForm> osfList = outStorageFormService.findOutStorageFormsByType("caseCode", types);
		Assert.assertEquals(1, osfList.size());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/outStorageForm/findCaseCodesForOutStorage-setup.xml")
	public void findCaseCodesForOutStorage() {
		List<String> caseList =  outStorageFormService.findCaseCodesForOutStorage();
		Assert.assertEquals(0, caseList.size());
	}
}
