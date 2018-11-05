package com.taiji.pubsec.ajqlc.wrapper.dhpt.Bean;

public class RoomBean {
			private String uuid;//审讯室编号UUID；
			private String roomName;//审讯室名称；
			private String roomType;//审讯室类别，{1：询问室 ，2：讯问室}；
			private String roomIp;//审讯主机ip；
			private String roomPort;//审讯主机端口；
			private String roomIsOnline;//审讯室在线状态，{0：离线，1：在线}；
			private String roomStatus;//审讯室使用情况，{0：空闲，1：使用}
			public String getUuid() {
				return uuid;
			}
			public void setUuid(String uuid) {
				this.uuid = uuid;
			}
			public String getRoomName() {
				return roomName;
			}
			public void setRoomName(String roomName) {
				this.roomName = roomName;
			}
			public String getRoomType() {
				return roomType;
			}
			public void setRoomType(String roomType) {
				this.roomType = roomType;
			}
			public String getRoomIp() {
				return roomIp;
			}
			public void setRoomIp(String roomIp) {
				this.roomIp = roomIp;
			}
			public String getRoomPort() {
				return roomPort;
			}
			public void setRoomPort(String roomPort) {
				this.roomPort = roomPort;
			}
			public String getRoomIsOnline() {
				return roomIsOnline;
			}
			public void setRoomIsOnline(String roomIsOnline) {
				this.roomIsOnline = roomIsOnline;
			}
			public String getRoomStatus() {
				return roomStatus;
			}
			public void setRoomStatus(String roomStatus) {
				this.roomStatus = roomStatus;
			}
			
}
