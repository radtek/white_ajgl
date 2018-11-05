package com.taiji.pubsec.ajqlc.wrapper.dhpt.service;

import com.taiji.pubsec.ajqlc.wrapper.common.bean.ResultBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;

/**
 * 定位卡服务
 * @author xinfan
 *
 */
public interface ILocationCardService {
	/**
	 * 激活定位卡
	 * @param id 定位卡id
	 * @param policeId 办案民警id，用该民警警号
	 * @param operatorId 操作人id，用登录账户警号
	 * @return ResultBean 【success : true 激活成功;
	 *                      success : false 激活失败;msg 失败原因】
	 */
	public ResultBean active(String id,String policeId,String operatorId) throws DahuaException;
	/**
	 * 挂起定位卡
	 * @param locationCardId 定位卡物理Id
	 * @return ResultBean 【success : true 激活成功;
	 *                      success : false 激活失败，msg:失败原因;】
	 */
	public ResultBean suspend(String locationCardId) throws DahuaException;
	/**
	 * 取消挂起
	 * @param locationCardId  定位卡物理Id
	 * @return  ResultBean 【success : true 激活成功;
	 *                       success : false 激活失败，msg:失败原因;】
	 */
	public ResultBean resume(String locationCardId) throws DahuaException;
	/**
	 * 回收定位卡
	 * @param id 定位卡id
	 * @param policeId 用该民警警号
	 * @param operatorId 操作人id，用登录账户警号
	 * @return ResultBean 【success : true 激活成功;
	 *                      success : false 激活失败，msg:失败原因;】
	 */
	public ResultBean reclaim(String id,String policeId,String operatorId) throws DahuaException;
}
