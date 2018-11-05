package com.taiji.pubsec.ajqlc.wrapper.jzpt.service.impl;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.taiji.pubsec.ajqlc.wrapper.jzpt.service.bean.BasicPersonBean;
import com.taiji.pubsec.ajqlc.wrapper.jzpt.service.bean.CriminalRecordBean;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SuspectServiceImplTest {
	@Resource
	private SuspectServiceImpl personInfoService;
	@Resource
	private SuspectServiceImpl suspectPersonServiceImpl;
	@Test
	public void testFindBasicInfo() {
		BasicPersonBean  basicPersonBean = personInfoService.findBasicInfo("430522198909042758");
		System.out.println(basicPersonBean.getPicture());
	}

	@Test
	public void testFindCriminalRecord() {
		List<CriminalRecordBean> criminalRecord = suspectPersonServiceImpl.findCriminalRecord("430522198909042758");
		for(int i = 0; i < criminalRecord.size(); i++){
			System.out.println(criminalRecord.get(i).getCaseName());
		}
	}

}
