package com.taiji.pubsec.ajgl.util.bean;
/**
 * 文件显示Bean
 * @author huangda
 *
 */
public class FileObjectBean {
	
	private String id;
	
	private String name;
	
	private String size;
	
	private String base64Str ;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getBase64Str() {
		return base64Str;
	}
	public void setBase64Str(String base64Str) {
		this.base64Str = base64Str;
	}
	
}
