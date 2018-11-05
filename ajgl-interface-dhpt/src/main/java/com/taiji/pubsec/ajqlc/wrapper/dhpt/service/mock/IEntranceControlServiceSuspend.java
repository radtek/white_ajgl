package com.taiji.pubsec.ajqlc.wrapper.dhpt.service.mock;

import com.taiji.pubsec.ajqlc.wrapper.common.bean.ResultBean;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;


/**
 * 门禁服务
 * @author xinfan
 *
 */
public interface IEntranceControlServiceSuspend {
/**
 * 
 * @param cardId 定位卡id
 * @param roomId 房间id 取案管系统房间管理中的房间id
 * @return  ResultBean 【success : true 授予权限成功;
 *                       success : false 授予权限失败 , msg : 失败原因】
 */
	 public ResultBean authorize(String cardId,String roomId) throws DahuaException;
}
