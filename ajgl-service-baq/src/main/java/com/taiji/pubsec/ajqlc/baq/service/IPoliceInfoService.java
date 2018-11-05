package com.taiji.pubsec.ajqlc.baq.service;

import java.util.List;

import com.taiji.pubsec.ajqlc.baq.model.PoliceInfo;

/**
 * 警员信息接口
 * @author huangda
 *
 */
public interface IPoliceInfoService {

	/**
	 * 保存警员信息，返回主键
	 * @param info 储物柜实体
	 */
	public String save(PoliceInfo info);
	
	/**
	 * 保存警员信息，返回主键
	 * @param info 储物柜实体
	 */
	public String update(PoliceInfo info);
	
	/**
	 * 删除警员信息
	 * @param id 警员信息id
	 * @return 删除结果
	 */
	public boolean delete(String id); 
	
	/**
	 * 根据id查找警员信息
	 * @param id 警员信息id
	 * @return 警员信息实体
	 */
	public PoliceInfo findById(String id);
	
	/**
	 * 根据警号查找警员信息
	 * @param code 警号
	 * @return 警员信息实体
	 */
	public PoliceInfo findByPoliceCode(String code);
	
	/**
	 * 根据id查找警员信息
	 * @param id 警员信息id
	 * @return 警员信息实体
	 */
	public PoliceInfo findByCardId(String id);
	
	/**
	 * 根据有卡的警员
	 * @param pi 查询条件 
	 * @return 警员信息实体
	 */
	public List<PoliceInfo> findPoliceWhoHasCardByCondition(PoliceInfo pi);
}
