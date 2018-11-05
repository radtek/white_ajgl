package com.taiji.pubsec.ajqlc.xtgl.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

import com.taiji.pubsec.common.tool.spring.SpringContextUtil;
import com.taiji.pubsec.springsecurity.resource.service.ResourceHolderService;

@Service("resourceRefreshAop")
@Aspect
public class ResourceRefreshAop {

    @Pointcut("execution(* **..AuthorityResourceServiceImpl.*(..))")
    public void aopPoint(){
    	
    }
    
    @After(value = "aopPoint()")
    public void after(JoinPoint jp){
    	for(ResourceHolderService rs:SpringContextUtil.getApplicationContext().getBeansOfType(ResourceHolderService.class).values()){
    		rs.reloadResources();
    	}
    }
}
