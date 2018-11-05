package com.taiji.pubsec.ajgl.tool.comet.example.push.handler;

import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.oort.Oort;
import org.cometd.oort.Seti;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.taiji.pubsec.common.tool.comet.handler.DefaultPushedMsgHandler;
import com.taiji.pubsec.common.tool.comet.model.DefaultHintMsg;
import com.taiji.pubsec.common.tool.comet.service.CometdBroadcastService;
import com.taiji.pubsec.common.tool.comet.service.CometdPushService;

@Service
public class ExampleHandler extends DefaultPushedMsgHandler{

	private static final Logger logger = LoggerFactory.getLogger(ExampleHandler.class);
	
	public final static String MARK_NOTIFY = "Comet_Example_Handler";
	public final static String CLIENT_ID_SUFFIX = "Example";
	
	@Resource
	private Oort oort;
	@Resource
	private Seti seti;
	@Resource
	private BayeuxServer bayeux;
	
	@Override
	public void handle(Object msgObj) {
		
		Set<String> userSet = seti.getUserIds() ;
		
		if(!userSet.contains("test_push-" + CLIENT_ID_SUFFIX)){
			logger.debug("clientid:"+"test_push-" + CLIENT_ID_SUFFIX+" 不在线");
			return ;
		}
		
		Object[] obj = (Object[])msgObj ;
		String a1 = (String)obj[0] ;
		String a2 = (String)obj[1] ;
		String ret = (String)obj[2] ;
		
		JSONObject js = new JSONObject() ;
		js.put("a1", a1) ;
		js.put("a2", a2) ;
		js.put("ret", ret) ;
		
		this.cometdPushService.pushHint("test_push-" + CLIENT_ID_SUFFIX, new DefaultHintMsg(js.toString()));
		this.cometdBroadcastService.pushBroadcast(new DefaultHintMsg(js.toString()));
	}
	
	@Override
	public String getMark(){
		return MARK_NOTIFY;
	}
	
	@Override
	@Resource(name="defaultCometdPushService")
	public void setCometdPushService(CometdPushService cometdPushService) {
		super.cometdPushService = cometdPushService ;
	}
	
	@Override
	@Resource(name="defaultCometdBroadcastService")
	public void setCometdBroadcastService(
			CometdBroadcastService cometdBroadcastService) {
		this.cometdBroadcastService = cometdBroadcastService;
	}
}
