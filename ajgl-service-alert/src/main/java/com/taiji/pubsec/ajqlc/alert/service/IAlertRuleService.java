package com.taiji.pubsec.ajqlc.alert.service;

import java.util.List;

import com.taiji.pubsec.ajqlc.alert.model.AlertRule;

/**
	 * 预警规则服务接口
	 * @author XIEHF
	 *
	 */
public interface IAlertRuleService {
	
	/**
	 * 新增预警规则
	 * @param AlertRule 预警规则实体
	 */
	public String saveOrUpdateAlertRule(AlertRule alertRule);
	
	/**
	 * 根据预警规则code获取预警规则信息
	 * @param code 预警规则code
	 * @return 预警规则记录实体
	 */
	public AlertRule findAlertRuleByCode(String code);
	
	/**
	 * 根据id查询预警规则
	 * @param id	预警规则id
	 * @return	预警规则
	 */
	public AlertRule findByid(String id);
	
	/**
	 * 获取所有预警规则信息
	 *
	 * @return 预警规则记录实体List
	 */
	public List<AlertRule> findAllAlertRules(); 
	
	
	/**
	 * 启用停用预警规则
	 * @param code 预警规则code； zt 预警规则状态
	 */
	public void updateAlertRuleStatus(String id,String zt);
	
	/**
	 * 根据名字查询预警规则
	 * @param name	预警名称
	 * @return	预警规则
	 */
	public AlertRule findRuleByName(String name);
	
	/**
	 * 根据所属模块查询 预警规则
	 * @param theirModulu	所属模块
	 * @return	预警规则List
	 */
	public List<AlertRule> findRuleByTheirModulu(String theirModulu);
	
}