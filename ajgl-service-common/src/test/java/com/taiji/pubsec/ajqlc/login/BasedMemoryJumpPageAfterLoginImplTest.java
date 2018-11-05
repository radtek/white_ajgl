/**
 * Copyright 2017 Taiji
 * All right reserved.
 * Created on 2017年3月13日 下午5:27:33
 */
package com.taiji.pubsec.ajqlc.login;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author yucy
 *
 */
public class BasedMemoryJumpPageAfterLoginImplTest {

	@Test
	public void testGetJumpUrl(){
		BasedMemoryJumpPageAfterLoginImpl jumpservice = new BasedMemoryJumpPageAfterLoginImpl();
		jumpservice.getRoleConfigs().add("role1, http://www.sina.com.cn, 7");
		jumpservice.getRoleConfigs().add("role2, http://www.baidu.com.cn, 3");
		jumpservice.getRoleConfigs().add("role3, http://www.sohu.com.cn, 9");
		jumpservice.getRoleConfigs().add("role4, http://www.taiji.com.cn, 2");
		jumpservice.getRoleConfigs().add("role5, http://www.tengxun.com.cn, 1");
		
		jumpservice.getUserConfigs().add("user1, http://tt.com");
		jumpservice.getUserConfigs().add("user2, http://vv.com");
		jumpservice.getUserConfigs().add("user3, http://mm.com");
		
		jumpservice.init();
		
		String url = jumpservice.getJumpUrl("user1", Arrays.asList(new String[]{"role3", "role1"}));
		Assert.assertEquals("http://tt.com", url);
		
		url = jumpservice.getJumpUrl("user4", Arrays.asList(new String[]{"role3", "role1"}));
		Assert.assertEquals("http://www.sohu.com.cn", url);
	}
	
	public static void main(String[] args){
		BasedMemoryJumpPageAfterLoginImplTest test = new BasedMemoryJumpPageAfterLoginImplTest();
		test.testGetJumpUrl();
	}
}
