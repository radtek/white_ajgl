package com.taiji.pubsec.ajqlc.baq.service;

import java.util.List;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.baq.model.IllegalType;

/**
	 * 违规类型接口
	 * @author XIEHF
	 *
	 */
public interface IIllegalTypeService {
	
	/**
	 * 保存违规类型
	 * @param illegalType 违规类型
	 */
	public void createIllegalType(IllegalType illegalType);

	/**
	 * 更新违规类型
	 * @param illegalType 违规类型
	 */
	public void updateIllegalType(IllegalType illegalType);

	/**
	 * 删除违规类型
	 * @param ids 违规类型ids
	 * @return String 不能删除的违规类型Name
	 */
	public String deleteIllegalType(String... ids);

	/**
	 * 校验违规类型名称是否重复
	 * @param name 名称
	 */
	public boolean isDistinctIllegalTypeName(String name,String id);

	/**
	 * 校验违规类型编码是否重复
	 * @param code 待校验编码
	 */
	public boolean isDistinctIllegalTypeCode(String code,String id);
	
	/**
	 * 更新违规类型状态
	 * @param id 编码   status 状态  operator 操作人
	 */
	public void updateIllegalTypeState(String id, String state,String operator);

	/**
	 * 通过code获取违规类型
	 * @param code 编码
	 */
	public IllegalType findIllegalTypeByCode(String code);
	
	/**
	 * 通过Id获取违规类型
	 * @param id
	 */
	public IllegalType findIllegalTypeById(String id);
	
	/**
	 * 查询违规类型集合
	 * @param status 状态;qtIncluded 是否包含 【其他】
	 */
	public List<IllegalType> findAllIllegalTypeByStatus(String status,boolean qtIncluded);
	/**
	 * 分页查询违规类型
	 * @param status 状态
	 */
	public Pager<IllegalType> findAllIllegalTypeByStatusForPage(String status,int pageNo, int pageSize);

	
}