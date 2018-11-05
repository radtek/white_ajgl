package com.taiji.pubsec.ajqlc.message.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajgl.util.ReturnMessageAction;
import com.taiji.pubsec.ajqlc.alert.model.AlertMessage;
import com.taiji.pubsec.ajqlc.alert.service.IAlertMessageService;
import com.taiji.pubsec.ajqlc.message.action.bean.AlertMessageBean;


@Controller("alertMessageAction")
@Scope("prototype")
public class AlertMessageAction extends ReturnMessageAction {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(AlertMessageAction.class);
	
	@Resource
	private IAlertMessageService alertMessageService;
	
	private Long startTime;
	
	private Long endTime;
	
	private List<AlertMessageBean> amLst = new ArrayList<AlertMessageBean>();
	
	public String findMessage(){
		Pager<AlertMessage> pager = alertMessageService.searchMessageByPersonId(this.findCurrentPerson().getId(), new Date(startTime), new Date(endTime), this.getStart() / this.getLength(), this.getLength());
		for (AlertMessage entity : pager.getPageList()) {
			AlertMessageBean bean = new AlertMessageBean();
			bean.setId(entity.getId());
			bean.setContent(entity.getContent());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			bean.setCreatedTime(sdf.format(entity.getCreatedTime()));
			amLst.add(bean);
		}
		this.setTotalNum(pager.getTotalNum());
		return SUCCESS;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public List<AlertMessageBean> getAmLst() {
		return amLst;
	}

	public void setAmLst(List<AlertMessageBean> amLst) {
		this.amLst = amLst;
	}
	
}
