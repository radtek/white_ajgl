package com.taiji.pubsec.ajqlc.httpinterface.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.pubsec.ajgl.util.PageCommonAction;
import com.taiji.pubsec.ajqlc.alert.model.AlertMessage;
import com.taiji.pubsec.ajqlc.alert.service.IAlertMessageService;
import com.taiji.pubsec.ajqlc.httpinterface.action.bean.MessageBean;
import com.taiji.pubsec.ajqlc.sla.model.CriminalBasicCase;
import com.taiji.pubsec.ajqlc.sla.model.CriminalPerson;
import com.taiji.pubsec.ajqlc.sla.service.ICriminalCaseService;
import com.taiji.pubsec.ajqlc.sla.service.ICriminalPersonService;

/**
 * 预警PDAAction
 * @author huangda
 */
@Controller("warningForPDAAction")
@Scope("prototype")
public class WarningForPDAAction extends PageCommonAction {

	private static final long serialVersionUID = 1L;
	
	private static Logger LOGGER = LoggerFactory
			.getLogger(WarningForPDAAction.class);
	
	@Resource
	private IAlertMessageService alertMessageService;
	
	@Resource
	private ICriminalCaseService criminalCaseService;
	
	@Resource
	private ICriminalPersonService criminalPersonService;
	
	private Date queryTime; //查询时间
	
	private List<MessageBean> mbList = new ArrayList<MessageBean>(); //消息bean的list
	
	private int size;	//页面大小
	
	private String accountName;	//用户名
	
	private String messageId; //消息id
	
	private String flag; 	//
	
	public String signMessage(){
		boolean bl = alertMessageService.signMessage(messageId);
		if(bl){
			flag = "true";
		}else{
			flag = "false";
		}
		return SUCCESS;
	}
	
	public String findNewMessagesByConditions(){
		List<AlertMessage> messages = alertMessageService.findNewMessagesByConditions(accountName, queryTime);
		for(AlertMessage message: messages){
			MessageBean bean = this.AlertMessageToBean(message);
			mbList.add(bean);
		}
		return SUCCESS;
	}
	
	public String findOldMessagesByConditions(){
		Map<Boolean, List<AlertMessage>> map = alertMessageService.findOldMessagesByConditions(accountName, queryTime, size);
		boolean bl = map.keySet().iterator().next();
		if(bl){
			flag = "true";
		}else{
			flag = "false";
		}
		List<AlertMessage> messages = map.get(bl);
		for(AlertMessage message: messages){
			MessageBean bean = this.AlertMessageToBean(message);
			mbList.add(bean);
		}
		return SUCCESS;
	}
	
	private MessageBean AlertMessageToBean(AlertMessage message){
		MessageBean bean = new MessageBean();
		CriminalBasicCase basicCase = criminalCaseService.findCriminalCaseByCaseId(message.getCaseCode());
		CriminalPerson person = criminalPersonService.findById(message.getSuspectId());
		if(basicCase != null){
			bean.setCaseName(basicCase.getCaseName());
			bean.setDetails(basicCase.getDetails());
		}
		if(person != null){
			bean.setSuspectName(person.getName());
		}
		bean.setCaseCode(message.getCaseCode());
		bean.setContent(message.getContent());
		bean.setCreatedTime(message.getCreatedTime());
		bean.setId(message.getId());
		bean.setIsSigned(message.getStatus());
		bean.setSource("案件管理");
		bean.setTitle(""); 
		bean.setType(message.getAlertRule().getName());
		
		return bean;
	}

	public Date getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(Date queryTime) {
		this.queryTime = queryTime;
	}

	public List<MessageBean> getMbList() {
		return mbList;
	}

	public void setMbList(List<MessageBean> mbList) {
		this.mbList = mbList;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
