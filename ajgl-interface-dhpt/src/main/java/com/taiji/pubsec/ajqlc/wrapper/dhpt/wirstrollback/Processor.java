package com.taiji.pubsec.ajqlc.wrapper.dhpt.wirstrollback;

public interface Processor {
	
	void process(ErrorDahuaDevice edd);

	boolean support(ErrorDahuaDevice edd);
}
