package com.taiji.pubsec.ajqlc.sawp.service;

import java.util.List;
import java.util.Map;

import com.taiji.pubsec.ajqlc.sawp.bean.CriminalBasicCaseBean;
import com.taiji.pubsec.ajqlc.sawp.bean.DocBean;
import com.taiji.pubsec.ajqlc.sawp.model.ImpoundedObject;

/**
 * 扣押物品查询接口
 * @author WangLei
 *
 */
public interface IImpoundedObjectService {
	
	/**
	 * 
	 * @param caseId 案件编号
	 * @return 扣押物品
	 */
	public ImpoundedObject findCaseNameByCaseId(String caseId);
	
	/**
	 * 通过案件id查询案件基本信息和相应的嫌疑人
	 * @param caseId 案件id
	 * @return 返回案件bean
	 */
	public CriminalBasicCaseBean findCriminalBasicCaseBeanForInStorageByCaseId(String caseId);
	
	/**
	 * 通过案件id和嫌疑人id查询对应的扣押物品信息
	 * @param caseId 案件id
	 * @param suspectId 嫌疑人id
	 * @return 返回扣押物品List
	 */
	public List<ImpoundedObject> findImpodundedObjectsByCaseIdAndSuspectId(String caseId, String suspectId);
	                             
	/**
	 * 通过案件id和嫌疑人id查询对应文书
	 * @param caseId 案件id
	 * @param suspectId 嫌疑人id
	 * @return 返回卷宗文书list
	 */
	public List<DocBean> findArchivedFilesByCaseIdAndSuspectId(String caseId, String suspectId);
	
	/**
	 * 通过案件id查询扣押物品信息
	 * @param caseId 案件id
	 * @return 返回Map<String, List<ImpoundedObject>>，string为嫌疑人id，list<ImpoundedObject>为相应嫌疑人的扣押物品信息
	 */
	public Map<String, List<ImpoundedObject>> findImpoundedObjectsByCaseId(String caseId);
	
	/**
	 * 通过物品编号查询扣押物品信息
	 * @param code 物品编号
	 * @return 返回扣押物品信息
	 */
	public ImpoundedObject findImpoundedObjectByCode(String code);
	
	

}
