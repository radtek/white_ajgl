package com.taiji.pubsec.ajqlc.wrapper.jzpt.util.Bean;


/**
 * 此类Bean 主要用于以 <Sinomessage>开头的请求报文
 * 请求报文Bean
 * @author xinfan
 *
 */
public class QqbwSinoBean {
	/**
	 * 资源调用ID  
	 */
	private String resourceID;
	/**
	 * 请求方ID
	 */
	private String serviceID;
	/**
	 * 请求方IP地址
	 */
	private String ip;
	/**
	 * 访问用户姓名
	 */
	private String fwyhxm;
	/**
	 * 访问用户的身份证号
	 */
	private String fwyhsfzh;
	/**
	 * 访问用户的所在单位代码
	 */
	private String fwyhdwdm;
	/**
	 * 传入查询的sql
	 */
	private String sql;
	
	/**
	 * 返回结果定义
	 */
	private String result;
	public String getResourceID() {
		return resourceID;
	}
	public void setResourceID(String resourceID) {
		this.resourceID = resourceID;
	}
	public String getServiceID() {
		return serviceID;
	}
	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getFwyhxm() {
		return fwyhxm;
	}
	public void setFwyhxm(String fwyhxm) {
		this.fwyhxm = fwyhxm;
	}
	public String getFwyhsfzh() {
		return fwyhsfzh;
	}
	public void setFwyhsfzh(String fwyhsfzh) {
		this.fwyhsfzh = fwyhsfzh;
	}
	public String getFwyhdwdm() {
		return fwyhdwdm;
	}
	public void setFwyhdwdm(String fwyhdwdm) {
		this.fwyhdwdm = fwyhdwdm;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
