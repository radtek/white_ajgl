package com.taiji.pubsec.ajgl.tool.comet.example.push.service.impl;

import org.springframework.stereotype.Service;

import com.taiji.pubsec.ajgl.tool.comet.example.push.handler.ExampleHandler;
import com.taiji.pubsec.ajgl.tool.comet.example.push.service.PushTestService;
import com.taiji.pubsec.common.tool.comet.annotation.PushMsg;

@Service("pushTestService")
public class PushTestServiceImpl implements PushTestService{
	
	@PushMsg(mark=ExampleHandler.MARK_NOTIFY, msgObj="new Object[]{#arg0, #arg1, #ret}")
	public String pushTest(String t1, String t2){
		
		return "example_ret" ;
	}

}
