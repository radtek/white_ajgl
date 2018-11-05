package com.taiji.pubsec.ajqlc.wrapper.dhpt.wirstrollback;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorRollbackDataMap {
	
	Map<String, ErrorDahuaDevice> map = new HashMap<String, ErrorDahuaDevice>();
	
	public void put(ErrorDahuaDevice device){
		map.put(device.getId(), device);
	}
	
	public List<ErrorDahuaDevice> get(){
		ErrorDahuaDevice[] a = new ErrorDahuaDevice[map.size()];
		return Arrays.asList(map.values().toArray(a));
	}
	
	public void remove(String wristid){
		map.remove(wristid);
	}

}
