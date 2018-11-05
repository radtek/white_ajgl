package com.taiji.pubsec.ajqlc.sla.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.sla.bean.AdministrationPersonServiceBean;
import com.taiji.pubsec.ajqlc.sla.bean.PenalPersonServiceBean;
import com.taiji.pubsec.ajqlc.sla.model.AdministrationPersonRecord;
import com.taiji.pubsec.ajqlc.sla.model.CaseAttachedInfo;

/**
 * 案件附加信息查询接口，案件到期提醒
 * @author wangfx
 *
 */
public interface ICaseAttachedInfoService {
	
	/**
	 * 创建案件到期提醒信息
	 * @param caseAttachedInfo 案件到期提醒信息
	 * @param caseId 案件id
	 */
	void createCaseAttachedInfo(CaseAttachedInfo caseAttachedInfo, String caseId);

	/**
	 * 根据案件类型（行政案件）、查询日期、案件是否关注、办案单位、案件名称查询案件到期提醒
	 * @param paramMap 查询条件map
	 * <br>:caseClass （刑事案件or行政案件）
	 * <br>:queryTimeStart 查询进入办案区时间：开始时间
	 * <br>:queryTimeEnd 查询进入办案区时间：结束时间
//	 * <br>：caseAttentionState 案件是否关注
	 * <br>： attendUnit 办案单位
	 * <br>：caseName 案件名称
	 * <br>:caseCode 案件编号
	 * </br>:disposePerson 办案民警名称或警号（模糊匹配）
	 * <br>:ifArchive 是否归档：已归档为“是”，未归档为“否”
	 * @param pageNo 页码
	 * @param pageSize 页面大小
	 * @return
	 */
	public Pager<AdministrationPersonServiceBean> findAdminPersonBeanByConditions(Map<String, Object> paramMap, int pageNo, int pageSize);

	/**
	 * 根据案件类型（刑事案件）、查询日期、案件是否关注、办案单位、案件名称查询案件到期提醒
	 * @param paramMap 查询条件map
	 * <br>:caseClass （刑事案件or行政案件）
	 * <br>:queryTimeStart 查询进入办案区时间：开始时间
	 * <br>:queryTimeEnd 查询进入办案区时间：结束时间
//	 * <br>：caseAttentionState 案件是否关注
	 * <br>： attendUnit 办案单位
	 * <br>：caseName 案件名称
	 * <br>:caseCode 案件编号
	 * </br>:disposePerson 办案民警名称或警号（模糊匹配）
	 * <br>:ifArchive 是否归档：已归档为“是”，未归档为“否”
	 * @param pageNo 页码
	 * @param pageSize 页面大小
	 * @return
	 */
	public Pager<PenalPersonServiceBean> findPenalPersonServiceBeanByConditions(Map<String, Object> paramMap, int pageNo, int pageSize);	
	
	/**
	 * 通过案件编号查询相应的案件附加信息
	 * @param caseCode 案件编号
	 * @return 返回案件附件信息
	 */
	public CaseAttachedInfo findByCaseCode(String caseCode);
	
	/**
	 * 通过案件编号查询行政案件人员记录list
	 * @param caseCode 案件编号
	 * @return 返回行政案件人员记录list
	 */
	public List<AdministrationPersonRecord> findAdminPersonRecordByCaseCode(String caseCode);
	
	/**
	 * 查询刑事案件到期提醒列表
	 * @param txType 提醒类型：
	 * 		 	<br>xj 刑拘到期提醒
	 * 			<br>bgjy 变更羁押到期提醒
	 * 			<br>tqdb 提请逮捕到期提醒			
	 * 			<br>pb 批捕到期提醒
	 * 			<br>ysqs 移送起诉到期提醒
	 * 			<br>qbhs 取保候审到期提醒
	 * 			<br>jsjz 监视居住到期提醒
	 * @param from 查询开始时间
	 * @param to 查询结束时间
	 * @param pageNo 页码
	 * @param pageSize 页面大小
	 * @return 返回刑拘到期提醒列表
	 */
	public Pager<PenalPersonServiceBean> findPenalDqtxByConditions(String txType, Date from, Date to, int pageNo, int pageSize);
	
	/**
	 * 查询行政案件到期提醒列表
	 * @param txType 提醒类型：
	 * 		 	<br>xzjl 行政拘留到期提醒
	 * 			<br>sqjd 社区戒毒到期提醒
	 * 			<br>qzjd 强制戒毒到期提醒			
	 * @param from 查询开始时间
	 * @param to 查询结束时间
	 * @param pageNo 页码
	 * @param pageSize 页面大小
	 * @return 返回刑拘到期提醒列表
	 */
	public Pager<AdministrationPersonServiceBean> findAdminDqtxByConditions(String txType, Date from, Date to, int pageNo, int pageSize);
}
