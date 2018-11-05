package com.taiji.pubsec.ajgl.dbtx.task.util;

public class ReceiveSubjectPojo {
	
	protected String targetId ;

	/**
	 * 出于安全性考虑，前台传回后台只传标识，不传完整的包名加类名
	 * 例如前台传回后台role，action层转化成com.taiji.pubsec.businesscomponent.authority.model.Role 
	 */
	protected String type ;

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}
