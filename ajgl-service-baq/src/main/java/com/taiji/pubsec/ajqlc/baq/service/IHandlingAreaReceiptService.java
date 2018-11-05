package com.taiji.pubsec.ajqlc.baq.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.baq.bean.HandlingAreaReceiptServiceBean;
import com.taiji.pubsec.ajqlc.baq.bean.PoliceInfoBean;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceipt;
import com.taiji.pubsec.ajqlc.baq.model.HandlingAreaReceiptToPoliceInfo;

/**
 * 办案区使用单服务接口
 * @author chengkai
 *
 */
public interface IHandlingAreaReceiptService {
	/**
	 * 保存办案区使用单，并返回其主键
	 * @param handlingAreaReceipt 办案区使用单实体
	 * @return 返回刚保存的办案区使用单的主键
	 */
	public String saveHandlingAreaReceipt(HandlingAreaReceipt handlingAreaReceipt);
	
	/**
	 * 保存办案区使用单，并返回其主键（新的）
	 * @param handlingAreaReceipt 办案区使用单实体
	 * @param piBeanLst 警员数据
	 * @return 返回刚保存的办案区使用单的主键
	 */
	public String saveHandlingAreaReceiptWithPoliceInfo(HandlingAreaReceipt handlingAreaReceipt, List<PoliceInfoBean> piBeanLst);
	
	/**
	 * 删除办案区使用单,参数为ids数组
	 * @param ids 多个办案区使用单id
	 */
	public void deleteHandlingAreaReceipt(Serializable... ids); 
	
	/**
	 * 更新办案区使用单
	 * @param handlingAreaReceipt 办案区使用单实体
	 */
	public void updateHandlingAreaReceipt(HandlingAreaReceipt handlingAreaReceipt);

	/**
	 * 更新办案区使用单
	 * @param handlingAreaReceipt 办案区使用单实体
	 * @param addMap 新增警员key警号val手环号
	 * @param delMap 删除警员key警号val手环号
	 */
	public void updateHandlingAreaReceiptWithPoliceLst(HandlingAreaReceipt handlingAreaReceipt, Map<String, String> addMap, Map<String, String> delMap);
	
	/**
	 * 更新办案区使用单
	 * @param handlingAreaReceipt 办案区使用单实体
	 * @param piBeanLst 警员列表
	 * @param htpTempLstForDelete 删除的警员关系列表
	 */
	public void updateHandlingAreaReceiptWithPoliceInfo(HandlingAreaReceipt handlingAreaReceipt, List<PoliceInfoBean> piBeanLst, List<HandlingAreaReceiptToPoliceInfo> htpTempLstForDelete);
	
	/**
	 * 根据id查找办案区使用单
	 * @param id 办案区使用单id
	 * @return 办案区使用单实体
	 */
	public HandlingAreaReceipt findHandlingAreaReceiptById(String id);
	
	/**
	 * 根据使用单编号查询办案区使用单
	 * @param receiptNum 办案区使用单编号
	 * @return 返回办案区使用单
	 */
	public HandlingAreaReceipt findHandlingAreaReceiptByNum(String receiptNum);
	
	/**
	 * 分页查询本单位的办案区使用单，unitId为空或者null时查询所有单位,根据创建时间倒序排列
	 * @param queryConditions 查询条件，Map<String, String>类型，有：
	 * 		unitId 单位id
	 * 		handlingAreaReceiptNum 使用单编码
	 * 		askRoomName 使用房间名称
	 * 		handlingPolice 办案民警
	 * 		haveRecord 是否上传问询笔录,为“1”有笔录，为“0”无笔录
	 * 		status 使用单状态
	 * 		enterAreaReasons 进入办案区原由
	 * 		byQuestioningPeopleName 被讯问人姓名
	 * 		byQuestioningPeopleIdentifyNum 被讯问人证件号码
	 * 		lawCase 所属案件
	 * 		enterIntoTimeStart 开始时间
	 * 		enterIntoTimeStartEnd 结束时间
	 * 		modifyPeopleName 申请使用人
	 * 		ifRecord 是否已刻录，为“0”未刻录，为“1”已刻录
	 * @param pageNo 页码
	 * @param pageSize 页面大小
	 * @return 办案区使用单分页信息
	 */
	public Pager<HandlingAreaReceipt> findHandlingAreaReceiptByKey(Map<String,Object> queryConditions, int pageNo, int pageSize);
	public Pager<HandlingAreaReceipt> findHandlingAreaReceiptByKey(String id, int pageNo, int pageSize);
	
