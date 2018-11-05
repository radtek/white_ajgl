package com.taiji.pubsec.ajqlc.security;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taiji.pubsec.springsecurity.resource.service.ResourceHolderService;



/**
 * Created by huangcheng on 2017/4/27.
 */
public class ResourceRefreshAop {

    public static final Logger LOGGER = LoggerFactory.getLogger(ResourceRefreshAop.class) ;

    @Resource
    private ResourceHolderService resourceHolderService ;

    public void doAfterReturning(JoinPoint jp, Object retVal){
        resourceHolderService.reloadResources();
    }
}
