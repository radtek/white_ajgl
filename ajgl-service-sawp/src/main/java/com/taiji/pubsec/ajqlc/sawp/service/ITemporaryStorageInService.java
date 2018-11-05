package com.taiji.pubsec.ajqlc.sawp.service;

import java.util.List;
import java.util.Map;

import com.taiji.persistence.dao.Pager;
import com.taiji.pubsec.ajqlc.sawp.bean.TempObjectBean;
import com.taiji.pubsec.ajqlc.sawp.bean.TempStorageInInfoBean;
import com.taiji.pubsec.ajqlc.sawp.bean.TemporaryStorageShelfBean;
import com.taiji.pubsec.ajqlc.sawp.model.TemporaryStorageIn;
/**
 * 暂存入库单 service
 * @author xinfan
 *
 */
public interface ITemporaryStorageInService {
	
	/**
	 * 保存暂存入库单
	 * @param temporaryStorageIn
	 * @return
	 */
	String  saveTemporaryStorageIn(TemporaryStorageIn temporaryStorageIn );
	/**
	 * 更新 暂存入库单
	 * @param temporaryStorageIn
	 * @return
	 */
	void  updateTemporaryStorageIn(TemporaryStorageIn temporaryStorageIn);
	/**查询 暂存入库单
	 * 查询条件如下：
	 * code:   入库单编号
	 * rkStartDate:入库单查询的起始时间 
	 * rkEndDate:入库单查询的结束时间 
	 * caseCode:  案件编号
	 * ownerName: 物品持有人  
	 * myid： 如果显示我的出库返还单：我的id，如果不选择显示我的出库返还单，为空
	 * @return
	 */
	Pager<TempStorageInInfoBean> findTemporaryStorageInByPage(
			Map<String, Object> paramMap, int pageNo, int pageSize);
    /**
     * 查询 暂存入库单详情 关联使用单基本信息
     * @param code 入库单编码
     * @return
     */
	TempStorageInInfoBean findTemporaryStorageInDeatailByCode(String code);
	/**
	 * 根据使用单code 查找暂存物品 没有则返回null
	 * @param storageId
	 * @return
	 */
	List<TempObjectBean> findTemporaryObjectByStorageByCode(String harCode);
	/**
	 * 根据入库单id 查询入库单 未关联案件基本信息
	 * @param storageId
	 * @return
	 */
	TemporaryStorageIn findTemporaryStorageInById(String storageId);
	/**
	 * 根据入库单code 查询入库单 未关联案件基本信息
	 * @param code 入库单编码
	 * @return
	 */
	TemporaryStorageIn findTemporaryStorageInByCode(String code);
	/**
	 * 根据使用单code 查询入库单 未关联案件基本信息
	 * @param code 使用单编码
	 * @return
	 */
	TemporaryStorageIn findTemporaryStorageInByHarCode(String harCode);
	/**
	 * 根据入库单code 查询所有储物柜 没有返回null
	 * @param storageId
	 * @return
	 */
	List<TemporaryStorageShelfBean> findStorageShelfByCode(String code);
	/**
	 * 获取入库单编号
	 * @return
	 */
	String acquireNum();
	/**
	 * 查询所有超时入库单
	 * @param pageSize 页大小
	 * @param pageNo 第几页
	 * @param paramMap 条件Map
	 * 
	 * areaCode : 储物区code
	 * shelfCodeList ： 储物柜编码List
	 * storageInCode : 入库单编号
	 * caseCodeOrName : 案件编号或名称
	 * suspectIdOrName : 嫌疑人Id或Name
	 * @return 返回入库单的入库时间，备注，使用单编号，入库单编码
	 */
	Pager<TempStorageInInfoBean> findTimeoutTemporaryInByPage(Map<String, Object> paramMap, int pageNo, int pageSize);
	
}
