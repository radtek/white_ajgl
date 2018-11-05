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
import com.taiji.pubsec.ajqlc.sawp.bean.BackStorageFormItemServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.BackStorageFormServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.StorageServiceBean;
import com.taiji.pubsec.ajqlc.sawp.model.BackStorageForm;
import com.taiji.pubsec.ajqlc.sawp.model.BackStorageFormItem;
import com.taiji.pubsec.ajqlc.test.TestBase;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class BackStorageFormServiceTestCase extends TestBase { 
	
	@Resource
	private IBackStorageFormService backStorageFormService;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@Test
	@DatabaseSetup("classpath:dataset/backStorageForm/createForm-setup.xml")
	@ExpectedDatabases({ 
		@ExpectedDatabase(value = "classpath:dataset/backStorageForm/createForm-expected.xml", table = "t_sawp_fhd", query = "select bm, bz, ajbh, ckdbm from t_sawp_fhd where id is not null", assertionMode = DatabaseAssertionMode.NON_STRICT),
		@ExpectedDatabase(value = "classpath:dataset/backStorageForm/createForm-expected.xml", table = "t_sawp_fhdx", assertionMode = DatabaseAssertionMode.NON_STRICT),
		@ExpectedDatabase(value = "classpath:dataset/backStorageForm/createForm-expected.xml", table = "t_sawp_bgwz", assertionMode = DatabaseAssertionMode.NON_STRICT)
	})
	public void createForm() {
		BackStorageFormServiceBean backStorageFormBean = new BackStorageFormServiceBean();
		backStorageFormBean.setCaseCode("ajbh");
		backStorageFormBean.setCode("bm");
		backStorageFormBean.setCreatedTime(new Date().getTime());
		backStorageFormBean.setOutStorageFormCode("ckdbm");
		backStorageFormBean.setRemark("bz");
		List<BackStorageFormItemServiceBean> backStorageFormItemBeanList = new ArrayList<BackStorageFormItemServiceBean>();
		BackStorageFormItemServiceBean backStorageFormItemServiceBean = new BackStorageFormItemServiceBean();
		List<StorageServiceBean> storageServiceBeans = new ArrayList<StorageServiceBean>();
		StorageServiceBean storageServiceBean = new StorageServiceBean();
		storageServiceBean.setLockerId("cwgId");
		storageServiceBean.setReturnNumber(2D);
		storageServiceBeans.add(storageServiceBean);
		backStorageFormItemServiceBean.setOutItemId("ckdxId");
		backStorageFormItemServiceBean.setRemark("bz");
		backStorageFormItemServiceBean.setInvolvedArticleId("sawpId");
		backStorageFormItemServiceBean.setMaintainTime(new Date().getTime());
		backStorageFormItemServiceBean.setAreaId("bgqId");
		backStorageFormItemServiceBean.setStorageServiceBeans(storageServiceBeans);
		backStorageFormItemBeanList.add(backStorageFormItemServiceBean);
		backStorageFormService.createForm(backStorageFormBean, backStorageFormItemBeanList);
		this.dao.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/backStorageForm/findById-setup.xml")
	public void findById() {
		BackStorageForm backStorageForm = backStorageFormService.findById("fhdId2");
		Assert.assertEquals("bm2", backStorageForm.getCode());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/backStorageForm/findByOutStorageForm-setup.xml")
	public void findByOutStorageForm() {
		BackStorageForm backStorageForm = backStorageFormService.findByOutStorageForm("ckdbm2");
		Assert.assertEquals("bm2", backStorageForm.getCode());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/backStorageForm/queryByCondition-setup.xml")
	public void queryByCondition() throws ParseException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("backStorageFormCode", "bm");
		paramMap.put("backTimeStart", DateUtils.parseDate("2016-10-1 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		paramMap.put("backTimeEnd", DateUtils.parseDate("2016-10-20 10:10:10", "yyyy-MM-dd HH:mm:ss"));
		paramMap.put("outStorageFormCode", "ckdbm");
		paramMap.put("caseCode", "ajbh");
		
		Pager<BackStorageForm> bsfPager = backStorageFormService.queryByCondition(paramMap, 0, 5);
		Assert.assertEquals(3, bsfPager.getTotalNum());
		Assert.assertEquals("bm3", bsfPager.getPageList().get(2).getCode());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/backStorageForm/updateForm-setup.xml")
	@ExpectedDatabases({ 
		@ExpectedDatabase(value = "classpath:dataset/backStorageForm/updateForm-expected.xml", table = "t_sawp_fhd", assertionMode = DatabaseAssertionMode.NON_STRICT)
	})
	public void updateForm() {
//		BackStorageFormServiceBean backStorageFormBean, List<BackStorageFormItemServiceBean> backStorageFormItemList
		BackStorageFormServiceBean backStorageFormBean = new BackStorageFormServiceBean();
		backStorageFormBean.setCaseCode("ajbh");
		backStorageFormBean.setCode("bm");
		backStorageFormBean.setCreatedTime(new Date().getTime());
		backStorageFormBean.setOutStorageFormCode("ckdbm");
		backStorageFormBean.setRemark("bz");
		backStorageFormBean.setId("fhdId");
		List<BackStorageFormItemServiceBean> backStorageFormItemBeanList = new ArrayList<BackStorageFormItemServiceBean>();
		BackStorageFormItemServiceBean backStorageFormItemServiceBean = new BackStorageFormItemServiceBean();
		List<StorageServiceBean> storageServiceBeans = new ArrayList<StorageServiceBean>();
		StorageServiceBean storageServiceBean = new StorageServiceBean();
		storageServiceBean.setLockerId("cwgId");
		storageServiceBean.setReturnNumber(5D);
		storageServiceBeans.add(storageServiceBean);
		backStorageFormItemServiceBean.setOutItemId("ckdxId");
		backStorageFormItemServiceBean.setRemark("bz");
		backStorageFormItemServiceBean.setInvolvedArticleId("sawpId");
		backStorageFormItemServiceBean.setMaintainTime(new Date().getTime());
		backStorageFormItemServiceBean.setAreaId("bgqId");
		backStorageFormItemServiceBean.setStorageServiceBeans(storageServiceBeans);
		backStorageFormItemBeanList.add(backStorageFormItemServiceBean);
		backStorageFormService.updateForm(backStorageFormBean, backStorageFormItemBeanList);
		this.dao.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/backStorageForm/findBackStorageFormItemsByOutStorageFormItemId-setup.xml")
	public void findBackStorageFormItemsByOutStorageFormItemId() {
		List<BackStorageFormItem> backStorageFormItems = backStorageFormService.findBackStorageFormItemsByOutStorageFormItemId("ckdxId");
		Assert.assertEquals(3, backStorageFormItems.size());
		Assert.assertEquals("bz3", backStorageFormItems.get(2).getRemark());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/backStorageForm/deleteForm-setup.xml")
	@ExpectedDatabases({ 
		@ExpectedDatabase(value = "classpath:dataset/backStorageForm/deleteForm-expected.xml", table = "t_sawp_fhd", assertionMode = DatabaseAssertionMode.NON_STRICT),
		@ExpectedDatabase(value = "classpath:dataset/backStorageForm/deleteForm-expected.xml", table = "t_sawp_fhdx", assertionMode = DatabaseAssertionMode.NON_STRICT),
		@ExpectedDatabase(value = "classpath:dataset/backStorageForm/deleteForm-expected.xml", table = "t_sawp_bgwz", assertionMode = DatabaseAssertionMode.NON_STRICT)
	})
	public void deleteForm() {
		backStorageFormService.deleteForm("fhdId");
		this.dao.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/backStorageForm/findBackStorageFormItemServiceBeanByOutFormId-setup.xml")
	public void findBackStorageFormItemServiceBeanByOutFormId() {
		List<BackStorageFormItemServiceBean> backStorageFormItemServiceBeans = backStorageFormService.findBackStorageFormItemServiceBeanByOutFormId("ckdId");
		Assert.assertEquals(1, backStorageFormItemServiceBeans.size());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/backStorageForm/findBackStorageFormByOutFormCode-setup.xml")
	public void findBackStorageFormByOutFormCode() {
		BackStorageForm backStorageForm = backStorageFormService.findBackStorageFormByOutFormCode("ckdbm2");
		Assert.assertEquals("bz2", backStorageForm.getRemark());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/backStorageForm/testAcquireNum-setup.xml")
	public void testAcquireNum () {
		String str = this.backStorageFormService.acquireNum();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date date = new Date();
        String formatDate = sdf.format(date);
		Assert.assertEquals("FH" + formatDate + "0001", str);
		System.out.println(str);
		
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/backStorageForm/findBackStorageFormItemByArticleIdAndLockerId-setup.xml")
	public void findBackStorageFormItemByArticleIdAndLockerId () {
		BackStorageFormItem backStorageFormItem = backStorageFormService.findBackStorageFormItemByArticleIdAndLockerId("sawpId", "cwgId");
		Assert.assertEquals("bz3", backStorageFormItem.getRemark());
	}
}