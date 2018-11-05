/**
 * Copyright 2017 Taiji
 * All right reserved.
 * Created on 2017年2月27日 下午3:57:17
 */
package com.taiji.pubsec.ajqlc.wrapper.cwgdevice.service;

import java.util.Map;

import com.taiji.pubsec.ajqlc.wrapper.common.bean.ResultBean;

/**
 * @author yucy
 *
 */
public class LockerServiceImplTest {

	public void testOpen(){
		LockerServiceImpl lockservice = new LockerServiceImpl();
		lockservice.getCwgAdressCfg().add("area=1, tank=2, ip=52.4.1.229, port=8031");
		lockservice.init();
		
		ResultBean r = lockservice.open(1, 2, 5);
		if(r.isResult()){
			System.out.println("打开成功");
		}else{
			System.out.println("打开失败 : " + r.getErrorMsg());
		}
		
//		lockservice.openAllDores(1, 2);
	}
	
	public void testLookupStatusOfDoors(){
		LockerServiceImpl lockservice = new LockerServiceImpl();
		lockservice.getCwgAdressCfg().add("area=1, ip=52.4.1.229, port=8031");
		lockservice.init();
		
		Map<String, Integer> status = lockservice.lookupStatusOfDoors(1, 2);
		System.out.println(status);
	}
	
	public static void main(String[] args) {
		LockerServiceImplTest test = new LockerServiceImplTest();
		test.testOpen();
//		test.testLookupStatusOfDoors();
	}
}
