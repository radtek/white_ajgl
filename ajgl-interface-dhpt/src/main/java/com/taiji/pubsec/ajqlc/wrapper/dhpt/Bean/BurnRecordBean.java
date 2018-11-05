package com.taiji.pubsec.ajqlc.wrapper.dhpt.Bean;
/**
 * 刻录记录Bean
 * @author xinfan
 *
 */
public class BurnRecordBean {
	 //操作用户编号
	 private String userUUID;
	 //操作用户姓名
	 private String userName;
	 //操作时间
	 private String createDate;
	 //操作内容
	 private String optContent;
	public String getUserUUID() {
		return userUUID;
	}
	public void setUserUUID(String userUUID) {
		this.userUUID = userUUID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getOptContent() {
		return optContent;
	}
	public void setOptContent(String optContent) {
		this.optContent = optContent;
	}
	 
}
