package com.taiji.pubsec.ajqlc.httpinterface.action.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.taiji.pubsec.ajqlc.httpinterface.action.InOutStorageService;
import com.taiji.pubsec.ajqlc.httpinterface.action.bean.AuthBean;
import com.taiji.pubsec.ajqlc.httpinterface.action.bean.ResultBean;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class InOutStorageServiceTest {

	private InOutStorageService inOutStorageService;
	private AuthBean auth;

	@Before
	public void login() {
		auth = new AuthBean();
		auth.setUsername("administrator");
		auth.setPassword("123");

		inOutStorageService = new InOutStorageImpl();
	}
	
	@Test
	public void acquireInvolvedArticle() {
		String articleCode = "20160825142745";
		ResultBean rb = inOutStorageService.acquireInvolvedArticle(auth, articleCode);
		
		Assert.assertEquals(true, rb.isSuccess());

	}
	
	@Test
	public void acquireInStorageForm() {
		String formCode = "RK2016080004";
		ResultBean rb = inOutStorageService.acquireInStorageForm(auth, formCode);
		
		Assert.assertEquals(true, rb.isSuccess());

	}
	
	@Test
	public void acquireOutStorageForm() {
		String formCode = "CK2016080002";
		ResultBean rb = inOutStorageService.acquireOutStorageForm(auth, formCode);
		
		Assert.assertEquals(true, rb.isSuccess());

	}
	
	@Test
	public void acquireBackStorageForm() {
		String formCode = "FH2016080001";
		ResultBean rb = inOutStorageService.acquireBackStorageForm(auth, formCode);
		
		Assert.assertEquals(true, rb.isSuccess());

	}
	
	@Test
	public void acquireAdjustmentForm() {
		String formCode = "TZ2016080001";
		ResultBean rb = inOutStorageService.acquireAdjustmentForm(auth, formCode);
		
		Assert.assertEquals(true, rb.isSuccess());

	}
	
	@Test
	public void putAjustmentStorage() {
		
	}
}
