package com.taiji.pubsec.ajqlc.alert.service.impl;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajqlc.alert.model.AlertRule;
import com.taiji.pubsec.ajqlc.alert.service.IAlertRuleService;

/**
 * @author XIEHF
 */
@Service("alertRuleService")
@Transactional(rollbackFor = Exception.class)
public class AlertRuleServiceImpl implements IAlertRuleService {
	
	@SuppressWarnings("rawtypes")
	@Resource
	private Dao dao;
	

	@SuppressWarnings("unchecked")
	@Override
	public String saveOrUpdateAlertRule(AlertRule alertRule) {
		if (StringUtils.isEmpty(alertRule.getId())) {
			alertRule.setCreatedTime(new Date());
			alertRule.setUpdateTime(new Date());
			this.dao.save(alertRule);
		} else {
			AlertRule rule = (AlertRule) this.dao.findById(AlertRule.class, alertRule.getId());
			alertRule.setUpdateTime(new Date());
			alertRule.setStatus(rule.getStatus());
			alertRule.setDescription(rule.getDescription());
			alertRule.setName(rule.getName());
			this.dao.update(alertRule);
		}
		return alertRule.getId();
	}


	@SuppressWarnings("unchecked")
	@Override
	public AlertRule findAlertRuleByCode(String code) {
		String xql = "select ar from AlertRule as ar where ar.code = ?"; 
		return (AlertRule) this.dao.findByParams(AlertRule.class, xql, new Object[]{code});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AlertRule> findAllAlertRules() {
		return this.dao.findAll(AlertRule.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateAlertRuleStatus(String id, String zt) {
		AlertRule rule = (AlertRule) this.dao.findById(AlertRule.class, id);
		rule.setStatus(zt);
		rule.setUpdateTime(new Date());
		this.dao.update(rule);
	}

	@SuppressWarnings("unchecked")
	@Override
	public AlertRule findRuleByName(String name) {
		String xql = "select ar from AlertRule as ar where ar.name = ?"; 
		return (AlertRule) this.dao.findByParams(AlertRule.class, xql, new Object[]{name});
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<AlertRule> findRuleByTheirModulu(String theirModulu) {
		String xql = "select ar from AlertRule as ar where ar.theirModulu = ?"; 
		return this.dao.findAllByParams(AlertRule.class, xql,  new Object[]{theirModulu});
	}


	@SuppressWarnings("unchecked")
	@Override
	public AlertRule findByid(String id) {
		return (AlertRule)this.dao.findById(AlertRule.class, id);
	}

}