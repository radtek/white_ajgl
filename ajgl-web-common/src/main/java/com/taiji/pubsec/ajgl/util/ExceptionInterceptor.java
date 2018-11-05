package com.taiji.pubsec.ajgl.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class ExceptionInterceptor extends MethodFilterInterceptor{

	private static final long serialVersionUID = -6526749429735083373L;
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(ExceptionInterceptor.class);

	@Override
	protected String doIntercept(ActionInvocation actioninvocation) throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		try{
			return actioninvocation.invoke();
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return "errorException" ;
		}
		
		
	}

}
