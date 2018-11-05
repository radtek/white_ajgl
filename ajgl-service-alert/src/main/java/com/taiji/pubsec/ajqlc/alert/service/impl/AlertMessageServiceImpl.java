package com.taiji.pubsec.ajqlc.alert.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.alert.model.AlertMessage;
import com.taiji.pubsec.ajqlc.alert.service.IAlertMessageService;
import com.taiji.pubsec.common.tool.sql.SQLTool;

/**
 * @author XIEHF
 */
@Service("alertMessageService")
@Transactional(rollbackFor = Exception.class)
public class AlertMessageServiceImpl implements IAlertMessageService {
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AlertMessageServiceImpl.class);
	
	private static final String ISSIGNED_YES = "1"; //签收状态：已签收
	private static final String ISSIGNED_NO = "0"; //签收状态：未签收
	private static final String ALERT_RULE_BAQ = "0000000013007"; //预警规则ID：进入办案区8小时预警
	
	/**
	 * 查询预警
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Pager<AlertMessage> searchMessageByPersonId(String personId, Date Start, Date end, int pageNo, int pageSize){
		Object[] paramValues = {personId, Start, end};
		return this.dao.findByPage(AlertMessage.class, paramValues, "select a from AlertMessage as a where a.receiver.id = ? and a.createdTime > ? and a.createdTime < ? order by a.createdTime desc", pageNo, pageSize);
	}
	
	/**
	 * 新增预警消息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void saveMessage(AlertMessage message){
		dao.save(message);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean signMessage(String messageId) {
		try {
			AlertMessage message = (AlertMessage) dao.findById(AlertMessage.class, messageId);
			message.setSignTime(new Date());
			message.setStatus(ISSIGNED_YES);
			dao.update(message);
		} catch (Exception e) {
			LOGGER.debug("签收失败！", e);
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AlertMessage> findNewMessagesByConditions(String accountName, Date queryTime) {
		StringBuilder xql = new StringBuilder("select m from AlertMessage as m, Account as a where m.receiver.id = a.person.id and a.accountName = ? ");
		if(queryTime != null){
			xql.append(" and m.createdTime > ?");
			xql.append(" order by m.createdTime desc");
			return dao.findAllByParams(AlertMessage.class, xql.toString(), new Object[]{accountName, queryTime});
		}
		xql.append(" order by m.createdTime desc");
		return dao.findAllByParams(AlertMessage.class, xql.toString(), new Object[]{accountName});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Boolean, List<AlertMessage>> findOldMessagesByConditions(String accountName, Date queryTime, int size) {
		Map<Boolean, List<AlertMessage>> map = new HashMap<Boolean, List<AlertMessage>>();
		StringBuilder xql = new StringBuilder("select m from AlertMessage as m, Account as a where m.receiver.id = a.person.id and a.accountName = ? ");
		List<AlertMessage> messages = new ArrayList<AlertMessage>();
		if(queryTime != null){
			xql.append(" and m.createdTime < ?");
			xql.append(" order by m.createdTime desc");
			Pager<AlertMessage> pagerMessages = dao.findByPage(AlertMessage.class, new Object[]{accountName, queryTime}, xql.toString(), 0, size);
			messages = pagerMessages.getPageList();
		}else{
			xql.append(" order by m.createdTime desc");
			Pager<AlertMessage> pagerMessages = dao.findByPage(AlertMessage.class, new Object[]{accountName}, xql.toString(), 0, size);
			messages = pagerMessages.getPageList();
		}
		List<AlertMessage> messages1 = new ArrayList<AlertMessage>();
		StringBuilder xql1 = new StringBuilder("select m from AlertMessage as m, Account as a where m.receiver.id = a.person.id and a.accountName = ? ");
		if(queryTime != null){
			xql1.append(" and m.createdTime < ?");
			xql1.append(" order by m.createdTime desc");
			Pager<AlertMessage> pagerMessages = dao.findByPage(AlertMessage.class, new Object[]{accountName, queryTime}, xql.toString(), 0, size + 1);
			messages1 = pagerMessages.getPageList();
		}else{
			xql1.append(" order by m.createdTime desc");
			Pager<AlertMessage> pagerMessages = dao.findByPage(AlertMessage.class, new Object[]{accountName}, xql.toString(), 0, size + 1);
			messages1 = pagerMessages.getPageList();
		}
		if(messages1.size() > messages.size()){
			map.put(true, messages);
		}else{
			map.put(false, messages);
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AlertMessage> findAlertMessagesByReceiptCode(String handlingAreaReceiptCode) {
		StringBuilder xql = new StringBuilder("select a from AlertMessage as a where 1 = 1 ");
		Map<String, Object> xqlMap = new HashMap<String, Object>();
		
		xql.append(" and a.alertRule.id = :id ");
		xqlMap.put("id", ALERT_RULE_BAQ);
		
		xql.append(" and a.content like :content");
		SQLTool.SQLAddEscape(xql);
		xqlMap.put("content", "%" + SQLTool.SQLSpecialChTranfer(handlingAreaReceiptCode) + "%");
		
		xql.append(" order by a.createdTime desc");
		
		return dao.findAllByParams(AlertMessage.class, xql.toString(), xqlMap);
	}

	
	
}