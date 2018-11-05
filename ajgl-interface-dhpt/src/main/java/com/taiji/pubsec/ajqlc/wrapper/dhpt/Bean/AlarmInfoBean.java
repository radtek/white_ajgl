package com.taiji.pubsec.ajqlc.wrapper.dhpt.Bean;

public class AlarmInfoBean {
	
   private String deviceCode;  //设备编号
   
   private String deviceName; //设备名称
   
   private String channelName ; //通道名称
   
   private String alarmType ; //报警类型
   
   private String alarmDate; //报警时间
   
   private  String  channelCode;//通道号
   
   private String braceletPhysicalId; //物理手环ID

public String getDeviceCode() {
	return deviceCode;
}

public void setDeviceCode(String deviceCode) {
	this.deviceCode = deviceCode;
}

public String getDeviceName() {
	return deviceName;
}

public void setDeviceName(String deviceName) {
	this.deviceName = deviceName;
}

public String getChannelName() {
	return channelName;
}

public void setChannelName(String channelName) {
	this.channelName = channelName;
}

public String getAlarmType() {
	return alarmType;
}

public void setAlarmType(String alarmType) {
	this.alarmType = alarmType;
}

public String getAlarmDate() {
	return alarmDate;
}

public void setAlarmDate(String alarmDate) {
	this.alarmDate = alarmDate;
}

public String getChannelCode() {
	return channelCode;
}

public void setChannelCode(String channelCode) {
	this.channelCode = channelCode;
}

public String getBraceletPhysicalId() {
	return braceletPhysicalId;
}

public void setBraceletPhysicalId(String braceletPhysicalId) {
	this.braceletPhysicalId = braceletPhysicalId;
}
   
}
