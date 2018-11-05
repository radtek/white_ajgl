package com.taiji.pubsec.ajqlc.sawp.service;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.taiji.pubsec.ajqlc.sawp.bean.OutStorageFormServiceBean;
import com.taiji.pubsec.ajqlc.sawp.model.OutStorageForm;
import com.taiji.pubsec.ajqlc.sawp.util.ParamMapUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class TestCase { 
	
	@Test
	public void test(){
		Assert.assertEquals(1, 1);
	}
	
	@Test
	public void testBeanUtil() throws ParseException {
		OutStorageFormServiceBean outStorageFormBean = new OutStorageFormServiceBean("qq", "qq", new Date().getTime(), "qq", "qq", new Date().getTime(), "qq", "qq", "qq", "qq", "qq", "qq");
		OutStorageForm outStorageForm = new OutStorageForm();
//		BeanUtils.copyProperties(outStorageFormBean, outStorageForm, new Array[]{"createdTime", "updatedTime"}));
		BeanUtils.copyProperties(outStorageFormBean, outStorageForm, new String[]{"createdTime", "updatedTime"});
		Assert.assertNotNull(outStorageForm.getId());
	}
	
	@Test
	public void testDateFormat() throws ParseException {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		String dateStr = sdf.format(date);
		System.out.println("dateStr: " + dateStr);
		
		Date dateaaa = DateUtils.parseDate("2016-01-01 12:00:00", "yyyy-MM-dd HH:mm:ss");
		String dateaaaStr = sdf.format(dateaaa);
		System.out.println("dateaaaStr: " + dateaaaStr);
	}
	
	@Test
	public void testColllections() {
		
		List<String> lista = new ArrayList<String> (0);
		lista.add("aaa");
		lista.add("bbb");
		lista.add("ccc");
		List<String> listb = new ArrayList<String> (0);
		listb.add("bbb");
		listb.add("ccc");
		listb.add("ddd");
		
		//并集
		Collection<String> unionList = CollectionUtils.union(lista, listb);
		//交集
		Collection<String> intersectionList = CollectionUtils.intersection(lista, listb);
		//交集的补集
		Collection<String> disjunctionList = CollectionUtils.disjunction(lista, listb);
		//集合相减
		Collection<String> subtractList = CollectionUtils.subtract(lista, listb);
		Collection<String> subtractList1 = CollectionUtils.subtract(listb, lista);
		
		 System.out.println("A: " + ArrayUtils.toString(lista.toArray()));
		 System.out.println("B: " + ArrayUtils.toString(listb.toArray())); 
		 System.out.println("Union(A, B): "  
	                + ArrayUtils.toString(unionList.toArray())); 
		 System.out.println("Intersection(A, B): "  
	                + ArrayUtils.toString(intersectionList.toArray())); 
		 System.out.println("Disjunction(A, B): "  
		                + ArrayUtils.toString(disjunctionList.toArray()));
		 System.out.println("Subtract(A, B): "  
	                + ArrayUtils.toString(subtractList.toArray())); 
		 System.out.println("Subtract(B, A): "  
		                + ArrayUtils.toString(subtractList1.toArray())); 
		 
		 for (String str : unionList) {
			 System.out.println("******************************************");
			 System.out.println(str);
		 }
	}
	
	@Test
	public void testNotBlank() {
		Assert.assertFalse(ParamMapUtil.isNotBlank(""));
		Assert.assertFalse(ParamMapUtil.isNotBlank(" "));
		Assert.assertTrue(ParamMapUtil.isNotBlank("aaaaa"));
		Long l = (long) 10.0;
		Date date = new Date();
		Integer i = 11;
		Assert.assertTrue(ParamMapUtil.isNotBlank(l));
		Assert.assertTrue(ParamMapUtil.isNotBlank(date));
		Assert.assertTrue(ParamMapUtil.isNotBlank(i));
		
		List<String> list = new ArrayList<String>();
		Assert.assertFalse(ParamMapUtil.isNotBlank(list));
		List<String> listb = new ArrayList<String>();
		listb.add("wwwww");
		Assert.assertTrue(ParamMapUtil.isNotBlank(listb));
	}
	
}
