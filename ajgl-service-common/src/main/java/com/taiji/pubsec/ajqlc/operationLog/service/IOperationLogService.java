package com.taiji.pubsec.ajqlc.operationLog.service;

import java.util.Map;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.operationLog.model.OperationLog;

/**
 * 操作日志服务接口
 * @author 50216
 *
 */
public interface IOperationLogService {
	
	/**
	 * 新增操作日志
	 * @param operationLog 操作日志
	 */
	public void save(OperationLog operationLog);
	
	/**
	 * 通过条件查询操作日志
	 * @param xqlMap 查询条件，包括：
	 * <br>:queryTimeStart 查询时间起始
	 * <br>:queryTimeEnd 查询时间截止
	 * <br>:userName 用户名
	 * <br>:functionMenuName 功能菜单名称
	 * <br>:clientIp 客户端IP
	 * @param pageNo 页码
	 * @param pageSize 页面大小
	 * @return 返回操作日志分页信息
	 */
	public Pager<OperationLog> findOperationLogByConditions(Map<String, Object> xqlMap, int pageNo, int pageSize);
}
