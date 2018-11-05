package com.taiji.pubsec.ajqlc.exceptionLog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 异常日志表
 * @author chengkai
 *
 */
@Entity
@Table(name = "t_ycrz")
public class ExceptionLog {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid",strategy = "uuid2")
	private String id;
	
	@Column(name = "ffr")
	private String functionName;	//方法全路径名
	
	@Column(name = "ycxx")
	private String exceptionInfo;	//异常信息
	
	@Column(name = "fssj")
	@Temporal(TemporalType.TIMESTAMP)
	private Date happeningTime; // 发生时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getExceptionInfo() {
		return exceptionInfo;
	}

	public void setExceptionInfo(String exceptionInfo) {
		this.exceptionInfo = exceptionInfo;
	}

	public Date getHappeningTime() {
		return happeningTime;
	}

	public void setHappeningTime(Date happeningTime) {
		this.happeningTime = happeningTime;
	}
	
	
}
