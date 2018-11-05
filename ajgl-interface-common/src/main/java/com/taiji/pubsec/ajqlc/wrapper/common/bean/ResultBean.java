package com.taiji.pubsec.ajqlc.wrapper.common.bean;

/**
 * 调用返回结果
 * @author dixiaofeng
 *
 */
public class ResultBean {
	
	private boolean result;  //调用成功还是失败结果
	
	private String errorMsg; //错误信息
	
	private String data;
	
	private String code;

	public ResultBean(boolean result) {
		this.result = result; 
	}

	public ResultBean(boolean result, String errorMsg) {
		super();
		this.result = result;
		this.errorMsg = errorMsg;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
