package com.taiji.pubsec.ajqlc.wrapper.dhpt.service.impl;

import static org.junit.Assert.*;

import java.util.List;

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
import com.taiji.pubsec.ajqlc.wrapper.common.bean.ResultBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IBasicDataService;
import com.taiji.pubsec.businesscomponent.organization.model.Account;
import com.taiji.pubsec.businesscomponent.organization.model.Department;
import com.taiji.pubsec.businesscomponent.organization.model.Unit;
import com.taiji.pubsec.businesscomponent.organization.service.IAccountService;
import com.taiji.pubsec.businesscomponent.organization.service.IDepartmentService;
import com.taiji.pubsec.businesscomponent.organization.service.IUnitService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-basic.xml")
@Transactional(rollbackFor=Exception.class)
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,TransactionDbUnitTestExecutionListener.class })
public class BasicDataServiceImplTest {
	@Resource 
	private  IUnitService  unitService;
	@Resource
	private  IAccountService accountService;
	@Resource
	private  IBasicDataService  basicDataServiceImpl;
	@Resource
	private  IDepartmentService  departmentService;
	@Test
	public void testSyncAddOrgnizationInfo() throws DahuaException {
		List<Unit> unitLists = unitService.findSubUnitsByUnitId("01");
		for(Unit unitTemp :unitLists){
			if(unitTemp.getId().equals("01")){
				continue;
			}
			ResultBean  res= basicDataServiceImpl.syncAddOrgnizationInfo(unitTemp.getId(), unitTemp.getShortName(), "01");
		    System.out.println(res.isResult());
		}
		
		List<Department> depLists = departmentService.findAll();
		for(Department depTemp :depLists){
			ResultBean  res= basicDataServiceImpl.syncAddOrgnizationInfo(depTemp.getId(), depTemp.getShortName(), depTemp.getSuperOrg().getId());
			System.out.println(res.isResult());
		}
		
	}
	@Test
	public void testSyncAddOrgnizationsInfo() throws DahuaException {
		List<Department> depLists = departmentService.findAll();
		for(Department depTemp :depLists){
			ResultBean  res= basicDataServiceImpl.syncAddOrgnizationInfo(depTemp.getId(), depTemp.getShortName(), depTemp.getSuperOrg().getId());
			System.out.println(res.isResult());
		}
	}

	@Test
	public void testSyncAddPersonInfo() throws DahuaException {
		List<Account>  accounts = accountService.findAll();
		for(Account accoutTemp: accounts )
		{
			ResultBean res = basicDataServiceImpl.syncAddPersonInfo(accoutTemp.getAccountName(), 
					accoutTemp.getPassword(), accoutTemp.getPerson().getOrganization().getId(), 
					accoutTemp.getPerson().getName(), accoutTemp.getPerson().getCode());
			System.out.println(res.isResult());
		}
	}

	@Test
	public void testSyncUpdateOrgnizationInfo() {
		fail("Not yet implemented");
	}

	@Test
	public void testSyncUpdatePersonInfo() {
		fail("Not yet implemented");
	}

}
