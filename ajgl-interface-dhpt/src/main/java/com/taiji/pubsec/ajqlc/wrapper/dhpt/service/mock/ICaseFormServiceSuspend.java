package com.taiji.pubsec.ajqlc.wrapper.dhpt.service.mock;

import java.util.List;
import java.util.Map;

import com.taiji.pubsec.ajqlc.wrapper.common.bean.ResultBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;


/**
 * 办案区使用单接口
 * @author xinfan
 *
 */
public interface ICaseFormServiceSuspend {
	/**
	 * 同步办案区使用单信息
	 * @param formCode 使用单号
	 * @param caseCode 案件编号
	 * @param suspectId 嫌疑人id 用使用单编号 办案人员信息：
	 * @param polices   案人员信息      【userId：办案人员警号；
	 *                                userRole：主办人/协办人，{1：主办人，2：协办人}】
	 * @return  ResultBean 【success : true ; 同步成功;
	 *                       success : false  同步失败, msg : 失败原因;】
	 */
	public ResultBean syncCaseFormInfo(String formCode,String caseCode,String suspectId,List<Map> polices) throws DahuaException;
	/**
	 * 同步新增案件基本信息
	 * @param caseCode 案件编号
	 * @param caseName 案件名称
	 * @param unitId办案单位id
	 * @param createDate 立案时间
	 * @return  ResultBean 【success : true ; 同步成功;
	 *                       success : false  同步失败, msg : 失败原因;】
	 */
	public ResultBean syncAddCaseInfo(String caseCode , String caseName , String unitId,String createDate) throws DahuaException;
	/**
	 * 同步新增更新案件基本信息
	 * @param caseCode 案件编号
	 * @param caseName 案件名称
	 * @param unitId办案单位id
	 * @return  ResultBean 【success : true ; 同步成功;
	 *                       success : false  同步失败, msg : 失败原因;】
	 */
	public ResultBean syncUpdateCaseInfo(String caseCode , String caseName , String unitId,String createDate) throws DahuaException;
	/**
	 * 同步新增嫌疑人基本信息
	 * @param idcard 身份证号
	 * @param name 姓名
	 * @param fromCode 使用单编号
	 * @return ResultBean 【success : true ; 同步成功;
	 *                      success : false  同步失败, msg : 失败原因;】
	 */
	public ResultBean syncAddSuspectInfo(String idcard, String name,String fromCode) throws DahuaException;
	/**
	 * 同步更新嫌疑人基本信息
	 * @param idcard 身份证号
	 * @param name 姓名
	 * @param fromCode 使用单编号
	 * @return ResultBean 【success : true ; 同步成功;
	 *                      success : false  同步失败, msg : 失败原因;】
	 */
	public ResultBean syncUpdateSuspectInfo(String idcard, String name,String fromCode) throws DahuaException;
	/**
	 * 分配审讯室
	 * @param formCode 使用单号
	 * @param caseCode 案件编号
	 * @param suspectId 嫌疑人id 取使用单号；
	 * @param roomId 审讯室id，取对应房间id；
	 * @param mainUserId 主审人员警号；
	 * @param assistUserId 协审人员警号；
	 * @return ResultBean 【success : true ; 同步成功;
	 *                      success : false  同步失败, msg : 失败原因;】
	 */
	public ResultBean allocateRoom(String formCode,String caseCode,String suspectId,String roomId,String mainUserId,String assistUserId) throws DahuaException;
	
}
