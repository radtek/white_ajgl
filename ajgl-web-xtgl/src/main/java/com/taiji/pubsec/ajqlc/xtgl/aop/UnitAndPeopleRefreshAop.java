package com.taiji.pubsec.ajqlc.xtgl.aop;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

import com.taiji.pubsec.ajqlc.wrapper.common.bean.ResultBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IBasicDataService;
import com.taiji.pubsec.businesscomponent.organization.model.Account;
import com.taiji.pubsec.businesscomponent.organization.model.Unit;

@Service("unitAndPeopleRefreshAop")
@Aspect
public class UnitAndPeopleRefreshAop {

	@Resource
	private IBasicDataService basicDataService;
	
	@Pointcut("execution(* com.taiji.pubsec.businesscomponent.organization.service.UnitServiceImpl.createUnit(..))")
    public void createUnitAopPoint(){
    	
    }
    
    @After(value = "createUnitAopPoint()")
    public void afterCreateUnitAopPoint(JoinPoint jp){
    	syncAddOrgnizationInfo(jp);
    }
    
    @Pointcut("execution(* com.taiji.pubsec.businesscomponent.organization.service.UnitServiceImpl.updateUnit(..))")
    public void updateUnitAopPoint(){
    	
    }
    
    @After(value = "updateUnitAopPoint()")
    public void afterUpdateUnitAopPoint(JoinPoint jp){
    	syncUpDateOrgnizationInfo(jp);
    }
    /**
     * 同步新增单位
     * @param jp
     */
    
    private void syncAddOrgnizationInfo(JoinPoint jp){
    	Object[] args = jp.getArgs() ;
    	Unit unit = (Unit)args[0];
    	try {
			ResultBean result = basicDataService.syncAddOrgnizationInfo(unit.getId(), unit.getName(), unit.getSuperOrg().getId());
		} catch (DahuaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * 同步更新单位
     * @param jp
     */
    
    private void syncUpDateOrgnizationInfo(JoinPoint jp){
    	Object[] args = jp.getArgs() ;
    	Unit unit = (Unit)args[0];
    	try {
			ResultBean result = basicDataService.syncUpdateOrgnizationInfo(unit.getId(), unit.getName(), unit.getSuperOrg().getId());
		} catch (DahuaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @Pointcut("execution(* com.taiji.pubsec.businesscomponent.organization.service.AccountServiceImpl.createAccount(..))")
    public void createAccountAopPoint(){
    	
    }
    
    @After(value = "createAccountAopPoint()")
    public void afterCreateAccountAopPoint(JoinPoint jp){
    	syncAddPersonInfo(jp);
    }
    
    @Pointcut("execution(* com.taiji.pubsec.businesscomponent.organization.service.AccountServiceImpl.updateAccount(..))")
    public void updateAccountAopPoint(){
    	
    }
   @After(value = "updateAccountAopPoint()")
    public void afterUpdateAccountAopPoint(JoinPoint jp){
    	syncAddPersonInfo(jp);
    }
    /**
     * 同步新增账号
     * @param jp
     */
    private void syncAddPersonInfo(JoinPoint jp){
    	Object[] args = jp.getArgs() ;
    	Account account = (Account)args[0];
    	try {
			ResultBean result = basicDataService.syncAddPersonInfo(account.getAccountName(), account.getPassword(), account.getPerson().getOrganization().getId(), account.getPerson().getName(), account.getPerson().getCode());
		} catch (DahuaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
   
}
