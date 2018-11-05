package com.taiji.pubsec.ajqlc.baq.service;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import com.taiji.pubsec.ajqlc.baq.model.FinallyLeaveInfo;
import com.taiji.pubsec.ajqlc.baq.model.TmpLeaveInfo;

/**
 * 最终离开信息服务接口
 * @author chengkai
 *
 */
public interface IFinallyLeaveInfoService {
	
	/**
	 * 保存最终离开信息
	 * @param finallyLeaveInfo 最终离开信息实体
	 * @param tliList 临时离开信息实体list
	 */
	public void saveFinallyLeaveInfo(FinallyLeaveInfo finallyLeaveInfo, List<TmpLeaveInfo> tliList);
	
	public void delete(FinallyLeaveInfo entity) ;
	
	/**
	 * 修改最终离开信息
	 * @param finallyLeaveInfo 最终离开信息实体
	 * @param tliList 临时离开信息实体list，当有id时修改，没id新增
	 * @param deleteIds 要删除的临时离开信息实体idList
	 */
	public void updateFinallyLeaveInfo(FinallyLeaveInfo finallyLeaveInfo, List<TmpLeaveInfo> tliList, List<String> deleteIds);
	
	/**
	 * 根据最终离开信息id查询最终离开信息
	 * @param finallyLeaveInfoId 最终离开信息id
	 * @return 最终离开信息实体
	 */
	public FinallyLeaveInfo findFinallyLeaveInfoById(String finallyLeaveInfoId);
	
	/**
	 * 根据使用单id查询最终离开信息
	 * @param handlingAreaReceiptId 使用单id
	 * @return 最终离开信息实体
	 */
	public FinallyLeaveInfo findFinallyLeaveInfoByHandlingAreaReceiptId(String handlingAreaReceiptId);
	
}
