package com.taiji.pubsec.ajqlc.sla.service;

import java.util.List;

import com.taiji.pubsec.ajqlc.sla.model.CaseExecutionProcess;

/**
 * 案件执行过程接口
 * @author wangfx
 *
 */
public interface ICaseExecutionProcessService {
	
	/**
	 * 根据案件id和嫌疑人姓名查询案件执行过程，执行过程按时间倒序
	 * @param caseId 案件id
	 * @param suspectName 嫌疑人姓名
	 * @return 案件执行过程信息列表
	 */
	List<CaseExecutionProcess> findCaseExecutionProcessByCaseAndSuspect(String caseId, String suspectName);

	/**
	 * 根据案件id查询案件执行过程，执行过程按时间倒序
	 * @param caseId 案件id
	 * @return 案件执行过程信息列表
	 */
	List<CaseExecutionProcess> findCaseExecutionProcessByCase(String caseId);
	
	/**
	 * 根据嫌疑人id查询案件执行过程，执行过程按时间倒序
	 * @param personId 嫌疑人id
	 * @return 案件执行过程信息列表
	 */
	List<CaseExecutionProcess> findCaseExecutionProcessByPerson(String personId);
}
