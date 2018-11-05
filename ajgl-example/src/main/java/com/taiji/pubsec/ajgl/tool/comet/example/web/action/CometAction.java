package com.taiji.pubsec.ajgl.tool.comet.example.web.action;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.pubsec.ajgl.tool.comet.example.push.service.PushTestService;
import com.taiji.pubsec.common.web.action.BaseAction;

@Controller("cometAction")
@Scope("prototype")
public class CometAction extends BaseAction{
	
	private static final Logger logger = LoggerFactory.getLogger(CometAction.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8446064261967283325L;
	
	@Resource
	private PushTestService pushTestService ;
	
	
	public void pushTest(){
/*		Object obj = servletContext.getAttribute("org.cometd.bayeux") ;
		Object obj1 = servletContext.getAttribute("org.cometd.oort.Oort") ;*/
		pushTestService.pushTest("a1", "a2") ;
	}
	
	public void publishTest(){
		//pushTestService.publishTest("p1", "p2") ;
	}

}
