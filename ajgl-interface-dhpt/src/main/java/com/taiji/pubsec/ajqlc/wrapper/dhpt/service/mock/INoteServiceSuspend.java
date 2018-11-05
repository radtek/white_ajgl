package com.taiji.pubsec.ajqlc.wrapper.dhpt.service.mock;

import com.taiji.pubsec.ajqlc.wrapper.common.bean.ResultBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;


/**
 * 笔录系统接口
 * @author xinfan
 *
 */
public interface INoteServiceSuspend {
	/**
	 * 打开审讯笔录控件页面。返回结果的data是ocx控件打开电子笔录时所需的数据，类型为json字符串。
	 * @param ip 审讯室绑定的审讯电脑的ip；
	 * @param userCode 审讯用户警号
	 * @return json json字符串
	 */
	public String produceOcxParam(String ip , String userCode) throws DahuaException;
	/**
	 * 电子笔录打开或关闭时同步状态。
	 * @param id 审讯ID（由ocx提供）
	 * @param stat 审讯状态（由ocx提供）
	 * @param strTime 操作时间（由ocx提供）
	 * @return ResultBean 【success : true 保存成功;
	 *                       success : false 保存失败，msg:失败原因;】
	 */
	public ResultBean syncTrialNoteStat(String id ,String stat ,String strTime) throws DahuaException;
}
