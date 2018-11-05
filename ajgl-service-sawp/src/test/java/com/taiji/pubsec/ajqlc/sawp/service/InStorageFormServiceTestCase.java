package com.taiji.pubsec.ajqlc.sawp.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.annotation.ExpectedDatabases;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.sawp.bean.InStorageFormItemServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.InStorageFormServiceBean;
import com.taiji.pubsec.ajqlc.sawp.bean.StorageServiceBean;
import com.taiji.pubsec.ajqlc.sawp.model.InStorageForm;
import com.taiji.pubsec.ajqlc.sawp.model.InStorageFormItem;
import com.taiji.pubsec.ajqlc.test.TestBase;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class InStorageFormServiceTestCase extends TestBase { 
	
	@Resource
	private IInStorageFormService inStorageFormService;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	@Test
	@DatabaseSetup("classpath:dataset/inStorageForm/createForm-setup.xml")
	@ExpectedDatabases({ 
//		@ExpectedDatabase(value = "classpath:dataset/inStorageForm/createForm-expected.xml", table = "t_sawp_rkd", query = "select bm, bz, ajbh, wsmc, xyrxm from t_sawp_rkd where id is not null", assertionMode = DatabaseAssertionMode.NON_STRICT),
//		@ExpectedDatabase(value = "classpath:dataset/inStorageForm/createForm-expected.xml", table = "t_sawp_sawp", query = "select ajbh, bh, kysl, tz, jldw, mc, zksl, wsmc, bamjxm, xyrsfzh, xyrxm, wpxz, bz from t_sawp_sawp where id is not null", assertionMode = DatabaseAssertionMode.NON_STRICT),
//		@ExpectedDatabase(value = "classpath:dataset/inStorageForm/createForm-expected.xml", table = "t_sawp_bgwz", query = "select zksl, bcsl, cwg_id from t_sawp_bgwz where id is not null", assertionMode = DatabaseAssertionMode.NON_STRICT),
		@ExpectedDatabase(value = "classpath:dataset/inStorageForm/createForm-expected.xml", table = "t_sawp_rkdx", query = "select rksl, bz, cwg_id from t_sawp_rkdx where id is not null", assertionMode = DatabaseAssertionMode.NON_STRICT)
	})
	public void createForm() throws ParseException{
		InStorageFormServiceBean form = new InStorageFormServiceBean();
		List<InStorageFormItemServiceBean> items = new ArrayList<InStorageFormItemServiceBean>();
		form.setCaseCode("ajbh");
		form.setCode("bm");
		form.setCreatedTimeStr("2016-10-10 10:10:10");
		form.setPapers("wsmc");
		form.setRemark("bz");
		form.setSuspectName("xyrxm");
		
		InStorageFormItemServiceBean inStorageFormItemServiceBean1 = new InStorageFormItemServiceBean();
		inStorageFormItemServiceBean1.setCaseCode("ajbh");
		inStorageFormItemServiceBean1.setArticleCode("bh");
		inStorageFormItemServiceBean1.setDetentionNumber("kysl");
		inStorageFormItemServiceBean1.setArticleFeature("tz");
		inStorageFormItemServiceBean1.setMeasurementUnit("jldw");
		inStorageFormItemServiceBean1.setArticleName("mc");
		inStorageFormItemServiceBean1.setNumberIncome(1D);
		inStorageFormItemServiceBean1.setPaperName("wsmc");
		inStorageFormItemServiceBean1.setPolices("bamjxm");
		inStorageFormItemServiceBean1.setRemark("bz");
		inStorageFormItemServiceBean1.setSuspectIdentifier("xyrsfzh");
		inStorageFormItemServiceBean1.setSuspectName("xyrxm");
		inStorageFormItemServiceBean1.setArticleType("wpxz");
		List<StorageServiceBean> storages1 = new ArrayList<StorageServiceBean>();
		StorageServiceBean storage1 = new StorageServiceBean();
		storage1.setExistingNumber(1);
		storage1.setIncomingNumber(1D);
		storage1.setLockerId("cwgId1");
		storages1.add(storage1);
		inStorageFormItemServiceBean1.setStorageServiceBeans(storages1);
		items.add(inStorageFormItemServiceBean1);
		inStorageFormService.createForm(form, items);
		this.dao.flush();
		
		System.out.println();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/inStorageForm/findById-setup.xml")
	public void findById(){
		InStorageForm inStorageForm = inStorageFormService.findById("rkdId");
		Assert.assertEquals("bz", inStorageForm.getRemark());
		Assert.assertEquals(1, inStorageForm.getInStorageFormItems().size());
		Assert.assertEquals("sawpId", inStorageForm.getInStorageFormItems().iterator().next().getArticle().getId());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/inStorageForm/queryByCondition-setup.xml")
	public void queryByCondition() throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> conditions = new HashMap<String, Object>(); 
		Pager<InStorageForm> iPager = inStorageFormService.queryByCondition(conditions, 0, 5);
		Assert.assertEquals(1, iPager.getTotalNum());
		Assert.assertEquals("sawpId", iPager.getPageList().get(0).getInStorageFormItems().iterator().next().getArticle().getId());
		
		Map<String, Object> conditions1 = new HashMap<String, Object>(); 
		conditions1.put("code", "bm");
		conditions1.put("startTime", sdf.parse("2016-10-1 10:10:10"));
		conditions1.put("endTime", sdf.parse("2016-10-20 10:10:10"));
		conditions1.put("caseCode", "ajbh");
		conditions1.put("suspectName", "xyrxm");
		conditions1.put("papers", "wsmc");
		conditions1.put("remark", "bz");
		conditions1.put("isShowMyForm", "0000000007001");
		Pager<InStorageForm> iPager1 = inStorageFormService.queryByCondition(conditions1, 0, 5);
		Assert.assertEquals(1, iPager1.getPageList().size());
		
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/inStorageForm/updateForm-setup.xml")
	@ExpectedDatabases({ 
//		@ExpectedDatabase(value = "classpath:dataset/inStorageForm/updateForm-expected.xml", table = "t_sawp_rkd", query = "select bm, bz, ajbh, wsmc, xyrxm from t_sawp_rkd where id is not null", assertionMode = DatabaseAssertionMode.NON_STRICT),
//		@ExpectedDatabase(value = "classpath:dataset/inStorageForm/updateForm-expected.xml", table = "t_sawp_sawp", query = "select ajbh, bh, kysl, tz, jldw, mc, zksl, wsmc, bamjxm, xyrsfzh, xyrxm, wpxz, bz from t_sawp_sawp where id is not null", assertionMode = DatabaseAssertionMode.NON_STRICT),
//		@ExpectedDatabase(value = "classpath:dataset/inStorageForm/updateForm-expected.xml", table = "t_sawp_bgwz", query = "select zksl, bcsl, cwg_id from t_sawp_bgwz where id is not null", assertionMode = DatabaseAssertionMode.NON_STRICT),
		@ExpectedDatabase(value = "classpath:dataset/inStorageForm/updateForm-expected.xml", table = "t_sawp_rkdx", query = "select rksl, bz, cwg_id, rkd_id from t_sawp_rkdx where id is not null", assertionMode = DatabaseAssertionMode.NON_STRICT)
	})
	public void updateForm() throws ParseException{
		InStorageFormServiceBean form = new InStorageFormServiceBean();
		List<InStorageFormItemServiceBean> items = new ArrayList<InStorageFormItemServiceBean>();
		form.setId("rkdId");
		form.setCaseCode("ajbh");
		form.setCode("bm");
		form.setCreatedTimeStr("2016-10-10 10:10:10");
		form.setPapers("wsmc");
		form.setRemark("bz-update");
		form.setSuspectName("xyrxm");
		
		InStorageFormItemServiceBean inStorageFormItemServiceBean1 = new InStorageFormItemServiceBean();
		inStorageFormItemServiceBean1.setCaseCode("ajbh");
		inStorageFormItemServiceBean1.setArticleCode("bh");
		inStorageFormItemServiceBean1.setDetentionNumber("kysl");
		inStorageFormItemServiceBean1.setArticleFeature("tz");
		inStorageFormItemServiceBean1.setMeasurementUnit("jldw");
		inStorageFormItemServiceBean1.setArticleName("mc");
		inStorageFormItemServiceBean1.setNumberIncome(1D);
		inStorageFormItemServiceBean1.setPaperName("wsmc");
		inStorageFormItemServiceBean1.setPolices("bamjxm");
		inStorageFormItemServiceBean1.setRemark("bz");
		inStorageFormItemServiceBean1.setSuspectIdentifier("xyrsfzh");
		inStorageFormItemServiceBean1.setSuspectName("xyrxm");
		inStorageFormItemServiceBean1.setArticleType("wpxz");
		List<StorageServiceBean> storages1 = new ArrayList<StorageServiceBean>();
		StorageServiceBean storage1 = new StorageServiceBean();
		storage1.setExistingNumber(1);
		storage1.setIncomingNumber(1D);
		storage1.setLockerId("cwgId1");
		storages1.add(storage1);
		inStorageFormItemServiceBean1.setStorageServiceBeans(storages1);
		items.add(inStorageFormItemServiceBean1);
		inStorageFormService.updateForm(form, items);
		this.dao.flush();
		
		System.out.println();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/inStorageForm/testAcquireNum-setup.xml")
	public void testAcquireNum () {
		String str = this.inStorageFormService.acquireNum();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date date = new Date();
        String formatDate = sdf.format(date);
		Assert.assertEquals("RK" + formatDate + "0001", str);
		
		
		String str1 = this.inStorageFormService.acquireNum();
		Date date1 = new Date();
        String formatDate1 = sdf.format(date);
		Assert.assertEquals("RK" + formatDate + "0002", str1);
		System.out.println(str);
		System.out.println(str1);
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/inStorageForm/inStorageFormToBean-setup.xml")
	public void inStorageFormToBean() {
		InStorageForm inStorageForm = (InStorageForm) this.dao.findById(InStorageForm.class, "rkdId");
		InStorageFormServiceBean inStorageFormServiceBean = inStorageFormService.inStorageFormToBean(inStorageForm);
		Assert.assertEquals(1, inStorageFormServiceBean.getInStorageFormItems().size());
		Assert.assertEquals("ajbh", inStorageFormServiceBean.getCaseCode());
		Assert.assertEquals("wsmc", inStorageFormServiceBean.getPapers());
		Assert.assertEquals("sawpId", inStorageFormServiceBean.getInStorageFormItems().get(0).getArticleId());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DatabaseSetup("classpath:dataset/inStorageForm/inStorageFormItemToBean-setup.xml")
	public void inStorageFormItemToBean() {
		InStorageFormItem inStorageFormItem = (InStorageFormItem) this.dao.findById(InStorageFormItem.class, "rkdxId");
		InStorageFormItemServiceBean inStorageFormItemServiceBean = inStorageFormService.inStorageFormItemToBean(inStorageFormItem);
		Assert.assertEquals("sawpId", inStorageFormItemServiceBean.getArticleId());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/inStorageForm/inStorageFormEditOrNot-setup.xml")
	public void inStorageFormEditOrNot() {
		boolean flag = inStorageFormService.inStorageFormEditOrNot("rkdId");
		Assert.assertEquals(true, flag);
		
		boolean flag2 = inStorageFormService.inStorageFormEditOrNot("rkdId2");
		Assert.assertEquals(false, flag2);
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/inStorageForm/deleteFormById-setup.xml")
	@ExpectedDatabases({ 
		@ExpectedDatabase(value = "classpath:dataset/inStorageForm/deleteFormById-expected.xml", table = "t_sawp_rkd", assertionMode = DatabaseAssertionMode.NON_STRICT),
		@ExpectedDatabase(value = "classpath:dataset/inStorageForm/deleteFormById-expected.xml", table = "t_sawp_rkdx", assertionMode = DatabaseAssertionMode.NON_STRICT),
		@ExpectedDatabase(value = "classpath:dataset/inStorageForm/deleteFormById-expected.xml", table = "t_sawp_sawp", assertionMode = DatabaseAssertionMode.NON_STRICT),
		@ExpectedDatabase(value = "classpath:dataset/inStorageForm/deleteFormById-expected.xml", table = "t_sawp_bgwz", assertionMode = DatabaseAssertionMode.NON_STRICT)
	})
	public void deleteFormById() {
		inStorageFormService.deleteFormById("rkdId");
		this.dao.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/inStorageForm/findInStorageFormItemsByFormIdForPage-setup.xml")
	public void findInStorageFormItemsByFormIdForPage() {
		Pager<InStorageFormItem> isfiPager = inStorageFormService.findInStorageFormItemsByFormIdForPage("rkdId", 0, 5);
		Assert.assertEquals(1, isfiPager.getTotalNum());
		Assert.assertEquals("bz", isfiPager.getPageList().get(0).getRemark());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/inStorageForm/findInStorageFormItemsByFormId-setup.xml")
	public void findInStorageFormItemsByFormId() {
		List<InStorageFormItem> inStorageFormItems = inStorageFormService.findInStorageFormItemsByFormId("rkdId");
		Assert.assertEquals(1, inStorageFormItems.size());
		Assert.assertEquals("bz", inStorageFormItems.get(0).getRemark());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/inStorageForm/findInStorageFormByCaseCodeAndSuspectName-setup.xml")
	public void findInStorageFormByCaseCodeAndSuspectName() {
		InStorageForm inStorageForm = inStorageFormService.findInStorageFormByCaseCodeAndSuspectId("ajbh", "xyrxm");
		Assert.assertEquals("bz", inStorageForm.getRemark());
		Assert.assertEquals("wsmc", inStorageForm.getPapers());
	}
	
	@Test
	@DatabaseSetup("classpath:dataset/inStorageForm/findInStorageFormItemsByArticleCodeAndLockerId-setup.xml")
	public void findInStorageFormItemsByArticleCodeAndLockerId() {
		InStorageFormItem inStorageFormItem = inStorageFormService.findInStorageFormItemsByArticleCodeAndLockerId("bh", "cwgId");
		Assert.assertEquals("bz", inStorageFormItem.getRemark());
		Assert.assertEquals("rkdxId", inStorageFormItem.getId());
	}
	
}