	/**
	 * 获取使用单编号
	 * @return
	 */
	public String acquireNum();
	
	/**
	 * 根据房间id查询正在使用该房间的使用单基本信息，如果该房间空闲则返回null
	 * @param roomId 房间id
	 * @return 使用单基本信息
	 */
	public HandlingAreaReceipt findHandlingAreaReceiptByRoomId(String roomId);
	
	/**
	 * 完成本次问讯时将使用单状态改为已完成
	 * @param receiptId 使用单id
	 */
	public void finishHandlingAreaReceipt(String receiptId);
	
	/**
	 * 问询室分配时默认查询本单位的状态为“进行中”的且当前在用问询室为“无”的办案区使用单，可以通过多条件查询
	 * @param queryConditions 查询条件，Map<String, String>类型，有：
	 * 		unitId 单位id
	 * 		handlingAreaReceiptNum 使用单编码
	 * 		handlingPolice 办案民警
	 * 		modifyPeople 申请使用人
	 * 		byQuestioningPeopleName 被讯问人姓名
	 * 		byQuestioningPeopleIdentifyNum 被讯问人证件号码
	 * 		enterAreaReasons 进入办案区原由
	 * 		lawCase 所属案件
	 * 		enterIntoTimeStart 开始时间
	 * 		enterIntoTimeStartEnd 结束时间
	 * @return 办案区使用单list
	 */
	public List<HandlingAreaReceipt> findHandlingAreaReceiptByConditions(Map<String, Object> queryConditions);
	
	/**
	 * 通过手环编号查询对应的进行中的使用单id
	 * @param bangleCode 手环编号
	 * @return 返回使用单ID，若查询不到，返回null
	 */
	public String findHandlingAreaReceiptIdByBangleCode(String bangleCode);
	
	/**
	 * 通过icNum查询对应的进行中的使用单id
	 * @param icNum ic卡号
	 * @return 返回使用单IDLst
	 */
	public List<String> findHandlingAreaReceiptIdByIcNum(String icNum);
	
	/**
	 * 通过角色ID查询所有该角色的用户id
	 * @param roleId 角色id
	 * @return 返回用户id的list
	 */
	public List<String> findAccountsByRoleId(String roleId);
	
	/**
	 * 通过条件查询“今日讯（询）问嫌疑人列表”
	 * @param conditions 查询条件，包括：
	 * 		<br> queryStartTime 起始时间
	 * 		<br> queryEndTime 结束时间
	 * @return 返回嫌疑人信息列表
	 */
	public List<HandlingAreaReceiptServiceBean> findJudgingSuspectsByConditions(Map<String, Object> conditions);
	
	/**
	 * 查询所有主办单位在相应时间段内的嫌疑人入区数
	 * @param conditions 查询条件，包括：
	 * 			<br> startTime 查询开始时间
	 * 			<br> endTime 查询结束时间
	 * @return 返回Map<String, Integer>，String是单位名称，Integer是该单位对应的嫌疑人入区数
	 */
	public Map<String, Integer> findVariousUnitsSuspectNums(Map<String, Object> conditions);
	/**
	 * 检查此民警卡是否可以用 
	 * @param icNum 民警卡卡号
	 * @return true为可用;false为不可用
	 */
	public boolean checkIcNumIsFree(String icNum);
	/**
	 * 检查此民警是否可以绑定民警卡
	 * @param policNum 民警警号
	 * @return
	 */
	public boolean checkPoliceIsFree(String policNum);
	
}
