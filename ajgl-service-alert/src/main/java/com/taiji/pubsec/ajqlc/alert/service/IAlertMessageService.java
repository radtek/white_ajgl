package com.taiji.pubsec.ajqlc.alert.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.alert.model.AlertMessage;

/**
	 * 预警消息服务接口
	 * @author XIEHF
	 *
	 */
public interface IAlertMessageService {
	
	/**
	 * 查询预警
	 */
	public Pager<AlertMessage> searchMessageByPersonId(String personId, Date Start, Date endint, int pageNo, int pageSize);
	
	
	/**
	 * 新增预警消息
	 */
	public void saveMessage(AlertMessage message);
	
	/**
	 * 预警消息签收
	 * @param messageId 预警消息id
	 * @return 签收成功，返回true；佛则返回false
	 */
	public boolean signMessage(String messageId);
	
	/**
	 * 通过用户名和时间查询预警消息
	 * @param accountName 用户名
	 * @param queryTime 查询时间
	 * @return 返回预警消息list
	 */
	List<AlertMessage> findNewMessagesByConditions(String accountName, Date queryTime);
	
	/**
	 * 通过用户名和时间查询相应数量的预警消息
	 * @param accountName 用户名
	 * @param queryTime 查询时间
	 * @param length 查询个数
	 * @return 返回Map<Boolean, List<AlertMessage>>，boolean标识还有没有更多的预警消息，为true：表示还有；为false：表示没有。list是查询得到的预警消息
	 */
	Map<Boolean, List<AlertMessage>> findOldMessagesByConditions(String accountName, Date queryTime, int size);
	
	/**
	 * 通过使用单编号查询预警消息列表
	 * @param handlingAreaReceiptCode 使用单编号
	 * @return 返回预警消息列表
	 */
	List<AlertMessage> findAlertMessagesByReceiptCode(String handlingAreaReceiptCode);
	
}