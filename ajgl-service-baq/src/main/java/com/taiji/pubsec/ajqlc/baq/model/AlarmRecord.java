package com.taiji.pubsec.ajqlc.baq.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "t_baq_yjjl")
public class AlarmRecord {
		
		@Id
		@GeneratedValue(generator = "uuid")
		@GenericGenerator(name = "uuid", strategy = "uuid2")
		private String id; // id
		
		@Column(name = "sbbm")
		private String deviceCode;  //设备编号
		  
		@Column(name = "sbmc")
		private String deviceName; //设备名称
		
		@Column(name = "bjlx")
		private String alarmType ; //报警类型

		@Column(name = "bjsj")
		@Temporal(TemporalType.TIMESTAMP)
		private Date alarmDate; //报警时间
		   
		@Column(name = "tdmc")
		private String channelName ; //通道名称
		 
		@Column(name = "tdbm")
		private  String  channelCode;//通道号
		
		@Column(name = "lxbm")
		private String typeCode;  //类型编号 [1 为手环 2 为网格 3 审讯室]
		
		@Column(name = "sbid")
		private String askDeviceId;  //请求设备ID[类型编号为1 时为手环ID  类型为2时 为网格ID 类型为3时 为审讯室Id]

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

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

		public String getChannelCode() {
			return channelCode;
		}

		public void setChannelCode(String channelCode) {
			this.channelCode = channelCode;
		}

		public String getTypeCode() {
			return typeCode;
		}

		public void setTypeCode(String typeCode) {
			this.typeCode = typeCode;
		}

		public Date getAlarmDate() {
			return alarmDate;
		}

		public void setAlarmDate(Date alarmDate) {
			this.alarmDate = alarmDate;
		}

		public String getAskDeviceId() {
			return askDeviceId;
		}

		public void setAskDeviceId(String askDeviceId) {
			this.askDeviceId = askDeviceId;
		}
}
