package com.taiji.pubsec.ajqlc.baq.service;

import org.springframework.security.access.annotation.Secured;

import com.taiji.pubsec.ajqlc.baq.model.TmpLeaveInfo;

/**
 * 临时离开信息服务接口
 * @author chengkai
 *
 */
public interface ITmpLeaveInfoService {
	
	/**
	 * 新增临时离开信息
	 * @param tmpLeaveInfo 临时离开信息实体
	 */
	public void save(TmpLeaveInfo tmpLeaveInfo);
	
	/**
	 * 修改临时离开信息
	 * @param tmpLeaveInfo 临时离开信息实体
	 */
	public void update(TmpLeaveInfo tmpLeaveInfo);
	
	/**
	 * 删除临时离开信息
	 * @param tmpLeaveInfoId 临时离开信息id
	 */
	public void delete(TmpLeaveInfo tmpLeaveInfo);
	
	/**
	 * 通过id查询临时离开信息
	 * @param tmpLeaveInfoId 临时离开信息id
	 * @return 临时离开信息实体
	 */
	public TmpLeaveInfo findById(String tmpLeaveInfoId);
}
