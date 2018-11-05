package com.taiji.pubsec.ajqlc.wrapper.dhpt.service.mock;

import com.taiji.pubsec.ajqlc.wrapper.common.bean.ResultBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;


/**
 * 手环服务
 * @author xinfan
 *
 */
public interface IWristbandServiceSuspend {
	/**
	 * 激活手环
	 * @param wristId 手环id
	 * @param suspectId 嫌疑人id，用使用单编码
	 * @param operatorId 操作人id，用登录账户警号
	 * @return ResultBean 【success : true 激活成功;
	 *                      success : false 激活失败;msg 失败原因】
	 */
	public ResultBean active(String wristId,String suspectId,String operatorId) throws DahuaException;
	/**
	 * 挂起手环
	 * @param wristId 手环物理Id
	 * @return ResultBean 【success : true 激活成功;
	 *                      success : false 激活失败，msg:失败原因;】
	 */
	public ResultBean suspend(String wristId) throws DahuaException;
	/**
	 * 取消挂起
	 * @param wristId  手环物理Id
	 * @return  ResultBean 【success : true 激活成功;
	 *                       success : false 激活失败，msg:失败原因;】
	 */
	public ResultBean resume(String wristId) throws DahuaException;
	/**
	 * 回收手环
	 * @param wristId 手环ID
	 * @param suspectId 嫌疑人id，用使用单编码
	 * @param operatorId 操作人id，用登录账户警号
	 * @return
	 */
	public ResultBean reclaim(String wristId,String suspectId,String operatorId) throws DahuaException;
	
	/**
	 * 查询手环状态信息
	 * @param braceletPhysicalId 手环物理编号，为null时返回所有手环信息
	 * @return ResultBean 【成功：{"success":true,"data":手环信息}；手环信息格式见备注】【失败：{"success":false,"msg":"错误原因"}】
	 * </br> 返回格式：[{"braceletPhysicalId":"xxx","braceletDeviceCode":"ccss","braceletName":"xxx","isFreeze":0,"operateType":0},.....]，其中：
	 * </br>braceletPhysicalId：手环物理编号
	 * </br>braceletDeviceCode：手环设备编号
	 * </br>braceletName：手环名称
	 * </br>isFreeze：挂起状态，0-未挂起、1-挂起
	 * </br>operateType：绑定状态，0-未绑定人员、1-已绑定人员
	 * @throws DahuaException
	 */
	public ResultBean getBraceletInfoByPcId(String braceletPhysicalId) throws DahuaException;
	
}
