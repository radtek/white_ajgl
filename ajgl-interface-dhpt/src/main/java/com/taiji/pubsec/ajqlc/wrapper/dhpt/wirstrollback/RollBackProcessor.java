package com.taiji.pubsec.ajqlc.wrapper.dhpt.wirstrollback;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 用于定时处理手环或定位卡的回滚
 * @author xinfan
 *
 */
public class RollBackProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(RollBackProcessor.class);
	
	private ErrorRollbackDataMap errorRollbackMap;
	
	private List<Processor> processors = new ArrayList<Processor>();
	
	private Integer maxRetried = 3;
	
	public void process(){
		List<ErrorDahuaDevice> edds = errorRollbackMap.get();
		
		for(ErrorDahuaDevice edd : edds){
			if(edd.getRetried() >= maxRetried){
				errorRollbackMap.remove(edd.getId());
				//TODO 写入专门的日志
				continue;
			}
			for(Processor p : processors){
				if( p.support(edd) ){
					p.process(edd);
				}
			}
		}
	}

	public void setProcessors(List<Processor> processors) {
		this.processors = processors;
	}

	public void setErrorRollbackMap(ErrorRollbackDataMap errorRollbackMap) {
		this.errorRollbackMap = errorRollbackMap;
	}
	
}
