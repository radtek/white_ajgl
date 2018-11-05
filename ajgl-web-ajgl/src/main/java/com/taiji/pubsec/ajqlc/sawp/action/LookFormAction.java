package com.taiji.pubsec.ajqlc.sawp.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.pubsec.ajgl.util.ReturnMessageAction;

/**
 * 通过类型和编号查看单据Action
 * 
 * @author WangLei
 *
 */
@Controller("lookFormAction")
@Scope("prototype")
public class LookFormAction extends ReturnMessageAction{

	private static final long serialVersionUID = 1L;
	
	private String transmitFormId;// 转发参数，单据id
	
	private String formId;// 单据id
	
	private String formType;// 单据类型

	public String checkForm(){
		if(formId != null && formType != null){
			transmitFormId = formId;
			return formType;
		}
		return "";
	}

	public String getTransmitFormId() {
		return transmitFormId;
	}

	public void setTransmitFormId(String transmitFormId) {
		this.transmitFormId = transmitFormId;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}
	
	
}
