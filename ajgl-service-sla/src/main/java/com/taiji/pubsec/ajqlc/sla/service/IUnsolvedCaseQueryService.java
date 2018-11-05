package com.taiji.pubsec.ajqlc.sla.service;

import java.util.List;
import java.util.Map;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.sla.bean.UnsolvedCaseServiceBean;
import com.taiji.pubsec.businesscomponent.dictionary.model.DictionaryItem;

/**
 * 未破案件月报表查询接口
 * @author chengkai
 *
 */
public interface IUnsolvedCaseQueryService {
	
	/**
	 * 根据案件类别（刑事案件or行政案件）、查询月份、案件编号或名称、、案件类别查询未破案件月报表
	 * @param paramMap 查询条件map
	 * <br>:queryTime 查询时间
	 * <br>:caseClass （刑事案件or行政案件）
//	 * <br>：caseTimeStart 发案时间起
//	 * <br>:caseTimeEnd 发案时间止
	 * <br>： caseCodeOrName 案件编号或名称
	 * <br>： attendUnit 办案单位
	 * <br>：caseSort 案件类别
	 * @param pageNo 页码
	 * @param pageSize 页面大小                      
	 * @return
	 */
//	Map<Pager<UnsolvedCaseServiceBean>, Long> findUnsolvedCaseByConditions(Map<String, Object> paramMap, int pageNo, int pageSize);
	
	/**
	 * 通过父级字典项code查询所有子级（包含子级的子级的子级...）字典项
	 * @param supCode 父级字典项code
	 * @param type 字典类型code
	 * @return 返回子级字典项list
	 */
	public List<DictionaryItem> findAllSubDictItemsBySupCode(String supCode, String type);
	
	/**
	 * 根据案件类型（刑事案件or行政案件）查询未破案件月报表
	 * @param paramMap 查询条件map
	 * <br>:queryTimeStart 查询开始时间
	 * <br>:queryTimeEnd 查询开始结束
	 * <br>:caseClass （刑事案件or行政案件）
	 * <br>： caseCodeOrName 案件编号或名称
	 * <br>:disposePerson 办案民警名称或警号（模糊匹配）
	 * <br>： attendUnit 办案单位
	 * <br>：caseSort 案件类别
	 * <br>: ifSolved 是否破案/结案
	 * @param pageNo 页码
	 * @param pageSize 页面大小 
	 * @return 返回未破案件列表信息
	 */
	public Map<Pager<UnsolvedCaseServiceBean>, Long> findUnsolvedCaseByConditions(Map<String, Object> paramMap, int pageNo, int pageSize);
}
